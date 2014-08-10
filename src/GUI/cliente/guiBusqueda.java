package GUI.cliente;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import GUI.estilos;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.jvnet.substance.SubstanceLookAndFeel;

import bd.DBConsultarTramite;
import bd.DbConsultar;

public class guiBusqueda extends JInternalFrame implements ActionListener {

	private JInternalFrame frmBusqueda;
	private JTextField txtNombre;
	private JTextField txtApellidoPaterno;
	private JTextField txtApellidoMaterno;
	private JTextField txtCostoTotal;
	private JTextField txtAnticipo;
	private JTextField txtFechaAnticipo;
	private JTextField txtTramite;
	private JTextField txtCurp;
	
	private JButton btnCancelar;
	private JButton btnGuardar;
	
	private JComboBox cmbBusquedaTramite;
	private JButton btnBuscar;
	
	/*public static void main(String[] args) {
		
		estilos obj = new estilos();
		obj.aplicar();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//guiBusqueda window = new guiBusqueda();
					//window.frmBusqueda.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public guiBusqueda() {
		//initialize();
	}
public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getActionCommand() == "Cancelar") System.exit(0);
		
		if(arg0.getActionCommand() == "Buscar")  
		{
			DBConsultarTramite objConsultar = new DBConsultarTramite();
			Object [][] vConsultaCliente = objConsultar.consultarTramite("","",txtCurp.getText());
			int iCuenta;
			int iContador = vConsultaCliente.length;
			for(iCuenta = 0; iCuenta < vConsultaCliente.length; iCuenta++ )
			{
				for(int iCuentaAux = 1; iCuentaAux < vConsultaCliente[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCliente[iCuenta][iCuentaAux].toString());
				}
			}
		}
			
		if(arg0.getActionCommand() == "Guardar"){}


	}

	public Component initialize() 
	{
		frmBusqueda = new JInternalFrame("Consultar Tramites por Cliente",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmBusqueda.setFrameIcon(new ImageIcon(URLIcon));
		//frmBusqueda.setAlwaysOnTop(true);
		frmBusqueda.setBounds(100, 100, 600, 500);
		frmBusqueda.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmBusqueda.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panelPie = new JPanel();
		panelPie.setSize(10,10);
		FlowLayout flowLayout = (FlowLayout) panelPie.getLayout();
		//panelPie.setBorder(new TitledBorder(null, "Datos del Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		frmBusqueda.getContentPane().add(panel, BorderLayout.NORTH);
		
		btnGuardar = new JButton("Guardar");
		panelPie.add(btnGuardar);
		frmBusqueda.getContentPane().add(panelPie, BorderLayout.SOUTH);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this);
		panelPie.add(btnCancelar);
		frmBusqueda.getContentPane().add(panelPie, BorderLayout.SOUTH);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{76, 115, 52, 76, 49, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 20, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};		
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblCurp = new JLabel("Buscar por:");
		GridBagConstraints gbc_lblCurp = new GridBagConstraints();
		gbc_lblCurp.anchor = GridBagConstraints.WEST;
		gbc_lblCurp.fill = GridBagConstraints.VERTICAL;
		gbc_lblCurp.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurp.gridx = 0;
		gbc_lblCurp.gridy = 0;
		panel.add(lblCurp, gbc_lblCurp);
		
		cmbBusquedaTramite = new JComboBox();
		cmbBusquedaTramite.addActionListener(estilos.tranfiereElFoco);
		cmbBusquedaTramite.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--", "No. Tramite", "Curp"}));
		GridBagConstraints gbc_cmbBusquedaTramite = new GridBagConstraints();
		gbc_cmbBusquedaTramite.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBusquedaTramite.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBusquedaTramite.gridx = 1;
		gbc_cmbBusquedaTramite.gridy = 0;
		panel.add(cmbBusquedaTramite, gbc_cmbBusquedaTramite);
		
		JLabel lblNoTramite = new JLabel("No. Tramite:");
		GridBagConstraints gbc_lblNoTramite = new GridBagConstraints();
		gbc_lblNoTramite.insets = new Insets(0, 0, 5, 5);
		gbc_lblNoTramite.anchor = GridBagConstraints.EAST;
		gbc_lblNoTramite.gridx = 2;
		gbc_lblNoTramite.gridy = 0;
		panel.add(lblNoTramite, gbc_lblNoTramite);
		
		txtTramite = new JTextField();
		txtTramite.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtTramite = new GridBagConstraints();
		gbc_txtTramite.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTramite.insets = new Insets(0, 0, 5, 5);
		gbc_txtTramite.gridx = 3;
		gbc_txtTramite.gridy = 0;
		panel.add(txtTramite, gbc_txtTramite);
		txtTramite.setColumns(10);
		
		JLabel lblCurp_1 = new JLabel("Curp:");
		GridBagConstraints gbc_lblCurp_1 = new GridBagConstraints();
		gbc_lblCurp_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblCurp_1.anchor = GridBagConstraints.EAST;
		gbc_lblCurp_1.gridx = 4;
		gbc_lblCurp_1.gridy = 0;
		panel.add(lblCurp_1, gbc_lblCurp_1);
		
		txtCurp = new JTextField();
		txtCurp.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtCurp = new GridBagConstraints();
		gbc_txtCurp.insets = new Insets(0, 0, 5, 0);
		gbc_txtCurp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCurp.gridx = 5;
		gbc_txtCurp.gridy = 0;
		panel.add(txtCurp, gbc_txtCurp);
		txtCurp.setColumns(10);
		
		JLabel lblNombres = new JLabel("Nombre(s):");
		GridBagConstraints gbc_lblNombres = new GridBagConstraints();
		gbc_lblNombres.anchor = GridBagConstraints.WEST;
		gbc_lblNombres.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombres.gridx = 0;
		gbc_lblNombres.gridy = 1;
		panel.add(lblNombres, gbc_lblNombres);
		
		txtNombre = new JTextField();
		txtNombre.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtNombre = new GridBagConstraints();
		gbc_txtNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNombre.gridwidth = 5;
		gbc_txtNombre.insets = new Insets(0, 0, 5, 0);
		gbc_txtNombre.gridx = 1;
		gbc_txtNombre.gridy = 1;
		panel.add(txtNombre, gbc_txtNombre);
		txtNombre.setColumns(42);
		
		JLabel lblApellidoPaterno = new JLabel("Apellido Paterno:");
		GridBagConstraints gbc_lblApellidoPaterno = new GridBagConstraints();
		gbc_lblApellidoPaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoPaterno.gridx = 0;
		gbc_lblApellidoPaterno.gridy = 2;
		panel.add(lblApellidoPaterno, gbc_lblApellidoPaterno);
		
		txtApellidoPaterno = new JTextField();
		txtApellidoPaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoPaterno = new GridBagConstraints();
		gbc_txtApellidoPaterno.gridwidth = 2;
		gbc_txtApellidoPaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoPaterno.insets = new Insets(0, 0, 5, 5);
		gbc_txtApellidoPaterno.gridx = 1;
		gbc_txtApellidoPaterno.gridy = 2;
		panel.add(txtApellidoPaterno, gbc_txtApellidoPaterno);
		txtApellidoPaterno.setColumns(15);
		
		JLabel lblApellidoMaterno = new JLabel("Apellido Materno:");
		GridBagConstraints gbc_lblApellidoMaterno = new GridBagConstraints();
		gbc_lblApellidoMaterno.anchor = GridBagConstraints.EAST;
		gbc_lblApellidoMaterno.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidoMaterno.gridx = 3;
		gbc_lblApellidoMaterno.gridy = 2;
		panel.add(lblApellidoMaterno, gbc_lblApellidoMaterno);
		
		txtApellidoMaterno = new JTextField();
		txtApellidoMaterno.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtApellidoMaterno = new GridBagConstraints();
		gbc_txtApellidoMaterno.insets = new Insets(0, 0, 5, 0);
		gbc_txtApellidoMaterno.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtApellidoMaterno.gridwidth = 2;
		gbc_txtApellidoMaterno.gridx = 4;
		gbc_txtApellidoMaterno.gridy = 2;
		panel.add(txtApellidoMaterno, gbc_txtApellidoMaterno);
		txtApellidoMaterno.setColumns(15);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.gridx = 5;
		gbc_btnBuscar.gridy = 3;
		panel.add(btnBuscar, gbc_btnBuscar);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(new TitledBorder(null, "Tramites", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		frmBusqueda.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("I", null, panel_2, null);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 84, 0, 84, 0, 84, 103};
		gbl_panel_2.rowHeights = new int[]{0, 0, 52, 67, 50, 39, 11};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblCaso = new JLabel("Caso:");
		GridBagConstraints gbc_lblCaso = new GridBagConstraints();
		gbc_lblCaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblCaso.anchor = GridBagConstraints.EAST;
		gbc_lblCaso.gridx = 1;
		gbc_lblCaso.gridy = 2;
		panel_2.add(lblCaso, gbc_lblCaso);
		
		
		String[] lista={"municipios","tecate","tijuana","ensenada","tecate"};
		JComboBox comboBox = new JComboBox(lista);
		//static JComboBox municipios = new JComboBox(lista );
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 2;
		panel_2.add(comboBox, gbc_comboBox);
		
		JLabel lblDescripcinDeCaso = new JLabel("Descripci\u00F3n:");
		lblDescripcinDeCaso.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblDescripcinDeCaso = new GridBagConstraints();
		gbc_lblDescripcinDeCaso.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc_lblDescripcinDeCaso.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescripcinDeCaso.gridx = 1;
		gbc_lblDescripcinDeCaso.gridy = 3;
		panel_2.add(lblDescripcinDeCaso, gbc_lblDescripcinDeCaso);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 2;
		gbc_scrollPane.gridy = 3;
		gbc_scrollPane.gridwidth = 6;
		panel_2.add(scrollPane, gbc_scrollPane);
		
		JTextArea txtDescripcion = new JTextArea();
		scrollPane.setViewportView(txtDescripcion);
		
		JLabel lblCostoTotal = new JLabel("Costo Total:");
		GridBagConstraints gbc_lblCostoTotal = new GridBagConstraints();
		gbc_lblCostoTotal.anchor = GridBagConstraints.EAST;
		gbc_lblCostoTotal.insets = new Insets(0, 0, 5, 5);
		gbc_lblCostoTotal.gridx = 1;
		gbc_lblCostoTotal.gridy = 4;
		panel_2.add(lblCostoTotal, gbc_lblCostoTotal);
		
		txtCostoTotal = new JTextField();
		txtCostoTotal.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtCostoTotal = new GridBagConstraints();
		gbc_txtCostoTotal.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtCostoTotal.insets = new Insets(0, 0, 5, 5);
		gbc_txtCostoTotal.gridx = 2;
		gbc_txtCostoTotal.gridy = 4;
		panel_2.add(txtCostoTotal, gbc_txtCostoTotal);
		txtCostoTotal.setColumns(4);
		
		JLabel lblTipoDePago = new JLabel("Tipo de Pago:");
		GridBagConstraints gbc_lblTipoDePago = new GridBagConstraints();
		gbc_lblTipoDePago.anchor = GridBagConstraints.EAST;
		gbc_lblTipoDePago.insets = new Insets(0, 0, 5, 5);
		gbc_lblTipoDePago.gridx = 5;
		gbc_lblTipoDePago.gridy = 4;
		panel_2.add(lblTipoDePago, gbc_lblTipoDePago);
		
		JComboBox cmbTipoPago = new JComboBox();
		cmbTipoPago.setModel(new DefaultComboBoxModel(new String[] {"Contado", "Pagos"}));
		GridBagConstraints gbc_cmbTipoPago = new GridBagConstraints();
		gbc_cmbTipoPago.insets = new Insets(0, 0, 5, 5);
		gbc_cmbTipoPago.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbTipoPago.gridx = 6;
		gbc_cmbTipoPago.gridy = 4;
		panel_2.add(cmbTipoPago, gbc_cmbTipoPago);
		
		JLabel lblNewLabel = new JLabel("Anticipo:");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		panel_2.add(lblNewLabel, gbc_lblNewLabel);
		
		txtAnticipo = new JTextField();
		txtAnticipo.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtAnticipo = new GridBagConstraints();
		gbc_txtAnticipo.anchor = GridBagConstraints.NORTH;
		gbc_txtAnticipo.insets = new Insets(0, 0, 5, 5);
		gbc_txtAnticipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtAnticipo.gridx = 2;
		gbc_txtAnticipo.gridy = 5;
		panel_2.add(txtAnticipo, gbc_txtAnticipo);
		txtAnticipo.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha Anticipo:");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 5;
		gbc_lblNewLabel_1.gridy = 5;
		panel_2.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		txtFechaAnticipo = new JTextField();
		txtFechaAnticipo.addActionListener(estilos.tranfiereElFoco);
		GridBagConstraints gbc_txtFechaAnticipo = new GridBagConstraints();
		gbc_txtFechaAnticipo.anchor = GridBagConstraints.NORTH;
		gbc_txtFechaAnticipo.insets = new Insets(0, 0, 5, 5);
		gbc_txtFechaAnticipo.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtFechaAnticipo.gridx = 6;
		gbc_txtFechaAnticipo.gridy = 5;
		panel_2.add(txtFechaAnticipo, gbc_txtFechaAnticipo);
		txtFechaAnticipo.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Observaciones:");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 6;
		panel_2.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 2;
		gbc_scrollPane_1.gridy = 6;
		gbc_scrollPane_1.gridwidth= 6;
		panel_2.add(scrollPane_1, gbc_scrollPane_1);
		
		JTextArea txtObservaciones = new JTextArea();
		scrollPane_1.setViewportView(txtObservaciones);
		
		return frmBusqueda;	
	}

}
