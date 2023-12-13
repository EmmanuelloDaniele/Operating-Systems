package Biglietti_stadio;

/*
 * Si modelli il sistema informatico per la gestione della biglietteria di uno stadio.
Per ciascun evento, vengono messi in vendita un numero TOT di biglietti totali che possono essere acquistati presso N diverse rivendite. 
Ciascun cliente può acquistare fino ad un massimo di MAX biglietti [e può scegliere se pagare in contanti o tramite carta di credito
 (si simuli un tempo per transazione più breve nel secondo caso rispetto al primo)]. A ciascuna rivendita verrà assegnato inizialmente un lotto di L biglietti (L << TOT); 
 se questi si esauriscono, la rivendita potrà rifornirsi, a patto che rimangano ancora biglietti disponibili.
[Alcune rivendite accettano solo il pagamento mediante carta di credito, mentre altre accettano entrambi i tipi di pagamento, 
ma daranno precedenza ai clienti che pagano con carta di credito rispetto a quelli che pagano in contanti.] Si descriva la sincronizzazione tra thread, 
discutendo anche se la soluzione proposta può presentare rinvio indefinito o deadlock. In tal caso, si propongano
delle soluzioni implementative per evitare i due fenomeni.
 */

public class Main {
    public static void main(String args[]){
        private int n_rivendite = 10;
        private int tot_biglietti = 500;
        Rivendite rivendita[i] = new Rivendite[10];

        Biglietteria biglietteria = new Biglietteria();

        private int n_clienti = 100;
        Cliente clienti[i] = new Clienti[n_clienti];

        


    }
}
