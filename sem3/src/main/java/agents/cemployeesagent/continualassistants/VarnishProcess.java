package agents.cemployeesagent.continualassistants;

import Entities.States.EmployeeState;
import Entities.States.OrderItemState;
import OSPABA.*;
import simulation.*;
import agents.cemployeesagent.*;
import OSPABA.Process;
import OSPRNG.UniformContinuousRNG;

//meta! id="169"
public class VarnishProcess extends OSPABA.Process
{
	private  UniformContinuousRNG tableVarnishTime = new UniformContinuousRNG(6000d, 28800d);
	private  UniformContinuousRNG chairVarnishTime = new UniformContinuousRNG(5400d, 24000d);
	private  UniformContinuousRNG wardrobeVarnishTime = new UniformContinuousRNG(18000d, 36000d);
	public VarnishProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="CEmployeesAgent", id="170", type="Start"
	public void processStart(MessageForm message)
	{
		MyMessage myMessage = (MyMessage) message;
		myMessage.getEmployee().setState(EmployeeState.VARNISHING);
		myMessage.getOrderItem().setState(OrderItemState.BEING_VARNISHED);
		myMessage.setCode(Mc.varnishOrderitem);
		switch (myMessage.getOrderItem().getItemType()) {
			case CHAIR:
				hold(chairVarnishTime.sample(), myMessage);
				break;
			case TABLE:
				hold(tableVarnishTime.sample(), myMessage);
				break;
			case WARDROBE:
				hold(wardrobeVarnishTime.sample(), myMessage);
				break;
			default:
				break;
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.varnishOrderitem:
				MyMessage myMessage = (MyMessage) message;
				myMessage.getEmployee().setState(EmployeeState.IDLE);
				myMessage.getOrderItem().setState(OrderItemState.VARNISHED);
				myMessage.setCode(Mc.finish);
				myMessage.setAddressee(myAgent());
				notice(myMessage);
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
	public CEmployeesAgent myAgent()
	{
		return (CEmployeesAgent)super.myAgent();
	}

}