package cinqueFilosofi;

public class Bacchetta {
    public boolean bacchettaLibera;
    public int bacchettaNum;

    public Bacchetta(int num) {
        bacchettaNum = num;
        bacchettaLibera = true;
    }

    public synchronized void prendi() {
        String nomeThread = Thread.currentThread().getName(); //Thread che usa la bacchetta

        while(!bacchettaLibera){ //Finché la bacchetta non é libera
            try{
                System.out.println(nomeThread + " sta cercando di prendere la bacchetta..");
                wait();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        bacchettaLibera = false;
    }

    public synchronized void rilascia() {
        bacchettaLibera = true;
        notifyAll(); //A quanto pare quando il thread rilascia l'uso della risorsa condivisa(syncronizzata) devo notificare il cambiamento;
    }
}
