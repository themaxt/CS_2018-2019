
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PanelBlackjack extends JPanel {

	public static final int WIDTH = 1536;
	public static final int HEIGHT = 864;
	public static final Color BACKGROUND = new Color(0, 128, 43);

	private final int delayLength = 50;

	private BufferedImage image;

	private BlackjackGameManager manager;

	private int numberOfPlayers;

	public PanelBlackjack() {
		numberOfPlayers = 2;
		// initialize manager
		manager = new BlackjackGameManager(numberOfPlayers, this);

		// set up graphics
		getPanelGraphics();

		// starting game thread
		Thread gameThread = new Thread(new GameRunnable());
		gameThread.start();
	}

	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	private Graphics getPanelGraphics() {

		if (null == image) {
			image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		}
		Graphics graphics = image.getGraphics();
		graphics.setColor(BACKGROUND);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		return graphics;

	}

	public class GameRunnable implements Runnable {

		public void run() {
			manager.playRound();
			try {
				Thread.sleep(10);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			this.run();
		}
	}

	public void updateGraphics() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					draw();
					try {
						Thread.sleep(delayLength);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		Graphics g = getPanelGraphics();
		ArrayList<BlackjackPlayer> players = manager.getPlayers();
		BlackjackDealer dealer = manager.getDealer();

		for (int i = 0; i < players.size(); i++) { // iterates through all players
			players.get(i).setCardPositions();
			players.get(i).display(g);
		}
		dealer.setCardPositions();
		dealer.display(g);

		repaint();
	}

}
