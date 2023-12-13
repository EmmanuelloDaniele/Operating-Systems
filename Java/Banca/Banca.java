import java.util.LinkedList;
public class Banca{
    public static void main(String args[]){
        Porta porta = new Porta();
        Cliente cliente[] = new Cliente[10];
        Bussola bussola[] = new Bussola[2];

        for(int i=0; i<10; i++){
            try{
                cliente[i] = new Cliente(i, porta);
                Thread.sleep(200);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
            cliente[i].start();
        }
        
        for(int i=0; i<2; i++){
            bussola[i] = new Bussola(i, porta);
            bussola[i].start();
        }
    }
}

class Cliente extends Thread{
    int idCliente;
    int posseggoMetallo; //0 no, 1 sì
    Porta porta;
    
    public Cliente(int idCliente, Porta porta){
        this.idCliente = idCliente;
        this.porta = porta;
        this.posseggoMetallo = (int)(Math.random()*2);
    }
    
    public void run(){
        try{
            porta.mettiInCoda(this);
            Thread.sleep(100);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Bussola extends Thread{
    int idBussola;
    Porta porta;
    
    public Bussola(int idBussola, Porta porta){
        this.idBussola = idBussola;
        this.porta = porta;
    }
    
    public void run(){
        while(true){
            try{
                porta.gestioneCoda(this);
                Thread.sleep(100);
                
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
}

class Porta{
    LinkedList<Cliente> codaIngresso = new LinkedList<Cliente>();
    LinkedList<Cliente> codaMetalDetector = new LinkedList<Cliente>();
    LinkedList<Cliente> codaUscita = new LinkedList<Cliente>();
    Cliente cliente;
    int MAX_CLIENTI = 5;
    
    public synchronized void mettiInCoda(Cliente c){
        while(codaIngresso.size()<=MAX_CLIENTI){
            System.out.println("I clienti entrano");
            codaIngresso.add(c);
            notifyAll();
        }
        //Siamo nello stato in cui un cliente non ha metalli con sè
        if(c.posseggoMetallo == 0){
            cliente = codaIngresso.remove();
            codaMetalDetector.add(cliente);
            System.out.println("I clienti non hanno oggetti di metallo");
            notifyAll(); 
        }
        else{
            System.out.println("I clienti hanno oggetti di metallo, non possono entrare");
        }
        while(codaMetalDetector.size()>0){
            cliente = codaMetalDetector.removeFirst();
            try{
                Thread.sleep(200);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            System.out.println("I clienti hanno terminato, possono recarsi all'uscita'");
            codaUscita.add(cliente);
            notifyAll();
        }
    }
    
    public synchronized void gestioneCoda(Bussola b){
        while(codaIngresso.size()==0 && codaMetalDetector.size() == 0){
            try{
                System.out.println("Nessun cliente presente");
                wait();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
        while(codaUscita.size()>0){
            System.out.println("Il cliente è andato via");
            cliente = codaUscita.removeFirst();
        }
    }
}

