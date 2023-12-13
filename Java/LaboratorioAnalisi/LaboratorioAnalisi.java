import java.util.LinkedList;

public class LaboratorioAnalisi{
    public static void main(String args[]){
        int nClienti = 50;
        int nAnalisti = 5;
        Cliente cliente[] = new Cliente[nClienti];
        Analista analista[]= new Analista[nAnalisti];
        Laboratorio lab = new Laboratorio();

        for(int i=0; i<nClienti; i++){
            cliente[i] = new Cliente(lab, i);
            cliente[i].start();
        }

        for(int i=0; i<nAnalisti; i++){
            analista[i] = new Analista(lab, i);
            analista[i].start();
        }

        try{
            Thread.sleep(1000);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Analista extends Thread{
    int idAnalista;
    Laboratorio lab;
    int disponibile; //1 occupato, 0 disponibile
    public Analista(Laboratorio lab, int idAnalista){
        this.idAnalista = idAnalista;
        this.lab = lab;
        this.disponibile = 0;
    }

    public void run(){
        try{
            Thread.sleep((int) (Math.random() *500));
            lab.serviCliente(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Cliente extends Thread{
    int idCliente;
    Laboratorio lab;
    int scelta; //0 tampone, 1 sangue
    int assegna;
    public Cliente(Laboratorio lab, int idCliente){
        this.idCliente = idCliente;
        this.lab = lab;
        this.scelta = (int) (Math.round(Math.random()*1));
    }

    public void run(){
        try{
            Thread.sleep((int) (Math.random() *500));
            lab.mettiInCoda(this);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}

class Laboratorio{
    public LinkedList<Cliente> coda = new LinkedList<Cliente>();
    public LinkedList<Cliente> sala = new LinkedList<Cliente>();
    Cliente clienteServito;
    static int contatore=0; //Serve per prevenire deadlock o posticipazione indefinita

    public synchronized void mettiInCoda(Cliente c){
        try{
            Thread.sleep((int) (Math.random() *500));
            coda.add(c);
            System.out.println("Il cliente " +c.idCliente+ " è in attesa.");
            wait();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public synchronized void serviCliente(Analista a){
        //Qui siamo nel caso in cui la coda è vuota
        while(contatore<50){
            if(coda.size()==0 && sala.size()==0){
                try{
                    Thread.sleep((int) (Math.random() *500));
                    System.out.println("L'analista " +a.idAnalista+ " è in attesa di clienti.");
                    wait();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
            if(sala.size() == 0 && coda.size()>0){
                //Verifico se un'analista è disponibile
                if(a.disponibile == 0){
                    //Siamo nel caso in cui la sala è vuota
                    clienteServito = coda.removeFirst();
                    a.disponibile = 1;
                    clienteServito.assegna = a.idAnalista;
                    //Il cliente vuole fatto un tampone
                    if(clienteServito.scelta == 0){
                        try {
                            Thread.sleep((int)Math.random()*2*1000);
                            System.out.println("Il cliente " +clienteServito.idCliente+ " è stato servito dall'analista " + clienteServito.assegna +", facendogli un tampone. Ha pagato ed è pronto per andarsene.");
                            a.disponibile = 0;
                            contatore++;
                            notifyAll();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //Il cliente vuole fatto un'analisi del sangue
                    else{
                        try{
                            coda.remove(clienteServito);
                            Thread.sleep((int)Math.random()*1*1000);
                            sala.add(clienteServito);
                            a.disponibile=0; 
                            Thread.sleep((int)Math.random()*3*1000);
                            System.out.println("Il cliente " + clienteServito.idCliente + ", servito dall'analista  "+clienteServito.assegna+" , deve andare in sala per attendere l'esito dell'esame del sangue.");
                            while(!(a.idAnalista == clienteServito.assegna && a.disponibile==0)){
                                try{
                                    Thread.sleep((int) (Math.random() *500));
                                    System.out.println("Il cliente " + clienteServito.idCliente +" sta ancora attendendo in sala.");
                                    wait();
                                }
                                catch(Exception ex){
                                    ex.printStackTrace();
                                }
                            }
                            a.disponibile = 1;
                            Thread.sleep((int)Math.random()*3*1000);
                            System.out.println("Il cliente " + clienteServito.idCliente +" ha ricevuto le analisi da parte dell'analista"+clienteServito.assegna+".");
                            sala.remove(clienteServito);
                            a.disponibile = 0;
                            contatore++;
                            notifyAll();
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
        System.exit(1);
    }
}