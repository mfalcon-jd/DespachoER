package GUI.reportes;

import GUI.estilos;
import java.awt.Dimension;
import java.awt.FlowLayout;

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
import conexion.Conexion;
import bd.DbConsultar;

@SuppressWarnings("serial")
public class frmEstadoCuenta extends JInternalFrame implements ActionListener{

	public static JInternalFrame frmEstadoDeCuenta;
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
	public frmEstadoCuenta() {
		//initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @return 
	 */
	
	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getActionCommand().equals("Buscar"))
		{
			buscarClientes();			
		}
		if(evento.getActionCommand().equals("Ver Reporte"))
		{
			verReportes();
		}
	}
	
	public void verReportes()
	{	
		consultaReporte();
		limpiar();
		EstadoViewer.showViewer();		
	}
		
	
	public void buscarClientes()
	{
		
		String sCount = "SELECT count(1) as cont FROM clientes where " + "curp LIKE " +"'" +txtCurp.getText().trim().toUpperCase()+"%'";
		String sCondicion = "SELECT * FROM clientes WHERE " + "curp LIKE " +"'" +txtCurp.getText().trim().toUpperCase()+"%'";
		
		//String sCondicion = "curp LIKE" + "'" + txtCurp.getText().trim().toUpperCase() + "%'";  				
		DbConsultar objConsultar = new DbConsultar();
		vConsultaClientes = objConsultar.consultarCliente(sCount, sCondicion);
		/*int cuenta, contador, cuentaAux;
		contador = vConsultaClientes.length;/
		/*for(cuenta = 0; cuenta<contador; cuenta++)
		{
			for(cuentaAux = 0; cuentaAux<vConsultaClientes[cuenta].length; cuentaAux++)
			{
				System.out.println("Dato de cliente " + cuenta + " dato " + cuentaAux + " valor " + vConsultaClientes[cuenta][cuentaAux].toString());
			}
		}*/
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
		busqueda.setSize(800, 370);
		busqueda.setLocationRelativeTo(frmEstadoDeCuenta);
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
			strsql = "SELECT * FROM repedocuenta WHERE curp LIKE" +"'" +curp +"'"+" order by NoTramite asc";			
			PreparedStatement pstm = con.getConnection().prepareStatement(strsql);
			res = pstm.executeQuery();
			System.out.println("Datos: " + res);
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
		estilos obj = new estilos();
		obj.aplicar();
		frmEstadoDeCuenta = new JInternalFrame("Estado de Cuenta",false,true,true,true);		
		URL URLIcon = obj.cargador("img/al.png");		
		frmEstadoDeCuenta.setFrameIcon(new ImageIcon(URLIcon));
		frmEstadoDeCuenta.setBounds(100, 100, 90, 166);
		/*Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		frmEstadoDeCuenta.setSize(pantalla.height / 3, pantalla.width / 3);
		Dimension ventana = frmEstadoDeCuenta.getSize();
		int width = (pantalla.width - ventana.width) / 2;
		int height = (pantalla.height - ventana.height) / 2;
		frmEstadoDeCuenta.setLocation(width, height);*/
		//frmEstadoDeCuenta.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 131, 90, 77, 0};
		gridBagLayout.rowHeights = new int[]{52, 33, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		frmEstadoDeCuenta.getContentPane().setLayout(gridBagLayout);			
		
		JLabel lblCurp = new JLabel("Curp:");
		GridBagConstraints gbc_lblCurp = new GridBagConstraints();
		gbc_lblCurp.insets = new Insets(20, 65, 0, 5);
		gbc_lblCurp.anchor = GridBagConstraints.EAST;
		gbc_lblCurp.gridx = 0;
		gbc_lblCurp.gridy = 0;
		frmEstadoDeCuenta.getContentPane().add(lblCurp, gbc_lblCurp);
		
		txtCurp = new JTextField();
		GridBagConstraints gbc_txtCurp = new GridBagConstraints();
		gbc_txtCurp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurp.insets = new Insets(20, 0, 5, 5);
		gbc_txtCurp.gridx = 1;
		gbc_txtCurp.gridy = 0;
		frmEstadoDeCuenta.getContentPane().add(txtCurp, gbc_txtCurp);
		txtCurp.setColumns(10);
		
		JLabel lblTramite = new JLabel("Tramite");
		
		JButton btnBuscar = new JButton("Buscar");
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.anchor = GridBagConstraints.WEST;
		gbc_btnBuscar.gridx = 2;
		gbc_btnBuscar.gridy = 0;
		gbc_btnBuscar.insets = new Insets(20, 0, 0, 0);
		frmEstadoDeCuenta.getContentPane().add(btnBuscar, gbc_btnBuscar);
		btnBuscar.addActionListener(this);
		
		JButton btnVerReporte = new JButton("Ver Reporte");
		GridBagConstraints gbc_btnVerReporte = new GridBagConstraints();
		gbc_btnVerReporte.anchor = GridBagConstraints.WEST;
		gbc_btnVerReporte.gridx = 2;
		gbc_btnVerReporte.gridy = 1;
		gbc_btnVerReporte.insets = new Insets(0, 0, 10, 0);
		frmEstadoDeCuenta.getContentPane().add(btnVerReporte, gbc_btnVerReporte);
		btnVerReporte.addActionListener(this);
		
		return frmEstadoDeCuenta;
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
