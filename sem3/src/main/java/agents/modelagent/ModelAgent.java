package agents.modelagent;

import OSPABA.*;
import simulation.*;



//meta! id="1"
public class ModelAgent extends OSPABA.Agent
{
	public ModelAgent(int id, Simulation mySim, Agent parent)
	{
		super(id, mySim, parent);
		init();
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		// Setup component for the next replication
		MyMessage newMessage = new MyMessage(this._mySim);
		newMessage.setCode(Mc.initialisation);
		newMessage.setAddressee(this);
		myManager().notice(newMessage);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		new ModelManager(Id.modelManager, mySim(), this);
		addOwnMessage(Mc.processOrder);
		addOwnMessage(Mc.orderArrival);
	}
	//meta! tag="end"
}