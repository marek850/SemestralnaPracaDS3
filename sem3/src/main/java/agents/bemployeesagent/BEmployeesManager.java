package agents.bemployeesagent;

import OSPABA.*;
import simulation.*;

//meta! id="7"
public class BEmployeesManager extends OSPABA.Manager
{
	public BEmployeesManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! userInfo="Removed from model"
	public void processEmployeeBAssignment(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processEmployeeBRelease(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="148", type="Request"
	public void processAssembleOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="159", type="Response"
	public void processTransferBEmployee(MessageForm message)
	{
	}

	//meta! sender="AssembleProcess", id="165", type="Finish"
	public void processFinish(MessageForm message)
	{
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
		case Mc.assembleOrderItem:
			processAssembleOrderItem(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		case Mc.transferBEmployee:
			processTransferBEmployee(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public BEmployeesAgent myAgent()
	{
		return (BEmployeesAgent)super.myAgent();
	}

}