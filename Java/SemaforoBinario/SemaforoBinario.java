import java.util.concurrent.Semaphore;
public class SemaforoBinario{
    public static void main(String args[]) throws Exception{
        /* 
         * Definisco un semaforo con le seguenti caratteristiche:
         * - Permits (Se è 1, allora il semaforo è binario) = 1
         * - Fair (gestione politica: true->FIFO, false->Scheduler VM) = true
        */
        Semaphore semaforo = new Semaphore(1, true);
        //Definisco due variabile thread che stamperanno un messaggio tramite la classe Stampa
        Thread thread_A = new Thread(new Stampa(semaforo, "messaggio da parte di A"));
        Thread thread_B = new Thread(new Stampa(semaforo, "messaggio da parte di B"));
        //Eseguo i thread
        thread_A.start();
        thread_B.start();
        //Verifico se un thread è deceduto
        thread_A.join();
        thread_B.join();
    }
}

class Stampa extends Thread{
    Semaphore sem;
    String messaggioDaInviare = "";

    public Stampa(Semaphore s, String m){
        sem = s;
        messaggioDaInviare = m;
    }

    public void run(){
        try{
            /*
             * Carico i thread in una coda, e li eseguo.
             * I thread vengono caricati a seconda di come li abbiamo definiti, rispettandone l'ordine stabilito.
             * Man mano che eseguo i thread, li elimino dalla coda.
             * Politica: FIFO
            */
            sem.acquire();
            for(int i=1; i<=20; i++){
                System.out.println(messaggioDaInviare+": "+i);
                Thread.sleep(300);
            }
            sem.release();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}