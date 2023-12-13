import java.util.LinkedList;
public class Covid{
	public static void main(String args[]){
		Hub hub = new Hub();
		Paziente paziente[] = new Paziente[20];
		Operatore operatore[] = new Operatore[3];
		
		for(int i=0; i<20; i++){
			try{
				paziente[i] = new Paziente(i, hub);
				Thread.sleep(500);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			finally{
				paziente[i].start();
			}
		}
		
		for(int i=0; i<3; i++){
			operatore[i] = new Operatore(i, hub);
			operatore[i].start();
		}
	}
	
	class Paziente extends Thread{
		int idPaziente;
		int tipologia; //0 tampone, 1 vaccino
		int scelta; //1 molecolare, 0 vado via
		int prenotazione; //0 no, 1 sì
		Hub hub;
		
		public Paziente(int idPaziente, Hub hub){
			this.idPaziente = idPaziente;
			this.hub = hub;
			this.tipologia = (int)(Math.random()*2);
			this.prenotazione = (int)(Math.random()*2);
			this.scelta = (int)(Math.random()*2);
		}
		
		public void run(){
			try{
				Thread.sleep(200);
				hub.mettiInCoda(this);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	class Operatore extends Thread{
		int idOperatore;
		int tipoTampone; //0 rapido, 1 molecolare
		int esito; //0 negativo, 1 positivo
		Hub hub;
		
		public Operatore(int idOperatore, Hub hub){
			this.idOperatore = idOperatore;
			this.hub = hub;
			this.esito = (int)(Math.random()*2);
		}
		
		public void run(){
			while(true){
				try{
					Thread.sleep(200);
					hub.serviPaziente(this);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	class Hub{
		LinkedList<Paziente> codaIngresso = new LinkedList<Paziente>();
		LinkedList<Paziente> codaTampone = new LinkedList<Paziente>();
		LinkedList<Paziente> codaVaccino = new LinkedList<Paziente>();
		LinkedList<Paziente> codaUscita = new LinkedList<Paziente>();
		Paziente paziente;
		int MAX_PAZIENTI = 10;
		boolean priorita = false;
		public synchronized void mettiInCoda(Paziente p){
			while(codaIngresso.size()<MAX_PAZIENTI){
				System.out.println("I pazienti stanno entrando");
				codaIngresso.add(p);
				notifyAll();
			}
			//Il paziente non ha prenotato
			if(p.prenotazione == 0){
				//Tampone
				if(p.tipologia == 0){
					paziente = codaIngresso.remove();
					System.out.println("Il paziente senza prenotazione vuole effettuare un tampone");
					codaTampone.add(paziente);
				}
				//Vaccino
				else{
					paziente = codaIngresso.remove();
					System.out.println("Il paziente senza prenotatazione vuole effettuare un vaccino");
					codaVaccino.add(paziente);
				}
			}
			else{
				priorita = true;
				//Tampone
				if(p.tipologia == 0){
					paziente = codaIngresso.remove();
					System.out.println("Il paziente con prenotazione vuole effettuare un tampone");
					codaTampone.add(paziente);
				}
				//Vaccino
				else{
					paziente = codaIngresso.remove();
					System.out.println("Il paziente con prenotatazione vuole effettuare un vaccino");
					codaVaccino.add(paziente);
				}
			}
		}
		
		public synchronized void serviPaziente(Operatore o){
			while(codaIngresso.size()==0 && codaTampone.size()==0 && codaVaccino.size()==0){
				try{
					System.out.println("Nessun paziente in HUB");
					wait();
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
			}
			//Tampone rapido
			if(o.tipoTampone == 0){
				//Coloro che hanno effettuato la prenotazione
				if(codaTampone.size()>0 && priorita){
					System.out.println("L'operatore sta facendo un tampone rapido a coloro che hanno effettuato la prenotazione");
					try{
						Thread.sleep((int)(Math.random() *500));
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if(o.esito == 0){
						System.out.println("Tampone negativo");
						paziente = codaTampone.removeFirst();
						codaUscita.add(paziente);
						priorita = false;
						notifyAll();
					}
					else{
						System.out.println("Tampone positivo");
						if(codaTampone.element().scelta == 0 && o.tipoTampone == 1){
							System.out.println("L'operatore sta facendo un tampone molecolare");
							try{
								Thread.sleep((int) (Math.random() *500));
							}
							catch(Exception e){
								e.printStackTrace();
							}
							paziente = codaTampone.removeFirst();
							codaUscita.add(paziente);
							priorita = false;
							notifyAll();
						}
					}
				}
				//Coloro che non hanno effettuato la prenotazione
				else{
					if(codaTampone.size()>0 && !priorita){
						System.out.println("L'operatore sta facendo un tampone rapido a coloro che non hanno effettuato la prenotazione");
						try{
							Thread.sleep((int)(Math.random() *500));
						}
						catch(Exception e){
							e.printStackTrace();
						}
						if(o.esito == 0){
							System.out.println("Tampone negativo");
							paziente = codaTampone.removeFirst();
							codaUscita.add(paziente);
							priorita = false;
							notifyAll();
						}
						else{
							System.out.println("Tampone positivo");
							if(codaTampone.element().scelta == 0 && o.tipoTampone == 1){
								System.out.println("L'operatore sta facendo un tampone molecolare");
								try{
									Thread.sleep((int) (Math.random() *500));
								}
								catch(Exception e){
									e.printStackTrace();
								}
								paziente = codaTampone.removeFirst();
								codaUscita.add(paziente);
								priorita = false;
								notifyAll();
							}
						}
					}
				}
			}
			//Tampone molecolare
			else{
				if(codaTampone.size()>0 && priorita){
					System.out.println("L'operatore sta facendo un tampone molecolare a coloro che hanno effettuato la prenotazione");
					try{
						Thread.sleep((int)(Math.random() *500));
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if(o.esito == 0){
						System.out.println("Tampone negativo");
						paziente = codaTampone.removeFirst();
						codaUscita.add(paziente);
						priorita = false;
						notifyAll();
					}
					else{
						System.out.println("Tampone positivo");
						paziente = codaTampone.removeFirst();
						codaUscita.add(paziente);
						priorita = false;
						notifyAll();
					}
				}
				else{
					System.out.println("L'operatore sta facendo un tampone molecolare a coloro che non hanno effettuato la prenotazione");
					try{
						Thread.sleep((int)(Math.random() *500));
					}
					catch(Exception e){
						e.printStackTrace();
					}
					if(o.esito == 0){
						System.out.println("Tampone negativo");
						paziente = codaTampone.removeFirst();
						codaUscita.add(paziente);
						priorita = false;
						notifyAll();
					}
					else{
						System.out.println("Tampone positivo");
						paziente = codaTampone.removeFirst();
						codaUscita.add(paziente);
						priorita = false;
						notifyAll();
					}
				}
			}
			if(codaVaccino.size()>0){
				if(codaVaccino.element().tipologia == 1){
					System.out.println("L'operatore sta effettuato un vaccino");
					try{
						Thread.sleep((int)(Math.random() *500));
					}
					catch(Exception e){
						e.printStackTrace();
					}
					paziente = codaVaccino.removeFirst();
					codaUscita.add(paziente);
					notifyAll();
				}
			}
			while(codaUscita.size()>0){
				System.out.println("Il paziente va via");
				paziente = codaUscita.removeFirst();
			}
		}
	}
}