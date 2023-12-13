package Barbiere;
//Negozio barbiere con divano d'attesa con 5 posti e un area di attesa in piedi con 10 posti
//Lavorano 3 barbieri che servono una persona alla volta
//Se un cliente entra si siede nel divano e aspetta, se il divano é pieno aspetta in piedi, se la coda é piena va via
//Per pagare c'é la fila
//Si implementi la sincronizzazione tra i clienti e i barbieri usando il costrutto Monitor, si discuta se la soluzione
//puó presentare rinvio indefinito e/o deadlock, e se si, discutere eventuali modifiche per evitarli

import java.util.LinkedList;

//Main

public class Main{
    public static void main(String[] args){

        int n_b = 3;
        int n_c = 100;
        Barbiere barbieri[] = new Barbiere[n_b];
        Cliente clienti[] - new Cliente[n_c];
        Sala sala = new Sala();

        //Inizializzo i barbieri
        for(int i=0; i < n_b; i++){
            barbieri[i] = new Barbiere(i, sala);
            barbieri[i].start();
        }
        //Inizializzo i clienti
        for(int i=0; i< n_c; i++){
            try{
                //La sleep iniziale del thread main, simula l'arrivo dilazionato dei clienti
                Thread.sleep((int)(Math.random()*500) );
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

            clienti[i] = new Cliente(i, sala);
            clienti[i].start();
        }
    }
}

public class Barbiere extends Thread{
    private int id;
    private Sala sala;

    public Barbiere(int id, Sala sala){
        this.id = id;
        this.sala = sala;
        System.out.println("Il barbiere" + id + "comincia a lavorare");
    }

    public void run(){
        while(true){
            System.out.println("Il barbiere" +id + "e'pronto a servire un cliente");
            sala.serviCliente(this.id);

            try{
                Thread.sleep((int)(Math.random()*3000));
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            
            sala.effettuaPagamento(this.id);
        }
    }
}

public class Cliente extends Thread{
    private int id;
    private Sala sala;

    public Cliente(int id, Sala sala){
        this.id = id;
        this.sala = sala;
    }

    public void run(){
        System.out.println("Il cliente" + id + "entra dal barbiere");
        sala.mettiInAttesa(this.id);
    }
}

public class Sala{
    private LinkedList<Integer> divano;
    private LinkedList<Integer> sala_attesa;
    private LinkedList<Integer> coda_pagamento;
    private int cliente_servito;
    private int primo_cliente_attesa;

    public Sala(){
        divano = new LinkedList<Integer>();
        sala_attesa = new LinkedList<Integer>();
        coda_pagamento = new LinkedList<Integer>();
    }

    public synchronized void serviCliente(int id){
        //Se nel divano non ci sono clienti il barbiere puó andare in wait
        while(divano.size() == 0){
            try{
                System.out.println("Il barbiere" +id+ "non ha clienti da servire");
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        //Se il divano non é vuoto il barbiere serve il primo cliente del divano
        cliente_servito = divano.removeFirst();
        System.out.println("Il barbiere ha servito il cliente N" + cliente_servito);

        //Dopo che il cliente é stato servito deve essere aggiunto un cliente al divano e lui alla coda pagamento
        //Controllo che ci siano clienti in coda fuori dal divano e nel caso aggiungo
        if(sala_attesa.size() > 0){
            primo_cliente_attesa = sala_attesa.removeFirst();
            divano.add(primo_cliente_attesa);
            System.out.println("Il primo cliente in attesa "+id+"si é seduto sul divano");
        }
        //Metto il cliente in coda di pagamento
        coda_pagamento.add(cliente_servito);

    }
    public synchronized void effettuaPagamento(int barbiere){
        //Il cliente servito paga
        System.out.println("Il barbiere "+barbiere+"ha fatto pagare il cliente"+coda_pagamento.removeFirst());
    }
    public synchronized void mettiInAttesa(int id){
        //Quando un cliente arriva deve verificare che ci sia posto nel divano se no va in attesa
        //Nel caso non ci sia neanche posto se ne va

        if((divano.size()>5) && (sala_attesa.size()>10)){
            System.out.println("Negozio pieno, cliente"+id+"va via");
        }
        else if(divano.size()>5){ //Divano pieno ma coda no
            sala_attesa.add(id);
            System.out.println("Il cliente"+id+"si mette in attesa");
        }
        else{
            divano.add(id);
            System.out.println("Il cliente"+id+"si siede sul divano");
            notifyAll();
        }
    }

}