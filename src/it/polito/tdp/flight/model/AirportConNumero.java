package it.polito.tdp.flight.model;

public class AirportConNumero implements Comparable<AirportConNumero>{
	private Airport aereoPorto;
	private int numero;
	public AirportConNumero(Airport aereoPorto, int numero) {
		super();
		this.aereoPorto = aereoPorto;
		this.numero = numero;
	}
	public Airport getAereoPorto() {
		return aereoPorto;
	}
	public void setAereoPorto(Airport aereoPorto) {
		this.aereoPorto = aereoPorto;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	@Override
	public int compareTo(AirportConNumero arg0) {
		
		return -this.getNumero()+arg0.getNumero();
	}

}
