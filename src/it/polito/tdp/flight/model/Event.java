package it.polito.tdp.flight.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	private  LocalDateTime istante;
	private Airport aereoporto;
	private int nummeroViaggiatori;
	
	

	

	public Event(LocalDateTime istante, Airport aereoporto, int nummeroViaggiatori) {
		super();
		this.istante = istante;
		this.aereoporto = aereoporto;
		this.nummeroViaggiatori = nummeroViaggiatori;
	}


	
	public LocalDateTime getIstante() {
		return istante;
	}



	public void setIstante(LocalDateTime istante) {
		this.istante = istante;
	}



	public Airport getAereoporto() {
		return aereoporto;
	}



	public void setAereoporto(Airport aereoporto) {
		this.aereoporto = aereoporto;
	}



	public int getNummeroViaggiatori() {
		return nummeroViaggiatori;
	}



	public void setNummeroViaggiatori(int nummeroViaggiatori) {
		this.nummeroViaggiatori = nummeroViaggiatori;
	}



	@Override
	public int compareTo(Event o) {
		 return this.istante.compareTo(o.getIstante());
		
	}



	@Override
	public String toString() {
		return "Event [istante=" + istante + ", aereoporto=" + aereoporto + ", nummeroViaggiatori=" + nummeroViaggiatori
				+ "]";
	}

	
}	