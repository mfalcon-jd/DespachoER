package GUI.login;
import Agenda.guiAgenda;
import Agenda.guiAgendaConsulta;
import Agenda.guiAgendaModificar;
import GUI.Pagos.guiPagos;
import GUI.casos.guiCasos;
import GUI.casos.guiConsultarCasos;
import GUI.casos.guiModificarCasos;
import GUI.cliente.guiAgregarClientes;
import GUI.cliente.guiBusquedaClientes;
import GUI.tramite.guiAgregarTramite;
import GUI.tramite.guiBusquedaTramite;
import bd.DbEstudiante;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class frmMain extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	private JFrame frmDespachoJuridicoEr;
	private JDesktopPane desktop;
	
	//private JMenuBar barramenu;
	/*JFrame frame;*/
	/*JScrollPane spEstudiantes,spNotas; 
	DefaultTableModel dtmEstudiantes,dtmNotas;
	JTable jtbEstudiantes,jtbNotas;   
	JPanel pnlEstudiantes;   
	JLabel lblCodigo,lblNombre;
	JTextField txtCodigo,txtNombre;   
	Object[][] dtEstudiantes;
	Object[][] dtNotas;
	DbEstudiante us = new DbEstudiante();
	int fila = -1;	*/
	
	public frmMain()
	{
		super("SISTEMA DE ESTUDIANTES");
	}
	
	public void mostrarForm(boolean mostrar){
		//Container contenedor = getContentPane();
		//contenedor.setLayout(new BorderLayout());
		
		if (mostrar == true)
		{
			/*barramenu = new JMenuBar(); 
			JMenu menuArchivo = new JMenu("Configuración y Mantenimiento");
			menuArchivo.setMnemonic('C');
						
			JMenuItem itemAcerca = new JMenuItem("Acerca de...");
			itemAcerca.setMnemonic('A');
			menuArchivo.add(itemAcerca);
			itemAcerca.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent evento)
						{
							JOptionPane.showMessageDialog(frmMain.this,
									"Ejemplo Login",
									"Acerca de", JOptionPane.INFORMATION_MESSAGE);
						}
					} 
			);
			JMenuItem itemSalir = new JMenuItem("Salir");
			itemSalir.setMnemonic('S');
			menuArchivo.add(itemSalir);
			itemSalir.addActionListener(
					new ActionListener(){
						public void actionPerformed(ActionEvent evento)
						{
							System.exit(0);
						}
					}
			);
			barramenu.add(menuArchivo);  
			setJMenuBar(barramenu);
			setSize(600, 400);
			setLocationRelativeTo(this.getParent());
			setVisible(mostrar);
			setDefaultCloseOperation(EXIT_ON_CLOSE);*/
			
			/*frame = new JFrame("Notas de estudiantes");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
			/*dtEstudiantes = us.getEstudiantes(); 
			String[] columNames = {"Matrícula","Nombres"}; 
			dtmEstudiantes= new DefaultTableModel(dtEstudiantes, columNames);  
			String[] columNamesNotas = {"Código","Materia","Calificación"};  
			dtmNotas = new DefaultTableModel(dtNotas,columNamesNotas);   
			jtbEstudiantes = new JTable(dtmEstudiantes); 
			jtbEstudiantes.setPreferredScrollableViewportSize(new Dimension(500, 120));
			jtbNotas = new JTable(dtmNotas);    
			jtbNotas.setPreferredScrollableViewportSize(new Dimension(500,80));   
			spEstudiantes = new JScrollPane(jtbEstudiantes);  
			spNotas = new JScrollPane(jtbNotas);
			lblCodigo = new JLabel("Codigo");  
			lblNombre = new JLabel("Nombre");   
			txtCodigo = new JTextField(10); 
			txtNombre = new JTextField(10);
			pnlEstudiantes = new JPanel(new GridLayout(2,2));
			pnlEstudiantes.add(lblCodigo);    
			pnlEstudiantes.add(txtCodigo);  
			pnlEstudiantes.add(lblNombre);  
			pnlEstudiantes.add(txtNombre);    
			jtbEstudiantes.addMouseListener(new MouseAdapter(){     
				public void mouseClicked(MouseEvent e){ 
					fila = jtbEstudiantes.rowAtPoint(e.getPoint()); 
					int columna = jtbEstudiantes.columnAtPoint(e.getPoint());    
					if ((fila > -1) && (columna > -1)){
						txtCodigo.setText(String.valueOf(jtbEstudiantes.getValueAt(fila,0)));
						txtNombre.setText(String.valueOf(jtbEstudiantes.getValueAt(fila,1)));
						dtNotas = us.getNotasByEstudiante(String.valueOf(jtbEstudiantes.getValueAt(fila,0)));
						int contRows = dtmNotas.getRowCount();
						for(int i=0;i<contRows;i++){
							dtmNotas.removeRow(0);
							
						} 
						for(int i=0;i<dtNotas.length;i++){
							Object[] newRow={dtNotas[i][0],dtNotas[i][1],dtNotas[i][2]};
							dtmNotas.addRow(newRow); 
							}    
						}
					}
				});
			contenedor.add(spEstudiantes,BorderLayout.NORTH);
			contenedor.add(pnlEstudiantes,BorderLayout.CENTER);
			contenedor.add(spNotas,BorderLayout.SOUTH);*/
			
			/*frame.getContentPane().add(spEstudiantes,BorderLayout.NORTH);
			frame.getContentPane().add(pnlEstudiantes,BorderLayout.CENTER); 
			frame.getContentPane().add(spNotas,BorderLayout.SOUTH);  
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(frame.getParent());   
			frame.setVisible(true);*/
			frmDespachoJuridicoEr = new JFrame();		
			frmDespachoJuridicoEr.setResizable(false);
			frmDespachoJuridicoEr.setTitle("Despacho Juridico ER y Asociados");
			frmDespachoJuridicoEr.setBounds(100, 100, 800, 600);
			frmDespachoJuridicoEr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmDespachoJuridicoEr.getContentPane().setLayout(new BorderLayout(0, 0));		
			JPanel panel_1 = new JPanel();
			frmDespachoJuridicoEr.getContentPane().add(panel_1, BorderLayout.CENTER);
			panel_1.setLayout(new GridLayout(1, 0, 0, 0));
			
			JDesktopPane desktopPane = new JDesktopPane();
			panel_1.add(desktopPane);
			
			
			
			JMenuBar menuBar = new JMenuBar();
			frmDespachoJuridicoEr.setJMenuBar(menuBar);
			
			JMenu mnOperaciones = new JMenu("Operaciones");
			menuBar.add(mnOperaciones);
			
			JMenuItem mntmAgregarCliente = new JMenuItem("Agregar Cliente");
			mntmAgregarCliente.addActionListener(this);
			mnOperaciones.add(mntmAgregarCliente);
			//mntmAgregarCliente.addActionListener(this);
			mntmAgregarCliente.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                guiAgregarClientes miVentana2 = new guiAgregarClientes(){{setVisible(true);}};
	            }
	        });
			
			JMenuItem mntmAgregarTramite = new JMenuItem("Agregar Tramite");
			mntmAgregarTramite.addActionListener(this);
			mnOperaciones.add(mntmAgregarTramite);
			
			JMenuItem mntmEstadoDeCuenta = new JMenuItem("Estado de Cuenta");
			mnOperaciones.add(mntmEstadoDeCuenta);
			
			JMenu mnCatalogos = new JMenu("Catalogos");
			menuBar.add(mnCatalogos);
			
			JMenuItem mntmCasosYEtapas = new JMenuItem("Nuevo Casos y Etapas");
			mntmCasosYEtapas.addActionListener(this);
			mnCatalogos.add(mntmCasosYEtapas);
			
			JMenuItem mntmConsultarCasosYEtapas = new JMenuItem("Consultar Casos y Etapas");
			mntmConsultarCasosYEtapas.addActionListener(this);
			mnCatalogos.add(mntmConsultarCasosYEtapas);
			
			JMenuItem mntmModCasosYEtapas = new JMenuItem("Modificar Casos y Etapas");
			mntmModCasosYEtapas.addActionListener(this);
			mnCatalogos.add(mntmModCasosYEtapas);	
			
			JMenu mnConsultas = new JMenu("Consultas");
			menuBar.add(mnConsultas);
			
			JMenuItem mntmClientes = new JMenuItem("Clientes");
			mntmClientes.addActionListener(this);
			mnConsultas.add(mntmClientes);
			
			JMenuItem mntmTramitesPorCliente = new JMenuItem("Tramites por Cliente");
			mntmTramitesPorCliente.addActionListener(this);
			mnConsultas.add(mntmTramitesPorCliente);
			
			JMenuItem mntmEstadoDeCuenta_1 = new JMenuItem("Estado de Cuenta del Cliente");
			mntmEstadoDeCuenta_1.addActionListener(this);
			mnConsultas.add(mntmEstadoDeCuenta_1);
			
			JMenu mnCaja = new JMenu("Caja");
			menuBar.add(mnCaja);
			
			JMenuItem mntmPagar = new JMenuItem("Pagar");
			mnCaja.add(mntmPagar);
			
			JMenu mnReportes = new JMenu("Reportes");
			menuBar.add(mnReportes);
			
			JMenuItem mntmTramitesTotales = new JMenuItem("Tramites Totales");
			mnReportes.add(mntmTramitesTotales);
			
			JMenuItem mntmCasos = new JMenuItem("Casos");
			mnReportes.add(mntmCasos);
			
			JMenuItem mntmClientes_1 = new JMenuItem("Clientes");
			mnReportes.add(mntmClientes_1);
			
			JMenuItem mntmEstadoDeCuenta_2 = new JMenuItem("Estado de Cuenta  Empresa");
			mnReportes.add(mntmEstadoDeCuenta_2);
			
			JMenu mnAgenda = new JMenu("Agenda");
			menuBar.add(mnAgenda);
			
			JMenuItem mntmVer = new JMenuItem("Ver");
			mntmVer.addActionListener(this);
			mnAgenda.add(mntmVer);
			
			JMenuItem mntmAgregar = new JMenuItem("Agregar");
			mntmAgregar.addActionListener(this);
			mnAgenda.add(mntmAgregar);
			
			JMenuItem mntmModificar = new JMenuItem("Modificar");
			mntmModificar.addActionListener(this);
			mnAgenda.add(mntmModificar);
			
			JMenuItem mntmEliminar = new JMenuItem("Eliminar");
			mnAgenda.add(mntmEliminar);
			
			JMenu mnAcercaDe = new JMenu("Acerca de...");
			menuBar.add(mnAcercaDe);
			
			JMenuItem mntmSnapping = new JMenuItem("Snapping");
			mnAcercaDe.add(mntmSnapping);
			
			desktop = new JDesktopPane();
			frmDespachoJuridicoEr.getContentPane().add(desktop);
			
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			frmDespachoJuridicoEr.setVisible(true);
			frmDespachoJuridicoEr.setSize(screenSize);
			frmDespachoJuridicoEr.setLocation(0,0);
			frmDespachoJuridicoEr.setAlwaysOnTop(false);
			frmDespachoJuridicoEr.setVisible(true);
			
			/*JInternalFrame obj2 = (JInternalFrame) (new guiBusqueda().initialize());
			obj2.pack();
			desktop.add(obj2);
			obj2.setVisible(true);
			//guiBusqueda objBusqueda = new guiBusqueda();
			obj2.setLocation(50+10,50+10);
			obj2.setSize(700, 500);
			obj2.setBackground(Color.white);
			obj2.setVisible(true);
			
			
			
			desktop.moveToFront(obj2);*/
			//desktop.setVisible(true);
		
		}
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		System.out.println("modificar " + arg0.getActionCommand());
	
		if(arg0.getActionCommand() == "Cancelar") System.exit(0);
		
		if(arg0.getActionCommand() == "Agregar Cliente")
		{
			guiAgregarClientes objAgregarCliente = new guiAgregarClientes();
			JInternalFrame JInAgregarCliente = (objAgregarCliente.initialize());
			JInAgregarCliente.pack();
			desktop.removeAll();
			desktop.add(JInAgregarCliente);
			JInAgregarCliente.setVisible(true);
			//guiBusqueda objBusqueda = new guiBusqueda();
			
			JInAgregarCliente.setSize(600, 500);
			JInAgregarCliente.setBackground(Color.white);
			JInAgregarCliente.setVisible(true);
			
			objAgregarCliente.interfazInicial();
			desktop.moveToFront(JInAgregarCliente);
		}
		
		if(arg0.getActionCommand() == "Agregar Tramite")
		{	
			guiAgregarTramite objAgregarTramite = new guiAgregarTramite();
			JInternalFrame JInAgregartramite = (objAgregarTramite.initialize());
			JInAgregartramite.pack();
			desktop.removeAll();
			desktop.add(JInAgregartramite);
			JInAgregartramite.setVisible(true);
			//guiBusqueda objBusqueda = new guiBusqueda();
			
			JInAgregartramite.setSize(800, 600);
			JInAgregartramite.setBackground(Color.white);
			JInAgregartramite.setVisible(true);
			
			objAgregarTramite.cntrlCampos();
			objAgregarTramite.llenaComboCasos();
			
			desktop.moveToFront(JInAgregartramite);
		}
		if(arg0.getActionCommand() == "Clientes")
		{
			guiBusquedaClientes objBusquedaCliente = new guiBusquedaClientes();
			JInternalFrame JInternalBusquedaCliente = (JInternalFrame) (objBusquedaCliente.initialize());
			JInternalBusquedaCliente.pack();
			desktop.removeAll();
			desktop.add(JInternalBusquedaCliente);
			JInternalBusquedaCliente.setVisible(true);
			JInternalBusquedaCliente.setSize(800, 600);
			JInternalBusquedaCliente.setBackground(Color.white);
			JInternalBusquedaCliente.setVisible(true);
						
			desktop.moveToFront(JInternalBusquedaCliente);
			objBusquedaCliente.interfazInicial();
			//desktop.setVisible(true)
		}
		if(arg0.getActionCommand() == "Estado de Cuenta del Cliente")
		{
			guiPagos objPagos = new guiPagos();
			JInternalFrame JInternalPagos = objPagos.initialize();
			JInternalPagos.pack();
			desktop.removeAll();
			desktop.add(JInternalPagos);
			JInternalPagos.setVisible(true);
			JInternalPagos.setSize(800, 600);
			JInternalPagos.setBackground(Color.white);
			JInternalPagos.setVisible(true);
			
			
			
			desktop.moveToFront(JInternalPagos);
		}
		
		if(arg0.getActionCommand() == "Tramites por Cliente")
		{
			guiBusquedaTramite objBusquedaTramite = new guiBusquedaTramite();
			JInternalFrame JInteernalBusquedaCliente = objBusquedaTramite.initialize();
			JInteernalBusquedaCliente.pack();
			desktop.removeAll();
			desktop.add(JInteernalBusquedaCliente);
			JInteernalBusquedaCliente.setVisible(true);
			JInteernalBusquedaCliente.setSize(800, 600);
			JInteernalBusquedaCliente.setBackground(Color.white);
			JInteernalBusquedaCliente.setVisible(true);
			
			
			
			desktop.moveToFront(JInteernalBusquedaCliente);
			//desktop.setVisible(true);
			
		}	
		if(arg0.getActionCommand() == "Agregar")
		{
			guiAgenda objAgregarAgenda = new guiAgenda();
			JInternalFrame JInternalAgregarAgenda = (JInternalFrame) (objAgregarAgenda.initialize());
			JInternalAgregarAgenda.pack();
			desktop.removeAll();
			desktop.add(JInternalAgregarAgenda);
			JInternalAgregarAgenda.setVisible(true);
			JInternalAgregarAgenda.setSize(800, 600);
			JInternalAgregarAgenda.setBackground(Color.white);
			JInternalAgregarAgenda.setVisible(true);
						
			desktop.moveToFront(JInternalAgregarAgenda);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		if(arg0.getActionCommand() == "Modificar")
		{
			System.out.println("modificar");
			guiAgendaModificar objModificarAgenda = new guiAgendaModificar();
			JInternalFrame JInternalModificarAgenda = (JInternalFrame) (objModificarAgenda.initialize());
			JInternalModificarAgenda.pack();
			desktop.removeAll();
			desktop.add(JInternalModificarAgenda);
			JInternalModificarAgenda.setVisible(true);
			JInternalModificarAgenda.setSize(800, 600);
			JInternalModificarAgenda.setBackground(Color.white);
			JInternalModificarAgenda.setVisible(true);
						
			desktop.moveToFront(JInternalModificarAgenda);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Nuevo Casos y Etapas")
		{
			System.out.println("ver casos y etapas");
			guiCasos objCasos = new guiCasos();
			
			JInternalFrame JInternalCasos = (objCasos.initialize());
			JInternalCasos.pack();
			desktop.removeAll();
			desktop.add(JInternalCasos);
			JInternalCasos.setVisible(true);
			JInternalCasos.setSize(800, 600);
			JInternalCasos.setBackground(Color.white);
			JInternalCasos.setVisible(true);
						
			desktop.moveToFront(JInternalCasos);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Consultar Casos y Etapas")
		{

			guiConsultarCasos objConsultarCasos = new guiConsultarCasos();
			JInternalFrame JInternalConsultarCasos = (objConsultarCasos.initialize());
			JInternalConsultarCasos.pack();
			desktop.removeAll();
			desktop.add(JInternalConsultarCasos);
			JInternalConsultarCasos.setVisible(true);
			JInternalConsultarCasos.setSize(800, 600);
			JInternalConsultarCasos.setBackground(Color.white);
			JInternalConsultarCasos.setVisible(true);
						
			desktop.moveToFront(JInternalConsultarCasos);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Ver")
		{
			System.out.println("ver agenda");
			guiAgendaConsulta objAgendaConsulta = new guiAgendaConsulta();
			JInternalFrame JInternalAgendaconsulta = (objAgendaConsulta.initialize());
			JInternalAgendaconsulta.pack();
			desktop.removeAll();
			desktop.add(JInternalAgendaconsulta);
			JInternalAgendaconsulta.setVisible(true);
			JInternalAgendaconsulta.setSize(800, 600);
			JInternalAgendaconsulta.setBackground(Color.white);
			JInternalAgendaconsulta.setVisible(true);
						
			desktop.moveToFront(JInternalAgendaconsulta);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Modificar Casos y Etapas")
		{
			System.out.println("Modificar Casos y Etapas");
			guiModificarCasos objModificarCaso = new guiModificarCasos();
			JInternalFrame JInternalModificarCaso = (objModificarCaso.initialize());
			JInternalModificarCaso.pack();
			desktop.removeAll();
			desktop.add(JInternalModificarCaso);
			JInternalModificarCaso.setVisible(true);
			JInternalModificarCaso.setSize(800, 600);
			JInternalModificarCaso.setBackground(Color.white);
			JInternalModificarCaso.setVisible(true);
						
			desktop.moveToFront(JInternalModificarCaso);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}

	}

}
