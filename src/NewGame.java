

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class NewGame {
	
	private Design d;
	private Buttons button;
	private Statistics stat;
	private boolean status,end=false;
	private int stage;
	
	public void startGame()
	{
		//d=null;
		stat = new Statistics();
		SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				//System.out.println("new game");
				Preferences pref = new Preferences();
				d = new Design("Mine Sweeper",pref.getDifficulty());
				if(pref.getDifficulty()==81)
					stage = 0;
				else if(pref.getDifficulty()==16*16)
						stage = 1;
				else
					stage = 2;
				d.init();
				d.startGame();
			}
		});
		PlaySound.play("start.wav");
		this.checkGame();
	}
	
	public void checkGame()
	{
		try {
			Thread.sleep(1000);
		}
		catch(InterruptedException ex) {
			JOptionPane.showMessageDialog(null,"Unknown error!\nPlease report the error!");
		}
		end = false;
		while(!end) {
			end = d.getStatus();
			System.out.print("");
		}
		status = d.getWinner();
		if(status)
			this.win();
		else {
			if(!d.getStartTime())
				this.lose();
			else
				this.changeGame();
		}
		this.startGame();
	}
	
	public void lose()
	{
		button = d.getButtons();
		int m[] = button.getMines();
		PlaySound.play("explosion-02.wav");
		int count,time;
		try {
			Thread.sleep(300);
			count=0;
			time=0;
			for(int i=0; i<button.getNumberOfMines(); i++) {
				count++;
				time++;
				if(count==3) {
					Thread.sleep(400);
					count=0;
				}
				if(time==2) {
					Thread.sleep(300);
					time=0;
				}
				button.setText(m[i]);
				Thread.sleep(100);
			}
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
		PlaySound.play("lose.wav");
		stat.newGame(false,stage);
		JOptionPane.showMessageDialog(null, "You Loose\nGamePlayed "+stat.getGamesPlayed(stage)+"\nGames Won "+stat.getGamesWon(stage)+"\nBest Time "+stat.getBestTime(stage));
		d.dispose();
	}

	public void win()
	{
		String time;
		stat.newGame(true,stage);
		stat.time(d.getTime(), 0);
		PlaySound.play("win.wav");
		if(stat.getBestTime(stage)==Integer.MAX_VALUE)
			time = "N/A";
		else
			time = ""+stat.getBestTime(stage);
		JOptionPane.showMessageDialog(null,"You win\nGamePlayed "+stat.getGamesPlayed(stage)+"\nGames Won "+stat.getGamesWon(stage)+"\nBest Time "+time);
		d.dispose();
	}
	
	public void changeGame()
	{
		d.dispose();
	}
}
