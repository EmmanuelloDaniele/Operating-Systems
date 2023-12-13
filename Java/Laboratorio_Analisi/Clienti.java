package Laboratorio_Analisi;

public class Clienti extends Thread{
    int id;
    Coda coda;
    int tipo_esame; 

    public Clienti(int id, Coda coda){
        this.coda = coda;
        this.id = id;

        if(Math.random() < 0.5)
            tipo_esame = 0; //Tampone
        else
            tipo_esame = 1; //Analisi sangue
    }

    public void run(){
        System.out.println("Il cliente "+id+" si mette in coda");
        coda.mettiInCoda(this);
    }

    public void aspetta(){
        try {
            System.out.println("Il cliente aspetta che il sangue si coaguli");
            Thread.sleep((int)Math.random()*500);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        coda.mettiInCoda(this);       
    }
}
