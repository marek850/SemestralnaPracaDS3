
import InformationABACorePack.InformationABACore;
import simulation.MySimulation;
public class Main {
    public static void main(String[] args) {
        int replikacie = 1;
        int casReplikacie = 7171200;

        MySimulation simulation = new MySimulation(5, 5, 50, 80);
        simulation.setMaxSimSpeed();
        simulation.simulate(replikacie, casReplikacie);
    }
}