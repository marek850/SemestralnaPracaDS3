package simulation;

import Entities.AssemblyStation;
import Entities.Employee;
import Entities.Order;
import Entities.OrderItem;
import OSPABA.*;

public class MyMessage extends OSPABA.MessageForm
{
	private Order order;
	public Order getOrder() {
		return order;
	}
	private OrderItem orderItem;
	public OrderItem getOrderItem() {
		return orderItem;
	}
	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
	private Employee employee;
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	private AssemblyStation assemblyStation;
	public AssemblyStation getAssemblyStation() {
		return assemblyStation;
	}
	public void setAssemblyStation(AssemblyStation assemblyStation) {
		this.assemblyStation = assemblyStation;
	}
	public void setOrder(Order order) {
		this.order = order;
	}

	private int aEmployeesNumber = 0;
	private int cEmployeesNumber = 0;
	private int aWaitingOrders = 0;
	public int getAWaitingOrders() {
		return aWaitingOrders;
	}
	public void setAWaitingOrders(int aWaitingOrders) {
		this.aWaitingOrders = aWaitingOrders;
	}
	private int cWaitingOrders = 0;
	public int getCWaitingOrders() {
		return cWaitingOrders;
	}
	public void setCWaitingOrders(int cWaitingOrders) {
		this.cWaitingOrders = cWaitingOrders;
	}
	public int getAEmployeesNumber() {
		return aEmployeesNumber;
	}
	public void setAEmployeesNumber(int aEmployeesNumber) {
		this.aEmployeesNumber = aEmployeesNumber;
	}
	public int getCEmployeesNumber() {
		return cEmployeesNumber;
	}
	public void setCEmployeesNumber(int cEmployeesNumber) {
		this.cEmployeesNumber = cEmployeesNumber;
	}
	public MyMessage(Simulation mySim)
	{
		super(mySim);
	}

	public MyMessage(MyMessage original)
	{
		super(original);
		// copy() is called in superclass
	}

	@Override
	public MessageForm createCopy()
	{
		return new MyMessage(this);
	}

	@Override
	protected void copy(MessageForm message)
	{
		super.copy(message);
		MyMessage original = (MyMessage)message;
		this.order = original.order;
		this.orderItem = original.orderItem;
		this.employee = original.employee;
		this.assemblyStation = original.assemblyStation;
	}
}