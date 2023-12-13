public class Main{
	public static void main(String args[]){
		Falegnameria f = new Falegnameria();
		Produzione p[] = new Produzione[3];
		Assemblaggio a[] = new Assemblaggio[2];
		
		for(int i=0; i<3; i++){
			try{
				p[i] = new Produzione(f, i);
				Thread.sleep(500);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			p[i].start();
		}
		for(int i=0; i<2; i++){
			a[i] = new Assemblaggio(f, i);
			a[i].start();
		}
	}
}

class Produzione extends Thread{
	int idProduzione;
	Falegnameria f;
	int tipoPezzo; //0 A, 1 B, 2 C
	
	public Produzione(Falegnameria f, int idProduzione){
		this.f = f;
		this.idProduzione = idProduzione;
		this.tipoPezzo = (int)(Math.random()*3);
	}
	
	public void run(){
		try{
			Thread.sleep(200);
			f.produciPezzi(this);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

class Assemblaggio extends Thread{
	int idAssemblaggio;
	Falegnameria f;
	
	public Assemblaggio(Falegnameria f, int idAssemblaggio){
		this.idAssemblaggio = idAssemblaggio;
		this.f = f;
	}
	
	public void run(){
		while(true){
			f.assemblaProdotto(this);
		}
	}
}

class Falegnameria{
    private LinkedList<Produzione> Magazzino1 = new LinkedList<Produzione>();
    private LinkedList<Produzione> Magazzino2 = new LinkedList<Produzione>();
    private LinkedList<Produzione> Magazzino3 = new LinkedList<Produzione>();
    int n_MAX = 5;

    public synchronized void produci(Produzione p){
        if(p.idProduzione == 0){
            while(Magazzino1.size() <= n_MAX){
                Magazzino1.add(p);
                notifyAll();
                System.out.println("Magazzino A fornito");
            }
        }
        else if(p.idProduzione == 1){
            while(Magazzino2.size() <= n_MAX){
                Magazzino2.add(p);
                notifyAll();
                System.out.println("Magazzino B fornito");
            }
        }
        else{
            while(Magazzino3.size() <= n_MAX){
                Magazzino3.add(p);
                notifyAll();
                System.out.println("Magazzino C fornito");
            }
        }
    }

    public synchronized void assembla(Assemblaggio a){
        while(Magazzino1.size() == 0 && Magazzino2.size() == 0 && Magazzino3.size() == 0){
            try{
				System.out.println("Magazzini vuoti");
                Thread.sleep((int) (Math.random() *500));
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        while(Magazzino1.size() > 0 && Magazzino2.size() > 0 && Magazzino3.size() > 0){
            if(a.idAssemblaggio == 0){
                Magazzino1.removeFirst();
                Magazzino2.removeFirst();
                Magazzino3.removeFirst();
                System.out.println("P1: Riceve quantità necessaria da A B e C, assembla");
                try{
                    Thread.sleep((int) (Math.random() *500));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("P1: Ha assemblato tutto!!");
            }
            else{
                Magazzino1.removeFirst();
                Magazzino2.removeFirst();
                Magazzino3.removeFirst();
                System.out.println("P2: Riceve quantità necessaria da A B e C, assembla");
                try{
                    Thread.sleep((int) (Math.random() *500));
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("P2: Ha assemblato tutto!!");
            }
        }
    }
}
}