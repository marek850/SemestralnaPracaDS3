package agents.workshopagent;

import OSPABA.*;
import OSPRNG.UniformContinuousRNG;
import OSPStat.Stat;
import OSPStat.WStat;
import simulation.*;



//meta! id="87"
public class WorkshopAgent extends OSPABA.Agent
{
	private Stat orderProcessingTimeStat;
	private Stat orderProcessGlobal;
	private WStat waitingOrders;
	private WStat globalWaitingOrders;
	public WStat getWaitingOrders() {
		return waitingOrders;
	}
	public WStat getGlobalWaitingOrders() {
		return globalWaitingOrders;
	}
	public Stat getOrderProcessGlobal() {
		return orderProcessGlobal;
	}

	public Stat getOrderProcessingTimeStat() {
		return orderProcessingTimeStat;
	}
	private UniformContinuousRNG fitHardwareTime = new UniformContinuousRNG(900d, 1500d);

	public UniformContinuousRNG getFitHardwareTime() {
		return fitHardwareTime;
	}

	public WorkshopAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		orderProcessingTimeStat = new Stat();
		orderProcessGlobal = new Stat();
		waitingOrders = new WStat(mySim);
		globalWaitingOrders = new WStat(mySim);
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