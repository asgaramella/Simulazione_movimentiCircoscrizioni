package it.polito.tdp.movimenti.bean;

public class Stat implements Comparable<Stat>{
	private int circ;
	private int eventi;
	
	public Stat(int circ, int eventi) {
		super();
		this.circ = circ;
		this.eventi = eventi;
	}

	public int getCirc() {
		return circ;
	}

	public void setCirc(int circ) {
		this.circ = circ;
	}

	public int getEventi() {
		return eventi;
	}

	public void setEventi(int eventi) {
		this.eventi = eventi;
	}

	@Override
	public int compareTo(Stat other) {
		
		return -(this.eventi-other.getEventi());
	}
	
	

}
