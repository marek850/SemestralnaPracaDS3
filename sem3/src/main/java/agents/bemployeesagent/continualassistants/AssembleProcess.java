package agents.bemployeesagent.continualassistants;

import Entities.OrderItem;
import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import Entities.States.Process;
import OSPABA.*;
import agents.bemployeesagent.*;
import simulation.*;
import OSPRNG.UniformContinuousRNG;

//meta! id="164"
public class AssembleProcess extends OSPABA.Process
{
	private  UniformContinuousRNG tableAssembleTime ;
	private  UniformContinuousRNG chairAssembleTime ;
	private  UniformContinuousRNG wardronbeAssembleTime ;
	public AssembleProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		tableAssembleTime = new UniformContinuousRNG(1800d, 3600d/* , simulation.seedGenerator */);
		chairAssembleTime = new UniformContinuousRNG(840d, 1440d/* ,simulation.seedGenerator */);
		wardronbeAssembleTime = new UniformContinuousRNG(2100d, 4500d/* ,simulation.seedGenerator */);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BEmployeesAgent", id="165", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.getEmployee().setState(EmployeeState.ASSEMBLING);
		myMessage.getAssemblyStation().setCurrentProcess(Process.ASSEMBLING);
		OrderItem orderItem = myMessage.getOrderItem();
		orderItem.setState(OrderItemState.BEING_ASSEMBLED);
		myMessage.setCode(Mc.assembleOrderItem);
		switch (orderItem.getItemType()) {
			case CHAIR:
				hold(chairAssembleTime.sample(), myMessage);
				break;
			case TABLE:
				hold(tableAssembleTime.sample(), myMessage);
				break;
			case WARDROBE:
				hold(wardronbeAssembleTime.sample(), myMessage);
				break;
			default:
				break;
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.assembleOrderItem:
				MyMessage myMessage = (MyMessage) message;
				myMessage.getEmployee().setState(EmployeeState.IDLE);
				myMessage.getOrderItem().setState(OrderItemState.ASSEMBLED);
				myMessage.setCode(Mc.finish);
				myMessage.setAddressee(myAgent());
				notice(myMessage);
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
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