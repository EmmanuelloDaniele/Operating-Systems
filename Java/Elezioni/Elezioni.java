import java.util.LinkedList;

public class Elezioni{
	public static void main(String args[]){
		int nElettori = 40;
		int ncommissione = 3;
		
		Seggi seggi = new Seggi();
		Commissione commissione[] = new Commissione[ncommissione];
		Elettori elettori[] = new Elettori[nElettori];
		
		for(int i=0; i<ncommissione; i++){
			commissione[i] = new Commissione(seggi, i);
			commissione[i].start();
		}
		
		for(int i=0; i<nElettori; i++){
			try{
				elettori[i] = new Elettori(seggi, i);
				Thread.sleep((int)(Math.random()*500));
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			elettori[i].start();
		}
	}
}
	
class Elettori extends Thread{
	int idElettori;
	int tipologia; //0 dirigente, 1 dipendente, 2 collaboratore
	int dirittoVoto; //0 non ha diritto, 1 ha diritto
	Seggi seggi;
	
	public Elettori(Seggi seggi, int idElettori){
		this.seggi = seggi;
		this.idElettori = idElettori;
		tipologia = (int)(Math.random()*3);
	}
	
	public void run(){
		try{
			Thread.sleep((int)(Math.random()*500));
			seggi.mettiInCoda(this);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}

class Commissione extends Thread{
	int idcommissione;
	Seggi seggi;
	
	public Commissione(Seggi seggi, int idcommissione){
		this.seggi = seggi;
		this.idcommissione = idcommissione;
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep((int)(Math.random()*500));
				seggi.eseguiVoto(this);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
}

class Seggi{
	int idSeggio;
	LinkedList<Elettori> ci1 = new LinkedList<Elettori>();
	LinkedList<Elettori> ci2 = new LinkedList<Elettori>();
	LinkedList<Elettori> ci3 = new LinkedList<Elettori>();
	LinkedList<Elettori> cc1 = new LinkedList<Elettori>();
	LinkedList<Elettori> cc2 = new LinkedList<Elettori>();
	LinkedList<Elettori> cc3 = new LinkedList<Elettori>();
	int maxIdentificazione = 5;
	int maxCabine = 3;
	Elettori e;
	boolean stato = false, stato2 = false, stato3 = false;
	
	public synchronized void mettiInCoda(Elettori e){
		while(ci1.size() == maxIdentificazione && ci2.size() == maxIdentificazione && ci3.size() == maxIdentificazione){
			try{
				Thread.sleep((int)(Math.random()*500));
				wait();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}

		if(ci1.size()<=maxIdentificazione){
			System.out.println("L'elettore è in coda per il Seggio 1");
			ci1.add(e);
			notifyAll();
		}

		if(ci2.size()<=maxIdentificazione){
			System.out.println("L'elettore è in coda per il Seggio 2");
			ci2.add(e);
			notifyAll();
		}

		if(ci3.size()<=maxIdentificazione){
			System.out.println("L'elettore è in coda per il Seggio 3");
			ci3.add(e);
			notifyAll();
		}
	}
	
	public synchronized void eseguiVoto(Commissione c){
		while(ci1.size() == 0 && ci2.size() == 0 && ci3.size() == 0){
			try{
				System.out.println("Non ci sono elettori");
				wait();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		while(ci1.size() > 0 && c.idcommissione == 0){
			if(ci1.element().dirittoVoto == 1){
				System.out.println("L'elettore ha il diritto di voto");
				e = ci1.removeFirst();
				notifyAll();
			}
			else{
				System.out.println("L'elettore non ha il diritto di voto");
				ci1.removeFirst();
				notifyAll();
			}
		}
		
		while(ci2.size() > 0 && c.idcommissione == 1){
			if(ci2.element().dirittoVoto == 1){
				System.out.println("L'elettore ha il diritto di voto");
				e = ci2.removeFirst();
				notifyAll();
			}
			else{
				System.out.println("L'elettore non ha il diritto di voto");
				ci2.removeFirst();
				notifyAll();
			}
		}
		
		while(ci3.size() > 0 && c.idcommissione == 2){
			if(ci3.element().dirittoVoto == 1){
				System.out.println("L'elettore ha il diritto di voto");
				e = ci3.removeFirst();
				notifyAll();
			}
			else{
				System.out.println("L'elettore non ha il diritto di voto");
				ci3.removeFirst();
				notifyAll();
			}
		}
		
		while(cc1.size() == maxCabine && cc2.size() == maxCabine && cc3.size() == maxCabine){
			try{
				System.out.println("Tutte le cabine sono occupate");
				wait();
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		while(cc1.size() <= maxCabine){
			if(ci1.element().tipologia == 0){
				e = ci1.remove();
				System.out.println("Il dirigente deve votare");
				cc1.add(e);
			}
			else if(ci1.element().tipologia == 1){
				e = ci1.remove();
				System.out.println("Il dipendente deve votare");
				cc1.add(e);
			}
			else if(ci1.element().tipologia == 2){
				e = ci1.remove();
				System.out.println("Il collaboratore deve votare");
				cc1.add(e);
			}
		}
		
		while(cc2.size() <= maxCabine){
			if(ci2.element().tipologia == 0){
				e = ci2.remove();
				System.out.println("Il dirigente deve votare");
				cc2.add(e);
			}
			else if(ci2.element().tipologia == 1){
				e = ci2.remove();
				System.out.println("Il dipendente deve votare");
				cc2.add(e);
			}
			else if(ci2.element().tipologia == 2){
				e = ci2.remove();
				System.out.println("Il collaboratore deve votare");
				cc2.add(e);
			}
		}
		
		while(cc3.size() <= maxCabine){
			if(ci3.element().tipologia == 0){
				e = ci3.remove();
				System.out.println("Il dirigente deve votare");
				cc3.add(e);
			}
			else if(ci3.element().tipologia == 1){
				e = ci3.remove();
				System.out.println("Il dipendente deve votare");
				cc3.add(e);
			}
			else if(ci3.element().tipologia == 2){
				e = ci3.remove();
				System.out.println("Il collaboratore deve votare");
				cc3.add(e);
			}
		}

		while(cc1.size() > 0){
			for(int i = cc1.size() -1; i>0; i--){
				if(cc1.element().tipologia == 0){
					e = cc1.removeFirst();
					stato = true;
				}
				else if((cc1.element().tipologia == 1) || (cc1.element().tipologia == 2)){
					e = cc1.remove(i);
					stato = false;
				}
			}
			if(stato){
				System.out.println("Il dirigente ha finito di votare");
				cc1.removeFirst();
				notifyAll();
				contatore++;
			}
			else if(!stato){
				if(e.tipologia == 1){
					System.out.println("Il dipendente ha finito di votare");
					cc1.removeFirst();
					notifyAll();
					contatore++;
				}
				else{
					System.out.println("Il collaboratore ha finito di votare");
					cc1.removeFirst();
					notifyAll();
					contatore++;
				}
			}
		}

		while(cc2.size() > 0){
			for(int i = cc2.size() -1; i>0; i--){
				if(cc2.element().tipologia == 0){
					e = cc2.removeFirst();
					stato2 = true;
				}
				else if((cc2.element().tipologia == 1) || (cc2.element().tipologia == 2)){
					e = cc2.remove(i);
					stato2 = false;
				}
			}
			if(stato2){
				System.out.println("Il dirigente ha finito di votare");
				cc2.removeFirst();
				notifyAll();
				contatore++;
			}
			else if(!stato2){
				if(e.tipologia == 1){
					System.out.println("Il dipendente ha finito di votare");
					cc2.removeFirst();
					notifyAll();
					contatore++;
				}
				else{
					System.out.println("Il collaboratore ha finito di votare");
					cc2.removeFirst();
					notifyAll();
					contatore++;
				}
			}
		}

		while(cc3.size() > 0){
			for(int i = cc3.size() -1; i>0; i--){
				if(cc3.element().tipologia == 0){
					e = cc1.removeFirst();
					stato = true;
				}
				else if((cc3.element().tipologia == 1) || (cc3.element().tipologia == 2)){
					e = cc1.remove(i);
					stato = false;
				}
			}
			if(stato3){
				System.out.println("Il dirigente ha finito di votare");
				cc3.removeFirst();
				notifyAll();
				contatore++;
			}
			else if(!stato3){
				if(e.tipologia == 1){
					System.out.println("Il dipendente ha finito di votare");
					cc3.removeFirst();
					notifyAll();
					contatore++;
				}
				else{
					System.out.println("Il collaboratore ha finito di votare");
					cc3.removeFirst();
					notifyAll();
					contatore++;
				}
			}
		}
	}
}