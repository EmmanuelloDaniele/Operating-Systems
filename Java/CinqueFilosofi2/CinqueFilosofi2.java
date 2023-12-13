import java.util.concurrent.*;

public class CinqueFilosofi2{
    public static void main(String args[]){
        int numeroFilosofi = 5;
        Filosofi filosofi[] = new Filosofi[numeroFilosofi];
        Bacchetta bacchette[] = new Bacchetta[numeroFilosofi];

        for(int i=0; i<numeroFilosofi; i++)
            bacchette[i] = new Bacchetta();
        
        ExecutorService executor = Executors.newFixedThreadPool(numeroFilosofi);

        for(int i=0; i<numeroFilosofi; i++){
            if(i%2==0)
                /*
                 * Imposto il valore della bacchetta di sinistra maggiore rispetto a quella di destra 
                */
                filosofi[i] = new Filosofi(i, bacchette[(i+1) % numeroFilosofi], bacchette[i]);
            else
                /*
                 * Imposto il valore della bacchetta di destra maggiore rispetto a quella di destra 
                */
                filosofi[i] = new Filosofi(i, bacchette[i], bacchette[(i+1) % numeroFilosofi]);

            executor.execute(filosofi[i]);
        }
    }
}

class Bacchetta{
    public Semaphore semaforo = new Semaphore(1);
    void prendiBacchetta(){
        try{
            semaforo.acquire();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void rilasciaBacchetta(){
        semaforo.release();
    }
}

class Filosofi implements Runnable{
    private int idFilosofo;
    Bacchetta bacchettaSinistra;
    Bacchetta bacchettaDestra;

    public Filosofi(int id, Bacchetta sinistra, Bacchetta destra){
        idFilosofo = id;
        bacchettaSinistra = sinistra;
        bacchettaDestra = destra;
    }

    @Override
    public void run(){
        while(true){
            stampa(" sta pensando...");
            bacchettaSinistra.prendiBacchetta();
            System.out.println("Il Filosofo " + idFilosofo + " ha preso la bacchetta di sinistra.");
            bacchettaDestra.prendiBacchetta();
            System.out.println("Il Filosofo " + idFilosofo + " ha preso la bacchetta di destra.");
            stampa(" sta mangiando...");
            bacchettaSinistra.rilasciaBacchetta();
            System.out.println("Il Filosofo " + idFilosofo + " ha postato la bacchetta di sinistra.");
            bacchettaDestra.rilasciaBacchetta();
            System.out.println("Il Filosofo " + idFilosofo + " ha postato la bacchetta di destra.");
        }
    }

    void stampa(String azione){
        try{
            Thread.sleep(Math.round(Math.random()*2*1000));
            System.out.println("Filosofo " + idFilosofo + azione);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}