package GUI.tramite;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Insets;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import reporteador.NotaPagoTramiteViewer;

import GUI.estilos;
import bd.DBConsultarCasos;
import bd.DBConsultarEtapas;
import bd.DBInsertarTramite;
import bd.DbActualizarTramite;
import bd.DbConsultar;
import bd.DbInsertarEstadoCuenta;

import com.ibm.icu.text.NumberFormat;


import conexion.Conexion;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;



@SuppressWarnings("serial")
public class guiAgregarTramite extends JInternalFrame implements ActionListener{

	public static JInternalFrame frmTramites;
	//private JTextField txtFechaAnticipo;
	private JTextField txtBusqueda;
	private JTextField txtTramite;
	private JTextField txtDescripcion;
	//private JTextField txtCostoTotal;
	private JFormattedTextField txtCostoTotal;	
	//private JTextField txtAnticipo;
	private JFormattedTextField txtAnticipo;
	private JScrollPane spClientes;
	private JScrollPane spTramites;
	
	private JTextField txtObservacion;
	
	private JTable tableClientes;
	private JTable tableTramites;
	MiModelo dtmCliente;
	MiModelo dtmTramites;
	//jtmClientes
	//jtmTramites
	private JButton btnBuscar;
	private	JComboBox comboBox;
	private JComboBox cmbBusqueda;
	private JComboBox cmbTipoPago;
	private JButton btnGuardar;
	private JButton btnAgregar;
	private JButton btnCancelar;
	private JButton btnCancelar2;
	public JDialog jCaja;
	public JFormattedTextField txtTotalPagar, txtEntrega;
	public JLabel txtCambio;
	public JButton btnPagar, btnCancel;
	public static int bandera;
	Object vDatosTramiteTable[][];
	Object vConsultaCliente [][];
	Object vConsultaEtapas [][];
	
	private DefaultComboBoxModel jtmCasos;
	
	public String sIdCaso, sCurp, sObservaciones, sTipoPago, sAnticipo, sMensaje;
	public String sPagoTotal, sEntrega;
	public static String sCambio;
	public Double pagoTotal, entrega, cambio;
	/*String[] columNames = {"Nombre","Apellido Paterno", "Apellido Materno","Curp","Direccion",
			"Fecha Nacimiento","Email","Celular","Telefono","Observaciones"};*/
	String[] columNames = {"Nombre","Apellido Paterno", "Apellido Materno","Curp","Direccion",
			"Fecha Nacimiento","Email","Observaciones"};
	String[] columNamesTramite = {"Caso","Curp", "Observaciones","Tipo Pago","Anticipo","Etapa"};
	String[] descripciones;
	String[] precios;
	String[] idCasos;
	String Carpeta;
	
	private int fila = -1;
	private int columna;
	
	private int filaTramite = -1;
	private int columnaTramite;		
	
	public static PreparedStatement prstm;
	public static ResultSet res;
	public static String importePagado;	
	
	public void dialogoCaja()
	{
		estilos obj = new estilos();
		obj.aplicar();
		jCaja = new JDialog();		
		jCaja.setTitle("Caja");
		jCaja.setBounds(100, 100, 404, 140);
		jCaja.setVisible(true);
		jCaja.setLocationRelativeTo(frmTramites);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{119, 227, 0};
		gridBagLayout.rowHeights = new int[]{34, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		jCaja.getContentPane().setLayout(gridBagLayout);
		
		JLabel lblTotalAPagar = new JLabel("Total a Pagar:");
		GridBagConstraints gbc_lblTotalAPagar = new GridBagConstraints();
		gbc_lblTotalAPagar.insets = new Insets(0, 0, 5, 5);
		gbc_lblTotalAPagar.anchor = GridBagConstraints.EAST;
		gbc_lblTotalAPagar.gridx = 0;
		gbc_lblTotalAPagar.gridy = 0;
		jCaja.getContentPane().add(lblTotalAPagar, gbc_lblTotalAPagar);
		
		//txtTotalPagar = new JFormattedTextField();
		txtTotalPagar = estilos.formatoText(txtTotalPagar);
		txtTotalPagar.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTotalPagar = new GridBagConstraints();
		gbc_txtTotalPagar.insets = new Insets(0, 0, 5, 0);
		gbc_txtTotalPagar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTotalPagar.gridx = 1;
		gbc_txtTotalPagar.gridy = 0;
		jCaja.getContentPane().add(txtTotalPagar, gbc_txtTotalPagar);
		txtTotalPagar.setColumns(10);
		
		JLabel lblEntrega = new JLabel("Entrega:");
		GridBagConstraints gbc_lblEntrega = new GridBagConstraints();
		gbc_lblEntrega.anchor = GridBagConstraints.EAST;
		gbc_lblEntrega.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntrega.gridx = 0;
		gbc_lblEntrega.gridy = 1;
		jCaja.getContentPane().add(lblEntrega, gbc_lblEntrega);
		
		//txtEntrega = new JFormattedTextField();
		txtEntrega = estilos.formatoText(txtEntrega);
		txtEntrega.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtEntrega = new GridBagConstraints();
		gbc_txtEntrega.insets = new Insets(0, 0, 5, 0);
		gbc_txtEntrega.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtEntrega.gridx = 1;
		gbc_txtEntrega.gridy = 1;
		jCaja.getContentPane().add(txtEntrega, gbc_txtEntrega);
		txtEntrega.setColumns(10);
		txtEntrega.requestFocus();
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		jCaja.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{150, 141, 0, 0};
		gbl_panel.rowHeights = new int[]{41, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		txtCambio = new JLabel("");				
		txtCambio.setFont(new Font("Tahoma", Font.BOLD, 16));
		GridBagConstraints gbc_txtCambio = new GridBagConstraints();
		gbc_txtCambio.insets = new Insets(0, 0, 0, 5);
		gbc_txtCambio.gridx = 0;
		gbc_txtCambio.gridy = 0;
		panel.add(txtCambio, gbc_txtCambio);
		
		btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(this);
		GridBagConstraints gbc_btnPagar = new GridBagConstraints();
		gbc_btnPagar.anchor = GridBagConstraints.EAST;
		gbc_btnPagar.insets = new Insets(0, 0, 0, 5);
		gbc_btnPagar.gridx = 1;
		gbc_btnPagar.gridy = 0;
		panel.add(btnPagar, gbc_btnPagar);
		
		btnCancelar2 = new JButton("Cancelar");
		btnCancelar2.addActionListener(this);
		GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		gbc_btnCancelar.gridx = 2;
		gbc_btnCancelar.gridy = 0;
		panel.add(btnCancelar2, gbc_btnCancelar);			
	}
	
	public void creaCarpetasEtapas()
	{
		String sCount = "SELECT count(1) as cont FROM etapas" + " WHERE idCaso  ='"+comboBox.getSelectedIndex()+"'";
		String sConsulta = "SELECT * FROM etapas WHERE idCaso  ='"+comboBox.getSelectedIndex()+"'";
		
		DBConsultarEtapas objConsultar = new DBConsultarEtapas();
		vConsultaEtapas = objConsultar.consultarEtapas(sConsulta,sCount);
		int iCuenta;
		int iContador = vConsultaEtapas.length;
		
		//if(iContador == 0) 	Campos();
		
		for(iCuenta = 0; iCuenta < vConsultaEtapas.length; iCuenta++ )
		{
			File directorio = new File(Carpeta +"/"+  vConsultaEtapas[iCuenta][1].toString() +"_" + vConsultaEtapas[iCuenta][2].toString()); 
			directorio.mkdirs();
			
			System.out.println("y¿tamaño columna " + vConsultaEtapas[iCuenta].length);
			for(int iCuentaAux = 0; iCuentaAux < vConsultaEtapas[iCuenta].length;iCuentaAux++)
			{
				
				System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaEtapas[iCuenta][iCuentaAux].toString());
			}
		}
	}

	public void creaCarpeta()
	{
		Carpeta = "C:/Despacho2/Casos/";
		if(fila == -1) fila = 0;
		
		Carpeta += comboBox.getSelectedItem().toString()+"/"+ String.valueOf(tableClientes.getValueAt(fila,0))
		+" "+String.valueOf(tableClientes.getValueAt(fila,1))+" "+String.valueOf(tableClientes.getValueAt(fila,2))+"_"+String.valueOf(tableClientes.getValueAt(fila,3));
		
		 File directorio = new File(Carpeta); 
		 directorio.mkdirs();
		 
		 creaCarpetasEtapas();
		 
	}
	
	public void actionPerformed(ActionEvent evento)
	{
		int e;
		System.out.println(evento.getActionCommand());
		if (evento.getSource() == cmbBusqueda )
		{
			if(cmbBusqueda.getSelectedItem() == "Todos")
			{	txtBusqueda.setText("");
				txtBusqueda.setEnabled(false);
			}
			else
				txtBusqueda.setEnabled(true);
		}
		 if (evento.getSource() == comboBox )
		 {
			 if(comboBox.getSelectedItem() == "Seleccionar")
			 {
				 txtDescripcion.setText("");
				 
				 //txtCostoTotal.setText("");
				 txtCostoTotal.setValue(new Double("0.00"));
			 }
			 else
			 {
				 txtDescripcion.setText(descripciones[comboBox.getSelectedIndex()-1]);
				 //txtCostoTotal.setText(precios[comboBox.getSelectedIndex()-1]);
				 txtCostoTotal.setValue(new Double(precios[comboBox.getSelectedIndex()-1]));

			 }
		 }
		 if (evento.getSource() == cmbTipoPago )
		 {
			 System.out.println(cmbTipoPago.getSelectedItem());
			 if(cmbTipoPago.getSelectedItem().equals("Pagos"))
			 {
				 txtAnticipo.setEditable(true);
				 //txtFechaAnticipo.setEditable(true);
			 }
			 else
			 {
				 //txtAnticipo.setText("");
				 txtAnticipo.setValue(new Double("0.00"));
				 
				 //txtFechaAnticipo.setText("");
				 txtAnticipo.setEditable(false);
				 //txtFechaAnticipo.setEditable(false);
			 }
			 
		 }
		 if (evento.getActionCommand().equals("Cancelar"))
	    {
			 cntrlCampos();
			 vDatosTramiteTable = new Object[1][6];
			 dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
			 tableTramites.setModel(dtmTramites);

	    }
	    
    	if (evento.getActionCommand().equals("Buscar"))
    	{
    		fila = -1;
    		columna = -1;
    		if (cmbBusqueda.getSelectedItem() == "--Seleccionar--" ) 
    			JOptionPane.showMessageDialog(this, "Debe Seleccionar el tipo de busqueda!!!", "Error Busqueda", JOptionPane.ERROR_MESSAGE );
    		else if(cmbBusqueda.getSelectedItem() != "Todos" && txtBusqueda.getText().length() == 0)
    			JOptionPane.showMessageDialog(this, "Debe proporcionar el Dato para la busqueda!!!", "Error Busqueda", JOptionPane.ERROR_MESSAGE );
    		else
    		{
    			String sCondicion = "";
    			String sCount     = "";
    			/*if(cmbBusqueda.getSelectedItem() == "CURP")             sCondicion = "curp            LIKE '" + txtBusqueda.getText().trim() + "%'";
    			if(cmbBusqueda.getSelectedItem() == "Nombre")           sCondicion = "nombre          LIKE '" + txtBusqueda.getText().trim() + "%'";
    			if(cmbBusqueda.getSelectedItem() == "Apellido Paterno") sCondicion = "apellidoPaterno LIKE '" + txtBusqueda.getText().trim() + "%'";
    			if(cmbBusqueda.getSelectedItem() == "Apellido Materno") sCondicion = "apellidoMaterno LIKE '" + txtBusqueda.getText().trim() + "%'";
    			if(cmbBusqueda.getSelectedItem() == "Todos")            sCondicion = "Todos";
    			*/
    			
    			
    			if(cmbBusqueda.getSelectedItem().equals("CURP"))
    			{
    				sCount = "SELECT count(1) as cont FROM clientes where " + "curp LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				sCondicion = "SELECT * FROM clientes WHERE " + "curp LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    			}
    			if(cmbBusqueda.getSelectedItem().equals("Nombre"))
    			{
    				sCount = "SELECT count(1) as cont FROM clientes where " + "nombre LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				sCondicion = "SELECT * FROM clientes WHERE " + "nombre LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				
    				//sCondicion = "nombre LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
    			}
    			if(cmbBusqueda.getSelectedItem().equals("Apellido Paterno"))
    			{
    				sCount = "SELECT count(1) as cont FROM clientes where " + "apellidoPaterno LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				sCondicion = "SELECT * FROM clientes WHERE " + "apellidoPaterno LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				//sCondicion = "apellidoPaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
    			}
    			if(cmbBusqueda.getSelectedItem().equals("Apellido Materno"))
    			{
    				sCount = "SELECT count(1) as cont FROM clientes where " + "apellidoMaterno LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				sCondicion = "SELECT * FROM clientes WHERE " + "apellidoMaterno LIKE " +"'" +txtBusqueda.getText().trim().toUpperCase()+"%'";
    				//sCondicion = "apellidoMaterno LIKE " +"'" +textField.getText().trim().toUpperCase()+"%'";
    			}
    			if(cmbBusqueda.getSelectedItem().equals("Todos"))
    			{
    				sCount = "SELECT count(1) as cont FROM clientes";
    				sCondicion = "SELECT * FROM clientes";
    				//sCondicion = "Todos";
    			}
    			
    			System.out.println("aConsulta " + sCondicion);
    			
    			DbConsultar objConsultar = new DbConsultar();
   		 	    
    			vConsultaCliente = objConsultar.consultarCliente(sCount,sCondicion);
    			int	iContador = vConsultaCliente.length;
    			int iCuenta;
    			
    			if(iContador == 0)
   				{
    				/*txtNombre.setText("");
	    			txtApellidoPaterno.setText("");
	    			txtApellidoMaterno.setText( "");*/
	    			cntrlCampos();
    				JOptionPane.showMessageDialog(this, "No se ha podido Encontar al cliente con los datos Proporcionados", "Error Consultar Clientes", JOptionPane.INFORMATION_MESSAGE );
    				vConsultaCliente = new Object[0][0];
    				dtmCliente = new MiModelo(vConsultaCliente, columNames);
    				tableClientes.setModel(dtmCliente);
    				
    				vDatosTramiteTable = new Object[0][0];
    				dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
    				tableTramites.setModel(dtmTramites);
    			}
    			else
    			{
    				for(iCuenta = 0; iCuenta < iContador; iCuenta++ )
	    			{
	    				for(int iCuentaAux = 0; iCuentaAux < vConsultaCliente[iCuenta].length;iCuentaAux++)
	    				{
	    					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCliente[iCuenta][iCuentaAux].toString());
	    				}
	    			}
	    			/*txtNombre.setText( vConsultaCliente[0][0].toString());
	    			txtApellidoPaterno.setText( vConsultaCliente[0][1].toString());
	    			txtApellidoMaterno.setText( vConsultaCliente[0][2].toString());*/
    				dtmCliente = new MiModelo(vConsultaCliente, columNames);
    				tableClientes.setModel(dtmCliente);
	    			interfazAgregar();
	    			
	    			vDatosTramiteTable = new Object[1][6];
	    			dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
    				tableTramites.setModel(dtmTramites);
    				
    				tableClientes.setRowSelectionInterval(0,0);
    				fila = tableClientes.getSelectedRow();		
    				tableClientes.requestFocus();
    			}
    			
    		}
    	}    	    
    	
    	if (evento.getActionCommand().equals("Guardar"))
    	{
    		boolean bBanderaCorrecto;
    		boolean lCorrecto;
    		String sCampos = "(idTramite, fechaCreacionEdoCuenta)";
    		//String sValores = ;
    		
    		Object vDatosTramite[] = new Object[6];   
    		            
            obtieneValoresFormulario();
            lCorrecto = validarCampos();
            
            if (lCorrecto == true)
            {
            	JOptionPane.showMessageDialog(this, sMensaje, "ERROR", JOptionPane.ERROR_MESSAGE );
            }
            else
            {
            	System.out.println("Combo " + comboBox.getSelectedIndex());
        		
        		vDatosTramite[0] = sIdCaso;
        		vDatosTramiteTable[0][0] =  sIdCaso;
        		vDatosTramite[1] = sCurp;
        		vDatosTramiteTable[0][1] =  sCurp;
        		vDatosTramite[2] = sObservaciones;
        		vDatosTramiteTable[0][2] =  sObservaciones;
        		vDatosTramite[3] = sTipoPago;
        		vDatosTramiteTable[0][3] =  sTipoPago;
        		
        		if(sAnticipo.length() == 0)
        		{
        			vDatosTramite[4] = (Object)0;
        			vDatosTramiteTable[0][4] =  (Object)0;
        		}
        		else 
        		{
        			vDatosTramite[4] = (Object)sAnticipo;
        			vDatosTramiteTable[0][4] = (Object)sAnticipo;
        		}
        		        
        		if(sTipoPago == "Contado")
            	{
            		sPagoTotal = estilos.getObtieneValorText(txtCostoTotal);
            		/*sPagoTotal = estilos.EliminaCaracteres(sPagoTotal, "$");
            		sPagoTotal = estilos.EliminaCaracteres(sPagoTotal, ","); */           		
            		System.out.println("ANTICIPO PARA CAJA TIPO PAGO: " + sPagoTotal);
            	}
            	else
            	{            		
            		sPagoTotal = estilos.getObtieneValorText(txtAnticipo);
            		sPagoTotal = estilos.EliminaCaracteres(sPagoTotal, "$");
            		sPagoTotal = estilos.EliminaCaracteres(sPagoTotal, ",");            		
            		System.out.println("ANTICIPO PARA CAJA: " + sPagoTotal);
            	}
        		
        		String sCount = "SELECT count(1) as cont FROM etapas" + " WHERE idCaso  ='"+sIdCaso+"'";
    			String sConsulta = "SELECT * FROM etapas WHERE idCaso  ='"+sIdCaso+"'";				
    			
    			DBConsultarEtapas objConsultar = new DBConsultarEtapas();
    			Object vCononsulgtaEtapaCaso [][] = objConsultar.consultarEtapas(sConsulta, sCount);
        		
    			vDatosTramite[5] = (Object)vCononsulgtaEtapaCaso [0][1];
    			vDatosTramiteTable[0][5] = vCononsulgtaEtapaCaso [0][2];
    			
        		DBInsertarTramite objTramite = new DBInsertarTramite();
        		
        		bBanderaCorrecto = objTramite.insertarTramite(vDatosTramite);
        		
        		if (bBanderaCorrecto == true) 
        		{
        			interfazGuardado();
        			dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
    				tableTramites.setModel(dtmTramites);
    				txtTramite.setText(Integer.toString(objTramite.getIdTramite()));
    				creaCarpeta();
        			//JOptionPane.showMessageDialog(this, "Los Datos Han Sido Guardados Exitosamente!!", "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );
    				dialogoCaja();    	    				
    				txtTotalPagar.setValue(Double.parseDouble(sPagoTotal));            		        			
        		}
        		else JOptionPane.showMessageDialog(this, objTramite.excepcion.getMessage(), "Dato Insertados", JOptionPane.INFORMATION_MESSAGE );        		        			
            }                                                
    	}  
    	
    	if(evento.getActionCommand().equals("Pagar"))
		{
    		if(txtEntrega.equals("$0.00"))
    		{
    			JOptionPane.showMessageDialog(null, "Debe ingresar una cantidad en Entrega");
    		}
    		else
    		{    			
    			boolean bBandera;
				pagoTotal = Double.parseDouble(estilos.getObtieneValorText(txtTotalPagar));
		        System.out.println("Pago Total Caja " + pagoTotal);
		        sEntrega = estilos.getObtieneValorText(txtEntrega);                       
		        entrega = Double.parseDouble(sEntrega);
		        System.out.println("Pago Total entrega " + entrega);
		        if(entrega < pagoTotal)
		        {
		        	JOptionPane.showMessageDialog(null, "El pago debe de ser igual o mayor al pago total");
		        }
		        else
		        {
		        	boolean bbBandera;					
					double saldo = 0.0;					
					double abonar = 0.0; 
					double pago = Double.parseDouble(estilos.getObtieneValorText(txtEntrega));
					double dImporteFaltante = 0.0;
					String SValores = "";
					String sCampos = "";
					String sCambio = "";
					String sSaldo = "";					
					if(sTipoPago == "Pagos")
					{
						saldo = Double.parseDouble(estilos.getObtieneValorText(txtCostoTotal));
						abonar = Double.parseDouble(estilos.getObtieneValorText(txtAnticipo));
						dImporteFaltante = saldo - abonar;						
						if(abonar > saldo)
						{
							JOptionPane.showMessageDialog(this, "El pago es Mayor que el saldo Favor de verificar ", "Error Pago", JOptionPane.ERROR_MESSAGE );
						}
						else
						{
							if(dImporteFaltante > 0.0)
							{
								sCampos = "(abono, fechaAbono, importeFaltante, idTramite)";
								SValores = "('"+abonar+"', NOW(),'"+dImporteFaltante+"', '"+txtTramite.getText().trim()+"')";
							}
							else
							{
								sCampos = "(abono, fechaAbono, importeFaltante, idTramite,pagoFinal,fechaPagoFinal)";
								SValores = "('"+abonar+"', NOW(),'"+dImporteFaltante+"', '"+txtTramite.getText().trim()+"','"+abonar+"', NOW())";
							}
							sCambio = Double.toString(pago - abonar);
							sSaldo  = Double.toString(saldo - abonar);													
						}
					}
					else if(sTipoPago == "Contado")
					{						
						abonar = Double.parseDouble(estilos.getObtieneValorText(txtCostoTotal));
						saldo = Double.parseDouble(estilos.getObtieneValorText(txtCostoTotal));
						dImporteFaltante = abonar - saldo;												
						if(abonar < saldo)
						{
							JOptionPane.showMessageDialog(this, "El pago es Menor que el saldo Favor de verificar ya que Se refiere a un pago de CONTADO ", "Error Pago", JOptionPane.ERROR_MESSAGE );
						}
						else
						{
							if(dImporteFaltante > 0.0)
							{
								sCampos = "(abono, fechaAbono, importeFaltante, idTramite)";
								SValores = "('"+estilos.getObtieneValorText(txtCostoTotal)+"', NOW(),'"+dImporteFaltante+"', '"+txtTramite.getText().trim()+"')";
							}
							else
							{
								sCampos = "(abono, fechaAbono, importeFaltante, idTramite,pagoFinal,fechaPagoFinal)";
								SValores = "('"+estilos.getObtieneValorText(txtCostoTotal)+"', NOW(),'"+dImporteFaltante+"', '"+txtTramite.getText().trim()+"','"+estilos.getObtieneValorText(txtCostoTotal)+"', NOW())";
							}
							
							sCambio = Double.toString(pago - saldo);
							sSaldo  = Double.toString(dImporteFaltante);
						}
					}
					DbInsertarEstadoCuenta objEstadoCuenta = new DbInsertarEstadoCuenta();				
					bBandera = objEstadoCuenta.insertarEstadoCuenta(sCampos,SValores);
					System.out.println("bandera false " + bBandera);
					if(bBandera)
					{
						System.out.println("Estado cuenta");							
						//txtCambio.setValue(Double.parseDouble(sCambio));							
						//txtSaldo.setValue(Double.parseDouble(sSaldo));							
						if(dImporteFaltante == 0.0)
						{
							boolean bBanderaCorrecto;
							String sConsulta = "";
							String sClaveTramite = txtTramite.getText().trim();								
							sConsulta += "pagado = " + 1 ;								
							DbActualizarTramite objActualizarTramite = new DbActualizarTramite();
				    		bBanderaCorrecto = objActualizarTramite.actualizarTramite(sConsulta , sClaveTramite);				    			
				    		if (bBanderaCorrecto == true)
				    		{
				    			int filaAux;
				    			if(filaTramite == -1) filaAux = 0;
				    			else filaAux = filaTramite;
				    				//table_1.setValueAt("Pagado", filaAux, 11);
				    		}
				    		else
				    		
				    			JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );				    				
								/*txtAbonar.setEnabled(false);
								txtPago.setEnabled(false);
								txtCambio.setEnabled(false);
								btnPagar.setEnabled(false);*/
						}
						else
						{
							/*txtAbonar.setEnabled(true);
							txtPago.setEnabled(true);
							txtCambio.setEnabled(true);
							btnPagar.setEnabled(true);*/
						}
					}
					else
						System.out.println("Excepcion " + objEstadoCuenta.excepcion);
					
					btnPagar.setEnabled(false);
					btnCancelar.setEnabled(false);
			        cambio = entrega - pagoTotal;
			        NumberFormat formatter = NumberFormat.getCurrencyInstance();
			        sCambio = formatter.format(cambio);
			        System.out.println("CAMBIO DE LA CAJA " + sCambio);
			        txtCambio.setText(sCambio);			        			        
			        consultaNotaPago();					
			        NotaPagoTramiteViewer npv = new NotaPagoTramiteViewer();
					npv.printReport();
					interfazAgregar();
		        }
    		}
    		    		
		}
    	
    	if(evento.getSource() == btnCancelar2)
    	{
    		jCaja.dispose();
    	}
    	    	
	}			
	
	public void consultaNotaPago()
	{			
		Conexion con;
		String curp;
		String noTramite;
		con = new Conexion();
		importePagado = txtEntrega.getText();
		System.out.println("Importe PAGADO: " + importePagado);						
		sCambio = txtCambio.getText();		
		/*Double dCambio = Double.parseDouble(sCambio);
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		sCambio = formatter.format(dCambio);	*/	
		System.out.println("CAMBIO: " + sCambio);
		try
		{
			String sql;
			int filaAux, filaAux2;
			if(fila == -1) filaAux = 0;
			else filaAux = fila;
			if(filaTramite == -1) filaAux2 = 0;
			else filaAux2 = filaTramite;
			curp = String.valueOf(tableClientes.getValueAt(filaAux, 3));
			System.out.println("29 de Noviembre " + curp);
			noTramite = txtTramite.getText();
			System.out.println("29 de Noviembre " + noTramite);
			sql = "SELECT * FROM notapago WHERE curp = '" +curp+ "' AND NoTramite = '" + noTramite + "' order by folio desc limit 1";			
			prstm = con.getConnection().prepareStatement(sql);
			res = prstm.executeQuery();
			con.desconectar();
		}		
		catch(Exception e)
		{
			System.out.println("Excepion: " + e);
		}
	}
	
	public void imprimirTicket()
	{		
		importePagado = txtEntrega.getText();
		System.out.println("Importe PAGADO: " + importePagado);				
		sCambio = txtCambio.getText();
		System.out.println("CAMBIO: " + cambio);
//		NotaPagoViewer.printReport();			
	}
	
	public boolean validarCampos()
	{
		sMensaje = "";
		boolean bBandera = false;
		/*if(sCurp.length() <18 || sCurp.length() > 18)
		{
			bBandera = true;
			sMensaje = "El curp No es correcto";
			return bBandera;
		}*/
		if(sIdCaso == "Seleccionar")
		{
			bBandera = true;
			sMensaje += "Debe seleccionar el Tipo de Caso\n";
		}
		if(sTipoPago == "Seleccionar")
		{
			bBandera = true;
			sMensaje += "Debe Seleccionar el Tipo de Pago\n";
		}
		if(sTipoPago == "Pagos" && sAnticipo.length() == 0)
		{
			bBandera = true;
			sMensaje += "Debe Proporcionar Anticipo";
		}
		
		return bBandera;
		
	}
	
	public void obtieneValoresFormulario()
	{
		int filaAux;
		
		if(comboBox.getSelectedIndex() == 0)
			sIdCaso        = comboBox.getSelectedItem().toString();
		else sIdCaso       = idCasos[comboBox.getSelectedIndex() - 1];
		
		if(fila == -1)filaAux =0;
		else filaAux = fila;
		
		sCurp = String.valueOf(tableClientes.getValueAt(filaAux,3));
		//sCurp          = txtBusqueda.getText().toString();
		sObservaciones = txtObservacion.getText().toString().trim().toUpperCase();
		sTipoPago	   = cmbTipoPago.getSelectedItem().toString();
		//sAnticipo	   = txtAnticipo.getText().toString();
		sAnticipo = estilos.getObtieneValorText(txtAnticipo);
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
		comboBox.setModel(jtmCasos);
				
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
		comboBox.addActionListener(this);
		//txtDescripcion.setText(descripciones[comboBox.getSelectedIndex()]);
		//txtCostoTotal.setText(precios[comboBox.getSelectedIndex()]);
		System.out.println("Seleccionado " + jtmCasos.getSelectedItem()+"\n At " +comboBox.getSelectedIndex());
	}
	
	public void interfazGuardado()
	{
		txtObservacion.setEditable(false);		
		comboBox.setEnabled(false);		
		cmbTipoPago.setEnabled(false);		
		btnGuardar.setEnabled(false);
		btnCancelar.setEnabled(false);
		
	}		
	
	public void interfazAgregar()
	{
		txtObservacion.setEditable(true);
		
		txtObservacion.setText("");
		//txtFechaAnticipo.setText("");
		
		//txtCostoTotal.setText("");
		txtCostoTotal.setValue(new Double("0.00"));
		
		//txtAnticipo.setText("");
		txtAnticipo.setValue(new Double("0.00"));
		
		txtTramite.setText("");
		comboBox.setEnabled(true);
		comboBox.setSelectedItem("Seleccionar");
		cmbTipoPago.setEnabled(true);
		cmbTipoPago.setSelectedItem("Seleccionar");
		//tableClientes.setModel(dtmTramites);
		btnGuardar.setEnabled(true);
		btnCancelar.setEnabled(true);
		
	}
	
	public void cntrlCampos()
	{
		/*txtNombre.setEditable(false);
		txtApellidoPaterno.setEditable(false);
		txtApellidoMaterno.setEditable(false);*/
		txtObservacion.setText("");
		//txtFechaAnticipo.setText("");
		
		//txtCostoTotal.setText("");
		txtCostoTotal.setValue(new Double("0.00"));
		
		//txtAnticipo.setText("");
		txtAnticipo.setValue(new Double("0.00"));
		
		txtTramite.setText("");
		comboBox.setEnabled(true);
		comboBox.setSelectedItem("Seleccionar");
		cmbTipoPago.setEnabled(true);
		cmbTipoPago.setSelectedItem("Seleccionar");
		
		txtTramite.setEditable(false);
		txtDescripcion.setEditable(false);
		txtAnticipo.setEditable(false);
		txtObservacion.setEditable(false);
		//txtFechaAnticipo.setEditable(false);
		txtCostoTotal.setEditable(false);
		
		comboBox.setEnabled(false);
		cmbTipoPago.setEnabled(false);
		
		btnGuardar.setEnabled(false);
		btnCancelar.setEnabled(false);
		
	}
	
	public guiAgregarTramite() {
		//initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public JInternalFrame initialize() {
		frmTramites = new JInternalFrame("Tramites",false, true, true, true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmTramites.setFrameIcon(new ImageIcon(URLIcon));
		//frmTramites.setTitle("Agregar Tramite");
		//frmTramites.setAlwaysOnTop(true);
		frmTramites.setBounds(100, 100, 726, 537);
		frmTramites.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTramites.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panelPie = new JPanel();
		panelPie.setSize(10,10);
		FlowLayout flowLayout = (FlowLayout) panelPie.getLayout();
		//panelPie.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		frmTramites.getContentPane().add(panel, BorderLayout.NORTH);
		
		btnAgregar = new JButton("Agregar +");
		btnAgregar.addActionListener(this);
		panelPie.add(btnAgregar);
		btnAgregar.setVisible(false);
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		panelPie.add(btnGuardar);
		frmTramites.getContentPane().add(panelPie, BorderLayout.SOUTH);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		panelPie.add(btnCancelar);
		frmTramites.getContentPane().add(panelPie, BorderLayout.SOUTH);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{76, 146, 52, 76, 49, 0, 0};
		gbl_panel.rowHeights = new int[]{39, 34, 41, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};		
		gbl_panel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		cmbBusqueda = new JComboBox();
		cmbBusqueda.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--", "Todos","CURP", "Nombre", "Apellido Paterno", "Apellido Materno"}));
		cmbBusqueda.addActionListener(this);
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		cmbBusqueda.addActionListener(this);
		panel.add(cmbBusqueda, gbc_comboBox);
		
		txtBusqueda = new JTextField();
		txtBusqueda.addActionListener(estilos.tranfiereElFoco);
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
		gbc_txtBusqueda.gridx = 2;
		gbc_txtBusqueda.gridy = 0;
		panel.add(txtBusqueda, gbc_txtBusqueda);
		txtBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 3;
		gbc_btnBuscar.gridy = 0;
		panel.add(btnBuscar, gbc_btnBuscar);
		
		tableClientes = new JTable();
		tableClientes.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spClientes = new JScrollPane(tableClientes);
		vConsultaCliente = new Object[0][0];
		dtmCliente = new MiModelo(vConsultaCliente, columNames);
		tableClientes.setModel(dtmCliente);
		tableClientes.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				fila = tableClientes.rowAtPoint(e.getPoint()); 
				columna = tableClientes.columnAtPoint(e.getPoint());    
				if ((fila > -1) && (columna > -1))
				{
					filaTramite = -1;
					columnaTramite = -1;
					interfazAgregar();
					vDatosTramiteTable = new Object[1][6];
					dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
					tableTramites.setModel(dtmTramites);
					
					/*txtNombre.setText(String.valueOf(table.getValueAt(fila,0)));
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
					txtCelular.setText(String.valueOf(table.getValueAt(fila,7)));
					txtTelefono.setText(String.valueOf(table.getValueAt(fila,8)));
					txtObservaciones.setText(String.valueOf(table.getValueAt(fila,9)));*/
														   
					}
				}
			});
		
		tableClientes.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaTramite = -1;
					columnaTramite = -1;
					interfazAgregar();
					vDatosTramiteTable = new Object[1][6];
					dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
					tableTramites.setModel(dtmTramites);			
				}
			}
		});
		GridBagConstraints gbc_jtmClientes = new GridBagConstraints();
		gbc_jtmClientes.gridheight = 2;
		gbc_jtmClientes.gridwidth = 6;
		gbc_jtmClientes.insets = new Insets(0, 0, 5, 5);
		gbc_jtmClientes.fill = GridBagConstraints.BOTH;
		gbc_jtmClientes.gridx = 0;
		gbc_jtmClientes.gridy = 1;
		panel.add(spClientes, gbc_jtmClientes);
		
		JPanel panelCentro = new JPanel();
		panelCentro.setBorder(new TitledBorder(null, "Tramites", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmTramites.getContentPane().add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{109, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelCentro.rowHeights = new int[]{78, 37, 52, 37, 37, 44, 0};
		gbl_panelCentro.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);
		
		tableTramites = new JTable();
		
		tableTramites.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spTramites = new JScrollPane(tableTramites);
		vDatosTramiteTable = new Object[0][0];
		dtmTramites = new MiModelo(vDatosTramiteTable, columNamesTramite);
		tableTramites.setModel(dtmTramites);
		tableTramites.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaTramite = tableTramites.rowAtPoint(e.getPoint()); 
				columnaTramite = tableTramites.columnAtPoint(e.getPoint());    
				if ((filaTramite > -1) && (columnaTramite > -1)){
					/*txtNombre.setText(String.valueOf(table.getValueAt(fila,0)));
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
					txtCelular.setText(String.valueOf(table.getValueAt(fila,7)));
					txtTelefono.setText(String.valueOf(table.getValueAt(fila,8)));
					txtObservaciones.setText(String.valueOf(table.getValueAt(fila,9)));*/
														   
					}
				}
			});
		
		GridBagConstraints gbc_jtmTramites = new GridBagConstraints();
		gbc_jtmTramites.gridwidth = 8;
		gbc_jtmTramites.insets = new Insets(0, 0, 5, 0);
		gbc_jtmTramites.fill = GridBagConstraints.BOTH;
		gbc_jtmTramites.gridx = 0;
		gbc_jtmTramites.gridy = 0;
		panelCentro.add(spTramites, gbc_jtmTramites);
		
		JLabel lblNewLabel = new JLabel("No. Tramite:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panelCentro.add(lblNewLabel, gbc_lblNewLabel);
		
		txtTramite = new JTextField();
		txtTramite.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTramite = new GridBagConstraints();
		gbc_txtTramite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTramite.insets = new Insets(0, 0, 5, 5);
		gbc_txtTramite.gridx = 1;
		gbc_txtTramite.gridy = 1;
		panelCentro.add(txtTramite, gbc_txtTramite);
		txtTramite.setColumns(10);
		
		JLabel lblCaso = new JLabel("Caso:");
		GridBagConstraints gbc_lblCaso = new GridBagConstraints();
		gbc_lblCaso.anchor = GridBagConstraints.EAST;
		gbc_lblCaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCaso.gridx = 4;
		gbc_lblCaso.gridy = 1;
		panelCentro.add(lblCaso, gbc_lblCaso);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--"}));
		comboBox.addActionListener(this);
		GridBagConstraints gbc_cmbBusqueda = new GridBagConstraints();
		gbc_cmbBusqueda.anchor = GridBagConstraints.WEST;
		gbc_cmbBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBusqueda.gridx = 5;
		gbc_cmbBusqueda.gridy = 1;
		panelCentro.add(comboBox, gbc_cmbBusqueda);
		
		JLabel lblDescripcion = new JLabel("Descripcion:");
		GridBagConstraints gbc_lblDescripcion = new GridBagConstraints();
		gbc_lblDescripcion.anchor = GridBagConstraints.EAST;
		gbc_lblDescripcion.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcion.gridx = 0;
		gbc_lblDescripcion.gridy = 2;
		panelCentro.add(lblDescripcion, gbc_lblDescripcion);
		
		txtDescripcion = new JTextField();
		txtDescripcion.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtDescripcion = new GridBagConstraints();
		gbc_txtDescripcion.gridwidth = 7;
		gbc_txtDescripcion.fill = GridBagConstraints.BOTH;
		gbc_txtDescripcion.insets = new Insets(0, 0, 5, 0);
		gbc_txtDescripcion.gridx = 1;
		gbc_txtDescripcion.gridy = 2;
		panelCentro.add(txtDescripcion, gbc_txtDescripcion);
		txtDescripcion.setColumns(10);
		
		JLabel lblCostoTotal = new JLabel("Costo total:");
		GridBagConstraints gbc_lblCostoTotal = new GridBagConstraints();
		gbc_lblCostoTotal.anchor = GridBagConstraints.EAST;
		gbc_lblCostoTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoTotal.gridx = 0;
		gbc_lblCostoTotal.gridy = 3;
		panelCentro.add(lblCostoTotal, gbc_lblCostoTotal);
		
		//txtCostoTotal = new JTextField();
		txtCostoTotal = estilos.formatoText(txtCostoTotal);
		txtCostoTotal.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtCostoTotal = new GridBagConstraints();
		gbc_txtCostoTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCostoTotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtCostoTotal.gridx = 1;
		gbc_txtCostoTotal.gridy = 3;
		panelCentro.add(txtCostoTotal, gbc_txtCostoTotal);
		txtCostoTotal.setColumns(10);
		
		JLabel lblTipoDePago = new JLabel("Tipo de Pago:");
		GridBagConstraints gbc_lblTipoDePago = new GridBagConstraints();
		gbc_lblTipoDePago.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDePago.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDePago.gridx = 4;
		gbc_lblTipoDePago.gridy = 3;
		panelCentro.add(lblTipoDePago, gbc_lblTipoDePago);
		
		/*txtTipoPago = new JTextField();
		GridBagConstraints gbc_txtTipoPago = new GridBagConstraints();
		gbc_txtTipoPago.insets = new Insets(0, 0, 5, 5);
		gbc_txtTipoPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTipoPago.gridx = 5;
		gbc_txtTipoPago.gridy = 3;
		panelCentro.add(txtTipoPago, gbc_txtTipoPago);
		txtTipoPago.setColumns(10);*/
		cmbTipoPago = new JComboBox();
		cmbTipoPago.setModel(new DefaultComboBoxModel(new String[] {"Seleccionar","Contado", "Pagos"}));
		cmbTipoPago.addActionListener(this);
		
		GridBagConstraints gbc_cmbTipoPago = new GridBagConstraints();
		gbc_cmbTipoPago.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTipoPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipoPago.gridx = 5;
		gbc_cmbTipoPago.gridy = 3;
		panelCentro.add(cmbTipoPago, gbc_cmbTipoPago);
		
		JLabel lblAnticipo = new JLabel("Anticipo:");
		GridBagConstraints gbc_lblAnticipo = new GridBagConstraints();
		gbc_lblAnticipo.anchor = GridBagConstraints.EAST;
		gbc_lblAnticipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnticipo.gridx = 0;
		gbc_lblAnticipo.gridy = 4;
		panelCentro.add(lblAnticipo, gbc_lblAnticipo);
		
		//txtAnticipo = new JTextField();
		txtAnticipo = estilos.formatoText(txtAnticipo);
		txtAnticipo.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtAnticipo = new GridBagConstraints();
		gbc_txtAnticipo.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnticipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnticipo.gridx = 1;
		gbc_txtAnticipo.gridy = 4;
		panelCentro.add(txtAnticipo, gbc_txtAnticipo);
		txtAnticipo.setColumns(10);
		
		JLabel lblObservacin = new JLabel("Observaci\u00F3n:");
		GridBagConstraints gbc_lblObservacin = new GridBagConstraints();
		gbc_lblObservacin.anchor = GridBagConstraints.EAST;
		gbc_lblObservacin.insets = new Insets(0, 0, 0, 5);
		gbc_lblObservacin.gridx = 0;
		gbc_lblObservacin.gridy = 5;
		panelCentro.add(lblObservacin, gbc_lblObservacin);
		
		txtObservacion = new JTextField();
		GridBagConstraints gbc_txtObservacion = new GridBagConstraints();
		gbc_txtObservacion.gridwidth = 7;
		gbc_txtObservacion.fill = GridBagConstraints.BOTH;
		gbc_txtObservacion.gridx = 1;
		gbc_txtObservacion.gridy = 5;
		panelCentro.add(txtObservacion, gbc_txtObservacion);
		txtObservacion.setColumns(10);
		ComboBoxModel lista;
		
		return frmTramites;
		//String[] lista={"municipios","tecate","tijuana","ensenada","tecate"};
	}
}
@SuppressWarnings("serial")
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