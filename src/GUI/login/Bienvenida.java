package GUI.login;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Bienvenida extends JDialog {	
		
	private JProgressBar progressBar;	
	private Timer timer2, timer;
	int num = 0;
	Imagen imb = new Imagen();		
	
	public Bienvenida()
	{			
		JFrame f = new JFrame();
		BorderLayout border = new BorderLayout();
		f.setLayout(border);		
		final JDialog dialog = new JDialog(f, "Test", true);
		progressBar = new JProgressBar(0, 100);		
		progressBar.setStringPainted(true);
		progressBar.setBorderPainted(false);
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add(imb, BorderLayout.CENTER);
		dialog.getContentPane().add(progressBar, BorderLayout.SOUTH);			
		dialog.setBounds(100, 100, 450, 250);
		dialog.setUndecorated(true);
		dialog.setLocationRelativeTo(this.getParent());		

		timer = new Timer(2000, new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{								
				dialog.setVisible(false);
				dialog.dispose();
			}
		});	
		timer2 = new Timer(2, new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(num <= 100)
				{
					progressBar.setValue(num);
					num = num + 1;
				}
			}
		});	
		timer2.start();
		timer.setRepeats(false);
		timer.start();	
		dialog.setVisible(true);
		System.out.println("Cerrando Ventana");		
	}			
}
