import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class BlackjackDealer extends BlackjackPlayer {

	public BlackjackDealer(String name, int totalMoney, int x, int y) {
		super(name, totalMoney, x, y);
	}
	
	@Override
	public void addMoney(int money) {
		this.setTotalMoney(this.getTotalMoney() + money);
		this.setTotalMoneyTransaction(this.getTotalMoneyTransaction()+money);
	}
	
	public void setFaceUp(int cardNum, boolean faceUp) { // sets a card in the hand to be faceUp/faceDown
		ArrayList<Card> dealerHand = this.getHand();
		Card card = dealerHand.get(cardNum);
		card.setFaceUp(faceUp);
		dealerHand.set(cardNum, card);
		this.setHand(dealerHand);
	}

	// graphics

	@Override
	public void setCardPositions() {
		ArrayList<Card> hand = this.getHand();
		for (int c = 0; c < hand.size(); c++) {// iterates through all cards
			int adjustedCardSize = (int) (Card.SIZE * Card.WIDTH);
			hand.get(c).setX(c * (adjustedCardSize + 10) + this.getX() - (hand.size() - 1) * adjustedCardSize / 2);
			hand.get(c).setY(50 + this.getY());
		}
		this.setHand(hand);
	}

	@Override
	public void display(Graphics g) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.BOLD, 32));
		g.drawString(this.getName(), this.getX(), this.getY());

		// total money
		g.setColor(Color.BLACK);
		g.setFont(new Font("MonoSpaced", Font.BOLD, 20));
		g.drawString("Total: " + (this.getTotalMoney()), this.getX(), this.getY() + 35);

		setTransactionColor(g, this.getTotalMoneyTransaction());
		g.drawString(getTransaction(this.getTotalMoneyTransaction()), this.getX() + 150, this.getY() + 35);

		for (Card card : this.getHand())
			card.draw(g);
	}
}
