package agents.bemployeesagent;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Entities.Employee;
import Entities.States.EmployeeType;
import Entities.States.Position;
import OSPABA.*;
import agents.bemployeesagent.continualassistants.*;
import simulation.*;



//meta! id="7"
public class BEmployeesAgent extends OSPABA.Agent
{	
	private Queue<MyMessage> waitingOrdersAssemble;
	private List<Employee> employees;
	private List<Employee> freeEmployees;
	public BEmployeesAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		MySimulation sim = (MySimulation) mySim;
		employees = new LinkedList<Employee>();
		freeEmployees = new LinkedList<Employee>();
		waitingOrdersAssemble = new LinkedList<MyMessage>();
		for (int i = 0; i < sim.getbEmpNumber(); i++) {
			employees.add(new Employee(i, EmployeeType.B, mySim));
			Employee employee = employees.get(i);
			employee.setPosition(Position.STORAGE);
			freeEmployees.add(employee);
		}
		init();
	}
	public Queue<MyMessage> getWaitingOrdersAssemble() {
		return waitingOrdersAssemble;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public List<Employee> getFreeEmployees() {
		return freeEmployees;
	} 
	public void addWaitingOrderAssemble(MyMessage message) {
		waitingOrdersAssemble.add(message);
	}
	public MyMessage getWaitingOrderAssemble() {
		if (waitingOrdersAssemble.isEmpty()) {
			return null;
		}
		return waitingOrdersAssemble.remove();
	}
	public void releaseEmployee(Employee employee) {
		freeEmployees.add(employee);
	}
	public Employee assignEmployee() {
		if (freeEmployees.isEmpty()) {
			return null;
		}
		return freeEmployees.remove(0);
	}
	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		waitingOrdersAssemble.clear();
		freeEmployees.clear();
		MySimulation sim = (MySimulation) mySim();
		int employeeSize = 36;
		int padding = 6;
		int rowPadding = 10;
		double startX = 0;
		double startY = 0;
		if (sim.animatorExists()) {
			 startX =  sim.getStorage().getPosition(sim.currentTime()).getX() + padding;
			 startY = sim.getStorage().getPosition(sim.currentTime()).getY() + rowPadding;
		}
		
		int index = 0;
		for(Employee employee : employees) {
			employee.reset();
			freeEmployees.add(employee);
			if (mySim().animatorExists()) {
                mySim().animator().register(employee);
				int x = (int)(startX + index * (employeeSize + padding));
				int y = (int)(startY + 1 * (employeeSize + rowPadding));
                employee.setPosition(new Point(x, y));
				employee.setDefaultStoragePosX(x);
				employee.setDefaultStoragePosY(y);
				index++;
        	}
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new BEmployeesManager(Id.bEmployeesManager, mySim(), this);
		new AssembleProcess(Id.assembleProcess, mySim(), this);
		addOwnMessage(Mc.assembleOrderItem);
		addOwnMessage(Mc.transferBEmployee);
	}
	//meta! tag="end"
}