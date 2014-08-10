package GUI.casos;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import GUI.estilos;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import bd.DBInsertarCaso;
import bd.DBInsertarEtapa;

import entidades.clientes;
import javax.swing.ImageIcon;

public class guiCasos extends JInternalFrame implements ActionListener {

	private JInternalFrame frmAgregarCasos;
	private JTextField txtNombreCaso;
	//private JTextField txtPrecio;
	private JFormattedTextField txtPrecio;
	private JTextField txtNombreEtapa;
	private JTable tableEtapas;
	private JTextField txtDescripcionEtapa;
	JTextArea txtDescripcion;
	JButton btnGuardaEtapas;
	JButton btnAgregar;
	JButton btnCancelar;
	JButton btnGuardar;
	JButton btnAgregarCaso;
	String sCamposFaltantes;
	String sNombreCaso,sDescripcion;
	Double dPrecio;
	JButton btnCancelarEtapa;
	
	
	MiModelo2 dtmEtapas;
	
	String[] columNamesEtapas = {"Nombre Etapa","Descripcion"};
	Vector vector=new Vector(20);
	Object [][] vEtapas;
	
	int i = 0;
	int j = 1;
	int k = 2;
	int l = 3;
	int idCaso = 0;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		estilos obj = new estilos();
		obj.aplicar();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiCasos window = new guiCasos();
					window.frmAgregarCasos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public guiCasos() {
		initialize();
	}
	

	public void crearCarpeta(String carpeta)
	{
		 File directorio = new File("C:/Despacho2/Casos/"+carpeta); 
				directorio.mkdirs();
		 //directorio.createNewFile();
	}
	
	public void actionPerformed(ActionEvent evento){
		
		if( evento.getActionCommand() == "Agregar Caso")
  	    {
			vector=new Vector(20);
			interfazInicial();
			limpiarCampos();
			
  	    }
		if( evento.getActionCommand() == "Cancelar")
  	    {
			txtNombreCaso.setEditable(false);
			txtPrecio.setEditable(false);
			txtDescripcion.setEditable(false);
			
			btnCancelar.setEnabled(false);
			btnGuardar.setEnabled(false);
			btnAgregarCaso.setEnabled(true);
			
			limpiarCampos();
  	    }
  	    if( evento.getSource() == btnGuardaEtapas)
  	    {
  	    	System.out.println("etapas");
  	    	if(vEtapas.length == 0)
  	    		JOptionPane.showMessageDialog(this, "Debe Insertar al menos una etapa", "Error Insertar Etapas", JOptionPane.ERROR_MESSAGE );
  	    	else
  	    	guardarEtapas();
  	    	
  	    }
    	if (evento.getSource() == btnGuardar)
    	{
    		guardarCaso(); 
    		txtNombreEtapa.requestFocus();
    	}
    	if (evento.getSource() == btnAgregar)
    	{
    		agregarEtapas();
    		txtNombreEtapa.setText("");
			txtDescripcionEtapa.setText("");
			txtNombreEtapa.requestFocus();
    	}
    	
    	
	}
	
	public void guardarEtapas()
	{
		boolean bBanderaCorrecto;
		boolean lCorrecto;
		
		DBInsertarEtapa objInsertar = new DBInsertarEtapa();
        bBanderaCorrecto = objInsertar.insertarEtapa(vEtapas,idCaso);
        		        		
        		if (bBanderaCorrecto == true) 
        		{
        			//interfazGuardados();
        			interfazEtapasGuardadas();
        			JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
        		}
        		else
        		{
        			JOptionPane.showMessageDialog(this, objInsertar.excepcion.getMessage(), "Error Insertar Caso", JOptionPane.ERROR_MESSAGE );
        		}
        	
		
   	}
	
	public void agregarEtapas()
	{
		if(txtNombreEtapa.getText().length() == 0)
			JOptionPane.showMessageDialog(this, "Debe Proporcionar al menos el nombre de Etapa!!", "Error Etapas", JOptionPane.ERROR_MESSAGE );
		else
		{
			vector.add(txtNombreEtapa.getText().trim().toLowerCase());
			vector.add(txtDescripcionEtapa.getText().trim().toLowerCase());
			
			
			vEtapas = new Object[vector.size()/2][2];
			int total = vector.size()/2;
			int to= 0;
			
			while(to < total)
			{
				vEtapas[to][0]= vector.elementAt(i);
				vEtapas[to][1]= vector.elementAt(j);

								
				to++;
				
				i+=2;
				j+=2;
			}
			i = 0;
			j = 1;
				
			dtmEtapas = new MiModelo2(vEtapas, columNamesEtapas);
		    tableEtapas.setModel(dtmEtapas);
		    //interfazAgregar();
		}
		
	}
	
	public void guardarCaso()
	{
		boolean bBanderaCorrecto;
		boolean lCorrecto;
		
		Object vDatosClientes[] = new Object[3];
        
        obtieneValoresFormularios();
        lCorrecto = validarCampos();
        
        if (lCorrecto == true)
        {
        	System.out.println("Campos Faltantes " + sCamposFaltantes);
        	JOptionPane.showMessageDialog(this, sCamposFaltantes, "ERROR", JOptionPane.ERROR_MESSAGE );
        }
        else
        {
        	/*if(sCurp.length() <18 || sCurp.length() > 18)
        		JOptionPane.showMessageDialog(this, "El Curp es invalido favor de Verificar", "ERROR", JOptionPane.ERROR_MESSAGE );
        	else if(txtCelular.getText().trim().length() > 10)
        		JOptionPane.showMessageDialog(this, "El número celular no es correcto Debe ser 10 Digitos!!!", "ERROR", JOptionPane.ERROR_MESSAGE );
        	else if(txtTelefono.getText().trim().length() > 7)
        		JOptionPane.showMessageDialog(this, "El número Telefonico no es correcto Debe ser 7 Digitos!!!", "ERROR", JOptionPane.ERROR_MESSAGE );
        	else
        	{*/
        		vDatosClientes[0] = sNombreCaso;
        		vDatosClientes[1] = dPrecio;
        		vDatosClientes[2] = sDescripcion;
        		        		
        		DBInsertarCaso objInsertar = new DBInsertarCaso();
        		bBanderaCorrecto = objInsertar.insertarCaso(vDatosClientes);
        		        		
        		if (bBanderaCorrecto == true) 
        		{
        			//interfazGuardados();
        			idCaso = objInsertar.getIdCaso();
        			interfazAgregarEtapas();
        			crearCarpeta(sNombreCaso);
        			JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
        		}
        		else
        		{
        			JOptionPane.showMessageDialog(this, objInsertar.excepcion.getMessage(), "Error Insertar Cliente", JOptionPane.ERROR_MESSAGE );
        		}
        	}
		
       // }
		
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
		if (sNombreCaso.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo Nombre de caso es obligatorio\n";
		}
		if (sDescripcion.length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo descripcion es obligatorio\n";
		}
		//if (txtPrecio.getText().trim().length() == 0)
		if(estilos.getObtieneValorText(txtPrecio).length() == 0)
		{
			lCampoVacio = true;
			sCamposFaltantes += "El Campo precio es obligatorio\n";
		}		
				
		return lCampoVacio;
	}

	public void obtieneValoresFormularios()
	{
		 sNombreCaso          = txtNombreCaso.getText().trim().toUpperCase();
		 sDescripcion = txtDescripcion.getText().trim().toUpperCase();
		 //if(txtPrecio.getText().trim().length() != 0) dPrecio =  Double.parseDouble(txtPrecio.getText().trim());
		 if(estilos.getObtieneValorText(txtPrecio).length() != 0) dPrecio =  Double.parseDouble(estilos.getObtieneValorText(txtPrecio));
		
		 
		
				 
	}
	
	public void limpiarCampos()
	{
		txtNombreCaso.setText("");
		
		//txtPrecio.setText("");
		txtPrecio.setValue(new Double("0.00"));
		txtNombreEtapa.setText("");
		txtDescripcionEtapa.setText("");
		txtDescripcion.setText("");
		
		vEtapas = new Object[0][0];	
		dtmEtapas = new MiModelo2(vEtapas, columNamesEtapas);
	    tableEtapas.setModel(dtmEtapas);
	}
	
	public void interfazAgregarEtapas()
	{
		btnCancelarEtapa.setEnabled(true);
		txtNombreCaso.setEditable(false);
		txtPrecio.setEditable(false);
		txtNombreEtapa.setEditable(true);
		tableEtapas.setEnabled(true);
		txtDescripcionEtapa.setEditable(true);
		txtDescripcion.setEditable(false);
		btnGuardaEtapas.setEnabled(true);
		btnAgregar.setEnabled(true);
		btnCancelar.setEnabled(false);
		btnGuardar.setEnabled(false);
	}
	
	public void interfazEtapasGuardadas()
	{
		btnCancelarEtapa.setEnabled(false);
		txtNombreEtapa.setEditable(false);
		tableEtapas.setEnabled(false);
		txtDescripcionEtapa.setEditable(false);
		btnGuardaEtapas.setEnabled(false);
		btnAgregar.setEnabled(false);
		btnAgregarCaso.setEnabled(true);
		
	}
	
	public void interfazInicial()
	{
	
		btnCancelarEtapa.setEnabled(false);
		txtNombreCaso.setEditable(true);
		txtPrecio.setEditable(true);
		txtNombreEtapa.setEditable(false);
		tableEtapas.setEnabled(false);
		txtDescripcionEtapa.setEditable(false);
		txtDescripcion.setEditable(true);
		btnGuardaEtapas.setEnabled(false);
		btnAgregar.setEnabled(false);
		btnCancelar.setEnabled(true);
		btnGuardar.setEnabled(true);
		btnAgregarCaso.setEnabled(false);
	}

	

	
	public JInternalFrame initialize() 
	{
		frmAgregarCasos = new JInternalFrame("Catálogo Casos",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmAgregarCasos.setFrameIcon(new ImageIcon(URLIcon));
		frmAgregarCasos.setBounds(100, 100, 600, 500);
		frmAgregarCasos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 110, 72, 0, 81, 0};
		gridBagLayout.rowHeights = new int[]{44, 0, 35, 35, 86, 35, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmAgregarCasos.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblCasos = new JLabel("Casos Nuevos");
		lblCasos.setFont(new Font("Tahoma", Font.PLAIN, 18));
		GridBagConstraints gbc_lblCasos = new GridBagConstraints();
		gbc_lblCasos.gridwidth = 5;
		gbc_lblCasos.insets = new Insets(0, 0, 5, 0);
		gbc_lblCasos.gridx = 0;
		gbc_lblCasos.gridy = 0;
		frmAgregarCasos.getContentPane().add(lblCasos, gbc_lblCasos);
		
		JLabel lblNombre = new JLabel("Nombre del Caso:");
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		frmAgregarCasos.getContentPane().add(lblNombre, gbc_lblNombre);
		
		txtNombreCaso = new JTextField();
		txtNombreCaso.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombreCaso = new GridBagConstraints();
		gbc_txtNombreCaso.gridwidth = 3;
		gbc_txtNombreCaso.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombreCaso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreCaso.gridx = 2;
		gbc_txtNombreCaso.gridy = 2;
		frmAgregarCasos.getContentPane().add(txtNombreCaso, gbc_txtNombreCaso);
		txtNombreCaso.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		GridBagConstraints gbc_lblPrecio = new GridBagConstraints();
		gbc_lblPrecio.anchor = GridBagConstraints.EAST;
		gbc_lblPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecio.gridx = 1;
		gbc_lblPrecio.gridy = 3;
		frmAgregarCasos.getContentPane().add(lblPrecio, gbc_lblPrecio);
		
		txtPrecio = estilos.formatoText(txtPrecio);
		txtPrecio.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtPrecio = new GridBagConstraints();
		gbc_txtPrecio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrecio.gridx = 2;
		gbc_txtPrecio.gridy = 3;
		frmAgregarCasos.getContentPane().add(txtPrecio, gbc_txtPrecio);
		txtPrecio.setColumns(10);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.fill = GridBagConstraints.VERTICAL;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 1;
		gbc_lblDescripcion.gridy = 4;
		frmAgregarCasos.getContentPane().add(lblDescripcion, gbc_lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 4;
		frmAgregarCasos.getContentPane().add(scrollPane, gbc_scrollPane);
		
		txtDescripcion = new JTextArea();
		scrollPane.setViewportView(txtDescripcion);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		GridBagConstraints gbc_btnGuardar = new GridBagConstraints();
		gbc_btnGuardar.anchor = GridBagConstraints.EAST;
		gbc_btnGuardar.insets = new Insets(0, 0, 5, 5);
		gbc_btnGuardar.gridx = 3;
		gbc_btnGuardar.gridy = 5;
		frmAgregarCasos.getContentPane().add(btnGuardar, gbc_btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.insets = new Insets(0, 0, 5, 0);
		gbc_btnCancelar.anchor = GridBagConstraints.WEST;
		gbc_btnCancelar.gridx = 4;
		gbc_btnCancelar.gridy = 5;
		frmAgregarCasos.getContentPane().add(btnCancelar, gbc_btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Etapas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 5;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		frmAgregarCasos.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 179, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblEtapa = new JLabel("Nombre Etapa:");
		GridBagConstraints gbc_lblEtapa = new GridBagConstraints();
		gbc_lblEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_lblEtapa.anchor = GridBagConstraints.EAST;
		gbc_lblEtapa.gridx = 1;
		gbc_lblEtapa.gridy = 0;
		panel.add(lblEtapa, gbc_lblEtapa);
		
		JLabel lblDescripcionEtapa = new JLabel("Descripcion Etapa:");
		GridBagConstraints gbc_lblDescripcionEtapa = new GridBagConstraints();
		gbc_lblDescripcionEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcionEtapa.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcionEtapa.gridx = 1;
		gbc_lblDescripcionEtapa.gridy = 1;
		panel.add(lblDescripcionEtapa, gbc_lblDescripcionEtapa);
		
		txtNombreEtapa = new JTextField();
		txtNombreEtapa.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombreEtapa = new GridBagConstraints();		
		gbc_txtNombreEtapa.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombreEtapa.insets = new Insets(0, 0, 5, 5);
		gbc_txtNombreEtapa.gridx = 2;
		gbc_txtNombreEtapa.gridy = 0;
		panel.add(txtNombreEtapa, gbc_txtNombreEtapa);
		//txtNombreEtapa.setColumns(10);
		
		txtDescripcionEtapa = new JTextField();
		txtDescripcionEtapa.addActionListener(this);
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();		
		gbc_txtDescripcion.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_txtDescripcion.gridx = 2;
		gbc_txtDescripcion.gridy = 1;
		panel.add(txtDescripcionEtapa, gbc_txtDescripcion);
		txtDescripcionEtapa.setColumns(10);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(this);
		GridBagConstraints gbc_btnAgregar = new GridBagConstraints();
		gbc_btnAgregar.anchor = GridBagConstraints.EAST;
		gbc_btnAgregar.insets = new Insets(0, 0, 5, 5);
		gbc_btnAgregar.gridx = 4;
		gbc_btnAgregar.gridy = 2;
		panel.add(btnAgregar, gbc_btnAgregar);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.gridwidth = 6;
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 3;
		panel.add(scrollPane_1, gbc_scrollPane_1);
		
		tableEtapas = new JTable();
		vEtapas = new Object[0][0];	
		dtmEtapas = new MiModelo2(vEtapas, columNamesEtapas);
	    tableEtapas.setModel(dtmEtapas);
		scrollPane_1.setViewportView(tableEtapas);
		
		btnAgregarCaso  = new JButton("Agregar Caso");
		btnAgregarCaso.addActionListener(this);
		GridBagConstraints gbc_btnAgregarCaso = new GridBagConstraints();
		gbc_btnAgregarCaso.anchor = GridBagConstraints.EAST;
		gbc_btnAgregarCaso.insets = new Insets(0,0,0,5);
		gbc_btnAgregarCaso.gridx = 0;
		gbc_btnAgregarCaso.gridy = 4;
		panel.add(btnAgregarCaso, gbc_btnAgregarCaso);
		
        btnGuardaEtapas = new JButton("Guardar");
        btnGuardaEtapas.addActionListener(this);
		GridBagConstraints gbc_btnGuardar_1 = new GridBagConstraints();
		gbc_btnGuardar_1.anchor = GridBagConstraints.EAST;
		gbc_btnGuardar_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnGuardar_1.gridx = 2;
		gbc_btnGuardar_1.gridy = 4;
		panel.add(btnGuardaEtapas, gbc_btnGuardar_1);
		
		btnCancelarEtapa = new JButton("Cancelar");
		GridBagConstraints gbc_btnCancelar_1 = new GridBagConstraints();
		gbc_btnCancelar_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnCancelar_1.gridx = 3;
		gbc_btnCancelar_1.gridy = 4;
		panel.add(btnCancelarEtapa, gbc_btnCancelar_1);
		btnCancelarEtapa.setVisible(false);
		interfazInicial();
		return frmAgregarCasos;
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