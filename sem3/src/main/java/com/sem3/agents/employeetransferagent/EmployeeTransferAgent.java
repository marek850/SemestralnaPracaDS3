package com.sem3.agents.employeetransferagent;

import OSPABA.*;
import com.sem3.simulation.*;
import com.sem3.agents.employeetransferagent.continualassistants.*;

//meta! id="77"
public class EmployeeTransferAgent extends OSPABA.Agent
{
	public EmployeeTransferAgent(int id, Simulation mySim, Agent parent)
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
		new EmployeeTransferManager(Id.employeeTransferManager, mySim(), this);
		new WareHouseTransferProcess(Id.wareHouseTransferProcess, mySim(), this);
		new WorkStationTransferProcess(Id.workStationTransferProcess, mySim(), this);
		addOwnMessage(Mc.transferEmployee);
	}
	//meta! tag="end"
}
