import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class BlackjackPlayer extends Player {

	private int totalMoney;
	private int bet;

	// ints that show the most recent transaction (+30)
	private int totalMoneyTransaction;

	private int playerStatus;

	// player status
	public static final int NORMAL = 1;
	public static final int BUST = 2;
	public static final int DOUBLE_DOWN = 3;
	public static final int SURRENDER = 4;
	public static final int BLACKJACK = 5;

	public BlackjackPlayer(String name, int totalMoney, int x, int y) {
		super(name, x, y);
		this.totalMoney = totalMoney;
		this.bet = 0;
		this.playerStatus = 1;
	}

	public int getPlayerStatus() {
		return playerStatus;
	}

	public void setPlayerStatus(int playerStatus) {
		this.playerStatus = playerStatus;
	}
	
	public String getStatusString() {
		if(getPlayerStatus()==BUST)
			return "Bust";
		else if(getPlayerStatus()==DOUBLE_DOWN)
			return "Double Down";
		else if(getPlayerStatus()==SURRENDER)
			return "Surrender";
		else if(getPlayerStatus()==BLACKJACK)
			return "Blackjack";
		else
			return "";
	}

	public int getHandValue() { // need case when player has 2 Aces!!!
		int sum1 = 0; // sum with Ace equaling 1
		int sum2 = 0; // sum with Ace equaling 11
		for (Card card : this.getHand()) {
			int faceValue = card.getFaceValue();
			if (faceValue == 1) { // Ace
				sum1 += 1;
				sum2 += 11;
			} else if (faceValue >= 11) { // Jack, Queen, King
				sum1 += 10;
				sum2 += 10;
			} else {
				sum1 += faceValue;
				sum2 += faceValue;
			}
		}
		if (sum2 > 21) { // if sum with Ace equaling 11 is a bust, return the other one
			return sum1;
		} else {
			return sum2;
		}
	}

	public int getComparativeHandValue() {
		int handValue = this.getHandValue();
		if (handValue > BlackjackGameManager.BLACKJACK) {// if bust
			return 0;
		}
		return handValue;
	}

	public int getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(int totalMoney) {
		this.totalMoney = totalMoney;
	}

	public void addMoney(int money) {
		this.setTotalMoney(this.getTotalMoney() + money);
		this.setTotalMoneyTransaction(money);
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
	}

	public void addBet(int bet) {
		if (this.getTotalMoney() - bet < 0) {
			bet = this.getTotalMoney();
		}
		this.setBet(this.getBet() + bet);

	}

	public void resetTransactions() {
		setTotalMoneyTransaction(0);
	}

	public int getTotalMoneyTransaction() {
		return totalMoneyTransaction;
	}

	public void setTotalMoneyTransaction(int totalMoneyTransaction) {
		this.totalMoneyTransaction = totalMoneyTransaction;
	}

	@Override
	public String toString() {
		return getName() + ": " + getHandValue() + " | Cards: " + getHand();
	}

	// graphics

	public void setCardPositions() {
		ArrayList<Card> hand = this.getHand();
		for (int c = 0; c < hand.size(); c++) {// iterates through all cards
			hand.get(c).setX(c * 25 + this.getX());
			hand.get(c).setY(75 + c * 25 + this.getY());
		}
		this.setHand(hand);
	}

	public void display(Graphics g) {
		// name
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", Font.BOLD, 32));
		g.drawString(this.getName(), this.getX(), this.getY());

		// bet
		g.setColor(Color.BLACK);
		g.setFont(new Font("MonoSpaced", Font.BOLD, 20));
		g.drawString("Bet: " + this.getBet(), this.getX(), this.getY() + 35);

		// total money
		g.setColor(Color.BLACK);
		g.drawString("Total: " + this.getTotalMoney(), this.getX(), this.getY() + 60);

		setTransactionColor(g, this.getTotalMoneyTransaction());
		g.drawString(getTransaction(this.getTotalMoneyTransaction()), this.getX() + 130, this.getY() + 60);

		for (Card card : this.getHand())
			card.draw(g);
	}

	protected void setTransactionColor(Graphics g, int transaction) {
		if (transaction > 0) { // positive
			g.setColor(Color.GREEN);
		} else if (transaction < 0) { // negative
			g.setColor(Color.RED);
		} else {
			g.setColor(Color.WHITE);
		}
	}

	protected String getTransaction(int transaction) { // (+10 or -10)
		String str = "";
		if (transaction >= 0) { // positive or zero
			str += "+";
		}

		str += transaction;
		return str;
	}

}
