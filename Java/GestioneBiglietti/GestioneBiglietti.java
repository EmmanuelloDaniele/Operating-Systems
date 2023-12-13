import java.util.LinkedList;
public class GestioneBiglietti{
    public static void main(String args[]){
        int numClienti = 10;
        int nRivenditori = 5;
        Cliente cliente[] = new Cliente[numClienti];
        Rivenditore rivenditore[] = new Rivenditore[nRivenditori];
        Teatro teatro = new Teatro();

        for(int i=0; i<numClienti; i++){
            cliente[i] = new Cliente(teatro, i);
            cliente[i].start();
        }

        for(int i=0; i<nRivenditori; i++){
            rivenditore[i] = new Rivenditore(teatro, i);
            rivenditore[i].start();
            System.out.println("ID Rivenditore " + rivenditore[i].idRivenditore + " è pronto.");
        }
        try{
            Thread.sleep(500);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Cliente extends Thread{
    Teatro teatro;
    int idCliente;
    int metodoPagamento;
    int numSala = 0;
    int MAX_BIGLIETTI = 5;
    int numBiglietti;
    int assegnaRivenditore;
    public Cliente(Teatro t, int id){
        this.teatro = t;
        this.idCliente = id;
        this.metodoPagamento = (int) (Math.round(Math.random()*2));
        this.numBiglietti = (int)(Math.round(1 + Math.random()*4));
    }

    public void run(){
        try{
            Thread.sleep(Math.round(Math.random()*2*1000));
            teatro.mettiInCoda(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Rivenditore extends Thread{
    int idRivenditore;
    Teatro teatro;
    int tipologiaPagamento;
    boolean libero;
    int lotto;
    public Rivenditore(Teatro t, int id){
        this.idRivenditore = id;
        this.teatro = t;
        this.tipologiaPagamento = (int)(Math.round(Math.random()*2));
        this.libero = true;
        lotto = 20;
    }

    public void run(){
        try{
            Thread.sleep(Math.round(Math.random()*2*1000));
            teatro.vendiBiglietto(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Teatro{
    public Cliente clienteServito;
    public static int nBiglietti = 100;
    public LinkedList<Cliente> attesa = new LinkedList<Cliente>();
    
    public synchronized void mettiInCoda(Cliente c){
        System.out.println("Arriva il cliente: " +c.idCliente);
        attesa.add(c);
        notifyAll();
    }

    public synchronized void vendiBiglietto(Rivenditore r) throws InterruptedException{
        while(!r.libero){
            try{
                System.out.println("Il rivenditore " + r.idRivenditore + " deve attendere.");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        while(r.libero){
            boolean esisteContanti = false;
            for(int i=attesa.size()-1; i>0; i--){
                clienteServito = attesa.get(i);
                if(clienteServito.metodoPagamento == 1){
                    attesa.remove(i);
                    esisteContanti = false;
                    break;
                }
                esisteContanti = true;
            }
            if(esisteContanti){
                clienteServito = attesa.removeFirst();
            }
            r.libero = false;
            System.out.println("Il rivenditore " + r.idRivenditore +" ha servito il cliente " + clienteServito.idCliente);
            if(lotto != 0 && lotto>clienteServito.numBiglietti){
                // Pagamento misto
                if(r.tipologiaPagamento == 1){
                    // Carta di credito
                    if(clienteServito.metodoPagamento == 1){
                        Thread.sleep(Math.round(Math.random()*1*1000));
                        lotto = lotto - clienteServito.numBiglietti;
                        System.out.println("Il cliente "+clienteServito.idCliente+" ha pagato con carta di credito.\nHa acquistato " +clienteServito.numBiglietti + " biglietti.\nRimangono ancora " + lotto + " biglietti al rivenditore " + r.idRivenditore);
                        r.libero = true;
                        notifyAll();
                    }
                    // Contanti
                    else{
                        Thread.sleep(Math.round(Math.random()*3*1000));
                        System.out.println("Il cliente "+clienteServito.idCliente+" ha pagato in contanti.\nHa acquistato " +clienteServito.numBiglietti + " biglietti.\nRimangono ancora " + lotto + " biglietti al rivenditore " + r.idRivenditore);
                        r.libero = true;
                        notifyAll();
                    }
                }
                // Solo carta di credito
                else{
                    Thread.sleep(Math.round(Math.random()*1*1000));
                    System.out.println("Il cliente "+clienteServito.idCliente+" ha pagato con carta di credito.\nHa acquistato " +clienteServito.numBiglietti + " biglietti.\nRimangono ancora " + lotto + " biglietti al rivenditore " + r.idRivenditore);
                    r.libero = true;
                    notifyAll();
                }
            }
            else if(lotto == 0 && nBiglietti >=20){
                System.out.println("Il cliente " + clienteServito.idCliente + " deve attendere il rifornimento.");
                r.libero = false;
                lotto = 20;
                nBiglietti = nBiglietti - lotto;
                r.libero = true;
            }
            else if(nBiglietti == 0){
                System.out.println("Biglietti terminati");
                System.exit(1);
            }
        }
        while(attesa.size() == 0){
            try{
                System.out.println("Il rivenditore non ha clienti da servire. Deve attendere.");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}