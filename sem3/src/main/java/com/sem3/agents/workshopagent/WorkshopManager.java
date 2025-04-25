package com.sem3.agents.workshopagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="5"
public class WorkshopManager extends OSPABA.Manager
{
	public WorkshopManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="ModelAgent", id="50", type="Request"
	public void processProcessOrder(MessageForm message)
	{
	}

	//meta! sender="BEmployeesAgent", id="45", type="Response"
	public void processEmployeeBAssignment(MessageForm message)
	{
	}

	//meta! sender="CEmployeesAgent", id="56", type="Response"
	public void processFitHardwareOnOrderItemCEmployeesAgent(MessageForm message)
	{
	}

	//meta! sender="AEmployeesAgent", id="57", type="Response"
	public void processFitHardwareOnOrderItemAEmployeesAgent(MessageForm message)
	{
	}

	//meta! sender="WorkStationAgent", id="40", type="Response"
	public void processWorkStationAssignment(MessageForm message)
	{
	}

	//meta! sender="BEmployeesAgent", id="53", type="Response"
	public void processAssembleOrderItem(MessageForm message)
	{
	}

	//meta! sender="CEmployeesAgent", id="44", type="Response"
	public void processEmployeeCAssignment(MessageForm message)
	{
	}

	//meta! sender="AEmployeesAgent", id="52", type="Response"
	public void processCutOrderItem(MessageForm message)
	{
	}

	//meta! sender="EmployeeTransferAgent", id="78", type="Response"
	public void processTransferEmployee(MessageForm message)
	{
	}

	//meta! sender="AEmployeesAgent", id="46", type="Response"
	public void processEmployeeAAssignment(MessageForm message)
	{
	}

	//meta! sender="CEmployeesAgent", id="55", type="Response"
	public void processStainOrderItem(MessageForm message)
	{
	}

	//meta! sender="CEmployeesAgent", id="54", type="Response"
	public void processVarnishOrderItem(MessageForm message)
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
		case Mc.employeeCAssignment:
			processEmployeeCAssignment(message);
		break;

		case Mc.processOrder:
			processProcessOrder(message);
		break;

		case Mc.cutOrderItem:
			processCutOrderItem(message);
		break;

		case Mc.employeeBAssignment:
			processEmployeeBAssignment(message);
		break;

		case Mc.employeeAAssignment:
			processEmployeeAAssignment(message);
		break;

		case Mc.varnishOrderItem:
			processVarnishOrderItem(message);
		break;

		case Mc.stainOrderItem:
			processStainOrderItem(message);
		break;

		case Mc.fitHardwareOnOrderItem:
			switch (message.sender().id())
			{
			case Id.cEmployeesAgent:
				processFitHardwareOnOrderItemCEmployeesAgent(message);
			break;

			case Id.aEmployeesAgent:
				processFitHardwareOnOrderItemAEmployeesAgent(message);
			break;
			}
		break;

		case Mc.transferEmployee:
			processTransferEmployee(message);
		break;

		case Mc.workStationAssignment:
			processWorkStationAssignment(message);
		break;

		case Mc.assembleOrderItem:
			processAssembleOrderItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public WorkshopAgent myAgent()
	{
		return (WorkshopAgent)super.myAgent();
	}

}
