package agents.bemployeesagent;

import Entities.Employee;
import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import Entities.States.Position;
import OSPABA.*;
import simulation.*;

//meta! id="7"
public class BEmployeesManager extends OSPABA.Manager
{
	public BEmployeesManager(int id, Simulation mySim, Agent myAgent)
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
	public void processEmployeeBAssignment(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processEmployeeBRelease(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="148", type="Request"
	public void processAssembleOrderItem(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_ASSEMBLY);
			myAgent().addWaitingOrderAssemble(msg);
		} else{
			msg.setEmployee(myAgent().assignEmployee());
			if (msg.getEmployee().getCurrentPosition() == Position.ASSEMBLY_STATION &&
					msg.getEmployee().getStation() == msg.getAssemblyStation()) {

					msg.setAddressee(myAgent().findAssistant(Id.assembleProcess));
					startContinualAssistant(msg);
			} else{
				msg.setCode(Mc.transferBEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
		}
	}

	//meta! sender="WorkshopAgent", id="159", type="Response"
	public void processTransferBEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		if (msg.getOrderItem().getState() == OrderItemState.VARNISHED || msg.getOrderItem().getState() == OrderItemState.WAITING_FOR_ASSEMBLY|| msg.getOrderItem().getState() == OrderItemState.STAINED) {
			msg.setCode(Mc.assembleOrderItem);
			msg.setAddressee(myAgent().findAssistant(Id.assembleProcess));
			startContinualAssistant(msg);
		}
	}

	//meta! sender="AssembleProcess", id="165", type="Finish"
	public void processFinish(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		msg.setEmployee(null);
		msg.setCode(Mc.assembleOrderItem);
		response(msg);
	}

	public void handleFinishedEmployee(Employee finishedEmployee) {
		if(!myAgent().getWaitingOrdersAssemble().isEmpty()) {
			//ak caka objednavka na rezanie tak presunieme agenta do skladu
			MyMessage waitingOrder = myAgent().getWaitingOrderAssemble();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferBEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else {
			//ak necaka ziadna objednavka
			finishedEmployee.setState(EmployeeState.IDLE);
			myAgent().releaseEmployee(finishedEmployee);
		}

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
		case Mc.assembleOrderItem:
			processAssembleOrderItem(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		case Mc.transferBEmployee:
			processTransferBEmployee(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public BEmployeesAgent myAgent()
	{
		return (BEmployeesAgent)super.myAgent();
	}

}