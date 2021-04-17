package project.views;

import project.controllers.Controller;
import project.models.fractals.ModelledFractal;
import project.models.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

public class Vue extends JFrame implements Observer {

	public static ModelledFractal fractal;

	JButton unZoomButton = new JButton("-");
	JButton zoomButton = new JButton("+");
	JButton newtonButton = new JButton("Newton");
	JButton mandelbrotButton = new JButton("Mandelbrot");
	JButton pythagorasButton = new JButton("Pythagore");

	JButton callRefreshButton = new JButton("OK");

	JLabel iterationsLabel = new JLabel("Iterations :");
	JTextField setIterations = new JTextField();
	
	JPanel buttonsPane = new JPanel();

	private final Controller controller;

	BufferedImage bufferedImage;

	public Vue(String titre, Controller controller) {
		super(titre);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Model.screenWidth, Model.screenHeight);
		this.setResizable(false);
		this.controller = controller;

		fractal = ModelledFractal.MANDELBROT;

		bufferedImage = this.controller.model().modeledFractal();

		initButtons();

        setVisible(true);

		this.paint(this.getGraphics());
		buttonsPane.updateUI();
	}

	private void initButtons() {
		buttonsPane.setLayout(null);
		this.setLayout(null);

		buttonsPane.setBounds(800, 0, 200, 800);
		buttonsPane.setBackground(Color.gray);

		buttonsPane.add(unZoomButton);
		buttonsPane.add(zoomButton);
		buttonsPane.add(newtonButton);
		buttonsPane.add(mandelbrotButton);
		buttonsPane.add(pythagorasButton);

		buttonsPane.add(iterationsLabel);
		buttonsPane.add(setIterations);
		buttonsPane.add(callRefreshButton);

		unZoomButton.setBounds(10, 10, 180, 20);
		zoomButton.setBounds(10, 40, 180, 20);
		newtonButton.setBounds(10, 70, 180, 20);
		mandelbrotButton.setBounds(10, 100, 180, 20);
		pythagorasButton.setBounds(10, 130, 180, 20);

		iterationsLabel.setBounds(10, 640, 200, 20);
		setIterations.setBounds(10, 670, 120, 20);
		setIterations.setText(Integer.toString(this.controller.getIterations()));
		callRefreshButton.setBounds(135, 670, 55, 20);

		this.add(buttonsPane);

		unZoomButton.addActionListener(arg0 -> controller.zoom(0.5));

		zoomButton.addActionListener(arg0 -> controller.zoom(2));

		newtonButton.addActionListener(arg0 -> {
			fractal = ModelledFractal.NEWTON;

			controller.refresh();
			setIterations.setText(Integer.toString(controller.getIterations()));
		});

		mandelbrotButton.addActionListener(arg0 -> {
			fractal = ModelledFractal.MANDELBROT;

			controller.refresh();
			setIterations.setText(Integer.toString(controller.getIterations()));
		});

		pythagorasButton.addActionListener(arg0 -> {
			fractal = ModelledFractal.PYTHAGORAS;

			controller.refresh();
			setIterations.setText(Integer.toString(controller.getIterations()));
		});

		callRefreshButton.addActionListener(arg0 -> {
			try {
				controller.updateIterations(Integer.parseInt(setIterations.getText()));
			} catch(Exception e) {
				setIterations.setText(Integer.toString(controller.getIterations()));
			} finally {
				controller.updateIterations(Integer.parseInt(setIterations.getText()));
			}
		});
		
		new MouseEvents(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		bufferedImage = this.controller.model().modeledFractal();
		this.paint(this.getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bufferedImage, 0, 0, this);
		buttonsPane.updateUI();
	}

	public class MouseEvents implements MouseListener {
		int originX = 0;
		int originY = 0;
		
		Vue vue;
		
		public MouseEvents(Vue vue) {
			this.vue = vue;
			this.vue.addMouseListener(this);
		}

		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			originX = e.getX();
			originY = e.getY();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			controller.offset(originX - e.getX(), originY - e.getY());
		}
	}
}
