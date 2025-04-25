
import InformationABACorePack.InformationABACore;
import simulation.MySimulation;
public class Main {
    public static void main(String[] args) {
        int replikacie = 1;
        int casReplikacie = 10000000;

        MySimulation simulation = new MySimulation();
        simulation.setSimSpeed(1, 0.00001);
        simulation.simulate(replikacie, casReplikacie);
    }
}