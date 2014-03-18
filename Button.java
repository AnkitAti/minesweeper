

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Buttons {
	
	private JButton[] button;
	private int[] buttons;
	private int numOfMines;
	private int numOfButtons;
	private int[] mines;
	private int row,col;
	
	public Buttons(int numOfButtons)
	{
		this.numOfButtons = numOfButtons;
		buttons = new int[this.numOfButtons];
		button = new JButton[this.numOfButtons];
		
		for(int i=0; i<this.numOfButtons; i++)
			button[i] = new JButton();
		
		if(numOfButtons==9*9)
		{
			numOfMines = 10;
			row=col=9;
		}
		else if(numOfButtons==16*16)
		{	
			numOfMines = 40;
			row=16;
			col=16;
		}
		else
		{
			numOfMines = 90;
			row=16;
			col=30;
		}
		
		mines = new int[numOfMines];
		
		for(int i=0; i<numOfMines; i++)
		{
			boolean flag = true;
			int rand = (int)(Math.random()*numOfButtons);
			for(int j=0; j<i; j++)
				if(rand==mines[j])
				{
					i--;
					flag=false;
					break;
				}
			if(!flag)
				continue;
			mines[i] = rand;
			buttons[rand]=-1;
		}
		/*for(int i=0; i<numOfMines; i++)
				System.out.println(mines[i]);
		*/
		for(int i=0; i<numOfMines; i++)
			for(int j=mines[i]%col-1; j<=mines[i]%col+1; j++)
				for(int k=mines[i]/col-1; k<=mines[i]/col+1; k++)
					if((j>=0 && k>=0) && (j<=col-1 && k<=row-1))
						if(buttons[k*col+j]!=-1)
							buttons[k*col+j]++;
	}
	
	public JButton[] getButton()
	{
		return button;
	}
	
	public int getValueOfButton(int i)
	{
		return buttons[i];
	}
	
	public int getNumberOfMines()
	{
		return numOfMines;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public int getColumn()
	{
		return col;
	}
	
	public void setDisabled(int i)
	{
		button[i].setEnabled(false);
	}
	
	public boolean setText(int num)
	{
		if(buttons[num]>0) {
			button[num].setText(""+buttons[num]);
			return true;
		}
		else if(buttons[num]==-1) {
			PlaySound.play("lose.wav");
			button[num].setIcon(new ImageIcon(getClass().getResource("bomb.png")));
			button[num].setEnabled(true);
			return false;
		}
		return true;
	}
	
	public boolean isEnabled(int i)
	{
		return button[i].isEnabled();
	}
	
	public int[] getMines()
	{
		return mines;
	}
	
	public int getEnabledButtons()
	{
		int retNumber=0;
		for(int i=0; i<numOfButtons; i++)
			if(button[i].isEnabled())
				retNumber++;
		return retNumber;
	}
}
