package it.polito.tdp.flight.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {
	private FlightDAO dao;
	private AirportIdMap mappaAirport;
	private SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge> grafo;
	
	//variabili punto uno
	private boolean connesso;
	private Airport aereoportoPiuLontanoRaggiungibileDaFiumicino;
	
	
	
	public Model() {
		this.dao = new FlightDAO();
		this.mappaAirport = new AirportIdMap();
	}
	
	



	public SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}





	public void CreaGrafo(double  distanzaMaxRotte){
		grafo=new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//aggiungo i vertici
		Graphs.addAllVertices(grafo, dao.getAllAirports(mappaAirport));
		System.out.println("vertici :"+grafo.vertexSet().size());
		//aggiungo gli archi 
		for(Route r:dao.getAllRoutes()){
			if(r.getDestinationAirportId()!=r.getSourceAirportId()){
					Airport partenza=mappaAirport.get(r.getSourceAirportId());
					Airport arrivo=mappaAirport.get(r.getDestinationAirportId());
					 LatLng airportPartenza = new LatLng(partenza.getLatitude(),partenza.getLongitude());
					 LatLng airportArrivo = new LatLng(arrivo.getLatitude(),arrivo.getLongitude());
					double distance =LatLngTool.distance(airportPartenza, airportArrivo, LengthUnit.KILOMETER);
					if(distance<distanzaMaxRotte){
						DefaultWeightedEdge arco= grafo.addEdge(partenza, arrivo);
						if(arco!=null){
						grafo.setEdgeWeight(arco, distance/800);
						//la distanza rappresenta il tempo in ore
						}
					}
			}
		}
		System.out.println("archi :"+grafo.edgeSet().size());
	}
	public void Analizza(){
		
		
		SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>grafo1=new SimpleDirectedWeightedGraph<Airport,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo1, grafo.vertexSet());
		Graphs.addAllEdges(grafo1, grafo, grafo.edgeSet());
		
		HashSet<Airport> daEliminare=new HashSet<Airport>();
		for(Airport a:grafo1.vertexSet()){
		int rotte=Graphs.neighborListOf(grafo1, a).size();
		if(rotte==0){
			daEliminare.add(a);
			}
		}
		grafo1.removeAllVertices(daEliminare);
		
		
		ConnectivityInspector<Airport,DefaultWeightedEdge> ispettore=new ConnectivityInspector<Airport,DefaultWeightedEdge>(grafo1);
		connesso=ispettore.isGraphConnected();	
		
		Airport fiumicino=mappaAirport.get(1555);
		double maxTempo=Double.MIN_VALUE;
		long startTime = System.nanoTime();
		DijkstraShortestPath<Airport,DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Airport,DefaultWeightedEdge>(grafo1);      
		  
		for(Airport destinazione:ispettore.connectedSetOf(fiumicino)){
			if(!destinazione.equals(fiumicino)){
				double tempoImpiegato=dijkstra.getPathWeight(fiumicino, destinazione);
			
				if(tempoImpiegato>maxTempo){
				
					maxTempo=tempoImpiegato;
					aereoportoPiuLontanoRaggiungibileDaFiumicino=destinazione;
				}
			}
		}
		long endTime = System.nanoTime(); 
		System.out.println(endTime-startTime);
		
		
	}
	public boolean isConnesso() {
		return connesso;
	}
	public Airport getAereoportoPiuLontanoRaggiungibileDaFiumicino() {
		return aereoportoPiuLontanoRaggiungibileDaFiumicino;
	}
	
	public RisultatiSimulazione Simula( int  n) {
		Simulatore simulatore =new Simulatore(this,n);
		//inizializzo la coda degli eventi
		//gli eventi sono creati a secondo delle rilevaioni delle fiume che per ogni fiume sono caricate in modo pigro.
		simulatore.caricaEventi();
		//effettuo la simulazione
		simulatore.run();
		//restitusico il risultato
		return simulatore.Results();
	}
}
