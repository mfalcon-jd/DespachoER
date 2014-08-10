package GUI.login;
import java.awt.Image;

import javax.swing.*;
import org.jvnet.substance.SubstanceLookAndFeel;

import complemento.wallpaper;

public class EjemploLogin{
	public static void main( String args[] )
	{		
	    JFrame.setDefaultLookAndFeelDecorated(true);	    
	    	    
	    try {
	    	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
	    	SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.MistAquaSkin");
	    	//SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.BusinessBlackSteelSkin");
	    	SubstanceLookAndFeel.setCurrentTheme( "org.jvnet.substance.theme.SubstanceAquaTheme" );
	    	SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceMetalWallWatermark");
		}
		catch (Exception e){   
			System.out.println(e);
		}
		/*JFrame.setDefaultLookAndFeelDecorated(true);
	    try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Look " + ex.getMessage());
        }*/
	    Bienvenida bv = new Bienvenida();
		frmLogin aplicacion = new frmLogin();
		aplicacion.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
	}
	
}