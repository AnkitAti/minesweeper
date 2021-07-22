

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class Preferences implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static final String FILE = "pref.dat";
	private int difficulty=81;
	private static FileInputStream streamIn;
	private static FileOutputStream streamOut;
	private static ObjectOutputStream oo;
	private static ObjectInputStream oi;
	
	public Preferences()
	{
		try {
			streamIn = new FileInputStream(FILE);
			streamIn.close();
		}
		catch(IOException ex) {
			try {
				streamOut = new FileOutputStream(FILE);
				oo = new ObjectOutputStream(streamOut);
				oo.writeObject(this);
				streamOut.close();
				oo.close();
				System.out.println();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void setDifficulty(int difficulty)
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			Preferences pref = (Preferences)oi.readObject();
			streamIn.close();
			oi.close();
			pref.difficulty = difficulty;
			streamOut = new FileOutputStream(FILE);
			oo = new ObjectOutputStream(streamOut);
			oo.writeObject(pref);
			streamOut.close();
			oo.close();
		}
		catch(IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unable to set your preferences.\nPlease report the bug.");
		}
	}
	
	public int getDifficulty()
	{
		try {
			streamIn = new FileInputStream(FILE);
			oi = new ObjectInputStream(streamIn);
			this.difficulty = ((Preferences)oi.readObject()).difficulty;
			oi.close();
			streamIn.close();
			return this.difficulty;
		}
		catch(IOException | ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Unable to get your preferences.\nPlease report the bug.");
		}
		return 81;
	}
}
