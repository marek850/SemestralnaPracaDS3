package com.sem3.agents.employeetransferagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="77"
public class EmployeeTransferManager extends OSPABA.Manager
{
	public EmployeeTransferManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="WorkshopAgent", id="78", type="Request"
	public void processTransferEmployee(MessageForm message)
	{
	}

	//meta! sender="WorkStationTransferProcess", id="81", type="Finish"
	public void processFinishWorkStationTransferProcess(MessageForm message)
	{
	}

	//meta! sender="WareHouseTransferProcess", id="83", type="Finish"
	public void processFinishWareHouseTransferProcess(MessageForm message)
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
			switch (message.sender().id())
			{
			case Id.workStationTransferProcess:
				processFinishWorkStationTransferProcess(message);
			break;

			case Id.wareHouseTransferProcess:
				processFinishWareHouseTransferProcess(message);
			break;
			}
		break;

		case Mc.transferEmployee:
			processTransferEmployee(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public EmployeeTransferAgent myAgent()
	{
		return (EmployeeTransferAgent)super.myAgent();
	}

}
