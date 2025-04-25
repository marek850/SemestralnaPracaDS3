package com.sem3.agents.bemployeesagent;

import OSPABA.*;
import com.sem3.agents.bemployeesagent.continualassistants.*;
import com.sem3.simulation.*;

//meta! id="7"
public class BEmployeesAgent extends OSPABA.Agent
{
	public BEmployeesAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new BEmployeesManager(Id.bEmployeesManager, mySim(), this);
		new AssembleProcess(Id.assembleProcess, mySim(), this);
		addOwnMessage(Mc.employeeBAssignment);
		addOwnMessage(Mc.employeeBRelease);
		addOwnMessage(Mc.assembleOrderItem);
	}
	//meta! tag="end"
}
