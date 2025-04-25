package agents.workshopagent.continualassistants;

import java.util.List;

import Entities.OrderItem;
import Entities.States.FurnitureType;
import OSPABA.*;
import simulation.*;
import agents.workshopagent.*;
import OSPABA.Process;
import OSPRNG.EmpiricPair;
import OSPRNG.EmpiricRNG;
import OSPRNG.UniformContinuousRNG;
import OSPRNG.UniformDiscreteRNG;

//meta! id="97"
public class CutProcess extends OSPABA.Process
{
	private EmpiricRNG tableCutTimeGen = new EmpiricRNG(new EmpiricPair(new UniformContinuousRNG(600d, 1500d), 0.6),
			new EmpiricPair(new UniformDiscreteRNG(1500, 3000), 0.4));
	private UniformContinuousRNG wardrobeCutTimeGen = new UniformContinuousRNG(900d, 4800d);
	private UniformContinuousRNG chairCutTimeGen = new UniformContinuousRNG(720d, 960d);

	public CutProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="WorkshopAgent", id="98", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message;
		OrderItem orderItem = myMessage.getOrderItem();
		myMessage.setCode(Mc.finishCutItem);
		switch (orderItem.getItemType())
		{
			case FurnitureType.TABLE:
				hold(wardrobeCutTimeGen.sample(), myMessage);
				break;
			case FurnitureType.WARDROBE:
				hold(wardrobeCutTimeGen.sample(), myMessage);
				break;
			case FurnitureType.CHAIR:
				hold(chairCutTimeGen.sample(), myMessage);
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
	public WorkshopAgent myAgent()
	{
		return (WorkshopAgent)super.myAgent();
	}

}