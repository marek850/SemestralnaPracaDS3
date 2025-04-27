package agents.modelagent;

import Entities.Order;
import Entities.OrderItem;
import OSPABA.*;
import simulation.*;

//meta! id="1"
public class ModelManager extends OSPABA.Manager
{
	public ModelManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="SurroundingAgent", id="32", type="Notice"
	public void processOrderArrival(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.processOrder);
		msg.setAddressee(Id.workshopAgent);
		request(msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.initialisation:
				message.setAddressee(mySim().findAgent(Id.surroundingAgent));
				notice(message);
				break;
			
		}
	}

	//meta! sender="WorkshopAgent", id="123", type="Response"
	public void processProcessOrder(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.orderCompletion);
		msg.setAddressee(Id.surroundingAgent);
		notice(msg);
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
		case Mc.processOrder:
			processProcessOrder(message);
		break;

		case Mc.orderArrival:
			processOrderArrival(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public ModelAgent myAgent()
	{
		return (ModelAgent)super.myAgent();
	}

}