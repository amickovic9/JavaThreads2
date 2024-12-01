public class Potrosac extends  Thread{
    private static int statId = 0;
    private int id = ++statId;

    private Skladiste skladiste;
    private int brojac = 0;
    private int minTime;
    private int maxTime;


    private int trajanje = minTime + (int)Math.random()*(maxTime-minTime);

    public Potrosac(Skladiste skladiste, int minTime, int maxTime){
        this.skladiste = skladiste;
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()){
            System.out.println("Potrosac " + id + " je krenuo sa potrosnjom");
            try{
                while (!interrupted()){
                    int trajanje = minTime + (int)Math.random()*(maxTime-minTime);
                    sleep(trajanje);
                    int proizvod = skladiste.Uzmi();
                    System.out.println("Potrosac " + id + " je uzeo proizvod "+ proizvod);
                }
            } catch (InterruptedException e) {
                System.out.println(
                        "Potrosac " + id + " je prekinut"
                );
            }
        }
    }
}
