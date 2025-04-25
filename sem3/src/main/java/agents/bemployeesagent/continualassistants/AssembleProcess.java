package agents.bemployeesagent.continualassistants;

import OSPABA.*;
import agents.bemployeesagent.*;
import simulation.*;
import OSPABA.Process;

//meta! id="164"
public class AssembleProcess extends OSPABA.Process
{
	public AssembleProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="BEmployeesAgent", id="165", type="Start"
	public void processStart(MessageForm message)
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
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
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