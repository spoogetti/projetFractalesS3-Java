package project;

import project.controllers.Controller;
import project.models.Model;
import project.views.Vue;

public class Main {
	public Main() {
		Model m = new Model();
		Controller c = new Controller(m);
		Vue v = new Vue("Roubaix Champlon", c);
		m.addObserver(v);
	}

	public static void main(String [] args)
	{
		new Main();
	}
}