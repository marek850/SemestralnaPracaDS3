package com.sem3.agents.workstationagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="10"
public class WorkStationAgent extends OSPABA.Agent
{
	public WorkStationAgent(int id, Simulation mySim, Agent parent)
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
		new WorkStationManager(Id.workStationManager, mySim(), this);
		addOwnMessage(Mc.workStationAssignment);
		addOwnMessage(Mc.workStationRelease);
	}
	//meta! tag="end"
}
