package agents.aemployeesagent;

import OSPABA.*;
import simulation.*;
import agents.aemployeesagent.continualassistants.*;



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
		new AFitHardwareProcess(Id.aFitHardwareProcess, mySim(), this);
		new CutProcess(Id.cutProcess, mySim(), this);
		addOwnMessage(Mc.requestAWaitingOrders);
		addOwnMessage(Mc.aFitHardwareOnItem);
		addOwnMessage(Mc.requestNumOfFreeEmpA);
		addOwnMessage(Mc.transferAEmployee);
		addOwnMessage(Mc.cutOrderItem);
	}
	//meta! tag="end"
}