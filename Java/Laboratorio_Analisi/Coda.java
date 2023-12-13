package Laboratorio_Analisi;
import java.util.LinkedList;


public class Coda {
    LinkedList <Clienti> lista_attesa = new LinkedList<Clienti>();
    
    public Coda(){

    }

    public synchronized void mettiInCoda(Clienti cliente){
        //Devo aggiungere che si mette in coda per primo se é gi''a stato visitato'
        
        if(cliente.id != 0){
            lista_attesa.addFirst(cliente);
        }
        else{
            lista_attesa.add(cliente);
        }
        notifyAll();
    }  
    
    public synchronized void serviCliente(int id_analista){

        if(lista_attesa.size() == 0){
            try {
                System.out.println("L'analista "+id_analista+ "non ha nessun cliente da servire");
                wait();
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            if((lista_attesa.getFirst()).tipo_esame == 0){  //Se devo fare il tampone
                lista_attesa.removeFirst();
            }
            else{                           
                int index = 1;

                while(true){   //Se devo fare l'esame o mettere il cerotto
                    int id_cliente = lista_attesa.get(index).id;

                    if((id_cliente == 0)){  //Primo incotro analisi del sangue
                        Clienti cliente_cerotto = lista_attesa.remove(index);
                        cliente_cerotto.aspetta();
                        break;
                    }
                    else if(id_cliente == id_analista){
                        lista_attesa.remove(index);
                        break;
                    }
                    else{
                        index++;
                    }
                }
            }

        }
    }
}
