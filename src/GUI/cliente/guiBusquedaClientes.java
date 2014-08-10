package GUI.cliente;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

import Agenda.operacionesJTable;
import GUI.estilos;
import bd.DBConsultarCasos;
import bd.DbActualizar;
import bd.DbConsultar;
import bd.DbConsultarTelefonos;
import bd.DbConsultarTipoTelefono;

import com.toedter.calendar.JDateChooser;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultComboBoxModel;

public class guiBusquedaClientes extends JInternalFrame implements ActionListener 
{
	private JInternalFrame frmConsultarClientes;
	
	private JTextField textField;
	private JTextField txtCurp;
	private JTextField txtNombre;
	private JTextField txtApellidoPaterno;
	private JTextField txtApellidoMaterno;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtCelular;
	private JTextField txtEmail;
	private JComboBox cmbTipo;
	private JButton btnAgregarTel;
	private JTable tablaTelefonos;
	private JScrollPane spTelefonos;
	
	private JDateChooser cal;
	private JTextArea txtObservaciones;
	private JScrollPane spClientes;
	private JTable table;
	
	//private DefaultTableModel dtmCliente;
	MiModelo dtmCliente;
	MiModelo3 dtmTelefono;
	private JComboBox comboBox;	
	private JButton btnConsultar; 
	private JButton btnCancelar;
	private JButton btnGuardar;
	private JButton btnModificar;
	private JButton btnNuevo;
	private JButton btnModificarTelefono;
	
	Object [][] vConsultaCliente;
	
	private int fila = -1;
	private int columna;
	
	private int filaTelefono = -1;
	private int columnaTelefono = -1;
	
	String[] columNames = {"Nombre","Apellido Paterno", "Apellido Materno","Curp","Direccion",
			"Fecha Nacimiento","Email","Observaciones"};
	
	private String  sNombre, sApellidoPaterno , sApellidoMaterno, sCurp,
	   sDireccion, sAnioNacimiento, sEmail, sCelular, sTelefono, sCamposFaltantes, sObservaciones;
	private Date fechaNacimiento;
	
	private DefaultComboBoxModel jtmTipoTelefono;
	String[] descripcionTipotelefono;
	String[] tipotelefono;
	/*public static void main(String[] args) {
		estilos obj = new estilos();
		obj.aplicar();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiBusquedaClientes window = new guiBusquedaClientes();
					window.frmConsultarClientes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	public guiBusquedaClientes() {
		//initialize();
	}
	
	public void renombrarCarpetaEtapa()
	{
		Object [][] vConsultaCasosCliente;
		
		DBConsultarCasos objConsultar = new DBConsultarCasos();
		
		String sCount = "";
		String sCondicion = "";
		if(fila == -1) fila = 0;
		String nombreUsuarioNuevo   = String.valueOf(table.getValueAt(fila,0)).toUpperCase();
    	String apellidoPaternoNuevo = String.valueOf(table.getValueAt(fila,1)).toUpperCase();
    	String apellidoMaternoNuevo = String.valueOf(table.getValueAt(fila,2)).toUpperCase();
    	String sCurp = String.valueOf(table.getValueAt(fila,3));
    	
    	String nombreUsuarioCambiar   = vConsultaCliente[fila][0].toString().toUpperCase();
    	String apellidoPaternoCambiar = vConsultaCliente[fila][1].toString().toUpperCase();
    	String apellidoMaternoCambiar = vConsultaCliente[fila][2].toString().toUpperCase();
    	    	    	
    	String carpetaRenombrar = "";
    	String carpetaNueva = "";
		
		sCount = "SELECT COUNT( DISTINCT t.idCaso ) AS cont " +
				 "FROM tramite t INNER JOIN casos c ON t.idCaso = c.idCaso " +
				 "WHERE t.curp =  '"+sCurp+ "'";
		
		sCondicion = "select DISTINCT t.idCaso, c.nombreCaso " +
					 "from tramite t inner join casos c on t.idCaso = c.idCaso " +
					 "where t.curp = '" +sCurp +"'";
		
		vConsultaCasosCliente = objConsultar.consultarNombreCaso(sCount, sCondicion);
		
		 
		int iCuenta;
		int iContador = vConsultaCasosCliente.length;
		for(iCuenta = 0; iCuenta < vConsultaCasosCliente.length; iCuenta++ )
		{
			String nombreCaso = vConsultaCasosCliente[iCuenta][1].toString();
				    	
	    	carpetaRenombrar = nombreCaso+"/"+ nombreUsuarioCambiar +" "+apellidoPaternoCambiar+" "+apellidoMaternoCambiar+"_"+sCurp;
	    	carpetaNueva = nombreCaso+"/"+ nombreUsuarioNuevo +" "+apellidoPaternoNuevo+" "+apellidoMaternoNuevo+"_"+sCurp;
	    	
	    	System.out.println("----------------------------C:/Despacho2/Casos/"+carpetaRenombrar + " " + carpetaNueva);
			File directorio = new File("C:/Despacho2/Casos/"+carpetaRenombrar); 
			File directorioNuevo = new File("C:/Despacho2/Casos/"+carpetaNueva); 
			
			directorio.renameTo(directorioNuevo);
	    	
			vConsultaCliente[fila][0] = nombreUsuarioNuevo;
	    	vConsultaCliente[fila][1] = apellidoPaternoNuevo;
	    	vConsultaCliente[fila][2] = apellidoMaternoNuevo;
		}
	}
	
	public void llenatipoTelefono()
	{
		DbConsultarTipoTelefono objTelefono = new DbConsultarTipoTelefono();
		Object [][] vConsultaTipoTelefono = objTelefono.consultarTipoTelefono();
		int iContador, iCuenta;
		iContador = vConsultaTipoTelefono.length;
		descripcionTipotelefono = new String[iContador];
		tipotelefono = new String[iContador];
		//String vDatosComboCasos[] = new String[iContador];
		jtmTipoTelefono = new DefaultComboBoxModel();
		jtmTipoTelefono.removeAllElements();
		cmbTipo.setModel(jtmTipoTelefono);
				
		jtmTipoTelefono.addElement("Seleccionar");
		for(iCuenta = 0; iCuenta < vConsultaTipoTelefono.length; iCuenta++ )
		{
			for(int iCuentaAux = 0; iCuentaAux < vConsultaTipoTelefono[iCuenta].length;iCuentaAux++)
			{
				if( iCuentaAux == 0)tipotelefono[iCuenta] = vConsultaTipoTelefono[iCuenta][iCuentaAux].toString();
				if( iCuentaAux == 1) 
				{
					descripcionTipotelefono[iCuenta] = vConsultaTipoTelefono[iCuenta][iCuentaAux].toString();
					jtmTipoTelefono.addElement(descripcionTipotelefono[iCuenta]);
				}
								 
				System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaTipoTelefono[iCuenta][iCuentaAux].toString());
			}
		}
		cmbTipo.addActionListener(this);
	}
	
	public void llenaCamposTelefonos()
	{
		if(filaTelefono == -1)filaTelefono = 0;
		
		txtTelefono.setText(String.valueOf(tablaTelefonos.getValueAt(filaTelefono,2)));		
		cmbTipo.setSelectedItem(String.valueOf(tablaTelefonos.getValueAt(filaTelefono,4)));
		
	}

	
	public void consultaClientes()
	{
		fila = -1;
		int iContador,iCiclo;
		iCiclo = 0;
		String sCondicion = "";
		String sCount     = "";
		if(comboBox.getSelectedItem().equals("CURP"))
		{
			sCount = "SELECT count(1) as cont FROM clientes where " + "curp LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			sCondicion = "SELECT * FROM clientes WHERE " + "curp LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
		}
		if(comboBox.getSelectedItem().equals("Nombre"))
		{
			sCount = "SELECT count(1) as cont FROM clientes where " + "nombre LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			sCondicion = "SELECT * FROM clientes WHERE " + "nombre LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			
			//sCondicion = "nombre LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
		}
		if(comboBox.getSelectedItem().equals("Apellido Paterno"))
		{
			sCount = "SELECT count(1) as cont FROM clientes where " + "apellidoPaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			sCondicion = "SELECT * FROM clientes WHERE " + "apellidoPaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			//sCondicion = "apellidoPaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
		}
		if(comboBox.getSelectedItem().equals("Apellido Materno"))
		{
			sCount = "SELECT count(1) as cont FROM clientes where " + "apellidoMaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			sCondicion = "SELECT * FROM clientes WHERE " + "apellidoMaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
			//sCondicion = "apellidoMaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
		}
		if(comboBox.getSelectedItem().equals("Todos"))
		{
			sCount = "SELECT count(1) as cont FROM clientes";
			sCondicion = "SELECT * FROM clientes";
			//sCondicion = "Todos";
		}
		
		System.out.println("sWhere " + sCondicion);
		
		DbConsultar objConsultar = new DbConsultar();
		//vConsultaCliente = objConsultar.consultarCliente(sCondicion);
		vConsultaCliente = objConsultar.consultarCliente(sCount,sCondicion );
		int iCuenta;
		iContador = vConsultaCliente.length;
		/*for(iCuenta = 0; iCuenta < vConsultaCliente.length; iCuenta++ )
		{
			for(int iCuentaAux = 1; iCuentaAux < vConsultaCliente[iCuenta].length;iCuentaAux++)
			{
				System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCliente[iCuenta][iCuentaAux].toString());
			}
		}*/
		//dtmCliente = new DefaultTableModel(vConsultaCliente, columNames);
		dtmCliente = new MiModelo(vConsultaCliente, columNames);
		table.setModel(dtmCliente);	
		table.setRowSelectionInterval(0,0);
		fila = table.getSelectedRow();		
		table.requestFocus();
		
		if(iContador == 0)
		{
			btnModificar.setEnabled(false);
			
			txtNombre.setText(""); 
			txtApellidoPaterno.setText(""); 
			txtApellidoMaterno.setText(""); 
			txtCurp.setText(""); 
			txtDireccion.setText(""); 
			txtEmail.setText(""); 
			/*txtCelular.setText(""); 
			txtTelefono.setText("");*/
			txtObservaciones.setText("");
			
			textField.requestFocus();
			JOptionPane.showMessageDialog(this, "No se ha podido Encontar al cliente con los datos Proporcionados", "Error Consultar Clientes", JOptionPane.INFORMATION_MESSAGE );
			
		}
		else
		{
			table.setRowSelectionInterval(0,0);
			fila = table.getSelectedRow();		
			table.requestFocus();
			browseCliente();			
			
		}
	}
	
	public void browseCliente()
	{
		llenaCamposFormulario(fila);
		btnModificar.setEnabled(true);
		consultaTelefonos(fila,columna);
		llenaCamposTelefonos();
	}
	public void consultaTelefonos(int filaCliente, int columnaCliente)
	{
		if ((fila == -1) && (columna == -1))
		{
			fila = 0; 
			columna = 0;
		}
		else
		{
			fila = filaCliente; 
			columna = columnaCliente;
			
		}
		    
		if ((fila > -1) && (columna > -1))
		{
			String sLlave = String.valueOf(table.getValueAt(fila,3));
			System.out.println("curp " + sLlave);
			String sCount;
			String sConsulta; 
			
				System.out.println("otra condicion");
				sCount = "SELECT count( 1)AS cont FROM telefonos WHERE curp ='" +sLlave+"'";
							
				sConsulta = "SELECT t1.curp, t1.id, t1.telefono,t1.tipo as 'Tipo Telefono' , t2.descripcion" +
							 " FROM telefonos AS t1, tipotelefonos AS t2 " +
							 " WHERE t1.tipo = t2.idTipo AND curp ='"+sLlave+"'";
			

			
		    dtmTelefono = new MiModelo3();
		    
		    DbConsultarTelefonos objConsultar = new DbConsultarTelefonos();
		    ResultSet rs = objConsultar.consultarTelefonos(sConsulta);
		    
		    if(rs == null) System.out.println("rs null");		    
		    
            operacionesJTable.rellena(rs, dtmTelefono);
            
            tablaTelefonos.setModel(dtmTelefono);
												   
		}
	}
	

	
	public void llenaCamposFormulario(int fila)
	{
		/*txtNombre.setText(vConsultaCliente[0][0].toString()); 
		txtApellidoPaterno.setText(vConsultaCliente[0][1].toString()); 
		txtApellidoMaterno.setText(vConsultaCliente[0][2].toString()); 
		txtCurp.setText(vConsultaCliente[0][3].toString()); 
		txtDireccion.setText(vConsultaCliente[0][4].toString()); 
		txtEmail.setText(vConsultaCliente[0][6].toString()); 
		txtCelular.setText(vConsultaCliente[0][7].toString()); 
		txtTelefono.setText(vConsultaCliente[0][8].toString()); 
		txtObservaciones.setText(vConsultaCliente[0][9].toString());
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");	
		try {
			Date fecha = formato.parse(vConsultaCliente[0][5].toString());
			cal.setDate(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//if(fila == -1) fila = 0;
		
		txtNombre.setText(String.valueOf(table.getValueAt(fila,0)));
		txtApellidoPaterno.setText(String.valueOf(table.getValueAt(fila,1)));			
		txtApellidoMaterno.setText(String.valueOf(table.getValueAt(fila,2)));
		txtCurp.setText(String.valueOf(table.getValueAt(fila,3)));
		txtDireccion.setText(String.valueOf(table.getValueAt(fila,4)));
		//txtAnioNacimiento.setText(String.valueOf(table.getValueAt(fila,5)));
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");	
		try {
			Date fecha = formato.parse(table.getValueAt(fila,5).toString());
			cal.setDate(fecha);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		txtEmail.setText(String.valueOf(table.getValueAt(fila,6)));
		//txtCelular.setText(String.valueOf(table.getValueAt(fila,7)));
		//txtTelefono.setText(String.valueOf(table.getValueAt(fila,8)));
		txtObservaciones.setText(String.valueOf(table.getValueAt(fila,7)));
		
	}
	
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getSource() == comboBox )
		{
			if(comboBox.getSelectedItem() == "Todos")
			{
				textField.setText("");
				textField.setEnabled(false);
			
			}
			else
				textField.setEnabled(true);
		}
		
		if(evento.getActionCommand() == "Guardar")
		{
			ActualizarCliente();   
			btnModificar.setEnabled(true);
    	}
		if(evento.getActionCommand() == "Consultar")
		{
			if(comboBox.getSelectedItem() == "Seleccionar")
				JOptionPane.showMessageDialog(this, "Debe Seleccionar una opcion de Busqueda", "Error Consultar Clientes", JOptionPane.ERROR_MESSAGE );
			else if(comboBox.getSelectedItem() != "Todos" && textField.getText().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe proporcionar el valor de Busqueda", "Error Consultar Clientes", JOptionPane.ERROR_MESSAGE );
			else
				consultaClientes();
		}
		if(evento.getActionCommand() == "Modificar")
		{
			interfazModificar();
			txtNombre.requestFocus();
		}
		if(evento.getActionCommand() == "Cancelar")
		{
			llenaCamposFormulario(fila);
			interfazInicial();		
			
			btnModificar.setEnabled(true);
		}
		if (evento.getSource() == btnNuevo )
		{
			btnNuevo.setEnabled(false);
		    btnModificarTelefono.setEnabled(false);
		    btnAgregarTel.setEnabled(true);
			limpiarCamposTel();
		}
		if (evento.getSource() == btnAgregarTel )
		{
			
			
		    
			if(txtTelefono.getText().length() < 7 || txtTelefono.getText().length() > 10)
				JOptionPane.showMessageDialog(this, "El Telefono puede tener minimo 7 digitos maximo 10 digitos", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else if(txtTelefono.getText().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos un telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else if(cmbTipo.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(this, "Debe Proporcionar el Tipo de telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else
			{
				
	    		DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
				Object[] datosFila = new Object[modelo.getColumnCount()];
	            
				datosFila[0] = String.valueOf(table.getValueAt(fila,3));
				datosFila[1] = "";
				datosFila[2] = txtTelefono.getText().trim();
				datosFila[3] = tipotelefono[cmbTipo.getSelectedIndex()-1];
				datosFila[4] = descripcionTipotelefono[cmbTipo.getSelectedIndex()-1];
				
	            modelo.addRow(datosFila);
	            limpiarCamposTel();
	            
	            btnNuevo.setEnabled(true);
			    btnModificarTelefono.setEnabled(true);
			    btnAgregarTel.setEnabled(false);
	            
			}
			
		}
		if (evento.getSource() == btnModificarTelefono )
		{
			actualizarTablaTelefono();
		}
		
	}
	
	public void limpiarCamposTel()
	{
		txtTelefono.setText("");
        cmbTipo.setSelectedIndex(0);
        txtTelefono.requestFocus();
	}
	
	public void ActualizarCliente()
	{
		boolean bBanderaCorrecto;
		String sConsulta = "";
		String sClavecliente;
		
		boolean lCorrecto;
        
        obtieneValoresFormularios();
        lCorrecto = validarCampos();
        if (lCorrecto == true)
        {
        	System.out.println("Campos Faltantes " + sCamposFaltantes);
        	JOptionPane.showMessageDialog(this, sCamposFaltantes, "ERROR", JOptionPane.INFORMATION_MESSAGE );
        }
        /*else if(txtCelular.getText().trim().length() > 10)
    		JOptionPane.showMessageDialog(this, "El número celular no es correcto Debe ser igual o menor que 10 Digitos!!!", "ERROR", JOptionPane.ERROR_MESSAGE );
    	else if(txtTelefono.getText().trim().length() > 10)
    		JOptionPane.showMessageDialog(this, "El número Telefonico no es correcto Debe ser igual o menor que 10 Digitos!!!", "ERROR", JOptionPane.ERROR_MESSAGE );*/
    	else
        {
        	sClavecliente = txtCurp.getText().toString();
        	/*if (txtAnioNacimiento.getText() != sAnioNacimiento)
    		{
    			sConsulta += "fechaNacimiento = '" +txtAnioNacimiento.getText() + "', ";
    		
    		}*/
    		if(sConsulta.length() == 0 ) sConsulta += "fechaNacimiento = '" + sAnioNacimiento + "'";
    		else sConsulta += ", fechaNacimiento = '" + sAnioNacimiento + "'";
    		
    		System.out.println(" txtApellidoPaterno.getText() " + txtApellidoPaterno.getText() + "sApellidoPaterno " + sApellidoPaterno);
    		if (txtApellidoPaterno.getText() != sApellidoPaterno)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "apellidoPaterno = '" + txtApellidoPaterno.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", apellidoPaterno = '" + txtApellidoPaterno.getText().trim().toUpperCase() + "'";
    		}
    		if (txtApellidoMaterno.getText() != sApellidoMaterno)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "apellidoMaterno = '" + txtApellidoMaterno.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", apellidoMaterno = '" + txtApellidoMaterno.getText().trim().toUpperCase() + "'";
    		}
    		if (txtNombre.getText() != sNombre)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "nombre = '"+ txtNombre.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", nombre = '"+ txtNombre.getText().trim().toUpperCase() + "'";
    		}		
    		if (txtDireccion.getText() != sDireccion)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "direccion = '" + txtDireccion.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", direccion = '" + txtDireccion.getText().trim().toUpperCase() + "'";
    		}
    		if (txtEmail.getText() != sEmail)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "email = '" + txtEmail.getText().trim().toLowerCase() + "'";
    			else sConsulta += ", email = '" + txtEmail.getText().trim().toLowerCase() + "'";
    		}
    		/*if (txtCelular.getText() != sCelular)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "celular = '" + txtCelular.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", celular = '" + txtCelular.getText().trim().toUpperCase() + "'";
    		}
    		if (txtTelefono.getText() != sTelefono)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "telefonoCasa = '" + txtTelefono.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", telefonoCasa = '" + txtTelefono.getText().trim().toUpperCase() + "'";
    		}*/
    		if (txtObservaciones.getText() != sObservaciones)
    		{
    			if(sConsulta.length() == 0 ) sConsulta += "observaciones = '" + txtObservaciones.getText().trim().toUpperCase() + "'";
    			else sConsulta += ", observaciones = '" + txtObservaciones.getText().trim().toUpperCase() + "'";
    		}
    		System.out.println("tamaño consulta "+ sConsulta.length());
    		if(sConsulta.length() == 0)
    			JOptionPane.showMessageDialog(this, "No se ha realizado ninguna Modificacion en la información", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
    		else
    		{
    			System.out.println("Consulta actualizar " + sConsulta);
    			
    			DbActualizar objInsertar = new DbActualizar();
    			DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
    			bBanderaCorrecto = objInsertar.actualizarCliente(sConsulta , sClavecliente, modelo);
    			
    			if (bBanderaCorrecto == true) 
    			{    				
    				actualizarTabla();
    	    		interfazInicial();
    	    		consultaTelefonos(fila,columna);
    	    		llenaCamposTelefonos();
    	    		renombrarCarpetaEtapa();
    	    		JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
    			}
    			else
    				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos " + objInsertar.excepcion , "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
    		}
    		
    		
        }
		
		
	}
	
	public void actualizarTablaTelefono()
	{   
		int filaAux;
		if (filaTelefono == -1)
		{
			filaAux = 0;
		}
		else filaAux = filaTelefono;
		
		tablaTelefonos.setValueAt(tipotelefono[cmbTipo.getSelectedIndex()-1], filaAux, 3);
		tablaTelefonos.setValueAt(txtTelefono.getText().trim(), filaAux, 2);
		tablaTelefonos.setValueAt(cmbTipo.getSelectedItem(), filaAux, 4);
		//cmbTipoTelefono.getSelectedItem()
	}
	
	public void actualizarTabla()
	{   
		int filaAux;
		if (fila == -1)
		{
			filaAux = 0;
		}
		else filaAux = fila;
		
		table.setValueAt(txtNombre.getText(), filaAux, 0);
		table.setValueAt(txtApellidoPaterno.getText(), filaAux, 1);
		table.setValueAt(txtApellidoMaterno.getText(), filaAux, 2);
		table.setValueAt(txtCurp.getText(), filaAux, 3);
		table.setValueAt(txtDireccion.getText(), filaAux, 4);
		table.setValueAt(sAnioNacimiento, filaAux, 5);
		//table.setValueAt(txtFechaNacimiento.getText(), filaAux, 5);
		table.setValueAt(txtEmail.getText(), filaAux, 6);
		/*table.setValueAt(txtCelular.getText(), filaAux, 7);
		table.setValueAt(txtTelefono.getText(), filaAux, 8);*/
		table.setValueAt(txtObservaciones.getText(), filaAux, 7);
	}
	
	public void interfazInicial ()
	{
		txtNombre.setEditable(false);
		txtApellidoPaterno.setEditable(false);
		txtApellidoMaterno.setEditable(false);
		txtCurp.setEditable(false);
		txtDireccion.setEditable(false);
		cal.getSpinner().setEnabled(false);
		txtEmail.setEditable(false);		
		txtTelefono.setEditable(false);
		txtObservaciones.setEditable(false);
		
		btnModificar.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnConsultar.setEnabled(true);
		comboBox.setEnabled(true);
		textField.setEnabled(true);
		
		table.setEnabled(true);
		tablaTelefonos.setEnabled(true);
		
		btnNuevo.setEnabled(false);
	    btnModificarTelefono.setEnabled(false);
	    btnAgregarTel.setEnabled(false);
		cmbTipo.setEnabled(false);
		//table.setEnabled(false);
		
	}
	
	public void interfazModificar ()
	{
		txtNombre.setEditable(true);
		txtApellidoPaterno.setEditable(true);
		txtApellidoMaterno.setEditable(true);
		//txtCurp.setEditable(true);
		txtDireccion.setEditable(true);
		cal.getSpinner().setEnabled(true);
		txtEmail.setEditable(true);		
		txtTelefono.setEditable(true);
		txtObservaciones.setEditable(true);
		btnModificar.setEnabled(false);
		btnGuardar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnConsultar.setEnabled(false);
		comboBox.setEnabled(false);
		textField.setEnabled(false);
		table.setEnabled(false);
		//tablaTelefonos.setEnabled(false);
		
		btnNuevo.setEnabled(true);
	    btnModificarTelefono.setEnabled(true);
	    btnAgregarTel.setEnabled(false);
		cmbTipo.setEnabled(true);
		
		
	}
	
	public boolean validarCampos()
	{
		boolean lCampoVacio = false;
		sCamposFaltantes = "";
		/*if (sAnioNacimiento.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Año Nacimiento es obligatorio\n";
		}*/
		if (sApellidoPaterno.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Apellido Paterno es obligatorio\n";
		}
		if (sApellidoMaterno.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Apellido Materno es obligatorio\n";
		}
		if (sNombre.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Nombre es obligatorio\n";
		}		
		if (sCurp.length() == 0) {
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Curp es obligatorio\n";
		}
		
		System.out.println("validaCampo " + cal.getDateFormatString() + lCampoVacio);
		
		return lCampoVacio;
	}

	public void obtieneValoresFormularios()
	{
		 sNombre          = txtNombre.getText();
		 sApellidoPaterno = txtApellidoPaterno.getText();
		 sApellidoMaterno = txtApellidoMaterno.getText();
		 sCurp            = txtCurp.getText();
		 sDireccion       = txtDireccion.getText();
		 
		 SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = cal.getDate();
		 sAnioNacimiento= formato.format(date);
		 
		 
		 sEmail           = txtEmail.getText();		 
		 //sTelefono        = txtTelefono.getText();
		 sObservaciones  = txtObservaciones.getText();
		 		 
	}
	
	public JInternalFrame initialize()
	{
		frmConsultarClientes = new JInternalFrame("Modificar / Consultar Catálogo Clientes",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmConsultarClientes.setFrameIcon(new ImageIcon(URLIcon));
		frmConsultarClientes.setBounds(100, 100, 600, 500);
		frmConsultarClientes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmConsultarClientes.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		frmConsultarClientes.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelbusqueda = new JPanel();
		panelbusqueda.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(panelbusqueda, BorderLayout.CENTER);
		GridBagLayout gbl_panelbusqueda = new GridBagLayout();
		gbl_panelbusqueda.columnWidths = new int[]{96, 90, 51, 94, 49, 22, 0};
		gbl_panelbusqueda.rowHeights = new int[]{35, 35, 35, 35, 18, 35, 41, 0, 0};
		gbl_panelbusqueda.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelbusqueda.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, Double.MIN_VALUE};
		panelbusqueda.setLayout(gbl_panelbusqueda);
		
		JLabel lblCurp = new JLabel("CURP:");
		GridBagConstraints gbc_lblCurp = new GridBagConstraints();
		gbc_lblCurp.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurp.anchor = GridBagConstraints.EAST;
		gbc_lblCurp.gridx = 0;
		gbc_lblCurp.gridy = 0;
		panelbusqueda.add(lblCurp, gbc_lblCurp);
		
		txtCurp = new JTextField();
		txtCurp.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtCurp = new GridBagConstraints();
		gbc_txtCurp.gridwidth = 2;
		gbc_txtCurp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurp.insets = new Insets(0, 0, 5, 5);
		gbc_txtCurp.gridx = 1;
		gbc_txtCurp.gridy = 0;
		panelbusqueda.add(txtCurp, gbc_txtCurp);
		txtCurp.setColumns(18);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha Nacimiento:");
		GridBagConstraints gbc_lblFechaDeNacimiento = new GridBagConstraints();
		gbc_lblFechaDeNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblFechaDeNacimiento.gridx = 4;
		gbc_lblFechaDeNacimiento.gridy = 0;
		panelbusqueda.add(lblFechaDeNacimiento, gbc_lblFechaDeNacimiento);
		
		cal=new JDateChooser("dd/MM/yyyy",false);
		GridBagConstraints gbc_fechanacimiento = new GridBagConstraints();
		gbc_fechanacimiento.insets = new Insets(0, 0, 5, 0);
		gbc_fechanacimiento.gridx = 5;
		gbc_fechanacimiento.gridy = 0;
		panelbusqueda.add(cal, gbc_fechanacimiento);
		
		
		JLabel lblNewLabel = new JLabel("Nombre(s):");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panelbusqueda.add(lblNewLabel, gbc_lblNewLabel);
		
		txtNombre = new JTextField();
		txtNombre.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridwidth = 5;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panelbusqueda.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 2;
		panelbusqueda.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtApellidoPaterno = new JTextField();
		txtApellidoPaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.gridwidth = 2;
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 2;
		panelbusqueda.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Apellido Materno:");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 3;
		gbc_lblNewLabel_3.gridy = 2;
		panelbusqueda.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		txtApellidoMaterno = new JTextField();
		txtApellidoMaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.gridwidth = 2;
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.gridx = 4;
		gbc_txtApellidoMaterno.gridy = 2;
		panelbusqueda.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Direccion:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 3;
		panelbusqueda.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		txtDireccion = new JTextField();
		txtDireccion.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
		gbc_txtDireccion.gridwidth = 5;
		gbc_txtDireccion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccion.gridx = 1;
		gbc_txtDireccion.gridy = 3;
		panelbusqueda.add(txtDireccion, gbc_txtDireccion);
		txtDireccion.setColumns(10);				
		
		JLabel lblNewLabel_6 = new JLabel("e-mail:");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 4;
		panelbusqueda.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		txtEmail = new JTextField();
		txtEmail.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 4;
		panelbusqueda.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblObservaciones = new JLabel("Observaciones:");
		GridBagConstraints gbc_lblObservaciones = new GridBagConstraints();
		gbc_lblObservaciones.anchor = GridBagConstraints.EAST;
		gbc_lblObservaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblObservaciones.gridx = 0;
		gbc_lblObservaciones.gridy = 5;
		panelbusqueda.add(lblObservaciones, gbc_lblObservaciones);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.HORIZONTAL;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 5;
		panelbusqueda.add(scrollPane, gbc_scrollPane);
		
		txtObservaciones = new JTextArea();
		scrollPane.setViewportView(txtObservaciones);
		
		JPanel panelTelefono = new JPanel();		
		panelTelefono.setBorder(new TitledBorder(null, "Telefonos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbl_panelPrin = new GridBagConstraints();
		gbl_panelPrin.insets = new Insets(0, 0, 5, 0);
		gbl_panelPrin.gridwidth = 8;
		gbl_panelPrin.fill = GridBagConstraints.BOTH;
		gbl_panelPrin.gridx = 0;
		gbl_panelPrin.gridy = 6;
		panelbusqueda.add(panelTelefono, gbl_panelPrin);
		
		GridBagLayout gbl_panelTelefono = new GridBagLayout();
		gbl_panelTelefono.columnWidths = new int[]{90, 80, 96, 36, 56, 0, 0, 0, 0};
		gbl_panelTelefono.rowHeights = new int[]{96, 43, 0};
		gbl_panelTelefono.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelTelefono.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelTelefono.setLayout(gbl_panelTelefono);
		
		JLabel lblTelefono = new JLabel("Telefono: ");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();		
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 1;
		gbc_lblTelefono.gridy = 1;
		panelTelefono.add(lblTelefono, gbc_lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();		
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.gridx = 2;
		gbc_txtTelefono.gridy = 1;
		panelTelefono.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		GridBagConstraints gbc_tipo = new GridBagConstraints();
		gbc_tipo.anchor = GridBagConstraints.EAST;
		gbc_tipo.insets = new Insets(0, 0, 5, 5);
		gbc_tipo.gridx = 3;
		gbc_tipo.gridy = 1;
		panelTelefono.add(lblTipo, gbc_tipo);
		
		cmbTipo = new JComboBox();
		cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--"}));
		GridBagConstraints gbc_cmbTipo = new GridBagConstraints();
		gbc_cmbTipo.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipo.gridx = 4;
		gbc_cmbTipo.gridy = 1;
		panelTelefono.add(cmbTipo, gbc_cmbTipo);
		
		estilos es = new estilos();
		
		btnNuevo = new JButton();
		URL URLNuevo = es.cargador("img/nuevo.png");
		ImageIcon imgNuevo = new ImageIcon(URLNuevo);
		btnNuevo.setIcon(imgNuevo);
		btnNuevo.addActionListener(this);
		GridBagConstraints gbc_btnNuevo = new GridBagConstraints();
		gbc_btnNuevo.anchor = GridBagConstraints.WEST;
		gbc_btnNuevo.insets = new Insets(0,9,5,0);
		gbc_btnNuevo.gridx = 5;
		gbc_btnNuevo.gridy = 1;
		panelTelefono.add(btnNuevo, gbc_btnNuevo);		
		
		btnAgregarTel = new JButton();
		URL URLAceptar = es.cargador("img/agregar.png");
		ImageIcon imgAceptar = new ImageIcon(URLAceptar);
		btnAgregarTel.setIcon(imgAceptar);
		btnAgregarTel.addActionListener(this);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.WEST;
		gbc_btnAgregar.insets = new Insets(0, 9, 5, 0);
		gbc_btnAgregar.gridx = 6;
		gbc_btnAgregar.gridy = 1;
		panelTelefono.add(btnAgregarTel, gbc_btnAgregar);
		
		btnModificarTelefono = new JButton();
		URL URLModificar = es.cargador("img/modificar.png");
		ImageIcon imgModificarTel = new ImageIcon(URLModificar);
		btnModificarTelefono.setIcon(imgModificarTel);	
		btnModificarTelefono.addActionListener(this);
		GridBagConstraints gbc_btnModificarTel = new GridBagConstraints();
		gbc_btnModificarTel.anchor = GridBagConstraints.WEST;
		gbc_btnModificarTel.insets = new Insets(0, 9, 5, 0);
		gbc_btnModificarTel.gridx = 7;
		gbc_btnModificarTel.gridy = 1;
		panelTelefono.add(btnModificarTelefono, gbc_btnModificarTel);
		
		tablaTelefonos = new JTable();
		tablaTelefonos.setPreferredScrollableViewportSize(new Dimension(150, 120));
		
		tablaTelefonos.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaTelefono = tablaTelefonos.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaTelefono);
				columnaTelefono = tablaTelefonos.columnAtPoint(e.getPoint());    
				if ((filaTelefono > -1) && (columnaTelefono > -1))
				{
					llenaCamposTelefonos();
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
		
		tablaTelefonos.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTelefono = tablaTelefonos.getSelectedRow();
					columnaTelefono = tablaTelefonos.getSelectedColumn();
					if((filaTelefono > -1) && (columnaTelefono > -1))
					{
						llenaCamposTelefonos();
					}
					System.out.println("Encontrar punto con teclas");			
				}
			}
		});

		
		spTelefonos = new JScrollPane(tablaTelefonos);
		GridBagConstraints gbc_tablaTelefonos = new GridBagConstraints();
		gbc_tablaTelefonos.gridwidth = 9;
		gbc_tablaTelefonos.fill = GridBagConstraints.BOTH;
		gbc_tablaTelefonos.gridx = 0;
		gbc_tablaTelefonos.gridy = 0;
		panelTelefono.add(spTelefonos, gbc_tablaTelefonos);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		GridBagConstraints gbc_btnModificar = new GridBagConstraints();
		gbc_btnModificar.anchor = GridBagConstraints.EAST;
		gbc_btnModificar.insets = new Insets(0, 0, 0, 5);
		gbc_btnModificar.gridx = 2;
		gbc_btnModificar.gridy = 8;
		panelbusqueda.add(btnModificar, gbc_btnModificar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 3;
		gbc_btnGuardar.gridy = 8;
		panelbusqueda.add(btnGuardar, gbc_btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 8;
		panelbusqueda.add(btnCancelar, gbc_btnCancelar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Consultas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmConsultarClientes.getContentPane().add(panel_1, BorderLayout.NORTH);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{110, 142, 71, 106, 152, 0};
		gbl_panel_1.rowHeights = new int[]{22, 33, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblConsultarPor = new JLabel("Consultar Por:");
		GridBagConstraints gbc_lblConsultarPor = new GridBagConstraints();
		gbc_lblConsultarPor.insets = new Insets(0, 0, 5, 5);
		gbc_lblConsultarPor.anchor = GridBagConstraints.EAST;
		gbc_lblConsultarPor.gridx = 1;
		gbc_lblConsultarPor.gridy = 0;
		panel_1.add(lblConsultarPor, gbc_lblConsultarPor);
		
		comboBox = new JComboBox();
		//comboBox.addActionListener(estilos.tranfiereElFoco);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar", "Todos","CURP", "Nombre", "Apellido Paterno", "Apellido Materno"}));
		comboBox.addActionListener(this);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		comboBox.addActionListener(this);
		panel_1.add(comboBox, gbc_comboBox);
		
		textField = new JTextField();
		textField.addActionListener(estilos.tranfiereElFoco);
		textField.addKeyListener( 
				  new KeyAdapter() { 
				     public void keyPressed(KeyEvent e) { 
				       if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				       { 
				    	   btnConsultar.doClick(); 
				       } 
				     } 
				  }); 
		
		GridBagConstraints gbc_textField = new GridBagConstraints();		
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 0;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(this);
		GridBagConstraints gbc_btnConsultar = new GridBagConstraints();
		gbc_btnConsultar.anchor = GridBagConstraints.WEST;
		gbc_btnConsultar.insets = new Insets(0, 0, 5, 0);
		gbc_btnConsultar.gridx = 4;
		gbc_btnConsultar.gridy = 0;
		panel_1.add(btnConsultar, gbc_btnConsultar);
		
		table = new JTable();
		table.setPreferredScrollableViewportSize(new Dimension(500, 120));
		table.isEditing();
		spClientes = new JScrollPane(table);
		vConsultaCliente = new Object[0][0];
		dtmCliente = new MiModelo(vConsultaCliente, columNames);
		table.setModel(dtmCliente);
		table.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				fila = table.rowAtPoint(e.getPoint()); 
				columna = table.columnAtPoint(e.getPoint());    
				if ((fila > -1) && (columna > -1))
				  {
					filaTelefono = -1;
					columnaTelefono = -1;
					
					browseCliente();
				}
				}
			});
		table.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					fila = table.getSelectedRow();
					columna = table.getSelectedColumn();
					if((fila > -1) && (columna > -1))
					{
						filaTelefono = -1;
						columnaTelefono = -1;
						
						browseCliente();
					}
					System.out.println("Encontrar punto con teclas UP o DOWN");			
				}
			}
		});
		/*table.setModel(new DefaultTableModel(
			new Object[][] {
				{new Integer(1989), "Mary Liliana"},
			},
			new String[] {
				"A\u00F1o Nacimiento", "Curp"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});*/
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 8;
		gbc_table.fill = GridBagConstraints.HORIZONTAL;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panel_1.add(spClientes, gbc_table);
		
		llenatipoTelefono();
		return frmConsultarClientes;
	}
}
class MiModelo extends DefaultTableModel
{
   public boolean isCellEditable (int row, int column)
   {
      return false;
   }
   public MiModelo(Object[][] data, Object[] columnNames)
   {
	   super( data, columnNames);
   }
}
class MiModelo3 extends DefaultTableModel
{
   public boolean isCellEditable (int row, int column)
   {
      return false;
   }
   public MiModelo3()
   {
	  super();
	  
   }
}