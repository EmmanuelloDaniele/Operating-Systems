package Aeroporto;

public class Aerei extends Thread {
    private int id;
    private Torre torre;
    private int stato; //0 = Decollo, 1 = Atterraggio
    private int provenienza = 0; //0 = Nazionale, 1 = Internazionale

    public Aerei(int id, Torre torre){
        this.id = id;
        this.torre = torre;

        if(Math.random() < 0.5) //Imposto randomicamente lo stato
            stato = 0;
        else
            stato = 1;

        if(stato == 1)
            if(Math.random() < 0.5) //Se l'aereo sta atterrando mi devo interessare della sua provenienza
                provenienza = 0;
            else
                provenienza = 1;
    }

    public void run(){
        torre.richiediAtterraggioDecollo(this);
    }

    public int getID(){
        return id;
    }

    public int getStato(){
        return stato;
    }
    
    public int getProvenienza(){
    return provenienza;
}
}
