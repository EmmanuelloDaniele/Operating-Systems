/*Esame del 1/02/22
Un aeroporto dispone di 2 piste, ciascuna utilizzata per il decollo e per l'atterraggio
Ogni pista ha un corridoio di accesso di capienza max di aerei in partenza.
Gli erei in atterraggio sono messi in un'unica coda virtuale di  lunghezza infinita gestita dalla torre di controllo
Per ragioni di sicurezza gli arei che debbono atterrare hanno prioritá di accesso alle piste
Sono inoltre avvantaggiati gli atterraggi di voli internazionali rispetto a quelli nazionali
Si modelli tramite java e uso di monitor


Piste = 2 (Per decollo e atterraggio)
Lista di accesso alla pista 5 posti
Lista di atterraggio infinita e con prioritá sul decollo                / Atterra
Atterraggi internazionali con prioritá sui voli nazionali  ----> Aereo                 Nazionale
                                                                        \ Decolla   /
                                                                                    \
                                                                                        Internazionale

*/                                                                     


package Aeroporto;

public class Main {
    public static void main(String args[]){
    
    int n_aerei = 100;
    int max_coda = 5;
    Aerei aereo[] = new Aerei[n_aerei];
    Piste pista[] = new Piste[2];
    Torre torre = new Torre(max_coda);

    for(int i=0; i<n_aerei; i++){ //Simulo lo spawn degli aerei dilazionato
        try {
            Thread.sleep((int)Math.random()*500);
        } catch (Exception e) {
            e.printStackTrace();
        }
        aereo[i] = new Aerei(i, torre);
        aereo[i].start();
    }
    for(int j=0; j<2; j++){
        pista[j] = new Piste(j+1, torre);
        pista[j].start();
    }
    }
}
