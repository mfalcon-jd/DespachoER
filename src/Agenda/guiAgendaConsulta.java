package Agenda;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import bd.DbActualizarAgenda;
import bd.DbConsultarAgenda;

import GUI.estilos;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

public class guiAgendaConsulta extends JInternalFrame implements ActionListener {

	private JInternalFrame frmConsultarTelefonos;
	private JTable tableAgenda;
	private JTextField textField;	
	private JTextField txtNombre;
	private JTextField txtApellidoPaterno;
	private JTextField txtApellidoMaterno;
	private JTextField txtDescripcion;
	private JTable tableTelefono;
	private JComboBox cmbBusqueda;
	
	JTextArea txtNotas ;
	
	MiModelo2 dtmAgenda;
	MiModelo2 dtmTelefono;
	
	String[] columNamesAgenda = {"Nombre","Apellido Paterno", "Apellido Materno","Direccion","Notas","ID Contacto"};
	String[] columNamesTelefono = {"idContacto","id","telefono","tipo","email","fax"};
	
	Object [][] vConsultaAgenda;
	Object [][] vConsultaTelefono;
	
	private int filaAgenda = -1;
	private int columnaAgenda;
	
	private int filaTelefono = -1;
	private int columnaTelefono;
	
	private JScrollPane spAgenda;
	private JScrollPane spTelefonos;
	JButton btnBuscar ;
	
	
	/*public static void main(String[] args) {
		estilos obj = new estilos();		
		obj.aplicar();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiAgendaConsulta window = new guiAgendaConsulta();
					window.frmConsultarTelefonos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	public guiAgendaConsulta() 
	{
		//initialize();	
	}
	
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getActionCommand() == "Buscar")  
		{ 
			if(cmbBusqueda.getSelectedIndex() == 0)
				JOptionPane.showMessageDialog(this, "Debe seleccionar una opcion de Busqueda", "Error Busqueda", JOptionPane.ERROR_MESSAGE );
			else if(cmbBusqueda.getSelectedItem() != "Todos" && textField.getText().length() == 0)
				JOptionPane.showMessageDialog(this, "Debe Ingresar un dato para la busqueda", "Error Busqueda", JOptionPane.ERROR_MESSAGE );
			else
			{
				filaAgenda = -1;
				columnaAgenda = 0;
				consultaClientes();
			}
		}
		if (evento.getSource() == cmbBusqueda )
		{
			if(cmbBusqueda.getSelectedItem() == "Todos")
			{	
				textField.setText("");
				textField.setEnabled(false);
			
			}
			else
				textField.setEnabled(true);
		}
		
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
			sCount     += "nombre LIKE " +"'" +textField.getText()+"%'";
			sCondicion += "nombre LIKE " +"'" +textField.getText()+"%'";
		}
		if(cmbBusqueda.getSelectedItem().equals("Apellido Paterno"))
		{
			sCount     += "apellidoPaterno LIKE " +"'" +textField.getText()+"%'";
			sCondicion += "apellidoPaterno LIKE " +"'" +textField.getText()+"%'";
		}
		if(cmbBusqueda.getSelectedItem().equals("Apellido Materno"))
		{
			sCount     += "apellidoMaterno LIKE " +"'" +textField.getText()+"%'";
			sCondicion += "apellidoMaterno LIKE " +"'" +textField.getText()+"%'";
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
			txtDescripcion.setText(""); 
			/*txtEmail.setText(""); 
			txtTelefono.setText("");*/
			txtNotas.setText("");
			/*txtFax.setText("");
			cmbTipoTelefono.setSelectedIndex(0);*/
			
			vConsultaTelefono = new Object [0][0];
			dtmTelefono = new MiModelo2(vConsultaTelefono, columNamesTelefono);
			tableTelefono.setModel(dtmTelefono);
			
			//btnModificar.setEnabled(false);
			
			JOptionPane.showMessageDialog(this, "No se ha podido Encontar al cliente con los datos Proporcionados", "Error Consultar Clientes", JOptionPane.INFORMATION_MESSAGE );
		}
		else
		{
			llenaCamposAgenda();	
			//MouseEvent e = new MouseEvent(spAgenda, 0, ABORT,1, 1, 1, 1, closable);
			tableAgenda.setRowSelectionInterval(0,0);
			filaAgenda = tableAgenda.getSelectedRow();
			columnaAgenda = tableAgenda.getSelectedColumn();
			tableAgenda.requestFocus();
			
			consultaTelefonos(filaAgenda, columnaAgenda);
			llenaCamposTelefonos();
			//btnModificar.setEnabled(true);
		}
	}
	
	public void llenaCamposAgenda()
	{
		int filaAux;
		if(filaAgenda == -1) filaAux = 0;
		else filaAux = filaAgenda;
		txtNombre.setText(String.valueOf(tableAgenda.getValueAt(filaAux,0))); 
		txtApellidoPaterno.setText(String.valueOf(tableAgenda.getValueAt(filaAux,1))); 
		txtApellidoMaterno.setText(String.valueOf(tableAgenda.getValueAt(filaAux,2))); 
		txtDescripcion.setText(String.valueOf(tableAgenda.getValueAt(filaAux,3))); 
		txtNotas.setText(String.valueOf(tableAgenda.getValueAt(filaAux,4)));
		
				
	}
	
	public void consultaTelefonos(int fila, int columna)
	{
		if ((filaAgenda == -1) && (columnaAgenda == -1))
		{
			filaAgenda = 0; 
			columnaAgenda = 0;
		}
		else
		{
			filaAgenda = fila; 
			columnaAgenda = columna;
		}
		    
			String sLlave = String.valueOf(tableAgenda.getValueAt(filaAgenda,5));
			System.out.println("curp " + sLlave);
			String sCount;
			String sConsulta; 
			
				System.out.println("otra condicion");
				sCount = "SELECT count( 1)AS cont FROM telefonos WHERE idContacto ='" +sLlave+"'";
							
				sConsulta = "SELECT t1.idContacto, t1.telefono,t1.email,t1.id,t1.fax, t2.descripcion" +
							 " FROM telefonos AS t1, tipotelefonos AS t2 " +
							 " WHERE t1.tipo = t2.idTipo AND idContacto ='"+sLlave+"'";
			

			DbConsultarAgenda objConsultar = new DbConsultarAgenda();
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
			
												   
			
	}
	
	public void llenaCamposTelefonos()
	{
		/*if(filaTelefono == -1)filaTelefono = 0;
		
		txtEmail.setText(String.valueOf(tableTelefono.getValueAt(filaTelefono,4)));
		txtTelefono.setText(String.valueOf(tableTelefono.getValueAt(filaTelefono,2)));		
		cmbTipoTelefono.setSelectedItem(String.valueOf(tableTelefono.getValueAt(filaTelefono,3)));
		txtFax.setText(String.valueOf(tableTelefono.getValueAt(filaTelefono,5)));*/
	}
	

	
	public void interfazInicial()
	{
		txtNombre.setEditable(false); 
		txtApellidoPaterno.setEditable(false); 
		txtApellidoMaterno.setEditable(false); 
		txtDescripcion.setEditable(false);
		txtNotas.setEditable(false); 
		cmbBusqueda.setEnabled(true);
		
		textField.setEditable(true);
		tableTelefono.setEnabled(true);
		tableAgenda.setEnabled(true);
		
		
	}
			
	public JInternalFrame initialize() {
		frmConsultarTelefonos = new JInternalFrame("Consultar Agenda",false, true, true, true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");
		frmConsultarTelefonos.setFrameIcon(new ImageIcon(URLIcon));
		frmConsultarTelefonos.setBounds(100, 100, 793, 502);
		frmConsultarTelefonos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmConsultarTelefonos.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmConsultarTelefonos.getContentPane().add(panel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{99, 56, 53, 73, 0};
		gbl_panel.rowHeights = new int[]{0, 23, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--", "Todos","Nombre", "Apellido Paterno", "Apellido Materno"}));
		GridBagConstraints gbc_cmbBusqueda = new GridBagConstraints();
		gbc_cmbBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBusqueda.fill = GridBagConstraints.EAST;
		gbc_cmbBusqueda.gridx = 1;
		gbc_cmbBusqueda.gridy = 0;
		cmbBusqueda.addActionListener(this);
		panel.add(cmbBusqueda, gbc_cmbBusqueda);
		
		textField = new JTextField();
		textField.addKeyListener( 
				  new KeyAdapter() { 
				     public void keyPressed(KeyEvent e) { 
				       if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				       { 
				    	   btnBuscar.doClick(); 
				       } 
				     } 
				  }); 
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 3;
		gbc_btnBuscar.gridy = 0;
		panel.add(btnBuscar, gbc_btnBuscar);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 4;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		tableAgenda = new JTable();
		tableAgenda.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spAgenda = new JScrollPane(tableAgenda);
		vConsultaAgenda = new Object [0][0];
		dtmAgenda = new MiModelo2(vConsultaAgenda, columNamesAgenda);
		tableAgenda.setModel(dtmAgenda);
		tableAgenda.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaAgenda = tableAgenda.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaAgenda);
				columnaAgenda = tableAgenda.columnAtPoint(e.getPoint());    
				if ((filaAgenda > -1) && (columnaAgenda > -1))
				{
					filaTelefono = -1;
					columnaTelefono = -1;
					llenaCamposAgenda();
					consultaTelefonos(filaAgenda,columnaAgenda);
					llenaCamposTelefonos();					   
				}
			}
		});
		
		tableAgenda.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTelefono = -1;
					columnaTelefono = -1;
					
					filaAgenda = tableAgenda.getSelectedRow();
					columnaAgenda = tableAgenda.getSelectedColumn();
					
					llenaCamposAgenda();
					consultaTelefonos(filaAgenda,columnaAgenda);
					llenaCamposTelefonos();				
				}
			}
		});
		
		panel_2.add(spAgenda);
		
		JPanel panel_1 = new JPanel();		
		frmConsultarTelefonos.getContentPane().add(panel_1, BorderLayout.CENTER);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{103, 0, 0};
		gbl_panel_1.rowHeights = new int[]{93, 24, 0, 0, 0, 72, 88, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Datos");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_1.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		JLabel lblNombre = new JLabel("Nombre:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.anchor = GridBagConstraints.WEST;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 1;
		panel_1.add(lblNombre, gbc_lblNombre);
		
		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panel_1.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblApellidoPaterno = new GridBagConstraints();
		gbc_lblApellidoPaterno.anchor = GridBagConstraints.WEST;
		gbc_lblApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoPaterno.gridx = 0;
		gbc_lblApellidoPaterno.gridy = 2;
		panel_1.add(lblApellidoPaterno, gbc_lblApellidoPaterno);
		
		txtApellidoPaterno = new JTextField();
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 2;
		panel_1.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(10);
		
		JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
		GridBagConstraints gbc_lblApellidoMaterno = new GridBagConstraints();
		gbc_lblApellidoMaterno.anchor = GridBagConstraints.WEST;
		gbc_lblApellidoMaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoMaterno.gridx = 0;
		gbc_lblApellidoMaterno.gridy = 3;
		panel_1.add(lblApellidoMaterno, gbc_lblApellidoMaterno);
		
		txtApellidoMaterno = new JTextField();
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.gridx = 1;
		gbc_txtApellidoMaterno.gridy = 3;
		panel_1.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(10);
		
		JLabel lblDireccion = new JLabel("Direccion:");
		GridBagConstraints gbc_lblDireccion = new GridBagConstraints();
		gbc_lblDireccion.anchor = GridBagConstraints.WEST;
		gbc_lblDireccion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDireccion.gridx = 0;
		gbc_lblDireccion.gridy = 4;
		panel_1.add(lblDireccion, gbc_lblDireccion);
		
		txtDescripcion = new JTextField();
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		gbc_txtDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.gridx = 1;
		gbc_txtDescripcion.gridy = 4;
		panel_1.add(txtDescripcion, gbc_txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Notas:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 5;
		panel_1.add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 5;
		panel_1.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_3.add(scrollPane);
		
		txtNotas = new JTextArea();
		scrollPane.setViewportView(txtNotas);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.gridwidth = 2;
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 6;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		tableTelefono = new JTable();
		tableTelefono.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spTelefonos = new JScrollPane(tableTelefono);
		vConsultaTelefono = new Object [0][0];
		dtmTelefono = new MiModelo2(vConsultaTelefono, columNamesTelefono);
		tableTelefono.setModel(dtmTelefono);
		tableTelefono.addMouseListener(new MouseAdapter()
		{     
			public void mouseClicked(MouseEvent e)
			{ 
				filaTelefono = tableTelefono.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaTelefono);
				columnaTelefono = tableTelefono.columnAtPoint(e.getPoint());    
				llenaCamposTelefonos();
			}
		});
		
		tableTelefono.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTelefono = tableTelefono.getSelectedRow();
					columnaTelefono = tableTelefono.getSelectedColumn();
					llenaCamposTelefonos();
								
				}
			}
		});


		scrollPane_1.setViewportView(spTelefonos);
		
		interfazInicial();
		
		return frmConsultarTelefonos;
	}

}
