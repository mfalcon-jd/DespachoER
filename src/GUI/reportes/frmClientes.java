package GUI.reportes;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import reporteador.EstadoViewer;
import reporteador.SampleViewerFrame;
import GUI.estilos;
import conexion.Conexion;
import bd.DbConsultar;

public class frmClientes extends JInternalFrame implements ActionListener{

	public static JInternalFrame frmClientes;
	public static JTextField txtCurp;	
	public static String sNombre, sApellidoPaterno, sApellidoMaterno, sCurp;
	public static JDialog busqueda;
	public static Object[][] vConsultaClientes;
	public static String[] nombreColumna = {"Nombre", "Apellido Paterno", "Apellido Materno", "CURP"};
	public static JScrollPane spClientes;
	public static JTable tabla;	
	private int fila = -1;
	private int columna;
	MiModelo dtmCliente;
	public static PreparedStatement pstm;
	public static ResultSet res;
	public static String curp;
	Conexion con;
	
//	estadoCuenta ec = new estadoCuenta();
	EstadoViewer ev = new EstadoViewer();

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmEstadoCuenta window = new frmEstadoCuenta();
					window.frmEstadoDeCuenta.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public frmClientes() {
		//initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getActionCommand().equals("Consultar"))
		{
			buscarClientes();			
		}
		if(evento.getActionCommand().equals("Ver"))
		{
			verReportes();
		}
	}
	
	public void verReportes()
	{	
		consultaReporte();
		limpiar();
		SampleViewerFrame.showViewer();
	}
		
	
	public void buscarClientes()
	{		
		String sCount = "SELECT count(1) as cont FROM clientes where " + "curp LIKE " +"'" +txtCurp.getText().trim().toUpperCase()+"%'";
		String sCondicion = "SELECT * FROM clientes WHERE " + "curp LIKE " +"'" +txtCurp.getText().trim().toUpperCase()+"%'";		
		//String sCondicion = "curp LIKE" + "'" + txtCurp.getText().trim().toUpperCase() + "%'";  				
		DbConsultar objConsultar = new DbConsultar();
		vConsultaClientes = objConsultar.consultarCliente(sCount, sCondicion);		
		dtmCliente = new MiModelo(vConsultaClientes, nombreColumna);
		//tabla.setModel(dtmCliente);
		
		mostrarJDialog();	
		//Consulta();
	}
	
	public void mostrarJDialog()
	{	
		estilos obj = new estilos();
		obj.aplicar();		
		busqueda=new JDialog();
		FlowLayout gbl = new FlowLayout();		
		busqueda.setTitle("Busqueda Clientes");				
		//busqueda.setLocationRelativeTo(null);				
		busqueda.setSize(800, 370);
		busqueda.setLocationRelativeTo(frmClientes);
		busqueda.setVisible(true);
		busqueda.getContentPane().setLayout(gbl);		
		tabla = new JTable();
		tabla.setPreferredScrollableViewportSize(new Dimension(750, 300));
		tabla.isEditing();
		spClientes = new JScrollPane(tabla);
		vConsultaClientes = new Object[0][0];
		//dtmCliente = new MiModelo(vConsultaClientes, nombreColumna);
		tabla.setModel(dtmCliente);
		
		tabla.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				fila = tabla.rowAtPoint(e.getPoint()); 
				columna = tabla.columnAtPoint(e.getPoint());
				if ((fila > -1) && (columna > -1))
				{					
					txtCurp.setText(String.valueOf(tabla.getValueAt(fila,3)));
					busqueda.dispose();						
				}				
			}
		});
		
		busqueda.getContentPane().add(spClientes);			
	}
	
	public void consultaReporte()
	{
		con = new Conexion();
		curp = txtCurp.getText();		
		try {
			String strsql = "";	
			//String sCondicion = "SELECT c.*, t.telefono, tt.descripcion FROM clientes c join telefonos t ON c.curp = t.curp join tipoTelefonos tt ON t.tipo = tt.idTipo WHERE " + "curp LIKE " +"'" +txtCurp.getText().trim().toUpperCase()+"%'";
			strsql = "SELECT * FROM datosclientes WHERE curp LIKE" +"'" +curp.trim().toUpperCase()+"%'";
			PreparedStatement pstm = con.getConnection().prepareStatement(strsql);
			res = pstm.executeQuery();			
			con.desconectar();
		} catch (Exception e) {
			System.out.println("e.toString() " + e.getMessage());
			System.out.println(e);
//			e.printStackTrace();
		}
	}
	
	public void limpiar()
	{
		txtCurp.setText("");
	}
			
	public JInternalFrame initialize() {
		frmClientes = new JInternalFrame("Clientes",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmClientes.setFrameIcon(new ImageIcon(URLIcon));
		//frmEstadoDeCuenta.setTitle("Estado de Cuenta");
		frmClientes.setBounds(100, 100, 90, 166);		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 131, 90, 77, 0};
		gridBagLayout.rowHeights = new int[]{52, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frmClientes.getContentPane().setLayout(gridBagLayout);			
		
		JLabel lblCurp = new JLabel("Curp:");
		GridBagConstraints gbc_lblCurp = new GridBagConstraints();
		gbc_lblCurp.insets = new Insets(20, 65, 0, 5);
		gbc_lblCurp.anchor = GridBagConstraints.EAST;
		gbc_lblCurp.gridx = 0;
		gbc_lblCurp.gridy = 0;
		frmClientes.getContentPane().add(lblCurp, gbc_lblCurp);
		
		txtCurp = new JTextField();
		GridBagConstraints gbc_txtCurp = new GridBagConstraints();
		gbc_txtCurp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurp.insets = new Insets(20, 0, 5, 5);
		gbc_txtCurp.gridx = 1;
		gbc_txtCurp.gridy = 0;
		frmClientes.getContentPane().add(txtCurp, gbc_txtCurp);
		txtCurp.setColumns(10);
		
		JLabel lblTramite = new JLabel("Tramite");
		
		JButton btnBuscar = new JButton("Consultar");
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.anchor = GridBagConstraints.WEST;
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 0;
		gbc_btnBuscar.insets = new Insets(20, 0, 0, 0);
		frmClientes.getContentPane().add(btnBuscar, gbc_btnBuscar);
		btnBuscar.addActionListener(this);
		
		JButton btnVerReporte = new JButton("Ver");
		GridBagConstraints gbc_btnVerReporte = new GridBagConstraints();
		gbc_btnVerReporte.anchor = GridBagConstraints.WEST;
		gbc_btnVerReporte.gridx = 2;
		gbc_btnVerReporte.gridy = 1;
		gbc_btnVerReporte.insets = new Insets(0, 0, 10, 0);
		frmClientes.getContentPane().add(btnVerReporte, gbc_btnVerReporte);
		btnVerReporte.addActionListener(this);
		
		return frmClientes;
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
