package agents.cemployeesagent;

import OSPABA.*;
import agents.cemployeesagent.continualassistants.*;
import simulation.*;



//meta! id="8"
public class CEmployeesAgent extends OSPABA.Agent
{
	public CEmployeesAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new CEmployeesManager(Id.cEmployeesManager, mySim(), this);
		new CFitHardwareProcess(Id.cFitHardwareProcess, mySim(), this);
		new VarnishProcess(Id.varnishProcess, mySim(), this);
		new StainProcess(Id.stainProcess, mySim(), this);
		addOwnMessage(Mc.transferCEmployee);
		addOwnMessage(Mc.requestCWaitingOrders);
		addOwnMessage(Mc.varnishOrderitem);
		addOwnMessage(Mc.cFitHardwareOnItem);
		addOwnMessage(Mc.stainOrderItem);
		addOwnMessage(Mc.requestNumOfFreeEmpC);
	}
	//meta! tag="end"
}