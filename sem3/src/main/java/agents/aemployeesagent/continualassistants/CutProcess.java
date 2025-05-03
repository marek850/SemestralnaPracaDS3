package agents.aemployeesagent.continualassistants;

import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import OSPABA.*;
import simulation.*;
import agents.aemployeesagent.*;
import Entities.States.Process;
import OSPRNG.EmpiricPair;
import OSPRNG.EmpiricRNG;
import OSPRNG.RNG;
import OSPRNG.UniformContinuousRNG;

//meta! id="161"
public class CutProcess extends OSPABA.Process
{
	private EmpiricRNG tableCutTime ;
	private UniformContinuousRNG chairCutTime;
	private UniformContinuousRNG wardrobeCutTime;
	public CutProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		MySimulation simulation = (MySimulation) mySim;
		tableCutTime = new EmpiricRNG(/* simulation.seedGenerator, */
		new EmpiricPair(new UniformContinuousRNG(600d, 1500d), 0.6),
		new EmpiricPair(new UniformContinuousRNG(1500d, 3000d), 0.4)
		);
	chairCutTime = new UniformContinuousRNG(720d, 960d/* ,simulation.seedGenerator */);
	wardrobeCutTime = new UniformContinuousRNG(900d, 4800d/* ,simulation.seedGenerator */);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AEmployeesAgent", id="162", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.getEmployee().setState(EmployeeState.CUTTING);
		myMessage.getAssemblyStation().setCurrentProcess(Process.CUTTING);
		myMessage.getOrderItem().setState(OrderItemState.BEING_CUT);
		myMessage.setCode(Mc.cutOrderItem);
		switch (myMessage.getOrderItem().getItemType()) {
			case CHAIR:
				hold(chairCutTime.sample(), myMessage);
				break;
			case TABLE:
				hold((double)tableCutTime.sample(), myMessage);
				break;
			case WARDROBE:
				hold(wardrobeCutTime.sample(), myMessage);
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
			case Mc.cutOrderItem:
				MyMessage myMessage = (MyMessage) message;
				myMessage.getOrderItem().setState(OrderItemState.CUT);
				myMessage.getEmployee().setState(EmployeeState.IDLE);
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
	public AEmployeesAgent myAgent()
	{
		return (AEmployeesAgent)super.myAgent();
	}

}