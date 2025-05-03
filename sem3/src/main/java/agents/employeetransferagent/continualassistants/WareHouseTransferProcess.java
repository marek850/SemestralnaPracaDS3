package agents.employeetransferagent.continualassistants;

import java.awt.geom.Point2D;

import Entities.Employee;
import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import Entities.States.Position;
import OSPABA.*;
import simulation.*;
import agents.employeetransferagent.*;
import OSPABA.Process;
import OSPRNG.TriangularRNG;

//meta! id="82"
public class WareHouseTransferProcess extends OSPABA.Process
{
	private TriangularRNG transferTimeGenerator;
	public WareHouseTransferProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		MySimulation simulation = (MySimulation) mySim;
		transferTimeGenerator = new TriangularRNG(60d, 120d, 480d/* , simulation.seedGenerator */);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="EmployeeTransferAgent", id="83", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.setCode(Mc.transferEmployee);
		if (myMessage.getOrderItem().getState() == OrderItemState.PENDING ) {
			switch (myMessage.getEmployee().getCurrentPosition()) {
				case STORAGE:
					hold(0, myMessage);
					break;
				case ASSEMBLY_STATION:
					Employee emp = myMessage.getEmployee();
					emp.setState(EmployeeState.MOVING);
					
					Double movingTime = transferTimeGenerator.sample();
					if (mySim().animatorExists()) {
						Point2D start = emp.getPosition(this.mySim().currentTime()); // pozícia v sklade
						MySimulation sim = (MySimulation) mySim();
						
						double koridorY = sim.getStorage().getPosition(sim.currentTime()).getY() + sim.getStorage().getHeight() + 30; // pevná výška koridoru (napr. o 100px nižšie než sklad)
		
						// Cieľ: montážne miesto
						Point2D target = new Point2D.Double(emp.getDefaultStoragePosX(), emp.getDefaultStoragePosY());
		
						// Definuj cestu v bodoch
						Point2D[] path = new Point2D[] {
							new Point2D.Double(start.getX(), start.getY()),     // výstup zo skladu
							new Point2D.Double(start.getX(), koridorY),     // výstup zo skladu
							new Point2D.Double(target.getX(), koridorY),      // horizontálny pohyb koridorom
							new Point2D.Double(target.getX(), target.getY())    // zostup na miesto
						};
						emp.startAnim(mySim().currentTime(), movingTime, path);
					}
					hold(movingTime, myMessage);

					break;
				default:
					break;
			}
		} else if (myMessage.getOrderItem().getState() == OrderItemState.MATERIAL_PREPARED) {
			Employee emp = myMessage.getEmployee();
			emp.setState(EmployeeState.MOVING);
			Double movingTime = transferTimeGenerator.sample();
			if (mySim().animatorExists()) {
				Point2D start = emp.getPosition(this.mySim().currentTime()); // pozícia v sklade
				MySimulation sim = (MySimulation) mySim();
				double koridorY = sim.getStorage().getPosition(sim.currentTime()).getY() + sim.getStorage().getHeight() + 30; // pevná výška koridoru (napr. o 100px nižšie než sklad)

				// Cieľ: montážne miesto
				Point2D target = new Point2D.Double(myMessage.getAssemblyStation().getPosition(mySim().currentTime()).getX() - 30, myMessage.getAssemblyStation().getPosition(mySim().currentTime()).getY() );

				// Definuj cestu v bodoch
				Point2D[] path = new Point2D[] {
					new Point2D.Double(start.getX(), start.getY()),
					new Point2D.Double(start.getX(), koridorY),     // výstup zo skladu
					new Point2D.Double(target.getX(), koridorY),      // horizontálny pohyb koridorom
					new Point2D.Double(target.getX(), target.getY())    // zostup na miesto
				};
				emp.startAnim(mySim().currentTime(), movingTime, path);
			}
			
			hold(movingTime, myMessage);
		}else {
			Employee emp = myMessage.getEmployee();
			emp.setState(EmployeeState.MOVING);
			Double movingTime = transferTimeGenerator.sample();
			if (mySim().animatorExists()) {
				Point2D start = emp.getPosition(this.mySim().currentTime()); // pozícia v sklade
				MySimulation sim = (MySimulation) mySim();
				double koridorY = sim.getStorage().getPosition(sim.currentTime()).getY() + sim.getStorage().getHeight() + 30; // pevná výška koridoru (napr. o 100px nižšie než sklad)

				// Cieľ: montážne miesto
				Point2D target = new Point2D.Double(myMessage.getAssemblyStation().getPosition(mySim().currentTime()).getX() -30, myMessage.getAssemblyStation().getPosition(mySim().currentTime()).getY());

				// Definuj cestu v bodoch
				Point2D[] path = new Point2D[] {
					new Point2D.Double(start.getX(), start.getY()),
					new Point2D.Double(start.getX(), koridorY),     // výstup zo skladu
					new Point2D.Double(target.getX(), koridorY),      // horizontálny pohyb koridorom
					new Point2D.Double(target.getX(), target.getY())    // zostup na miesto
				};
				emp.startAnim(mySim().currentTime(), movingTime, path);
			}
			hold(movingTime, myMessage);
			
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.transferEmployee:
				MyMessage myMessage = (MyMessage) message.createCopy();
				if (myMessage.getOrderItem().getState()  == OrderItemState.PENDING) {
					myMessage.getEmployee().setPosition(Position.STORAGE);
					myMessage.getEmployee().setStation(null);
				}else if (myMessage.getOrderItem().getState() == OrderItemState.MATERIAL_PREPARED) {
					myMessage.getEmployee().setPosition(Position.ASSEMBLY_STATION);
					myMessage.getEmployee().setStation(myMessage.getOrderItem().getAssemblyStation());
				} else {
					myMessage.getEmployee().setPosition(Position.ASSEMBLY_STATION);
					myMessage.getEmployee().setStation(myMessage.getOrderItem().getAssemblyStation());
				}
				myMessage.setAddressee(myAgent());
				myMessage.setCode(Mc.finish);
				notice(myMessage);
				break;
			default:
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message)
	{
		switch (message.code())
		{
		case Mc.start:
			processStart(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public EmployeeTransferAgent myAgent()
	{
		return (EmployeeTransferAgent)super.myAgent();
	}

}