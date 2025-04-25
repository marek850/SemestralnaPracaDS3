package com.sem3.agents.surroundingagent;

import OSPABA.*;
import com.sem3.simulation.*;
import com.sem3.agents.surroundingagent.continualassistants.*;

//meta! id="3"
public class SurroundingAgent extends OSPABA.Agent
{
	public SurroundingAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		addOwnMessage(Mc.orderArrival);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new SurroundingManager(Id.surroundingManager, mySim(), this);
		new ArrivalPlanner(Id.arrivalPlanner, mySim(), this);
		addOwnMessage(Mc.orderCompletion);
		addOwnMessage(Mc.initialisation);
	}
	//meta! tag="end"
}
