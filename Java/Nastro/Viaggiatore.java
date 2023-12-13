package Nastro;

public class Viaggiatore extends Thread{
    int viaggiatoreId;
    Nastri nastri;

    public Viaggiatore(Nastri nastri, int viaggiatoreId){
        this.viaggiatoreId=viaggiatoreId;
        this.nastri = nastri;
    }

    public void run(){
        try{
            nastri.ritiraBagaglio(this);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
