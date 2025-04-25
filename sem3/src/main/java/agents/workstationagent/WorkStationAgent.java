package agents.workstationagent;

import java.util.Queue;

import OSPABA.*;
import OSPStat.Stat;
import simulation.*;



//meta! id="10"
public class WorkStationAgent extends OSPABA.Agent
{
	

	public WorkStationAgent(int id, Simulation mySim, Agent parent)
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
		new WorkStationManager(Id.workStationManager, mySim(), this);
		addOwnMessage(Mc.workStationAssignment);
		addOwnMessage(Mc.workStationRelease);
	}
	//meta! tag="end"
}