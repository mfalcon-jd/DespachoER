package complemento;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;

public class wallpaper extends JDesktopPane{
	private  Image IMG=new ImageIcon(getClass().getResource("img/fondo.png")).getImage();
	 
    public void paintChildren(Graphics g){
        g.drawImage(IMG, 0, 0, getWidth(), getHeight(), this);
        super.paintChildren(g);
    }

}
