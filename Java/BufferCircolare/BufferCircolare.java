public class BufferCircolare{
    public static void main(String args[]){
        CircularBuffer condivisione = new CircularBuffer();
        Produttore produttore = new Produttore(condivisione);
        Consumatore consumatore = new Consumatore(condivisione);

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

class CircularBuffer implements Buffer{
    //Ogni elemento presente all'interno dell'array è un buffer

    /* Nota Bene
     * Se volessi avere un buffer con {-1, -1, -1} come da spiegazione
     * ci sarà un errore sul risultato finale: troppi -1 all'inizio.
     * Invece, se avessi soltanto un valore -1 il programma funziona regolarmente.
     */
    private int buffer[] = {-1};
    private int bufferOccupato = 0;
    private int posLettura = 0;
    private int posScrittura = 0;

    public synchronized void set(int valore){
        String nomeThread = Thread.currentThread().getName();
        while(bufferOccupato == buffer.length){
            try{
                stampa("Tutti i buffer risultano essere pieni. Il thread: " + nomeThread + " deve attendere.");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        buffer[posScrittura] = valore;
        ++bufferOccupato;
        posScrittura = (posScrittura + 1) % buffer.length;
        stampa("Il thread " + nomeThread + " ha scritto il valore: " + buffer[posScrittura]);
        notify();
    }

    public synchronized int get(){
        String nome = Thread.currentThread().getName();
        while(bufferOccupato == 0){
            try{
                stampa("Tutti i buffer risultano essere vuoti. Il thread: " + nome + " deve attendere.");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        int valoreLettura = buffer[posLettura];
        --bufferOccupato;
        posLettura = (posLettura + 1) % buffer.length;
        stampa("Il thread " + nome + " ha letto il valore: " + buffer[posLettura]);
        notify();
        return valoreLettura;
    }

    public void stampa(String messaggio){
        System.out.println(messaggio);
    }
}