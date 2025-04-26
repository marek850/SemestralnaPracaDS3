package agents.aemployeesagent.continualassistants;

import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import OSPABA.*;
import simulation.*;
import agents.aemployeesagent.*;
import OSPABA.Process;
import OSPRNG.TriangularRNG;

//meta! id="181"
public class MaterialPrepareProcess extends OSPABA.Process
{
	private TriangularRNG prepareTimeGenerator = new TriangularRNG(300d, 500d, 900d);
	public MaterialPrepareProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AEmployeesAgent", id="182", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message;
		myMessage.setCode(Mc.cutOrderItem);
		myMessage.getEmployee().setState(EmployeeState.PREPARING_MATERIAL);
		myMessage.getOrderItem().setState(OrderItemState.PREPARING_MATERIAL);
		hold(prepareTimeGenerator.sample(), myMessage);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.cutOrderItem:
				MyMessage myMessage = (MyMessage) message;
				myMessage.getOrderItem().setState(OrderItemState.MATERIAL_PREPARED);
				message.setCode(Mc.finish);
				myMessage.setAddressee(myAgent());
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
	public AEmployeesAgent myAgent()
	{
		return (AEmployeesAgent)super.myAgent();
	}

}
