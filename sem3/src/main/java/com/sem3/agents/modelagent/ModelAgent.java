package com.sem3.agents.modelagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="1"
public class ModelAgent extends OSPABA.Agent
{
	public ModelAgent(int id, Simulation mySim, Agent parent)
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
		new ModelManager(Id.modelManager, mySim(), this);
		addOwnMessage(Mc.processOrder);
		addOwnMessage(Mc.orderArrival);
	}
	//meta! tag="end"
}
