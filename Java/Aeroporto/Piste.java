package Aeroporto;

public class Piste extends Thread {
    private int id;
    private int slot = 5;
    private Torre torre;

    public Piste(int id, Torre torre){
        this.id = id;
        this.torre = torre;
    }

    public void run(){
        while(true){
            try {
                Thread.sleep((int)Math.random()*300);
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            torre.effettuaAtterraggioDecollo(id);
        }
    }

    public int getID(){
        return id;
    }
}
