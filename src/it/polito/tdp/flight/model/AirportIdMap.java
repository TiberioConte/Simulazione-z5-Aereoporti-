package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {
	private Map<Integer,Airport> map;

	public AirportIdMap() {
		map = new HashMap<Integer,Airport>();
	}
	
	public Airport get(int  id){
		return map.get(id);
	}
	
	public void put(Airport t){
		 map.put(t.getAirportId(),t);
		 
	}
}
