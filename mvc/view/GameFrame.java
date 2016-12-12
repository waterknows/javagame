package _08final.mvc.view;
import java.awt.*;
import java.awt.event.WindowEvent;
import javax.swing.*;

import _08final.mvc.controller.Game;
import _08final.mvc.model.Constant;


public class GameFrame extends JFrame {


	/* The Game panel for the game **/
	private GamePanel mPanel;

	/* The controller for the game **/
	private Game mController;

	public GameFrame(Game controller) {
		//Enable the frame to be notified if the user clicks on the close button
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);

		//Set the game controller for the Frame
		this.mController = controller;
		try {
			// Try to initialize the game panel
			initPanel();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Initialize the frame
		this.setTitle("Galaga_Yuxiao Sun");
		this.setSize(Constant.FRAME_DIM);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setFocusable(true);
		this.setCursor(new Cursor(1));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Draws the current game information to the window
	 */
	public void draw() {
		this.mPanel.repaint();
	}

	private void initPanel() throws Exception {

		//Set the layout for the panel
		JPanel contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(new BorderLayout());
		//Create a new GamePanel for the controller
		this.mPanel = new GamePanel();
		//Let the controller be the listener for the all actions that happen on the game panel
		this.addKeyListener(this.mController);
		this.addMouseListener(this.mController);
		//Add the game panel to the window's content panel.
		contentPane.add(mPanel);
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		//Exit the game if the window closed button is closed.
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}
}
