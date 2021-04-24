package project.views;

import project.controllers.Controller;
import project.models.Model;
import project.models.fractals.ModelledFractal;
import project.views.components.CustomActionButton;
import project.views.components.behavior_buttons.ChangeFractalTypeButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static project.views.PositioningConstants.*;

public class Vue extends JFrame implements Observer {
	CustomActionButton unZoomButton;
	CustomActionButton zoomButton;
	ArrayList<ChangeFractalTypeButton> fractalChangeButtons = new ArrayList<>();

	JButton callRefreshButton = new JButton("OK");
	JLabel iterationsLabel = new JLabel("Iterations :");
	public JTextField setIterations = new JTextField();

	JPanel buttonsPane = new JPanel();

	public final Controller controller;

	BufferedImage bufferedImage;

	public Vue(String titre, Controller controller) {
		super(titre);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Model.screenWidth, Model.screenHeight);
		this.setResizable(false);
		this.controller = controller;

		bufferedImage = this.controller.model().modeledFractalImage();

		initButtons();

        setVisible(true);

		this.paint(this.getGraphics());
		buttonsPane.updateUI();
	}

	private void initButtons() {
		buttonsPane.setLayout(null);
		this.setLayout(null);

		buttonsPane.setBounds(0, 0, col(1.5), line(12));

		int buttonsCount = 0;
		for (ModelledFractal fractal : ModelledFractal.values()) {
			fractalChangeButtons.add(new ChangeFractalTypeButton(this, buttonsPane, fractal.label,
					col(0.25), line(0.25 + buttonsCount * 0.6), BUTTON_WIDTH, BUTTON_HEIGHT, fractal));
			buttonsCount++;
		}

		this.unZoomButton = new CustomActionButton(buttonsPane, "-",
				arg0 -> controller.zoom(0.5),
				col(0.25), line(0.25 + buttonsCount * 0.6), BUTTON_WIDTH, BUTTON_HEIGHT);
		buttonsCount++;

		this.zoomButton =  new CustomActionButton(buttonsPane, "+",
				arg0 -> controller.zoom(2),
				col(0.25), line(0.25 + buttonsCount * 0.6), BUTTON_WIDTH, BUTTON_HEIGHT);

		buttonsPane.add(iterationsLabel);
		buttonsPane.add(setIterations);
		buttonsPane.add(callRefreshButton);

		iterationsLabel.setBounds(10, 640, 200, 20);
		setIterations.setBounds(10, 670, 120, 20);
		setIterations.setText(Integer.toString(this.controller.getIterations()));
		callRefreshButton.setBounds(135, 670, 55, 20);


		callRefreshButton.addActionListener(arg0 -> {
			try {
				controller.updateIterations(Integer.parseInt(setIterations.getText()));
			} catch(Exception e) {
				setIterations.setText(Integer.toString(controller.getIterations()));
			} finally {
				controller.updateIterations(Integer.parseInt(setIterations.getText()));
			}
		});

		this.add(buttonsPane);
		new MouseEvents(this);
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		bufferedImage = this.controller.model().modeledFractalImage();
		this.paint(this.getGraphics());
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(bufferedImage, 610, 0, this);
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
