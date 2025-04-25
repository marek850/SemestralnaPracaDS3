package com.sem3.agents.cemployeesagent;

import OSPABA.*;
import com.sem3.simulation.*;

//meta! id="8"
public class CEmployeesManager extends OSPABA.Manager
{
	public CEmployeesManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="WorkshopAgent", id="56", type="Request"
	public void processFitHardwareOnOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="44", type="Request"
	public void processEmployeeCAssignment(MessageForm message)
	{
	}

	//meta! sender="FitHardwareProcess", id="75", type="Finish"
	public void processFinishFitHardwareProcess(MessageForm message)
	{
	}

	//meta! sender="StainProcess", id="73", type="Finish"
	public void processFinishStainProcess(MessageForm message)
	{
	}

	//meta! sender="VarnishProcess", id="71", type="Finish"
	public void processFinishVarnishProcess(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="49", type="Notice"
	public void processEmployeeCRelease(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="55", type="Request"
	public void processStainOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="54", type="Request"
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

		case Mc.stainOrderItem:
			processStainOrderItem(message);
		break;

		case Mc.fitHardwareOnOrderItem:
			processFitHardwareOnOrderItem(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.fitHardwareProcessC:
				processFinishFitHardwareProcess(message);
			break;

			case Id.stainProcess:
				processFinishStainProcess(message);
			break;

			case Id.varnishProcess:
				processFinishVarnishProcess(message);
			break;
			}
		break;

		case Mc.employeeCRelease:
			processEmployeeCRelease(message);
		break;

		case Mc.varnishOrderItem:
			processVarnishOrderItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public CEmployeesAgent myAgent()
	{
		return (CEmployeesAgent)super.myAgent();
	}

}
