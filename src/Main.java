import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Skladiste skladiste = new Skladiste(10);
        upisiRaspored();

        List<Thread> sveNiti = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Thread proizvodjac = new Proizvodjac(skladiste, 1000, 2000);
            proizvodjac.start();
            sveNiti.add(proizvodjac);
        }

        for (int i = 0; i < 30; i++) {
            Thread potrosac = new Potrosac(skladiste, 1000, 2000);
            potrosac.start();
            sveNiti.add(potrosac);
        }

        Map<Integer, List<String>> raspored = ucitajRaspored();

        List<Izvestac> izvestaci = new ArrayList<>();
        izvestaci.add(new Izvestac(skladiste, raspored));
        izvestaci.add(new Izvestac(skladiste,  raspored));
        izvestaci.add(new Izvestac(skladiste,  raspored));

        for (Izvestac izvestac : izvestaci) {
            izvestac.start();
        }

        Scanner scanner = new Scanner(System.in);
        scanner.next();
        for (Izvestac izvestac : izvestaci) {
            izvestac.interrupt();
        }
        for (Thread nit : sveNiti) {
            nit.interrupt();
        }
    }
    public static void upisiRaspored() throws IOException {
        int izvestac;
        FileWriter fileWriter = new FileWriter("./Raspored.txt");
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            izvestac = random.nextInt(3) + 1;
            fileWriter.write((i + 1) + " Izvestac" + izvestac + "\n");
        }

        fileWriter.close();
    }

    public static Map<Integer, List<String>> ucitajRaspored() throws IOException {
        Map<Integer, List<String>> raspored = new HashMap<>();

        Files.lines(Paths.get("./Raspored.txt")).forEach(line -> {
            String[] delovi = line.split(" ");
            int trenutak = Integer.parseInt(delovi[0]);
            String izvestac = delovi[1];
            raspored.computeIfAbsent(trenutak, k -> new ArrayList<>()).add(izvestac);
        });

        return raspored;
    }

}
