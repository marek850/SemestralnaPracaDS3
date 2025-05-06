package agents.cemployeesagent;

import java.awt.geom.Point2D;

import Entities.AssemblyStation;
import Entities.Employee;
import Entities.States.EmployeeState;
import Entities.States.FurnitureType;
import Entities.States.OrderItemState;
import Entities.States.Position;
import Entities.States.Process;
import OSPABA.*;
import OSPAnimator.AnimImageItem;
import UserInterface.AnimatorConfig;
import simulation.*;

//meta! id="8"
public class CEmployeesManager extends OSPABA.Manager
{
	public CEmployeesManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="160", type="Response"
	public void processTransferCEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		if (msg.getOrderItem().getState() == OrderItemState.CUT || msg.getOrderItem().getState() == OrderItemState.WAITING_FOR_VARNISH) {
			msg.setCode(Mc.varnishOrderitem);
			msg.setAddressee(myAgent().findAssistant(Id.varnishProcess));
			startContinualAssistant(msg);
		} else {
			msg.setCode(Mc.cFitHardwareOnItem);
			msg.setAddressee(myAgent().findAssistant(Id.cFitHardwareProcess));
			startContinualAssistant(msg);
		}
	}

	//meta! sender="WorkshopAgent", id="146", type="Request"
	public void processRequestCWaitingOrders(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setAWaitingOrders(myAgent().getWaitingOrdersHardwareFit().size());
		response(msg);
	}

	//meta! sender="WorkshopAgent", id="149", type="Request"
	public void processVarnishOrderitem(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_VARNISH);
			myAgent().addWaitingOrderVarnish(msg);
		} else{
			Employee employee = myAgent().assignEmployee();
			msg.setEmployee(employee);
			employee.getWorkloadStat().addSample(1d);
			if (employee.getCurrentPosition() == Position.ASSEMBLY_STATION &&
				employee.getStation() == msg.getAssemblyStation()) {
					if (mySim().animatorExists()) {
						Point2D target = new Point2D.Double(msg.getAssemblyStation().getPosition(mySim().currentTime()).getX() - 30,msg.getAssemblyStation().getPosition(mySim().currentTime()).getY());
						Point2D[] path = new Point2D[] {
							new Point2D.Double(employee.getPosition(mySim().currentTime()).getX(), employee.getPosition(mySim().currentTime()).getY()),       // výstup
							new Point2D.Double(target.getX(), employee.getPosition(mySim().currentTime()).getY()),      // horizontálny presun
							target                                                     // zostup
						};
						employee.startAnim(mySim().currentTime(), 3, path);
					}
					msg.setAddressee(myAgent().findAssistant(Id.varnishProcess));
					startContinualAssistant(msg);
			} else{
				msg.setCode(Mc.transferCEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
		}
	}

	//meta! sender="WorkshopAgent", id="156", type="Request"
	public void processCFitHardwareOnItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		//ak nie je volny zamestnanec tak dam spravu do cakania
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_FITTING);
			myAgent().addWaitingOrderFitting(msg);
		} else{
			//ak je volny zamestnanec tak skontrolujem ci ho treba presunut
			Employee employee = myAgent().assignEmployee();
			msg.setEmployee(employee);
			employee.getWorkloadStat().addSample(1d);
			if(employee.getCurrentPosition() == Position.ASSEMBLY_STATION && employee.getStation() == msg.getAssemblyStation()) {
				//ak je uz na montaznom mieste tak zacne s montazou
				msg.setCode(Mc.cFitHardwareOnItem);
				if (mySim().animatorExists()) {
					Point2D target = new Point2D.Double(msg.getAssemblyStation().getPosition(mySim().currentTime()).getX() - 30,msg.getAssemblyStation().getPosition(mySim().currentTime()).getY());
					Point2D[] path = new Point2D[] {
						new Point2D.Double(employee.getPosition(mySim().currentTime()).getX(), employee.getPosition(mySim().currentTime()).getY()),       // výstup
						new Point2D.Double(target.getX(), employee.getPosition(mySim().currentTime()).getY()),      // horizontálny presun
						target                                                     // zostup
					};
					employee.startAnim(mySim().currentTime(), 3, path);
				}
				msg.setAddressee(myAgent().findAssistant(Id.cFitHardwareProcess));
				startContinualAssistant(msg);
			}else {
				//ak noie je v sklade tak ho tam presuniem
				msg.setCode(Mc.transferCEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
			
		}
	}

	//meta! sender="VarnishProcess", id="170", type="Finish"
	public void processFinishVarnishProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getAssemblyStation().setCurrentProcess(Process.NONE);
		if(msg.getOrderItem().isStain()){
			msg.setAddressee(myAgent().findAssistant(Id.stainProcess));
			startContinualAssistant(msg);
		} else{
			Employee finishedEmployee = msg.getEmployee();
			handleFinishedEmployee(finishedEmployee);
			
			msg.setEmployee(null);
			msg.setCode(Mc.varnishOrderitem);
			response(msg);
		}
	}

	//meta! sender="StainProcess", id="168", type="Finish"
	public void processFinishStainProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getAssemblyStation().setCurrentProcess(Process.NONE);
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		msg.setEmployee(null);
		msg.setCode(Mc.varnishOrderitem);
		response(msg);
	}

	public void handleFinishedEmployee(Employee finishedEmployee) {
		if (!myAgent().getWaitingOrdersHardwareFit().isEmpty()) {
			//ak caka objednavka na montaz kovani je uprednostnena a zamestnanec sa musi presunut na ine montazne miesto
			MyMessage waitingOrder = myAgent().getWaitingOrderFitting();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferCEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else if(!myAgent().getWaitingOrdersVarnish().isEmpty()) {
			//ak caka objednavka na morenie tak presunieme agenta do skladu
			MyMessage waitingOrder = myAgent().getWaitingOrderVarnish();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferCEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else {
			//ak necaka ziadna objednavka
			finishedEmployee.setState(EmployeeState.IDLE);
			finishedEmployee.getWorkloadStat().addSample(0d);
			if(mySim().animatorExists()){
				AssemblyStation station = finishedEmployee.getStation();
				Point2D startQueue = new Point2D.Double(station.getPosition(mySim().currentTime()).getX(), station.getPosition(mySim().currentTime()).getY() + 40);
				station.setStartPositionOfQueue(startQueue);
				moveEmployeeToFinishedQueue(finishedEmployee, station, mySim().currentTime());
			}
			
			myAgent().releaseEmployee(finishedEmployee);
		}

	}
	public void moveEmployeeToFinishedQueue(AnimImageItem employee, 
                                               AssemblyStation station,
                                               double simTime) {
		Point2D start = employee.getPosition(simTime);
		Point2D target = station.getNextFinishedPosition();

		// Cesta: vystúpi hore, presunie sa horizontálne, potom zostúpi na pozíciu
		Point2D[] path = new Point2D[] {
			new Point2D.Double(start.getX(), start.getY()),
			new Point2D.Double(start.getX(), target.getY()),
			target
		};

		employee.startAnim(simTime, 3, path);
		employee.setZIndex(station.getFinishedCount());
		station.incrementFinishedCount();
	}
	//meta! sender="CFitHardwareProcess", id="172", type="Finish"
	public void processFinishCFitHardwareProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getAssemblyStation().setCurrentProcess(Process.NONE);
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		msg.getAssemblyStation().setImage(AnimatorConfig.ASSEMBLY_STATION);
		msg.setEmployee(null);
		msg.setCode(Mc.cFitHardwareOnItem);
		response(msg);

	}

	//meta! userInfo="Removed from model"
	public void processStainOrderItem(MessageForm message)
	{
		
	}

	//meta! sender="WorkshopAgent", id="178", type="Request"
	public void processRequestNumOfFreeEmpC(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCEmployeesNumber(myAgent().getFreeEmployees().size());
		response(msg);
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
		case Mc.transferCEmployee:
			processTransferCEmployee(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.varnishProcess:
				processFinishVarnishProcess(message);
			break;

			case Id.stainProcess:
				processFinishStainProcess(message);
			break;

			case Id.cFitHardwareProcess:
				processFinishCFitHardwareProcess(message);
			break;
			}
		break;

		case Mc.requestCWaitingOrders:
			processRequestCWaitingOrders(message);
		break;

		case Mc.requestNumOfFreeEmpC:
			processRequestNumOfFreeEmpC(message);
		break;

		case Mc.varnishOrderitem:
			processVarnishOrderitem(message);
		break;

		case Mc.cFitHardwareOnItem:
			processCFitHardwareOnItem(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public CEmployeesAgent myAgent()
	{
		return (CEmployeesAgent)super.myAgent();
	}

}