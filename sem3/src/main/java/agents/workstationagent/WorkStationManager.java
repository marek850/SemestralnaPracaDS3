package agents.workstationagent;

import Entities.Order;
import Entities.OrderItem;
import OSPABA.*;
import simulation.*;

//meta! id="10"
public class WorkStationManager extends OSPABA.Manager
{
	public WorkStationManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="WorkshopAgent", id="40", type="Request"
	public void processWorkStationAssignment(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="42", type="Notice"
	public void processWorkStationRelease(MessageForm message)
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
		case Mc.workStationAssignment:
			processWorkStationAssignment(message);
		break;

		case Mc.workStationRelease:
			processWorkStationRelease(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public WorkStationAgent myAgent()
	{
		return (WorkStationAgent)super.myAgent();
	}

}