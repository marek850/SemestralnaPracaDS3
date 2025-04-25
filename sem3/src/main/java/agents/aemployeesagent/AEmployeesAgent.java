package agents.aemployeesagent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.sem3.Entities.States.EmployeeType;

import Entities.Employee;
import OSPABA.*;
import simulation.*;
import agents.aemployeesagent.continualassistants.*;



//meta! id="6"
public class AEmployeesAgent extends OSPABA.Agent
{
	private Queue<MyMessage> waitingOrders;
	public Queue<MyMessage> getWaitingOrders() {
		return waitingOrders;
	}
	private List<Employee> employees;
	private List<Employee> freeEmployees;
	public AEmployeesAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		MySimulation sim = (MySimulation) mySim;
		employees = new LinkedList<Employee>();
		freeEmployees = new LinkedList<Employee>();
		waitingOrders = new LinkedList<MyMessage>();
		for (int i = 0; i < sim.getaEmpNumber(); i++) {
			employees.add(new Employee(i, EmployeeType.A));
			freeEmployees.add(employees.get(i));
		}
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}
	public void addWaitingOrder(MyMessage message) {
		waitingOrders.add(message);
	}
	public MyMessage getWaitingOrder() {
		if (waitingOrders.isEmpty()) {
			return null;
		}
		return waitingOrders.remove();
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