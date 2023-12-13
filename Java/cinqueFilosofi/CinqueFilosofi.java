public class CinqueFilosofi {
    public static void main(String args[]){
        int numeroFilosofi = 5;
        Filosofo filosofo[] = new Filosofo[numeroFilosofi];
        Bacchetta bacchetta[] = new Bacchetta[numeroFilosofi];

        for(int i=0; i<5; i++)
            bacchetta[i] = new Bacchetta(i);
        for(int i=0; i<5; i++){
            int sinistra, destra;

            sinistra = i-1;
            if(sinistra<0)
                sinistra = numeroFilosofi -1;
            destra = i;

            filosofo[i] = new Filosofo(i, bacchetta[sinistra], bacchetta[destra]);
            filosofo[i].start();
        }
    }
}

class Bacchetta{
    private boolean bacchettaLibera;
    public int bacchettaNum;

    public Bacchetta(int quale){
        bacchettaNum = quale;
        bacchettaLibera = true;
    }

    public synchronized void prendi(){
        String nomeThread = Thread.currentThread().getName();
        while(!bacchettaLibera){
            try{
                System.out.println(nomeThread + " sta aspettando che si liberi la bacchetta " + bacchettaNum);
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        bacchettaLibera = false;
    }

    public synchronized void rilascia(){
        bacchettaLibera = true;
        notifyAll();
    }
}

class Filosofo extends Thread{
    private int idFilosofo;
    private final int nSecondi = 2;
    private Bacchetta bacchettaSinistra;
    private Bacchetta bacchettaDestra;

    public Filosofo(int ID, Bacchetta sinistra, Bacchetta destra){
        idFilosofo = ID;
        this.setName("Filosofo #" + idFilosofo);
        bacchettaSinistra = sinistra;
        bacchettaDestra = destra;
    }

    public void run(){
        for(;;){
            this.pensa();
            this.prendiBacchette();
            this.mangia();
            this.rilasciaBacchette();
        }
    }

    private void pensa(){
        String nomeThread = this.getName();
        System.out.println(nomeThread + " sta pensando...");
        try{
            Thread.sleep(Math.round(Math.random()*nSecondi*1000));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void prendiBacchette(){
        String nomeThread = this.getName();
        System.out.println(nomeThread + " ha fame.\nSta cercando di prendere la bacchetta " + bacchettaSinistra.bacchettaNum + " alla sua sinistra");
        bacchettaSinistra.prendi();
        System.out.println(nomeThread + " ha preso la bacchetta " + bacchettaSinistra.bacchettaNum + " .\nAdesso sta cercando di prendere la bacchetta " + bacchettaDestra.bacchettaNum + " alla sua destra");
        bacchettaDestra.prendi();
        System.out.println(nomeThread + " ha preso la bacchetta " + bacchettaDestra.bacchettaNum + " .");
    }

    private void mangia(){
        String nomeThread = this.getName();
        System.out.println(nomeThread + " sta mangiando.");
        try{
            Thread.sleep(Math.round(Math.random()*nSecondi*1000));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void rilasciaBacchette(){
        String nomeThread = this.getName();
        System.out.println(nomeThread + " ha finito di mangiare --> sta rilasciando le bacchette.");
        bacchettaSinistra.rilascia();
        bacchettaDestra.rilascia();
    }
}
