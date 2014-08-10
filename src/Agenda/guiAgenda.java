package Agenda;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import bd.DbConsultarTipoTelefono;
import bd.DbInsertarAgenda;

import GUI.estilos;


public class guiAgenda extends JInternalFrame implements ActionListener {

	private JInternalFrame frmAgenda;
	
	private JLabel lblDireccin;
	
	private JPanel panel;
	private JLabel lblTelefono;
	private JLabel lblTipo;
	
	private JLabel lblEmail;
	
	private JLabel lblFax;
	
	private JTable tablaContactos;
	private JLabel lblNotas;
	
	private JScrollPane scrollPane;	
	
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnAgregar;
	
	private JScrollPane spContactos;
	private int filaContacto = -1;
	private int columnaContacto;
	MiModelo2 dtmContactos;
	Object [][] vTelefono;
	
	private JComboBox cmbTipo;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JTextArea  txtNotas;
	private JTextField txtNombre;
	private JTextField txtApellidoPaterno;
	private JTextField txtApellidoMaterno;
	private JTextField txtDireccion;
	private JTextField txtFax;
	
	int i = 0;
	int j = 1;
	int k = 2;
	int l = 3;
		
	private DefaultComboBoxModel jtmTipoTelefono;
	
	String[] descripcionTipotelefono;
	String[] tipotelefono;
	String[] columNamesTelefonos = {"Tipo","Telefono","Fax", "Email"};
	Vector vector=new Vector(20);
	/*public static void main(String[] args) {
		estilos obj = new estilos();		
		obj.aplicar();	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiAgenda window = new guiAgenda();
					window.frmAgenda.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	public guiAgenda()
	{
		//initialize();
	}
	
	public void llenaCampos()
	{
		int filaAux;
		if(filaContacto == -1) filaAux = 0;
		else filaAux = filaContacto;
		
		cmbTipo.setSelectedItem(String.valueOf(tablaContactos.getValueAt(filaAux,0)));
		txtTelefono.setText(String.valueOf(tablaContactos.getValueAt(filaAux,1)));
		txtFax.setText(String.valueOf(tablaContactos.getValueAt(filaAux,2)));
		txtEmail.setText(String.valueOf(tablaContactos.getValueAt(filaAux,3)));
	}
	
	public void interfazAgregar()
	{
		cmbTipo.setEnabled(true);
		cmbTipo.setSelectedIndex(0);
		txtTelefono.setEditable(true);
		txtTelefono.setText("");
		txtEmail.setEditable(true);
		txtEmail.setText("");
		txtFax.setEditable(true);
		txtFax.setText("");
		txtTelefono.requestFocus();
		
	}
	
	public void interfazGuardar()
	{
		cmbTipo.setEnabled(false);
		txtTelefono.setEditable(false);
		txtEmail.setEditable(false);
		txtNotas.setEditable(false);
		txtNombre.setEditable(false);
		txtApellidoPaterno.setEditable(false);
		txtApellidoMaterno.setEditable(false);
		txtDireccion.setEditable(false);
		txtFax.setEditable(false);
		btnGuardar.setEnabled(false);
		btnAgregar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNuevo.setEnabled(true);
		
	}
	
	public void interfazInicial()
	{
		cmbTipo.setEnabled(true);
		txtTelefono.setEditable(true);
		txtEmail.setEditable(true);
		txtNotas.setEditable(true);
		txtNombre.setEditable(true);
		txtApellidoPaterno.setEditable(true);
		txtApellidoMaterno.setEditable(true);
		txtDireccion.setEditable(true);
		txtFax.setEditable(true);
		btnGuardar.setEnabled(true);
		btnAgregar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnNuevo.setEnabled(false);
		
	}
	
	public void interfazCancelar()
	{
		cmbTipo.setEnabled(false);
		txtTelefono.setEditable(false);
		txtEmail.setEditable(false);
		txtNotas.setEditable(false);
		txtNombre.setEditable(false);
		txtApellidoPaterno.setEditable(false);
		txtApellidoMaterno.setEditable(false);
		txtDireccion.setEditable(false);
		txtFax.setEditable(false);
		btnGuardar.setEnabled(false);
		btnAgregar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNuevo.setEnabled(true);
	}
	
    public void limpiaCampos()
    {
    	cmbTipo.setSelectedIndex(0);
		txtTelefono.setText("");
		txtEmail.setText("");
		txtNotas.setText("");
		txtNombre.setText("");
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtDireccion.setText("");
		txtFax.setText("");
		
		filaContacto    = -1;
		columnaContacto = 0;
		
		vector=new Vector(20);
		
		vTelefono = new Object [0][0];
		dtmContactos = new MiModelo2(vTelefono, columNamesTelefonos);
		tablaContactos.setModel(dtmContactos);
    }
	
	public void llenatipoTelefono()
	{
		DbConsultarTipoTelefono objTelefono = new DbConsultarTipoTelefono();
		Object [][] vConsultaTipoTelefono = objTelefono.consultarTipoTelefono();
		int iContador, iCuenta;
		iContador = vConsultaTipoTelefono.length;
		String[] descripcionTipotelefono = new String[iContador];
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
	
	public void actionPerformed(ActionEvent evento)
	{
		if (evento.getActionCommand().equals("Cancelar"))
	    {
			limpiaCampos();
			interfazGuardar();
	    }
		if (evento.getActionCommand().equals("Nuevo"))
	    {
			interfazInicial();
			limpiaCampos();
			
			filaContacto    = -1;
			columnaContacto = 0;
			
			vector=new Vector(20);
			
			vTelefono = new Object[0][0];
			dtmContactos = new MiModelo2(vTelefono, columNamesTelefonos);
		    tablaContactos.setModel(dtmContactos);
		    
		    btnNuevo.setEnabled(false);
			
	    }
		if (evento.getActionCommand().equals("Agregar +"))
	    {
			if(txtFax.getText().length() > 10)
				JOptionPane.showMessageDialog(this, "El Fax debe ser igual o menor que 10 digitos!!", "Error Fax", JOptionPane.ERROR_MESSAGE );
			else if(txtTelefono.getText().length() > 10)
				JOptionPane.showMessageDialog(this, "El telefono debe ser igual o menor que 10 digitos!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else if(txtTelefono.getText().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos un telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else if(cmbTipo.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(this, "Debe Proporcionar el Tipo de telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else
			{
				vector.add(tipotelefono[cmbTipo.getSelectedIndex()-1]);
				vector.add(txtTelefono.getText().trim());
				vector.add(txtFax.getText().trim());
				vector.add(txtEmail.getText().trim().toLowerCase());
				
				vTelefono = new Object[vector.size()/4][4];
				int total = vector.size()/4;
				int to= 0;
				
				while(to < total)
				{
					vTelefono[to][0]= vector.elementAt(i);
					vTelefono[to][1]=vector.elementAt(j);
					vTelefono[to][2]=vector.elementAt(k);
					vTelefono[to][3]=vector.elementAt(l);
							
					to++;
					
					i+=4;
					j+=4;
					k+=4;
					l+=4;	
				}
				i = 0;
				j = 1;
				k = 2;
				l = 3;
			
			    dtmContactos = new MiModelo2(vTelefono, columNamesTelefonos);
			    tablaContactos.setModel(dtmContactos);
			    tablaContactos.changeSelection(0, 0, false, false);
				interfazAgregar();
				
				int row =  tablaContactos.getRowCount () - 1;
				 Rectangle rect = tablaContactos.getCellRect(row, 0, true);
				 tablaContactos.scrollRectToVisible(rect);
				 tablaContactos.clearSelection();
				 tablaContactos.setRowSelectionInterval(row, row);
				 DefaultTableModel modelo = (DefaultTableModel)tablaContactos.getModel();
				 modelo.fireTableDataChanged();

			}
			
	    }
		if (evento.getActionCommand().equals("Guardar"))
	    {
			if(txtNombre.getText().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos el Nombre del Contacto!!", "Error Contacto", JOptionPane.ERROR_MESSAGE );
			else if(vTelefono.length == 0)
				JOptionPane.showMessageDialog(this, "Debe al Menos Agregar un Telefono!!", "Error Telefono", JOptionPane.ERROR_MESSAGE );
			else
			{
				boolean bBanderaCorrecto = false;
		     
				 String sWhereAgenda = "insert into agenda (nombre, apellidoPaterno, apellidoMaterno, direccion, notas)values( '"+txtNombre.getText().trim().toUpperCase()+"',";
				// String sWhereTelefono = "insert into telefonos(telefono,tipo,email,fax,idContacto) values ('" + txtTelefono.getText()+ "',";
				 
				 sWhereAgenda += " '" +txtApellidoPaterno.getText().trim().toUpperCase()+"',";
				 
				 sWhereAgenda += " '" +txtApellidoMaterno.getText().trim().toUpperCase()+"',";
				 
				 sWhereAgenda += " '" +txtDireccion.getText().trim().toUpperCase()+"',";
				 
				 sWhereAgenda += " '" +txtNotas.getText().trim().toUpperCase()+"')";
				 
				 
				/* sWhereTelefono += " '" + tipotelefono[cmbTipo.getSelectedIndex()-1]+ "',";
				 
				 sWhereTelefono += " '" + txtEmail.getText().trim().toLowerCase() + "',";
				 
				 if(txtFax.getText() == "" )sWhereTelefono += " '" + 0 + "',";
				 else sWhereTelefono += " '" + txtFax.getText() + "',";
				 
				 sWhereTelefono += " '" + 1+ "')";*/
				 
				 DbInsertarAgenda objagenda = new DbInsertarAgenda();
	     		
	     		//bBanderaCorrecto = objagenda.insertarAgenda(sWhereAgenda, sWhereTelefono);
				 bBanderaCorrecto = objagenda.insertarAgenda(sWhereAgenda, vTelefono);
	     		if (bBanderaCorrecto == true) 
	     		{
	     			interfazGuardar();
	     			JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
	     		}
	     		else
	     		{
	     			JOptionPane.showMessageDialog(this, "No se pudo guardar el contacto" + objagenda.excepcion, "Error al Insertar Contacto", JOptionPane.ERROR_MESSAGE );
	     		}
			} 
			 
	    }
		
	}

	public JInternalFrame initialize() 
	{
		frmAgenda = new JInternalFrame("Agenda",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");
		frmAgenda.setFrameIcon(new ImageIcon(URLIcon));
		frmAgenda.setBounds(100, 100, 600, 500);
		frmAgenda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Datos Personales", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmAgenda.getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{95, 82, 109, 105, 66, 75, 0};
		gbl_panel_1.rowHeights = new int[]{37, 37, 37, 49, 73, 37, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		panel_1.add(lblNombre, gbc_lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 5;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 0;
		panel_1.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblApellidoPaterno = new GridBagConstraints();
		gbc_lblApellidoPaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoPaterno.gridx = 0;
		gbc_lblApellidoPaterno.gridy = 1;
		panel_1.add(lblApellidoPaterno, gbc_lblApellidoPaterno);
		
		txtApellidoPaterno = new JTextField();
		txtApellidoPaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.gridwidth = 2;
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 1;
		panel_1.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(10);
		
		JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
		GridBagConstraints gbc_lblApellidoMaterno = new GridBagConstraints();
		gbc_lblApellidoMaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoMaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoMaterno.gridx = 3;
		gbc_lblApellidoMaterno.gridy = 1;
		panel_1.add(lblApellidoMaterno, gbc_lblApellidoMaterno);
		
		txtApellidoMaterno = new JTextField();
		txtApellidoMaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.gridwidth = 2;
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.gridx = 4;
		gbc_txtApellidoMaterno.gridy = 1;
		panel_1.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(10);
		
		lblDireccin = new JLabel("Direcci\u00F3n:");
		GridBagConstraints gbc_lblDireccin = new GridBagConstraints();
		gbc_lblDireccin.anchor = GridBagConstraints.EAST;
		gbc_lblDireccin.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccin.gridx = 0;
		gbc_lblDireccin.gridy = 2;
		panel_1.add(lblDireccin, gbc_lblDireccin);
		
		txtDireccion = new JTextField();
		txtDireccion.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
		gbc_txtDireccion.gridwidth = 5;
		gbc_txtDireccion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccion.gridx = 1;
		gbc_txtDireccion.gridy = 2;
		panel_1.add(txtDireccion, gbc_txtDireccion);
		txtDireccion.setColumns(10);
		
		lblNotas = new JLabel("Notas:");
		GridBagConstraints gbc_lblNotas = new GridBagConstraints();
		gbc_lblNotas.anchor = GridBagConstraints.EAST;
		gbc_lblNotas.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotas.gridx = 0;
		gbc_lblNotas.gridy = 3;
		panel_1.add(lblNotas, gbc_lblNotas);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 5;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 3;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		txtNotas = new JTextArea();
		scrollPane.setViewportView(txtNotas);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Contacto", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 6;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 4;
		panel_1.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{71, 124, 49, 48, 68, 112, 0};
		gbl_panel.rowHeights = new int[]{37, 37, 37, 54, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblTelefono = new JLabel("Telefono:");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.gridx = 0;
		gbc_lblTelefono.gridy = 0;
		panel.add(lblTelefono, gbc_lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 0;
		panel.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);
		
		lblTipo = new JLabel("Tipo:");
		GridBagConstraints gbc_lblTipo = new GridBagConstraints();
		gbc_lblTipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipo.anchor = GridBagConstraints.EAST;
		gbc_lblTipo.gridx = 2;
		gbc_lblTipo.gridy = 0;
		panel.add(lblTipo, gbc_lblTipo);
		
		cmbTipo = new JComboBox();
		cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--"}));
		GridBagConstraints gbc_cmbTipo = new GridBagConstraints();
		gbc_cmbTipo.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipo.gridx = 3;
		gbc_cmbTipo.gridy = 0;
		panel.add(cmbTipo, gbc_cmbTipo);
		
		lblFax = new JLabel("Fax:");
		GridBagConstraints gbc_lblFax = new GridBagConstraints();
		gbc_lblFax.insets = new Insets(0, 0, 5, 5);
		gbc_lblFax.anchor = GridBagConstraints.EAST;
		gbc_lblFax.gridx = 0;
		gbc_lblFax.gridy = 1;
		panel.add(lblFax, gbc_lblFax);
		
		txtFax = new JTextField();
		txtFax.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtFax = new GridBagConstraints();
		gbc_txtFax.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFax.insets = new Insets(0, 0, 5, 5);
		gbc_txtFax.gridx = 1;
		gbc_txtFax.gridy = 1;
		panel.add(txtFax, gbc_txtFax);
		txtFax.setColumns(10);
		
		lblEmail = new JLabel("e-mail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 2;
		panel.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.gridwidth = 4;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 5);
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.gridx = 1;
		gbc_txtEmail.gridy = 2;
		panel.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);
		
		btnAgregar = new JButton("Agregar +");
		btnAgregar.addActionListener(this);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.EAST;
		gbc_btnAgregar.insets = new Insets(0, 0, 5, 0);
		gbc_btnAgregar.gridx = 5;
		gbc_btnAgregar.gridy = 2;
		panel.add(btnAgregar, gbc_btnAgregar);
		
		tablaContactos = new JTable();
		tablaContactos.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spContactos = new JScrollPane(tablaContactos);
		vTelefono = new Object [0][0];
		dtmContactos = new MiModelo2(vTelefono, columNamesTelefonos);
		tablaContactos.setModel(dtmContactos);
		tablaContactos.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaContacto = tablaContactos.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaContacto);
				columnaContacto = tablaContactos.columnAtPoint(e.getPoint());    
				if ((filaContacto > -1) && (columnaContacto > -1))
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
		GridBagConstraints gbc_tablaContactos = new GridBagConstraints();
		gbc_tablaContactos.gridwidth = 6;
		gbc_tablaContactos.fill = GridBagConstraints.BOTH;
		gbc_tablaContactos.gridx = 0;
		gbc_tablaContactos.gridy = 3;
		panel.add(spContactos, gbc_tablaContactos);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.anchor = GridBagConstraints.CENTER;
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 2;
		gbc_btnGuardar.gridy = 5;
		panel_1.add(btnGuardar, gbc_btnGuardar);
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.addActionListener(this);
		GridBagConstraints gbc_btnNuevo = new GridBagConstraints();
		gbc_btnNuevo.anchor = GridBagConstraints.EAST;
		gbc_btnNuevo.insets = new Insets(0, 0, 0, 5);
		gbc_btnNuevo.gridx = 1;
		gbc_btnNuevo.gridy = 5;
		panel_1.add(btnNuevo, gbc_btnNuevo);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar.gridx = 3;
		gbc_btnCancelar.gridy = 5;
		panel_1.add(btnCancelar, gbc_btnCancelar);
		
		llenatipoTelefono();
		interfazInicial();
		return frmAgenda;
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
	  //super();
   }
}

