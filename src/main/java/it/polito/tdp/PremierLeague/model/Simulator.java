package it.polito.tdp.PremierLeague.model;

import java.util.PriorityQueue;

public class Simulator {
	
	private Model model;
	
	public Simulator(Model m) {
		this.model = m;
	}
	
	//coda eventi
	private PriorityQueue<Event> queue;
	
	//input
	private int N;
	private Match match;
	
	//modello del mondo
	private String squadraMigliore;
	private String squadraCasa;
	private String squadraTrasferta;
	private int nGiocatoriCasa;
	private int nGiocatoriTrasferta;
	
	//output
	private int goalCasa;
	private int goalTrasferta;
	private int rossiCasa;
	private int rossiTrasferta;
	
	public void init(Match m, int N) {
		
		this.match = m;
		this.N = N;
		
		this.squadraCasa = this.match.teamHomeNAME;
		this.squadraTrasferta = this.match.teamAwayNAME;
		
		this.squadraMigliore = model.getTeamFtomPlayer(match, model.getMigliore().migliore);
		
		this.nGiocatoriCasa = 11;
		this.nGiocatoriTrasferta = 11;
		
		this.goalCasa = 0;
		this.goalTrasferta = 0;
		this.rossiCasa = 0;
		this.rossiTrasferta = 0;
		
		this.queue = new PriorityQueue<Event>();
		
	}
	
	public void run() {
		
		while(N > 0) {
			
			int p = (int)(Math.random()*100);
			
			if(p<50) {
				//goal
				
				if(this.nGiocatoriCasa > this.nGiocatoriTrasferta) {
					this.goalCasa++;
				} else if(this.nGiocatoriCasa < this.nGiocatoriTrasferta) {
					this.goalTrasferta++;
				} else {
					
					if(this.squadraMigliore.equals(this.squadraCasa)) {
						this.goalCasa++;
					}else {
						this.goalTrasferta++;
					}
					
				}
				
			} else if(p>=50&&p<80) {
				//espulsione
				
				int p2 = (int)(Math.random()*100);
				
				if(p2 < 60) {
					
					if(this.squadraMigliore.equals(this.squadraCasa)) {
						this.nGiocatoriCasa--;
						this.rossiCasa++;
					} else {
						this.nGiocatoriTrasferta--;
						this.rossiTrasferta++;
					}
					
				} else {
					
					if(this.squadraMigliore.equals(this.squadraCasa)) {
						this.nGiocatoriTrasferta--;
						this.rossiTrasferta++;
					} else {
						this.nGiocatoriCasa--;
						this.rossiCasa++;
					}
					
				}
				
			} else {
				//infortunio
				
				int p3 = (int)(Math.random()*100);
				int p4 = (int)(Math.random()*100);
				
				if(p3 < 50) {
					
					this.nGiocatoriCasa--;
					
				} else {
					
					this.nGiocatoriTrasferta--;
					
				}
				
				if(p4 < 50) {
					
					this.N += 2;
					
				} else {
					
					this.N += 3;
					
				}
				
			}
			
			this.N--;
			
			if(this.nGiocatoriCasa == 0 || this.nGiocatoriTrasferta == 0) {
				return;
			}
			
		}
		
	}

	public int getGoalCasa() {
		return goalCasa;
	}

	public int getGoalTrasferta() {
		return goalTrasferta;
	}

	public int getRossiCasa() {
		return rossiCasa;
	}

	public int getRossiTrasferta() {
		return rossiTrasferta;
	}
	
}
