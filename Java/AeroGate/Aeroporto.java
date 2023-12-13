package AeroGate;

import java.util.LinkedList;

public class Aeroporto {
    private int n_varchi;
    private int t_coda;
    private LinkedList <Passeggeri> varcoFF = new LinkedList<Passeggeri>();
    private LinkedList<Passeggeri> varchi[] = new LinkedList[n_varchi - 1]; //Array di linkedList, é legale?
    //L'array contiene tutti i varchi che sono linkedlist che conterranno la coda di gente al varco

    //LinkedList<Passeggeri> gate = new LinkedList<Passeggeri>();

    public Aeroporto(int n_varchi, int t_coda) {
        this.n_varchi = n_varchi;
        this.t_coda = t_coda;

        for(int i=0; i<n_varchi - 1; i++){
            varchi[i] = new LinkedList<Passeggeri>();
        }
    }

    public synchronized void varcoPasseggeri(Passeggeri passeggero) {
        if(passeggero.getTipo() == 2){ //Passeggero ff
            varcoFF.add(passeggero);
        }
        else if(passeggero.getTipo() == 1){ //Passeggero immediato
            int j;
            for(j=0; j< n_varchi - 1; j++){
                if(varchi[j].size() < t_coda){
                    varchi[j].add(passeggero);
                    break;
                }
                else{
                    varcoFF.add(passeggero);
                }
            }
        }
        else{   //Passeggero comune
            int n = (int)(Math.random() * (3 - 0) + 0); //Random tra valori min e max secondo algoritmo (max-min)min
            varchi[n].add(passeggero);
        }
        notifyAll();
    }

    //public synchronized void gatePasseggeri(Passeggeri passeggero) {
        
    //}

    public synchronized void imbarcoGate(int id_aereo) {
       boolean vuoto = true;
       boolean prioritá = false;

        for(int i=0; i<n_varchi -1; i++){
            if(varchi[i].size() != 0)
                vuoto = false;
        }

        if(varcoFF.size()==0 && vuoto == true){
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(varcoFF.size() != 0){
            int ff_size = varcoFF.size();
            for(int i = 0; i< ff_size; i++){
                if(varcoFF.get(i).getBiglietto() == id_aereo){
                    varcoFF.remove(i);
                    break;
                }
            }
        }
        else{
            for(int i=0; i<n_varchi -1; i++){
                if(varchi[i].size() != 0){
                    for(int j = 0; j<varchi[i].size(); j++){
                        if(varchi[i].get(j).getTipo() == 2){
                            if(varchi[i].get(j).getBiglietto() == id_aereo){
                                varchi[i].remove(j);
                                prioritá = true;
                                break;
                            }
                        }
                    }   
                }
            }

            if(prioritá == false){
                for(int i=0; i<n_varchi -1; i++){
                    if(varchi[i].size() != 0){
                        if(varchi[i].get(i).getBiglietto() == id_aereo){
                            varchi[i].remove(i);
                            break;
                        }
                         
                    }
                        
                } 
            }  
        }
    }

}
