package Aeroporto;
import java.util.LinkedList;

public class Torre {
    LinkedList <Aerei> codaDecolloPistaA = new LinkedList<Aerei>();
    LinkedList <Aerei> codaDecolloPistaB = new LinkedList<Aerei>();
    int max_coda;
    LinkedList <Aerei> codaAtterraggio = new LinkedList<Aerei>();

    public Torre(int m){
        max_coda = m;
    }

    public synchronized void effettuaAtterraggioDecollo(int id_pista) { //Consumatore -> wait()
        Aerei aereo;

        if(codaAtterraggio.size() == 0 && codaDecolloPistaA.size() == 0 && codaDecolloPistaB.size() == 0){
            try {
                System.out.println("La pista #"+id_pista+" non ha ne decolli ne atterraggi da effettuare");
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(codaAtterraggio.size() == 0){ //Effettuo decolli
            if(id_pista == 1){ //Decolli pista 1
                aereo = codaDecolloPistaA.removeFirst();
                System.out.println("L'aereo "+aereo.getID()+" é decollato con successo dalla pista#"+1);    
            }
            else{
                aereo = codaDecolloPistaB.removeFirst();
                System.out.println("L'aereo "+aereo.getID()+" é decollato con successo dalla pista#"+2);
            }
        }
        else{                  //Gli atterraggi hanno la precedenza 
            int maxIndex = codaAtterraggio.size(); //Essendo la coda virtualmente infinita mi serve un indice massimo per poterla scorrere.
            int index = 1;

            while(true){     //Gli atterraggi internazionali hanno la precedenza
                if(index < maxIndex){
                    aereo = codaAtterraggio.get(index);
                    if(aereo.getProvenienza() == 1){
                        codaAtterraggio.remove();
                        System.out.println("L'aereo "+aereo.getID()+" é atterrato con successo sulla pista#"+id_pista);
                        break;
                    }
                    else
                        index++;
                }
                else{
                    // Procedo con gli atterraggi nazionali. Non ci sono atterraggi internazionai da effettuare 
                    aereo = codaAtterraggio.removeFirst();
                    System.out.println("L'aereo "+aereo.getID()+" é atterrato con successo sulla pista#"+id_pista);  
                    break;
                } 
            }  
        }
    }

    public synchronized void richiediAtterraggioDecollo(Aerei aereo) { //Produttore -> notifyAll()
        if(aereo.getStato() == 0){                    //Richiede decollo
            if(codaDecolloPistaA.size() == max_coda && codaDecolloPistaB.size() ==  max_coda){
                System.out.println("Le piste per il decollo sono piene, l'aereo "+aereo.getID()+" non puó inserirsi in coda.");
            }
            else if(codaDecolloPistaA.size() < max_coda){
                System.out.println("L'aereo "+aereo.getID()+" si inserisce in coda sulla pista di decollo A.");
                codaDecolloPistaA.add(aereo);
            }
            else if(codaDecolloPistaB.size() < max_coda){
                System.out.println("L'aereo "+aereo.getID()+" si inserisce in coda sulla pista di decollo A.");
                codaDecolloPistaB.add(aereo);
            }
            notifyAll(); //Inserisco quí il notify perché la coda di atterraggio da direttive é virtualmente infinita.
        }
        else{                                   //Richiede atterraggio
            System.out.println("L'aereo "+aereo.getID()+" richiede l'atterraggio e si inserisce in coda.");
            codaAtterraggio.add(aereo);
        }
    }
}
