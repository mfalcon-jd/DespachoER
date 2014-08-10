package GUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.jvnet.substance.*;

import com.codeko.util.Num;

public class estilos 
{
	public estilos()
	{
	}
	
	public void aplicar()
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.MistAquaSkin");
	    	SubstanceLookAndFeel.setCurrentTheme( "org.jvnet.substance.theme.SubstanceAquaTheme" );
	    	SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceMetalWallWatermark");
		}
		catch(Exception e)
		{
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
	}
	
	public URL cargador(String direccion)
	{
		ClassLoader cargador = this.getClass().getClassLoader();
		URL urlImgNodo= cargador.getResource (direccion);
		return urlImgNodo;
	}
	
	public static String getObtieneValorText(JFormattedTextField textField)
	{
		String valor =  "";
		
		 Object dato = textField.getValue();
         Num obj = new Num();
         
         System.out.println("numero correcto " + obj.getDecimal(dato));
         
         return valor = Double.toString(obj.getDecimal(dato));
         
	}
	
	 public static ActionListener  tranfiereElFoco = new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent arg0) {
	            // Se transfiere el foco al siguiente elemento.
	            ((Component) arg0.getSource()).transferFocus();
	        }
	    };
	
	public static JFormattedTextField formatoText(JFormattedTextField txtAnticipo)
	{
		// Formato de visualización
    	NumberFormat dispFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    	// Formato de edición: inglés (separador decimal: el punto)
    	NumberFormat editFormat =
    	                NumberFormat.getNumberInstance(Locale.getDefault());
    	
    	// Para la edición, no queremos separadores de millares
    	editFormat.setGroupingUsed(false);
    	// Creamos los formateadores de números
    	NumberFormatter dnFormat = new NumberFormatter(dispFormat);
    	NumberFormatter enFormat = new NumberFormatter(editFormat);
    	enFormat.setOverwriteMode(true);
    	// Creamos la factoría de formateadores especificando los
    	// formateadores por defecto, de visualización y de edición
    	DefaultFormatterFactory currFactory = new DefaultFormatterFactory(dnFormat, dnFormat, enFormat);
    	
    	// Asignamos la factoría al campo
    	txtAnticipo = new JFormattedTextField();
    	txtAnticipo.setValue(new Float("00.00"));
    	//enFormat.setMaximum(10);
    	txtAnticipo.setFormatterFactory(currFactory);
    	
    	return txtAnticipo;
	}
	public static JLabel formatoLabel(JLabel txtAnticipo)
	{
		// Formato de visualización
    	NumberFormat dispFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
    	// Formato de edición: inglés (separador decimal: el punto)
    	NumberFormat editFormat =
    	                NumberFormat.getNumberInstance(Locale.getDefault());
    	
    	editFormat.format(txtAnticipo);
    	
    	return txtAnticipo;
	}
		
	public static String EliminaCaracteres(String s_cadena, String s_caracteres)
	{
	  String nueva_cadena = "";
	  Character caracter = null;
	  boolean valido = true;
	 
	  /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
	     sólo los caracteres que no estén en la cadena s_caracteres */
	  for (int i=0; i<s_cadena.length(); i++)
	      {
	       valido = true;
	       for (int j=0; j<s_caracteres.length(); j++)
	           {
	            caracter = s_caracteres.charAt(j);
	 
	            if (s_cadena.charAt(i) == caracter)
	               {
	                valido = false;
	                break;
	               }
	           }
	       if (valido)
	           nueva_cadena += s_cadena.charAt(i);
	      }
	 
	  return nueva_cadena;
	}
	
	public void centrarJInternal(JInternalFrame JInternal, JDesktopPane JDesktop)
	{
		Dimension JDesktopSize = JDesktop.getSize();
		Dimension JInternalSize = JInternal.getSize();
		JInternal.setLocation((JDesktopSize.width - JInternalSize.width)/2, (JDesktopSize.height - JInternalSize.height)/2);
	}
	

}
