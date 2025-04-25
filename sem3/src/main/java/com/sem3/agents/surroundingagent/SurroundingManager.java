package com.sem3.agents.surroundingagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="3"
public class SurroundingManager extends OSPABA.Manager
{
	public SurroundingManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="ModelAgent", id="33", type="Notice"
	public void processOrderCompletion(MessageForm message)
	{
	}

	//meta! sender="ModelAgent", id="29", type="Notice"
	public void processInitialisation(MessageForm message)
	{
		message.setAddressee(myAgent().findAssistant(Id.arrivalPlanner));
		startContinualAssistant(message);
	}

	//meta! sender="ArrivalPlanner", id="38", type="Finish"
	public void processFinish(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.orderArrival:
				message.setAddressee(myAgent().parent());
				notice(message);
				MyMessage newMessage = (MyMessage)message.createCopy();
				newMessage.setAddressee(myAgent().findAssistant(Id.arrivalPlanner));
				startContinualAssistant(newMessage);
				break;
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
		case Mc.initialisation:
			processInitialisation(message);
		break;

		case Mc.orderCompletion:
			processOrderCompletion(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public SurroundingAgent myAgent()
	{
		return (SurroundingAgent)super.myAgent();
	}

}
