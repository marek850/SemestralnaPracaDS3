package agents.cemployeesagent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Entities.Employee;
import Entities.States.EmployeeType;
import Entities.States.Position;
import OSPABA.*;
import agents.cemployeesagent.continualassistants.*;
import simulation.*;



//meta! id="8"
public class CEmployeesAgent extends OSPABA.Agent
{
	private Queue<MyMessage> waitingOrdersVarnish;
	private Queue<MyMessage> waitingOrdersHardwareFit;
	private List<Employee> employees;
	private List<Employee> freeEmployees;
	
	public CEmployeesAgent(int id, Simulation mySim, Agent parent)
	{

		super(id, mySim, parent);
		MySimulation sim = (MySimulation) mySim;
		employees = new LinkedList<Employee>();
		freeEmployees = new LinkedList<Employee>();
		waitingOrdersVarnish = new LinkedList<MyMessage>();
		waitingOrdersHardwareFit = new LinkedList<MyMessage>();
		for (int i = 0; i < sim.getcEmpNumber(); i++) {
			employees.add(new Employee(i, EmployeeType.C));
			Employee employee = employees.get(i);
			employee.setPosition(Position.STORAGE);
			freeEmployees.add(employee);
		}
		init();
	}
	public Queue<MyMessage> getWaitingOrdersHardwareFit() {
		return waitingOrdersHardwareFit;
	}
	public Queue<MyMessage> getWaitingOrdersVarnish() {
		return waitingOrdersVarnish;
	}
	public List<Employee> getEmployees() {
		return employees;
	}
	public List<Employee> getFreeEmployees() {
		return freeEmployees;
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
	public void addWaitingOrderVarnish(MyMessage message) {
		waitingOrdersVarnish.add(message);
	}
	public MyMessage getWaitingOrderVarnish() {
		if (waitingOrdersVarnish.isEmpty()) {
			return null;
		}
		return waitingOrdersVarnish.remove();
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
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new CEmployeesManager(Id.cEmployeesManager, mySim(), this);
		new CFitHardwareProcess(Id.cFitHardwareProcess, mySim(), this);
		new VarnishProcess(Id.varnishProcess, mySim(), this);
		new StainProcess(Id.stainProcess, mySim(), this);
		addOwnMessage(Mc.transferCEmployee);
		addOwnMessage(Mc.requestCWaitingOrders);
		addOwnMessage(Mc.varnishOrderitem);
		addOwnMessage(Mc.cFitHardwareOnItem);
		addOwnMessage(Mc.stainOrderItem);
		addOwnMessage(Mc.requestNumOfFreeEmpC);
	}
	//meta! tag="end"
}