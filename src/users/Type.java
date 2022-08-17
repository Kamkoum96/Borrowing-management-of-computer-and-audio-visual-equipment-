package users;

public enum Type {
	ETUDIANT("etudiant"), PERSONNEL("Personnel"),ENSEIGNANT("Enseignant"), GESTIONNAIRE(
			"gestionnaire");
	
	private String type;
	
	Type(String type) {
		this.type=type;
	}
	public String toString() {
		return type;
	}
	public String getTypeUser() {
		return this.type;
	}
}
