package AeroGate;

public class Passeggeri extends Thread {
    private int id_passeggero;
    private int tipo_passeggero; //0 Comune, 1 Immediato, 2 FF
    private int id_biglietto; //Corrispondente all'id dell'aereo da prendere
    Aeroporto aeroporto;

    public Passeggeri(int id, Aeroporto aeroporto){
        id_passeggero = id;
        this.aeroporto = aeroporto;

        int comodo = (int)Math.random() * 10;
        
        if(comodo > 8)
            tipo_passeggero = 2; //FF
        else if(comodo> 5 && comodo < 9)
            tipo_passeggero = 1; //Fast
        else
            tipo_passeggero = 0; //Common
    }

    public void run(){
        aeroporto.varcoPasseggeri(this);

        try {
            Thread.sleep((int)Math.random()*300);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //aeroporto.gatePasseggeri(this);
    }

    public int getID(){
        return id_passeggero;
    }
    public int getTipo(){
        return tipo_passeggero;
    }
    public int getBiglietto()[
        return id_biglietto;
    ]
}

