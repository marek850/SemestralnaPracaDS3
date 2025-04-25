package com.sem3.agents.modelagent;

import OSPABA.*;
import com.sem3.simulation.*;

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

	//meta! sender="WorkshopAgent", id="50", type="Response"
	public void processProcessOrder(MessageForm message)
	{
	}

	//meta! sender="SurroundingAgent", id="32", type="Notice"
	public void processOrderArrival(MessageForm message)
	{
		message.setCode(Mc.cutOrderItem);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
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
