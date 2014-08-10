package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import complemento.Fecha;
import complemento.ImagenFondo;
import complemento.wallpaper;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;

import reporteador.EstadoViewerEmpresa;
import reporteador.SampleViewerFrame;
import reporteador.TramitesViewer;

import Agenda.guiAgenda;
import Agenda.guiAgendaConsulta;
import Agenda.guiAgendaModificar;
import GUI.Pagos.guiPagos;
import GUI.casos.guiCasos;
import GUI.casos.guiConsultarCasos;
import GUI.casos.guiModificarCasos;
import GUI.cliente.guiAgregarClientes;
import GUI.cliente.guiBusquedaClientes;
//import GUI.configuracion.guiConfiguracion;
import GUI.login.AdminUsuario;
import GUI.login.Imagen;
import GUI.login.frmLogin;
import GUI.reportes.frmClientes;
import GUI.reportes.frmEstadoCuenta;
import GUI.tramite.guiAgregarTramite;
import GUI.tramite.guiBusquedaTramite;
import reporteador.CasosViewer;
import complemento.pinta_escritorio;

public class guiPrincipalNew  extends JFrame implements ActionListener, ItemListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frmDespachoJuridicoEr;
	private JDesktopPane desktop;
	
	public guiPrincipalNew()
	{
		
	}
		
	
	public void inicializar()
	{
		String cTipoUsuario = frmLogin.getTipoUsuario();
		frmDespachoJuridicoEr = new JFrame();	
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");
		System.out.println("URLIcon: " + URLIcon);
		frmDespachoJuridicoEr.setIconImage(Toolkit.getDefaultToolkit().getImage(URLIcon));
		frmDespachoJuridicoEr.setResizable(false);
		frmDespachoJuridicoEr.setTitle("AdLaw --- Despacho Juridico ER y Asociados");
		frmDespachoJuridicoEr.setBounds(100, 100, 800, 600);
		
		frmDespachoJuridicoEr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDespachoJuridicoEr.getContentPane().setLayout(new BorderLayout(0, 0));								
		
		JMenuBar menuBar = new JMenuBar();
		frmDespachoJuridicoEr.setJMenuBar(menuBar);		
		
		JMenu mnSistema = new JMenu("Sistema");
		menuBar.add(mnSistema);				
		
		JMenuItem mntmEmpresa = new JMenuItem("Empresa");
		mntmEmpresa.addActionListener(this);
		mnSistema.add(mntmEmpresa);
		
		JMenu mnAdminCuentas = new JMenu("Administración de Cuentas");
		mnSistema.add(mnAdminCuentas);
		
		JMenuItem mnCambiarPassword = new JMenuItem("Cambiar Contraseña");
		mnCambiarPassword.addActionListener(this);
		mnAdminCuentas.add(mnCambiarPassword);
		
		JMenuItem mnAgregarUsuario = new JMenuItem("Agregar Usuario");
		mnAgregarUsuario.addActionListener(this);
		mnAdminCuentas.add(mnAgregarUsuario);	
		
		JMenu mnCatalogos = new JMenu("Catalogos");
		menuBar.add(mnCatalogos);
		
		JMenu mntmClientes2 = new JMenu("Clientes");
		mnCatalogos.add(mntmClientes2);
		
		JMenuItem mntmAgregarCliente = new JMenuItem("Nuevo Cliente");
		mntmAgregarCliente.addActionListener(this);
		mntmClientes2.add(mntmAgregarCliente);
		
		if(cTipoUsuario.equals("1") )mntmAgregarCliente.setVisible(true);
		else if(cTipoUsuario.equals("2") )mntmAgregarCliente.setVisible(false);
		
		JMenuItem mntmClientes = new JMenuItem("Modificar / Consultar Cliente");
		mntmClientes.addActionListener(this);
		mntmClientes2.add(mntmClientes);
		
		JMenu mntmCasos2 = new JMenu("Casos");
		mnCatalogos.add(mntmCasos2);
					
		JMenuItem mntmCasosYEtapas = new JMenuItem("Nuevo Caso");
		mntmCasosYEtapas.addActionListener(this);
		mntmCasos2.add(mntmCasosYEtapas);
		
		JMenuItem mntmModCasosYEtapas = new JMenuItem("Modificar Caso");
		mntmModCasosYEtapas.addActionListener(this);
		mntmCasos2.add(mntmModCasosYEtapas);										
		
		JMenu mnOperaciones = new JMenu("Operaciones");
		menuBar.add(mnOperaciones);				
		
		JMenuItem mntmAgregarTramite = new JMenuItem("Agregar Tramite");
		mntmAgregarTramite.addActionListener(this);
		mnOperaciones.add(mntmAgregarTramite);
		
		JMenuItem mntmPagos = new JMenuItem("Pagos");
		mntmPagos.addActionListener(this);
		mnOperaciones.add(mntmPagos);										
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);		

		JMenuItem mntmConsultarCasosYEtapas = new JMenuItem("Casos y Etapas");
		mntmConsultarCasosYEtapas.addActionListener(this);
		mnConsultas.add(mntmConsultarCasosYEtapas);
		
		JMenuItem mntmTramitesPorCliente = new JMenuItem("Tramites por Cliente");
		mntmTramitesPorCliente.addActionListener(this);
		mnConsultas.add(mntmTramitesPorCliente);
		
		JMenuItem mntmEstadoDeCuenta_1 = new JMenuItem("Estado de Cuenta del Cliente");
		mntmEstadoDeCuenta_1.addActionListener(this);
		mnConsultas.add(mntmEstadoDeCuenta_1);		
		
		JMenu mnReportes = new JMenu("Reportes");
		menuBar.add(mnReportes);
		
		JMenuItem mntmTramitesTotales = new JMenuItem("Tramites Clientes");		
		mntmTramitesTotales.addActionListener(this);
		mnReportes.add(mntmTramitesTotales);
		
		JMenuItem mntmCasos = new JMenuItem("Casos");
		mnReportes.add(mntmCasos);
		mntmCasos.addActionListener(this);
		
		JMenuItem mntmClientes_1 = new JMenuItem("Clientes");
		mntmClientes_1.addActionListener(this);
		mnReportes.add(mntmClientes_1);
		
		JMenuItem mntmEstadoDeCuenta_2 = new JMenuItem("Estado de Cuenta Empresa");
		mnReportes.add(mntmEstadoDeCuenta_2);
		mntmEstadoDeCuenta_2.addActionListener(this);
		
		JMenuItem mntmEstadoCuentaCliente = new JMenuItem("Estado de Cuenta Clientes");
		mntmEstadoCuentaCliente.addActionListener(this);
		mnReportes.add(mntmEstadoCuentaCliente);
		
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
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmContenido = new JMenuItem("Contenido");
		mnAyuda.add(mntmContenido);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de DevTeam");
		mnAyuda.add(mntmAcercaDe);
		
		desktop = new JDesktopPane();	    						
		ImagenFondo ifondo = new ImagenFondo(desktop);
		desktop.add(ifondo);
		//desktop.setBorder(new pinta_escritorio());
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		frmDespachoJuridicoEr.getContentPane().add(panelPrincipal, BorderLayout.SOUTH);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		JPanel panelOeste = new JPanel();
		panelPrincipal.add(panelOeste, BorderLayout.WEST);
		GridBagLayout gbl_panelOeste = new GridBagLayout();
		gbl_panelOeste.columnWidths = new int[]{20, 0, 0, 2, 0};
		gbl_panelOeste.rowHeights = new int[]{18, 0};
		gbl_panelOeste.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelOeste.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelOeste.setLayout(gbl_panelOeste);
		
		JLabel lblImagen = new JLabel("");
		GridBagConstraints gbc_lblImagen = new GridBagConstraints();
		gbc_lblImagen.anchor = GridBagConstraints.NORTHWEST;
		gbc_lblImagen.insets = new Insets(0, 0, 0, 5);
		gbc_lblImagen.gridx = 0;
		gbc_lblImagen.gridy = 0;
		panelOeste.add(lblImagen, gbc_lblImagen);
		lblImagen.setHorizontalAlignment(SwingConstants.LEFT);
		URL URLLogin = obj.cargador("img/login.png");
		lblImagen.setIcon(new ImageIcon(URLLogin));
		
		JLabel lblUsuario = new JLabel("Usuario: " + frmLogin.usuario);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 10));
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsuario.gridx = 1;
		gbc_lblUsuario.gridy = 0;
		panelOeste.add(lblUsuario, gbc_lblUsuario);
		
		JLabel lblTipoUsuario = new JLabel("     " + frmLogin.nombreTipoUsuario);
		lblTipoUsuario.setFont(new Font("Arial", Font.BOLD, 10));
		GridBagConstraints gbc_lblTipoUsuario = new GridBagConstraints();
		gbc_lblTipoUsuario.insets = new Insets(0, 0, 0, 5);
		gbc_lblTipoUsuario.gridx = 2;
		gbc_lblTipoUsuario.gridy = 0;
		panelOeste.add(lblTipoUsuario, gbc_lblTipoUsuario);
		
		JSeparator Separador1 = new JSeparator();
		Separador1.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_Separador1 = new GridBagConstraints();
		gbc_Separador1.fill = GridBagConstraints.VERTICAL;
		gbc_Separador1.gridx = 3;
		gbc_Separador1.gridy = 0;
		panelOeste.add(Separador1, gbc_Separador1);
		
		JPanel panelEste = new JPanel();
		panelPrincipal.add(panelEste, BorderLayout.EAST);
		GridBagLayout gbl_panelEste = new GridBagLayout();
		gbl_panelEste.columnWidths = new int[]{2, 186, 0};
		gbl_panelEste.rowHeights = new int[]{18, 0};
		gbl_panelEste.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelEste.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panelEste.setLayout(gbl_panelEste);
		
		JSeparator Separador2 = new JSeparator();
		Separador2.setOrientation(SwingConstants.VERTICAL);
		GridBagConstraints gbc_Separador2 = new GridBagConstraints();
		gbc_Separador2.fill = GridBagConstraints.VERTICAL;
		gbc_Separador2.anchor = GridBagConstraints.WEST;
		gbc_Separador2.insets = new Insets(0, 0, 0, 5);
		gbc_Separador2.gridx = 0;
		gbc_Separador2.gridy = 0;
		panelEste.add(Separador2, gbc_Separador2);
		
		Fecha fecha = new Fecha(0, 0, 0, 0);
		fecha.setHorizontalAlignment(SwingConstants.CENTER);
		fecha.setFont(new Font("Arial", Font.BOLD, 10));
		GridBagConstraints gbc_fecha = new GridBagConstraints();
		gbc_fecha.anchor = GridBagConstraints.WEST;
		gbc_fecha.gridx = 1;
		gbc_fecha.gridy = 0;
		panelEste.add(fecha, gbc_fecha);
		
		JPanel panelCentro = new JPanel();
		panelPrincipal.add(panelCentro, BorderLayout.CENTER);
		GridBagLayout gbl_panelCentro = new GridBagLayout();
		gbl_panelCentro.columnWidths = new int[]{0};
		gbl_panelCentro.rowHeights = new int[]{0};
		gbl_panelCentro.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panelCentro.rowWeights = new double[]{Double.MIN_VALUE};
		panelCentro.setLayout(gbl_panelCentro);

		
		frmDespachoJuridicoEr.getContentPane().add(desktop, BorderLayout.CENTER);
		frmDespachoJuridicoEr.getContentPane().add(panelPrincipal, BorderLayout.SOUTH);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frmDespachoJuridicoEr.setSize(screenSize);
		frmDespachoJuridicoEr.setLocation(0,0);
		frmDespachoJuridicoEr.setAlwaysOnTop(false);
		frmDespachoJuridicoEr.setVisible(true);
		
		addKeyListener(new manejadorTeclado());		
		
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		
	}

	public void actionPerformed(ActionEvent arg0) 
	{
		System.out.println("modificar " + arg0.getActionCommand());
		estilos e;
		if(arg0.getActionCommand() == "Cancelar") System.exit(0);
		
		/*if(arg0.getActionCommand() == "Empresa")
		{
			guiConfiguracion objConfiguracion = new guiConfiguracion();
			JInternalFrame JInEmpresa = (objConfiguracion.initialize());
			JInEmpresa.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInEmpresa);
			JInEmpresa.setVisible(true);
			
			JInEmpresa.setSize(616, 340);
			JInEmpresa.setBackground(Color.white);
			JInEmpresa.setVisible(true);
			
			desktop.moveToFront(JInEmpresa);
			
			
		}*/
		
		if(arg0.getActionCommand() == "Nuevo Cliente")
		{
			guiAgregarClientes objAgregarCliente = new guiAgregarClientes();
			JInternalFrame JInAgregarCliente = (objAgregarCliente.initialize());			
			JInAgregarCliente.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInAgregarCliente);								
			JInAgregarCliente.setSize(600, 500);
			JInAgregarCliente.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInAgregarCliente, desktop);			
			JInAgregarCliente.setVisible(true);
			objAgregarCliente.interfazInicial();
			desktop.moveToFront(JInAgregarCliente);
		}
		
		if(arg0.getActionCommand() == "Modificar / Consultar Cliente")
		{
			guiBusquedaClientes objBusquedaCliente = new guiBusquedaClientes();
			JInternalFrame JInternalBusquedaCliente = (JInternalFrame) (objBusquedaCliente.initialize());
			JInternalBusquedaCliente.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalBusquedaCliente);
			e = new estilos();
			e.centrarJInternal(JInternalBusquedaCliente, desktop);
			JInternalBusquedaCliente.setVisible(true);
			JInternalBusquedaCliente.setSize(800, 670);
			JInternalBusquedaCliente.setBackground(Color.white);
			desktop.moveToFront(JInternalBusquedaCliente);
			objBusquedaCliente.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Nuevo Caso")
		{
			System.out.println("ver casos y etapas");
			guiCasos objCasos = new guiCasos();			
			JInternalFrame JInternalCasos = (objCasos.initialize());
			JInternalCasos.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalCasos);			
			JInternalCasos.setSize(800, 600);
			JInternalCasos.setBackground(Color.white);
			e = new estilos();			
			e.centrarJInternal(JInternalCasos, desktop);			
			JInternalCasos.setVisible(true);
			desktop.moveToFront(JInternalCasos);			
		}
		
		if(arg0.getActionCommand() == "Modificar Caso")
		{
			System.out.println("Modificar Casos y Etapas");
			guiModificarCasos objModificarCaso = new guiModificarCasos();
			JInternalFrame JInternalModificarCaso = (objModificarCaso.initialize());
			JInternalModificarCaso.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalModificarCaso);						
			JInternalModificarCaso.setSize(800, 600);
			JInternalModificarCaso.setBackground(Color.white);		
			e = new estilos();
			e.centrarJInternal(JInternalModificarCaso, desktop);
			JInternalModificarCaso.setVisible(true);
			desktop.moveToFront(JInternalModificarCaso);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Agregar Tramite")
		{	
			e = new estilos();
			guiAgregarTramite objAgregarTramite = new guiAgregarTramite();
			JInternalFrame JInAgregartramite = (objAgregarTramite.initialize());
			JInAgregartramite.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInAgregartramite);					
			JInAgregartramite.setSize(800, 600);
			JInAgregartramite.setBackground(Color.white);			
			e.centrarJInternal(JInAgregartramite, desktop);
			JInAgregartramite.setVisible(true);	
			objAgregarTramite.cntrlCampos();
			objAgregarTramite.llenaComboCasos();			
			desktop.moveToFront(JInAgregartramite);
		}
		
		if(arg0.getActionCommand() == "Pagos")
		{
			guiPagos objPagos = new guiPagos();
			JInternalFrame JInPagos = (objPagos.initialize());
			JInPagos.setTitle("Pagos");
			JInPagos.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInPagos);						
			JInPagos.setSize(800, 600);
			JInPagos.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInPagos, desktop);
			JInPagos.setVisible(true);
			desktop.moveToFront(JInPagos);
		}
		
		if(arg0.getActionCommand() == "Cambiar Contraseña")
		{
			System.out.println("Cambiar Contraseña");
			AdminUsuario objAdminUsuario = new AdminUsuario();
			JInternalFrame JInternalAdminUsuario = (objAdminUsuario.initialize("Cambiar"));			
			JInternalAdminUsuario.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalAdminUsuario);			
			JInternalAdminUsuario.setSize(300, 150);
			JInternalAdminUsuario.setBackground(Color.white);	
			e = new estilos();
			e.centrarJInternal(JInternalAdminUsuario, desktop);
			JInternalAdminUsuario.setVisible(true);
			desktop.moveToFront(JInternalAdminUsuario);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		if(arg0.getActionCommand() == "Agregar Usuario")
		{
			System.out.println("Agregar Usuario");
			AdminUsuario objAdminUsuario = new AdminUsuario();
			JInternalFrame JInternalAdminUsuario = (objAdminUsuario.initialize("Insertar"));			
			JInternalAdminUsuario.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalAdminUsuario);			
			JInternalAdminUsuario.setSize(400, 200);
			JInternalAdminUsuario.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalAdminUsuario, desktop);
			JInternalAdminUsuario.setVisible(true);					
			desktop.moveToFront(JInternalAdminUsuario);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}			
		
		if(arg0.getActionCommand() == "Estado de Cuenta del Cliente")
		{
			guiPagos objPagos = new guiPagos();
			JInternalFrame JInternalPagos = objPagos.initialize();
			JInternalPagos.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalPagos);			
			JInternalPagos.setSize(800, 600);
			JInternalPagos.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalPagos, desktop);
			JInternalPagos.setVisible(true);
			desktop.moveToFront(JInternalPagos);
		}
		
		if(arg0.getActionCommand() == "Casos y Etapas")
		{

			guiConsultarCasos objConsultarCasos = new guiConsultarCasos();
			JInternalFrame JInternalConsultarCasos = (objConsultarCasos.initialize());
			JInternalConsultarCasos.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalConsultarCasos);			
			JInternalConsultarCasos.setSize(800, 600);
			JInternalConsultarCasos.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalConsultarCasos, desktop);
			JInternalConsultarCasos.setVisible(true);			
			desktop.moveToFront(JInternalConsultarCasos);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}
		
		if(arg0.getActionCommand() == "Tramites por Cliente")
		{
			guiBusquedaTramite objBusquedaTramite = new guiBusquedaTramite();
			JInternalFrame JInteernalBusquedaCliente = objBusquedaTramite.initialize();
			JInteernalBusquedaCliente.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInteernalBusquedaCliente);			
			JInteernalBusquedaCliente.setSize(800, 600);
			JInteernalBusquedaCliente.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInteernalBusquedaCliente, desktop);
			JInteernalBusquedaCliente.setVisible(true);					
			desktop.moveToFront(JInteernalBusquedaCliente);		
		}	
		
		if(arg0.getActionCommand()=="Estado de Cuenta Clientes")
		{
			frmEstadoCuenta objEstadoCuenta = new frmEstadoCuenta();
			JInternalFrame JInternalrepEstadoCuenta = (objEstadoCuenta.initialize());
			JInternalrepEstadoCuenta.pack();
			desktop.removeAll();
			desktop.add(JInternalrepEstadoCuenta);
			JInternalrepEstadoCuenta.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalrepEstadoCuenta, desktop);			
			JInternalrepEstadoCuenta.setVisible(true);					
			desktop.moveToFront(JInternalrepEstadoCuenta);
		}
												
		if(arg0.getActionCommand()== "Clientes")
		{									
			frmClientes objEstadoCuenta = new frmClientes();
			JInternalFrame JInternalrepClientes = (objEstadoCuenta.initialize());
			JInternalrepClientes.pack();
			desktop.removeAll();
			desktop.add(JInternalrepClientes);
			JInternalrepClientes.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalrepClientes, desktop);
			JInternalrepClientes.setVisible(true);					
			desktop.moveToFront(JInternalrepClientes);
		}
		
		if(arg0.getActionCommand() == "Tramites Clientes")
		{						
			TramitesViewer.showViewer ();			
		}
		
		if(arg0.getActionCommand() == "Casos")
		{			
			CasosViewer.showViewer();
		}
		
		if(arg0.getActionCommand()=="Estado de Cuenta Empresa")
		{
			EstadoViewerEmpresa.showViewer();
		}				
		
		if(arg0.getActionCommand() == "Agregar")
		{
			guiAgenda objAgregarAgenda = new guiAgenda();
			JInternalFrame JInternalAgregarAgenda = (JInternalFrame) (objAgregarAgenda.initialize());
			JInternalAgregarAgenda.pack();
			desktop.removeAll();
			desktop.repaint();
			desktop.add(JInternalAgregarAgenda);			
			JInternalAgregarAgenda.setSize(800, 600);
			JInternalAgregarAgenda.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalAgregarAgenda, desktop);
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
			desktop.repaint();
			desktop.add(JInternalModificarAgenda);			
			JInternalModificarAgenda.setSize(800, 600);
			JInternalModificarAgenda.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalModificarAgenda, desktop);
			JInternalModificarAgenda.setVisible(true);			
			desktop.moveToFront(JInternalModificarAgenda);
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
			desktop.repaint();
			desktop.add(JInternalAgendaconsulta);			
			JInternalAgendaconsulta.setSize(800, 600);
			JInternalAgendaconsulta.setBackground(Color.white);
			e = new estilos();
			e.centrarJInternal(JInternalAgendaconsulta, desktop);
			JInternalAgendaconsulta.setVisible(true);			
			desktop.moveToFront(JInternalAgendaconsulta);
			//objAgregarAgenda.interfazInicial();
			//desktop.setVisible(true)
		}						

	}
}