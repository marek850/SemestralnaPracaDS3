package agents.aemployeesagent;

import OSPABA.*;
import simulation.*;

//meta! id="6"
public class AEmployeesManager extends OSPABA.Manager
{
	public AEmployeesManager(int id, Simulation mySim, Agent myAgent)
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
	public void processEmployeeARelease(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processEmployeeAAssignment(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
	}

	//meta! sender="WorkshopAgent", id="143", type="Request"
	public void processRequestAWaitingOrders(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="157", type="Request"
	public void processAFitHardwareOnItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="158", type="Response"
	public void processTransferAEmployee(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="147", type="Request"
	public void processCutOrderItem(MessageForm message)
	{
		MyMessage msg = (MyMessage) message;
		msg.setCode(Mc.transferAEmployee);
		msg.setAddressee(myAgent().parent());
		request(msg);
	}

	//meta! sender="AFitHardwareProcess", id="174", type="Finish"
	public void processFinishAFitHardwareProcess(MessageForm message)
	{
	}

	//meta! sender="CutProcess", id="162", type="Finish"
	public void processFinishCutProcess(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="177", type="Request"
	public void processRequestNumOfFreeEmpA(MessageForm message)
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
		case Mc.requestNumOfFreeEmpA:
			processRequestNumOfFreeEmpA(message);
		break;

		case Mc.requestAWaitingOrders:
			processRequestAWaitingOrders(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.aFitHardwareProcess:
				processFinishAFitHardwareProcess(message);
			break;

			case Id.cutProcess:
				processFinishCutProcess(message);
			break;
			}
		break;

		case Mc.transferAEmployee:
			processTransferAEmployee(message);
		break;

		case Mc.cutOrderItem:
			processCutOrderItem(message);
		break;

		case Mc.aFitHardwareOnItem:
			processAFitHardwareOnItem(message);
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