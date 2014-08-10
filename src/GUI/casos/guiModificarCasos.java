package GUI.casos;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.DefaultComboBoxModel;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import bd.DBActualizarCasos;
import bd.DBActualizarEtapas;
import bd.DBConsultarCasos;
import bd.DBConsultarEtapas;
import bd.DBInsertarEtapa;
import bd.DbActualizarAgenda;
import bd.DbConsultar;

import GUI.estilos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

public class guiModificarCasos extends JInternalFrame implements ActionListener 
{
	private JInternalFrame frmModificarCasos;
	private JTextField txtBusqueda;
	private JScrollPane scrollPane;
	private JPanel panel;
	private JTable tablaCasos;
	private JLabel lblNombreCaso;
	private JTextField txtNombreCaso;
	private JLabel lblDescripcionEtapa;
	private JTextField txtDescripcionEtapa; 
	private JLabel lblPreci;
	//private JTextField txtPrecio;
	private JFormattedTextField txtPrecio;
	private JLabel lblDescripcin;
	private JScrollPane scrollPane_1;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JTextArea txtDescripcion;
	private JPanel panel_1;
	private JScrollPane scrollPane_2;
	private JTable tablaEtapas;
	private JLabel lblNombreEtapa;
	private JTextField txtNombreEtapa;
	private JButton btnModificar_1;
	private JButton btnModificarEtapa;
	private JButton btnMas;
	JButton btnBuscar ;
	JComboBox cmbSeleccionar;
	
	DBInsertarEtapa objInsertar;
	
	MiModelo3 dtmCasos;
	MiModelo3 dtmEtapas;
	Object [][] vConsultaCasos;
	Object [][] vConsultaEtapas;
	
	String[] columNamesCasos = {"Id Caso","Nombre Caso","Precio", "Descripcion"};
	String[] columNamesEtapas = {"idCaso","idEtapa","Nombre","Descripcion"};
	
	private JScrollPane spCasos;
	private JScrollPane spEtapas;
	private int filaCaso = -1;
	private int columnaCaso;
		
	private int filaEtapa = -1;
	private int columnaEtapa;


	private JButton btnCancelar_1;

	public void interfazCasoguardado()
	{
		txtNombreCaso.setEditable(false);
		txtDescripcion.setEditable(false);
		txtPrecio.setEditable(false);
		btnModificar.setEnabled(false);
		btnCancelar.setEnabled(false);
	}
	
	public void interfazEtapaGuardada()
	{
		txtNombreEtapa.setEditable(true);
		txtDescripcionEtapa.setEditable(true);
		btnMas.setEnabled(true);
		btnModificarEtapa.setEnabled(true);
		btnCancelar_1.setEnabled(false);
		btnModificar_1.setEnabled(false);
	}
	
	public void interfazInicial()
	{
		txtNombreCaso.setEditable(false);
		txtDescripcion.setEditable(false);
		txtPrecio.setEditable(false);
		txtNombreEtapa.setEditable(false);
		txtDescripcionEtapa.setEditable(false);
		
		btnMas.setEnabled(false);
		btnModificar.setEnabled(false);
		btnModificarEtapa.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnCancelar_1.setEnabled(false);
		btnModificar_1.setEnabled(false);
	}
	
	public void interfazModificarEtapas()
	{
		txtNombreEtapa.setEditable(true);
		txtDescripcionEtapa.setEditable(true);
		btnMas.setEnabled(true);
		btnModificarEtapa.setEnabled(true);
		btnCancelar_1.setEnabled(true);
		btnModificar_1.setEnabled(true);
	}
	
	public void interfazModificar()
	{
		System.out.println("interfaz modificar");
		txtNombreCaso.setEditable(true);
		txtDescripcion.setEditable(true);
		txtPrecio.setEditable(true);
		txtNombreEtapa.setEditable(true);
		txtDescripcionEtapa.setEditable(true);
		
		btnMas.setEnabled(true);
		btnModificar.setEnabled(true);
		btnModificarEtapa.setEnabled(true);
		btnCancelar.setEnabled(true);
		/*btnCancelar_1.setEnabled(true);
		btnModificar_1.setEnabled(true);*/
	}
	
	public guiModificarCasos() {
		//initialize();
	}
	
	public void renombrarCarpeta(String carpetaAntigua, String carpetaNueva)
	{
		System.out.println("----------------------------C:/Despacho2/Casos/"+carpetaAntigua);
		File directorio = new File("C:/Despacho2/Casos/"+carpetaAntigua); 
		File directorioNuevo = new File("C:/Despacho2/Casos/"+carpetaNueva); 
		
		directorio.renameTo(directorioNuevo);
		//directorio.createNewFile();
	}
	
	public void renombrarCarpetaEtapa(String etapaAntigua, String etapaNueva, String idCaso, String nombreCaso, String Modalidad)
	{
		Object [][] vConsultaCliente;
		
		DbConsultar objConsultar = new DbConsultar();
		
		String sCount = "";
		String sCondicion = "";
		String nombreUsuario   = "";
    	String apellidoPaterno = "";
    	String apellidoMaterno = "";
    	String curpUsuario     = "";
    	
    	String carpetaRenombrar = "";
    	String carpetaNueva = "";
		
		sCount = "SELECT COUNT( DISTINCT c.curp ) AS cont FROM clientes c INNER JOIN tramite t ON c.curp = t.curp "+
				 "INNER JOIN casos ca ON t.idCaso = ca.idCaso WHERE t.idCaso =  '"+idCaso+ "'";
		
		sCondicion = "SELECT DISTINCT (c.curp ), c.nombre, c.apellidoPaterno, c.apellidoMaterno, c.direccion, c.fechaNacimiento, " +
					 "c.email, c.telefonoCasa, c.celular, c.observaciones FROM clientes c INNER JOIN tramite t ON c.curp = t.curp " +
					 "INNER JOIN casos ca ON t.idCaso = ca.idCaso WHERE t.idCaso =  '"+idCaso+"'";
		
		vConsultaCliente = objConsultar.consultarCliente(sCount,sCondicion );
		
		 
		int iCuenta;
		int iContador = vConsultaCliente.length;
		for(iCuenta = 0; iCuenta < vConsultaCliente.length; iCuenta++ )
		{
			nombreUsuario   = vConsultaCliente[iCuenta][0].toString();
	    	apellidoPaterno = vConsultaCliente[iCuenta][1].toString();
	    	apellidoMaterno = vConsultaCliente[iCuenta][2].toString();
	    	curpUsuario     = vConsultaCliente[iCuenta][3].toString();
	    	
	    	carpetaRenombrar = nombreCaso+"/"+ nombreUsuario +" "+apellidoPaterno+" "+apellidoMaterno+"_"+curpUsuario+"/"+ etapaAntigua;
	    	carpetaNueva = nombreCaso+"/"+ nombreUsuario +" "+apellidoPaterno+" "+apellidoMaterno+"_"+curpUsuario+"/"+ etapaNueva;
	    	
	    	if(Modalidad == "Renombrar")renombrarCarpeta(carpetaRenombrar, carpetaNueva);
	    	else if(Modalidad == "Agregar")
	    	{
	    		try
	    		{
	    			File directorio = new File("C:/Despacho2/Casos/" + carpetaNueva);
	    			directorio.mkdirs();
	    		
	    		}
	    		catch(Exception e)
	    		{
	    			JOptionPane.showMessageDialog(this, "No se pudo crear la carpeta de la nueva Carpeta!!", "Error Agregar La carpeta de la nueva Etapa", JOptionPane.ERROR_MESSAGE );
	    		}
				
	    	}
		}
	}
	
	public void actualizarEtapa()
	{
		if(txtNombreEtapa.getText().length() == 0)
		{
			JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos una Etapa!!", "Error Etapas", JOptionPane.ERROR_MESSAGE );
		}
		else
		{
			int filaAux ;
		
			if(filaEtapa == -1)
			{
				filaAux = 0;
			}
			else filaAux = filaEtapa;
			
			int filaCasoAux = 0;
        	if(filaCaso == -1) filaCasoAux = 0;
        	else filaCasoAux = filaCaso;
        				
			//vConsultaEtapas[filaAux][3] = txtDescripcionEtapa.getText().trim();
        	
        	
			/*String nombreEatapaAntigua = vConsultaEtapas[filaAux][2].toString();
        	String nombreEtapaActualizar = txtNombreEtapa.getText().trim();
			String descripcionEtapaActualizar = txtDescripcionEtapa.getText().trim();
			String iEtapaActualizar = vConsultaEtapas[filaAux][1].toString();
			String idCasoEtapaActualizar = vConsultaCasos[filaCasoAux][0].toString();
			String nombreCasoEtapaActualizar = vConsultaCasos[filaCasoAux][1].toString();*/
        	
        	String nombreEatapaAntigua = String.valueOf(tablaEtapas.getValueAt(filaAux,2));
        	String nombreEtapaActualizar = txtNombreEtapa.getText().trim();
			String descripcionEtapaActualizar = txtDescripcionEtapa.getText().trim();
			String iEtapaActualizar = String.valueOf(tablaEtapas.getValueAt(filaAux,1));
			String idCasoEtapaActualizar = vConsultaCasos[filaCasoAux][0].toString();
			String nombreCasoEtapaActualizar = vConsultaCasos[filaCasoAux][1].toString();
			
			String nombreCarpetaAntigua = iEtapaActualizar+"_"+nombreEatapaAntigua;
			String nombreCarpetaNueva   = iEtapaActualizar+"_"+nombreEtapaActualizar;
			
			System.out.println("Valor Actualizar Etapa nombreEtapaActualizar: " + nombreEtapaActualizar);
			System.out.println("Valor Actualizar Etapa descripcionEtapaActualizar " + descripcionEtapaActualizar);
			System.out.println("Valor Actualizar Etapa iEtapaActualizar " + iEtapaActualizar);
			System.out.println("Valor Actualizar Etapa idCasoEtapaActualizar " + idCasoEtapaActualizar);
			boolean bBanderaCorrecto;
			System.out.println("Tamaño etapas " + vConsultaEtapas.length );
        	DBActualizarEtapas objActualizarEtapas = new DBActualizarEtapas();
			bBanderaCorrecto = objActualizarEtapas.actualizarEtapa(idCasoEtapaActualizar,iEtapaActualizar,nombreEtapaActualizar,descripcionEtapaActualizar);
			
			if (bBanderaCorrecto == true) 
			{
//				vConsultaEtapas[filaAux][2] = txtNombreEtapa.getText().trim();
				renombrarCarpetaEtapa(nombreCarpetaAntigua, nombreCarpetaNueva, idCasoEtapaActualizar, nombreCasoEtapaActualizar, "Renombrar");
				JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
				actualizarTablaEtapas();
				//interfazInicial();							
			}
			else
				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos " + objActualizarEtapas.exception.getMessage(), "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
			
			
		}
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == btnCancelar) interfazCasoguardado();
		
		if(arg0.getSource() == btnCancelar_1) interfazEtapaGuardada();
		
		
		if(arg0.getSource() == cmbSeleccionar)
		{
			if(cmbSeleccionar.getSelectedItem() == "Todos")
			{
				txtBusqueda.setEnabled(false);
				txtBusqueda.setText("");
			}
			else
				txtBusqueda.setEnabled(true);
		}
	
		if(arg0.getSource() == btnModificar_1) 
		{
			if(txtNombreEtapa.getText().trim().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe Ingresar El nombre de Etapa", "Error Agregar Etapa", JOptionPane.INFORMATION_MESSAGE );
			else if(txtDescripcionEtapa.getText().trim().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe proporcionar la descripcion de la Etapa", "Error Agregar Etapa", JOptionPane.INFORMATION_MESSAGE );
			else
			{
				int filaAux = 0;
				if(filaCaso == -1) filaAux = 0;
	        	else filaAux = filaCaso;
	        	boolean bCorrecto;
	        	int iClaveCaso = Integer.parseInt(String.valueOf(tablaCasos.getValueAt(filaAux,0)));
	        	
				objInsertar = new DBInsertarEtapa ();
				bCorrecto = objInsertar.agregarEtapa(iClaveCaso, txtNombreEtapa.getText().trim(), txtDescripcionEtapa.getText().trim());
				if(bCorrecto)
				{
					String nombreCarpetaNueva     = objInsertar.getidEtapaAgregar() +"_"+txtNombreEtapa.getText().trim();
					String nombreCasoEtapaAgregar = String.valueOf(tablaCasos.getValueAt(filaAux,1));
					System.out.println("Tamaño vector antes de " + vConsultaEtapas.length );
					dtmEtapas.addRow(new Object[]{iClaveCaso,objInsertar.getidEtapaAgregar(),txtNombreEtapa.getText().trim(),txtDescripcionEtapa.getText().trim()});
					System.out.println("Tamaño vector despues de " + vConsultaEtapas.length );
					JOptionPane.showMessageDialog(this,"La etapa ha sido Agregada Correctamente" , "Exito", JOptionPane.INFORMATION_MESSAGE );
					renombrarCarpetaEtapa("",nombreCarpetaNueva ,Integer.toString(iClaveCaso) , nombreCasoEtapaAgregar, "Agregar");
					interfazEtapaGuardada();
				}
				else JOptionPane.showMessageDialog(this,"No se pudo realizar la operacion " + objInsertar.excepcion.getMessage() ,"Error" , JOptionPane.INFORMATION_MESSAGE );
			}
			
			
			/*boolean bBanderaCorrecto;
			int filaAux = 0;
        	if(filaEtapa == -1) filaAux = 0;
        	else filaAux = filaEtapa;
        	System.out.println("Tamaño etapas " + vConsultaEtapas.length );
        	DBActualizarEtapas objActualizarEtapas = new DBActualizarEtapas();
			bBanderaCorrecto = objActualizarEtapas.actualizarEtapas(vConsultaEtapas);
			
			if (bBanderaCorrecto == true) 
			{
				JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
				actualizarTablaEtapas();
				//interfazInicial();							
			}
			else
				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos " + objActualizarEtapas.exception.getMessage(), "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
	    	*/
		}
	
		
		if(arg0.getSource() == btnModificarEtapa)  
		{ 
			/*if(txtNombreEtapa.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos una Etapa!!", "Error Etapas", JOptionPane.ERROR_MESSAGE );
			}
			else
			{
				int filaAux ;
			
				if(filaEtapa == -1)
				{
					filaAux = 0;
				}
				else filaAux = filaEtapa;
				
				vConsultaEtapas[filaAux][2] = txtNombreEtapa.getText().trim();
				vConsultaEtapas[filaAux][3] = txtDescripcionEtapa.getText().trim();
				
				actualizarTablaEtapas();
			}*/
			actualizarEtapa();
				
		}
		
		if(arg0.getSource() == btnMas) 
		{
			btnModificarEtapa.setEnabled(false);
			limpiaCamposEtapas();
			btnCancelar_1.setEnabled(true);
			btnModificar_1.setEnabled(true);
		}
		if(arg0.getSource() == btnModificar) 
		{
			String sConsulta = "";
			String sClaveContacto;
			boolean bBanderaCorrecto;
			int filaAux = 0;
        	if(filaCaso == -1) filaAux = 0;
        	else filaAux = filaCaso;
        	
        	sClaveContacto = String.valueOf(tablaCasos.getValueAt(filaAux,0));
        	
        	if(sConsulta.length() == 0 ) sConsulta += "nombreCaso = '" + txtNombreCaso.getText().trim() + "'";
    		else sConsulta += ", nombreCaso = '" + txtNombreCaso.getText().trim() + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "precio = '" + estilos.getObtieneValorText(txtPrecio) + "'";
    		else sConsulta += ", precio = '" + estilos.getObtieneValorText(txtPrecio) + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "descripcion = '" + txtDescripcion.getText() + "'";
    		else sConsulta += ", descripcion = '" + txtDescripcion.getText() + "'";
        	
        	       	
        	System.out.println("Consulta actualizar " + sConsulta);
        	//sustituyeTipoTelefono();
			DBActualizarCasos objActualizarCaso = new DBActualizarCasos();
			bBanderaCorrecto = objActualizarCaso.actualizarCaso(sConsulta , sClaveContacto);
			
			if (bBanderaCorrecto == true) 
			{
				renombrarCarpeta(String.valueOf(tablaCasos.getValueAt(filaAux,1)),txtNombreCaso.getText().trim());
				JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
				actualizarTablaCaso();
				//interfazInicial();
				//btnModificar.setEnabled(true);
				
			}
			else
				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos " + objActualizarCaso.exception, "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
        	
		}
			
		if(arg0.getActionCommand() == "Buscar") 
		{
			
				if(cmbSeleccionar.getSelectedItem() == "--Seleccionar--")
					JOptionPane.showMessageDialog(this, "Debe seleccionar el tipo de busqueda", "Error consultar Casos", JOptionPane.INFORMATION_MESSAGE );
				else if(cmbSeleccionar.getSelectedItem() != "Todos" && txtBusqueda.getText().trim().length() == 0)
					JOptionPane.showMessageDialog(this, "Debe proporcionar el parametro de busqueda", "Error consultar Casos", JOptionPane.INFORMATION_MESSAGE );
				else
				{
					filaCaso = -1;
					columnaCaso = -1;
					String sWhere = "";
					String sModalidad = "";
					DBConsultarCasos objConsultar = new DBConsultarCasos();
					
					if(cmbSeleccionar.getSelectedItem() == "Clave")
					{
						sWhere = " idCaso = '"+ txtBusqueda.getText().trim() + "'"; 
						sModalidad = "Casos";
						
					}
					if(cmbSeleccionar.getSelectedItem() == "Nombre Caso")
					{
						sWhere = " nombreCaso LIKE '"+ txtBusqueda.getText().trim() + "%'";
						sModalidad = "Casos";
					}
					else if(cmbSeleccionar.getSelectedItem() == "Todos")
					{
						sModalidad = "Tramite";
					}
					vConsultaCasos = objConsultar.consultarCasos(sModalidad, sWhere);
					int iCuenta;
					int iContador = vConsultaCasos.length;
					
					dtmCasos = new MiModelo3(vConsultaCasos, columNamesCasos);
					tablaCasos.setModel(dtmCasos);
					if(iContador == 0)
					{
						vConsultaCasos = new Object [0][0];
						dtmCasos = new MiModelo3(vConsultaCasos, columNamesCasos);
						tablaCasos.setModel(dtmCasos);
						limpiaCamposCasos();
						limpiaCamposEtapas();
						vConsultaEtapas = new Object [0][0];
						dtmEtapas = new MiModelo3(vConsultaEtapas, columNamesEtapas);
						tablaEtapas.setModel(dtmEtapas);
						interfazInicial();
						JOptionPane.showMessageDialog(this, "No se ha podido Encontrar Caso", "Error consultar Casos", JOptionPane.INFORMATION_MESSAGE );
					}
					else
					{
						for(iCuenta = 0; iCuenta < vConsultaCasos.length; iCuenta++ )
						{
							System.out.println("y¿tamaño columna " + vConsultaCasos[iCuenta].length);
							for(int iCuentaAux = 0; iCuentaAux < vConsultaCasos[iCuenta].length;iCuentaAux++)
							{
								System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCasos[iCuenta][iCuentaAux].toString());
							}
						}
						llenaDatosCaso();
						//MouseEvent e = new MouseEvent(spCasos, iContador, ABORT, iCuenta, columnaCaso, columnaCaso, iContador, closable);
						tablaCasos.setRowSelectionInterval(0,0);
						filaCaso = tablaCasos.getSelectedRow();
						columnaCaso = tablaCasos.getSelectedColumn();
						tablaCasos.requestFocus();
						
						consultaEtapas(filaCaso, columnaCaso);
						interfazModificar();
						
						if(vConsultaEtapas.length == 0)
						{
							btnModificarEtapa.setEnabled(false);
							limpiaCamposEtapas();
						}
						else btnModificarEtapa.setEnabled(true);
							
						llenaDatosEtapas();
						

					}
					
				
					
				}
				txtNombreCaso.requestFocus();
			}
	}
	
	public void limpiaCamposCasos()
	{
		txtNombreCaso.setText(""); 
		
		txtPrecio.setText("");
		txtPrecio.setValue(new Double("0.00"));
		txtDescripcion.setText("");
	}
	
	public void limpiaCamposEtapas()
	{
		txtNombreEtapa.setText("");
		txtDescripcionEtapa.setText("");
	}
	
	public void actualizarTablaEtapas()
	{
		int filaAux;
		if (filaEtapa == -1)
		{
			filaAux = 0;
		}
		else filaAux = filaEtapa;
		
		tablaEtapas.setValueAt(txtNombreEtapa.getText().trim(), filaAux, 2);
		tablaEtapas.setValueAt(txtDescripcionEtapa.getText().trim(), filaAux, 3);
		
	}
	
	public void actualizarTablaCaso()
	{   
		int filaAux;
		if (filaCaso == -1)
		{
			filaAux = 0;
		}
		else filaAux = filaCaso;
		
		tablaCasos.setValueAt(txtNombreCaso.getText(), filaAux, 1);
		tablaCasos.setValueAt(txtDescripcion.getText(), filaAux, 3);
		tablaCasos.setValueAt(estilos.getObtieneValorText(txtPrecio), filaAux, 2);
				
	}
	
	public void consultaEtapas(int fila, int columna)
	{
		if ((filaCaso == -1) && (columnaCaso == -1))
		{
			filaCaso = 0; 
			columnaCaso = 0;
		}
		else
		{
			filaCaso = fila; 
			columnaCaso = columna;
		}
		    
		
			String cIDCaso = String.valueOf(tablaCasos.getValueAt(filaCaso,0));
			System.out.println("curp " + cIDCaso);
			
			String sCount;
			String sConsulta;
			
			sCount = "SELECT count(1) as cont FROM etapas" + " WHERE idCaso  ='"+cIDCaso+"'";
			sConsulta = "SELECT * FROM etapas WHERE idCaso  ='"+cIDCaso+"'";				
			
			DBConsultarEtapas objConsultar = new DBConsultarEtapas();
			vConsultaEtapas = objConsultar.consultarEtapas(sConsulta, sCount);
			int iCuenta;
			int iContador = vConsultaEtapas.length;
			
			//if(iContador == 0) 	limpiaCampos();
			
			for(iCuenta = 0; iCuenta < vConsultaEtapas.length; iCuenta++ )
			{
				System.out.println("y¿tamaño columna " + vConsultaEtapas[iCuenta].length);
				for(int iCuentaAux = 0; iCuentaAux < vConsultaEtapas[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaEtapas[iCuenta][iCuentaAux].toString());
				}
			}
			dtmEtapas = new MiModelo3(vConsultaEtapas, columNamesEtapas);
			tablaEtapas.setModel(dtmEtapas);		
								   
			
	}
	
	public void llenaDatosCaso()
	{
		int filaAux;
		if(filaCaso == -1) filaAux = 0;
		else filaAux = filaCaso;
		txtNombreCaso.setText(String.valueOf(tablaCasos.getValueAt(filaAux,1))); 
		//txtPrecio.setText(String.valueOf(tablaCasos.getValueAt(filaAux,2))); 
		txtPrecio.setValue(Double.parseDouble(String.valueOf(tablaCasos.getValueAt(filaAux,2))));
		txtDescripcion.setText(String.valueOf(tablaCasos.getValueAt(filaAux,3))); 
		
	}
	
	public void llenaDatosEtapas()
	{
		if(filaEtapa == -1)filaEtapa = 0;
		
		txtNombreEtapa.setText(String.valueOf(tablaEtapas.getValueAt(filaEtapa,2)));
		txtDescripcionEtapa.setText(String.valueOf(tablaEtapas.getValueAt(filaEtapa,3)));		
		
	}


	/**
	 * Initialize the contents of the frame.
	 */
	public JInternalFrame initialize() 
	{
		frmModificarCasos = new JInternalFrame("Modificar Catálogo Casos",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmModificarCasos.setFrameIcon(new ImageIcon(URLIcon));
		frmModificarCasos.setBounds(100, 100, 600, 550);
		frmModificarCasos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 153, 188, 0, 69, 51, 0};
		gridBagLayout.rowHeights = new int[]{40, 103, 168, 123, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmModificarCasos.getContentPane().setLayout(gridBagLayout);
		 
		cmbSeleccionar = new JComboBox();
		cmbSeleccionar.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--","Todos", "Clave", "Nombre Caso"}));
		GridBagConstraints gbc_cmbSeleccionar = new GridBagConstraints();
		gbc_cmbSeleccionar.anchor = GridBagConstraints.EAST;		
		gbc_cmbSeleccionar.insets = new Insets(0, 0, 5, 5);
		gbc_cmbSeleccionar.gridx = 2;
		gbc_cmbSeleccionar.gridy = 0;
		cmbSeleccionar.addActionListener(this);
		frmModificarCasos.getContentPane().add(cmbSeleccionar, gbc_cmbSeleccionar);
		
		txtBusqueda = new JTextField();
		txtBusqueda.addKeyListener( 
				  new KeyAdapter() { 
					     public void keyPressed(KeyEvent e) { 
					       if (e.getKeyCode() == KeyEvent.VK_ENTER) 
					       { 
					    	   btnBuscar.doClick(); 
					       } 
					     } 
					  }); 
		GridBagConstraints gbc_txtBusqueda = new GridBagConstraints();
		gbc_txtBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_txtBusqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBusqueda.gridx = 3;
		gbc_txtBusqueda.gridy = 0;
		frmModificarCasos.getContentPane().add(txtBusqueda, gbc_txtBusqueda);
		txtBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(estilos.tranfiereElFoco);
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 4;
		gbc_btnBuscar.gridy = 0;
		frmModificarCasos.getContentPane().add(btnBuscar, gbc_btnBuscar);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridwidth = 7;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frmModificarCasos.getContentPane().add(scrollPane, gbc_scrollPane);
		
		tablaCasos = new JTable();
		tablaCasos.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spCasos = new JScrollPane(tablaCasos);
		vConsultaCasos = new Object [0][0];
		dtmCasos = new MiModelo3(vConsultaCasos, columNamesCasos);
		tablaCasos.setModel(dtmCasos);
		tablaCasos.addMouseListener(new MouseAdapter()
		{     
			public void mouseClicked(MouseEvent e)
			{ 
				filaCaso = tablaCasos.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaCaso);
				columnaCaso = tablaCasos.columnAtPoint(e.getPoint());
				filaEtapa = -1;
				columnaEtapa = -1;
				llenaDatosCaso();
				consultaEtapas(filaCaso, columnaCaso);
				llenaDatosEtapas();
								
				//llenaCampos();
			}
		});
		
		tablaCasos.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaCaso = tablaCasos.getSelectedRow();
					columnaCaso = tablaCasos.getSelectedColumn();
					filaEtapa = -1;
					columnaEtapa = -1;
					llenaDatosCaso();
					consultaEtapas(filaCaso, columnaCaso);
					llenaDatosEtapas();		
				}
			}
		});
		
		scrollPane.setViewportView(spCasos);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Modificar Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 7;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frmModificarCasos.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 99, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{30, 30, 51, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblNombreCaso = new JLabel("Nombre Caso:");
		GridBagConstraints gbc_lblNombreCaso = new GridBagConstraints();
		gbc_lblNombreCaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreCaso.anchor = GridBagConstraints.EAST;
		gbc_lblNombreCaso.gridx = 1;
		gbc_lblNombreCaso.gridy = 0;
		panel.add(lblNombreCaso, gbc_lblNombreCaso);
		
		txtNombreCaso = new JTextField();
		txtNombreCaso.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombreCaso = new GridBagConstraints();
		gbc_txtNombreCaso.gridwidth = 2;
		gbc_txtNombreCaso.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreCaso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreCaso.gridx = 2;
		gbc_txtNombreCaso.gridy = 0;
		panel.add(txtNombreCaso, gbc_txtNombreCaso);
		txtNombreCaso.setColumns(10);
		
		lblPreci = new JLabel("Precio:");
		GridBagConstraints gbc_lblPreci = new GridBagConstraints();
		gbc_lblPreci.anchor = GridBagConstraints.EAST;
		gbc_lblPreci.fill = GridBagConstraints.VERTICAL;
		gbc_lblPreci.insets = new Insets(0, 0, 5, 5);
		gbc_lblPreci.gridx = 1;
		gbc_lblPreci.gridy = 1;
		panel.add(lblPreci, gbc_lblPreci);
		
		txtPrecio = estilos.formatoText(txtPrecio);
		txtPrecio.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtPrecio = new GridBagConstraints();
		gbc_txtPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrecio.anchor = GridBagConstraints.WEST;
		gbc_txtPrecio.gridx = 2;
		gbc_txtPrecio.gridy = 1;
		panel.add(txtPrecio, gbc_txtPrecio);
		txtPrecio.setColumns(10);
		
		lblDescripcin = new JLabel("Descripci\u00F3n:");
		GridBagConstraints gbc_lblDescripcin = new GridBagConstraints();
		gbc_lblDescripcin.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcin.gridx = 1;
		gbc_lblDescripcin.gridy = 2;
		panel.add(lblDescripcin, gbc_lblDescripcin);
		
		scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 2;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		txtDescripcion = new JTextArea();
		scrollPane_1.setViewportView(txtDescripcion);
		
		btnModificar = new JButton("Guardar");
		btnModificar.addActionListener(this);
		GridBagConstraints gbc_btnModificar = new GridBagConstraints();
		gbc_btnModificar.anchor = GridBagConstraints.EAST;
		gbc_btnModificar.insets = new Insets(0, 0, 0, 5);
		gbc_btnModificar.gridx = 2;
		gbc_btnModificar.gridy = 3;
		panel.add(btnModificar, gbc_btnModificar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 3;
		panel.add(btnCancelar, gbc_btnCancelar);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Etapas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 7;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		frmModificarCasos.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 104, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{60, 0, 32, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		scrollPane_2 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
		gbc_scrollPane_2.gridwidth = 4;
		gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_2.gridx = 0;
		gbc_scrollPane_2.gridy = 0;
		panel_1.add(scrollPane_2, gbc_scrollPane_2);
		
		tablaEtapas = new JTable();
		tablaEtapas.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spEtapas = new JScrollPane(tablaEtapas);
		vConsultaEtapas = new Object [0][0];
		dtmEtapas = new MiModelo3(vConsultaEtapas, columNamesEtapas);
		tablaEtapas.setModel(dtmEtapas);
		tablaEtapas.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaEtapa = tablaEtapas.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaEtapa);
				columnaEtapa = tablaEtapas.columnAtPoint(e.getPoint());    
				if ((filaEtapa > -1) && (columnaEtapa > -1))
				{
					//interfazModificarEtapas();
					llenaDatosEtapas();
														   
				}
			}
		});
		
		tablaEtapas.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaEtapa = tablaEtapas.getSelectedRow();
					columnaEtapa = tablaEtapas.getSelectedColumn();
					llenaDatosEtapas();			
				}
			}
		});
		scrollPane_2.setViewportView(spEtapas);
		
		lblNombreEtapa = new JLabel("Nombre Etapa:");
		GridBagConstraints gbc_lblNombreEtapa = new GridBagConstraints();
		gbc_lblNombreEtapa.anchor = GridBagConstraints.EAST;
		gbc_lblNombreEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombreEtapa.gridx = 1;
		gbc_lblNombreEtapa.gridy = 1;
		panel_1.add(lblNombreEtapa, gbc_lblNombreEtapa);
		
		txtNombreEtapa = new JTextField();
		txtNombreEtapa.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombreEtapa = new GridBagConstraints();
		gbc_txtNombreEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreEtapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreEtapa.gridx = 2;
		gbc_txtNombreEtapa.gridy = 1;
		panel_1.add(txtNombreEtapa, gbc_txtNombreEtapa);
		txtNombreEtapa.setColumns(10);
		
		lblDescripcionEtapa = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcionEtapa = new GridBagConstraints();
		gbc_lblDescripcionEtapa.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcionEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionEtapa.gridx = 1;
		gbc_lblDescripcionEtapa.gridy = 2;
		panel_1.add(lblDescripcionEtapa, gbc_lblDescripcionEtapa);
		
		txtDescripcionEtapa = new JTextField();
		txtDescripcionEtapa.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtDescripcionEtapa = new GridBagConstraints();
		gbc_txtDescripcionEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescripcionEtapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcionEtapa.gridx = 2;
		gbc_txtDescripcionEtapa.gridy = 2;
		panel_1.add(txtDescripcionEtapa, gbc_txtDescripcionEtapa);
		txtDescripcionEtapa.setColumns(10);
		
		btnMas = new JButton("+");
		btnMas.addActionListener(this);
		GridBagConstraints gbc_btnMas = new GridBagConstraints();
		gbc_btnMas.anchor = GridBagConstraints.EAST;
		gbc_btnMas.insets = new Insets(0, 0, 0, 5);
		gbc_btnMas.gridx = 3;
		gbc_btnMas.gridy = 1;
		panel_1.add(btnMas, gbc_btnMas);
		
		btnModificarEtapa = new JButton("Modificar");
		btnModificarEtapa.addActionListener(this);
		GridBagConstraints gbc_btnModificarEtapa = new GridBagConstraints();
		gbc_btnModificarEtapa.anchor = GridBagConstraints.EAST;
		gbc_btnModificarEtapa.insets = new Insets(0, 0, 0, 5);
		gbc_btnModificarEtapa.gridx = 3;
		gbc_btnModificarEtapa.gridy = 2;
		panel_1.add(btnModificarEtapa, gbc_btnModificarEtapa);
		
		btnModificar_1 = new JButton("Agregar");
		btnModificar_1.addActionListener(this);
		GridBagConstraints gbc_btnModificar_1 = new GridBagConstraints();
		gbc_btnModificar_1.anchor = GridBagConstraints.EAST;
		gbc_btnModificar_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnModificar_1.gridx = 2;
		gbc_btnModificar_1.gridy = 3;
		panel_1.add(btnModificar_1, gbc_btnModificar_1);
		
		btnCancelar_1 = new JButton("Cancelar");
		btnCancelar_1.addActionListener(this);
		GridBagConstraints gbc_btnCancelar_1 = new GridBagConstraints();
		gbc_btnCancelar_1.gridx = 3;
		gbc_btnCancelar_1.gridy = 3;
		panel_1.add(btnCancelar_1, gbc_btnCancelar_1);
		
		interfazInicial();
		return frmModificarCasos;
	}

}
