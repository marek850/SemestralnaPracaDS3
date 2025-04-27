package agents.workshopagent;

import Entities.Employee;
import Entities.Order;
import Entities.OrderItem;
import Entities.States.FurnitureType;
import Entities.States.OrderItemState;
import OSPABA.*;
import simulation.*;

//meta! id="87"
public class WorkshopManager extends OSPABA.Manager
{
	public WorkshopManager(int id, Simulation mySim, Agent myAgent)
	{
		super(id, mySim, myAgent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication

		if (petriNet() != null)
		{
			petriNet().clear();
		}
	}

	//meta! userInfo="Removed from model"
	public void processFitHardwareOnOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkStationAgent", id="40", type="Response"
	public void processWorkStationAssignment(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.cutOrderItem);
		msg.setAddressee(Id.aEmployeesAgent);
		request(msg);
	}

	//meta! sender="EmployeeTransferAgent", id="78", type="Response"
	public void processTransferEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		Employee employee = msg.getEmployee();
		switch (employee.getType()) {
			case A:
				msg.setCode(Mc.transferAEmployee);
				response(msg);
				break;
			case B:
				msg.setCode(Mc.transferBEmployee);
				response(msg);
				break;
			case C:
				msg.setCode(Mc.transferCEmployee);
				response(msg);
				break;
			default:
				break;
		}
	}


	//meta! userInfo="Removed from model"
	public void processFinishAssembleProcess(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processFinishCutProcess(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processFinishFItHardwareProcess(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processFinishStainProcess(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processFinishVarnishProcess(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processAssignEmployee(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processVarnishOrderItem(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="ModelAgent", id="123", type="Request"
	public void processProcessOrder(MessageForm message)
	{
		MyMessage newMessage = (MyMessage)message.createCopy();
		Order order = (Order)newMessage.getOrder();
		for (OrderItem item : order.getItems())
		{
			item.setState(OrderItemState.PENDING);
			MyMessage newItemMessage = (MyMessage)newMessage.createCopy();
			newItemMessage.setOrderItem(item);
			newItemMessage.setAddressee(Id.workStationAgent);
			newItemMessage.setCode(Mc.workStationAssignment);
			request(newItemMessage);
		}
	}

	//meta! sender="CEmployeesAgent", id="146", type="Response"
	public void processRequestCWaitingOrders(MessageForm message)
	{
	}

	//meta! sender="AEmployeesAgent", id="143", type="Response"
	public void processRequestAWaitingOrders(MessageForm message)
	{
	}

	//meta! sender="BEmployeesAgent", id="148", type="Response"
	public void processAssembleOrderItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		OrderItem orderItem = msg.getOrderItem();
		if (orderItem.getItemType() == FurnitureType.WARDROBE) {
			msg.setCode(Mc.requestNumOfFreeEmpA);
			msg.setAddressee(Id.aEmployeesAgent);
			request(msg);
		} else{
			orderItem.setState(OrderItemState.FINISHED);
			Order order = orderItem.getOrder();
			if (order.isOrderCompleted()) {
				msg.setCode(Mc.processOrder);
				response(msg);
			}
		}
	}

	//meta! sender="AEmployeesAgent", id="158", type="Request"
	public void processTransferAEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.transferEmployee);
		msg.setAddressee(Id.employeeTransferAgent);
		request(msg);
	}

	//meta! sender="CEmployeesAgent", id="156", type="Response"
	public void processCFitHardwareOnItem(MessageForm message)
	{
	}

	//meta! sender="CEmployeesAgent", id="149", type="Response"
	public void processVarnishOrderitem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.assembleOrderItem);
		msg.setAddressee(Id.cEmployeesAgent);
		request(msg);
	}

	//meta! sender="AEmployeesAgent", id="147", type="Response"
	public void processCutOrderItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.varnishOrderitem);
		msg.setAddressee(Id.cEmployeesAgent);
		request(msg);
	}

	//meta! sender="CEmployeesAgent", id="160", type="Request"
	public void processTransferCEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.transferEmployee);
		msg.setAddressee(Id.employeeTransferAgent);
		request(msg);
	}

	//meta! sender="AEmployeesAgent", id="157", type="Response"
	public void processAFitHardwareOnItem(MessageForm message)
	{
	}

	//meta! sender="BEmployeesAgent", id="159", type="Request"
	public void processTransferBEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.transferEmployee);
		msg.setAddressee(Id.employeeTransferAgent);
		request(msg);
	}

	//meta! sender="CEmployeesAgent", id="154", type="Response"
	public void processStainOrderItem(MessageForm message)
	{
	}

	//meta! sender="AEmployeesAgent", id="177", type="Response"
	public void processRequestNumOfFreeEmpA(MessageForm message)
	{
	}

	//meta! sender="CEmployeesAgent", id="178", type="Response"
	public void processRequestNumOfFreeEmpC(MessageForm message)
	{
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
	}

	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.requestAWaitingOrders:
			processRequestAWaitingOrders(message);
		break;

		case Mc.transferAEmployee:
			processTransferAEmployee(message);
		break;

		case Mc.transferEmployee:
			processTransferEmployee(message);
		break;

		case Mc.requestNumOfFreeEmpC:
			processRequestNumOfFreeEmpC(message);
		break;

		case Mc.processOrder:
			processProcessOrder(message);
		break;

		case Mc.cFitHardwareOnItem:
			processCFitHardwareOnItem(message);
		break;

		case Mc.aFitHardwareOnItem:
			processAFitHardwareOnItem(message);
		break;

		case Mc.assembleOrderItem:
			processAssembleOrderItem(message);
		break;

		case Mc.stainOrderItem:
			processStainOrderItem(message);
		break;

		case Mc.workStationAssignment:
			processWorkStationAssignment(message);
		break;

		case Mc.transferCEmployee:
			processTransferCEmployee(message);
		break;

		case Mc.requestNumOfFreeEmpA:
			processRequestNumOfFreeEmpA(message);
		break;

		case Mc.requestCWaitingOrders:
			processRequestCWaitingOrders(message);
		break;

		case Mc.transferBEmployee:
			processTransferBEmployee(message);
		break;

		case Mc.varnishOrderitem:
			processVarnishOrderitem(message);
		break;

		case Mc.cutOrderItem:
			processCutOrderItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public WorkshopAgent myAgent()
	{
		return (WorkshopAgent)super.myAgent();
	}

}