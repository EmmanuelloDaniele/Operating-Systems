import java.util.LinkedList;

/* Esame del 11/06/20
 * Presso un laboratorio di analisi cliniche in cui operano J analisti, 
 * i clienti vengono gestiti utilizzando un’unica lista di prenotazione di lunghezza infinita. 
 * Se l’analista j è disponibile, il cliente che si trova in testa alla lista si accomoda nella postazione di j. 
 * Il laboratorio offre due tipi di analisi: tampone faringeo, esame del sangue. I clienti che devono fare il tampone, 
 * al termine dell’esame (si simuli un tempo casuale) pagheranno e lasceranno lo studio (non occorre gestire il pagamento!). 
 * I clienti che devono fare l’esame del sangue, 
 * al termine del prelievo sono invitati ad aspettare un tempo T (casuale) di coagulazione in un’apposita sala S, 
 * liberando così l’analista che può nel frattempo esaminare altri clienti. Passato il tempo di coagulazione, 
 * il cliente è pronto a lasciare lo studio ma dovrà restare in attesa finché il suo analista non sia di nuovo libero. 
 * Quando questo si libererà, il cliente che era in attesa nella sala S avrà priorità sugli altri pazienti
 * presenti nella lista di prenotazione, metterà un cerotto, pagherà e lascerà lo studio.
 * Si modelli lo scenario descritto mediante thread in linguaggio Java usando il costrutto monitor. 
 * Si descriva la sincronizzazione tra thread, discutendo anche se la soluzione proposta può presentare rinvio indefinito o deadlock. 
 * In tal caso, si propongano delle soluzioni implementative per evitare i due fenomeni.
 * 
 * Idea di risoluzione:
 * 
 * Main
 * Clienti
 * Analisti
 * Lista (Con metodi sync)
 * 
 *              Ogni analista ha un codice:
 *              Analista 1 = 1
 *              Analista 2 = 2
 *              Analista 3 = 3
 *              
 *              Ogni cliente ha codice 0 se arriva per la prima volta
 *              codice dell'analista se aspetta il cerotto 
 *              Cliente == 0
 *
 */

 public class Main{
    public static void main(String args[]){
        int n_analisti = 3;
        Analisti analista[] = new Analisti[n_analisti];
        int n_clienti = 100;
        Clienti cliente[] = new Clienti[n_clienti];
        Coda coda = new Coda();

        for (int i = 0; i < n_analisti; i++) {
            analista[i] = new Analisti(i+1, coda);
            analista[i].start();
        }

        for (int i = 0; i < n_clienti; i++) {
            try {
                Thread.sleep(((int)Math.random())*100);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cliente[i] = new Clienti(0, coda);
            cliente[i].start();
        }
    }
 }

 