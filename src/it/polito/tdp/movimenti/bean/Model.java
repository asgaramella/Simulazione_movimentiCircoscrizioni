package it.polito.tdp.movimenti.bean;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.movimenti.dao.MovimentiDAO;

public class Model {
	
	private List<Integer> circoscrizioni;
	private MovimentiDAO dao;
	private DefaultDirectedWeightedGraph<Integer,DefaultWeightedEdge>  graph;
	private int maxVertex;
	private int bestPath;
	
	
	public Model() {
		super();
		dao=new MovimentiDAO();
		bestPath=0;
	}



	public List<Integer> getAllCircoscrizioni() {
		if(circoscrizioni==null)
		 circoscrizioni=dao.getAllCircoscrizioni();
		
		return circoscrizioni;
	}
	
	public void creaGrafo(){
		graph=new DefaultDirectedWeightedGraph<Integer,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(graph,this.getAllCircoscrizioni());
		
		for(Movimento mtemp: dao.getAllMovimenti()){
			double peso=0.0;
			DefaultWeightedEdge e=graph.getEdge(mtemp.getCircoscrizioneProvenienza(),  mtemp.getCircoscrizioneDestinazione());
			//caso in cui arco già esiste
			if(e!=null){
			peso=graph.getEdgeWeight(e);
			graph.setEdgeWeight(e, peso+mtemp.getNumeroEventi());
			}else{
			Graphs.addEdgeWithVertices(graph, mtemp.getCircoscrizioneProvenienza(),  mtemp.getCircoscrizioneDestinazione(),mtemp.getNumeroEventi()+peso);
			}
		}
		
		
	}
	
	public List<Stat> getCambiInd(int partenza){
		this.creaGrafo();
		List<Stat>  stats=new ArrayList<>();
		for(int circ: Graphs.successorListOf(graph, partenza)){
			stats.add(new Stat(circ,(int)(graph.getEdgeWeight(graph.getEdge(partenza, circ)))));
		}
		
		Collections.sort(stats);
		
		return stats;
	}
	
	public List<Integer> getCamminomax(int partenza,int nrVert){
		maxVertex=nrVert;
		List<Integer> parziale=new ArrayList<>();
		parziale.add(partenza);
		List<Integer> best=new ArrayList<>();
		int eventiParz=0;
		
		
		recursive(0,parziale,eventiParz,best,partenza);
		
		return best;
	}



	private void recursive(int livello, List<Integer> parziale, int eventiParz, List<Integer> best, int partenza) {
		
		
		if(parziale.size()==maxVertex){
			if(eventiParz>bestPath){
				bestPath=eventiParz;
				best.clear();
				best.addAll(parziale);
			}
			
		return;
		}
		
		for( Integer succ: Graphs.successorListOf(graph, partenza)){
			if(!parziale.contains(succ)){
				
				parziale.add(succ);
				eventiParz+=graph.getEdgeWeight(graph.getEdge(partenza, succ));
				
				recursive(livello+1,parziale,eventiParz,best,succ);
				
				//per far rimuovere oggetto INTEGER !! vs usa quello remove per pos
				parziale.remove(succ);
				eventiParz-=graph.getEdgeWeight(graph.getEdge(partenza, succ));
			}
		}
		
	}

}
