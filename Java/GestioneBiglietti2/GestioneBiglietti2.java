import java.util.LinkedList;

public class GestioneBiglietti2 {
    public static void main(String args[]){
        int numClienti = 10;
        int numRivenditori = 5;
        Cliente cliente[] = new Cliente[numClienti];
        Rivenditore rivenditore[] = new Rivenditore[numRivenditori];
        Teatro teatro = new Teatro();

        for(int i=0; i<numClienti; i++){
            cliente[i] = new Cliente(teatro, i);
            cliente[i].start();
            System.out.println("ID cliente " + cliente[i].idCliente + " è stato creato");
        }
        
        for(int i=0; i<numRivenditori; i++){
            rivenditore[i] = new Rivenditore(teatro, i);
            rivenditore[i].start();
            System.out.println("ID Rivenditore " + rivenditore[i].idRivenditore + " è stato creato");
        }

        try{
            Thread.sleep(1000);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Cliente extends Thread{
    boolean servito;
    int idCliente;
    Teatro teatro;
    int tipologiaPagamento; //se è 1, pagamento digitale
    int assegnaRivenditore;
    int numBiglietti;
    public Cliente(Teatro t, int id){
        idCliente = id;
        teatro = t;
        tipologiaPagamento = (int) (Math.round(Math.random()*1));
        numBiglietti = 1+(int) (Math.round(Math.random()*3));
        servito = false;
    }

    public void run(){
        try {
            Thread.sleep((int) (Math.random() *500));
            teatro.mettiInCoda(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Rivenditore extends Thread{
    int idRivenditore;
    Teatro teatro;
    int tipologiaPagamento; //se è 1, pagamento misto. Se è 0, solo digitale
    int libero; //Se è 1, allora è occupato
    int lotto;
    public Rivenditore(Teatro t, int id){
        idRivenditore = id;
        teatro = t;
        tipologiaPagamento = (int) (Math.random()*2);
        libero = 0;
        lotto = 50;
        Teatro.nBiglietti -= lotto;
    }

    public void run(){
        try {
            Thread.sleep((int) (Math.random() *500));
            teatro.vendiBiglietto(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Teatro{
    public static int nBiglietti = 1000;
    static int contatore = 0;
    public LinkedList<Cliente> codaAttesa = new LinkedList<Cliente>();
    Cliente clienteServito;
    boolean esisteContanti = true;
    public synchronized void mettiInCoda(Cliente c){
        try{
            Thread.sleep((int) (Math.random() *500));
            System.out.println("Arriva il cliente: " +c.idCliente);
            codaAttesa.add(c);
            wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public synchronized void vendiBiglietto(Rivenditore r) throws InterruptedException{  
        while(contatore<10){
            System.out.println(contatore);
            if(codaAttesa.size() == 0){
                try{
                    Thread.sleep((int) (Math.random() *500));
                    System.out.println("Il rivenditore" +r.idRivenditore+ " è in attesa di clienti.");
                    wait();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            if(r.lotto>0){
                if(r.libero == 1){
                    try{
                        Thread.sleep((int) (Math.random() *500));
                        System.out.println("Il rivenditore " + r.idRivenditore + " deve attendere.");
                        wait();
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
                else{
                    r.libero = 1;
                    //Siamo nel caso in cui il pagamento è misto o solo digitale
                    if(r.tipologiaPagamento == 1){ //Misto
                        for (int i=codaAttesa.size()-1; i>0; i--){
                            clienteServito = codaAttesa.get(i);
                            if(clienteServito.tipologiaPagamento == 1){ //Pagamento digitale
                                esisteContanti = false;
                                clienteServito = codaAttesa.remove(i);
                                clienteServito.assegnaRivenditore = r.idRivenditore;
                                System.out.println("Cipolle");
                                break;
                            }
                        }
                        if(esisteContanti && !clienteServito.servito){
                            try{
                                r.lotto -= clienteServito.numBiglietti;
                                Thread.sleep(Math.round(Math.random()*3*1000));
                                System.out.println("Il cliente "+clienteServito.idCliente+" ha pagato in contanti al rivenditore "+clienteServito.assegnaRivenditore+".\nHa acquistato " +clienteServito.numBiglietti+" biglietti.\nRimangono ancora " + r.lotto + " biglietti al rivenditore " + r.idRivenditore);
                                r.libero = 0;
                                clienteServito.servito = true;
                                contatore++;
                                notifyAll();
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else if(!esisteContanti && !clienteServito.servito){
                            try{
                                r.lotto -= clienteServito.numBiglietti;
                                Thread.sleep(Math.round(Math.random()*1*1000));
                                System.out.println("Il cliente "+clienteServito.idCliente+" ha pagato con carta di credito al rivenditore "+clienteServito.assegnaRivenditore+".\nHa acquistato " +clienteServito.numBiglietti + " biglietti.\nRimangono ancora " + r.lotto + " biglietti al rivenditore " + r.idRivenditore);
                                r.libero = 0;
                                clienteServito.servito = true;
                                contatore++;
                                notifyAll();                          
                            }
                            catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }
                    //Solo pagamento digitale
                    else if(r.tipologiaPagamento==0 && !clienteServito.servito){
                        for (int i=codaAttesa.size()-1; i>0; i--){
                            clienteServito = codaAttesa.get(i);
                            if(clienteServito.tipologiaPagamento == 1){
                                clienteServito = codaAttesa.remove(i);
                                clienteServito.assegnaRivenditore = r.idRivenditore;
                                break;
                            }
                        }
                        try{
                            r.lotto -= clienteServito.numBiglietti;
                            Thread.sleep(Math.round(Math.random()*1*1000));
                            System.out.println("Il cliente "+clienteServito.idCliente+" ha pagato con carta di credito al rivenditore "+clienteServito.assegnaRivenditore+".\nHa acquistato " +clienteServito.numBiglietti + " biglietti.\nRimangono ancora " + r.lotto + " biglietti al rivenditore " + r.idRivenditore);
                            r.libero = 0;
                            clienteServito.servito = true;
                            contatore++;
                            notifyAll(); 
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
                notifyAll(); 
            }
            if(r.lotto == 0 && nBiglietti>=50){
                System.out.println("Il cliente " + clienteServito.idCliente + " deve attendere il rifornimento.");
                r.libero = 1;
                r.lotto = 50;
                nBiglietti -= r.lotto;
                r.libero = 0;
            }
            else if(r.lotto<clienteServito.numBiglietti){
                try{
                    Thread.sleep((int) (Math.random() *500));
                    System.out.println("Il cliente " + clienteServito.idCliente + " deve attendere il rifornimento.");
                    wait();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else if(nBiglietti == 0){
                System.out.println("Biglietti terminati");
                System.exit(1);
            }
        }
        System.exit(1);
    }
}