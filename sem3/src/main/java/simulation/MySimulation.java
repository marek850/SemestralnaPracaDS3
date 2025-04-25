package simulation;

import OSPABA.*;
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
	private int aEmpNumber;
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
		
		this.aEmpNumber = aEmpNumber;
		this.bEmpNumber = bEmpNumber;
		this.cEmpNumber = cEmpNumber;
		this.workStationNumber = workStationNumber;
		init();
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
		// Reset entities, queues, local statistics, etc...
	}

	@Override
	public void replicationFinished()
	{
		// Collect local statistics into global, update UI, etc...
		super.replicationFinished();
		System.out.println("Replication finished");
	}

	@Override
	public void simulationFinished()
	{
		// Display simulation results
		super.simulationFinished();
		System.out.println("Simulation finished");
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