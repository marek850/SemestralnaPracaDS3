package agents.cemployeesagent;

import OSPABA.*;
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

	//meta! userInfo="Removed from model"
	public void processEmployeeCAssignment(MessageForm message)
	{
	}

	//meta! userInfo="Removed from model"
	public void processEmployeeCRelease(MessageForm message)
	{
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
	}

	//meta! sender="WorkshopAgent", id="146", type="Request"
	public void processRequestCWaitingOrders(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="149", type="Request"
	public void processVarnishOrderitem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="156", type="Request"
	public void processCFitHardwareOnItem(MessageForm message)
	{
	}

	//meta! sender="VarnishProcess", id="170", type="Finish"
	public void processFinishVarnishProcess(MessageForm message)
	{
	}

	//meta! sender="StainProcess", id="168", type="Finish"
	public void processFinishStainProcess(MessageForm message)
	{
	}

	//meta! sender="CFitHardwareProcess", id="172", type="Finish"
	public void processFinishCFitHardwareProcess(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="154", type="Request"
	public void processStainOrderItem(MessageForm message)
	{
	}

	//meta! sender="WorkshopAgent", id="178", type="Request"
	public void processRequestNumOfFreeEmpC(MessageForm message)
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
		case Mc.stainOrderItem:
			processStainOrderItem(message);
		break;

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