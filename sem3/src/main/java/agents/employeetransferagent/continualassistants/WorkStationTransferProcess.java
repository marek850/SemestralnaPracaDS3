package agents.employeetransferagent.continualassistants;

import OSPABA.*;
import simulation.*;
import agents.employeetransferagent.*;
import OSPABA.Process;
import OSPRNG.TriangularRNG;

//meta! id="80"
public class WorkStationTransferProcess extends OSPABA.Process
{
	private TriangularRNG transferTimeGenerator = new TriangularRNG(120d, 150d, 500d);
	public WorkStationTransferProcess(int id, Simulation mySim, CommonAgent myAgent)
	{
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="EmployeeTransferAgent", id="81", type="Start"
	public void processStart(MessageForm message)
	{
		message.setCode(Mc.transferEmployee);
		hold(transferTimeGenerator.sample(), message);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
			case Mc.transferEmployee:
				message.setAddressee(myAgent());
				notice(message);
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