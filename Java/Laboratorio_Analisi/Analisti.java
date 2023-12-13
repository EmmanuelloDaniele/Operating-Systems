package Laboratorio_Analisi;

public class Analisti extends Thread {
    int id;
    Coda coda;

    public Analisti(int id, Coda coda){
        this.id = id;
        this.coda = coda;
    }

    public void run(){
        while(true){

            coda.serviCliente(id);

            try { //Sleep mentre serve il cliente
               Thread.sleep((int)Math.random()*500); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
