package GUI.login;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;

import conexion.StringMD;

import GUI.estilos;
import GUI.guiPrincipal;
import GUI.guiPrincipalNew;
import bd.DbUsuario;

public class frmLogin extends JFrame implements ActionListener
{
	private static String sTipoUsuario = "";
	public static String nombreTipoUsuario;
	static String clave ;
	static String claveSinCifrar;
	public static String usuario;
	
	private static final long serialVersionUID = 1L;
	private JTextField txtusuario;
	private JPasswordField txtpassword;
	private JButton btnAceptar, btnCancelar;
	private JLabel lblusuario, lblpassword;
	private Container contenedor;
	private GridLayout cuadricula1;
		
	public frmLogin()
	{
		super("Acceso al Sistema");
		estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(URLIcon));
		cuadricula1 = new GridLayout(3, 3, 10, 10);
		
		lblusuario = new JLabel("Usuario:");
		lblpassword = new JLabel("Clave:");
		
		txtusuario = new JTextField(10);
		txtusuario.addActionListener(estilos.tranfiereElFoco);
		
		
		txtpassword = new JPasswordField(10);
		txtpassword.addActionListener(estilos.tranfiereElFoco);
		txtpassword.addKeyListener( 
				  new KeyAdapter() { 
				     public void keyPressed(KeyEvent e) { 
				       if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				       { 
				    	   btnAceptar.doClick(); 
				       } 
				     } 
				  }); 
				 

		estilos e = new estilos();
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setMnemonic('A');
		URL urlImgUser =  e.cargador("img/user_go.png");
		ImageIcon imgAceptar = new ImageIcon(urlImgUser);
		btnAceptar.setIcon(imgAceptar);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setMnemonic('C');
		URL urlImgCancel = e.cargador("img/sign_cancel.gif");
		ImageIcon imgCancelar = new ImageIcon(urlImgCancel);
		btnCancelar.setIcon(imgCancelar);
		btnCancelar.addActionListener(this);
		
		contenedor = getContentPane();
		contenedor.setLayout(cuadricula1);
		contenedor.add(lblusuario);
		contenedor.add(txtusuario);
		contenedor.add(lblpassword);
		contenedor.add(txtpassword);
		contenedor.add(btnAceptar);
		contenedor.add(btnCancelar);		
		
		/*btnAceptar.addActionListener( 
			new ActionListener()
			{
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent evento)
				{
					DbUsuario user = new DbUsuario();
					//frmMain principal = new frmMain();
					
					String usuario;
					String clave;
					
					usuario = txtusuario.getText().trim();
					clave = txtpassword.getText().trim();
					
					if(usuario.length() == 0 && clave.length() == 0)
						JOptionPane.showMessageDialog( frmLogin.this,"Debe Proporcionar usuario y Contraseña", "Error Loguin",JOptionPane.INFORMATION_MESSAGE );
					else if(usuario.length() == 0)
						JOptionPane.showMessageDialog( frmLogin.this,"Debe Proporcionar usuario", "Error Loguin",JOptionPane.INFORMATION_MESSAGE );
					else if (clave.length() == 0)
						JOptionPane.showMessageDialog( frmLogin.this,"Debe Proporcionar Contraseña", "Error Loguin",JOptionPane.INFORMATION_MESSAGE );
					else
					{
						try {
							System.out.println("try");
							Object[][] itemUsuario = user.UsuarioAcceder(usuario, clave);
							System.out.println("objcr");
							String x_iduser = itemUsuario[0][0].toString();
							System.out.println("id");
							String x_user = itemUsuario[0][1].toString();
							System.out.println("user");
							String x_pass = itemUsuario[0][2].toString();
							System.out.println("pass");
							sTipoUsuario  = itemUsuario[0][3].toString();
							System.out.println("Usuario " + x_user + " Pasword "+ x_pass+ " Tipo " + sTipoUsuario);
							if ((x_user != "") && (x_pass != ""))
							{
																
								guiPrincipalNew objPrincipal = new guiPrincipalNew();
								objPrincipal.inicializar();
															
								dispose();
							}
						}
						catch(Exception e){
							JOptionPane.showMessageDialog( frmLogin.this,
									"Usuario o contraseña incorrecto",
									"Error de Loguin", JOptionPane.ERROR_MESSAGE );
						}						
					}					
				}
			}
		);
		
		btnCancelar.addActionListener(
			new ActionListener() {
				public void actionPerformed( ActionEvent evento )
				{
					System.exit(0);
				}
			}
		);*/				
		setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(300, 150);		
		setVisible(true);
		setLocationRelativeTo(this.getParent());		
	}
	
	public static String getTipoUsuario()
	{
		return sTipoUsuario;
	}
		    
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == btnCancelar)
		{
			System.exit(0);
		}
		if(arg0.getSource() == btnAceptar)
		{
			DbUsuario user = new DbUsuario();
			//frmMain principal = new frmMain();
			usuario = txtusuario.getText().trim();
											
			claveSinCifrar = txtpassword.getText().trim();
			clave = StringMD.getStringMessageDigest(txtpassword.getText().trim(), StringMD.SHA1);
			
			if(usuario.length() == 0 && clave.length() == 0)
				JOptionPane.showMessageDialog( frmLogin.this,"Debe Proporcionar usuario y Contraseña", "Error Loguin",JOptionPane.INFORMATION_MESSAGE );
			else if(usuario.length() == 0)
				JOptionPane.showMessageDialog( frmLogin.this,"Debe Proporcionar usuario", "Error Loguin",JOptionPane.INFORMATION_MESSAGE );
			else if (clave.length() == 0)
				JOptionPane.showMessageDialog( frmLogin.this,"Debe Proporcionar Contraseña", "Error Loguin",JOptionPane.INFORMATION_MESSAGE );
			else
			{
				try {
					System.out.println("try " + clave);
					Object[][] itemUsuario = user.UsuarioAcceder(usuario, clave);
					System.out.println("objcr");
					String x_iduser = itemUsuario[0][0].toString();
					System.out.println("id");
					String x_user = itemUsuario[0][1].toString();
					System.out.println("user");
					String x_pass = itemUsuario[0][2].toString();
					System.out.println("pass");
					sTipoUsuario  = itemUsuario[0][3].toString();
					System.out.println("Usuario " + x_user + " Pasword "+ x_pass+ " Tipo " + sTipoUsuario);
					Object[][] itemUsuario2 = user.nombretipoUsuario(Integer.parseInt(sTipoUsuario));
					nombreTipoUsuario = itemUsuario2[0][1].toString();
					if ((x_user != "") && (x_pass != ""))
					{
						
						System.out.println("Dentro de if comprobacion");
						guiPrincipalNew objPrincipal = new guiPrincipalNew();
						objPrincipal.inicializar();
													
						dispose();
					}
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog( frmLogin.this,
							"Usuario o contraseña incorrecto " + e.getMessage(),
							"Error de Loguin", JOptionPane.ERROR_MESSAGE );
				}						
			}	
		}
	}
}
