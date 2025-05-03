package agents.surroundingagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Entities.Order;
import OSPABA.*;
import OSPRNG.UniformDiscreteRNG;
import simulation.*;
import agents.surroundingagent.continualassistants.*;



//meta! id="3"
public class SurroundingAgent extends OSPABA.Agent
{
	
	private UniformDiscreteRNG itemNumberGen = new UniformDiscreteRNG(1, 5);
	private UniformDiscreteRNG itemTypeGen = new UniformDiscreteRNG(0, 100);
	private UniformDiscreteRNG stainProbGen = new UniformDiscreteRNG(0, 100);
	public UniformDiscreteRNG getStainProbGen() {
		return stainProbGen;
	}
	private Random orderIDGen;
	private Random itemIDGen;

	public UniformDiscreteRNG getItemNumberGen() {
		return itemNumberGen;
	}

	public UniformDiscreteRNG getItemTypeGen() {
		return itemTypeGen;
	}

	public Random getOrderIDGen() {
		return orderIDGen;
	}

	public Random getItemIDGen() {
		return itemIDGen;
	}
	private List<Order> activOrders = new ArrayList<Order>();
	public List<Order> getActivOrders() {
		return activOrders;
	}

	public void removeOrder(Order order) {
		activOrders.remove(order);
	}
	public SurroundingAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		MySimulation sim = (MySimulation) mySim;
		itemNumberGen = new UniformDiscreteRNG(1, 5/* , sim.seedGenerator */);
		itemTypeGen = new UniformDiscreteRNG(0, 100/* ,sim.seedGenerator */);
		stainProbGen = new UniformDiscreteRNG(0, 100/* ,sim.seedGenerator */);
		orderIDGen = new Random(/* sim.seedGenerator.nextInt(10000000) */);
		itemIDGen = new Random(/* sim.seedGenerator.nextInt(10000000) */);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		addOwnMessage(Mc.orderArrival);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new SurroundingManager(Id.surroundingManager, mySim(), this);
		new ArrivalPlanner(Id.arrivalPlanner, mySim(), this);
		addOwnMessage(Mc.orderCompletion);
		addOwnMessage(Mc.initialisation);
	}
	//meta! tag="end"
}