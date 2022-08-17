package materiel;



public class CableRJ45 extends Materiel {
	
	public CableRJ45(int DureeMax) {
		super(DureeMax);
	}
	public CableRJ45()
	{
		super(2);
	}
	public String toString() {
		return "CableRJ45";
	}
}

