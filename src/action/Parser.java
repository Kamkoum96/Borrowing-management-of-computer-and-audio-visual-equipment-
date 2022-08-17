package action;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import materiel.*;
import gestion.Emprunt;
import users.*;


public class Parser {
	
	private ValidCommand commands;
	private Scanner reader;
	private String word;
	private int number;

	public Parser() {
		commands = new ValidCommand();
		reader = new Scanner(System.in);
	}

	
	public CommandWord getCommand() {
		System.out.println("Que voulez-vous faire ?");
		System.out.print("> ");
		word = reader.nextLine();
		return commands.getCommandWord(word);
	}

	
	public void showCommands() {
		commands.showAll();
	}

	
	public int askNumber(String question, int maxValu){
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			System.out.println(question);
			System.out.print("> ");
			try {
				number = Integer.parseInt(reader.readLine());
				if (number < 1 || (maxValu != -1 && number > maxValu) )
					throw new Exception();
				break;
			} catch (Exception e) {System.out.println("entre invalide");;}
		}
		return number;
	}

	
	public String askName() {
		word = "";
		while (word == "") {
			System.out.println("Quel est votre nom ?");
			System.out.print("> ");
			word = reader.nextLine();
		}
		return word;
	}


	
	public Emprunt askMaterielARendre(Emprunteur user) {
		word = "";
		while (!user.isInTheList(word)) {
			System.out.println("Que voulez-vous rendre ?");
			System.out.print("> ");
			word = reader.nextLine();
		}

		return user.rendre(word);
	}
	
	
	public Materiel askMateriel() {
		while (true) {

			System.out.println("Quel materiel vous interresse ?");
			System.out.print("> ");
			word = reader.nextLine();
			switch (word) {
			case "PcPortable":
				return new PcPortable();
			case "Ecran":
				return new Ecran();
			case "HautParleur":
				return new HautParleur();
			case "VideoProjecteur":
				return new VideoProjecteur();
			case "RetroProjecteur":
				return new RetroProjecteur();
			case "Imprimante":
				return new Imprimante();
			case "AdaptateurHDMI":
				return new AdaptateurHDMI();
			case "CableRJ45":
				return new CableRJ45();
			case "Multiprise":
				return new Multuprise();
			case "help":
				System.out.
				println("commandes possibles : PcPortable, Ecran, HautParleur, "
						+ "VideoPojecteur, RetroPojecteur, Imprimante, AdaptateurHDMI, CableRJ45, Multiprise");
				break;
			default:
				System.out
						.println("Choix invalide, tapez \"help\" pour afficher les choix possible");

			}
		}
	}

	

	
	
	public boolean askAutorisation() {
		System.out.print("Voulez vous autoriser cette emprunt : (y/n)");
		String word = reader.nextLine();
		switch (word) {
		case "y":
		case "Y":
		case "O":
		case "o":
			return true;
		case "N":
		case "n":
			return false;
		}
		System.out.println("PAS COMPRIS, emprunt non autoriser");
		return false;
	}
}
