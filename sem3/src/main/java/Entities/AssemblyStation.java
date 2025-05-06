package Entities;
import java.awt.geom.Point2D;

import Entities.States.Process;
import OSPAnimator.AnimImageItem;
import UserInterface.AnimatorConfig;
public class AssemblyStation extends AnimImageItem implements Comparable<AssemblyStation>  {
    private int id;
    private final Point2D startPositionOfQueue;
    private int finishedCount = 0;
    private final int verticalOffset = 10;
    
    public AssemblyStation(int id) {
        super(AnimatorConfig.ASSEMBLY_STATION, 36, 36);
        this.id = id;
        this.currentProcess = Process.NONE;
        this.startPositionOfQueue = new Point2D.Double(0, 0);
        setToolTip("Montazne miesto s ID:" + id);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    private Process currentProcess;
    public Process getCurrentProcess() {
        return currentProcess;
    }
    public void setCurrentProcess(Process currentProcess) {
        this.currentProcess = currentProcess;
    }
    public void setStartPositionOfQueue(Point2D startPositionOfQueue) {
        this.startPositionOfQueue.setLocation(startPositionOfQueue);
    }
    public Point2D getNextFinishedPosition() {
        return new Point2D.Double(
            startPositionOfQueue.getX(),
            startPositionOfQueue.getY() + finishedCount * verticalOffset
        );
    }

    public void incrementFinishedCount() {
        finishedCount++;
    }

    public int getFinishedCount() {
        return finishedCount;
    }
    @Override
    public int compareTo(AssemblyStation o) {
        return Integer.compare(this.id, o.id);
    }
    
}