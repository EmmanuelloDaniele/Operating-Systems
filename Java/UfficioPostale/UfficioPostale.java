import java.util.LinkedList;

public class UfficioPostale{
    public static void main(String args[]){
        UffPostale uffPostale = new UffPostale();
        Cliente[] cliente = new Cliente[10];
        Sportello[] sportello = new Sportello[3];

        for(int i = 0; i < 10; i++){
            cliente[i] = new Cliente(i, uffPostale);
            try{
                Thread.sleep(500);
            }catch(Exception e){
                e.printStackTrace();
            }
            cliente[i].start();
        }
		
        for(int i = 0; i<3; ++i){
			sportello[i] = new Sportello(i, uffPostale);
            sportello[i].start();
        }
    }
}

class Cliente extends Thread{
    int idCliente;
    UffPostale uffPostale;
    int tipo; // 0 veloce, 1 consulenza

    public Cliente(int idCliente, UffPostale uffPostale){
        this.idCliente = idCliente;
        this.uffPostale = uffPostale;
        this.tipo = (int)(Math.random()*2);
    }

    public void run(){
        try{
            Thread.sleep(500);
            uffPostale.mettiInCoda(this);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}

class Sportello extends Thread{
    int idSportello;
    UffPostale uffPostale;
    int tipo; //0 veloce, 1 consulenza
    public Sportello(int idSportello, UffPostale uffPostale){
        this.idSportello = idSportello;
        this.uffPostale = uffPostale;
        this.tipo = (int)(Math.random()*2);
    }

    public void run(){
        while(true){
			uffPostale.assegnaLista(this);
        }
    }
}

class UffPostale{
    LinkedList<Cliente> ListaA = new LinkedList<Cliente>();
    LinkedList<Cliente> ListaB = new LinkedList<Cliente>();
    LinkedList<Cliente> uscita = new LinkedList<Cliente>();
	int n_MAX = 3;
	Cliente c, c1;
	static int contatore = 0;
	boolean flag = false;
	
    public synchronized void mettiInCoda(Cliente c){
		System.out.println(contatore);
		if(c.tipo == 0){
			System.out.println("Cliente in Operazione Veloce, aggiunto in lista A");
			ListaA.add(c);
			notifyAll();
		}
		else if(c.tipo == 1){
			if(ListaB.size() <= n_MAX){
				System.out.println("Cliente in Consulenza, aggiunto in lista B");
				ListaB.add(c);
				notifyAll();
			}
			else{
				System.out.println("Cliente consulenza non trova spazio, allora va in lista A");
				ListaA.add(c);
				notifyAll();
			}
		}
		
		while(uscita.size() == 0){
			try{
				Thread.sleep(500);
				wait();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		for(int i=uscita.size()-1; i>0; i--){
			c = uscita.remove(i);
			if(c.tipo == 1){
				flag = true;
			}
			else{
				flag = false;
			}
		}
		if(uscita.size() > 0 && c.tipo == 1 && flag){
			System.out.println("Cliente in consulenza lascia l'ufficio postale");
			uscita.removeFirst();
			contatore++;
		}
		else if(uscita.size() > 0 && c.tipo == 0 && !flag){
			System.out.println("Cliente in Operazione Veloce lascia l'ufficio postale");
			uscita.remove(c);
			contatore++;
		}
		if(contatore==10){
			System.exit(1);
		}
	}

	public synchronized void assegnaLista(Sportello s){
		while(ListaA.size() == 0 && ListaB.size() == 0){
            try{
                Thread.sleep(500);
                wait();
            }
			catch(InterruptedException e){
                e.printStackTrace();
            }
        }
		if(s.tipo == 0){
            while(ListaA.size() > 0){
                System.out.println("Cliente in Operazione Veloce si reca allo sportello per effettuare l'operazione");
                c = ListaA.removeFirst();
                try{
                    Thread.sleep(500);
					uscita.add(c);
					notifyAll();
                }
				catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
		else if(s.tipo == 1){
            if(ListaB.size() > 0){
				System.out.println("Cliente in Consulenza si reca allo sportello per effettuare l'operazione");
				c1 = ListaB.removeFirst();
				try{
					Thread.sleep(500);
					uscita.add(c1);
					notifyAll();
				}
				catch(InterruptedException e){
					e.printStackTrace();
				}
            }
        }
    }
}