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