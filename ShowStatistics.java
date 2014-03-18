 

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;

public class ShowStatistics extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel,panelLeft,panelRight;
	private JComboBox<String >stage;
	
	public ShowStatistics()
	{
		mainPanel = new JPanel(new GridLayout(1,3));
		panelLeft = new JPanel(new GridLayout(3,1));
		panelRight = new JPanel(new GridLayout(4,1));
		stage = new JComboBox<String>();
		stage.addItem("Beginner");
		stage.addItem("Intermediate");
		stage.addItem("Advanced");
		stage.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				showSelectedItem();
			}
		});
		panelLeft.add(new JLabel("    "));
		panelLeft.add(stage);
		panelLeft.add(new JLabel("    "));
	}
	
	public void showSelectedItem()
	{
		int diff;
		Statistics stat = new Statistics();
		diff = stage.getSelectedIndex();
		String time;
		if(stat.getBestTime(diff)==Integer.MAX_VALUE)
			time = "N/A";
		else
			time = ""+stat.getBestTime(diff);
		panelRight.add(new JLabel("Best Time          "+time));
		panelRight.add(new JLabel("Games Played   "+stat.getGamesPlayed(diff)));
		panelRight.add(new JLabel("Games Won          "+stat.getGamesWon(diff)));
		panelRight.add(new JLabel("Win Percentage "+(((double)(stat.getGamesWon(diff))/stat.getGamesPlayed(diff))*100)));
	}
	
	public void showStat()
	{
		mainPanel.add(panelLeft);
		mainPanel.add(new JLabel(" "));
		mainPanel.add(panelRight);
		add(mainPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400,200);
		setVisible(true);
	}

}
