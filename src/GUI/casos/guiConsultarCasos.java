package GUI.casos;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JComboBox;
import java.awt.GridBagConstraints;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

import bd.DBConsultarCasos;
import bd.DBConsultarEtapas;
import bd.DBConsultarTramite;

import GUI.estilos;

public class guiConsultarCasos extends JInternalFrame implements ActionListener {

	private JInternalFrame frmConsultarCasos;
	private JTextField txtBusqueda;
	private JTable tablaEtapas;
	private JTable tablaCasos;
	private JButton btnBuscar;
	private JComboBox cmbSeleccionar;
	
	MiModelo3 dtmCasos;
	MiModelo3 dtmEtapas;
	Object [][] vConsultaCasos;
	Object [][] vConsultaEtapas;
	
	String[] columNamesCasos  = {"Id Caso","Nombre Caso","Precio", "Descripcion"};
	String[] columNamesEtapas = {"idCaso","idEtapa","Nombre","Descripcion"};
	
	private JScrollPane spCasos;
	private JScrollPane spEtapas;
	private int filaCaso = -1;
	private int columnaCaso;
		
	private int filaEtapa = -1;
	private int columnaEtapa;

	/*public static void main(String[] args) {
		estilos obj = new estilos();
		obj.aplicar();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiConsultarCasos window = new guiConsultarCasos();
					window.frmConsultarCasos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	
	public guiConsultarCasos() {
		//initialize();
	}
	
	public void actionPerformed(ActionEvent arg0) 
	{
		
			if(arg0.getSource() == cmbSeleccionar)
			{
				if(cmbSeleccionar.getSelectedItem() == "Todos")
				{
					txtBusqueda.setText("");
					txtBusqueda.setEnabled(false);
				}
				else
					txtBusqueda.setEnabled(true);
			}
			
			if(arg0.getActionCommand() == "Buscar") 
			{
				if(cmbSeleccionar.getSelectedItem() == "--Seleccionar--")
					JOptionPane.showMessageDialog(this, "Debe seleccionar el tipo de busqueda", "Error consultar Casos", JOptionPane.INFORMATION_MESSAGE );
				else if(cmbSeleccionar.getSelectedItem() != "Todos" && txtBusqueda.getText().trim().length() == 0)
					JOptionPane.showMessageDialog(this, "Debe proporcionar el parametro de busqueda", "Error consultar Casos", JOptionPane.INFORMATION_MESSAGE );
				else
				{
					filaCaso = -1;
					columnaCaso = -1;
					String sWhere = "";
					String sModalidad = "";
					DBConsultarCasos objConsultar = new DBConsultarCasos();
					
					if(cmbSeleccionar.getSelectedItem() == "Clave")
					{
						sWhere = " idCaso = '"+ txtBusqueda.getText().trim() + "'"; 
						sModalidad = "Casos";
						
					}else if(cmbSeleccionar.getSelectedItem() == "Nombre Caso")
					{
						sWhere = " nombreCaso LIKE '"+ txtBusqueda.getText().trim() + "%'";
						sModalidad = "Casos";
						
					}else if(cmbSeleccionar.getSelectedItem() == "Todos")
					{
						sModalidad = "Tramite";
					}
					
					vConsultaCasos = objConsultar.consultarCasos(sModalidad, sWhere);
					int iCuenta;
					int iContador = vConsultaCasos.length;
					
					dtmCasos = new MiModelo3(vConsultaCasos, columNamesCasos);
					tablaCasos.setModel(dtmCasos);
					if(iContador == 0)
					{
						vConsultaCasos = new Object [0][0];
						dtmCasos = new MiModelo3(vConsultaCasos, columNamesCasos);
						tablaCasos.setModel(dtmCasos);
						//limpiaCampos();
						vConsultaEtapas = new Object [0][0];
						dtmEtapas = new MiModelo3(vConsultaEtapas, columNamesEtapas);
						tablaEtapas.setModel(dtmEtapas);
						JOptionPane.showMessageDialog(this, "No se ha podido Encontrar Caso", "Error consultar Casos", JOptionPane.INFORMATION_MESSAGE );
					}
					else
					{
						for(iCuenta = 0; iCuenta < vConsultaCasos.length; iCuenta++ )
						{
							System.out.println("y¿tamaño columna " + vConsultaCasos[iCuenta].length);
							for(int iCuentaAux = 0; iCuentaAux < vConsultaCasos[iCuenta].length;iCuentaAux++)
							{
								System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaCasos[iCuenta][iCuentaAux].toString());
							}
						}
						//MouseEvent e = new MouseEvent(spCasos, iContador, ABORT, iCuenta, columnaCaso, columnaCaso, iContador, closable);
						tablaCasos.setRowSelectionInterval(0,0);
						filaCaso = tablaCasos.getSelectedRow();
						columnaCaso = tablaCasos.getSelectedColumn();
						tablaCasos.requestFocus();
						consultaEtapas(filaCaso,columnaCaso);
						//llenaCampos();
					}
					
				
					
				}
			}
	}
	
	public void consultaEtapas(int fila, int columna)
	{
		System.out.println("consulta etapa");
		if ((filaCaso == -1) && (columnaCaso == -1))
		{
			filaCaso = 0; 
			columnaCaso = 0;
		}
		else
		{
			filaCaso    = fila; 
			columnaCaso = columna;
		}
		    
		
			String cIDCaso = String.valueOf(tablaCasos.getValueAt(filaCaso,0));
			System.out.println("curp " + cIDCaso);
			
			String sCount;
			String sConsulta;
			
			sCount    = "SELECT count(1) as cont FROM etapas" + " WHERE idCaso  ='"+cIDCaso+"'";
			sConsulta = "SELECT * FROM etapas WHERE idCaso  ='"+cIDCaso+"'";				
			
			DBConsultarEtapas objConsultar = new DBConsultarEtapas();
			vConsultaEtapas = objConsultar.consultarEtapas(sConsulta, sCount);
			int iCuenta;
			int iContador = vConsultaEtapas.length;
			
			//if(iContador == 0) 	limpiaCampos();
			
			for(iCuenta = 0; iCuenta < vConsultaEtapas.length; iCuenta++ )
			{
				System.out.println("y¿tamaño columna " + vConsultaEtapas[iCuenta].length);
				for(int iCuentaAux = 0; iCuentaAux < vConsultaEtapas[iCuenta].length;iCuentaAux++)
				{
					System.out.println("Dato de Cuenta " + iCuenta + " dato " + iCuentaAux + " Valor " + vConsultaEtapas[iCuenta][iCuentaAux].toString());
				}
			}
			dtmEtapas = new MiModelo3(vConsultaEtapas, columNamesEtapas);
			tablaEtapas.setModel(dtmEtapas);		
							   
			
	}


	public JInternalFrame initialize() {
		frmConsultarCasos = new JInternalFrame("Consultar Casos y Etapas",false,true,true,true);
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");		
		frmConsultarCasos.setFrameIcon(new ImageIcon(URLIcon));
		frmConsultarCasos.setBounds(100, 100, 600, 500);
		frmConsultarCasos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 221, 137, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 143, 131, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		frmConsultarCasos.getContentPane().setLayout(gridBagLayout);
		
		cmbSeleccionar = new JComboBox();
		cmbSeleccionar.setModel(new DefaultComboBoxModel(new String[] {"--Seleccionar--", "Todos","Clave", "Nombre Caso"}));
		GridBagConstraints gbc_cmbSeleccionar = new GridBagConstraints();
		gbc_cmbSeleccionar.anchor = GridBagConstraints.EAST;
		gbc_cmbSeleccionar.insets = new Insets(0, 0, 5, 5);
		gbc_cmbSeleccionar.gridx = 1;
		gbc_cmbSeleccionar.gridy = 0;
		cmbSeleccionar.addActionListener(this);
		frmConsultarCasos.getContentPane().add(cmbSeleccionar, gbc_cmbSeleccionar);
		
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
		gbc_txtBusqueda.gridx = 2;
		gbc_txtBusqueda.gridy = 0;
		frmConsultarCasos.getContentPane().add(txtBusqueda, gbc_txtBusqueda);
		txtBusqueda.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		GridBagConstraints gbc_btnBuscar = new GridBagConstraints();
		gbc_btnBuscar.anchor = GridBagConstraints.WEST;
		gbc_btnBuscar.insets = new Insets(0, 0, 5, 5);
		gbc_btnBuscar.gridx = 3;
		gbc_btnBuscar.gridy = 0;
		frmConsultarCasos.getContentPane().add(btnBuscar, gbc_btnBuscar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Casos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.gridwidth = 5;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		frmConsultarCasos.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{145, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panel.add(scrollPane, gbc_scrollPane);
		
		tablaCasos = new JTable();
		tablaCasos.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spCasos = new JScrollPane(tablaCasos);
		vConsultaCasos = new Object [0][0];
		dtmCasos = new MiModelo3(vConsultaCasos, columNamesCasos);
		tablaCasos.setModel(dtmCasos);
		tablaCasos.addMouseListener(new MouseAdapter()
		{    
			
			public void mouseClicked(MouseEvent e)
			{ 
				filaCaso = tablaCasos.rowAtPoint(e.getPoint()); 
				columnaCaso = tablaCasos.columnAtPoint(e.getPoint());
				consultaEtapas(filaCaso, columnaCaso);
				//llenaCampos();
			}
		});
		
		tablaCasos.addKeyListener(new KeyAdapter(){
			public void keyReleased(KeyEvent e)
			{
				if((e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN))
				{
					filaCaso = tablaCasos.getSelectedRow();
					columnaCaso = tablaCasos.getSelectedColumn();
					consultaEtapas(filaCaso, columnaCaso);			
				}
			}
		});

		scrollPane.setViewportView(tablaCasos);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Etapas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 5;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		frmConsultarCasos.getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{145, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 0;
		panel_1.add(scrollPane_1, gbc_scrollPane_1);
		
		tablaEtapas = new JTable();
		tablaEtapas.setPreferredScrollableViewportSize(new Dimension(150, 120));
		spEtapas = new JScrollPane(tablaEtapas);
		vConsultaEtapas = new Object [0][0];
		dtmEtapas = new MiModelo3(vConsultaEtapas, columNamesEtapas);
		tablaEtapas.setModel(dtmEtapas);
		tablaEtapas.addMouseListener(new MouseAdapter(){     
			public void mouseClicked(MouseEvent e){ 
				filaEtapa = tablaEtapas.rowAtPoint(e.getPoint()); 
				System.out.println("fila " + filaEtapa);
				columnaEtapa = tablaEtapas.columnAtPoint(e.getPoint());    
				if ((filaEtapa > -1) && (columnaEtapa > -1))
				{
														   
				}
			}
		});
		scrollPane_1.setViewportView(spEtapas);
		
		return frmConsultarCasos;
	}

}
class MiModelo3 extends DefaultTableModel
{
   public boolean isCellEditable (int row, int column)
   {
      return false;
   }
   public MiModelo3(Object[][] data, Object[] columnNames)
   {
	   super( data, columnNames);
   }
}