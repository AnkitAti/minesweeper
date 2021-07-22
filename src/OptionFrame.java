

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
public class OptionFrame extends JFrame{

	private JRadioButton easy,inter,advance;
	private JPanel mainPanel,panel1,panel2;
	private JButton save,cancel;
	private static final long serialVersionUID = -271056960433744181L;
	
	public OptionFrame(String title)
	{
		super(title);
		mainPanel = new JPanel(new BorderLayout());
		
		panel1 = new JPanel(new GridLayout(12,1));
		easy = new JRadioButton("     10 Mines");
		inter = new JRadioButton("      40 Mines");
		advance = new JRadioButton("      99 Mines");
		int difficult = new Preferences().getDifficulty();
		if(difficult == 81)
			easy.setSelected(true);
		else if(difficult == 16*16)
			inter.setSelected(true);
		else
			advance.setSelected(true);
		ButtonGroup diff = new ButtonGroup();
		diff.add(easy);
		diff.add(inter);
		diff.add(advance);
		
		panel1.add(new JLabel("            9X9 Grid"));
		panel1.add(easy);
		//panel1.add(new JLabel("            10 Mines"));
		panel1.add(new JLabel("   "));
		panel1.add(new JLabel("            16X16 Grid"));
		panel1.add(inter);
		//panel1.add(new JLabel("            40 Mines"));
		panel1.add(new JLabel("   "));
		panel1.add(new JLabel("            16X40 Grid"));
		panel1.add(advance);
		//panel1.add(new JLabel("            99 Mines"));
		panel1.add(new JLabel("   "));
		
		panel2 = new JPanel(new GridLayout(1,2));
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				Preferences pref = new Preferences();
				int difficulty;
				if(easy.isSelected())
					difficulty = 81;
				else if(inter.isSelected())
					difficulty = 16*16;
				else
					difficulty = 16*40;
				pref.setDifficulty(difficulty);
				close();
			}
		});
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				close();
			}
		});
		panel2.add(save);
		panel2.add(cancel);
		mainPanel.add(BorderLayout.NORTH,new JLabel("Choose Difficulty"));
		mainPanel.add(BorderLayout.CENTER,panel1);
		mainPanel.add(BorderLayout.SOUTH,panel2);
	}
	
	public void showFrame()
	{
		add(mainPanel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(200,250);
		setVisible(true);
	}
	
	public void close()
	{
		this.dispose();
	}
}
