package agents.workshopagent;

import java.util.ArrayList;
import java.util.List;

import Entities.Employee;
import Entities.Order;
import OSPABA.*;
import OSPRNG.UniformContinuousRNG;
import OSPStat.Stat;
import OSPStat.WStat;
import agents.aemployeesagent.AEmployeesAgent;
import agents.bemployeesagent.BEmployeesAgent;
import agents.cemployeesagent.CEmployeesAgent;
import simulation.*;



//meta! id="87"
public class WorkshopAgent extends OSPABA.Agent
{
	private Stat orderProcessingTimeStat;
	private Stat orderProcessGlobal;
	private Stat groupAWorkload = new Stat();
	private Stat groupBWorkload = new Stat();
	private Stat groupCWorkload = new Stat();
	private List<Order> unstartedOrders = new ArrayList<>();
	public void addUnstartedOrder(Order order) {
		unstartedOrders.add(order);
		getWaitingOrders().addSample(unstartedOrders.size());
	}
	public void removeUnstartedOrder(Order order) {
		unstartedOrders.remove(order);
		getWaitingOrders().addSample(unstartedOrders.size());
	}
	public List<Order> getUnstartedOrders() {
		return unstartedOrders;
	}
	public Stat getGroupAWorkload() {
		return groupAWorkload;
	}
	public Stat getGroupBWorkload() {
		return groupBWorkload;
	}
	public Stat getGroupCWorkload() {
		return groupCWorkload;
	}
	public void afterReplicationWorkloadUpdate(){
		AEmployeesAgent aEmployeesAgent = (AEmployeesAgent) mySim().findAgent(Id.aEmployeesAgent);
		BEmployeesAgent bEmployeesAgent = (BEmployeesAgent) mySim().findAgent(Id.bEmployeesAgent);
		CEmployeesAgent cEmployeesAgent = (CEmployeesAgent) mySim().findAgent(Id.cEmployeesAgent);
		double sum = 0;
		double count = 0;
		getOrderProcessGlobal().addSample(getOrderProcessingTimeStat().mean());
		double mean = getWaitingOrders().mean();
		getGlobalWaitingOrders().addSample(mean);

		for (Employee employee : aEmployeesAgent.getEmployees()) {
			employee.getGlobalWorkloadStat().addSample(employee.getWorkload());
			sum += employee.getWorkload();
			count++;
		}
		groupAWorkload.addSample(sum/count);
		sum = 0;
		count = 0;
		
		
		for (Employee employee : bEmployeesAgent.getEmployees()) {
			employee.getGlobalWorkloadStat().addSample(employee.getWorkload());
			sum += employee.getWorkload();
			count++;
		}
		groupBWorkload.addSample(sum/count);
		sum = 0;
		count = 0;
		for (Employee employee : cEmployeesAgent.getEmployees()) {
			employee.getGlobalWorkloadStat().addSample(employee.getWorkload());
			sum += employee.getWorkload();
			count++;
		}
		groupCWorkload.addSample(sum/count);
		
	}
	private WStat waitingOrders;
	private Stat globalWaitingOrders;
	public WStat getWaitingOrders() {
		return waitingOrders;
	}
	public Stat getGlobalWaitingOrders() {
		return globalWaitingOrders;
	}
	public Stat getOrderProcessGlobal() {
		return orderProcessGlobal;
	}

	public Stat getOrderProcessingTimeStat() {
		return orderProcessingTimeStat;
	}
	private UniformContinuousRNG fitHardwareTime;

	public UniformContinuousRNG getFitHardwareTime() {
		return fitHardwareTime;
	}

	public WorkshopAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		MySimulation sim = (MySimulation) mySim;
		fitHardwareTime  = new UniformContinuousRNG(900d, 1500d, sim.seedGenerator);
		orderProcessingTimeStat = new Stat();
		orderProcessGlobal = new Stat();
		waitingOrders = new WStat(mySim);
		globalWaitingOrders = new Stat();
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		orderProcessingTimeStat.clear();
		waitingOrders.clear();
		unstartedOrders.clear();
		
		
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
	}
	//meta! tag="end"
}