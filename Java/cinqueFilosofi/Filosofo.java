package cinqueFilosofi;

public class Filosofo extends Thread {
    public int idFilosofo;
    private final int nSecondi = 2;
    private Bacchetta bacchettaSx;
    private Bacchetta bacchettaDx;

    public Filosofo(int id, Bacchetta sx, Bacchetta dx){
        idFilosofo = id;
        bacchettaSx = sx;
        bacchettaDx = dx;
        this.setName("Filosofo #" + idFilosofo);
    }

    public void run(){
        while(true){
            this.pensa();
            this.prendiBacchette();
            this.mangia();
            this.rilasciaBacchette();
        }
    }

    private void pensa(){
        System.out.println(this.getName()+" sta pensando..");

        try{
            Thread.sleep((int)(Math.random()*nSecondi*1000));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void prendiBacchette(){
        System.out.println(this.getName() + " sta cercando di prendere le baccehetta num "+bacchettaSx.bacchettaNum +" alla sua sx");
        bacchettaSx.prendi();
        System.out.println(this.getName() + " ha presola bacchetta sx "+bacchettaSx.bacchettaNum);
        bacchettaDx.prendi();
        System.out.println(this.getName() + " ha presola bacchetta dx "+bacchettaDx.bacchettaNum);
    }
    private void mangia(){
        System.out.println(this.getName()+" sta mangiando..");

        try{
            Thread.sleep((int)(Math.random()*nSecondi*1000));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void rilasciaBacchette(){
        System.out.println(this.getName() + " ha finito di mangiare e lascia le bacchette ");
        bacchettaDx.rilascia();
        bacchettaSx.rilascia();
    }

}
