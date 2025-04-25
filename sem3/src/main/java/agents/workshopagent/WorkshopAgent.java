package agents.workshopagent;

import OSPABA.*;
import OSPStat.Stat;
import simulation.*;



//meta! id="87"
public class WorkshopAgent extends OSPABA.Agent
{
	private Stat orderProcessingTimeStat;
	public WorkshopAgent(int id, Simulation mySim, Agent parent)
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
		new WorkshopManager(Id.workshopManager, mySim(), this);
		addOwnMessage(Mc.requestCWaitingOrders);
		addOwnMessage(Mc.workStationAssignment);
		addOwnMessage(Mc.requestAWaitingOrders);
		addOwnMessage(Mc.assembleOrderItem);
		addOwnMessage(Mc.requestNumOfFreeEmpA);
		addOwnMessage(Mc.transferAEmployee);
		addOwnMessage(Mc.cFitHardwareOnItem);
		addOwnMessage(Mc.varnishOrderitem);
		addOwnMessage(Mc.cutOrderItem);
		addOwnMessage(Mc.requestNumOfFreeEmpC);
		addOwnMessage(Mc.transferCEmployee);
		addOwnMessage(Mc.processOrder);
		addOwnMessage(Mc.aFitHardwareOnItem);
		addOwnMessage(Mc.transferBEmployee);
		addOwnMessage(Mc.transferEmployee);
		addOwnMessage(Mc.stainOrderItem);
	}
	//meta! tag="end"
}