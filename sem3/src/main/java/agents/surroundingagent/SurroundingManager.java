package agents.surroundingagent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sem3.Entities.States.OrderState;

import Entities.Order;
import Entities.OrderItem;
import Entities.States.FurnitureType;
import Generators.DiscreteGenerator;
import OSPABA.*;
import simulation.*;

//meta! id="3"
public class SurroundingManager extends OSPABA.Manager
{
	
	

	public SurroundingManager(int id, Simulation mySim, Agent myAgent)
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

	//meta! sender="ModelAgent", id="33", type="Notice"
	public void processOrderCompletion(MessageForm message)
	{
		MyMessage msg = (MyMessage) message.createCopy();
		myAgent().removeOrder(msg.getOrder());
	}

	//meta! sender="ModelAgent", id="29", type="Notice"
	public void processInitialisation(MessageForm message)
	{
		message.setAddressee(myAgent().findAssistant(Id.arrivalPlanner));
		startContinualAssistant(message);
	}

	//meta! sender="ArrivalPlanner", id="38", type="Finish"
	public void processFinish(MessageForm message)
	{
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message)
	{
		MyMessage myMessage = (MyMessage)message;
		switch (myMessage.code())
		{
			case Mc.orderArrival:
				Order newOrder = new Order(myAgent().getOrderIDGen().nextInt());
				newOrder.setState(OrderState.UNSTARTED);
				newOrder.setOrderArrivalTime(mySim().currentTime());
				for( int i = 0; i < myAgent().getItemNumberGen().sample(); i++)
				{
					int itemType = myAgent().getItemTypeGen().sample();
					FurnitureType type = null;
					if (itemType < 50) {
						type = FurnitureType.TABLE;
					} else if (itemType < 65){
						type = FurnitureType.CHAIR;
					}
					else {
						type = FurnitureType.WARDROBE;
					}
					OrderItem item = new OrderItem(myAgent().getItemIDGen().nextInt(), newOrder.getOrderArrivalTime(), type);
					int stainProb = myAgent().getStainProbGen().sample();
					if (stainProb < 15) {
						item.setStain(true);
					} else {
						item.setStain(false);
					}
					newOrder.addItem(item);
				}
				myAgent().getActivOrders().add(newOrder);
				myMessage.setOrder(newOrder);
				myMessage.setAddressee(myAgent().parent());
				notice(myMessage);
				MyMessage newMessage = new MyMessage(mySim());
				newMessage.setAddressee(myAgent().findAssistant(Id.arrivalPlanner));
				startContinualAssistant(newMessage);
				break;
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
		case Mc.initialisation:
			processInitialisation(message);
		break;

		case Mc.finish:
			processFinish(message);
		break;

		case Mc.orderCompletion:
			processOrderCompletion(message);
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