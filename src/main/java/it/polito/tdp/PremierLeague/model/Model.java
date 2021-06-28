package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private PremierLeagueDAO dao;
	
	private Graph<Player,DefaultWeightedEdge> grafo;
	
	private Map<Integer,Player> idMap;
	
	private List<Match> matches;
	
	private Match match;
	
	public Model() {
		
		this.dao = new PremierLeagueDAO();
		
		this.matches = dao.listAllMatches();
		
		this.idMap = new HashMap<>();
		
		this.dao.listAllPlayers(idMap);
		
	}
	
	public void creaGrafo(Match m) {
		
		this.match = m;
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getPlayersByMatch(m, idMap));
		
		for(Adiacenza a : dao.getAdiacenze(m, idMap)) {
			
			if(a.getPeso() > 0) {
				if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP1(), a.getP2(), a.getPeso());
				}
			} else {
				if(grafo.containsVertex(a.getP1()) && grafo.containsVertex(a.getP2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getP2(), a.getP1(), (-1)*a.getPeso());
				}
			}
			
		}
		
	}

	public GiocatoreMigliore getMigliore() {
		
		if(grafo == null) {
			return null;
		}
		
		Player migliore = null;
		Double maxDelta = (double) Integer.MIN_VALUE;
		
		for(Player p : grafo.vertexSet()) {
			
			double pesoUscente = 0.0;
			
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p)) {
				pesoUscente += this.grafo.getEdgeWeight(e);
			}
			
			double pesoEntrante = 0.0;
			
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(p)) {
				pesoEntrante += this.grafo.getEdgeWeight(e);
			}
			
			double delta = pesoUscente-pesoEntrante;
			
			if(delta > maxDelta) {
				migliore = p;
				maxDelta = delta;
			}
			
		}
		
		return new GiocatoreMigliore(migliore,maxDelta);
		
	}
	
	public List<Match> getMatches() {
		return matches;
	}
	
	public String crazioneGrafo() {
		
		return "#Vertici: "+grafo.vertexSet().size()+"\n#Archi: "+grafo.edgeSet().size()+"\n";
		
	}
	
	public String getTeamFtomPlayer(Match m, Player p) {
		
		return dao.getTeamFromPlayer(p, m);
		
	}
	
	public String simula(int N) {
		
		Simulator sim = new Simulator(this);
		
		sim.init(this.match, N);
		
		sim.run();
		
		String result = this.match+"\n";
		
		result += "Result: "+sim.getGoalCasa()+" - "+sim.getGoalTrasferta()+"\n";
		
		result += "Expelled: "+sim.getRossiCasa()+" - "+sim.getRossiTrasferta()+"\n";
		
		return result;
		
	}
	
}
