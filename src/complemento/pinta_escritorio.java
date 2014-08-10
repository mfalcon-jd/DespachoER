package complemento;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.border.Border;
public class pinta_escritorio implements Border {
	 	private  BufferedImage image ;

	    public pinta_escritorio()
	    {
	    	try {
	            URL imagePath = new URL(getClass().getResource("../img/fondo.png").toString());
	            System.out.println("imagenPath: " + imagePath);
	            image = ImageIO.read(imagePath);
	        }
	    	catch (Exception ex)
	    	{            
	        }
	    }

	    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
	    {
		    int x0 = x+ (width-image.getWidth())/2;
		    int y0 = y+ (height-image.getHeight())/2;
		    g.drawImage(image,x0,y0,null);
		}

	    public Insets getBorderInsets(Component c)
	    {
	    	return new Insets(0,0,0,0);
	    }

	    public boolean isBorderOpaque()
	    {
	    	return true;
	    }
	
}