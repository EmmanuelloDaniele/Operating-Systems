package AeroGate;
public class Aerei extends Thread{
    private int id_aereo;
    Aeroporto aeroporto;

    public Aerei(int id_aereo, Aeroporto aeroporto){
        this.id_aereo= id_aereo;
        this.aeroporto = aeroporto;
    }

    public void run(){
        while(true){
            aeroporto.imbarcoGate(id_aereo);

            try {
                Thread.sleep((int)Math.random()*50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
