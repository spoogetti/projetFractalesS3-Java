package project;

import project.controllers.Controller;
import project.models.Model;
import project.views.Vue;

public class Main {
	public static void main(String [] args)
	{
		Model m = new Model();
		Controller c = new Controller(m);
		Vue v = new Vue("Fractal Observer by Romain Roubaix and Romain Champlon", c);
		m.addObserver(v);
	}
}