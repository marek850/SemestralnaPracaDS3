package agents.workstationagent;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Entities.AssemblyStation;
import OSPABA.*;
import simulation.*;



//meta! id="10"
public class WorkStationAgent extends OSPABA.Agent
{
	private Queue<MyMessage> waitingOrderItems;
	public Queue<MyMessage> getWaitingOrderItems() {
		return waitingOrderItems;
	}

	private List<AssemblyStation> assemblyStations;
	public List<AssemblyStation> getAssemblyStations() {
		return assemblyStations;
	}

	private List<AssemblyStation> freeAssemblyStations;
	

	public List<AssemblyStation> getFreeAssemblyStations() {
		return freeAssemblyStations;
	}

	public WorkStationAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		MySimulation mySimul = (MySimulation)mySim();
		waitingOrderItems = new LinkedList<MyMessage>();
		assemblyStations = new LinkedList<AssemblyStation>();
		freeAssemblyStations = new LinkedList<AssemblyStation>();
		for(int i = 0; i < mySimul.getWorkStationNumber(); i++)
		{
			assemblyStations.add(new AssemblyStation(i));
			freeAssemblyStations.add(assemblyStations.get(i));
		}
		init();
	}
	public AssemblyStation assignAssemblyStation(){
		return freeAssemblyStations.remove(0);
	}
	public void releaseAssemblyStation(AssemblyStation assemblyStation){
		freeAssemblyStations.add(assemblyStation);
	}
	public boolean isFree(){
		return freeAssemblyStations.size() > 0;
	}
	public void addWaitingOrderItem(MyMessage msg){
		waitingOrderItems.add(msg);
	}
	public MyMessage getWaitingOrderItem(){
		return waitingOrderItems.poll();
	}
	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		MySimulation mySimul = (MySimulation)mySim();
		
		waitingOrderItems.clear();
		assemblyStations.clear();
		freeAssemblyStations.clear();
		int sizeOfAssemblyStation = 45;
		int padding = 25;

		int totalWidth = 0;
		double startX = 0;
		double startY = 0;
		if (mySimul.animatorExists()) {
			totalWidth = (int)mySimul.getWorkStationNumber() * (sizeOfAssemblyStation + padding) - padding;
			startX = mySimul.getStorage().getPosition(mySimul.currentTime()).getX() + (mySimul.getStorage().getWidth() - totalWidth) / 2; 
			startY = mySimul.getStorage().getPosition(mySimul.currentTime()).getY() + mySimul.getStorage().getHeight() + 150;
		}
		
		for(int i = 0; i < mySimul.getWorkStationNumber(); i++)
		{
			AssemblyStation a = new AssemblyStation(i);
			assemblyStations.add(a);
			freeAssemblyStations.add(a);
			if (mySimul.animatorExists()) {
                mySimul.animator().register(a);
				int posX = (int)startX + i * (sizeOfAssemblyStation + padding);
				a.setPosition(new Point(posX, (int)startY));
				
        	}
		}
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