

//import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.TimerTask;

import javax.swing.Timer;
import javax.swing.JTextField;

public class TimeCount implements ActionListener {
	int time = 0;
	JTextField showTime;
	Timer t; 
	
	public TimeCount(JTextField showTime)
	{
		this.showTime = showTime;
	}
	public void start()
	{
		t = new Timer(1000,this);
		/*t.scheduleAtFixedRate(new TimerTask()
		{
			public void run()
			{
				time++;
				showTime.setText("0"+time);
			}
		}, 0, 1000);*/
		t.start();
	}
	
	public void stop() {
		/*time = Integer.parseInt(showTime.getText());
		t.scheduleAtFixedRate(new TimerTask()
		{
			public void run() {
				time--;
			}
		},0,1000);*/
		t.stop();
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		time++;
		showTime.setText("0"+time);
	}
}
