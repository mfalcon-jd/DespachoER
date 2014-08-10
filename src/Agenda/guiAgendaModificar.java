package Agenda;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.net.URL;
import java.sql.*;

import GUI.estilos;
import bd.DBConsultarTramite;
import bd.DbActualizarAgenda;
import bd.DbActualizarTramite;
import bd.DbConsultar;
import bd.DbConsultarAgenda;
import bd.DbConsultarTelefonos;
import bd.DbConsultarTipoTelefono;
import bd.DbEliminarAgenda;

public class guiAgendaModificar extends JInternalFrame implements ActionListener {

	private JInternalFrame frmModificarContactos;
	private JTextField txtBusqueda;
	public static JTable tableAgenda;
	private JPanel panel;
	private JLabel lblNombre;
	static JTextField txtNombre;
	private JLabel lblApellidoPaterno;
	static JTextField txtApellidoPaterno;
	private JLabel lblApellidomaterno;
	static JTextField txtApellidoMaterno;
	private JLabel lblDireccion;
	static JTextField txtDireccion;
	private JLabel lblFax;
	private JTextField txtFax;
	private JLabel lblNotas;
	private JScrollPane scrollPane;
	static JTextArea txtNotas;
	private JPanel panel_1;
	private JLabel lblTelefono;
	private JTextField txtTelefono;
	private JLabel lblTipoTelefono;
	private JComboBox cmbTipoTelefono;
	private JLabel lblEmail;
	private JTextField txtEmail;
	public static JTable tableTelefono;
	private JButton btnGuardar;
	private JButton btnCancelar;
	private JButton btnAgregar;
	private JButton btnModificar;
	private JButton btnNuevo;
	private JButton btnModificarTelefono;	
	private JButton btnAgregarTel;
	private DefaultComboBoxModel jtmTipoTelefono;
	
	String[] descripcionTipotelefono;
	String[] tipotelefono;
	
	JButton btnBuscar;
	JComboBox cmbBusqueda;
	MiModelo2 dtmAgenda;
	MiModelo3 dtmTelefono;
	
	String[] columNamesAgenda = {"Nombre","Apellido Paterno", "Apellido Materno","Direccion","Notas","ID Contacto"};
	String[] columNamesTelefono = {"idContacto","id","telefono","tipo","email","fax"};
	
	Object [][] vConsultaAgenda;
	Object [][] vConsultaTelefono;
	
	public static int filaAgenda = -1;
	private int columnaAgenda;
	
	public static int filaTelefono = -1;
	private int columnaTelefono;
	
	public static int idContacto;
	public static int idTelefono;
	private JScrollPane spAgenda;
	private JScrollPane spTelefonos;
	/*public static void main(String[] args) {
		estilos obj = new estilos();		
		obj.aplicar();	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiAgendaModificar window = new guiAgendaModificar();
					window.frmModificarContactos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public guiAgendaModificar() 
	{
		//initialize();
	}
	
	public void interfazInicial()
	{
		txtNombre.setEditable(false); 
		txtApellidoPaterno.setEditable(false); 
		txtApellidoMaterno.setEditable(false); 
		txtDireccion.setEditable(false);  
		txtEmail.setEditable(false); 
		txtTelefono.setEditable(false); 
		txtNotas.setEditable(false); 
		txtFax.setEditable(false); 
		cmbTipoTelefono.setSelectedIndex(0);
		cmbTipoTelefono.setEnabled(false);
		cmbBusqueda.setEnabled(true);
		
		btnGuardar.setEnabled(false);
		btnCancelar.setEnabled(false);
		//btnAgregar.setEnabled(false);
		btnBuscar.setEnabled(true);
		
		txtBusqueda.setEditable(true);
		btnModificar.setEnabled(false);
		tableTelefono.setEnabled(true);
		tableAgenda.setEnabled(true);
		
		btnNuevo.setEnabled(false);
	    btnModificarTelefono.setEnabled(false);
	    btnAgregarTel.setEnabled(false);
		
		
	}
	public void interfazModificar()
	{
		txtNombre.setEditable(true); 
		txtApellidoPaterno.setEditable(true); 
		txtApellidoMaterno.setEditable(true); 
		txtDireccion.setEditable(true);  
		txtEmail.setEditable(true); 
		txtTelefono.setEditable(true); 
		txtNotas.setEditable(true); 
		txtFax.setEditable(true); 
		//cmbTipoTelefono.setSelectedIndex(0);
		cmbTipoTelefono.setEnabled(true);
		cmbBusqueda.setEnabled(false);
		
		btnGuardar.setEnabled(true);
		btnCancelar.setEnabled(true);
		//btnAgregar.setEnabled(true);
		btnBuscar.setEnabled(false);
		
		txtBusqueda.setEditable(false);
		btnModificar.setEnabled(false);
		
		tableTelefono.setEnabled(true);
		tableAgenda.setEnabled(false);
		
		btnNuevo.setEnabled(true);
	    btnModificarTelefono.setEnabled(true);
	    btnAgregarTel.setEnabled(false);
	}
	public void sustituyeTipoTelefono()
	{
		for (int i=0; i< vConsultaTelefono.length;i++)
		{
			for(int i2=0;i2 < vConsultaTelefono[i].length; i2++)
			{
				if(i2 == 3)
				{
					System.out.println("descripcionTipotelefono.length " + descripcionTipotelefono.length);
					for(int cuenta = 0;cuenta <descripcionTipotelefono.length;cuenta++)
					{	
						System.out.println("DESCRIPCIO " + vConsultaTelefono[i][3]);
						System.out.println("Descripcion Telefono: " + descripcionTipotelefono[cuenta]);
						if(descripcionTipotelefono[cuenta].equals(vConsultaTelefono[i][3].toString()))
							{vConsultaTelefono[i][3] = tipotelefono[cuenta];
							System.out.println("TIPO TELEFONO: " + tipotelefono[cuenta]);}
					}
				}
			}
			
			
		}
	}
	
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getActionCommand() == "Modificar")  
		{ 
			interfazModificar();
			
		}
		if(evento.getActionCommand() == "Cancelar")  
		{ 
			interfazInicial();
			llenaCamposAgenda();
			llenaCamposTelefonos();
			btnModificar.setEnabled(true);
		}
		if(evento.getActionCommand() == "+")  
		{ 
			if(txtTelefono.getText().length() == 0)
			{
				JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos un telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			}
			else if(cmbTipoTelefono.getSelectedIndex() == 0)
			{
				JOptionPane.showMessageDialog(this, "Debe Proporcionar el Tipo de telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			}
			else
			{
				int filaAux ;
			
				if(filaTelefono == -1)
				{
					filaAux = 0;
				}
				else filaAux = filaTelefono;
				/*vConsultaTelefono[filaAux][2] = txtTelefono.getText();
				vConsultaTelefono[filaAux][3] = tipotelefono[cmbTipoTelefono.getSelectedIndex()-1];
				vConsultaTelefono[filaAux][4] = txtEmail.getText();
				vConsultaTelefono[filaAux][5] = txtFax.getText();*/
				
				DefaultTableModel modelo = (DefaultTableModel) tableTelefono.getModel();
				Object[] datosFila = new Object[modelo.getColumnCount()];
                
				datosFila[0] = String.valueOf(tableTelefono.getValueAt(filaAux,1));
				datosFila[2] = txtTelefono.getText().trim();
				datosFila[3] = tipotelefono[cmbTipoTelefono.getSelectedIndex()-1];
				datosFila[4] = txtEmail.getText().trim();
				datosFila[5] = txtFax.getText().trim();
				
                modelo.addRow(datosFila);
				
				actualizarTablaTelefono();
			}
			
				
		}
		if(evento.getActionCommand() == "Buscar")  
		{ 
			if(cmbBusqueda.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(this, "Debe seleccionar una opcion de Busqueda", "Error Busqueda", JOptionPane.ERROR_MESSAGE );
			else if(cmbBusqueda.getSelectedItem() != "Todos" && txtBusqueda.getText().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe Ingresar un dato para la busqueda", "Error Busqueda", JOptionPane.ERROR_MESSAGE );
			else
			{
				filaAgenda = -1;
				columnaAgenda = 0;
				consultaClientes();
			}
		}
		if(evento.getActionCommand() == "Guardar")  
		{
			String sConsulta = "";
			String sClaveContacto;
			boolean bBanderaCorrecto;
			int filaAux = 0;
        	if(filaAgenda == -1) filaAux = 0;
        	else filaAux = filaAgenda;
        	
        	sClaveContacto = String.valueOf(tableAgenda.getValueAt(filaAux,5));
        	
        	if(sConsulta.length() == 0 ) sConsulta += "nombre = '" + txtNombre.getText() + "'";
    		else sConsulta += ", nombre = '" + txtNombre.getText() + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "apellidoPaterno = '" + txtApellidoPaterno.getText() + "'";
    		else sConsulta += ", apellidoPaterno = '" + txtApellidoPaterno.getText() + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "apellidoMaterno = '" + txtApellidoMaterno.getText() + "'";
    		else sConsulta += ", apellidoMaterno = '" + txtApellidoMaterno.getText() + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "direccion = '" + txtDireccion.getText() + "'";
    		else sConsulta += ", direccion = '" + txtDireccion.getText() + "'";
        	
        	if(sConsulta.length() == 0 ) sConsulta += "notas = '" + txtNotas.getText() + "'";
    		else sConsulta += ", notas = '" + txtNotas.getText() + "'";
        	
        	System.out.println("Consulta actualizar " + sConsulta);
        	sustituyeTipoTelefono();
			DbActualizarAgenda objActualizarAgenda = new DbActualizarAgenda();
			
			DefaultTableModel modelo = (DefaultTableModel) tableTelefono.getModel();
			bBanderaCorrecto = objActualizarAgenda.actualizarAgenda(sConsulta , sClaveContacto,vConsultaTelefono, modelo);
			
			if (bBanderaCorrecto == true) 
			{
				JOptionPane.showMessageDialog(this, "Los Datos Han Sido Actualizados Exitosamente!!", "Dato Actualizados", JOptionPane.INFORMATION_MESSAGE );
				actualizarTablaContacto();
				interfazInicial();
				btnModificar.setEnabled(true);
				
			}
			else
				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos " + objActualizarAgenda.exception, "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
        	
		}
		if (evento.getSource() == cmbBusqueda )
		{
			if(cmbBusqueda.getSelectedItem() == "Todos")
			{
				txtBusqueda.setEnabled(false);
				txtBusqueda.setText("");
			}
			else
				txtBusqueda.setEnabled(true);
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
			else if(cmbTipoTelefono.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(this, "Debe Proporcionar el Tipo de telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else
			{
				
	    		DefaultTableModel modelo = (DefaultTableModel) tableTelefono.getModel();
				Object[] datosFila = new Object[modelo.getColumnCount()];
	            
				datosFila[0] = String.valueOf(tableTelefono.getValueAt(filaTelefono,0));
				datosFila[1] = txtTelefono.getText().trim();
				datosFila[2] = txtEmail.getText().trim();
				datosFila[3] = "";
				datosFila[4] = txtFax.getText().trim();
				datosFila[5] = cmbTipoTelefono.getSelectedItem();
				datosFila[6] = tipotelefono[cmbTipoTelefono.getSelectedIndex()-1];
				
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
		cmbTipoTelefono.setSelectedIndex(0);
		txtEmail.setText("");
		txtFax.setText("");
	}
	public void actualizarTablaTelefono()
	{
		int filaAux;
		if (filaTelefono == -1)
		{
			filaAux = 0;
		}
		else filaAux = filaTelefono;
		
		tableTelefono.setValueAt(txtTelefono.getText(), filaAux, 1);
		tableTelefono.setValueAt(cmbTipoTelefono.getSelectedItem(), filaAux, 5);
		tableTelefono.setValueAt(txtEmail.getText(), filaAux, 2);
		tableTelefono.setValueAt(txtFax.getText(), filaAux, 4);
		tableTelefono.setValueAt(tipotelefono[cmbTipoTelefono.getSelectedIndex() - 1], filaAux, 6);
		
	}
	
	public void actualizarTablaContacto()
	{   
		int filaAux;
		if (filaAgenda == -1)
		{
			filaAux = 0;
		}
		else filaAux = filaAgenda;
		
		tableAgenda.setValueAt(txtNombre.getText(), filaAux, 0);
		tableAgenda.setValueAt(txtApellidoPaterno.getText(), filaAux, 1);
		tableAgenda.setValueAt(txtApellidoMaterno.getText(), filaAux, 2);
		tableAgenda.setValueAt(txtDireccion.getText(), filaAux, 3);
		tableAgenda.setValueAt(txtNotas.getText(), filaAux, 4);
		
	}
	
	public void consultaClientes()
	{
		int iContador,iCiclo;
		iCiclo = 0;
		String sCount = "SELECT count(1) AS cont " +
						"FROM agenda " +
						"WHERE ";
	     String sCondicion = "SELECT * " +
			   " FROM agenda "+
			   " WHERE  ";
	     
	   if(cmbBusqueda.getSelectedItem().equals("Todos"))
	    {
		   	sCount = "";
		   	sCondicion = "";
		    	
		   sCount = "SELECT count(1) AS cont FROM agenda ";
		   sCondicion = "SELECT * FROM agenda";
		}
		if(cmbBusqueda.getSelectedItem().equals("Nombre"))
		{
			sCount     += "nombre LIKE " +"'" +txtBusqueda.getText()+"%'";
			sCondicion += "nombre LIKE " +"'" +txtBusqueda.getText()+"%'";
		}
		if(cmbBusqueda.getSelectedItem().equals("Apellido Paterno"))
		{
			sCount     += "apellidoPaterno LIKE " +"'" +txtBusqueda.getText()+"%'";
			sCondicion += "apellidoPaterno LIKE " +"'" +txtBusqueda.getText()+"%'";
		}
		if(cmbBusqueda.getSelectedItem().equals("Apellido Materno"))
		{
			sCount     += "apellidoMaterno LIKE " +"'" +txtBusqueda.getText()+"%'";
			sCondicion += "apellidoMaterno LIKE " +"'" +txtBusqueda.getText()+"%'";
		}
		
		System.out.println("sWhere " + sCondicion);
		
		DbConsultarAgenda objConsultar = new DbConsultarAgenda();
		
		vConsultaAgenda = objConsultar.consultarAgenda("Agenda",sCount, sCondicion);
		int iCuenta;
		iContador = vConsultaAgenda.length;
		for(iCuenta = 0; iCuenta < vConsultaAgenda.length; iCuenta++ )
		{
			for(int iCuentaAux = 1; iCuentaAux < vConsultaAgenda[iCuenta].length;iCuentaAux++)
			{
				System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaAgenda[iCuenta][iCuentaAux].toString());
			}
		}
		//dtmCliente = new DefaultTableModel(vConsultaCliente, columNames);
		dtmAgenda = new MiModelo2(vConsultaAgenda, columNamesAgenda);
		tableAgenda.setModel(dtmAgenda);
		
		if(iContador == 0)
		{
			
			txtNombre.setText(""); 
			txtApellidoPaterno.setText(""); 
			txtApellidoMaterno.setText(""); 
			txtDireccion.setText(""); 
			txtEmail.setText(""); 
			txtTelefono.setText("");
			txtNotas.setText("");
			txtFax.setText("");
			cmbTipoTelefono.setSelectedIndex(0);
			
			vConsultaTelefono = new Object [0][0];
			//dtmTelefono = new MiModelo2(vConsultaTelefono, columNamesTelefono);
			//tableTelefono.setModel(dtmTelefono);
			
			btnModificar.setEnabled(false);
			
			JOptionPane.showMessageDialog(this, "No se ha podido Encontar al cliente con los datos Proporcionados", "Error Consultar Clientes", JOptionPane.INFORMATION_MESSAGE );
		}
		else
		{
			tableAgenda.setRowSelectionInterval(0,0);
			filaAgenda = tableAgenda.getSelectedRow();		
			tableAgenda.requestFocus();
			browseAgenda();
		}
	}
	
	public void browseAgenda()
	{
		llenaCamposAgenda();	
		//MouseEvent e = new MouseEvent(spAgenda, 0, ABORT,1, 1, 1, 1, closable);
		consultaTelefonos(filaAgenda, columnaAgenda);
		llenaCamposTelefonos();
		btnModificar.setEnabled(true);
						
	}
	
	public static  void llenaCamposAgenda()
	{
		int filaAux;
		if(filaAgenda == -1) filaAux = 0;
		else filaAux = filaAgenda;
		txtNombre.setText(String.valueOf(tableAgenda.getValueAt(filaAux,0))); 
		txtApellidoPaterno.setText(String.valueOf(tableAgenda.getValueAt(filaAux,1))); 
		txtApellidoMaterno.setText(String.valueOf(tableAgenda.getValueAt(filaAux,2))); 
		txtDireccion.setText(String.valueOf(tableAgenda.getValueAt(filaAux,3))); 
		txtNotas.setText(String.valueOf(tableAgenda.getValueAt(filaAux,4)));
		
				
	}
	
	public void consultaTelefonos(int fila, int columna)
	{
		if ((fila == -1) && (columna == -1))
		{
			filaAgenda = 0; 
			columnaAgenda = 0;
		}
		else
		{
			filaAgenda = fila; 
			columnaAgenda = columna;
		}
		    
		if ((filaAgenda > -1) && (columnaAgenda > -1))
		{
			String sLlave = String.valueOf(tableAgenda.getValueAt(filaAgenda,5));
			System.out.println("curp " + sLlave);
			String sCount;
			String sConsulta; 
			
				System.out.println("otra condicion");
				sCount = "SELECT count( 1)AS cont FROM telefonos WHERE idContacto ='" +sLlave+"'";
							
				sConsulta = "SELECT t1.idContacto, t1.telefono,t1.email,t1.id,t1.fax, t2.descripcion, t1.tipo AS 'Tipo telefono'" +
							 " FROM telefonos AS t1, tipotelefonos AS t2 " +
							 " WHERE t1.tipo = t2.idTipo AND idContacto ='"+sLlave+"'";
			

			/*DbConsultarAgenda objConsultar = new DbConsultarAgenda();
			vConsultaTelefono = objConsultar.consultarAgenda("Telefono",sCount,sConsulta);
			int iCuenta;
			int iContador = vConsultaTelefono.length;
			System.out.println("contactos telefono " + iContador );
			for(iCuenta = 0; iCuenta < vConsultaTelefono.length; iCuenta++ )
			{
				System.out.println("y¿tamaño columna " + vConsultaTelefono[iCuenta].length);
				for(int iCuentaAux = 0; iCuentaAux < vConsultaTelefono[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaTelefono[iCuenta][iCuentaAux].toString());
				}
			}
			dtmTelefono = new MiModelo2(vConsultaTelefono, columNamesTelefono);
			tableTelefono.setModel(dtmTelefono);
			*/
		    dtmTelefono = new MiModelo3();
		    
		    DbConsultarTelefonos objConsultar = new DbConsultarTelefonos();
		    ResultSet rs = objConsultar.consultarTelefonos(sConsulta);
		    
		    if(rs == null) System.out.println("rs null");		    
		    
            operacionesJTable.rellena(rs, dtmTelefono);
            
            tableTelefono.setModel(dtmTelefono);
												   
		}
	}
	
	public static int getIdContacto()
	{
		idContacto = 0;
		
		idContacto = Integer.parseInt(String.valueOf(tableAgenda.getValueAt(filaAgenda,5)));
		
		return idContacto;
	}
	
	public static int getIdTelefono()
	{
		idTelefono = 0;
		
		idTelefono = Integer.parseInt(String.valueOf(tableTelefono.getValueAt(filaTelefono,3)));
		
		return idContacto;
	}
	
	public void llenaCamposTelefonos()
	{
		if(filaTelefono == -1)filaTelefono = 0;
		
		txtEmail.setText(String.valueOf(tableTelefono.getValueAt(filaTelefono,2)));
		txtTelefono.setText(String.valueOf(tableTelefono.getValueAt(filaTelefono,1)));		
		cmbTipoTelefono.setSelectedItem(String.valueOf(tableTelefono.getValueAt(filaTelefono,5)));
		txtFax.setText(String.valueOf(tableTelefono.getValueAt(filaTelefono,4)));
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
		cmbTipoTelefono.setModel(jtmTipoTelefono);
				
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
		cmbTipoTelefono.addActionListener(this);
	}

	public JInternalFrame initialize() 
	{
		frmModificarContactos = new JInternalFrame("Modificar Contactos",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");
		frmModificarContactos.setFrameIcon(new ImageIcon(URLIcon));
		frmModificarContactos.setBounds(100, 100, 600, 500);
		frmModificarContactos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{122, 70, 117, 141, 0, 67, 0};
		gridBagLayout.rowHeights = new int[]{42, 70, 144, 87, 34, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmModificarContactos.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblBuscar = new JLabel("Buscar:");
		GridBagConstraints gbc_lblBuscar = new GridBagConstraints();
		gbc_lblBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_lblBuscar.anchor = GridBagConstraints.EAST;
		gbc_lblBuscar.gridx = 1;
		gbc_lblBuscar.gridy = 0;
		frmModificarContactos.getContentPane().add(lblBuscar, gbc_lblBuscar);
		
		cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--","Todos","Nombre", "Apellido Paterno", "Apellido Materno"}));
		GridBagConstraints gbc_cmbBusqueda = new GridBagConstraints();
		gbc_cmbBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBusqueda.fill = GridBagConstraints.EAST;
		gbc_cmbBusqueda.gridx = 2;
		gbc_cmbBusqueda.gridy = 0;
		cmbBusqueda.addActionListener(this);
		frmModificarContactos.getContentPane().add(cmbBusqueda, gbc_cmbBusqueda);
		
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
		frmModificarContactos.getContentPane().add(txtBusqueda, gbc_txtBusqueda);
		txtBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 4;
		gbc_btnBuscar.gridy = 0;
		frmModificarContactos.getContentPane().add(btnBuscar, gbc_btnBuscar);
		
		tableAgenda = new JTable();
		tableAgenda.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spAgenda = new JScrollPane(tableAgenda);
		vConsultaAgenda = new Object [0][0];
		dtmAgenda = new MiModelo2(vConsultaAgenda, columNamesAgenda);
		tableAgenda.setModel(dtmAgenda);
		tableAgenda.addMouseListener(new PopClickListener());
		tableAgenda.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaAgenda = tableAgenda.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaAgenda);
				columnaAgenda = tableAgenda.columnAtPoint(e.getPoint());    
				if ((filaAgenda > -1) && (columnaAgenda > -1))
				{
					filaTelefono = -1;
					columnaTelefono = -1;
					/*llenaCamposAgenda();
					consultaTelefonos(filaAgenda, columnaAgenda);
					llenaCamposTelefonos();*/
					browseAgenda();
														   
					}
				}
			});
		
		tableAgenda.addKeyListener(new KeyAdapter()
		{
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaAgenda = tableAgenda.getSelectedRow();
					columnaTelefono = tableAgenda.getSelectedColumn();
					if((filaAgenda > -1) && (columnaAgenda > -1))
					{
						filaTelefono = -1;
						columnaTelefono = -1;
						/*llenaCamposAgenda();
						consultaTelefonos(filaAgenda,columnaAgenda);
						llenaCamposTelefonos();*/
						browseAgenda();
					}
					System.out.println("Encontrar punto con teclas");			
				}
			}
		});


		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.insets = new Insets(0, 0, 5, 0);
		gbc_table.gridwidth = 6;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		frmModificarContactos.getContentPane().add(spAgenda, gbc_table);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 6;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frmModificarContactos.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 195, 0, 0, 133, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 55, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel.add(lblNombre, gbc_lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 4;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 0;
		panel.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		lblApellidoPaterno = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblApellidoPaterno = new GridBagConstraints();
		gbc_lblApellidoPaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoPaterno.gridx = 0;
		gbc_lblApellidoPaterno.gridy = 1;
		panel.add(lblApellidoPaterno, gbc_lblApellidoPaterno);
		
		txtApellidoPaterno = new JTextField();
		txtApellidoPaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 1;
		panel.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(10);
		
		lblApellidomaterno = new JLabel("ApellidoMaterno:");
		GridBagConstraints gbc_lblApellidomaterno = new GridBagConstraints();
		gbc_lblApellidomaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidomaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidomaterno.gridx = 3;
		gbc_lblApellidomaterno.gridy = 1;
		panel.add(lblApellidomaterno, gbc_lblApellidomaterno);
		
		txtApellidoMaterno = new JTextField();
		txtApellidoMaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.gridx = 4;
		gbc_txtApellidoMaterno.gridy = 1;
		panel.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(10);
		
		lblDireccion = new JLabel("Direccion:");
		GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
		gbc_lblDireccion.anchor = GridBagConstraints.EAST;
		gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccion.gridx = 0;
		gbc_lblDireccion.gridy = 2;
		panel.add(lblDireccion, gbc_lblDireccion);
		
		txtDireccion = new JTextField();
		txtDireccion.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
		gbc_txtDireccion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDireccion.gridwidth = 4;
		gbc_txtDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccion.gridx = 1;
		gbc_txtDireccion.gridy = 2;
		panel.add(txtDireccion, gbc_txtDireccion);
		txtDireccion.setColumns(10);
		
		lblNotas = new JLabel("Notas:");
		GridBagConstraints gbc_lblNotas = new GridBagConstraints();
		gbc_lblNotas.anchor = GridBagConstraints.EAST;
		gbc_lblNotas.insets = new Insets(0, 0, 0, 5);
		gbc_lblNotas.gridx = 0;
		gbc_lblNotas.gridy = 3;
		panel.add(lblNotas, gbc_lblNotas);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 4;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		panel.add(scrollPane, gbc_scrollPane);
		
		txtNotas = new JTextArea();
		scrollPane.setViewportView(txtNotas);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Datos de Contacto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.gridwidth = 6;
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 3;
		frmModificarContactos.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{92, 139, 64, 99, 90, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{69, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		tableTelefono = new JTable();
		tableTelefono.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spAgenda = new JScrollPane(tableTelefono);
		vConsultaTelefono = new Object [0][0];
		/*dtmTelefono = new MiModelo2(vConsultaTelefono, columNamesTelefono);
		tableTelefono.setModel(dtmTelefono);*/
		tableTelefono.addMouseListener(new PopClickListener());
		tableTelefono.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaTelefono = tableTelefono.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaTelefono);
				columnaTelefono = tableTelefono.columnAtPoint(e.getPoint());    
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
		
		tableTelefono.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTelefono = tableTelefono.getSelectedRow();
					columnaTelefono = tableTelefono.getSelectedColumn();
					if((filaTelefono > -1) && (columnaTelefono > -1))
					{
						llenaCamposTelefonos();
					}
					System.out.println("Encontrar punto con teclas");			
				}
			}
		});

		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.gridwidth = 9;
		gbc_table_1.insets = new Insets(0, 0, 5, 5);
		gbc_table_1.fill = GridBagConstraints.BOTH;
		gbc_table_1.gridx = 0;
		gbc_table_1.gridy = 0;
		panel_1.add(spAgenda, gbc_table_1);
		
		lblTelefono = new JLabel("Telefono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.gridx = 0;
		gbc_lblTelefono.gridy = 1;
		panel_1.add(lblTelefono, gbc_lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 1;
		panel_1.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);
		
		lblTipoTelefono = new JLabel("Tipo Telefono:");
		GridBagConstraints gbc_lblTipoTelefono = new GridBagConstraints();
		gbc_lblTipoTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTipoTelefono.gridx = 3;
		gbc_lblTipoTelefono.gridy = 1;
		panel_1.add(lblTipoTelefono, gbc_lblTipoTelefono);
		
		cmbTipoTelefono = new JComboBox();
		cmbTipoTelefono.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--"}));
		GridBagConstraints gbc_cmbTipoTelefono = new GridBagConstraints();
		gbc_cmbTipoTelefono.anchor = GridBagConstraints.WEST;
		gbc_cmbTipoTelefono.insets = new Insets(0, 0, 5, 0);
		gbc_cmbTipoTelefono.gridx = 4;
		gbc_cmbTipoTelefono.gridy = 1;
		panel_1.add(cmbTipoTelefono, gbc_cmbTipoTelefono);
		
		lblFax = new JLabel("Fax: ");
		GridBagConstraints gbc_lblFax = new GridBagConstraints();
		gbc_lblFax.anchor = GridBagConstraints.EAST;
		gbc_lblFax.insets = new Insets(0,0,5,5);
		gbc_lblFax.gridx = 6;
		gbc_lblFax.gridy = 1;
		panel_1.add(lblFax, gbc_lblFax);
		
		txtFax = new JTextField();
		txtFax.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtFax = new GridBagConstraints();
		gbc_txtFax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFax.insets = new Insets(0,0,5,0);
		gbc_txtFax.gridwidth = 2;
		gbc_txtFax.gridx = 7;
		gbc_txtFax.gridy = 1;
		panel_1.add(txtFax, gbc_txtFax);		
		
		lblEmail = new JLabel("e_mail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 0, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 2;
		panel_1.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.gridwidth = 4;
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 2;
		panel_1.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		btnNuevo = new JButton();
		ImageIcon imgNuevo = new ImageIcon("img/nuevo.png");
		btnNuevo.setIcon(imgNuevo);
		btnNuevo.addActionListener(this);
		GridBagConstraints gbc_btnNuevo = new GridBagConstraints();
		gbc_btnNuevo.anchor = GridBagConstraints.WEST;
		gbc_btnNuevo.insets = new Insets(0,9,5,0);
		gbc_btnNuevo.gridx = 5;
		gbc_btnNuevo.gridy = 2;
		panel_1.add(btnNuevo, gbc_btnNuevo);		
		
		btnAgregarTel = new JButton();
		ImageIcon imgAceptar = new ImageIcon("img/agregar.png");
		btnAgregarTel.setIcon(imgAceptar);
		btnAgregarTel.addActionListener(this);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.WEST;
		gbc_btnAgregar.insets = new Insets(0, 9, 5, 0);
		gbc_btnAgregar.gridx = 6;
		gbc_btnAgregar.gridy = 2;
		panel_1.add(btnAgregarTel, gbc_btnAgregar);
		
		btnModificarTelefono = new JButton();
		ImageIcon imgModificarTel = new ImageIcon("img/modificar.png");
		btnModificarTelefono.setIcon(imgModificarTel);	
		btnModificarTelefono.addActionListener(this);
		GridBagConstraints gbc_btnModificarTel = new GridBagConstraints();
		gbc_btnModificarTel.anchor = GridBagConstraints.WEST;
		gbc_btnModificarTel.insets = new Insets(0, 9, 5, 0);
		gbc_btnModificarTel.gridx = 7;
		gbc_btnModificarTel.gridy = 2;
		panel_1.add(btnModificarTelefono, gbc_btnModificarTel);
		
		btnModificar = new JButton("Modificar");
		btnModificar.addActionListener(this);
		GridBagConstraints gbc_btnModificar = new GridBagConstraints();
		gbc_btnModificar.anchor = GridBagConstraints.EAST;
		gbc_btnModificar.insets = new Insets(0,0,0,5);
		gbc_btnModificar.gridx = 1;
		gbc_btnModificar.gridy = 4;
		frmModificarContactos.getContentPane().add(btnModificar, gbc_btnModificar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.anchor = GridBagConstraints.CENTER;
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 4;
		frmModificarContactos.getContentPane().add(btnGuardar, gbc_btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 4;
		frmModificarContactos.getContentPane().add(btnCancelar, gbc_btnCancelar);
		
		llenatipoTelefono();
		interfazInicial();
		return frmModificarContactos;
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
class PopUpDemo extends JPopupMenu implements ActionListener{
    JMenuItem cancelarItem;
    JMenuItem cambiaretapaItem;
    public PopUpDemo()
    {
        cancelarItem = new JMenuItem("Eliminar");
        cancelarItem.addActionListener(this);
        add(cancelarItem);
        
        cambiaretapaItem = new JMenuItem("Cambiar Etapa");
        cambiaretapaItem.addActionListener(this);
        add(cambiaretapaItem);
    }
    
    public int getIndex()
    {
    	int index = 0;
    	
    	if(guiAgendaModificar.filaAgenda == 0) index = 0;
    	else if(guiAgendaModificar.filaAgenda > 0) index = guiAgendaModificar.filaAgenda - 1;
    	
    	return index;
    }
    
    public void actionPerformed(ActionEvent evento)
	{
    	System.out.println("menu evnt" + evento.getSource());
    	if(evento.getActionCommand() == "Eliminar")
    	{
    		
    			System.out.println("evento table agenda " + guiAgendaModificar.filaAgenda);
    			System.out.println("evento table" + evento.getActionCommand());
        		DbEliminarAgenda objeliminarAgenda = new DbEliminarAgenda();
        		objeliminarAgenda.eliminarContacto(guiAgendaModificar.getIdContacto());
        		
        		DefaultTableModel modelo = (DefaultTableModel)guiAgendaModificar.tableAgenda.getModel(); 
        		modelo.removeRow(guiAgendaModificar.filaAgenda);
        		
        		DefaultTableModel modelo2 = (DefaultTableModel)guiAgendaModificar.tableTelefono.getModel();
        		


                while (modelo2.getRowCount() > 0)
        		modelo2.removeRow(0);
        		/*for(int i = 0; i <= modelo2.getRowCount(); i++)
        		{
        			modelo2.removeRow(i);
        		}*/
                guiAgendaModificar.tableAgenda.requestFocus();
                guiAgendaModificar.tableAgenda.setRowSelectionInterval(getIndex(),getIndex());
                guiAgendaModificar.filaAgenda = guiAgendaModificar.tableAgenda.getSelectedRow();		
                guiAgendaModificar.tableAgenda.requestFocus();
                
                //guiAgendaModificar.llenaCamposAgenda();
                guiAgendaModificar objModificar = new guiAgendaModificar();
                objModificar.browseAgenda();
    		
    		
    	}
	}
}

class PopClickListener extends MouseAdapter {
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
        PopUpDemo menu = new PopUpDemo();
        menu.show(e.getComponent(), e.getX(), e.getY());
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