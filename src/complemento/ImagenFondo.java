package complemento;

import java.awt.Dimension;
import java.awt.Graphics;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;

import GUI.estilos;

@SuppressWarnings("serial")
public class ImagenFondo extends JDesktopPane
{
	ImageIcon i = null;
	
	public ImagenFondo(JDesktopPane desktop)
	{		
		
		estilos es = new estilos();
		URL URLLogo = es.cargador("img/fondo.png");
		i= new ImageIcon(URLLogo);				
		this.setSize(i.getIconWidth(), i.getIconHeight());
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(i.getImage(), 0, 0, null);
	}
}
