package simulation;

import java.awt.Color;
import java.util.Random;

import Entities.Employee;
import OSPABA.*;
import OSPAnimator.AnimImageItem;
import OSPAnimator.AnimShape;
import OSPAnimator.AnimShapeItem;
import UserInterface.AnimatorConfig;
import agents.bemployeesagent.*;
import agents.modelagent.*;
import agents.surroundingagent.*;
import agents.workstationagent.*;
import agents.aemployeesagent.*;
import agents.workshopagent.*;
import agents.cemployeesagent.*;
import agents.employeetransferagent.*;



public class MySimulation extends OSPABA.Simulation
{
	public long seed = 412/* new Random().nextInt(1000) */;
	public Random seedGenerator = new Random(seed);
	private int aEmpNumber;
	private AnimShapeItem storage;
	public AnimShapeItem getStorage() {
		return storage;
	}
	public int getaEmpNumber() {
		return aEmpNumber;
	}

	private int bEmpNumber;
	public int getbEmpNumber() {
		return bEmpNumber;
	}

	private int cEmpNumber;
	public int getcEmpNumber() {
		return cEmpNumber;
	}

	private int workStationNumber;
	public int getWorkStationNumber() {
		return workStationNumber;
	}

	public MySimulation(int aEmpNumber, int bEmpNumber, int cEmpNumber, int workStationNumber)
	{
		super();
		this.aEmpNumber = aEmpNumber;
		this.bEmpNumber = bEmpNumber;
		this.cEmpNumber = cEmpNumber;
		this.workStationNumber = workStationNumber;
		init();
		AEmployeesAgent aEmployeesAgent = (AEmployeesAgent) findAgent(Id.aEmployeesAgent);
		BEmployeesAgent bEmployeesAgent = (BEmployeesAgent) findAgent(Id.bEmployeesAgent);
		CEmployeesAgent cEmployeesAgent = (CEmployeesAgent) findAgent(Id.cEmployeesAgent);
		int width = Math.max(aEmployeesAgent.getEmployees().size(), Math.max(bEmployeesAgent.getEmployees().size(), cEmployeesAgent.getEmployees().size())) * 42; 
		int height = (36 + 10) * 3 + 20;
		
			storage = new AnimShapeItem(AnimShape.RECTANGLE, width, height);
			storage.setZIndex(0);
			storage.setColor(new Color(102, 51, 0));
			storage.setPosition(500d, 150d); 
			storage.setFill(true);
			storage.setToolTip("Sklad");
	}

	@Override
	public void prepareSimulation()
	{
		super.prepareSimulation();
		// Create global statistcis
	}

	@Override
	public void prepareReplication()
	{
		super.prepareReplication();
		/* if (animatorExists()) { */
			AEmployeesAgent aEmployeesAgent = (AEmployeesAgent) findAgent(Id.aEmployeesAgent);
			BEmployeesAgent bEmployeesAgent = (BEmployeesAgent) findAgent(Id.bEmployeesAgent);
			CEmployeesAgent cEmployeesAgent = (CEmployeesAgent) findAgent(Id.cEmployeesAgent);
			int width = Math.max(aEmployeesAgent.getEmployees().size(), Math.max(bEmployeesAgent.getEmployees().size(), cEmployeesAgent.getEmployees().size())) * 42; 
			int height = (36 + 10) * 3 + 20;

			//storage = new AnimShapeItem(AnimShape.RECTANGLE, width, height);
			if (animatorExists()) {
			animator().register(storage);
			storage.setZIndex(0);
			/* storage.setColor(new Color(102, 51, 0));
			storage.setPosition(animator(), 100); 
			storage.setFill(true);
			storage.setToolTip("Sklad"); */
			}
			

		/* } */
	}

	@Override
	public void replicationFinished()
	{
		// Collect local statistics into global, update UI, etc...
		super.replicationFinished();
		AEmployeesAgent aEmployeesAgent = (AEmployeesAgent) findAgent(Id.aEmployeesAgent);
		BEmployeesAgent bEmployeesAgent = (BEmployeesAgent) findAgent(Id.bEmployeesAgent);
		CEmployeesAgent cEmployeesAgent = (CEmployeesAgent) findAgent(Id.cEmployeesAgent);
		for (Employee employee : aEmployeesAgent.getEmployees()) {
			employee.getWorkloadStat().addSample(0d);
		}
		for (Employee employee : bEmployeesAgent.getEmployees()) {
			employee.getWorkloadStat().addSample(0d);
		}
		for (Employee employee : cEmployeesAgent.getEmployees()) {
			employee.getWorkloadStat().addSample(0d);
		}
		WorkshopAgent workshopAgent = (WorkshopAgent) findAgent(Id.workshopAgent);
		workshopAgent.afterReplicationWorkloadUpdate();

		
		
		for(ISimDelegate simDelegate : this.delegates()) {
			simDelegate.refresh(this);
		}
	}

	@Override
	public void simulationFinished()
	{
		// Display simulation results
		super.simulationFinished();
		for(ISimDelegate simDelegate : this.delegates()) {
			simDelegate.refresh(this);
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init()
	{
		setModelAgent(new ModelAgent(Id.modelAgent, this, null));
		setSurroundingAgent(new SurroundingAgent(Id.surroundingAgent, this, modelAgent()));
		setWorkshopAgent(new WorkshopAgent(Id.workshopAgent, this, modelAgent()));
		setAEmployeesAgent(new AEmployeesAgent(Id.aEmployeesAgent, this, workshopAgent()));
		setBEmployeesAgent(new BEmployeesAgent(Id.bEmployeesAgent, this, workshopAgent()));
		setCEmployeesAgent(new CEmployeesAgent(Id.cEmployeesAgent, this, workshopAgent()));
		setWorkStationAgent(new WorkStationAgent(Id.workStationAgent, this, workshopAgent()));
		setEmployeeTransferAgent(new EmployeeTransferAgent(Id.employeeTransferAgent, this, workshopAgent()));
	}

	private ModelAgent _modelAgent;

public ModelAgent modelAgent()
	{ return _modelAgent; }

	public void setModelAgent(ModelAgent modelAgent)
	{_modelAgent = modelAgent; }

	private SurroundingAgent _surroundingAgent;

public SurroundingAgent surroundingAgent()
	{ return _surroundingAgent; }

	public void setSurroundingAgent(SurroundingAgent surroundingAgent)
	{_surroundingAgent = surroundingAgent; }

	private WorkshopAgent _workshopAgent;

public WorkshopAgent workshopAgent()
	{ return _workshopAgent; }

	public void setWorkshopAgent(WorkshopAgent workshopAgent)
	{_workshopAgent = workshopAgent; }

	private AEmployeesAgent _aEmployeesAgent;

public AEmployeesAgent aEmployeesAgent()
	{ return _aEmployeesAgent; }

	public void setAEmployeesAgent(AEmployeesAgent aEmployeesAgent)
	{_aEmployeesAgent = aEmployeesAgent; }

	private BEmployeesAgent _bEmployeesAgent;

public BEmployeesAgent bEmployeesAgent()
	{ return _bEmployeesAgent; }

	public void setBEmployeesAgent(BEmployeesAgent bEmployeesAgent)
	{_bEmployeesAgent = bEmployeesAgent; }

	private CEmployeesAgent _cEmployeesAgent;

public CEmployeesAgent cEmployeesAgent()
	{ return _cEmployeesAgent; }

	public void setCEmployeesAgent(CEmployeesAgent cEmployeesAgent)
	{_cEmployeesAgent = cEmployeesAgent; }

	private WorkStationAgent _workStationAgent;

public WorkStationAgent workStationAgent()
	{ return _workStationAgent; }

	public void setWorkStationAgent(WorkStationAgent workStationAgent)
	{_workStationAgent = workStationAgent; }

	private EmployeeTransferAgent _employeeTransferAgent;

public EmployeeTransferAgent employeeTransferAgent()
	{ return _employeeTransferAgent; }

	public void setEmployeeTransferAgent(EmployeeTransferAgent employeeTransferAgent)
	{_employeeTransferAgent = employeeTransferAgent; }
	//meta! tag="end"
}