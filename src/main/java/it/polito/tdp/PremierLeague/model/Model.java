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
	
	public Model() {
		
		this.dao = new PremierLeagueDAO();
		
		this.matches = dao.listAllMatches();
		
		this.idMap = new HashMap<>();
		
		this.dao.listAllPlayers(idMap);
		
	}
	
	public void creaGrafo(Match m) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getPlayersByMatch(m, idMap));
		
		Map<Player,Float> mapEff = dao.getEfficienze(m, idMap);
		
		for(Player p : idMap.values()) {
			
			for(Player pp : idMap.values()) {
				
				if(!p.equals(pp) && grafo.vertexSet().contains(p) && grafo.vertexSet().contains(pp) && !grafo.containsEdge(p, pp)) {
				
					if(mapEff.keySet().contains(p) && mapEff.keySet().contains(pp)) {
					
						float weight = mapEff.get(p)-mapEff.get(pp);
						
						if(weight>0) {
							Graphs.addEdgeWithVertices(grafo, p, pp, weight);
						}
					
					}
				
				}
				
			}
			
		}
		
	}

	public List<Match> getMatches() {
		return matches;
	}
	
	public String crazioneGrafo() {
		
		return "#Vertici: "+grafo.vertexSet().size()+"\n#Archi: "+grafo.edgeSet().size()+"\n";
		
	}
	
}
