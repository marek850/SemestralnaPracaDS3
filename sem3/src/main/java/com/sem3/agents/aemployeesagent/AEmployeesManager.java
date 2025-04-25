package com.sem3.agents.aemployeesagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="6"
public class AEmployeesManager extends OSPABA.Manager
{
	public AEmployeesManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="WorkshopAgent", id="47", type="Notice"
	public void processEmployeeARelease(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="57", type="Request"
	public void processFitHardwareOnOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="52", type="Request"
	public void processCutOrderItem(MessageForm message)
	{
	}

	//meta! sender="FitHardwareProcess", id="65", type="Finish"
	public void processFinishFitHardwareProcess(MessageForm message)
	{
	}

	//meta! sender="PrepareMaterialProcess", id="61", type="Finish"
	public void processFinishPrepareMaterialProcess(MessageForm message)
	{
	}

	//meta! sender="CutMaterialProcess", id="63", type="Finish"
	public void processFinishCutMaterialProcess(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="46", type="Request"
	public void processEmployeeAAssignment(MessageForm message)
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
		case Mc.cutOrderItem:
			processCutOrderItem(message);
		break;

		case Mc.employeeARelease:
			processEmployeeARelease(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.fitHardwareProcess:
				processFinishFitHardwareProcess(message);
			break;

			case Id.prepareMaterialProcess:
				processFinishPrepareMaterialProcess(message);
			break;

			case Id.cutMaterialProcess:
				processFinishCutMaterialProcess(message);
			break;
			}
		break;

		case Mc.employeeAAssignment:
			processEmployeeAAssignment(message);
		break;

		case Mc.fitHardwareOnOrderItem:
			processFitHardwareOnOrderItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public AEmployeesAgent myAgent()
	{
		return (AEmployeesAgent)super.myAgent();
	}

}
