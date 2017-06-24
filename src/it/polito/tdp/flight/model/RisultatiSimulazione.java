package it.polito.tdp.flight.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class RisultatiSimulazione {
	private HashMap<Airport,AirportConNumero> viaggiatoriInQuestoAereoporto;
	
	
	public RisultatiSimulazione() {
		this.viaggiatoriInQuestoAereoporto= new HashMap<Airport,AirportConNumero>();;
	}


	public void AggiornaviaggiatoriInQuestoAereoporto(Airport aereoporto, int viaggiatoriDaAggiungere) {
		if(viaggiatoriInQuestoAereoporto.get(aereoporto)==null){
			viaggiatoriInQuestoAereoporto.put(aereoporto, new AirportConNumero(aereoporto,viaggiatoriDaAggiungere));
			}else{
				AirportConNumero c= viaggiatoriInQuestoAereoporto.get(aereoporto);
				c.setNumero(viaggiatoriDaAggiungere+c.getNumero());
			}
	}

	public HashMap<Airport, AirportConNumero> getViaggiatoriInQuestoAereoporto() {
		return viaggiatoriInQuestoAereoporto;
	}


	public String classificaOrdinata(){
		LinkedList<AirportConNumero> lista= new LinkedList<AirportConNumero>(viaggiatoriInQuestoAereoporto.values());
		Collections.sort(lista);
		
		StringBuilder risultato = new StringBuilder();
		
		risultato.append("Classifica Aereoporti:\n\n");
				
		for (AirportConNumero c: lista) {
				risultato.append(c.getAereoPorto().toString()+" Ha accolto viaggiatori : "+c.getNumero()+" , ");
		}
		risultato.setLength(risultato.length() - 2);//elimina ultimo ", "
		risultato.append(".\n");


		return risultato.toString();
	}
}
