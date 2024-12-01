import java.util.List;
import java.util.Map;
public class Izvestac extends Thread {
    private static int idCounter = 1;
    private int id;
    private Skladiste skladiste;
    private Map<Integer, List<String>> raspored;

    public Izvestac(Skladiste skladiste,  Map<Integer, List<String>> raspored) {
        this.id = idCounter++;
        this.skladiste = skladiste;
        this.raspored = raspored;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                for (int trenutak : raspored.keySet()) {
                    List<String> trenutniIzvestaci = raspored.get(trenutak);
                    if (trenutniIzvestaci.contains("Izvestac" + id)) {
                        System.out.println("Izvestac " + id );
                        System.out.println(skladiste.getStanje());
                    }
                    sleep(10000);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Izvestac " + id + " zavrsava sa radom.");
        }
    }
}
