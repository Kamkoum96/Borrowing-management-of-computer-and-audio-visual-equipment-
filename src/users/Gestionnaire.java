package users;



public class Gestionnaire extends User {
	
	public Gestionnaire(String name) {
		super(Type.GESTIONNAIRE, name);
	}
	
	public String toString() {
		return "M." + super.getName();
	}

}
