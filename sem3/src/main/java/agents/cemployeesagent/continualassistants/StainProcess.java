package agents.cemployeesagent.continualassistants;

import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import OSPABA.*;
import simulation.*;
import agents.cemployeesagent.*;
import Entities.States.Process;
import OSPRNG.EmpiricPair;
import OSPRNG.EmpiricRNG;
import OSPRNG.UniformContinuousRNG;

//meta! id="167"
public class StainProcess extends OSPABA.Process
{
	private  EmpiricRNG tableStainTime = new EmpiricRNG(new EmpiricPair(
			new UniformContinuousRNG(3000d, 4200d), 0.1),
			new EmpiricPair(new UniformContinuousRNG(4200d, 9000d), 0.6),
			new EmpiricPair(new UniformContinuousRNG(9000d, 12000d), 0.3));
	private  UniformContinuousRNG chairStainTime = new UniformContinuousRNG(2400d, 12000d);
	private  UniformContinuousRNG wardrobeStainTime = new UniformContinuousRNG(15000d, 33600d);
	public StainProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CEmployeesAgent", id="168", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message;
		myMessage.getEmployee().setState(EmployeeState.STAINING);
		myMessage.getAssemblyStation().setCurrentProcess(Process.STAINING);
		myMessage.getOrderItem().setState(OrderItemState.BEING_STAINED);
		myMessage.setCode(Mc.stainOrderItem);
		switch (myMessage.getOrderItem().getItemType()) {
			case CHAIR:
				hold(chairStainTime.sample(), myMessage);
				break;
			case TABLE:
				hold((double)tableStainTime.sample(), myMessage);
				break;
			case WARDROBE:
				hold(wardrobeStainTime.sample(), myMessage);
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
			case Mc.stainOrderItem:
				MyMessage myMessage = (MyMessage) message;
				myMessage.getEmployee().setState(EmployeeState.IDLE);
				myMessage.getOrderItem().setState(OrderItemState.STAINED);
				myMessage.setCode(Mc.finish);
				myMessage.setAddressee(myAgent());
				notice(myMessage);
				break;
			default:
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
	public CEmployeesAgent myAgent()
	{
		return (CEmployeesAgent)super.myAgent();
	}

}