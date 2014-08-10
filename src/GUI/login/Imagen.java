package GUI.login;
import java.awt.Graphics;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.estilos;

@SuppressWarnings("serial")
public class Imagen extends JPanel
{
	ImageIcon i = null;
	
	public Imagen()
	{		
		estilos es = new estilos();
		URL URLLogo = es.cargador("img/logo2.png");
		i= new ImageIcon(URLLogo);
		this.setSize(450, 230);
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(i.getImage(), 0, 0, null);
	}
}
