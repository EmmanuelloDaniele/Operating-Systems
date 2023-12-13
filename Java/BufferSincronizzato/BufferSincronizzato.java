public class BufferSincronizzato{
    public static void main(String args[]){
        // Creo un oggetto che verrà utilizzato dai thread
        Buffer condivisione = new Sincronizza();

        //Creo un oggetto produttore e consumatore
        Produttore produttore = new Produttore(condivisione);
        Consumatore consumatore = new Consumatore(condivisione);

        //Eseguo i thread
        produttore.start();
        consumatore.start();
    }
}

interface Buffer{
    public void set(int valore);
    public int get();
}

class Produttore extends Thread{
    private Buffer shareLocation;
    public Produttore(Buffer shared){
        super("Produttore");
        shareLocation = shared;
    }
  
    public void run(){
        for(int i=1; i<=5; i++){
            try{
                Thread.sleep((int)(Math.random()*3001));
                shareLocation.set(i);
            }
            catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }
        System.err.println("Il thread " + getName() + " ha appena terminato la sua esecuzione");
    }
}
 
class Consumatore extends Thread{
    private Buffer shareLocation;
  
    public Consumatore(Buffer shared){
        super("Consumatore");
        shareLocation = shared;
    }
  
    public void run(){
        int sum = 0;
        for (int i=1; i<=5; ++i){
            try{
                Thread.sleep((int)(Math.random()*3001));
                sum += shareLocation.get();
            }
            catch (InterruptedException exception){
                exception.printStackTrace();
            }
        }
        System.err.println("Il thread " +getName() + " ha appena terminato la sua esecuzione.\nLa somma di tutti i valori letti è pari a: " + sum);
    }
}

class Sincronizza implements Buffer{
    private int buffer = -1;
    private int bufferOccupato = 0;

    public synchronized void set(int valore){
        String nomeThread = Thread.currentThread().getName();
        while(bufferOccupato == 1){
            try{
                stampa(nomeThread + " sta tentando di scrivere\n");
                stampa("Buffer pieno. Il thread " +nomeThread+ " deve attendere");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        //Se siamo qui, il buffer risulterà vuoto
        buffer = valore;

        //Buffer pieno, non potrò inserire altri valori
        ++bufferOccupato;
        stampa(nomeThread + " ha scritto " + buffer + " --> notify()");
        notify();
    }

    public synchronized int get(){
        String nomeThread = Thread.currentThread().getName();

        while(bufferOccupato == 0){
            try{
                stampa(nomeThread + " sta tentando di leggere");
                stampa("Buffer vuoto. Il thread " + nomeThread + " deve attendere");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        //Quando sarò qui, avrà un buffer con un valore
        //Decremento, in modo tale che il produttore possa scrivere
        --bufferOccupato;
        stampa(nomeThread + " ha letto " + buffer + " --> notify()");
        notify();
        return buffer;
    }

    public void stampa(String messaggio){
        System.out.println(messaggio);
    }
}