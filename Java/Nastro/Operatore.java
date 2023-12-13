package Nastro;

public class Operatore extends Thread {
    int operatoreId;
    Nastri nastri;

    public Operatore(Nastri nastri,int operatoreId){
        this.operatoreId = operatoreId;
        this.nastri = nastri;
    }

    public void run(){
        while(true){
            try{
                nastri.depositabagaglio(this);
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
