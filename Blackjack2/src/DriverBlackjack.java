
import javax.swing.JFrame;

public class DriverBlackjack {
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Blackjack");
		frame.setSize(PanelBlackjack.WIDTH, PanelBlackjack.HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new PanelBlackjack());
		frame.setVisible(true);
	}
}
