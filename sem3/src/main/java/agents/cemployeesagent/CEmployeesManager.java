package agents.cemployeesagent;

import Entities.Employee;
import Entities.States.EmployeeState;
import Entities.States.FurnitureType;
import Entities.States.OrderItemState;
import Entities.States.Position;
import OSPABA.*;
import simulation.*;

//meta! id="8"
public class CEmployeesManager extends OSPABA.Manager
{
	public CEmployeesManager(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="160", type="Response"
	public void processTransferCEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		if (msg.getOrderItem().getState() == OrderItemState.CUT || msg.getOrderItem().getState() == OrderItemState.WAITING_FOR_VARNISH) {
			msg.setCode(Mc.varnishOrderitem);
			msg.setAddressee(myAgent().findAssistant(Id.varnishProcess));
			startContinualAssistant(msg);
		} else {
			msg.setCode(Mc.cFitHardwareOnItem);
			msg.setAddressee(myAgent().findAssistant(Id.cFitHardwareProcess));
			startContinualAssistant(msg);
		}
	}

	//meta! sender="WorkshopAgent", id="146", type="Request"
	public void processRequestCWaitingOrders(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setAWaitingOrders(myAgent().getWaitingOrdersHardwareFit().size());
		response(msg);
	}

	//meta! sender="WorkshopAgent", id="149", type="Request"
	public void processVarnishOrderitem(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_VARNISH);
			myAgent().addWaitingOrderVarnish(msg);
		} else{
			msg.setEmployee(myAgent().assignEmployee());
			if (msg.getEmployee().getCurrentPosition() == Position.ASSEMBLY_STATION &&
					msg.getEmployee().getStation() == msg.getAssemblyStation()) {

					msg.setAddressee(myAgent().findAssistant(Id.varnishProcess));
					startContinualAssistant(msg);
			} else{
				msg.setCode(Mc.transferCEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
		}
	}

	//meta! sender="WorkshopAgent", id="156", type="Request"
	public void processCFitHardwareOnItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		//ak nie je volny zamestnanec tak dam spravu do cakania
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_FITTING);
			myAgent().addWaitingOrderFitting(msg);
		} else{
			//ak je volny zamestnanec tak skontrolujem ci ho treba presunut
			msg.setEmployee(myAgent().assignEmployee());
			if(msg.getEmployee().getCurrentPosition() == Position.ASSEMBLY_STATION && msg.getEmployee().getStation() == msg.getAssemblyStation()) {
				//ak je uz na montaznom mieste tak zacne s montazou
				msg.setCode(Mc.cFitHardwareOnItem);
				msg.setAddressee(myAgent().findAssistant(Id.cFitHardwareProcess));
				startContinualAssistant(msg);
			}else {
				//ak noie je v sklade tak ho tam presuniem
				msg.setCode(Mc.transferCEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
			
		}
	}

	//meta! sender="VarnishProcess", id="170", type="Finish"
	public void processFinishVarnishProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		if(msg.getOrderItem().isStain()){
			msg.setAddressee(myAgent().findAssistant(Id.stainProcess));
			startContinualAssistant(msg);
		} else{
			Employee finishedEmployee = msg.getEmployee();
			handleFinishedEmployee(finishedEmployee);
			if(msg.getOrderItem().getItemType() == FurnitureType.WARDROBE) {
				System.out.println("");
			}
			msg.setEmployee(null);
			msg.setCode(Mc.varnishOrderitem);
			response(msg);
		}
	}

	//meta! sender="StainProcess", id="168", type="Finish"
	public void processFinishStainProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		if(msg.getOrderItem().getItemType() == FurnitureType.WARDROBE) {
			System.out.println("");
		}
		msg.setEmployee(null);
		msg.setCode(Mc.varnishOrderitem);
		response(msg);
	}

	public void handleFinishedEmployee(Employee finishedEmployee) {
		if (!myAgent().getWaitingOrdersHardwareFit().isEmpty()) {
			//ak caka objednavka na montaz kovani je uprednostnena a zamestnanec sa musi presunut na ine montazne miesto
			MyMessage waitingOrder = myAgent().getWaitingOrderFitting();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferCEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else if(!myAgent().getWaitingOrdersVarnish().isEmpty()) {
			//ak caka objednavka na morenie tak presunieme agenta do skladu
			MyMessage waitingOrder = myAgent().getWaitingOrderVarnish();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferCEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else {
			//ak necaka ziadna objednavka
			finishedEmployee.setState(EmployeeState.IDLE);
			myAgent().releaseEmployee(finishedEmployee);
		}

	}

	//meta! sender="CFitHardwareProcess", id="172", type="Finish"
	public void processFinishCFitHardwareProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		msg.setEmployee(null);
		msg.setCode(Mc.cFitHardwareOnItem);
		response(msg);

	}

	//meta! sender="WorkshopAgent", id="154", type="Request"
	public void processStainOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="178", type="Request"
	public void processRequestNumOfFreeEmpC(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCEmployeesNumber(myAgent().getFreeEmployees().size());
		response(msg);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.stainOrderItem:
			processStainOrderItem(message);
		break;

		case Mc.transferCEmployee:
			processTransferCEmployee(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.varnishProcess:
				processFinishVarnishProcess(message);
			break;

			case Id.stainProcess:
				processFinishStainProcess(message);
			break;

			case Id.cFitHardwareProcess:
				processFinishCFitHardwareProcess(message);
			break;
			}
		break;

		case Mc.requestCWaitingOrders:
			processRequestCWaitingOrders(message);
		break;

		case Mc.requestNumOfFreeEmpC:
			processRequestNumOfFreeEmpC(message);
		break;

		case Mc.varnishOrderitem:
			processVarnishOrderitem(message);
		break;

		case Mc.cFitHardwareOnItem:
			processCFitHardwareOnItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public CEmployeesAgent myAgent()
	{
		return (CEmployeesAgent)super.myAgent();
	}

}