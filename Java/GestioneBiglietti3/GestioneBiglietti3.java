import java.util.LinkedList;

public class GestioneBiglietti3{
    public static void main(String args[]){
        int nClienti = 10;
        int nVenditori = 5;

        Biglietteria biglietteria = new Biglietteria();
        Cliente cliente[] = new Cliente[nClienti];
        Venditore venditore[] = new Venditore[nVenditori];

        for(int i=0; i<nClienti; i++){
            cliente[i] = new Cliente(biglietteria, i);
            cliente[i].start();
        }

        for(int i=0; i<nVenditori; i++){
            venditore[i] = new Venditore(biglietteria, i);
            venditore[i].start();
        }

        try{
            Thread.sleep((int)Math.random()*500);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Cliente extends Thread{
    // Pagamento contanti = 0, Digitale = 1
    int idCliente, bigliettiRichiesti, sceltaPagamento, assegna;
    Biglietteria biglietteria;
    public Cliente(Biglietteria biglietteria, int idCliente){
        this.biglietteria = biglietteria;
        this.idCliente = idCliente;
        bigliettiRichiesti = (int) (1+(Math.round(Math.random()%3)));
        sceltaPagamento = (int) (Math.random()*1);
        assegna = 0;
    }

    public void run(){
        try{
            Thread.sleep((int)Math.random()*500);
            biglietteria.mettiInCoda(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Venditore extends Thread{
    //Disponibile = 0, Occupato = 1
    //Pagamento misto = 0, Digitale = 1
    int idVenditore, lotto, metodoPagamento, disponibile;
    Biglietteria biglietteria;

    public Venditore(Biglietteria biglietteria, int idVenditore){
        this.biglietteria = biglietteria;
        this.idVenditore = idVenditore;
        lotto = 20;
        metodoPagamento = (int) Math.random()*1;
        disponibile = 0;
        Biglietteria.nBigliettiBiglietteria -= lotto;
    }

    public void run(){
        try{
            Thread.sleep((int)Math.random()*500);
            biglietteria.serviCliente(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Biglietteria{
    Cliente clienteServito;
    static int nBigliettiBiglietteria = 100;
    static int contatore = 0;
    LinkedList<Cliente> coda = new LinkedList<Cliente>();
    public synchronized void mettiInCoda(Cliente c){
        try{
            System.out.println("Il cliente " + c.idCliente + " si trova in coda.");
            Thread.sleep((int) (Math.random() *500));
            coda.add(c);
            wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    public synchronized void serviCliente(Venditore v) {
        while(true){
            if(coda.size() == 0){
                System.out.println("La coda è vuota, nessun cliente disponibile. Il venditore " +v.idVenditore+ " deve attendere");
                try {
                    Thread.sleep((int) (Math.random() *500));
                    System.out.println("Il rivenditore" +v.idVenditore+ " è in attesa di clienti.");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(v.lotto>0){
                //Siamo nel caso in cui il venditore x è disponibile
                if(v.disponibile == 0){
                    // L'errore sembra cominciare dopo aver posto v.disponibile = 1
                    v.disponibile = 1;
                    //Siamo nel caso in cui un botteghino che accetta metodo di pagamento misto
                    if(v.metodoPagamento == 0){
                        for(int i=coda.size()-1; i>0; i--){
                            clienteServito = coda.get(i);
                            //Il cliente paga in contanti
                            if(clienteServito.sceltaPagamento == 0){
                                try{
                                    v.disponibile = 1;
                                    clienteServito = coda.remove(i);
                                    clienteServito.assegna = v.idVenditore;
                                    v.lotto -= clienteServito.bigliettiRichiesti;
                                    System.out.println("Il cliente " + clienteServito.idCliente + " ha pagato in contanti al venditore " +clienteServito.assegna+" . Ha acquistato "+clienteServito.bigliettiRichiesti+" biglietti.");
                                    System.out.println("Al venditore gli rimangono " +v.lotto);
                                    Thread.sleep((int)Math.random()*1*500);
                                    v.disponibile = 0;
                                    notifyAll();
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                            //Il cliente paga con carta di credito
                            else{
                                try{
                                    v.disponibile = 1;
                                    clienteServito = coda.remove(i);
                                    clienteServito.assegna = v.idVenditore;
                                    v.lotto -= clienteServito.bigliettiRichiesti;
                                    System.out.println("Il cliente " + clienteServito.idCliente + " ha pagato con carta di credito al venditore " +clienteServito.assegna+" . Ha acquistato "+clienteServito.bigliettiRichiesti+" biglietti.");
                                    System.out.println("Al venditore gli rimangono " +v.lotto);
                                    Thread.sleep((int)Math.random()*1*500);
                                    v.disponibile = 0;
                                    notifyAll();
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    //Siamo nel caso in cui un botteghino che accetta metodo di pagamento digitale
                    else{
                        for(int i=coda.size()-1; i>0; i--){
                            clienteServito = coda.get(i);
                            if(clienteServito.sceltaPagamento == 1){
                                try{
                                    v.disponibile = 1;
                                    clienteServito = coda.remove(i);
                                    clienteServito.assegna = v.idVenditore;
                                    v.lotto -= clienteServito.bigliettiRichiesti;
                                    System.out.println("Il cliente " + clienteServito.idCliente + " ha pagato con carta di credito al venditore " +clienteServito.assegna+" . Ha acquistato "+clienteServito.bigliettiRichiesti+" biglietti.");
                                    System.out.println("Al venditore gli rimangono " +v.lotto);
                                    Thread.sleep((int)Math.random()*1*500);
                                    v.disponibile = 0;
                                    notifyAll();
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                }
                //Siamo nel caso in cui il venditore x non è disponibile
                else{
                    try{
                        Thread.sleep((int) (Math.random() *500));
                        System.out.println("Il rivenditore " + v.idVenditore + " deve attendere.");
                        wait();
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
            else if(v.lotto == 0 && nBigliettiBiglietteria>=10){
                System.out.println("Il cliente " + clienteServito.idCliente + " deve attendere il rifornimento.");
                v.disponibile = 1;
                v.lotto = 20;
                nBigliettiBiglietteria -= v.lotto;
                v.disponibile = 0;
            }
            else if(v.lotto<clienteServito.bigliettiRichiesti){
                try{
                    Thread.sleep((int) (Math.random() *500));
                    System.out.println("Il cliente " + clienteServito.idCliente + " deve attendere il rifornimento.");
                    wait();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            else if(nBigliettiBiglietteria == 0){
                System.out.println("Biglietti terminati, botteghino chiuso.");
                System.exit(1);
            }
        }
    }
}