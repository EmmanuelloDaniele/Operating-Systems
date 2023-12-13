package AeroGate;

/*
 * Aeroporto che fa salire sugli aerei i passeggeri I passeggeri sono di tre tipi: 
 * comuni, imbarco immediato e frequent flyers. Ci sono N Varchi dedicati ai comuni
 * e agli imbarchi immediati + 1 Singolo Varco dedicato ai Frequent Flyers
   I passeggeri comuni vanno sempre e solo nei varchi dedicati a loro
   Se i passeggeri con imbarco immediato non trovano Varchi con una coda inferiore a T,
   allora vanno nel varco dedicato ai frequent flyers. In ogni varco hanno più priorità i FF, 
   poi Imbarco Immediato infine i comuni. 
   Successivamente ogni passeggero va al suo gate disegnato (a seconda dell'aereo che lui deve prendere) per imbarcarsi. 
   In ogni Gate va rispettata la stessa priorità citata in precedenza.
 * Esame del 22/06/22
 */


public class Main {
    public static void main(String args[]){
        int n_aerei = 10; //Consumer
        int n_passeggeri = 500; //Producer
        Aerei aereo[] = new Aerei[n_aerei];
        Passeggeri passeggero[] = new Passeggeri[n_passeggeri];
        int n_varchi = 5; //4 comuni e Immediati e 1 FF.
        int t_coda = 50; // Valore T per gli immediati.
        Aeroporto aeroporto = new Aeroporto(n_varchi, t_coda);

        for(int i=0; i<n_aerei; i++){
            aereo[i] = new Aerei(i+1, aeroporto);
            aereo[i].start();
        }
        for(int j=0; j<n_passeggeri; j++){
            try {
                Thread.sleep((int)Math.random()*200);
            } catch (Exception e) {
                e.printStackTrace();
            }
            passeggero[i] = new Passeggeri(j, aeroporto);
            passeggero[i].start();
        }
    }
}
