package agents.aemployeesagent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Entities.Employee;
import Entities.States.EmployeeType;
import Entities.States.Position;
import OSPABA.*;
import simulation.*;
import agents.aemployeesagent.continualassistants.*;



//meta! id="6"
public class AEmployeesAgent extends OSPABA.Agent
{
	private Queue<MyMessage> waitingOrdersCutting;
	private Queue<MyMessage> waitingOrdersHardwareFit;
	public Queue<MyMessage> getWaitingOrdersHardwareFit() {
		return waitingOrdersHardwareFit;
	}
	public Queue<MyMessage> getWaitingOrdersCutting() {
		return waitingOrdersCutting;
	}
	private List<Employee> employees;
	public List<Employee> getEmployees() {
		return employees;
	}
	private List<Employee> freeEmployees;
	public List<Employee> getFreeEmployees() {
		return freeEmployees;
	}
	public AEmployeesAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		MySimulation sim = (MySimulation) mySim;
		employees = new LinkedList<Employee>();
		freeEmployees = new LinkedList<Employee>();
		waitingOrdersCutting = new LinkedList<MyMessage>();
		waitingOrdersHardwareFit = new LinkedList<MyMessage>();
		for (int i = 0; i < sim.getaEmpNumber(); i++) {
			employees.add(new Employee(i, EmployeeType.A));
			Employee employee = employees.get(i);
			employee.setPosition(Position.STORAGE);
			freeEmployees.add(employee);
		}
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}
	public void addWaitingOrderFitting(MyMessage message) {
		waitingOrdersHardwareFit.add(message);
	}
	public MyMessage getWaitingOrderFitting() {
		if (waitingOrdersHardwareFit.isEmpty()) {
			return null;
		}
		return waitingOrdersHardwareFit.remove();
	}
	public void addWaitingOrderCutting(MyMessage message) {
		waitingOrdersCutting.add(message);
	}
	public MyMessage getWaitingOrderCutting() {
		if (waitingOrdersCutting.isEmpty()) {
			return null;
		}
		return waitingOrdersCutting.remove();
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
		new MaterialPrepareProcess(Id.materialPrepareProcess, mySim(), this);
		addOwnMessage(Mc.requestAWaitingOrders);
		addOwnMessage(Mc.aFitHardwareOnItem);
		addOwnMessage(Mc.requestNumOfFreeEmpA);
		addOwnMessage(Mc.transferAEmployee);
		addOwnMessage(Mc.cutOrderItem);
	}
	//meta! tag="end"
}