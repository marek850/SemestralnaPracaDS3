package agents.aemployeesagent;

import Entities.Employee;
import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import Entities.States.Position;
import OSPABA.*;
import agents.workshopagent.WorkshopAgent;
import simulation.*;

//meta! id="6"
public class AEmployeesManager extends OSPABA.Manager
{
	public AEmployeesManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! userInfo="Removed from model"
	public void processEmployeeARelease(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processEmployeeAAssignment(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="143", type="Request"
	public void processRequestAWaitingOrders(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setAWaitingOrders(myAgent().getWaitingOrdersHardwareFit().size());
		response(msg);
	}

	//meta! sender="WorkshopAgent", id="157", type="Request"
	public void processAFitHardwareOnItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		//ak nie je volny zamestnanec tak dam spravu do cakania
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_FITTING);
			myAgent().addWaitingOrderFitting(msg);
		} else{
			//ak je volny zamestnanec tak skontrolujem ci ho treba presunut
			msg.setEmployee(myAgent().assignEmployee());
			if(msg.getEmployee().getCurrentPosition() == Position.ASSEMBLY_STATION && msg.getEmployee().getStation() == msg.getAssemblyStation()) {
				//ak je uz na montaznom mieste tak zacne s montazou
				msg.setCode(Mc.aFitHardwareOnItem);
				msg.setAddressee(myAgent().findAssistant(Id.aFitHardwareProcess));
				startContinualAssistant(msg);
			}else {
				//ak noie je v sklade tak ho tam presuniem
				msg.setCode(Mc.transferAEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
			
		}
	}

	//meta! sender="WorkshopAgent", id="158", type="Response"
	public void processTransferAEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		OrderItemState state = msg.getOrderItem().getState();
		if (state == OrderItemState.PENDING) {
			msg.setCode(Mc.preparingMaterial);
			msg.setAddressee(myAgent().findAssistant(Id.materialPrepareProcess));
			startContinualAssistant(msg);
		} else if(state == OrderItemState.MATERIAL_PREPARED){
			msg.setCode(Mc.cutting);
			msg.setAddressee(myAgent().findAssistant(Id.cutProcess));
			startContinualAssistant(msg);
		} else {
			msg.setCode(Mc.aFitHardwareOnItem);
			msg.setAddressee(myAgent().findAssistant(Id.aFitHardwareProcess));
			startContinualAssistant(msg);
		}
		
	}

	//meta! sender="WorkshopAgent", id="147", type="Request"
	public void processCutOrderItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		//ak nie je volny zamestnanec tak dam spravu do cakania
		if (myAgent().getFreeEmployees().isEmpty()) {
			WorkshopAgent statagent = (WorkshopAgent)mySim().findAgent(Id.workshopAgent);
			statagent.getWaitingOrders().addSample(myAgent().getWaitingOrdersCutting().size());
			myAgent().addWaitingOrderCutting(msg);
		} else{
			//ak je volny zamestnanec tak skontrolujem ci ho treba presunut
			msg.setEmployee(myAgent().assignEmployee());
			if(msg.getEmployee().getCurrentPosition() == Position.STORAGE) {
				//ak je uz v sklade nemusim presuvat a zacne s pripravou materialu
				//msg.setCode(Mc.preparingMaterial);
				msg.setAddressee(myAgent().findAssistant(Id.materialPrepareProcess));
				startContinualAssistant(msg);
			}else {
				//ak noie je v sklade tak ho tam presuniem
				msg.setCode(Mc.transferAEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
			
		}
		
	}

	//meta! sender="AFitHardwareProcess", id="174", type="Finish"
	public void processFinishAFitHardwareProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		msg.setEmployee(null);
		msg.setCode(Mc.aFitHardwareOnItem);
		response(msg);
	}
	public void handleFinishedEmployee(Employee finishedEmployee) {
		if (!myAgent().getWaitingOrdersHardwareFit().isEmpty()) {
			//ak caka objednavka na montaz kovani je uprednostnena a zamestnanec sa musi presunut na ine montazne miesto
			MyMessage waitingOrder = myAgent().getWaitingOrderFitting();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferAEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else if(!myAgent().getWaitingOrdersCutting().isEmpty()) {
			//ak caka objednavka na rezanie tak presunieme agenta do skladu
			MyMessage waitingOrder = myAgent().getWaitingOrderCutting();
			WorkshopAgent statagent = (WorkshopAgent)mySim().findAgent(Id.workshopAgent);
			statagent.getWaitingOrders().addSample(myAgent().getWaitingOrdersCutting().size());
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferAEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else {
			//ak necaka ziadna objednavka
			finishedEmployee.setState(EmployeeState.IDLE);
			myAgent().releaseEmployee(finishedEmployee);
		}
	}
	//meta! sender="CutProcess", id="162", type="Finish"
	public void processFinishCutProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		//Poslem agentovi WorkshopAgent odpoved s narezanym kusom objednavky
		msg.getOrderItem().setState(OrderItemState.CUT);
		msg.setCode(Mc.cutOrderItem);
		msg.setEmployee(null);
		response(msg);
	}

	//meta! sender="WorkshopAgent", id="177", type="Request"
	public void processRequestNumOfFreeEmpA(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setAEmployeesNumber(myAgent().getFreeEmployees().size());
		response(msg);
	}

	//meta! sender="MaterialPrepareProcess", id="182", type="Finish"
	public void processFinishMaterialPrepareProcess(MessageForm message)
	{
		//po priprave materialu ho musim vzdy presunut na montazne miesto
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.transferAEmployee);
		msg.setAddressee(myAgent().parent());
		request(msg);
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
		case Mc.requestNumOfFreeEmpA:
			processRequestNumOfFreeEmpA(message);
		break;

		case Mc.requestAWaitingOrders:
			processRequestAWaitingOrders(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.aFitHardwareProcess:
				processFinishAFitHardwareProcess(message);
			break;

			case Id.materialPrepareProcess:
				processFinishMaterialPrepareProcess(message);
			break;

			case Id.cutProcess:
				processFinishCutProcess(message);
			break;
			}
		break;

		case Mc.transferAEmployee:
			processTransferAEmployee(message);
		break;
		case Mc.cutOrderItem:
			processCutOrderItem(message);
		break;

		case Mc.aFitHardwareOnItem:
			processAFitHardwareOnItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AEmployeesAgent myAgent()
	{
		return (AEmployeesAgent)super.myAgent();
	}

}