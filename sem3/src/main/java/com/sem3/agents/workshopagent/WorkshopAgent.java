package com.sem3.agents.workshopagent;

import OSPABA.*;
import com.sem3.simulation.*;
//meta! id="5"
public class WorkshopAgent extends OSPABA.Agent
{
	public WorkshopAgent(int id, Simulation mySim, Agent parent)
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
		new WorkshopManager(Id.workshopManager, mySim(), this);
		addOwnMessage(Mc.processOrder);
		addOwnMessage(Mc.employeeBAssignment);
		addOwnMessage(Mc.fitHardwareOnOrderItem);
		addOwnMessage(Mc.workStationAssignment);
		addOwnMessage(Mc.assembleOrderItem);
		addOwnMessage(Mc.employeeCAssignment);
		addOwnMessage(Mc.cutOrderItem);
		addOwnMessage(Mc.transferEmployee);
		addOwnMessage(Mc.employeeAAssignment);
		addOwnMessage(Mc.stainOrderItem);
		addOwnMessage(Mc.varnishOrderItem);
	}
	//meta! tag="end"
}
