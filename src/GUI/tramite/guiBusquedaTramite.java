package GUI.tramite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import bd.DBConsultarCasos;
import bd.DBConsultarEtapas;
import bd.DBConsultarTramite;
import bd.DbActualizar;
import bd.DBActualizarEstadoCuenta;
import bd.DbActualizarTramite;
import bd.DbEliminarAgenda;

import Agenda.guiAgendaModificar;
import GUI.estilos;

import javax.swing.JTable;

public class guiBusquedaTramite extends JInternalFrame implements ActionListener {

	private JInternalFrame frmBusqueda;
	private JTextField     txtTramite;
	//private JTextField     textAnticipo;
	private JFormattedTextField textAnticipo;
	//private JTextField     textCostototal;
	private JFormattedTextField textCostototal;
	static JTable         tableTramite;
	private JTable         tableClientes;
	private JTextArea      textObservaciones;
	JTextArea              textDescripcion;
	private JTextField     txtEtapa;
	static JButton        btnCambiarEtapa;
	static JButton        btnExaminar;
	private JScrollPane    spClientes;
	private JScrollPane    spTramites;
	
	String                 sCamposFaltantes;
	private JComboBox      cmbBusquedaTramite;
	JComboBox              comboTipoPago;
	JComboBox              comboCaso;
	private DefaultComboBoxModel jtmCasos;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnBuscar;
	static JButton btnModificar;
	
	MiModelo2 dtmCliente;
	MiModelo2 dtmTramites;
	Object [][] vConsultaTramite;
	
	String[] columNamesClientes = {"Curp","Nombre","Apellido Paterno", "Apellido Materno"};
	String[] columNamesTramites = {"No Tramite","Caso","Nombre Caso", "Descripcion","Observaciones","Precio","TipoPago","Anticipo","Estatus","Etapa","idEtapa"};
	
	String[] descripciones;
	String[] precios;
	String[] idCasos;
	
	String sCaso, sDescripcion, sCostoTotal, sAnticipo, sTipoPago, sObservaciones;
	
	private int fila = -1;
	private int columna;
		
	static int filaTramite = -1;
	private int columnaTramite;
	
	static String sIdTramite;
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		
		estilos obj = new estilos();
		obj.aplicar();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiBusquedaTramite window = new guiBusquedaTramite();
					window.frmBusqueda.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/
	public boolean validarCampos()
	{
		boolean lCampoVacio = false;
		sCamposFaltantes = "";
		
		if (sDescripcion.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Descripcion es obligatorio\n";
		}								
		if(sCaso == "Seleccionar")
		{
			lCampoVacio      = true;
			sCamposFaltantes += "Debe seleccionar el Tipo de Caso\n";
		}
		if(sTipoPago == "Seleccionar")
		{
			lCampoVacio      = true;
			sCamposFaltantes += "Debe Seleccionar el Tipo de Pago\n";
		}
		if(sTipoPago == "Pagos" && sCostoTotal.length() == 0)
		{
			lCampoVacio      = true;
			sCamposFaltantes += "Debe Proporcionar Anticipo";
		}
				
		return lCampoVacio;
	}
	public void llenaCampos()
	{
		int filaAux;
		if(filaTramite == -1) filaAux = 0;
		else filaAux = filaTramite;
		
		if(vConsultaTramite.length != 0)btnModificar.setEnabled(true);
		textObservaciones.setText(String.valueOf(tableTramite.getValueAt(filaAux,4)));//precio
		textAnticipo.setValue(new Double(String.valueOf(tableTramite.getValueAt(filaAux,5))));//tipo pago
		textCostototal.setValue(new Double(String.valueOf(tableTramite.getValueAt(filaAux,7))));//anticipo
		System.out.println("String.valueOf(tableTramite.getValueAt(filaAux,7)) " + String.valueOf(tableTramite.getValueAt(filaAux,7)));
		comboCaso.setSelectedItem(String.valueOf(tableTramite.getValueAt(filaAux,2)));
		comboTipoPago.setSelectedItem(String.valueOf(tableTramite.getValueAt(filaAux,6)));
		textDescripcion.setText(String.valueOf(tableTramite.getValueAt(filaAux,3)));
		txtEtapa.setText(String.valueOf(tableTramite.getValueAt(filaAux,9)));		
	}
	
	public void obtenerPago()
	{
		int filaAux;
		if(filaTramite == -1) filaAux = 0;
		else filaAux = filaTramite;
		textCostototal.setValue(new Double(String.valueOf(tableTramite.getValueAt(filaAux,7))));//anticipo
	}
	public void actualizarTabla()
	{   
		int filaAux;
		if (filaTramite == -1)
		{
			filaAux = 0;
		}
		else filaAux = filaTramite;
		
		tableTramite.setValueAt(textObservaciones.getText(), filaAux, 4);
		tableTramite.setValueAt(estilos.getObtieneValorText(textAnticipo), filaAux, 5);
		tableTramite.setValueAt(estilos.getObtieneValorText(textCostototal), filaAux, 7);
		tableTramite.setValueAt(comboCaso.getSelectedItem().toString(), filaAux, 2);
		tableTramite.setValueAt(comboTipoPago.getSelectedItem().toString(), filaAux, 6);
		tableTramite.setValueAt(textDescripcion.getText(), filaAux, 3);
	}
	public void obtieneValoresFormularios()
	{
		if(comboCaso.getSelectedIndex() == 0)
			sCaso        = comboCaso.getSelectedItem().toString();
		else sCaso       = idCasos[comboCaso.getSelectedIndex() - 1];
		
		//sCaso = idCasos[comboCaso.getSelectedIndex()-1];

		sDescripcion = textDescripcion.getText();
		sCostoTotal = estilos.getObtieneValorText(textAnticipo);
		sAnticipo = estilos.getObtieneValorText(textCostototal);
		sTipoPago = comboTipoPago.getSelectedItem().toString();
		sObservaciones = textObservaciones.getText();  
	
	}
	public void ActualizarTramite()
	{
		boolean bBanderaCorrecto;
		String sConsulta = "";
		String sClaveTramite;
		
		boolean lCorrecto;
        
        obtieneValoresFormularios();
        lCorrecto = validarCampos();
        if (lCorrecto == true)
        {
        	System.out.println("Campos Faltantes " + sCamposFaltantes);
        	JOptionPane.showMessageDialog(this, sCamposFaltantes, "ERROR", JOptionPane.INFORMATION_MESSAGE );
        }
        else
        {
        	int filaAux = 0;
        	if(filaTramite == -1) filaAux = 0;
        	else filaAux = filaTramite;
        	
        	sClaveTramite = String.valueOf(tableTramite.getValueAt(filaAux,0));
        	
        	if(sConsulta.length() == 0 ) sConsulta += "idCaso = '" + sCaso + "'";
    		else sConsulta += ", idCaso = '" + sCaso + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "observaciones = '" + sObservaciones + "'";
    		else sConsulta += ", observaciones = '" + sObservaciones + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "tipoPago = '" + sTipoPago + "'";
    		else sConsulta += ", tipoPago = '" + sTipoPago + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "anticipo = '" + sAnticipo + "'";
    		else sConsulta += ", anticipo = '" + sAnticipo + "'";
        	
        	sConsulta += ", fechaModificacion = NOW()";
        	if(sConsulta.length() == 0)
    			JOptionPane.showMessageDialog(this, "No se ha realizado ninguna Modificacion en la información", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
    		else
    		{
    			System.out.println("Consulta actualizar " + sConsulta);
    			
    			DbActualizarTramite objActualizarTramite = new DbActualizarTramite();
    			bBanderaCorrecto = objActualizarTramite.actualizarTramite(sConsulta , sClaveTramite);
    			
    			if (bBanderaCorrecto == true) 
    				JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
    			else
    				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
    		}
    		
    		actualizarTabla();
    		interfazInicial();
        }
		
		
	}
	
	public void ActualizarEstadoCuenta()
	{
		boolean bBanderaCorrecto;
		String sConsulta = "";
		String sClaveTramite;
		
		boolean lCorrecto;
        
        obtieneValoresFormularios();
        lCorrecto = validarCampos();
        if (lCorrecto == true)
        {
        	System.out.println("Campos Faltantes " + sCamposFaltantes);
        	JOptionPane.showMessageDialog(this, sCamposFaltantes, "ERROR", JOptionPane.INFORMATION_MESSAGE );
        }
        else
        {
        	int filaAux = 0;
        	if(filaTramite == -1) filaAux = 0;
        	else filaAux = filaTramite;
        	String costoTotal = estilos.getObtieneValorText(textAnticipo);
        	String nuevoanticipo = estilos.getObtieneValorText(textCostototal);
        	double saldo = (Double.parseDouble(costoTotal)) - (Double.parseDouble(nuevoanticipo));
        	
        	sClaveTramite = String.valueOf(tableTramite.getValueAt(filaAux,0));
        	
        	if(sConsulta.length() == 0 ) sConsulta += "importeFaltante = '" + saldo + "'";
    		else sConsulta += ", importeFaltante = '" + saldo + "'";
        	
        	if(sConsulta.length() == 0)
    			JOptionPane.showMessageDialog(this, "No se ha realizado ninguna Modificacion en la información", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
    		else
    		{
    			System.out.println("Consulta actualizar " + sConsulta);
    			
    			DBActualizarEstadoCuenta objActualizarEdoCuenta = new DBActualizarEstadoCuenta();
    			bBanderaCorrecto = objActualizarEdoCuenta.actualizarEstadoCuenta(sConsulta, sClaveTramite);    			    			
    			
    			/*if (bBanderaCorrecto == true) 
    				JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
    			else
    				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );*/
    		}
    		
    		actualizarTabla();
    		interfazInicial();
        }
		
		
	}

	/**
	 * Create the application.
	 */
	public guiBusquedaTramite() {
		//initialize();
	}
public void actionPerformed(ActionEvent arg0) 
{
	if(arg0.getActionCommand() == "Agregar Archivo") 
	{
		String log = "";
		
		int filaAux = 0;
    	if(filaTramite == -1) filaAux = 0;
    	else filaAux = filaTramite;
    	
    	String nombreCaso      = comboCaso.getSelectedItem().toString();
    	String nombreUsuario   = String.valueOf(tableClientes.getValueAt(fila,1));
    	String apellidoPaterno = String.valueOf(tableClientes.getValueAt(fila,2));
    	String apellidoMaterno = String.valueOf(tableClientes.getValueAt(fila,3));
    	String curpUsuario     = String.valueOf(tableClientes.getValueAt(fila,0));
    	String idEtapa         = String.valueOf(tableTramite.getValueAt(filaAux,10));
    	String nombreEtapa     = String.valueOf(tableTramite.getValueAt(filaAux,9));
    	
    	String cRuta ="C:/Despacho2/Casos/" +nombreCaso+"/"+ nombreUsuario +" "+apellidoPaterno+" "+apellidoMaterno+"_"+curpUsuario+"/"+  idEtapa +"_"+nombreEtapa+"/";
		
		JFileChooser fc = new JFileChooser();
		fc.setBounds(100, 100, 726, 537);
	    fc.setDialogTitle("Ubicacion a Copiar a la Etapa");
	    int returnVal = fc.showOpenDialog(null);
	    if( returnVal == JFileChooser.APPROVE_OPTION)
	    {
	    	File file = fc.getSelectedFile();
	    	log += "Abrir: " + file.getName()+"\n";
	    	System.out.println("Abrir: " +file.getAbsolutePath() + file.getName());
	    	
	    	 File archivoEntrada = new File(file.getAbsolutePath()); 

	    	 File archivoSalida = new File(cRuta + file.getName()); 

	    	 System.out.println("cRuta + file.getName() " + cRuta + file.getName());
			  try {
				copiarArchivo(archivoEntrada, archivoSalida, cRuta + file.getName());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, "No se ha podido copiar el archivo " +e.getMessage(), "Error Copiar Archivo a Etapa", JOptionPane.ERROR_MESSAGE );
				e.printStackTrace();
			}
	     }
	     else
	     {
	    	 log += "Comando cancelado por el usuario.";
	      }
	    System.out.println("log " + log);
	}

	if(arg0.getActionCommand() == "Cambiar Etapa") 
	{
		cambiarEtapa();
	}
		if(arg0.getActionCommand() == "Cancelar") 
		{
			interfazInicial();
			llenaCampos(); 			
		}
		
		if (arg0.getSource() == cmbBusquedaTramite )
		{
			if(cmbBusquedaTramite.getSelectedItem() == "Todos")
			{
				txtTramite.setText("");
				txtTramite.setEnabled(false);
			}
			else
				txtTramite.setEnabled(true);
		}
		
		if (arg0.getSource() == comboCaso )
		 {
			if(comboCaso.getSelectedItem() == "Seleccionar")
			 {
				 textDescripcion.setText("");
				 textAnticipo.setValue(new Double("0.00"));
			 }
			 else
			 {
				 textDescripcion.setText(descripciones[comboCaso.getSelectedIndex()-1]);
				 textAnticipo.setValue(Double.parseDouble(precios[comboCaso.getSelectedIndex()-1]));
			 }
		 }
		 if (arg0.getSource() == comboTipoPago && comboTipoPago.isEnabled() == true )
		 {
			
			 System.out.println(comboTipoPago.getSelectedItem());
			 if(comboTipoPago.getSelectedItem().equals("Pagos"))
			 {
				 textCostototal.setEditable(true);
				 //textCostototal.setText("");
				 //txtFechaAnticipo.setEditable(true);
			 }
			 else
			 {
				 //textCostototal.setText("");
				 //txtFechaAnticipo.setText("");
				 textCostototal.setEditable(false);
				 //txtFechaAnticipo.setEditable(false);
			 }
		 }
		
		if(arg0.getActionCommand() == "Buscar")  
		{ 
			fila = -1;
			columna = -1;
			filaTramite = -1;
			columnaTramite = -1;
			
			String sCount    = "SELECT COUNT(DISTINCT t2.curp )AS cont " +
			  				   "FROM clientes AS t1, tramite AS t2, casos AS t3 " +
			  				   "WHERE t1.curp = t2.curp AND t2.idCaso = t3.idCaso AND ";
			String sConsulta = "SELECT DISTINCT(t1.curp), t1.nombre, t1.apellidoPaterno, t1.apellidoMaterno" +
							   " FROM clientes AS t1, tramite AS t2, casos AS t3"+
							   " WHERE t2.idCaso = t3.idCaso AND t1.curp = t2.curp AND ";
			
			System.out.println("txtTramite.getText() " + txtTramite.getText());
			if(cmbBusquedaTramite.getSelectedItem() == "No. Tramite") 
			{
				sCount    += "t2.NoTramite LIKE '"+txtTramite.getText()+"%'";
				sConsulta += "t2.NoTramite LIKE '"+txtTramite.getText()+"%'" + " GROUP BY curp";
			}
			if(cmbBusquedaTramite.getSelectedItem() == "Curp")
			{
				sCount    += "t2.curp LIKE '"+txtTramite.getText()+"%'";
				sConsulta += "t2.curp LIKE '"+txtTramite.getText()+"%'" + " GROUP BY curp";
			}
			if(cmbBusquedaTramite.getSelectedItem() == "Nombre")
			{          
				sCount    += "t1.nombre LIKE '"+txtTramite.getText()+"%'";
				sConsulta += "t1.nombre LIKE '"+txtTramite.getText()+"%'" + " GROUP BY curp";
			}
			if(cmbBusquedaTramite.getSelectedItem() == "Apellido Paterno")
			{
				sCount    += "t1.apellidoPaterno LIKE '"+txtTramite.getText()+"%'";
				sConsulta += "t1.apellidoPaterno LIKE '"+txtTramite.getText()+"%'" + " GROUP BY curp";
			}
			if(cmbBusquedaTramite.getSelectedItem() == "Apellido Materno")
			{
				sCount    += "t1.apellidoMaterno LIKE '"+txtTramite.getText()+"%'";
				sConsulta += "t1.apellidoMaterno LIKE '"+txtTramite.getText()+"%'" + " GROUP BY curp";
			}
			if(cmbBusquedaTramite.getSelectedItem() == "Todos")
			{
				sCount = "";
				sConsulta = "";
				
				sCount    = "SELECT COUNT(DISTINCT t2.curp )AS cont"+
						 	" FROM clientes AS t1, tramite AS t2, casos AS t3"+
						 	" WHERE t1.curp = t2.curp" +
						 	" AND t2.idCaso = t3.idCaso";
				
				sConsulta = "SELECT DISTINCT (t1.curp), t1.nombre, t1.apellidoPaterno, t1.apellidoMaterno"+
						    " FROM clientes AS t1, tramite AS t2, casos AS t3" +
						    " WHERE t2.idCaso = t3.idCaso" +
						    " AND t1.curp = t2.curp";

				


			}
			//sConsulta     += " GROUP BY curp";
			realizarConsulta(sCount,sConsulta );
			
		}
			
		if(arg0.getActionCommand() == "Guardar")
		{
			ActualizarTramite();
			ActualizarEstadoCuenta();
		}
		
		if(arg0.getActionCommand() == "Modificar")
		{
			interfazModificar();					
			validarComboTipo();	
			
			String sEstatus;
			
			int filaAux = 0;
	    	if(filaTramite == -1) filaAux = 0;
	    	else filaAux = filaTramite;
	    	
	    	sEstatus      = String.valueOf(tableTramite.getValueAt(filaAux,8));
	    	System.out.println("sEstatus " + sEstatus);
	    	if(sEstatus.equals("Terminado") || sEstatus.equals("Cancelado"))
	    	{
	    		btnCambiarEtapa.setEnabled(false);
	    		btnExaminar.setEnabled(false);
	    	}
	    	else if(sEstatus.equals(""))
	    	{
	    		btnCambiarEtapa.setEnabled(true);
	    		btnExaminar.setEnabled(true);
	    	}
		}


	}
	
	public void creaArchivo(File archivoOrigen, File destFile)throws IOException
	{
		FileChannel fuente = null; 
		FileChannel destination = null; 
		
		try 
		{			
			fuente = new FileInputStream(archivoOrigen).getChannel();
			destination = new FileOutputStream(destFile).getChannel();
			destination.transferFrom(fuente, 0, fuente.size());	
		} 
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally 
		{
			if(fuente != null) 
			{
				fuente.close(); 
			} 
			
			if(destination != null) 
			{
				destination.close();
			}
		} 
	
	}

	public  void copiarArchivo(File archivoOrigen, File destFile, String direccion) throws IOException 
	{	
		boolean lExistearchivo = destFile.exists();
		if(!lExistearchivo) 
		{
			//destFile.createNewFile();
			File directorio = new File(direccion); 
			directorio.createNewFile();
			creaArchivo(archivoOrigen,destFile);
			JOptionPane.showMessageDialog(this, "El Archivo " + destFile.getName() + " ha sido Copiado Exitosamente!!", "Copiar Archivo a Etapa", JOptionPane.INFORMATION_MESSAGE );
			//directorio.mkdir();
		}
		else
		{
			int seleccion = JOptionPane.showOptionDialog( this, // Componente padre
					  "Ya se cuenta con ese archivo. ¿Desea Sobreescribirlo?", //Mensaje
					  "Confirmar Copia", // Título
					  JOptionPane.YES_NO_CANCEL_OPTION,
					  JOptionPane.QUESTION_MESSAGE,
					  null,    // null para icono por defecto.
					  new Object[] { "Si", "No"},    // null para YES, NO y CANCEL
					  "Si");
			
			System.out.println("Seleccion " + seleccion);
			if(seleccion != -1)
			{
			if (seleccion == 0)
			{
				//JOptionPane.showMessageDialog( this,"Selecciono SI","Seleccion", JOptionPane.INFORMATION_MESSAGE );
				creaArchivo(archivoOrigen,destFile);
				JOptionPane.showMessageDialog(this, "El Archivo " + destFile.getName() + " ha sido Copiado Exitosamente!!", "Copiar Archivo a Etapa", JOptionPane.INFORMATION_MESSAGE );
								
			}			
			/*else
			JOptionPane.showMessageDialog( this,"Selecciono NO","Seleccion", JOptionPane.INFORMATION_MESSAGE );*/
			}
			
			
		}
		
		

	 }

	public void cambiarEtapa()
	{
		String nombreUltimaetapa;
		String sClaveTramite;
		String sEstatus;
		boolean bBanderaCorrecto = false;
		int idUltimaEtapa;
		int idEtapaActual;
		
		int filaAux = 0;
    	if(filaTramite == -1) filaAux = 0;
    	else filaAux = filaTramite;
    	
    	sEstatus      = String.valueOf(tableTramite.getValueAt(filaAux,8));
    	
    	if(sEstatus == "")
    	{
    		sClaveTramite = String.valueOf(tableTramite.getValueAt(filaAux,0));
	    	idEtapaActual = Integer.parseInt(String.valueOf(tableTramite.getValueAt(filaAux,10)));
	    	
			String consultaUltimaEtapa = "SELECT idEtapa, nombreEtapa FROM etapas WHERE idCaso = "+ idCasos[comboCaso.getSelectedIndex()-1]  +" ORDER BY idEtapa DESC  limit 1";
			DBConsultarEtapas objEtapas = new DBConsultarEtapas();
			
			objEtapas.consultaEtapaSiguiente(consultaUltimaEtapa);
			nombreUltimaetapa = objEtapas.getNombreEtapaSiguiente();
			idUltimaEtapa = objEtapas.getIdEtapasiguiente();
			
			System.out.println("Etapa siguiente " + "\n"+ objEtapas.getNombreEtapaSiguiente() +
					"Id Etapa siguiente " + objEtapas.getIdEtapasiguiente());
			
			if(idEtapaActual != idUltimaEtapa)
			{
				String sConsultaActualizarEtapa = "idEtapa = '" + (idEtapaActual +1) + "'";
				
				DbActualizarTramite objActualizarTramite = new DbActualizarTramite();
				bBanderaCorrecto = objActualizarTramite.actualizarTramite(sConsultaActualizarEtapa , sClaveTramite);
				
				if (bBanderaCorrecto == true) 
				{
					String consultaEtapaSiguiente = "SELECT idEtapa, nombreEtapa FROM etapas WHERE idEtapa = '" + (idEtapaActual +1) + "' AND idCaso = '" + idCasos[comboCaso.getSelectedIndex()-1] +"'";
					objEtapas.consultaEtapaSiguiente(consultaEtapaSiguiente);
					
					txtEtapa.setText(objEtapas.getNombreEtapaSiguiente());
					tableTramite.setValueAt(objEtapas.getNombreEtapaSiguiente(), filaAux, 9);
					tableTramite.setValueAt(idEtapaActual +1, filaAux, 10);
					JOptionPane.showMessageDialog(this, "La Etapa ha sido Cambiada Exitosamente!!", "Cambiar etapa", JOptionPane.INFORMATION_MESSAGE );
				}
				else
					JOptionPane.showMessageDialog(this, "No se ha podido Cambiar Etapa", "Error Cambiar etapa", JOptionPane.INFORMATION_MESSAGE );
			}
			else
			{
				String sConsultaEstadoPagado = "SELECT pagado FROM tramite where NoTramite = '"+sClaveTramite+"'";
				DBConsultarTramite objConsultarTramite = new DBConsultarTramite();
				objConsultarTramite.consultarDatoEspecifico(sConsultaEstadoPagado);
				int iEstatusPagado = objConsultarTramite.getEstatusPagado();
				
				if(iEstatusPagado != 1)
					JOptionPane.showMessageDialog(this, "No se puede finalizar el Tramite ya que no se encuentra Pagado!!", "Error Terminar Tramite", JOptionPane.ERROR_MESSAGE );
				else
				{
					String sConsultaTerminarTramite = "estatus = 'Terminado', fechaFinalizacion = NOW() ";
					
					DbActualizarTramite objActualizarTramite = new DbActualizarTramite();
					bBanderaCorrecto = objActualizarTramite.actualizarTramite(sConsultaTerminarTramite , sClaveTramite);
					
					if (bBanderaCorrecto == true) 
					{
						tableTramite.setValueAt("Terminado", filaAux, 8);
						JOptionPane.showMessageDialog(this, "El tramite ha sido terminado Exitosamente!!", "Terminar Tramite", JOptionPane.INFORMATION_MESSAGE );
					}
					else
						JOptionPane.showMessageDialog(this, "No se ha podido terminar el tramite", "Error Terminar Tramite", JOptionPane.ERROR_MESSAGE );
				}				
			}
    	}
    	else if(sEstatus == "Terminado")
    		JOptionPane.showMessageDialog(this, "No se puede realizar la operacion por que el tramite se encuentra Terminado", "Error Terminar Tramite", JOptionPane.INFORMATION_MESSAGE );
    	
		
		
	}
	
	public void realizarConsulta(String sCount, String sConsulta)
	{
		DBConsultarTramite objConsultar = new DBConsultarTramite();
		Object [][] vConsultaCliente = objConsultar.consultarTramite("Cliente",sCount,sConsulta);
		int iCuenta;
		int iContador = vConsultaCliente.length;
		
		dtmCliente = new MiModelo2(vConsultaCliente, columNamesClientes);
		tableClientes.setModel(dtmCliente);
		if(iContador == 0)
		{
			vConsultaTramite = new Object [0][0];
			dtmTramites = new MiModelo2(vConsultaTramite, columNamesTramites);
			tableTramite.setModel(dtmTramites);
			limpiaCampos();
			JOptionPane.showMessageDialog(this, "No se ha podido Encontrar Cliente", "Error consultar Cliente", JOptionPane.INFORMATION_MESSAGE );
		}
		else
		{
			for(iCuenta = 0; iCuenta < vConsultaCliente.length; iCuenta++ )
			{
				System.out.println("y¿tamaño columna " + vConsultaCliente[iCuenta].length);
				for(int iCuentaAux = 0; iCuentaAux < vConsultaCliente[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCliente[iCuenta][iCuentaAux].toString());
				}
			}
			//MouseEvent e = new MouseEvent(spTramites, iContador, ABORT, iCuenta, columna, columnaTramite, iContador, closable);
			tableClientes.setRowSelectionInterval(0,0);
			fila = tableClientes.getSelectedRow();
			columna = tableClientes.getSelectedColumn();
			tableClientes.requestFocus();
			consultaTramite(fila, columna);
			llenaCampos();
		}
		
	}
	
	public void llenaComboCasos()
	{
		DBConsultarCasos objCasos = new DBConsultarCasos();
		Object [][] vConsultaCasos = objCasos.consultarCasos("Tramite","");
		int iContador, iCuenta;
		iContador = vConsultaCasos.length;
		String[] lista = new String[iContador];
		 descripciones = new String[iContador];
		 precios = new String[iContador];
		 idCasos = new String[iContador];
		//String vDatosComboCasos[] = new String[iContador];
		jtmCasos = new DefaultComboBoxModel();
		jtmCasos.removeAllElements();
		comboCaso.setModel(jtmCasos);
				
		jtmCasos.addElement("Seleccionar");
		for(iCuenta = 0; iCuenta < vConsultaCasos.length; iCuenta++ )
		{
			for(int iCuentaAux = 0; iCuentaAux < vConsultaCasos[iCuenta].length;iCuentaAux++)
			{
				if( iCuentaAux == 0)idCasos[iCuenta] = vConsultaCasos[iCuenta][iCuentaAux].toString();
				if( iCuentaAux == 1) 
				{
					lista[iCuenta] = vConsultaCasos[iCuenta][iCuentaAux].toString();
					jtmCasos.addElement(lista[iCuenta]);
				}
				if( iCuentaAux == 2) 
				{
					precios[iCuenta] = vConsultaCasos[iCuenta][iCuentaAux].toString();
					
				}
				if(iCuentaAux == 3)
				{
					descripciones[iCuenta] = vConsultaCasos[iCuenta][iCuentaAux].toString();
				}
				 
				System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCasos[iCuenta][iCuentaAux].toString());
			}
		}
		comboCaso.addActionListener(this);
		//txtDescripcion.setText(descripciones[comboBox.getSelectedIndex()]);
		//txtCostoTotal.setText(precios[comboBox.getSelectedIndex()]);
		System.out.println("Seleccionado " + jtmCasos.getSelectedItem()+"\n At " +comboCaso.getSelectedIndex());
	}
	
	public void interfazInicial()
	{
		txtTramite.setEnabled(true);
		textAnticipo.setEditable(false);
		textCostototal.setEditable(false);
		txtEtapa.setEditable(false);
		tableTramite.setEnabled(true);
		tableClientes.setEnabled(true);
		textObservaciones.setEditable(false);
		textDescripcion.setEditable(false);
		
			
		cmbBusquedaTramite.setEnabled(true);
		comboTipoPago.setEnabled(false);
		comboCaso.setEnabled(false);
		
		btnGuardar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnBuscar.setEnabled(true);
		btnCambiarEtapa.setEnabled(false);
		btnExaminar.setEnabled(false);
		btnModificar.setEnabled(false);
	}
	public void limpiaCampos()
	{
		txtTramite.setText("");
		textCostototal.setValue(new Double("0.00"));
		textObservaciones.setText("");
		textDescripcion.setText("");
		comboTipoPago.setSelectedItem("Seleccionar");
		comboCaso.setSelectedItem("Seleccionar");
	}
	public void consultaTramite(int filaAux, int columnaAux)
	{
		System.out.println("consulta tramite fila " + filaAux);
		System.out.println("consulta tramite columna " + columnaAux);
		if ((fila == -1) && (columna == -1))
		{
			fila = 0; 
			columna = 0;
		}
		else
		{
			fila = filaAux; 
			columna = columnaAux;
		}
		    
			String curp = String.valueOf(tableClientes.getValueAt(fila,0));
			System.out.println("curp " + curp);
			String sCount;
			String sConsulta; 
			if(cmbBusquedaTramite.getSelectedItem() == "No. Tramite")
			{
				System.out.println("nombre tramite");
				sCount = "SELECT count( 1)AS cont FROM tramite WHERE curp ='" +curp+"' AND NoTramite LIKE '"+txtTramite.getText().trim()+"%'";
				/* sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones,"+
										" t3.nombreCaso, t3.idCaso, t3.descripcion, t3.precio " +
									    " FROM tramite AS t2, casos AS t3 " +
									    " WHERE t2.idCaso = t3.idCaso AND t2.curp ='"+curp+"' AND t2.NoTramite LIKE '"+txtTramite.getText()+"%'";*/
				sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones, t2.estatus,"+
        		" t3.nombreCaso, t3.descripcion, t3.precio,"+
        		" t4.nombreEtapa, t4.idEtapa " +
        		" FROM tramite AS t2, casos AS t3, etapas AS t4"+
        		" WHERE  t3.idCaso = t4.idCaso AND t2.idCaso = t3.idCaso "+
        		" AND t2.idEtapa = t4.idEtapa AND t2.NoTramite LIKE'"+txtTramite.getText().trim()+"%' AND t2.curp ='"+curp+"'";
				 
				
				
			}
			else
			{
				System.out.println("otra condicion");
				sCount = "SELECT count( 1)AS cont FROM tramite WHERE curp ='" +curp+"'";
				/* sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones,"+
										" t3.nombreCaso, t3.idCaso, t3.descripcion, t3.precio " +
									    " FROM tramite AS t2, casos AS t3 " +
									    " WHERE t2.idCaso = t3.idCaso AND t2.curp ='"+curp+"'";*/
				sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones, t2.estatus,"+
					        		" t3.nombreCaso, t3.descripcion, t3.precio,"+
					        		" t4.nombreEtapa, t4.idEtapa " +
					        		" FROM tramite AS t2, casos AS t3, etapas AS t4"+
					        		" WHERE t3.idCaso = t4.idCaso AND t2.idCaso = t3.idCaso "+
					        		" AND t2.idEtapa = t4.idEtapa AND t2.curp ='"+curp+"'";
			}

			DBConsultarTramite objConsultar = new DBConsultarTramite();
			vConsultaTramite = objConsultar.consultarTramite("Tramite",sCount,sConsulta);
			int iCuenta;
			int iContador = vConsultaTramite.length;
			
			if(iContador == 0) 	limpiaCampos();
			
			/*for(iCuenta = 0; iCuenta < vConsultaTramite.length; iCuenta++ )
			{
				System.out.println("y¿tamaño columna " + vConsultaTramite[iCuenta].length);
				for(int iCuentaAux = 0; iCuentaAux < vConsultaTramite[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaTramite[iCuenta][iCuentaAux].toString());
				}
			}*/
			
			if(vConsultaTramite.length != 0)btnModificar.setEnabled(true);
			dtmTramites = new MiModelo2(vConsultaTramite, columNamesTramites);
			tableTramite.setModel(dtmTramites);
												   
			
	}
	
	public void validarComboTipo()
	{				
		if(comboTipoPago.getSelectedItem().toString() == "Contado")
		{			
			textCostototal.setEditable(false);			
		}
		else if(comboTipoPago.getSelectedItem().toString() == "Pagos")
		{
			textCostototal.setEditable(true);			
		}		
	}
	public void interfazModificar()
	{
		txtTramite.setEnabled(false);
		tableTramite.setEnabled(true);
		tableClientes.setEnabled(false);
		textObservaciones.setEditable(true);
		textDescripcion.setEditable(false);
		textCostototal.setEditable(true);
			
		cmbBusquedaTramite.setEnabled(false);
		comboTipoPago.setEnabled(true);
		comboCaso.setEnabled(false);
		
		btnGuardar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnCambiarEtapa.setEnabled(true);
		btnExaminar.setEnabled(true);
		btnBuscar.setEnabled(false);
		btnModificar.setEnabled(false);		
	}
	
	static String getObtieneIdTramite()
	{
		
		sIdTramite = String.valueOf(tableTramite.getValueAt(filaTramite,0));
		return sIdTramite;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	public JInternalFrame initialize() {
		frmBusqueda = new JInternalFrame("Consultar Tramites", false, true, true, true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmBusqueda.setFrameIcon(new ImageIcon(URLIcon));
		frmBusqueda.setBounds(100, 100, 716, 534);
		frmBusqueda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBusqueda.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panelPie = new JPanel();
		panelPie.setSize(10,10);
		FlowLayout flowLayout = (FlowLayout) panelPie.getLayout();
		//panelPie.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		frmBusqueda.getContentPane().add(panel, BorderLayout.NORTH);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		panelPie.add(btnModificar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		panelPie.add(btnGuardar);
		frmBusqueda.getContentPane().add(panelPie, BorderLayout.SOUTH);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		panelPie.add(btnCancelar);
		frmBusqueda.getContentPane().add(panelPie, BorderLayout.SOUTH);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{76, 115, 52, 76, 49, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 80, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};		
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCurp = new JLabel("Buscar por:");
		GridBagConstraints gbc_lblCurp = new GridBagConstraints();
		gbc_lblCurp.anchor = GridBagConstraints.EAST;
		gbc_lblCurp.fill = GridBagConstraints.VERTICAL;
		gbc_lblCurp.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurp.gridx = 1;
		gbc_lblCurp.gridy = 0;
		panel.add(lblCurp, gbc_lblCurp);
		
		cmbBusquedaTramite = new JComboBox();
		cmbBusquedaTramite.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--", "Todos" ,"No. Tramite", "Curp", "Nombre", "Apellido Paterno", "Apellido Materno"}));
		GridBagConstraints gbc_cmbBusquedaTramite = new GridBagConstraints();
		gbc_cmbBusquedaTramite.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBusquedaTramite.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBusquedaTramite.gridx = 2;
		gbc_cmbBusquedaTramite.gridy = 0;
		cmbBusquedaTramite.addActionListener(this);
		panel.add(cmbBusquedaTramite, gbc_cmbBusquedaTramite);
		
		txtTramite = new JTextField();
		txtTramite.addActionListener(estilos.tranfiereElFoco);
		txtTramite.addKeyListener( 
				  new KeyAdapter() { 
					     public void keyPressed(KeyEvent e) { 
					       if (e.getKeyCode() == KeyEvent.VK_ENTER) 
					       { 
					    	   btnBuscar.doClick(); 
					       } 
					     } 
					  }); 
		GridBagConstraints gbc_txtTramite = new GridBagConstraints();
		gbc_txtTramite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTramite.insets = new Insets(0, 0, 5, 5);
		gbc_txtTramite.gridx = 3;
		gbc_txtTramite.gridy = 0;
		panel.add(txtTramite, gbc_txtTramite);
		txtTramite.setColumns(10);
	
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 4;
		gbc_btnBuscar.gridy = 0;
		panel.add(btnBuscar, gbc_btnBuscar);
		
		tableClientes = new JTable();
		tableClientes.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spClientes = new JScrollPane(tableClientes);
		vConsultaTramite = new Object [0][0];
		dtmCliente = new MiModelo2(vConsultaTramite, columNamesClientes);
		tableClientes.setModel(dtmCliente);
		tableClientes.addMouseListener(new MouseAdapter()
		{     
			public void mouseClicked(MouseEvent e)
			{ 
				filaTramite = -1;
				columnaTramite = -1;
				
				fila = tableClientes.rowAtPoint(e.getPoint()); 
				columna = tableClientes.columnAtPoint(e.getPoint());
				
				consultaTramite(fila,columna);
				llenaCampos();
			}
		});
		
		tableClientes.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTramite = -1;
					columnaTramite = -1;
					
					fila = tableClientes.getSelectedRow(); 
					columna = tableClientes.getSelectedColumn();
					
					consultaTramite(fila,columna);
					llenaCampos();
				}
			}
		});

		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.gridwidth = 6;
		gbc_table_1.fill = GridBagConstraints.BOTH;
		gbc_table_1.gridx = 0;
		gbc_table_1.gridy = 1;
		panel.add(spClientes, gbc_table_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Tramites", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmBusqueda.getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{90, 107, 90, 190, 87, 0};
		gbl_panel_1.rowHeights = new int[]{75, 39, 68, 0, 0, 69, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		tableTramite = new JTable();
		tableTramite.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spTramites = new JScrollPane(tableTramite);
		vConsultaTramite = new Object [0][0];
		dtmTramites = new MiModelo2(vConsultaTramite, columNamesTramites);
		tableTramite.setModel(dtmTramites);
		tableTramite.addMouseListener(new listenerTramites());
		tableTramite.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaTramite = tableTramite.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaTramite);
				columnaTramite = tableTramite.columnAtPoint(e.getPoint());    
				if ((filaTramite > -1) && (columnaTramite > -1))
				{
					llenaCampos();
					/*textObservaciones.setText(String.valueOf(tableTramite.getValueAt(filaTramite,4)));//precio
					textAnticipo.setText(String.valueOf(tableTramite.getValueAt(filaTramite,5)));//tipo pago
					textCostototal.setText(String.valueOf(tableTramite.getValueAt(filaTramite,7)));//anticipo
					comboCaso.setSelectedItem(String.valueOf(tableTramite.getValueAt(filaTramite,2)));
					comboTipoPago.setSelectedItem(String.valueOf(tableTramite.getValueAt(filaTramite,6)));
					textDescripcion.setText(String.valueOf(tableTramite.getValueAt(filaTramite,3)));
					*/									   
					}
				}
			});
		tableTramite.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTramite = tableTramite.getSelectedRow(); 
					columnaTramite = tableTramite.getSelectedColumn();
					llenaCampos();
				}
			}
		});

		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 8;
		gbc_table.insets = new Insets(0, 0, 5, 5);
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_1.add(spTramites, gbc_table);
		
		JLabel label = new JLabel("Caso:");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		panel_1.add(label, gbc_label);
		
		comboCaso = new JComboBox();
		//comboCaso.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--","Caso 1"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel_1.add(comboCaso, gbc_comboBox);
		comboCaso.addActionListener(this);
		
		JLabel label_1 = new JLabel("Descripci\u00F3n:");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		panel_1.add(label_1, gbc_label_1);
		label_1.setVerticalAlignment(SwingConstants.TOP);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 2;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		textDescripcion = new JTextArea();
		scrollPane.setViewportView(textDescripcion);
		
		JLabel label_2 = new JLabel("Costo Total:");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 3;
		panel_1.add(label_2, gbc_label_2);
		
		textAnticipo = estilos.formatoText(textAnticipo);
		textAnticipo.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 3;
		panel_1.add(textAnticipo, gbc_textField);
		textAnticipo.setColumns(10);
		
		JLabel label_4 = new JLabel("Anticipo:");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.EAST;
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 0;
		gbc_label_4.gridy = 4;
		panel_1.add(label_4, gbc_label_4);
		
		textCostototal =  estilos.formatoText(textCostototal);
		textCostototal.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 4;
		panel_1.add(textCostototal, gbc_textField_1);
		textCostototal.setColumns(10);
		
		JLabel label_3 = new JLabel("Tipo de Pago:");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.anchor = GridBagConstraints.EAST;
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 4;
		panel_1.add(label_3, gbc_label_3);
		
		comboTipoPago = new JComboBox();
		comboTipoPago.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent evento)
			{
				if(evento.getStateChange() == ItemEvent.SELECTED)
				{
					Object item = evento.getItem();
					System.out.println("OBJECT: " + item);
					if(item == "Contado")
					{
						textCostototal.setValue(new Double("0.00"));
					}
					else if(item == "Pagos")
					{
						obtenerPago();
					}
				}
			}
		});
		comboTipoPago.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar","Contado","Pagos"}));
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.anchor = GridBagConstraints.WEST;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.gridx = 3;
		gbc_comboBox_1.gridy = 4;
		panel_1.add(comboTipoPago, gbc_comboBox_1);
		comboTipoPago.addActionListener(this);
		
		JLabel label_6 = new JLabel("Observaciones:");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.anchor = GridBagConstraints.EAST;
		gbc_label_6.insets = new Insets(0, 0, 0, 5);
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 5;
		panel_1.add(label_6, gbc_label_6);
		
		JLabel etapa = new JLabel("Etapa:");
		GridBagConstraints gbc_etapa = new GridBagConstraints();
		gbc_etapa.anchor = GridBagConstraints.EAST;
		gbc_etapa.insets = new Insets(0, 0, 5, 5);
		gbc_etapa.gridx = 2;
		gbc_etapa.gridy = 1;
		panel_1.add(etapa, gbc_etapa);
		etapa.setVerticalAlignment(SwingConstants.TOP);
		
		txtEtapa = new JTextField();
		txtEtapa.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtEtapa = new GridBagConstraints();
		gbc_txtEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_txtEtapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEtapa.gridx = 3;
		gbc_txtEtapa.gridy = 1;
		panel_1.add(txtEtapa, gbc_txtEtapa);
		txtEtapa.setColumns(10);
		
		btnExaminar = new JButton("Agregar Archivo");
		btnExaminar.addActionListener(this);
		GridBagConstraints gbc_btnExaminar = new GridBagConstraints();
		gbc_btnExaminar.insets = new Insets(0, 0, 5, 5);
		gbc_btnExaminar.gridx = 5;
		gbc_btnExaminar.gridy = 1;
		panel_1.add(btnExaminar, gbc_btnExaminar);
		
        btnCambiarEtapa = new JButton("Cambiar Etapa");
        btnCambiarEtapa.addActionListener(this);
		GridBagConstraints gbc_btnCambiarEtapa = new GridBagConstraints();
		gbc_btnCambiarEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_btnCambiarEtapa.gridx = 4;
		gbc_btnCambiarEtapa.gridy = 1;
		panel_1.add(btnCambiarEtapa, gbc_btnCambiarEtapa);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.gridwidth = 5;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 5;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		textObservaciones = new JTextArea();
		scrollPane_1.setViewportView(textObservaciones);
					
		llenaComboCasos();
		interfazInicial();
		return frmBusqueda;
	}

}
class MiModelo2 extends DefaultTableModel
{
   public boolean isCellEditable (int row, int column)
   {
      return false;
   }
   public MiModelo2(Object[][] data, Object[] columnNames)
   {
	   super( data, columnNames);
   }
}

class opcionesTramite extends JPopupMenu implements ActionListener{
    JMenuItem cancelarItem;
    JMenuItem cambiaretapaItem;
    JMenuItem agregarArchivoItem;
    public opcionesTramite()
    {
        cancelarItem = new JMenuItem("Cancelar");
        cancelarItem.addActionListener(this);
       // add(cancelarItem);
        
        cambiaretapaItem = new JMenuItem("Cambiar Etapa");
        cambiaretapaItem.addActionListener(this);
        //add(cambiaretapaItem);
        
        agregarArchivoItem = new JMenuItem("Agregar Archivo");
        agregarArchivoItem.addActionListener(this);
        //add(agregarArchivoItem);
        
        if(guiBusquedaTramite.btnModificar.isEnabled() == false)
        {
        	add(cancelarItem);
        	add(cambiaretapaItem);
        	add(agregarArchivoItem);
        }
        
        String sEstatus = String.valueOf(guiBusquedaTramite.tableTramite.getValueAt(guiBusquedaTramite.filaTramite,8));
        
        if (sEstatus.equals("Cancelado") || sEstatus.equals("Terminado"))
        {
        	cancelarItem.setEnabled(false);
        	cambiaretapaItem.setEnabled(false);
        	agregarArchivoItem.setEnabled(false);
        }
        else
        {
        	cancelarItem.setEnabled(true);
        	cambiaretapaItem.setEnabled(true);
        	agregarArchivoItem.setEnabled(true);
        }
    }
    
    public void actionPerformed(ActionEvent evento)
	{
    	String sClaveTramite2 = guiBusquedaTramite.getObtieneIdTramite();
		
		
		System.out.println("menu evnt" + sClaveTramite2);
    	if(evento.getActionCommand() == "Cancelar")
    	{
    		boolean bBanderaCorrecto;
    		String sConsulta = "";
    		String sClaveTramite = guiBusquedaTramite.getObtieneIdTramite();
    		sConsulta = "estatus = 'Cancelado'";
    		
    		System.out.println("menu evnt" + sClaveTramite);
    		
    		DbActualizarTramite objActualizarTramite = new DbActualizarTramite();
    		bBanderaCorrecto = objActualizarTramite.actualizarTramite(sConsulta , sClaveTramite);
    		
    		if (bBanderaCorrecto == true) 
    		{
    			guiBusquedaTramite.tableTramite.setValueAt("Cancelado", guiBusquedaTramite.filaTramite,8);
				JOptionPane.showMessageDialog(this, "El tramite ha sido cancelado Exitosamente!!", "Tramite Cancelado", JOptionPane.INFORMATION_MESSAGE );
    		}
			else
				JOptionPane.showMessageDialog(this, "No se ha podido cancelar el Tramite", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
    			    		
    	}
    	else if(evento.getActionCommand() == "Cambiar Etapa")
    	{
    		System.out.println("popup cambiar etapa");
    		guiBusquedaTramite.btnCambiarEtapa.doClick();
    	}
    	else if(evento.getActionCommand() == "Agregar Archivo")
    	{
    		System.out.println("popup agregar archivo");
    		guiBusquedaTramite.btnExaminar.doClick();
    	}
	}
}

class listenerTramites extends MouseAdapter {
    public void mousePressed(MouseEvent e)
    {
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
    	opcionesTramite menu = new opcionesTramite();
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}