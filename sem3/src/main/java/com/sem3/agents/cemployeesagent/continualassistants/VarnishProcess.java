package com.sem3.agents.cemployeesagent.continualassistants;

import OSPABA.*;
import com.sem3.simulation.*;
import com.sem3.agents.cemployeesagent.*;
import OSPABA.Process;

//meta! id="70"
public class VarnishProcess extends OSPABA.Process
{
	public VarnishProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CEmployeesAgent", id="71", type="Start"
	public void processStart(MessageForm message)
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
