import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	private int faceValue; // the numeric value of the card (1 – 13 → A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q,
							// K)
	private int suit; // the numeric value of the suit (0 – 3)
	public static final int CLUBS = 0, DIAMONDS = 1, HEARTS = 2, SPADES = 3;
	public static final int ACE = 1, JACK = 11, QUEEN = 12, KING = 13;
	
	//graphics
	private int x;
	private int y;
	private boolean faceUp; // whether the card is face up or face down
	public static final int WIDTH = 500;
	public static final int HEIGHT = 726;
	public static final double SIZE = 0.25;

	public Card(int faceValue, int suit) {
		this.faceValue = faceValue;
		this.suit = suit;
		this.faceUp = true;
		this.x = 0;
		this.y = 0;
	}

	public int getFaceValue() {
		return faceValue;
	}

	public String getFaceValueAsString() {
		if (faceValue == ACE)
			return "Ace";
		else if (faceValue == JACK)
			return "Jack";
		else if (faceValue == QUEEN)
			return "Queen";
		else if (faceValue == KING)
			return "King";
		else
			return Integer.toString(faceValue);
	}

	public int getSuit() {
		return suit;
	}

	public String getSuitAsString() {
		if (suit == CLUBS) {
			return "Clubs";
		} else if (suit == DIAMONDS) {
			return "Diamonds";
		} else if (suit == HEARTS) {
			return "Hearts";
		} else {
			return "Spades";
		}
	}

	public boolean isFaceUp() {
		return faceUp;
	}

	public void setFaceUp(boolean faceUp) {
		this.faceUp = faceUp;
	}

	public String getSimple() { // returns a simple representation of the card (S_4). Used for drawing the images
		String str = "";
		if (suit == CLUBS) {
			str += "C";
		} else if (suit == DIAMONDS) {
			str += "D";
		} else if (suit == HEARTS) {
			str += "H";
		} else {
			str += "S";
		}
		str += "_";
		if (faceValue == ACE)
			str += "A";
		else if (faceValue == JACK)
			str += "J";
		else if (faceValue == QUEEN)
			str += "Q";
		else if (faceValue == KING)
			str += "K";
		else
			str += Integer.toString(faceValue);
		return str;
	}

	@Override
	public boolean equals(Object object) {
		boolean equal = false;

		if (object instanceof Card) {
			equal = this.faceValue == ((Card) object).faceValue && this.suit == ((Card) object).suit;
		}

		return equal;
	}

	@Override
	public String toString() {
		return getFaceValueAsString() + " of " + getSuitAsString();
	}
	
	//graphics
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void draw(Graphics g) {
		if (this.isFaceUp()) {
			try {
				BufferedImage cardImage = ImageIO.read(new File("src/card_images/" + this.getSimple() + ".png"));
				double scalar = 1 / Card.SIZE;
				g.drawImage(cardImage, x, y, (int) (Card.WIDTH / scalar), (int) (Card.HEIGHT / scalar), null);
			} catch (IOException ex) {
				System.out.println("could not find image: " + this.getSimple() + ".png");
			}
		} else {
			double scalar = 1 / Card.SIZE;
			g.setColor(Color.gray.darker());
			g.fillRect(x, y, (int) (Card.WIDTH / scalar), (int) (Card.HEIGHT / scalar));
		}
	}

}
