

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class Statistics implements Serializable {

	private static final long serialVersionUID = 1L;
	private int[] gamesPlayed;
	private int[] gamesWon;
	private int[] bestTime;
	private static final String FILE = "stat.dll",ERROR_MSG="Unable tp get statistics.\nReport the bug.";
	private static FileOutputStream streamOut;
	private static ObjectOutputStream oo;
	private static FileInputStream streamIn;
	private static ObjectInputStream oi;
	
	
	public Statistics()
	{
		try {
			streamIn = new FileInputStream(FILE);
			streamIn.close();
		}
		catch(IOException e) {
			try {
				streamOut = new FileOutputStream(FILE);
				oo = new ObjectOutputStream(streamOut);
				gamesPlayed = new int[3];
				gamesWon = new int[3];
				bestTime = new int[3];
				for(int i=0; i<3; i++) {
					this.bestTime[i] = Integer.MAX_VALUE;
					this.gamesWon[i] = 0;
					this.gamesPlayed[i] = 0;
				}
				oo.writeObject(this);
				streamOut.close();
				oo.close();
			} catch (FileNotFoundException e1) {
			} catch (IOException e1) {
			}
		}
	}	
	public int getGamesPlayed(int stage)
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			Object obj = oi.readObject();
			streamIn.close();
			oi.close();
			return ((Statistics)(obj)).gamesPlayed[stage];
		}
		catch(IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ERROR_MSG);
		}
		return 0;
	}
	
	public int getGamesWon(int stage)
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			Object obj = oi.readObject();
			oi.close();
			streamIn.close();
			return ((Statistics)(obj)).gamesWon[stage];
		}
		catch(IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ERROR_MSG);
		}
		return 0;
	}
	
	public int getBestTime(int stage)
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			Object obj = oi.readObject();
			streamIn.close();
			oi.close();
			return ((Statistics)(obj)).bestTime[stage];
		}
		catch(IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ERROR_MSG);
		}
		return 0;
	}
	
	public void time(int currentTime,int stage)
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			Object obj = oi.readObject();
			Statistics st = (Statistics)obj;
			if(currentTime<st.bestTime[stage]) {
				st.bestTime[stage] = currentTime;
				streamOut = new FileOutputStream(FILE);
				oo = new ObjectOutputStream(streamOut);
				oo.writeObject(st);
			}
			streamOut.close();
			oo.close();
		}
		catch(IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ERROR_MSG);
		}
	}
	
	public void newGame(boolean win,int stage)
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			Object obj = oi.readObject();
			oi.close();
			streamIn.close();
			oi.close();
			Statistics st = (Statistics)obj;
			st.gamesPlayed[stage]++;
			if(win)
				st.gamesWon[stage]++;
			streamOut = new FileOutputStream(FILE);
			oo = new ObjectOutputStream(streamOut);
			oo.writeObject(st);
			streamOut.close();
			oo.close();
		}
		catch(IOException | ClassNotFoundException ex) {
			JOptionPane.showMessageDialog(null,ERROR_MSG);
		}
	}
}
