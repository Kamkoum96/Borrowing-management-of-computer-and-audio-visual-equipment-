package gestion;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import action.Parser;
import users.Emprunteur;
import users.Gestionnaire;
import users.Type;
import users.User;



public class Gestion {
	private HashMap<String, Integer> stock;
	private Parser parser = new Parser();
	private ArrayList<User> users;
	private ArrayList<Emprunt> empruntEnCours;
	private ArrayList<Reservation> reservations;
	private ArrayList<Emprunt>	aValider;
	private Gestionnaire gest = new Gestionnaire("le gestionnaire");
	
	private static final int MAX_MULTIPRISE = 20;
	private static final int MAX_PCPORTABLE = 10;
	private static final int MAX_VIDEOPROJECTEUR = 20;
	private static final int MAX_HAUTPARLEUR = 5;
	private static final int MAX_ECRAN = 10;
	private static final int MAX_CABLERJ45 = 10;
	private static final int MAX_ADAPTATEURHDMI = 10;
	private static final int MAX_IMPRIMANTE = 10;
	private static final int MAX_RETROPROJECTEUR = 10;
	
	
	
	public Gestion() {
		stock = new HashMap<String, Integer>();
		initStock();
		users = new ArrayList<User>();
		empruntEnCours = new ArrayList<Emprunt>();
		aValider = new ArrayList<Emprunt>();
		reservations = new ArrayList<Reservation>();
	}
	
	
	
	private void initStock() {
		stock.put("Multiprise", MAX_MULTIPRISE);
		stock.put("PcPortable", MAX_PCPORTABLE);
		stock.put("VideoProjecteur", MAX_VIDEOPROJECTEUR);
		stock.put("HautParleur", MAX_HAUTPARLEUR);
		stock.put("Ecran", MAX_ECRAN);
		stock.put("CablesRJ45", MAX_CABLERJ45);
		stock.put("AdaptateurHDMI", MAX_ADAPTATEURHDMI);
		stock.put("Imprimante", MAX_IMPRIMANTE);
		stock.put("RetroProjecteur", MAX_RETROPROJECTEUR);
		
	}

	
	public Emprunteur getUser(String name, Type type) {
		Emprunteur newUser = new Emprunteur(type, name);
		for (User user : users)
			if (user.equals(newUser))
				return (Emprunteur) user;
		users.add(newUser);
		return newUser;
	}
	

	public Gestionnaire getGestionnaire() {
		return gest;
	}
	
	
	
	public int getMaxStock(String key) {
		switch (key) {
		case "Multiprise" : return MAX_MULTIPRISE;
		case "PcPortable" : return MAX_PCPORTABLE ;
		case "VideoProjecteur" : return MAX_VIDEOPROJECTEUR;
		case "HautParleur" : return MAX_HAUTPARLEUR;
		case "Ecran" : return MAX_ECRAN;
		case "CableRJ45" : return MAX_CABLERJ45;
		case "AdaptateurHDMI" : return MAX_ADAPTATEURHDMI;
		case "Imprimante" : return MAX_IMPRIMANTE;
		case "RetroProjecteur" : return MAX_RETROPROJECTEUR;
		default : return 10;
		}
	}
	
	
	
	
	public int getNumberItemInStock(String key){
		Set<Entry<String, Integer>> set = stock.entrySet();
		Iterator<Entry<String, Integer>> i = set.iterator();
		while(i.hasNext()) {
			Map.Entry<String, Integer> me = i.next();
			if (key.equals(me.getKey()))
					return me.getValue();	
		}
		return 0;
	}

	
	
	public String affichage(User user) {
		if(user instanceof Gestionnaire) {
			int choice = parser.askNumber("Que voulez vous afficher :\n1 : Etat du stock\n" +
					"2 : Liste des emprunts a valider\n3 : Liste des emprunts en cours\n" +
					"4 : Liste des reservations",4);
			switch (choice){
			case 1 : return affichageStock();
			case 2 : return affichageEmpruntAValider();
			case 3 : return affichageEmpruntEnCours();
			case 4 : return affichageReservations();
			}
		} else if(user instanceof Emprunteur) {
			return ((Emprunteur) user).getAllEmprunt();
		}
		
		return null;
	}
	
	
	
	private String affichageStock() {
		String str = "Etat du stock:\n";
		Set<Entry<String, Integer>> set = stock.entrySet();
		
		Iterator<Entry<String, Integer>> i = set.iterator();
		
		while(i.hasNext()) {
			Map.Entry<String, Integer> me = (Map.Entry<String, Integer>)i.next();
			str += me.getKey() + ": " + me.getValue()+"\n";
		}
		return str;
	}
	
	
	
	private String affichageEmpruntAValider() {
		String str = "Emprunt a Valider:\n";
		if (aValider.isEmpty())
			return str+"aucun\n";
		for (Emprunt resa : aValider)
			str += resa+"\n";
		return str;
	}
	
	
	
	private String affichageEmpruntEnCours() {
		String str = "Emprunt en cours:\n";
		if (empruntEnCours.isEmpty())
			return str+"aucun\n";
		for (Emprunt resa : empruntEnCours)
			str += resa+"\n";
		return str;
	}
	
	
	
	private String affichageReservations() {
		String str = "Liste des reservations:\n";
		if (reservations.isEmpty())
			return str+"aucunes reservations\n";
		for (Reservation resa : reservations)
			str += resa+"\n";
		return str;
	}
	

	
	public void rendre(Emprunt emprunt) {
		if (emprunt==null)
			return;
		if (emprunt.isActive())
			empruntEnCours.remove(emprunt);
		emprunt.switchActive();
		int nombre = stock.get(emprunt.getMateriel().toString());
		stock.remove(emprunt.getMateriel().toString());
		stock.put(emprunt.getMateriel().toString(), nombre + emprunt.getNombre());
		System.out.println("Le rendu a bien etait effectuer, merci.");
		
	}
	
	
	
	
	public void emprunter(Emprunt emprunt) {
		if (emprunt==null)
			System.out.println("desolé, il n'y a pas assez de materiel disponible pour donner suite a votre demande." +
					"\nAbandon...");
		else {
			System.out.println("Votre emprunt a bien etait enregistre.\nEn attente d'un gestionnaire pour le valider.");
			aValider.add(emprunt);
			emprunt.getEmprunteur().addEmprunt(emprunt);
			}
	}
	
	
	public void reserver(Reservation r) {
		if (r==null)
			System.out.println("desolé, il n'y a pas assez de materiel disponible pour donner suite a votre demande." +
					"\nAbandon...");
		else {
			System.out.println("Votre reservation a bien etait enregistre.");
	
		reservations.add(r);
		r.getEmprunteur().addEmprunt(r);
		}
	}

	
	
	public void valider(Emprunt emprunt) {
		empruntEnCours.add(emprunt);
		int nombre = stock.get(emprunt.getMateriel().toString());
		stock.remove(emprunt.getMateriel().toString());
		stock.put(emprunt.getMateriel().toString(), nombre - emprunt.getNombre());
		emprunt.switchActive();
	}
	
	
	
	public void validerTout() {
		System.out.println("Liste de demande a valider :");
		for (Emprunt resa : aValider){
			System.out.println(resa);
			if (parser.askAutorisation())
				valider(resa);
			else
				resa.annulerEmprunt();
		}
		aValider = new ArrayList<Emprunt>();
	}

	
	
	public void verification(Date date) {
			int choice = parser.askNumber("Que voulez vous verifier :\n1 : Emprunts en cours\n" +
					"2 : Reservations",2);
			switch (choice){
			case 1 : verifierEmprunt(date);
			case 2 : gererReservation(date);
			}
	}
	
	
	@SuppressWarnings("unchecked")
	private void verifierEmprunt(Date date) {
		if (empruntEnCours.isEmpty())
			System.out.println("Aucun emprunt a recuperer");
		else {
			ArrayList<Emprunt> tmp = (ArrayList<Emprunt>) empruntEnCours.clone();
			for (Emprunt emprunt : tmp) {
				if (emprunt.getFin().before(date)){
					System.out.println("Attention : "+emprunt.toString()+" a expirer.");
					System.out.println("Recuperation automagiquement du pret en cours...");
					rendre(emprunt);
				}
			}
		}		
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private void gererReservation(Date date) {
		if (reservations.isEmpty())
			System.out.println("Aucune reservations a mettre a jour");
		else {
			ArrayList<Reservation> tmp = (ArrayList<Reservation>) reservations.clone();		
			for (Reservation resa : tmp)
				if (resa.getDebut().before(date)){
					System.out.println("Reservation a mettre a jour : "+resa);
					if (resa.getNombre() < stock.get(resa.getMateriel().toString()) && parser.askAutorisation())
							valider(resa);
					else {
							System.out.println("reservation annuler");
							reservations.remove(resa);
							resa.annulerEmprunt();
					}
				}
		}
	}
}
