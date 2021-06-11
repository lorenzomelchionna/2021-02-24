package it.polito.tdp.PremierLeague.model;

public class GiocatoreMigliore {
	
	Player migliore;
	double maxDelta;
	
	public GiocatoreMigliore(Player migliore, double maxDelta) {
		super();
		this.migliore = migliore;
		this.maxDelta = maxDelta;
	}

	public Player getMigliore() {
		return migliore;
	}

	public void setMigliore(Player migliore) {
		this.migliore = migliore;
	}

	public double getMaxDelta() {
		return maxDelta;
	}

	public void setMaxDelta(double maxDelta) {
		this.maxDelta = maxDelta;
	}
	
	@Override
	public String toString() {
		return "GiocatoreMigliore: " + this.migliore + ", delta=" + this.maxDelta;
	}
	
}
