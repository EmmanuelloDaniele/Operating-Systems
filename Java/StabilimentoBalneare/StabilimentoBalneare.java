import java.util.LinkedList;
public class StabilimentoBalneare{
    public static void main(String args[]){
        Spiaggia spiaggia = new Spiaggia();
        Cliente cliente[] = new Cliente[20];
        Biglietteria biglietteria = new Biglietteria(spiaggia);

        for(int i=0; i<20; i++){
            try{
                cliente[i] = new Cliente(spiaggia, i);
                Thread.sleep(500);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            cliente[i].start();
        }

        biglietteria.start();

        try{
            for(int i=0; i<20; i++){
                cliente[i].join();
            }
            biglietteria.join();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Cliente extends Thread{
    int idCliente;
    int gruppo; //da 1 a 4 persone
    int tipologia; //0 occasionali, 1 altri
    Spiaggia spiaggia;

    public Cliente(Spiaggia spiaggia, int idCliente){
        this.spiaggia = spiaggia;
        this.idCliente = idCliente;
        this.gruppo = 1 + (int)(Math.random()*5);
        this.tipologia = (int)(Math.random()*2);
    }

    public void run(){
        try{
            spiaggia.accediSpiaggia(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Biglietteria extends Thread{
    Spiaggia spiaggia;

    public Biglietteria(Spiaggia spiaggia){
        this.spiaggia = spiaggia;
    }

    public void run(){
        while(true){
            try{
                spiaggia.gestisciPostazione(this);
                Thread.sleep(500);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}

class Spiaggia{
    LinkedList<Cliente> postazioni = new LinkedList<Cliente>();
    LinkedList<Cliente> accesso = new LinkedList<Cliente>();
    Cliente cliente;
    int MAX_POSTAZIONI = 5;
    int contatore = 0;
    public synchronized void accediSpiaggia(Cliente c){
        System.out.println("I clienti sono in coda per la biglietteria");
        accesso.add(c);
    }

    public synchronized void gestisciPostazione(Biglietteria b){
        while(postazioni.size() == MAX_POSTAZIONI){
            try {
                System.out.println("Tutte le postazioni sono occupate.");
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        while(accesso.size() == 0){
            try{
                System.out.println("Nessun cliente si trova in biglietteria.");
                System.exit(1);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        while(accesso.size()>0){
            //Siamo nel caso in cui abbiamo clienti abbonati
            if(accesso.element().tipologia == 1){
                System.out.println("Stanno entranto i clienti abbonati");
                cliente = accesso.removeFirst();
                if(postazioni.size()<=MAX_POSTAZIONI){
                    System.out.println("I clienti abbonati occupano " + cliente.gruppo/2 + " postazioni");
                    postazioni.add(cliente);
                    contatore++;
                }
            }
            //Siamo nel caso in cui abbiamo clienti non abbonati
            else{
                System.out.println("Stanno entrano i clienti occasionali");
                cliente = accesso.remove();
                if(postazioni.size()<=MAX_POSTAZIONI){
                    System.out.println("I clienti occasionali occupano " + cliente.gruppo/2 + " postazioni");
                    postazioni.add(cliente);
                    contatore++;
                }
            }
        }
        while(postazioni.size() > 0){
            try {
                Thread.sleep(100);
                System.out.println("I clienti vanno via");
                postazioni.removeFirst();
                notifyAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        contatore = 0;   
    }
}