package com.sem3.agents.bemployeesagent;

import OSPABA.*;
import com.sem3.simulation.*;

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

	//meta! sender="WorkshopAgent", id="45", type="Request"
	public void processEmployeeBAssignment(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="48", type="Notice"
	public void processEmployeeBRelease(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="53", type="Request"
	public void processAssembleOrderItem(MessageForm message)
	{
	}

	//meta! sender="AssembleProcess", id="68", type="Finish"
	public void processFinish(MessageForm message)
	{
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
		case Mc.finish:
			processFinish(message);
		break;

		case Mc.employeeBAssignment:
			processEmployeeBAssignment(message);
		break;

		case Mc.employeeBRelease:
			processEmployeeBRelease(message);
		break;

		case Mc.assembleOrderItem:
			processAssembleOrderItem(message);
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
