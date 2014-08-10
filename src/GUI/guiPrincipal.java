package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.BorderLayout;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JDesktopPane;

import complemento.pinta_escritorio;

import Agenda.guiAgenda;
import Agenda.guiAgendaConsulta;
import Agenda.guiAgendaModificar;
import GUI.Pagos.guiPagos;
import GUI.casos.guiCasos;
import GUI.casos.guiConsultarCasos;
import GUI.casos.guiModificarCasos;
import GUI.cliente.guiAgregarClientes;
import GUI.cliente.guiBusqueda;
import GUI.cliente.guiBusquedaClientes;
import GUI.tramite.guiAgregarTramite;
import GUI.tramite.guiBusquedaTramite;

public class guiPrincipal extends JFrame implements ActionListener, ItemListener{

	private JFrame frmDespachoJuridicoEr;
	private JDesktopPane desktop;
	File fondo=new File("C:/FONDO/fondo.png");	

	/**
	 * Launch the application.
	 */
		
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					guiPrincipal window = new guiPrincipal();
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					window.frmDespachoJuridicoEr.setSize(screenSize);
					window.frmDespachoJuridicoEr.setLocation(0,0);
					window.frmDespachoJuridicoEr.setAlwaysOnTop(false);
					window.frmDespachoJuridicoEr.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		//guiPrincipal objPrincipal = new guiPrincipal(); 
	}

	/**
	 * Create the application.
	 */
	public guiPrincipal() {
		
		
		
		initialize();
		cargarImagen(desktop, fondo);
				
	}
	public  void cargarImagen(JDesktopPane jDeskp,File fileImagen)
	{
	    try{
	        BufferedImage image = ImageIO.read(fileImagen);
	        jDeskp.setBorder(new pinta_escritorio()); }
	        catch (Exception e)
	        {
	        	System.out.println("No cargo imagen, sorry");
	        }
	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
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
		addKeyListener(new manejadorTeclado());
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
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		
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
class manejadorTeclado implements KeyListener
{	
	public void keyPressed(KeyEvent arg0) 
	{		
		System.out.println("keypress " + arg0.getKeyChar());
	}
	
	
	public void keyReleased(KeyEvent arg0) 
	{
		System.out.println("release " + arg0.KEY_FIRST);		
	}

	
	public void keyTyped(KeyEvent arg0) 
	{
		System.out.println("typed " + arg0.KEY_FIRST);		
		System.out.println("getKeyChar " + arg0.getKeyChar());
		System.out.println("getKeyCode " + arg0.getKeyCode());
		System.out.println("paramString " + arg0.paramString());
		System.out.println("toString " + arg0.toString());
		System.out.println("getID " + arg0.getID());
		System.out.println("getModifiers " + arg0.getModifiers());
		System.out.println("getModifiersEx " + arg0.getModifiersEx());
		System.out.println("hashCode " + arg0.hashCode());
		System.out.println("getSource " + arg0.getSource().toString());
	}
}
