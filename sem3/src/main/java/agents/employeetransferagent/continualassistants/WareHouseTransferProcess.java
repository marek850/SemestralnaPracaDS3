package agents.employeetransferagent.continualassistants;

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
	private TriangularRNG transferTimeGenerator = new TriangularRNG(60d, 120d, 480d);
	public WareHouseTransferProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
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
		MyMessage myMessage = (MyMessage) message;
		myMessage.setCode(Mc.transferEmployee);
		if (myMessage.getOrderItem().getState() == OrderItemState.PENDING) {
			switch (myMessage.getEmployee().getCurrentPosition()) {
				case STORAGE:
					hold(0, myMessage);
					break;
				case ASSEMBLY_STATION:
					myMessage.getEmployee().setState(EmployeeState.MOVING);
					hold(transferTimeGenerator.sample(), myMessage);
					break;
				default:
					break;
			}
		} else if (myMessage.getOrderItem().getState() == OrderItemState.MATERIAL_PREPARED) {
			myMessage.getEmployee().setState(EmployeeState.MOVING);
			hold(transferTimeGenerator.sample(), myMessage);
		}{
			
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