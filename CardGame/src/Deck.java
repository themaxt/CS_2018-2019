import java.util.ArrayList;

public class Deck {
	public static final int SIZE = 52;
	private ArrayList<Card> deck;

	public Deck() {
		deck = new ArrayList<Card>();
		initialize();
	}

	public void initialize() {
		for (int suit = 0; suit < 4; suit++) {
			for (int card = 1; card < 14; card++) {
				deck.add(new Card(card, suit));
			}
		}
	}

	public void clear() {
		deck.clear();
	}

	public Card draw() {
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}

	public void add(Card card) {
		deck.add(card);
	}
	
	public int getLength() {
		return deck.size();
	}

	public void shuffle() {
		ArrayList<Double> shuffler = new ArrayList<Double>();
		for (int i = 0; i < SIZE; i++) {
			shuffler.add(Math.random());
		}

		for (int i = 0; i < SIZE - 1; i++) {
			for (int k = 0; k < SIZE - i - 1; k++) {
				if (shuffler.get(k) > shuffler.get(k + 1)) {
					double temp = shuffler.get(k);
					shuffler.set(k, shuffler.get(k + 1));
					shuffler.set(k + 1, temp);

					Card temp2 = deck.get(k);
					deck.set(k, deck.get(k + 1));
					deck.set(k + 1, temp2);
				}
			}
		}
	}

	@Override
	public String toString() {
		String str = "";
		for (Card card : deck) {
			str += card.getSimple() + "\n";
		}
		return str;
	}
}
