import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;

public class GridBagFenetre {
	public static void addComponentsToPane(Container pane) {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) screenSize.getWidth() / 100;
		int y = (int) screenSize.getHeight() / 100;

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.ipady = 15 * y;
		c.ipadx = 40 * x;
		c.insets = new Insets(10, 5, 10, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		addAButton("Round 10", pane, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.ipady = 70 * y;
		c.ipadx = 20 * x;
		addAButton("Dominos manche", pane, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridheight = 1;
		addAButton("Dominos suivants", pane, c);

		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		int max = Math.max(x, y);
		c.ipady = 40 * max;
		c.ipadx = 40 * max;
		addAButton("Plateau  test", pane, c);

	}

	private static void addAButton(String text, Container container, GridBagConstraints c) {
		
		JButton button = new JButton(text);

		button.setFont(new Font("Book Antiqua", Font.PLAIN, 20));
	
		container.add(button, c);

	}

	
	private static void createAndShowGUI() {
		JFrame frame = new JFrame("BoxLayoutDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentsToPane(frame.getContentPane());

		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setTitle("Test");
		frame.setVisible(true);
		frame.setResizable(false);

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}