package GUI.Pagos;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import com.codeko.util.Num;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

import bd.DBConsultarTramite;
import bd.DbActualizarTramite;
import bd.DbConsultarSaldo;
import bd.DbInsertarEstadoCuenta;
import bd.DBNotaPago;
import GUI.estilos;
import java.sql.*;
import conexion.Conexion;

import reporteador.NotaPagoViewer;


public class guiPagos extends JInternalFrame implements ActionListener
{
	private JInternalFrame frmPagos;
	private JTextField txtBusqueda;
	private JTable table;
	private JTextField txtNombre;
	private JTextField txtApellidoPaterno;
	private JTextField txtApellidoMaterno;
	private JTextField txtTramite;
	private JTextField txtCaso;
	private JFormattedTextField txtPrecio;
	//private JTextField txtPrecio;
	private JTable table_1;
	//private JTextField txtAnticipo;
	private JFormattedTextField txtAnticipo;
	//private JTextField txtSaldo;
	private JFormattedTextField txtSaldo;
	//private JTextField txtAbonar;
	private JFormattedTextField txtAbonar;
	public static JFormattedTextField txtPago;
	//private JTextField txtCambio;
	public static JFormattedTextField txtCambio;
	private JTextField txtTipoPago;
	private int filaTramite = -1;
	private int columnaTramite;
	
	private JButton btnPagar;
	private JButton btnBuscar;
	
	private JComboBox comboBox;
	
	private JScrollPane spClientes;
	private JScrollPane spTramites;
	
	/*private JTable tableTramite;
	private JTable tableClientes;*/
	
	String[] columNamesClientes = {"Curp","Nombre","Apellido Paterno", "Apellido Materno"};
	String[] columNamesTramites = {"No Tramite","Caso","Nombre Caso", "Descripcion","Observaciones","Precio","TipoPago","Anticipo","Estatus","Etapa","idEtapa","Pagado"};
	
	MiModelo2 dtmCliente;
	MiModelo2 dtmTramites;
	Object [][] vConsultaTramite;
	
	private int fila = -1;
	private int columna;
	
	String curp;
	String noTramite;
	public static PreparedStatement prstm;
	public static ResultSet res;
	public static String importePagado;
	public static String cambio;
	Conexion con;
			
	public guiPagos() {
		//initialize();
	}
	
	public String getObtieneValorText(JFormattedTextField textField)
	{
		 String valor =  "";		
		 Object dato = textField.getValue();
         Num obj = new Num();         
         System.out.println("numero correcto " + obj.getDecimal(dato));         
         return valor = Double.toString(obj.getDecimal(dato));         
	}
	
	public JFormattedTextField formatoText(JFormattedTextField textField)
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
    	NumberFormatter enFormat = new NumberFormatter(editFormat);enFormat.setOverwriteMode(true);
    	// Creamos la factoría de formateadores especificando los
    	// formateadores por defecto, de visualización y de edición
    	DefaultFormatterFactory currFactory = new DefaultFormatterFactory(dnFormat, dnFormat, enFormat);
    	
    	// Asignamos la factoría al campo
    	textField = new JFormattedTextField();
    	textField.setValue(new Float("00.00"));
    	//enFormat.setMaximum(10);
    	textField.setFormatterFactory(currFactory);
    	
    	return textField;
	}
	
	public void interfazInicial()
	{
		txtNombre.setEditable(false);
		txtApellidoPaterno.setEditable(false);
		txtApellidoMaterno.setEditable(false);
		
		txtTramite.setEditable(false);
		txtAnticipo.setEditable(false);
		txtCaso.setEditable(false);
		txtTipoPago.setEditable(false);
		txtPrecio.setEditable(false);
		txtSaldo.setEditable(false);
	}
	
	public void llenaCamposCliente()
	{
		int filaAux;
		if(fila == -1) filaAux = 0;
		else filaAux = fila;
		
		txtNombre.setText(String.valueOf(table.getValueAt(filaAux,1)));
		txtApellidoPaterno.setText(String.valueOf(table.getValueAt(filaAux,2)));
		txtApellidoMaterno.setText(String.valueOf(table.getValueAt(filaAux,3)));
		
		if(String.valueOf(table.getValueAt(filaAux,3))  == "Pagado")
		{
			txtAbonar.setEnabled(false);
			txtPago.setEnabled(false);
			txtCambio.setEnabled(false);
			btnPagar.setEnabled(false);
		}
		
	}
	public void llenaCamposTramite()
	{
		txtAbonar.setValue(new Double("00.00"));
		txtPago.setValue(new Double("00.00"));
		txtCambio.setValue(new Double("00.00"));
		int filaAux;
				if(filaTramite == -1) filaAux = 0;
				else filaAux = filaTramite;
		
		txtTramite.setText(String.valueOf(table_1.getValueAt(filaAux,0)));
		//txtAnticipo.setEditable(false);
		txtCaso.setText(String.valueOf(table_1.getValueAt(filaAux,2)));
		//txtPrecio.setText(String.valueOf(table_1.getValueAt(filaAux,5)));
		txtPrecio.setValue(Double.parseDouble(String.valueOf(table_1.getValueAt(filaAux,5))));
		//txtAnticipo.setText(String.valueOf(table_1.getValueAt(filaAux,7)));
		txtAnticipo.setValue(Double.parseDouble(String.valueOf(table_1.getValueAt(filaAux,7))));
		txtTipoPago.setText(String.valueOf(table_1.getValueAt(filaAux,6)));
				
		
		boolean bBanderaSaldo;
		DbConsultarSaldo objSaldo = new DbConsultarSaldo();//
		bBanderaSaldo = objSaldo.consultarSaldo(txtTramite.getText().trim());
		
		if(bBanderaSaldo)
		{
			if(objSaldo.lHuboCoincidencia == false)
			{
				//txtAnticipo.setEnabled(true);
				double Total = Double.parseDouble(getObtieneValorText(txtPrecio));
				double abono = Double.parseDouble(getObtieneValorText(txtAnticipo));
				
				//txtSaldo.setText(Double.toString(Total - abono));
				System.out.println(Total - abono);
				txtSaldo.setValue(Total - abono);
				
				if(txtSaldo.getText() == "0.0")
				{
					txtAbonar.setEnabled(false);
					txtPago.setEnabled(false);
					txtCambio.setEnabled(false);
					btnPagar.setEnabled(false);
				}
				else
				{
					if(txtTipoPago.getText().toString().equals("Pagos"))
						txtAbonar.setEnabled(true);
					else
						txtAbonar.setEnabled(false);
					txtPago.setEnabled(true);
					txtCambio.setEnabled(true);
					btnPagar.setEnabled(true);
				}
				
			}
			else
			{
				
				//txtSaldo.setText(Double.toString(objSaldo.idContacto));
				txtSaldo.setValue(objSaldo.idContacto);
				if(objSaldo.idContacto == 0.0)
				{
					txtAbonar.setEnabled(false);
					txtPago.setEnabled(false);
					txtCambio.setEnabled(false);
					btnPagar.setEnabled(false);
				}
				else
				{
					if(txtTipoPago.getText().toString().equals("Pagos"))
						txtAbonar.setEnabled(true);
					else
						txtAbonar.setEnabled(false);
					txtPago.setEnabled(true);
					txtCambio.setEnabled(true);
					btnPagar.setEnabled(true);
				}
				txtAnticipo.setEnabled(false);
			}
		}
				
				
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getActionCommand() == "Pagar") 
		{
			boolean bBandera;
			//bandera = 2;
			double saldo = Double.parseDouble(getObtieneValorText(txtSaldo));
			//double abonar = Double.parseDouble(txtAbonar.getText());
			double abonar = 0.0; 
			double pago = Double.parseDouble(getObtieneValorText(txtPago));
			double dImporteFaltante = 0.0;
			String SValores = "";
			String sCampos = "";
			String sCambio = "";
			String sSaldo = "";
			
			if(txtTipoPago.getText().toString().equals("Pagos"))
			{
				abonar = Double.parseDouble(getObtieneValorText(txtAbonar));
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
			else if(txtTipoPago.getText().toString().equals("Contado"))
			{
				
				abonar = pago;
				//dImporteFaltante = saldo - abonar;
				dImporteFaltante = 0.0;
				
				if(abonar < saldo)
				{
					JOptionPane.showMessageDialog(this, "El pago es Menor que el saldo Favor de verificar ya que Se refiere a un pago de CONTADO ", "Error Pago", JOptionPane.ERROR_MESSAGE );
				}
				else
				{
					if(dImporteFaltante > 0.0)
					{
						sCampos = "(abono, fechaAbono, importeFaltante, idTramite)";
						SValores = "('"+getObtieneValorText(txtPago)+"', NOW(),'"+dImporteFaltante+"', '"+txtTramite.getText().trim()+"')";
					}
					else
					{
						sCampos = "(abono, fechaAbono, importeFaltante, idTramite,pagoFinal,fechaPagoFinal)";
						SValores = "('"+getObtieneValorText(txtPago)+"', NOW(),'"+dImporteFaltante+"', '"+txtTramite.getText().trim()+"','"+getObtieneValorText(txtPago)+"', NOW())";
					}
					
					sCambio = Double.toString(pago - saldo);
					sSaldo  = Double.toString(dImporteFaltante);
				}
			}
			
				/*Object[] pagos = new Object[3];
				pagos[0] = txtAbonar.getText().trim();
				pagos[1] = saldo - abonar;
				pagos[2] = txtTramite.getText().trim();*/
				
				DbInsertarEstadoCuenta objEstadoCuenta = new DbInsertarEstadoCuenta();
				
				bBandera = objEstadoCuenta.insertarEstadoCuenta(sCampos,SValores);
				if(bBandera)
				{
					System.out.println("Estado cuenta");
					/*txtCambio.setText(Double.toString(pago - abonar));
					txtSaldo.setText(Double.toString(saldo - abonar));*/
					
					//txtCambio.setText(sCambio);
					txtCambio.setValue(Double.parseDouble(sCambio));
					//txtSaldo.setText(sSaldo);
					txtSaldo.setValue(Double.parseDouble(sSaldo));
					
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
		    				table_1.setValueAt("Pagado", filaAux, 11);
		    			}
		    			else
		    				JOptionPane.showMessageDialog(this, "No se ha podido Actualizar los datos", "Error Actualizar", JOptionPane.INFORMATION_MESSAGE );
		    				
						txtAbonar.setEnabled(false);
						txtPago.setEnabled(false);
						txtCambio.setEnabled(false);
						btnPagar.setEnabled(false);
					}
					else
					{
						txtAbonar.setEnabled(true);
						txtPago.setEnabled(true);
						txtCambio.setEnabled(true);
						btnPagar.setEnabled(true);
					}
				}
				else
					System.out.println("Excepcion " + objEstadoCuenta.excepcion);
			//consultaNotaPago();				
			//imprimirTicket();
				consultaNotaPago();
				//NotaPagoViewer.showViewer();
				NotaPagoViewer npv = new NotaPagoViewer();
				npv.printReport();
		}
			
			if(arg0.getActionCommand() == "Cancelar") 
			{
				llenaCamposCliente();
				llenaCamposTramite();
				interfazInicial();
			}
			if (arg0.getSource() == comboBox )
			{
				if(comboBox.getSelectedItem() == "Todos")
				{	txtBusqueda.setText("");
				    txtBusqueda.setEnabled(false);
				
				}
				else
					txtBusqueda.setEnabled(true);
			}
					
			if(arg0.getActionCommand() == "Buscar")  
			{ 
				fila = -1;
				columna = 0;
				filaTramite = -1;
				columnaTramite = -1;
				String sCount = "SELECT COUNT(DISTINCT t2.curp )AS cont " +
				  				"FROM clientes AS t1, tramite AS t2, casos AS t3 " +
				  				"WHERE t1.curp = t2.curp AND t2.idCaso = t3.idCaso AND ";
				String sConsulta = "SELECT DISTINCT(t1.curp), t1.nombre, t1.apellidoPaterno, t1.apellidoMaterno" +
								   " FROM clientes AS t1, tramite AS t2, casos AS t3"+
								   " WHERE t2.idCaso = t3.idCaso AND t1.curp = t2.curp AND ";
				
				System.out.println("txtTramite.getText() " + txtBusqueda.getText().trim());
				if(comboBox.getSelectedItem() == "No. Tramite") 
				{
					sCount+= "t2.NoTramite LIKE '"+txtBusqueda.getText().trim()+"%'";
					sConsulta += "t2.NoTramite LIKE '"+txtBusqueda.getText().trim()+"%'" + " GROUP BY curp";
				}
				if(comboBox.getSelectedItem() == "CURP")
				{
					sCount+= "t2.curp LIKE '"+txtBusqueda.getText().trim()+"%'";
					sConsulta += "t2.curp LIKE '"+txtBusqueda.getText().trim()+"%'" + " GROUP BY curp";;
				}
				if(comboBox.getSelectedItem() == "Nombre")
				{          
					sCount+= "t1.nombre LIKE '"+txtBusqueda.getText().trim()+"%'";
					sConsulta += "t1.nombre LIKE '"+txtBusqueda.getText().trim()+"%'" + " GROUP BY curp";;
				}
				if(comboBox.getSelectedItem() == "Apellido Paterno")
				{
					sCount+= "t1.apellidoPaterno LIKE '"+txtBusqueda.getText().trim()+"%'";
					sConsulta += "t1.apellidoPaterno LIKE '"+txtBusqueda.getText().trim()+"%'" + " GROUP BY curp";;
				}
				if(comboBox.getSelectedItem() == "Apellido Materno")
				{
					sCount+= "t1.apellidoMaterno LIKE '"+txtBusqueda.getText().trim()+"%'";
					sConsulta += "t1.apellidoMaterno LIKE '"+txtBusqueda.getText().trim()+"%'" + " GROUP BY curp";
				}
				if(comboBox.getSelectedItem() == "Todos")
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
				//sConsulta += " GROUP BY curp";
				realizarConsulta(sCount,sConsulta );
				
			}
				
			/*
			if(arg0.getActionCommand() == "Modificar")
			{
				interfazModificar();
			}*/


		}
	
	public void realizarConsulta(String sCount, String sConsulta)
	{
		System.out.println("sConsulta    " + sConsulta);
		System.out.println("sCount    " + sCount);
		DBConsultarTramite objConsultar = new DBConsultarTramite();
		Object [][] vConsultaCliente = objConsultar.consultarTramite("Cliente",sCount,sConsulta);
		int iCuenta;
		int iContador = vConsultaCliente.length;
		
		dtmCliente = new MiModelo2(vConsultaCliente, columNamesClientes);
		table.setModel(dtmCliente);
		if(iContador == 0)
		{
			vConsultaTramite = new Object [0][0];
			dtmTramites = new MiModelo2(vConsultaTramite, columNamesTramites);
			table_1.setModel(dtmTramites);
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
			MouseEvent e = new MouseEvent(spTramites, iContador, ABORT, iCuenta, columna, columnaTramite, iContador, closable);
			consultaTramite(e);
			llenaCamposCliente();
			llenaCamposTramite();						
		}
		
	}
	
	public void imprimirTicket()
	{		
		importePagado = txtPago.getText();
		System.out.println("Importe PAGADO: " + importePagado);				
		cambio = txtCambio.getText();
		System.out.println("CAMBIO: " + cambio);
//		NotaPagoViewer.printReport();			
	}
	
	public void consultaNotaPago()
	{	
		/*int filaAux, filaAux2;
		if(fila == -1) filaAux = 0;
		else filaAux = fila;
		if(filaTramite == -1) filaAux2 = 0;
		else filaAux2 = filaTramite;
		curp = String.valueOf(table.getValueAt(filaAux, 0));
		System.out.println("29 de Noviembre " + curp);
		noTramite = String.valueOf(table_1.getValueAt(filaAux2, 0));
		System.out.println("29 de Noviembre " + noTramite);
		String sCount, sCondicion;
		sCount = "SELECT COUNT(1) AS cont FROM notapago WHERE curp = '" + curp + "' AND NoTramite ='" + noTramite + "'";
		sCondicion = "SELECT * FROM notapago WHERE curp = '" +curp+ "' AND NoTramite = '" + noTramite + "'";
		System.out.println("29 de Noviembre " + sCondicion);
		DBNotaPago objConsultar = new DBNotaPago();
		vConsultaTramite = objConsultar.generarNotaPago(sCount, sCondicion);	*/
		con = new Conexion();
		importePagado = txtPago.getText();
		System.out.println("Importe PAGADO: " + importePagado);				
		cambio = txtCambio.getText();
		System.out.println("CAMBIO: " + cambio);
		try
		{
			String sql;
			int filaAux, filaAux2;
			if(fila == -1) filaAux = 0;
			else filaAux = fila;
			if(filaTramite == -1) filaAux2 = 0;
			else filaAux2 = filaTramite;
			curp = String.valueOf(table.getValueAt(filaAux, 0));
			System.out.println("29 de Noviembre " + curp);
			noTramite = String.valueOf(table_1.getValueAt(filaAux2, 0));
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
			
	public void limpiaCampos()
	{
		txtAbonar.setText("");
		txtPago.setText("");
		txtCambio.setText("");
			
		txtTramite.setText("");
		//txtAnticipo.setEditable(false);
		txtCaso.setText("");
		txtPrecio.setText("");
		txtAnticipo.setText("");
		txtTipoPago.setText("");
		txtNombre.setText("");
		txtApellidoPaterno.setText("");
		txtApellidoMaterno.setText("");
		txtSaldo.setText("");
	}
	public void consultaTramite(MouseEvent e)
	{
		if ((fila == -1) && (columna == -1))
		{
			fila = 0; 
			columna = 0;
		}
		else
		{
			fila = table.rowAtPoint(e.getPoint()); 
			columna = table.columnAtPoint(e.getPoint());
		}
		    
		if ((fila > -1) && (columna > -1))
		{
			String curp = String.valueOf(table.getValueAt(fila,0));
			System.out.println("curp " + curp);
			String sCount;
			String sConsulta; 
			if(comboBox.getSelectedItem() == "No. Tramite")
			{
				System.out.println("nombre tramite");
				sCount = "SELECT count( 1)AS cont FROM tramite WHERE curp ='" +curp+"' AND NoTramite LIKE '"+txtBusqueda.getText().trim()+"%'";
				/* sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones,"+
										" t3.nombreCaso, t3.idCaso, t3.descripcion, t3.precio " +
									    " FROM tramite AS t2, casos AS t3 " +
									    " WHERE t2.idCaso = t3.idCaso AND t2.curp ='"+curp+"' AND t2.NoTramite LIKE '"+txtTramite.getText()+"%'";*/
				sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones, t2.estatus,t2.pagado,"+
        		" t3.nombreCaso, t3.descripcion, t3.precio,"+
        		" t4.nombreEtapa, t4.idEtapa " +
        		" FROM tramite AS t2, casos AS t3, etapas AS t4"+
        		" WHERE t3.idCaso = t4.idCaso AND t2.idCaso = t3.idCaso "+
        		" AND t2.idEtapa = t4.idEtapa AND t2.NoTramite LIKE'"+txtBusqueda.getText().trim()+"%' AND t2.curp ='"+curp+"'";
				 
				
				
			}
			else
			{
				System.out.println("otra condicion");
				sCount = "SELECT count( 1)AS cont FROM tramite WHERE curp ='" +curp+"'";
				/* sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones,"+
										" t3.nombreCaso, t3.idCaso, t3.descripcion, t3.precio " +
									    " FROM tramite AS t2, casos AS t3 " +
									    " WHERE t2.idCaso = t3.idCaso AND t2.curp ='"+curp+"'";*/
				sConsulta = "SELECT t2.NoTramite, t2.idCaso, t2.anticipo, t2.tipoPago, t2.observaciones, t2.estatus,t2.pagado,"+
					        		" t3.nombreCaso, t3.descripcion, t3.precio,"+
					        		" t4.nombreEtapa, t4.idEtapa " +
					        		" FROM tramite AS t2, casos AS t3, etapas AS t4"+
					        		" WHERE t3.idCaso = t4.idCaso AND t2.idCaso = t3.idCaso "+
					        		" AND t2.idEtapa = t4.idEtapa AND t2.curp ='"+curp+"'";
			}


			DBConsultarTramite objConsultar = new DBConsultarTramite();
			vConsultaTramite = objConsultar.consultarTramite("Pagos",sCount,sConsulta);
			int iCuenta;
			int iContador = vConsultaTramite.length;
			/*for(iCuenta = 0; iCuenta < vConsultaTramite.length; iCuenta++ )
			{
				System.out.println("y¿tamaño columna " + vConsultaTramite[iCuenta].length);
				for(int iCuentaAux = 0; iCuentaAux < vConsultaTramite[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaTramite[iCuenta][iCuentaAux].toString());
				}
			}*/
			dtmTramites = new MiModelo2(vConsultaTramite, columNamesTramites);
			table_1.setModel(dtmTramites);
			
												   
			}
	}
	
	public JInternalFrame initialize()
	{
		frmPagos = new JInternalFrame("Consultar Estado de Cuenta",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmPagos.setFrameIcon(new ImageIcon(URLIcon));
		frmPagos.setBounds(100, 100, 600, 500);
		frmPagos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmPagos.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos clientes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmPagos.getContentPane().add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{151, 125, 134, 0, 0};
		gbl_panel.rowHeights = new int[]{41, 73};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 0.0};
		panel.setLayout(gbl_panel);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--","Todos","No. Tramite", "CURP", "Nombre", "Apellido Paterno", "Apellido Materno"}));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.anchor = GridBagConstraints.EAST;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		comboBox.addActionListener(this);
		panel.add(comboBox, gbc_comboBox);
		
		txtBusqueda = new JTextField();
		GridBagConstraints gbc_txtBusqueda = new GridBagConstraints();
		gbc_txtBusqueda.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtBusqueda.insets = new Insets(0, 0, 5, 5);
		gbc_txtBusqueda.gridx = 2;
		gbc_txtBusqueda.gridy = 0;
		panel.add(txtBusqueda, gbc_txtBusqueda);
		txtBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.anchor = GridBagConstraints.WEST;
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 0);
		gbc_btnBuscar.gridx = 3;
		gbc_btnBuscar.gridy = 0;
		panel.add(btnBuscar, gbc_btnBuscar);
		
		table = new JTable();
		table.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spClientes = new JScrollPane(table);
		vConsultaTramite = new Object [0][0];
		dtmCliente = new MiModelo2(vConsultaTramite, columNamesClientes);
		table.setModel(dtmCliente);
		table.addMouseListener(new MouseAdapter()
		{     
			public void mouseClicked(MouseEvent e)
			{ 
				filaTramite = -1;
				columnaTramite = -1;
				consultaTramite(e);				
				llenaCamposCliente();
				llenaCamposTramite();							
			}
		});
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.gridwidth = 4;
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 1;
		panel.add(spClientes, gbc_table);
		
		JPanel panel_1 = new JPanel();
		frmPagos.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Estado de Cuenta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmPagos.getContentPane().add(panel_2, BorderLayout.CENTER);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{102, 188, 99, 188, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_2.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblNombres = new JLabel("Nombre(s):");
		GridBagConstraints gbc_lblNombres = new GridBagConstraints();
		gbc_lblNombres.anchor = GridBagConstraints.EAST;
		gbc_lblNombres.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombres.gridx = 0;
		gbc_lblNombres.gridy = 0;
		panel_2.add(lblNombres, gbc_lblNombres);
		
		txtNombre = new JTextField();
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.gridwidth = 3;
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 0;
		panel_2.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblApellidoPaterno = new GridBagConstraints();
		gbc_lblApellidoPaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoPaterno.gridx = 0;
		gbc_lblApellidoPaterno.gridy = 1;
		panel_2.add(lblApellidoPaterno, gbc_lblApellidoPaterno);
		
		txtApellidoPaterno = new JTextField();
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 1;
		panel_2.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(10);
		
		JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
		GridBagConstraints gbc_lblApellidoMaterno = new GridBagConstraints();
		gbc_lblApellidoMaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoMaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoMaterno.gridx = 2;
		gbc_lblApellidoMaterno.gridy = 1;
		panel_2.add(lblApellidoMaterno, gbc_lblApellidoMaterno);
		
		txtApellidoMaterno = new JTextField();
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.gridx = 3;
		gbc_txtApellidoMaterno.gridy = 1;
		panel_2.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Pagos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 4;
		gbc_panel_3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel_2.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 122, 62, 110, 69, 108, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0, 0, 69, 0, 0, 0};
		gbl_panel_3.columnWeights = new double[]{1.0, 1.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblNoTramite = new JLabel("No. Tramite:");
		GridBagConstraints gbc_lblNoTramite = new GridBagConstraints();
		gbc_lblNoTramite.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoTramite.anchor = GridBagConstraints.EAST;
		gbc_lblNoTramite.gridx = 0;
		gbc_lblNoTramite.gridy = 0;
		panel_3.add(lblNoTramite, gbc_lblNoTramite);
		
		txtTramite = new JTextField();
		GridBagConstraints gbc_txtTramite = new GridBagConstraints();
		gbc_txtTramite.insets = new Insets(0, 0, 5, 5);
		gbc_txtTramite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTramite.gridx = 1;
		gbc_txtTramite.gridy = 0;
		panel_3.add(txtTramite, gbc_txtTramite);
		txtTramite.setColumns(10);
		
		JLabel lblCaso = new JLabel("Caso:");
		GridBagConstraints gbc_lblCaso = new GridBagConstraints();
		gbc_lblCaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCaso.anchor = GridBagConstraints.EAST;
		gbc_lblCaso.gridx = 2;
		gbc_lblCaso.gridy = 0;
		panel_3.add(lblCaso, gbc_lblCaso);
		
		txtCaso = new JTextField();
		GridBagConstraints gbc_txtCaso = new GridBagConstraints();
		gbc_txtCaso.gridwidth = 3;
		gbc_txtCaso.insets = new Insets(0, 0, 5, 0);
		gbc_txtCaso.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCaso.gridx = 3;
		gbc_txtCaso.gridy = 0;
		panel_3.add(txtCaso, gbc_txtCaso);
		txtCaso.setColumns(10);
		
		JLabel lblPrecio = new JLabel("Precio:");
		GridBagConstraints gbc_lblPrecio = new GridBagConstraints();
		gbc_lblPrecio.anchor = GridBagConstraints.EAST;
		gbc_lblPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrecio.gridx = 0;
		gbc_lblPrecio.gridy = 1;
		panel_3.add(lblPrecio, gbc_lblPrecio);
		
		txtPrecio = formatoText(txtPrecio);
		GridBagConstraints gbc_txtPrecio = new GridBagConstraints();
		gbc_txtPrecio.insets = new Insets(0, 0, 5, 5);
		gbc_txtPrecio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrecio.gridx = 1;
		gbc_txtPrecio.gridy = 1;
		panel_3.add(txtPrecio, gbc_txtPrecio);
		txtPrecio.setColumns(10);
		
		JLabel lblTipoDePago = new JLabel("Tipo de Pago:");
		GridBagConstraints gbc_lblTipoDePago = new GridBagConstraints();
		gbc_lblTipoDePago.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDePago.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDePago.gridx = 2;
		gbc_lblTipoDePago.gridy = 1;
		panel_3.add(lblTipoDePago, gbc_lblTipoDePago);
		
		txtTipoPago = new JTextField();
		GridBagConstraints gbc_txtTipoPago = new GridBagConstraints();
		gbc_txtTipoPago.insets = new Insets(0, 0, 5, 5);
		gbc_txtTipoPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTipoPago.gridx = 3;
		gbc_txtTipoPago.gridy = 1;
		panel_3.add(txtTipoPago, gbc_txtTipoPago);
		txtTipoPago.setColumns(10);
		
		JLabel lblAnticipo = new JLabel("Anticipo:");
		GridBagConstraints gbc_lblAnticipo = new GridBagConstraints();
		gbc_lblAnticipo.anchor = GridBagConstraints.EAST;
		gbc_lblAnticipo.insets = new Insets(0, 0, 5, 5);
		gbc_lblAnticipo.gridx = 4;
		gbc_lblAnticipo.gridy = 1;
		panel_3.add(lblAnticipo, gbc_lblAnticipo);
		
		txtAnticipo = formatoText(txtAnticipo);
		GridBagConstraints gbc_txtAnticipo = new GridBagConstraints();
		gbc_txtAnticipo.insets = new Insets(0, 0, 5, 0);
		gbc_txtAnticipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnticipo.gridx = 5;
		gbc_txtAnticipo.gridy = 1;
		panel_3.add(txtAnticipo, gbc_txtAnticipo);
		txtAnticipo.setColumns(10);
		
		JLabel lblImporteFaltante = new JLabel("Saldo:");
		GridBagConstraints gbc_lblImporteFaltante = new GridBagConstraints();
		gbc_lblImporteFaltante.anchor = GridBagConstraints.EAST;
		gbc_lblImporteFaltante.insets = new Insets(0, 0, 5, 5);
		gbc_lblImporteFaltante.gridx = 0;
		gbc_lblImporteFaltante.gridy = 2;
		panel_3.add(lblImporteFaltante, gbc_lblImporteFaltante);
		
		//txtSaldo = new JTextField();
		txtSaldo = formatoText(txtSaldo);
		
		GridBagConstraints gbc_txtSaldo = new GridBagConstraints();
		gbc_txtSaldo.insets = new Insets(0, 0, 5, 5);
		gbc_txtSaldo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSaldo.gridx = 1;
		gbc_txtSaldo.gridy = 2;
		panel_3.add(txtSaldo, gbc_txtSaldo);
		txtSaldo.setColumns(10);
		
		table_1 = new JTable();
		table_1.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spTramites = new JScrollPane(table_1);
		vConsultaTramite = new Object [0][0];
		dtmTramites = new MiModelo2(vConsultaTramite, columNamesClientes);
		table_1.setModel(dtmTramites);
		table_1.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaTramite = table_1.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaTramite);
				columnaTramite = table_1.columnAtPoint(e.getPoint());    
				if ((filaTramite > -1) && (columnaTramite > -1))
				{
					llenaCamposCliente();
					llenaCamposTramite();
					
					
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
		
		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.insets = new Insets(0, 0, 5, 0);
		gbc_table_1.gridwidth = 6;
		gbc_table_1.fill = GridBagConstraints.BOTH;
		gbc_table_1.gridx = 0;
		gbc_table_1.gridy = 3;
		panel_3.add(spTramites, gbc_table_1);
		
		JLabel lblPago = new JLabel("Abonar:");
		GridBagConstraints gbc_lblPago = new GridBagConstraints();
		gbc_lblPago.anchor = GridBagConstraints.EAST;
		gbc_lblPago.insets = new Insets(0, 0, 5, 5);
		gbc_lblPago.gridx = 0;
		gbc_lblPago.gridy = 4;
		panel_3.add(lblPago, gbc_lblPago);
		
		txtAbonar = formatoText(txtAbonar);
		/*txtAbonar = new JFormattedTextField();
		MaskFormatter mascara;
		try {
			mascara = new MaskFormatter("#,###.00");
			//txtAbonar = new JFormattedTextField(new DecimalFormat("#,###.00"));
			txtAbonar = new JFormattedTextField(mascara);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/		
		GridBagConstraints gbc_txtAbonar = new GridBagConstraints();
		gbc_txtAbonar.insets = new Insets(0, 0, 5, 5);
		gbc_txtAbonar.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAbonar.gridx = 1;
		gbc_txtAbonar.gridy = 4;
		panel_3.add(txtAbonar, gbc_txtAbonar);
		txtAbonar.setHorizontalAlignment(JTextField.RIGHT);
		txtAbonar.setColumns(10);
		
		JLabel lblPago_1 = new JLabel("Pago:");
		GridBagConstraints gbc_lblPago_1 = new GridBagConstraints();
		gbc_lblPago_1.anchor = GridBagConstraints.EAST;
		gbc_lblPago_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblPago_1.gridx = 2;
		gbc_lblPago_1.gridy = 4;
		panel_3.add(lblPago_1, gbc_lblPago_1);
		
		txtPago = formatoText(txtPago);
		GridBagConstraints gbc_txtPago = new GridBagConstraints();
		gbc_txtPago.insets = new Insets(0, 0, 5, 5);
		gbc_txtPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPago.gridx = 3;
		gbc_txtPago.gridy = 4;
		panel_3.add(txtPago, gbc_txtPago);
		txtPago.setHorizontalAlignment(JTextField.RIGHT);
		txtPago.setColumns(10);
		
		btnPagar = new JButton("Pagar");
		btnPagar.addActionListener(this);
		GridBagConstraints gbc_btnPagar = new GridBagConstraints();
		gbc_btnPagar.insets = new Insets(0, 0, 5, 5);
		gbc_btnPagar.gridx = 4;
		gbc_btnPagar.gridy = 4;
		panel_3.add(btnPagar, gbc_btnPagar);
		
		JLabel lblNewLabel = new JLabel("Cambio:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 5;
		panel_3.add(lblNewLabel, gbc_lblNewLabel);
		
		txtCambio = formatoText(txtCambio);
		GridBagConstraints gbc_txtCambio = new GridBagConstraints();
		gbc_txtCambio.insets = new Insets(0, 0, 0, 5);
		gbc_txtCambio.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCambio.gridx = 3;
		gbc_txtCambio.gridy = 5;
		panel_3.add(txtCambio, gbc_txtCambio);
		txtCambio.setHorizontalAlignment(JTextField.RIGHT);
		txtCambio.setColumns(10);
		
		interfazInicial();
		return frmPagos;
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