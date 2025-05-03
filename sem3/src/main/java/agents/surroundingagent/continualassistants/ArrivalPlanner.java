package agents.surroundingagent.continualassistants;

import OSPABA.*;
import OSPRNG.ExponentialRNG;

import com.sem3.Generators.ExponentialGenerator;

import Entities.Order;
import agents.surroundingagent.*;
import simulation.*;

//meta! id="37"
public class ArrivalPlanner extends OSPABA.Scheduler
{
	private ExponentialRNG arrivalTimeGenerator;
	public ArrivalPlanner(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
		MySimulation simulation = (MySimulation) mySim;
		arrivalTimeGenerator = new ExponentialRNG(1800d/* , simulation.seedGenerator */);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="SurroundingAgent", id="38", type="Start"
	public void processStart(MessageForm message)
	{
		message.setCode(Mc.orderArrival);
		hold(arrivalTimeGenerator.sample(), message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.orderArrival:
				
				message.setAddressee(myAgent());
				notice(message);
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
	public SurroundingAgent myAgent()
	{
		return (SurroundingAgent)super.myAgent();
	}

}