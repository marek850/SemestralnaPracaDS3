package agents.bemployeesagent;

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

//meta! id="7"
public class BEmployeesManager extends OSPABA.Manager
{
	public BEmployeesManager(int id, Simulation mySim, Agent myAgent)
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
	public void processEmployeeBAssignment(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processEmployeeBRelease(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="148", type="Request"
	public void processAssembleOrderItem(MessageForm message)
	{
		MyMessage msg = (MyMessage)message.createCopy();
		if (myAgent().getFreeEmployees().isEmpty()) {
			msg.getOrderItem().setState(OrderItemState.WAITING_FOR_ASSEMBLY);
			myAgent().addWaitingOrderAssemble(msg);
		} else{
			msg.setEmployee(myAgent().assignEmployee());
			msg.getEmployee().getWorkloadStat().addSample(1d);
			if (msg.getEmployee().getCurrentPosition() == Position.ASSEMBLY_STATION &&
					msg.getEmployee().getStation() == msg.getAssemblyStation()) {
					if (mySim().animatorExists()) {
						Employee employee = msg.getEmployee();
						Point2D target = new Point2D.Double(msg.getAssemblyStation().getPosition(mySim().currentTime()).getX() - 30,msg.getAssemblyStation().getPosition(mySim().currentTime()).getY());
						Point2D[] path = new Point2D[] {
							new Point2D.Double(employee.getPosition(mySim().currentTime()).getX(), employee.getPosition(mySim().currentTime()).getY()),       // výstup
							new Point2D.Double(target.getX(), employee.getPosition(mySim().currentTime()).getY()),      // horizontálny presun
							target                                                     // zostup
						};
						employee.startAnim(mySim().currentTime(), 3, path);
					}
					msg.setAddressee(myAgent().findAssistant(Id.assembleProcess));
					startContinualAssistant(msg);
			} else{
				msg.setCode(Mc.transferBEmployee);
				msg.setAddressee(myAgent().parent());
				request(msg);
			}
		}
	}

	//meta! sender="WorkshopAgent", id="159", type="Response"
	public void processTransferBEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		if (msg.getOrderItem().getState() == OrderItemState.VARNISHED || msg.getOrderItem().getState() == OrderItemState.WAITING_FOR_ASSEMBLY|| msg.getOrderItem().getState() == OrderItemState.STAINED) {
			msg.setCode(Mc.assembleOrderItem);
			msg.setAddressee(myAgent().findAssistant(Id.assembleProcess));
			startContinualAssistant(msg);
		}
	}

	//meta! sender="AssembleProcess", id="165", type="Finish"
	public void processFinish(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getAssemblyStation().setCurrentProcess(Process.NONE);
		Employee finishedEmployee = msg.getEmployee();
		handleFinishedEmployee(finishedEmployee);
		if (msg.getOrderItem().getItemType() != FurnitureType.WARDROBE) {
			msg.getAssemblyStation().setImage(AnimatorConfig.ASSEMBLY_STATION);
		}
		msg.setEmployee(null);
		msg.setCode(Mc.assembleOrderItem);
		response(msg);
	}

	public void handleFinishedEmployee(Employee finishedEmployee) {
		if(!myAgent().getWaitingOrdersAssemble().isEmpty()) {
			//ak caka objednavka na rezanie tak presunieme agenta do skladu
			MyMessage waitingOrder = myAgent().getWaitingOrderAssemble();
			waitingOrder.setEmployee(finishedEmployee);
			waitingOrder.setCode(Mc.transferBEmployee);
			waitingOrder.setAddressee(myAgent().parent());
			request(waitingOrder);
		} else {
			//ak necaka ziadna objednavka
			finishedEmployee.setState(EmployeeState.IDLE);
			finishedEmployee.getWorkloadStat().addSample(0d);
			if (mySim().animatorExists()) {
				// Presuň zamestnanca do fronty na dokončenie
				AssemblyStation station = finishedEmployee.getStation();
				Point2D startQueue = new Point2D.Double(station.getPosition(mySim().currentTime()).getX(), station.getPosition(mySim().currentTime()).getY() + 40);
				station.setStartPositionOfQueue(startQueue);
				moveEmployeeToFinishedQueue(finishedEmployee, station, mySim().currentTime());
			}
			
			myAgent().releaseEmployee(finishedEmployee);
		}

	}
	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init()
	{
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
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.assembleOrderItem:
			processAssembleOrderItem(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		case Mc.transferBEmployee:
			processTransferBEmployee(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public BEmployeesAgent myAgent()
	{
		return (BEmployeesAgent)super.myAgent();
	}

}