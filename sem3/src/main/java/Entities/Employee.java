package Entities;

import Entities.States.EmployeeState;
import Entities.States.EmployeeType;
import Entities.States.Position;
import OSPABA.Simulation;
import OSPAnimator.AnimImageItem;
import OSPStat.Stat;
import OSPStat.WStat;
import UserInterface.AnimatorConfig;

public class Employee extends AnimImageItem{
    private EmployeeState state;
    private int id;
    private OrderItem currentOrderItem;
    private Position position;
    private EmployeeType type;
    private AssemblyStation station;
    private double notWorkingTime;
    private boolean isWorking;
    private WStat workloadStat;
    private double defaultStoragePosX;
    public double getDefaultStoragePosX() {
        return defaultStoragePosX;
    }

    public void setDefaultStoragePosX(double defaultStoragePosX) {
        this.defaultStoragePosX = defaultStoragePosX;
    }
    private double defaultStoragePosY;

    
    public double getDefaultStoragePosY() {
        return defaultStoragePosY;
    }

    public void setDefaultStoragePosY(double defaultStoragePosY) {
        this.defaultStoragePosY = defaultStoragePosY;
    }

    public WStat getWorkloadStat() {
        return workloadStat;
    }
    private Stat overallWorkloadStat;
    

    public Stat getGlobalWorkloadStat() {
        return overallWorkloadStat;
    }
	
    public Employee(int id, EmployeeType type, Simulation simulation) {
        super(AnimatorConfig.EMPLOYEE, 36, 36);
        this.type = type;
        this.state = EmployeeState.IDLE;
        this.position = Position.STORAGE;
        setZIndex(1);
        switch (type) {
            case A:
                setImage(AnimatorConfig.EMPLOYEE);
                setToolTip("Zamestnanec skupiny A s ID:" + id);
                break;
            case B:
                setImage(AnimatorConfig.EMPLOYEEB);
                setToolTip("Zamestnanec skupiny B s ID:" + id);
                break;
            case C:
                setImage(AnimatorConfig.EMPLOYEEC);
                setToolTip("Zamestnanec skupiny C s ID:" + id);
                break;
            default:
        }
        this.station = null;
        this.id = id;
        this.isWorking = false;
        lastTimeChange = -1;
        this.workloadStat = new WStat(simulation);
        this.overallWorkloadStat = new Stat();
    }
    public OrderItem getCurrentOrderItem() {
        return currentOrderItem;
    }

    public void setCurrentOrderItem(OrderItem currentOrderItem) {
        this.currentOrderItem = currentOrderItem;
    }
    public boolean isWorking() {
		return isWorking;
	}
	public void setWorking(boolean isWorking, double time) {
        if (!isWorking() && isWorking) {
            notWorkingTime += time - lastTimeChange;
            lastTimeChange = -1;
            
        } else if (isWorking() && !isWorking) {
            lastTimeChange = time;
        }
		this.isWorking = isWorking;
	}
	public double getNotWorkingTime() {
		return notWorkingTime;
	}
	public void setNotWorkingTime(double notWorkingTime) {
		this.notWorkingTime = notWorkingTime;
	}
	private double lastTimeChange;
    public double getLastTimeChange() {
		return lastTimeChange;
	}
	public void setLastTimeChange(double lastTimeChange) {
		this.lastTimeChange = lastTimeChange;
	}
	public AssemblyStation getStation() {
        return station;
    }
    public void setStation(AssemblyStation station) {
        this.station = station;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void reset(){
        this.state = EmployeeState.IDLE;
        this.position = Position.STORAGE;
        this.station = null;
        this.isWorking = false;
        lastTimeChange = -1;
        this.notWorkingTime = 0;
        workloadStat.clear();
    }
    public EmployeeType getType() {
        return type;
    }
    public Position getCurrentPosition() {
        return position;
    }
    public void setPosition(Position position) {
        this.position = position;
    }
    public EmployeeState getState() {
        return state;
    }
    public void setState(EmployeeState state) {
        
        this.state = state;
    }
    public double getWorkload() {
        return workloadStat.mean();
    }
}
