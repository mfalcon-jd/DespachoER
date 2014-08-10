package GUI.cliente;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;

import GUI.estilos;

import bd.DbConsultarTipoTelefono;

import com.toedter.calendar.JDateChooser;

import entidades.clientes;

//import org.jvnet.substance.SubstanceLookAndFeel;

public class guiAgregarClientes extends JInternalFrame implements ActionListener {

	private JInternalFrame frmCapturarClientes;
	private JTextField txtCurp;
	private JTextField txtNombre;
	private JTextField txtApellidoPaterno;
	private JTextField txtApellidoMaterno;
	private JTextField txtDireccion;
	private JTextField txtTelefono;
	private JTextField txtCelular;
	private JTextField txtEmail;
	private JTextArea textObservaciones;
	private JDateChooser cal;
	private JButton btnCancelar;
	private JButton btnActivar;
	private JButton btnGuardar;
	private JButton btnDeshacer;
	private JComboBox cmbTipo;
	private JButton btnAgregarTel;
	private JTable tablaTelefonos;
	private JScrollPane spTelefonos;
	
	private String  sNombre, sApellidoPaterno , sApellidoMaterno, sCurp,
	   sDireccion, sAnioNacimiento, sEmail, sCelular, sTelefono, sObservaciones;
	
	private String sCamposFaltantes;
	private Date fechaNacimiento;
	private SimpleDateFormat formato;
	private Date convertirfecha;
	
	String[] descripcionTipotelefono;
	String[] tipotelefono;
	String[] columNamesTelefono = {"Telefono","Tipo","Descripcion"};
	
	private DefaultComboBoxModel jtmTipoTelefono;
	MiModelo dtmTelefono;
	Object [][] vConsultaTelefono;
	//private JDateChooser dateChooserFechaNac;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		
		estilos obj = new estilos();		
		obj.aplicar();		
				
		//objUsuario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiAgregarClientes window = new guiAgregarClientes();
					window.frmCapturarClientes.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		LookAndFeelInfo[] lista = UIManager.getInstalledLookAndFeels();		
		for (int i = 0; i < lista.length; i++) {
		    System.out.println(lista[i].getClassName());		
		}
						
	}*/
	
	public guiAgregarClientes() {
		initialize();
	}
		
public void actionPerformed(ActionEvent evento){
  	    if(evento.getSource() == btnDeshacer)
  	    {
  	    	clientes objClientes = new clientes();
  	    	objClientes.setRollback();
  	    	
  	    }
	
    	if (evento.getActionCommand().equals("Guardar"))
    	{
    		guardarCliente();            
    	}
    	if (evento.getActionCommand().equals("Agregar +"))
    	{    		  	    	
    		limpiaCampos();
    		interfazInicial();    		
    	}
    	if (evento.getActionCommand().equals("Cancelar"))
    	{
    		limpiaCampos();
    		interfazGuardados();
    	}
    	if(evento.getSource()== btnAgregarTel)
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
	            
				datosFila[0] = txtTelefono.getText().trim();
				datosFila[1] = tipotelefono[cmbTipo.getSelectedIndex()-1];
				datosFila[2] = descripcionTipotelefono[cmbTipo.getSelectedIndex()-1];
				
	            modelo.addRow(datosFila);
	            
	            txtTelefono.setText("");
	            cmbTipo.setSelectedIndex(0);
	            txtTelefono.requestFocus();
			}
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

	
	public void guardarCliente()
	{
		boolean bBanderaCorrecto;
		boolean lCorrecto;
		
		String vDatosClientes[] = new String[9];
        
        obtieneValoresFormularios();
        lCorrecto = validarCampos();
        
        if (lCorrecto == true)
        {
        	System.out.println("Campos Faltantes " + sCamposFaltantes);
        	JOptionPane.showMessageDialog(this, sCamposFaltantes, "ERROR", JOptionPane.ERROR_MESSAGE );
        }
        else
        {
        	if(sCurp.length() <18 || sCurp.length() > 18)
        		JOptionPane.showMessageDialog(this, "El Curp es invalido favor de Verificar", "ERROR", JOptionPane.ERROR_MESSAGE );
        	/*else if(txtCelular.getText().trim().length() > 10)
        		JOptionPane.showMessageDialog(this, "El número celular no es correcto Debe ser igual o menor que 10 Digitos!!!", "ERROR", JOptionPane.ERROR_MESSAGE );
        	else if(txtTelefono.getText().trim().length() > 10)
        		JOptionPane.showMessageDialog(this, "El número Telefonico no es correcto Debe ser igual o menor que 10 Digitos!!!", "ERROR", JOptionPane.ERROR_MESSAGE );*/
        	else
        	{
        		/*vDatosClientes[0] = sNombre;
        		vDatosClientes[1] = sApellidoPaterno;
        		vDatosClientes[2] = sApellidoMaterno;
        		vDatosClientes[3] = sCurp;
        		vDatosClientes[4] = sDireccion;
        		vDatosClientes[5] = sAnioNacimiento;
        		vDatosClientes[6] = sEmail;
        		vDatosClientes[7] = sCelular;
        		vDatosClientes[8] = sTelefono;*/
        		
        		/*DbInsertar objInsertar = new DbInsertar();
        		bBanderaCorrecto = objInsertar.insertarCliente(vDatosClientes);*/
        		DefaultTableModel modelo = (DefaultTableModel) tablaTelefonos.getModel();
        		clientes objCliente = new clientes(sNombre,sApellidoPaterno,sApellidoMaterno,sCurp,
        				                 sDireccion,sAnioNacimiento,sEmail,/*sCelular,sTelefono,*/sObservaciones,modelo);
        		
        		bBanderaCorrecto = objCliente.crearCliente();
        		
        		if (bBanderaCorrecto == true) 
        		{
        			interfazGuardados();
        			clientes objClientes = new clientes();
        			objClientes.setCommit();
        			JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
        		}
        		else
        		{
        			JOptionPane.showMessageDialog(this, objCliente.excepcion.getMessage(), "Error Insertar Cliente", JOptionPane.ERROR_MESSAGE );
        		}
        	}
		
        }
		
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
		 sNombre          = txtNombre.getText().trim().toUpperCase();
		 sApellidoPaterno = txtApellidoPaterno.getText().trim().toUpperCase();
		 sApellidoMaterno = txtApellidoMaterno.getText().trim().toUpperCase();
		 sCurp            = txtCurp.getText().trim().toUpperCase();
		 sDireccion       = txtDireccion.getText().trim().toUpperCase();
		 //sAnioNacimiento  = cal.getDateFormatString();
		 sEmail           = txtEmail.getText().trim().toLowerCase();
		 /*sCelular         = txtCelular.getText().trim().toUpperCase();
		 sTelefono        = txtTelefono.getText().trim().toUpperCase();*/
		 //fechaNacimiento = cal.getDate();
		 SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = cal.getDate();
		 sAnioNacimiento= formato.format(date);
		 
		 sObservaciones = textObservaciones.getText().trim().toUpperCase();
				 
	}
	
	public void interfazGuardados()
	{
		txtNombre.setEditable(false);
		txtApellidoPaterno.setEditable(false);
		txtApellidoMaterno.setEditable(false);
		txtCurp.setEditable(false);
		txtDireccion.setEditable(false);
		cal.getSpinner().setEnabled(false);
		txtEmail.setEditable(false);
		/*txtCelular.setEditable(false);
		txtTelefono.setEditable(false);*/
		textObservaciones.setEditable(false);
		btnActivar.setEnabled(true);
		btnGuardar.setEnabled(false);
		btnCancelar.setEnabled(false);
		txtTelefono.setEnabled(false);
		cmbTipo.setEnabled(false);
	    btnAgregarTel.setEnabled(false);
	    tablaTelefonos.setEnabled(false);
		
	}
	
	public void interfazInicial ()
	{
		txtNombre.setEditable(true);
		txtApellidoPaterno.setEditable(true);
		txtApellidoMaterno.setEditable(true);
		txtCurp.setEditable(true);
		txtDireccion.setEditable(true);
		cal.getSpinner().setEnabled(true);
		txtEmail.setEditable(true);		
		textObservaciones.setEditable(true);
		btnActivar.setEnabled(false);
		btnGuardar.setEnabled(true);
		btnCancelar.setEnabled(true);
		txtTelefono.setEnabled(true);
		cmbTipo.setEnabled(true);
	    btnAgregarTel.setEnabled(true);
	    tablaTelefonos.setEnabled(true);		
	}
	
	public void limpiaCampos()
	{
		txtNombre.setText("");
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtCurp.setText("");
		txtDireccion.setText("");
		cal.getSpinner().setDefaultLocale(getLocale());
		txtEmail.setText("");		
		textObservaciones.setText("");
		
		interfazInicial();
	}
	
	public JInternalFrame initialize() {
		frmCapturarClientes = new JInternalFrame("Catálogo Clientes",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");
		frmCapturarClientes.setFrameIcon(new ImageIcon(URLIcon));
		frmCapturarClientes.setBounds(100, 100, 600, 500);
		frmCapturarClientes.getContentPane().setLayout(new BorderLayout(0, 0));
				
		
		JPanel panel = new JPanel();
		frmCapturarClientes.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		frmCapturarClientes.getContentPane().add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		frmCapturarClientes.getContentPane().add(panel_2, BorderLayout.EAST);
		
		/*JPanel panel_3 = new JPanel();
		frmCapturarClientes.getContentPane().add(panel_3, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 197, 0, 139, 0};
		gbl_panel_3.rowHeights = new int[]{23, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);*/
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos Clientes", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		frmCapturarClientes.getContentPane().add(panel_4, BorderLayout.CENTER);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{105, 136, 46, 77, 130, 0};
		gbl_panel_4.rowHeights = new int[]{33, 33, 33, 33, 33, 50, 90, 2, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblCurp = new JLabel("CURP:");
		lblCurp.setForeground(Color.BLACK);
		GridBagConstraints gbc_lblCurp = new GridBagConstraints();
		gbc_lblCurp.anchor = GridBagConstraints.EAST;
		gbc_lblCurp.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurp.gridx = 0;
		gbc_lblCurp.gridy = 0;
		panel_4.add(lblCurp, gbc_lblCurp);
		
		txtCurp = new JTextField();
		txtCurp.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtCurp = new GridBagConstraints();
		gbc_txtCurp.gridwidth = 2;
		gbc_txtCurp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurp.insets = new Insets(0, 0, 5, 5);
		gbc_txtCurp.gridx = 1;
		gbc_txtCurp.gridy = 0;
		panel_4.add(txtCurp, gbc_txtCurp);
		txtCurp.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 1;
		panel_4.add(lblNombre, gbc_lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 4;
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panel_4.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblApellidoPaterno = new GridBagConstraints();
		gbc_lblApellidoPaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoPaterno.gridx = 0;
		gbc_lblApellidoPaterno.gridy = 2;
		panel_4.add(lblApellidoPaterno, gbc_lblApellidoPaterno);
		
		txtApellidoPaterno = new JTextField();
		txtApellidoPaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.gridwidth = 2;
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 2;
		panel_4.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(10);
		
		JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
		GridBagConstraints gbc_lblApellidoMaterno = new GridBagConstraints();
		gbc_lblApellidoMaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoMaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoMaterno.gridx = 3;
		gbc_lblApellidoMaterno.gridy = 2;
		panel_4.add(lblApellidoMaterno, gbc_lblApellidoMaterno);
		
		txtApellidoMaterno = new JTextField();
		txtApellidoMaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.gridx = 4;
		gbc_txtApellidoMaterno.gridy = 2;
		panel_4.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(10);
		
		JLabel lblDireccion = new JLabel("Direccion");
		GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
		gbc_lblDireccion.anchor = GridBagConstraints.EAST;
		gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccion.gridx = 0;
		gbc_lblDireccion.gridy = 3;
		panel_4.add(lblDireccion, gbc_lblDireccion);
		
		txtDireccion = new JTextField();
		txtDireccion.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtDireccion = new GridBagConstraints();
		gbc_txtDireccion.gridwidth = 4;
		gbc_txtDireccion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDireccion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDireccion.gridx = 1;
		gbc_txtDireccion.gridy = 3;
		panel_4.add(txtDireccion, gbc_txtDireccion);
		txtDireccion.setColumns(10);
		
		JLabel lblAoNacimiento = new JLabel("A\u00F1o Nacimiento:");
		GridBagConstraints gbc_lblAoNacimiento = new GridBagConstraints();
		gbc_lblAoNacimiento.anchor = GridBagConstraints.EAST;
		gbc_lblAoNacimiento.insets = new Insets(0, 0, 5, 5);
		gbc_lblAoNacimiento.gridx = 0;
		gbc_lblAoNacimiento.gridy = 4;
		panel_4.add(lblAoNacimiento, gbc_lblAoNacimiento);
		
		cal=new JDateChooser("dd/MM/yyyy",false);
		GridBagConstraints gbc_cal = new GridBagConstraints();
		gbc_cal.insets = new Insets(0, 0, 5, 5);
		gbc_cal.anchor = GridBagConstraints.WEST;
		gbc_cal.gridx = 1;
		gbc_cal.gridy = 4;		
		panel_4.add(cal, gbc_cal);
		
		JLabel lblEmail = new JLabel("e-mail:");
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 3;
		gbc_lblEmail.gridy = 4;
		panel_4.add(lblEmail, gbc_lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtEmail = new GridBagConstraints();
		gbc_txtEmail.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEmail.insets = new Insets(0, 0, 5, 0);
		gbc_txtEmail.gridx = 4;
		gbc_txtEmail.gridy = 4;
		panel_4.add(txtEmail, gbc_txtEmail);
		txtEmail.setColumns(10);				
		
		JLabel lblObservaciones = new JLabel("Observaciones:");
		GridBagConstraints gbc_lblObservaciones = new GridBagConstraints();
		gbc_lblObservaciones.anchor = GridBagConstraints.EAST;
		gbc_lblObservaciones.insets = new Insets(0, 0, 5, 5);
		gbc_lblObservaciones.gridx = 0;
		gbc_lblObservaciones.gridy = 5;
		panel_4.add(lblObservaciones, gbc_lblObservaciones);
		
		textObservaciones = new JTextArea();		
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 4;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 5;
		panel_4.add(textObservaciones, gbc_textField);
		textObservaciones.setColumns(10);
				
		JPanel panelTelefono = new JPanel();		
		panelTelefono.setBorder(new TitledBorder(null, "Telefonos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbl_panelPrin = new GridBagConstraints();
		gbl_panelPrin.insets = new Insets(0, 0, 5, 0);
		gbl_panelPrin.gridwidth = 5;
		gbl_panelPrin.fill = GridBagConstraints.BOTH;
		gbl_panelPrin.gridx = 0;
		gbl_panelPrin.gridy = 6;
		panel_4.add(panelTelefono, gbl_panelPrin);
		
		GridBagLayout gbl_panelTelefono = new GridBagLayout();
		gbl_panelTelefono.columnWidths = new int[]{126, 96, 36, 56, 46, 0};
		gbl_panelTelefono.rowHeights = new int[]{33, 96, 0};
		gbl_panelTelefono.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelTelefono.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelTelefono.setLayout(gbl_panelTelefono);
		
		JLabel lblTelefono = new JLabel("Telefono: ");
		GridBagConstraints gbc_lblTelefono = new GridBagConstraints();		
		gbc_lblTelefono.anchor = GridBagConstraints.EAST;
		gbc_lblTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelefono.gridx = 0;
		gbc_lblTelefono.gridy = 0;
		panelTelefono.add(lblTelefono, gbc_lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();		
		gbc_txtTelefono.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 0;
		panelTelefono.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setColumns(10);
		
		JLabel lblTipo = new JLabel("Tipo");
		GridBagConstraints gbc_tipo = new GridBagConstraints();
		gbc_tipo.anchor = GridBagConstraints.EAST;
		gbc_tipo.insets = new Insets(0, 0, 5, 5);
		gbc_tipo.gridx = 2;
		gbc_tipo.gridy = 0;
		panelTelefono.add(lblTipo, gbc_tipo);
		
		cmbTipo = new JComboBox();
		cmbTipo.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--"}));
		GridBagConstraints gbc_cmbTipo = new GridBagConstraints();
		gbc_cmbTipo.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipo.gridx = 3;
		gbc_cmbTipo.gridy = 0;
		panelTelefono.add(cmbTipo, gbc_cmbTipo);
		
		btnAgregarTel = new JButton();
		estilos es = new estilos();
		URL URLAgregar = es.cargador("img/agregar.png");
		ImageIcon imgAceptar = new ImageIcon(URLAgregar);
		btnAgregarTel.setIcon(imgAceptar);
		btnAgregarTel.addActionListener(this);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.WEST;
		gbc_btnAgregar.insets = new Insets(0, 9, 5, 0);
		gbc_btnAgregar.gridx = 4;
		gbc_btnAgregar.gridy = 0;
		panelTelefono.add(btnAgregarTel, gbc_btnAgregar);
		
		tablaTelefonos = new JTable();
		tablaTelefonos.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spTelefonos = new JScrollPane(tablaTelefonos);
		dtmTelefono = new MiModelo(vConsultaTelefono, columNamesTelefono);
		tablaTelefonos.setModel(dtmTelefono);
		
		GridBagConstraints gbc_tablaTelefonos = new GridBagConstraints();
		gbc_tablaTelefonos.gridwidth = 7;
		gbc_tablaTelefonos.fill = GridBagConstraints.BOTH;
		gbc_tablaTelefonos.gridx = 0;
		gbc_tablaTelefonos.gridy = 1;
		panelTelefono.add(spTelefonos, gbc_tablaTelefonos);
		
		btnDeshacer = new JButton();
		URL URLDeshacer = es.cargador("img/deshacer.png");
		ImageIcon imgDeshacer = new ImageIcon(URLDeshacer);
		btnDeshacer.setIcon(imgDeshacer);
		btnDeshacer.addActionListener(this);
		GridBagConstraints gbc_btnDeshacer = new GridBagConstraints();
		gbc_btnDeshacer.insets = new Insets(0, 0, 0, 5);
		gbc_btnDeshacer.gridx = 0;
		gbc_btnDeshacer.gridy = 8;
		panel_4.add(btnDeshacer, gbc_btnDeshacer);
		
		btnActivar = new JButton("Agregar +");
		btnActivar.addActionListener(this);
		GridBagConstraints gbc_btnActivar  = new GridBagConstraints();
		gbc_btnActivar.insets = new Insets(0,0,0,5);
		gbc_btnActivar.gridx = 2;
		gbc_btnActivar.gridy = 8;
		panel_4.add(btnActivar, gbc_btnActivar);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar.gridx = 3;
		gbc_btnGuardar.gridy = 8;
		panel_4.add(btnGuardar, gbc_btnGuardar);
				
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 8;
		panel_4.add(btnCancelar, gbc_btnCancelar);
		
		llenatipoTelefono();
		return frmCapturarClientes;
		
	}	

}
