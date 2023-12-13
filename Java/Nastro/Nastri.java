package Nastro;

import java.util.LinkedList;

public class Nastri {
    LinkedList <Integer> nastroA;
    LinkedList <Integer> nastroB;
    int posti;

    public Nastri(int p){
        nastroA = new LinkedList<Integer>();
        nastroB = new LinkedList<Integer>();
        posti = p;
    }

    public synchronized void ritiraBagaglio(Viaggiatore v) {
        if((nastroA.size() == 0) && (nastroB.size() == 0)){
            try{
                System.out.println("Il viaggiatore n "+ v.viaggiatoreId + " non ha bagagli da ritirare");
                wait(); //In attesa di una nofity dall'operatore
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroA.size()>0){
            try{
                nastroA.removeFirst();
                System.out.println("Il viaggiatore n "+ v.viaggiatoreId + " ritira un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroB.size()>0){
            try{
                nastroB.removeFirst();
                System.out.println("Il viaggiatore n "+ v.viaggiatoreId + " ritira un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public synchronized void depositabagaglio(Operatore o) {
        if((nastroA.size() >= posti) && (nastroB.size() >= posti)){
            try{
                System.out.println("L'operatore n "+ o.operatoreId + " non puó depositare bagagli");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroA.size() < posti){
            try{
                nastroA.addFirst(null);
                System.out.println("L'operatore n "+ o.operatoreId + " deposita un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(nastroB.size() < posti){
            try{
                nastroB.addFirst(null);
                System.out.println("L'operatore n "+ o.operatoreId + " deposita un bagaglio");
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        notifyAll(); 
    }

}
