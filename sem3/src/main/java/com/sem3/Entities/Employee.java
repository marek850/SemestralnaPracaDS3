package com.sem3.Entities;

import com.sem3.Entities.States.EmployeeState;
import com.sem3.Entities.States.EmployeeType;
import com.sem3.Entities.States.Position;
import com.sem3.Statistics.Statistic;

public class Employee {
    private EmployeeState state;
    private int id;
    private OrderItem currentOrderItem;
    private Position position;
    private EmployeeType type;
    private AssemblyStation station;
    private double notWorkingTime;
    private boolean isWorking;
    

    private Statistic workloadStat;
	
    public Employee(int id, EmployeeType type) {
        this.type = type;
        this.state = EmployeeState.IDLE;
        this.position = Position.STORAGE;
        this.station = null;
        this.id = id;
        this.isWorking = false;
        lastTimeChange = -1;
        this.workloadStat = new Statistic();
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
    public Statistic getWorkloadStat() {
		return workloadStat;
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
        if (type == EmployeeType.C && (state != EmployeeState.IDLE && state != EmployeeState.VARNISHING && state != EmployeeState.MOVING && state != EmployeeState.FITTING)) {
            throw new IllegalStateException("Employee type C cannot be in state " + state);
            
        }
        this.state = state;
    }
    public double getWorkload(double time) {
        if (notWorkingTime == 0) {
            return 0;
        } else {
            return ((time - notWorkingTime) / time) * 100;
        }
    }
}
