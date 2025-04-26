package agents.employeetransferagent;

import Entities.States.Position;
import OSPABA.*;
import simulation.*;

//meta! id="77"
public class EmployeeTransferManager extends OSPABA.Manager
{
	public EmployeeTransferManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="WorkshopAgent", id="78", type="Request"
	public void processTransferEmployee(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		switch (msg.getOrderItem().getState()) {
			case PENDING:
				msg.setAddressee(myAgent().findAssistant(Id.wareHouseTransferProcess));
				startContinualAssistant(msg);
				break;
			case MATERIAL_PREPARED:
				msg.setAddressee(myAgent().findAssistant(Id.wareHouseTransferProcess));
				startContinualAssistant(msg);
				break;
			default:
				break;
		}
	}

	//meta! sender="WorkStationTransferProcess", id="81", type="Finish"
	public void processFinishWorkStationTransferProcess(MessageForm message)
	{	
		MyMessage msg = (MyMessage) message.createCopy();
		msg.getEmployee().setPosition(Position.ASSEMBLY_STATION);
		response(msg);
	}

	//meta! sender="WareHouseTransferProcess", id="83", type="Finish"
	public void processFinishWareHouseTransferProcess(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		msg.setCode(Mc.transferEmployee);
		response(msg);
		
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		switch (message.code())
		{
		}
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
		case Mc.transferEmployee:
			processTransferEmployee(message);
		break;

		case Mc.finish:
			switch (message.sender().id())
			{
			case Id.workStationTransferProcess:
				processFinishWorkStationTransferProcess(message);
			break;

			case Id.wareHouseTransferProcess:
				processFinishWareHouseTransferProcess(message);
			break;
			}
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