package agents.aemployeesagent.continualassistants;

import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import Entities.States.Process;
import OSPABA.*;
import simulation.*;
import agents.aemployeesagent.*;
import OSPRNG.UniformContinuousRNG;

//meta! id="173"
public class AFitHardwareProcess extends OSPABA.Process
{
	public AFitHardwareProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="AEmployeesAgent", id="174", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message.createCopy();
		myMessage.getEmployee().setState(EmployeeState.FITTING);
		myMessage.getAssemblyStation().setCurrentProcess(Process.FITTING);
		myMessage.getOrderItem().setState(OrderItemState.BEING_FITTED);
		myMessage.setCode(Mc.aFitHardwareOnItem);
		hold(myMessage.getHardwareFitTIme(), myMessage);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.aFitHardwareOnItem:
				MyMessage myMessage = (MyMessage) message.createCopy();
				myMessage.getOrderItem().setState(OrderItemState.FITTED);
				myMessage.getEmployee().setState(EmployeeState.IDLE);
				myMessage.setCode(Mc.finish);
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