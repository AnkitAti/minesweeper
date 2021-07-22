

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Design extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private Buttons gameButtons;
	private JLabel time,mines;
	private JTextField timeIndicator, minesIndicator;
	private int difficulty = 81;
	private JPanel mainPanel,panel,extras;
	private int minesRemaining;
	private int[] buttonStatus;
	private boolean endGame = true;
	private boolean startTime = true,win = false;
	private TimeCount showTime;
	
	public Design(String title,int difficulty)
	{
		super(title);
		this.difficulty = difficulty;
		gameButtons = new Buttons(this.difficulty);
		minesRemaining = gameButtons.getNumberOfMines();
		buttonStatus = new int[this.difficulty];
		time = new JLabel("Time");
		mines = new JLabel("Mines");
		timeIndicator = new JTextField();
		timeIndicator.setText("00");
		timeIndicator.setEditable(false);
		minesIndicator = new JTextField();
		minesIndicator = new JTextField(""+gameButtons.getNumberOfMines());
		minesIndicator.setEditable(false);
		showTime = new TimeCount(timeIndicator);
	}
	
	public void init()
	{
		mainPanel = new JPanel(new BorderLayout());
		panel = new JPanel(new GridLayout(gameButtons.getRow(),gameButtons.getColumn()));
		extras = new JPanel(new GridLayout(1,4));
		
		JButton[] buttons = gameButtons.getButton();
		for(int i=0; i<difficulty; i++)
		{
			panel.add(buttons[i]);
			buttons[i].addMouseListener(new MouseClick(i));
		}
		
		
		extras.add(time);
		extras.add(timeIndicator);
		extras.add(mines);
		extras.add(minesIndicator);
		
		mainPanel.add(panel);
		mainPanel.add(BorderLayout.SOUTH,extras);
	}
	
	public void startGame()
	{
		setJMenuBar(setMenu());
		add(mainPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		if(difficulty==81)
			setSize(400,400);
		else if(difficulty==16*16)
			setSize(700,730);
		else
			setSize(1380,730);
		setIconImage(new ImageIcon(getClass().getResource("bomb.png")).getImage());
		setResizable(false);
		setVisible(true);
	}
	
	public boolean getStatus()
	{
		return (!endGame);
	}
	
	public Buttons getButtons()
	{
		return gameButtons;
	}
	
	public boolean getWinner()
	{
		return win;
	}
	
	public int getTime()
	{
		return Integer.parseInt(timeIndicator.getText());
	}
	
	public boolean getStartTime()
	{
		return startTime;
	}
	
	public JMenuBar setMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		
		JMenu game = new JMenu("Game");
		
		JMenuItem newGame = new JMenuItem("New Game   ");
		newGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				String str = "Are you sure you want to quit?";
				str += "\nThis will count as a loss in your statistic.";
				if(!startTime) {
					int option = JOptionPane.showConfirmDialog(null, str);
					if(option==JOptionPane.YES_OPTION) {
						win = false;
						endGame = false;
					}
				}
				else {
					win = false;
					endGame = false;
				}
			}
		});
		
		JMenuItem options = new JMenuItem("Options   ");
		options.addActionListener(new Options());
		JMenuItem statistics = new JMenuItem("Statistics  ");
		statistics.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				ShowStatistics stat = new ShowStatistics();
				stat.showSelectedItem();
				stat.showStat();
			}
		});
		JMenuItem exit = new JMenuItem("Exit     ");
		final Design d = this;
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				try {
					d.finalize();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		game.add(newGame);
		game.add(options);
		game.add(statistics);
		game.add(exit);
		
		JMenu help = new JMenu("Help");
		JMenuItem aboutGame = new JMenuItem("About Minesweeper");
		aboutGame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				String about = "Minesweeper is a single-player video game.\n";
						about+="The object of the game is to clear an abstract minefield without detonating a mine.";
						about+="\nThe game has been written for many system platforms in use today.";
				JOptionPane.showMessageDialog(null,about,"About",0,(Icon)(new ImageIcon(getClass().getResource("bomb.png"))));
			}
		});
		JMenuItem aboutMe = new JMenuItem("About Developer");
		aboutMe.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt)
			{
				String str = "Developer       :   Ankit";
				     str+= "\nCoded in          :   Java (SE)7";
				     str+= "\nVersion            :   Minesweeper 1.0.0.0";
				     str+= "\nDesigned By   :   Ankit";
				     str+= "\nConcept           :   Microsoft";
				     str+= "\nResources      :   Microsoft, stackoverflow.com,en.wikipedia.org";
				     str+= "\nPlease report the bugs to ankmonuati@gmail.com";
				     str+= "\nI'll be extremely greatful for your suggestions and feedback.";
				ImageIcon icon = new ImageIcon(getClass().getResource("developer.png"));
				JOptionPane.showMessageDialog(null,str,"Developers", 0, (Icon)icon);
			}
		});
		JMenuItem helpGame = new JMenuItem("How To Play");
		helpGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				String help = "The game is played by revealing squares of the grid by clicking or otherwise indicating each square.";
						help+= "\nIf a square containing a mine is revealed, the player loses the game."
								+ "\nIf no mine is revealed, a digit is instead displayed in the square, "
								+ "\nindicating how many adjacent squares contain mines; if no mines are adjacent, the square becomes blank."
								+ "\nThe player uses this information to deduce the contents of other squares, and may either safely reveal each square"
								+ "\n or mark the square as containing a mine.";
						JOptionPane.showMessageDialog(null,help,"How To Play",0,(Icon)(new ImageIcon(getClass().getResource("bomb.png"))));
			}
		});
		help.add(aboutGame);
		help.add(aboutMe);
		help.add(helpGame);
		
		menuBar.add(game);
		menuBar.add(help);
		return menuBar;
	}
	
	class MouseClick implements MouseListener
	{
		int i;
		MouseClick(int i)
		{
			this.i = i;
		}
		public void mouseClicked(MouseEvent mouseEvent)
		{
			JButton buttonClicked = (JButton)mouseEvent.getSource();
			switch(mouseEvent.getButton())
			{
			case MouseEvent.BUTTON1:
				if(buttonStatus[i]==0)
				{
					buttonClicked.setEnabled(false);
					int value = gameButtons.getValueOfButton(i);
					if(value!=0)
					{
						win = gameButtons.setText(i);
						endGame = win;
					}
					else if(value==0)
					{
						PlaySound.play("multiple.wav");
						zeroClicked(i);
					}
					buttonStatus[i]=1;
				}
				else if(buttonStatus[i]==2)
				{
					buttonClicked.setIcon(null);
					minesIndicator.setText(""+(++minesRemaining));
					buttonStatus[i]=0;
				}
				break;
			case MouseEvent.BUTTON2:
				break;
			case MouseEvent.BUTTON3:
				if(buttonStatus[i]==0)
				{
					minesIndicator.setText(""+(--minesRemaining));
					buttonClicked.setIcon(new ImageIcon(getClass().getResource("flag.png")));
					buttonStatus[i]=2;
				}
				break;
			}
			if(startTime) {
				startTime = false;
				showTime.start();
			}
			
			if(gameButtons.getEnabledButtons()==gameButtons.getNumberOfMines()) {
				endGame = false;
				win = true;
				showTime.stop();
			}
		}
		public void mousePressed(MouseEvent mouseEvent){}
		public void mouseReleased(MouseEvent mouseEvent){}
		public void mouseEntered(MouseEvent mouseEvent){}
		public void mouseExited(MouseEvent mouseEvent){}
		
		public void zeroClicked(int clickNumber)
		{
			buttonStatus[clickNumber]=1;
			gameButtons.setDisabled(clickNumber);
			gameButtons.setText(clickNumber);
			int rowNumber=clickNumber/gameButtons.getColumn(),colNumber=clickNumber%gameButtons.getColumn();
			if(gameButtons.getValueOfButton(clickNumber)==0)
			{
				//Upper row
				if(clickNumber-gameButtons.getColumn()>=0 && clickNumber-gameButtons.getColumn()<difficulty && gameButtons.isEnabled(clickNumber-gameButtons.getColumn()) && (rowNumber-1)>=0 && (rowNumber-1)<gameButtons.getRow())
					zeroClicked(clickNumber-gameButtons.getColumn());
				//Lower Row
				if(clickNumber+gameButtons.getColumn()>=0 && clickNumber+gameButtons.getColumn()<difficulty && gameButtons.isEnabled(clickNumber+gameButtons.getColumn()) && (rowNumber+1)>=0 && (rowNumber+1)<gameButtons.getRow())
					zeroClicked(clickNumber+gameButtons.getColumn());
				//Left Column
				if(clickNumber-1>=0 && clickNumber-1<difficulty && gameButtons.isEnabled(clickNumber-1) && (colNumber-1)>=0 && (colNumber-1)<gameButtons.getColumn())
					zeroClicked(clickNumber-1);
				//Right Column
				if(clickNumber+1>=0 && clickNumber+1<difficulty && gameButtons.isEnabled(clickNumber+1) && (colNumber+1)>=0 && (colNumber+1)<gameButtons.getColumn())
						zeroClicked(clickNumber+1);
				//Upper Row Diagonal Left
				if(clickNumber-gameButtons.getColumn()-1>=0 && clickNumber-gameButtons.getColumn()-1<difficulty && gameButtons.isEnabled(clickNumber-gameButtons.getColumn()-1) && (rowNumber-1)>=0 && (rowNumber-1)<gameButtons.getRow() && (colNumber-1>=0))
					zeroClicked(clickNumber-gameButtons.getColumn()-1);
				//Upper Row Diagonal Right
				if(clickNumber-gameButtons.getColumn()+1>=0 && clickNumber-gameButtons.getColumn()+1<difficulty && gameButtons.isEnabled(clickNumber-gameButtons.getColumn()+1) && (rowNumber-1)>=0 && (rowNumber-1)<gameButtons.getRow() && (colNumber+1)<gameButtons.getColumn())
					zeroClicked(clickNumber-gameButtons.getColumn()+1);
				//Lower Row Diagonal Left
				if(clickNumber+gameButtons.getColumn()-1>=0 && clickNumber+gameButtons.getColumn()-1<difficulty && gameButtons.isEnabled(clickNumber+gameButtons.getColumn()-1) && (rowNumber+1)>=0 && (rowNumber+1)<gameButtons.getRow() && (colNumber-1>=0))
					zeroClicked(clickNumber+gameButtons.getColumn()-1);
				//Lower Row Diagonal Right
				if(clickNumber+gameButtons.getColumn()+1>=0 && clickNumber+gameButtons.getColumn()+1<difficulty && gameButtons.isEnabled(clickNumber+gameButtons.getColumn()+1) && (rowNumber+1)>=0 && (rowNumber+1)<gameButtons.getRow() && (colNumber+1<gameButtons.getColumn()))
					zeroClicked(clickNumber+gameButtons.getColumn()+1);
			}
		}
	}
	
	class Options implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			OptionFrame opt = new OptionFrame("Options");
			opt.showFrame();
		}
	}
}
