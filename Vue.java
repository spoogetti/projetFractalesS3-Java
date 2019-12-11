package projetFractales;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;

@SuppressWarnings({ "serial" })
public class Vue extends JFrame implements Observer{

	static fractaleModelisee fractale;

	// boutons et toolbar
	JButton boutonDeZoom = new JButton("-");
	JButton boutonZoom = new JButton("+");
	JButton boutonNewton = new JButton("Newton");
	JButton boutonMandelbrot = new JButton("Mandelbrot");
	JButton boutonPythagore = new JButton("Pythagore");

	JButton boutonIterations = new JButton("OK");

	JLabel iterations = new JLabel("Iterations :");
	JTextField setIterations = new JTextField();
	
	// écrans à afficher
	JPanel panelBoutons = new JPanel();

	private Controleur controleur;

	BufferedImage buf;

	public Vue(String titre, Controleur controle) {
		super(titre);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Modele.largeurEcran, Modele.hauteurEcran);
		this.setResizable(false);
		this.controleur = controle;

		fractale = fractaleModelisee.MANDELBROT;

		buf = this.controleur.modele().fractaleModelisee();

		initBoutons();

        setVisible(true);

		this.paint(this.getGraphics());
		panelBoutons.updateUI();
	}

	private void initBoutons() {
		panelBoutons.setLayout(null);
		this.setLayout(null);

		panelBoutons.setBounds(800, 0, 200, 800);
		panelBoutons.setBackground(Color.gray);

		panelBoutons.add(boutonDeZoom); // Bouton de dezoom
		panelBoutons.add(boutonZoom); // Bouton de zoom
		panelBoutons.add(boutonNewton); // Bouton fractale Newton
		panelBoutons.add(boutonMandelbrot); // Bouton fractale MandelBrot
		panelBoutons.add(boutonPythagore); // Bouton fractale MandelBrot

		panelBoutons.add(iterations);
		panelBoutons.add(setIterations);
		panelBoutons.add(boutonIterations); // Bouton pour set les iterations

		boutonDeZoom.setBounds(10, 10, 180, 20);
		boutonZoom.setBounds(10, 40, 180, 20);
		boutonNewton.setBounds(10, 70, 180, 20);
		boutonMandelbrot.setBounds(10, 100, 180, 20);
		boutonPythagore.setBounds(10, 130, 180, 20);

		iterations.setBounds(10, 640, 200, 20);    // label "itérations"
		setIterations.setBounds(10, 670, 120, 20); // textBox
		setIterations.setText(Integer.toString(this.controleur.getIterations()));
		boutonIterations.setBounds(135, 670, 55, 20); // set iterations

		this.add(panelBoutons);

		boutonDeZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controleur.zoom(0.5);
			}});

		boutonZoom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controleur.zoom(2);
			}});

		boutonNewton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fractale = fractaleModelisee.NEWTON;

				controleur.refresh();
				setIterations.setText(Integer.toString(controleur.getIterations()));

			}});

		boutonMandelbrot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fractale = fractaleModelisee.MANDELBROT;
				
				controleur.refresh();		
				setIterations.setText(Integer.toString(controleur.getIterations()));

		}});

		boutonPythagore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fractale = fractaleModelisee.PYTHAGORE;

				controleur.refresh();
				setIterations.setText(Integer.toString(controleur.getIterations()));

			}});

		boutonIterations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					controleur.updateIterations(Integer.parseInt(setIterations.getText()));
				}
				catch(Exception e) {
					// java.lang.NumberFormatException
					setIterations.setText(Integer.toString(controleur.getIterations()));
				}
				finally {
					controleur.updateIterations(Integer.parseInt(setIterations.getText()));
				}
		}});
		
		// Ajout des écouteurs qui gèrent les évènements souris
		EvenementSouris eSouris = new EvenementSouris(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		buf = this.controleur.modele().fractaleModelisee();
		this.paint(this.getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(buf, 0, 0, this); // rafraichis l'image de la fractale
		panelBoutons.updateUI(); // affiche la toolbar par dessus en rafraichissant l'interface
	}

	public class EvenementSouris implements MouseListener {
		int originX = 0;
		int originY = 0;
		
		Vue vue;
		
		public EvenementSouris(Vue vue) {
			this.vue = vue;
			this.vue.addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// inutilisé
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// inutilisé
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// inutilisé
		}

		@Override
		public void mousePressed(MouseEvent e) {
			originX = e.getX();
			originY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			controleur.offset(originX - e.getX(), originY - e.getY());
		}
		
	}
}
