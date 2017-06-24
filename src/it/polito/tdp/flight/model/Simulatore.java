package it.polito.tdp.flight.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

import org.jgrapht.Graphs;

public class Simulatore {
		//solo ti serva il model durante la simulazione
		Model model;
		
		
		// Parametri di simulazione(parametri che puoi cambiare 
		//prima di avviare la simulazione ma una volta avviata
		//restano costanti)
		private int k;
		
		// Stato del modello(variabili che durante la simulazione 
		//variano il loro valore)
		//coincidono
		// Variabili di interesse(che sono il risultato della simulazione)
		private RisultatiSimulazione rs;
		
		// Lista degli eventi
		PriorityQueue<Event> queue;
		
		public Simulatore(Model model, int k) {
			this.model = model;
			this.k = k;
			this.rs = new RisultatiSimulazione();
			this.queue = new PriorityQueue<Event>();
		}

		public void caricaEventi() {
			ArrayList<Airport> tuttiGliAereoporti=new ArrayList<Airport>(model.getGrafo().vertexSet());
			for (int i=0;i<k;i++){
				
				queue.add(new Event (LocalDateTime.of(1970, 1, 1, 06, 0),tuttiGliAereoporti.get((int)(Math.random()*tuttiGliAereoporti.size())),1));
			}
		}
		//evento=arrivo di un cliente in un aereoporto
		public void run() {
			while (!queue.isEmpty()) {
				Event e = queue.poll();
				//se il tuo aereo atterra a simulazione finita non ne raccolgo le statisctiche e non gli faccio generare eventi ulteriori
				if(e.getIstante().compareTo(LocalDateTime.of(1970,1,3,06,0))<=0){	
						rs.AggiornaviaggiatoriInQuestoAereoporto(e.getAereoporto(), e.getNummeroViaggiatori());
						ArrayList<Airport> aereoporti=new ArrayList<Airport>(Graphs.successorListOf(model.getGrafo(),e.getAereoporto()));
						if(aereoporti.size()>0){
								Airport prossimoAereoporto=aereoporti.get((int)Math.random()*aereoporti.size());
								LocalDateTime arrivoAlprossimoAereoPorto=null;
								LocalDateTime partenzaDaQuestoAereoPorto=null;
								if(e.getIstante().getHour()<23){
								//arrivi in orario utile per non aspettare aereo del giorno dopo
												if(e.getIstante().getHour()%2==0){
													//se e' arrivato in un ora pari
													partenzaDaQuestoAereoPorto=e.getIstante().plusHours(1);
												}else if(e.getIstante().getHour()%2==1&&e.getIstante().getMinute()!=0){
													//se e' arrivato in un ora dispari e non al minuto 00
													partenzaDaQuestoAereoPorto=e.getIstante().plusHours(2);
												}else{
													//se e' arrivato in un ora dispari e al minuto 00
													partenzaDaQuestoAereoPorto=e.getIstante().plusHours(0);
												}
												int durataViaggio=(int)(model.getGrafo().getEdgeWeight(model.getGrafo().getEdge(e.getAereoporto(), prossimoAereoporto))*60);
												arrivoAlprossimoAereoPorto=partenzaDaQuestoAereoPorto.plusMinutes(durataViaggio);
								}else{
								//devo in questo caso generare un finto arrivo in aereoporto cosi se arrivi dopo le 23 e non ci sono più voli 
								//e come se fossi arrivato alle 6 del mattino
												LocalDateTime fintoArrivo=LocalDateTime.of(e.getIstante().getYear(), e.getIstante().getMonth(), e.getIstante().getDayOfMonth()+1, 06, 0);
												if(fintoArrivo.getHour()%2==0){
													//se e' arrivato in un ora pari
													partenzaDaQuestoAereoPorto=e.getIstante().plusHours(1);
												}else if(fintoArrivo.getHour()%2==1&&fintoArrivo.getMinute()!=0){
													//se e' arrivato in un ora dispari e non al minuto 00
													partenzaDaQuestoAereoPorto=e.getIstante().plusHours(2);
												}else{
													//se e' arrivato in un ora dispari e al minuto 00
													partenzaDaQuestoAereoPorto=e.getIstante().plusHours(0);
												}
												int durataViaggio=(int)(model.getGrafo().getEdgeWeight(model.getGrafo().getEdge(e.getAereoporto(), prossimoAereoporto))*60);
												arrivoAlprossimoAereoPorto=partenzaDaQuestoAereoPorto.plusMinutes(durataViaggio);
								}
								queue.add(new Event (arrivoAlprossimoAereoPorto,prossimoAereoporto,1));
						}//fine if sul controllo aereoporto in cui sei arrivato almeno una tratta verso un altro aereoporto
				}//fine if su controllo evento oltre scadenza	
			}//fine while
		}

		public RisultatiSimulazione Results() {
			return rs;
		}
	
		
		
		
}
