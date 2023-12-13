import java.util.concurrent.Semaphore;

class GestioneControllo{
    int oggetto;
    Semaphore semaforoConsumatore = new Semaphore(0, true);
    Semaphore semaforoProduttore = new Semaphore(1, true);

    void get(){
        try{
            semaforoConsumatore.acquire();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Il consumatore ha consumato questo oggetto: " + oggetto);
        semaforoProduttore.release();
    }

    void put(int oggetto){
        try{
            semaforoProduttore.acquire();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        this.oggetto = oggetto;
        System.out.println("Il produttore ha prodotto questo oggetto: " + oggetto);
        semaforoConsumatore.release();
    }
}

class Produttore implements Runnable{
    GestioneControllo v;
    Produttore(GestioneControllo v){
        this.v = v;
        new Thread(this, "Produttore").start();
    }
    public void run(){
        for(int i=1; i<=20; i++)
            v.put(i);
    }
}

class Consumatore implements Runnable{
    GestioneControllo v;
    Consumatore(GestioneControllo v){
        this.v = v;
        new Thread(this, "Consumatore").start();
    }
    public void run(){
        for(int i=1; i<=20; i++)
            v.get();
    }
}

public class SemaforoProduttoreConsumatore{
    public static void main(String args[]){
        GestioneControllo v = new GestioneControllo();
        new Consumatore(v);
        new Produttore(v);
    }
}