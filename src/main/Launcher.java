package main;

import gestion.Gestion;
import action.Action;



public class Launcher {
	public static void main(String [] args) {
		Gestion gestion = new Gestion();
		Action action = new Action(gestion);
		action.run();
	}
}
