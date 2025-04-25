package com.sem3.agents.cemployeesagent;

import OSPABA.*;
import com.sem3.agents.cemployeesagent.continualassistants.*;
import com.sem3.simulation.*;

//meta! id="8"
public class CEmployeesAgent extends OSPABA.Agent
{
	public CEmployeesAgent(int id, Simulation mySim, Agent parent)
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
		new CEmployeesManager(Id.cEmployeesManager, mySim(), this);
		new StainProcess(Id.stainProcess, mySim(), this);
		new VarnishProcess(Id.varnishProcess, mySim(), this);
		new FitHardwareProcess(Id.fitHardwareProcessC, mySim(), this);
		addOwnMessage(Mc.fitHardwareOnOrderItem);
		addOwnMessage(Mc.employeeCAssignment);
		addOwnMessage(Mc.employeeCRelease);
		addOwnMessage(Mc.stainOrderItem);
		addOwnMessage(Mc.varnishOrderItem);
	}
	//meta! tag="end"
}
