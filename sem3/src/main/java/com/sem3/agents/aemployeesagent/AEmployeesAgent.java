package com.sem3.agents.aemployeesagent;

import OSPABA.*;
import com.sem3.simulation.*;
import com.sem3.agents.aemployeesagent.continualassistants.*;

//meta! id="6"
public class AEmployeesAgent extends OSPABA.Agent
{
	public AEmployeesAgent(int id, Simulation mySim, Agent parent)
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
		new AEmployeesManager(Id.aEmployeesManager, mySim(), this);
		new FitHardwareProcess(Id.fitHardwareProcess, mySim(), this);
		new PrepareMaterialProcess(Id.prepareMaterialProcess, mySim(), this);
		new CutMaterialProcess(Id.cutMaterialProcess, mySim(), this);
		addOwnMessage(Mc.employeeARelease);
		addOwnMessage(Mc.fitHardwareOnOrderItem);
		addOwnMessage(Mc.cutOrderItem);
		addOwnMessage(Mc.employeeAAssignment);
	}
	//meta! tag="end"
}
