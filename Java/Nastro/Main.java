/*Un nastro trasportatore é composto da N vani, ciascuno dei quali puó ospitare un singolo bagaglio.
Un singolo operatore si occupa di caricare i bagagli sul nastro ogni qual volta vi sia un vano disponibile.
Un flusso costante di viaggiatori, di numero molto maggiore di N, si reca in prossimitá del nastro e
vi sosta in attesa del bagaglio, e lo preleva non appena disponibile.
Si modelli mediante thread in java mediante il costrutto Monitor, l'implementazione deve essere coerente alla realtá,
Si descriva la sincronizzazione tra i thread, discutendo se la soluzione proposta puó portare a rinvio indefinito o deadlock
In caso si propongano soluzioni implementative per evitarlo.

Esercizio monitor esame 16/04/20

*/

package Nastro;

public class Main {
    public static void main(String args[]){
        int postri_nastro = 5;
        Nastri nastri = new Nastri(postri_nastro);
        int n_operatori = 3;
        Operatore operatore[] = new Operatore[n_operatori];
        int n_viaggiatori = 100;
        Viaggiatore viaggiatore[] = new Viaggiatore[n_viaggiatori];

        for (int j = 0; j < n_operatori; j++) {
            operatore[j] = new Operatore(nastri, j);
            operatore[j].start();
        }
        for(int i=0; i<n_viaggiatori; i++){

            try {
                Thread.sleep((int)(Math.random()*500) );
            } catch (Exception e) {
                e.printStackTrace();
            }            
            viaggiatore[i] = new Viaggiatore(nastri, i);
            viaggiatore[i].start();
        }
    }
}
public class Viaggiatore extends Thread{
    int viaggiatoreId;
    Nastri nastri;

    public Viaggiatore(Nastri nastri, int viaggiatoreId){
        this.viaggiatoreId=viaggiatoreId;
        this.nastri = nastri;
    }

    public void run(){
        try{
            nastri.ritiraBagaglio(this);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
public class Operatore extends Thread {
    int operatoreId;
    Nastri nastri;

    public Operatore(Nastri nastri,int operatoreId){
        this.operatoreId = operatoreId;
        this.nastri = nastri;
    }

    public void run(){
        while(true){
            try{
                nastri.depositabagaglio(this);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
import java.util.LinkedList;

public class Nastri {
    LinkedList <Integer> nastroA;
    LinkedList <Integer> nastroB;
    int posti;

    public Nastri(int p){
        nastroA = new LinkedList<Integer>();
        nastroB = new LinkedList<Integer>();
        posti = p;
    }

    public synchronized void ritiraBagaglio(Viaggiatore v) {
        if((nastroA.size() == 0) && (nastroB.size() == 0)){
            try{
                System.out.println("Il viaggiatore n "+ v.viaggiatoreId + " non ha bagagli da ritirare");
                wait(); //In attesa di una nofity dall'operatore
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroA.size()>0){
            try{
                nastroA.removeFirst();
                System.out.println("Il viaggiatore n "+ v.viaggiatoreId + " ritira un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroB.size()>0){
            try{
                nastroB.removeFirst();
                System.out.println("Il viaggiatore n "+ v.viaggiatoreId + " ritira un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void depositabagaglio(Operatore o) {
        if((nastroA.size() >= posti) && (nastroB.size() >= posti)){
            try{
                System.out.println("L'operatore n "+ o.operatoreId + " non puó depositare bagagli");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroA.size() < posti){
            try{
                nastroA.addFirst(null);
                System.out.println("L'operatore n "+ o.operatoreId + " deposita un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroB.size() < posti){
            try{
                nastroB.addFirst(null);
                System.out.println("L'operatore n "+ o.operatoreId + " deposita un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        notifyAll(); 
    }

}
import java.time.chrono.ThaiBuddhistEra;
import java.util.LinkedList;
public class Nastro{
    public static void main(String args[]){
        Vani vani = new Vani();
        Operatore operatore[] = new Operatore[3];
        Viaggiatore viaggiatore[] = new Viaggiatore[10];

        for(int i=0; i<10; i++){
            viaggiatore[i] = new Viaggiatore(vani, i);
            viaggiatore[i].start();
        }

        for(int i=0; i<3; i++){
            operatore[i] = new Operatore(vani, i);
            operatore[i].start();
        }
    }
}

class Operatore extends Thread{
    int idOperatore;
    Vani vani;
    public Operatore(Vani vani, int idOperatore){
        this.idOperatore = idOperatore;
        this.vani = vani;
    }
    public void run(){
        while(true){
            try{
                vani.posizionaBagaglio(this);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

class Viaggiatore extends Thread{
    int idViaggiatore;
    Vani vani;
    public Viaggiatore(Vani vani, int idViaggiatore){
        this.idViaggiatore = idViaggiatore;
        this.vani = vani;
    }
    public void run(){
        try{
            vani.ritiraBagaglio(this);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Vani{
    LinkedList<Viaggiatore> VanoA = new LinkedList<Viaggiatore>();
    LinkedList<Viaggiatore> VanoB = new LinkedList<Viaggiatore>();
    int MAX = 5;
    Viaggiatore v;
    static int contatore = 0;

    public synchronized void ritiraBagaglio(Viaggiatore v){
        if(VanoA.size()==0 && VanoB.size()==0){
            try{
                System.out.println("Nessun bagaglio presente all'interno del nastro trasportatore");
                Thread.sleep(500);
                wait();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            if(VanoA.size()>0){
                VanoA.removeFirst();
                System.out.println("Il viaggiatore ha ritirato il bagaglio");
                contatore++;
            }
            else if(VanoB.size()>0){
                VanoB.removeFirst();
                System.out.println("Il viaggiatore ha ritirato il bagaglio");
                contatore++;
            }
        }
    }

    public synchronized void posizionaBagaglio(Operatore p){
        if(VanoA.size()==MAX && VanoB.size()==MAX){
            try{
                System.out.println("Nastro trasportatore pieno.");
                Thread.sleep(500);
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        if(VanoA.size()<MAX){
            System.out.println("Bagaglio aggiunto");
            VanoA.add(v);
            notifyAll();
        }
        else if(VanoB.size()<MAX){
            System.out.println("Bagaglio aggiunto");
            VanoB.add(v);
            notifyAll();  
        }
    }
}