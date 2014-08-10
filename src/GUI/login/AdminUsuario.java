package GUI.login;

import java.awt.*;
import java.awt.event.*;
//import java.beans.PropertyVetoException;

import javax.swing.*;

import conexion.StringMD;

import GUI.estilos;
import GUI.guiPrincipal;
import GUI.guiPrincipalNew;
import bd.DBConsultarCasos;
import bd.DbUsuario;

public class AdminUsuario extends JInternalFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPasswordField txtContrasenia;
	private	JComboBox comboTipoUsuario;
	private JPasswordField txtxNuevacontrasenia;
	private JTextField txtNuevoUsuario;
	private JPasswordField txtxConfircontrasenia;
	private JButton btnAceptar, btnCancelar;
	private JLabel lblContrasenia, lblNuevaContrasenia, lblConfirmar, lblTipoUsuario;
	private GridLayout cuadricula1;
	private JInternalFrame frmAdminUsuario;
	private String Modalidad;
	private String iIdTipoUsuario;
	
	String[] idTipoUsuario;
	private DefaultComboBoxModel jtmTipoUsuario;
	
	public AdminUsuario()
	{
	}
	
	public JInternalFrame initialize(String modalidad)
	{
		this.Modalidad = modalidad;
		if(Modalidad == "Cambiar")
		{
			frmAdminUsuario = new JInternalFrame("Cambiar contrase�a",false,true,true,true);
			
			lblContrasenia = new JLabel("Contrase�a:");
			lblNuevaContrasenia = new JLabel("Nueva Contrase�a:");
			lblConfirmar = new JLabel("Confirmar Contrase�a:");
			txtxNuevacontrasenia = new JPasswordField(10);
			txtxNuevacontrasenia.addActionListener(estilos.tranfiereElFoco);
			
			cuadricula1 = new GridLayout(3, 3, 10, 10);
			
		}
		else
		{
			frmAdminUsuario = new JInternalFrame("Agregar Usuario",false,true,true,true);
			
			lblContrasenia = new JLabel("Nuevo Usuario:");
			lblNuevaContrasenia = new JLabel("Nuevo Usuario:");
			lblConfirmar = new JLabel("Contrase�a:");
			lblTipoUsuario = new JLabel("TipoUsuario:");
			
			comboTipoUsuario = new JComboBox();
			comboTipoUsuario.setModel(new DefaultComboBoxModel(new String[] {"---Seleccionar---"}));
			comboTipoUsuario.addActionListener(this);
			
			llenaComboCasos();
			txtNuevoUsuario = new JTextField();
			txtNuevoUsuario.addActionListener(estilos.tranfiereElFoco);
			
			cuadricula1 = new GridLayout(4, 3, 10, 10);
		}
		
		frmAdminUsuario.setBounds(100, 100, 600, 500);
		frmAdminUsuario.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAdminUsuario.getContentPane().setLayout(new BorderLayout(0, 0));		
		
		txtContrasenia = new JPasswordField(10);
		txtContrasenia.addActionListener(estilos.tranfiereElFoco);
		
		txtxConfircontrasenia = new JPasswordField(10);
		txtxConfircontrasenia.addActionListener(estilos.tranfiereElFoco);
		txtxConfircontrasenia.addKeyListener( 
				  new KeyAdapter() { 
				     public void keyPressed(KeyEvent e) { 
				       if (e.getKeyCode() == KeyEvent.VK_ENTER) 
				       { 
				    	   btnAceptar.doClick(); 
				       } 
				     } 
				  }); 
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setMnemonic('A');
		ImageIcon imgAceptar = new ImageIcon("img/user_go.png");
		btnAceptar.setIcon(imgAceptar);
		btnAceptar.addActionListener(this);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setMnemonic('C');
		ImageIcon imgCancelar = new ImageIcon("img/sign_cancel.gif");
		btnCancelar.setIcon(imgCancelar);
		btnCancelar.addActionListener(this);
		
		//contenedor = getContentPane();
		frmAdminUsuario.setLayout(cuadricula1);
		/*frmAdminUsuario.add(lblContrasenia);
		frmAdminUsuario.add(txtContrasenia);*/
		frmAdminUsuario.add(lblNuevaContrasenia);
		
		if(Modalidad == "Cambiar") frmAdminUsuario.add(txtxNuevacontrasenia);
		else frmAdminUsuario.add(txtNuevoUsuario);
		frmAdminUsuario.add(lblConfirmar);
		frmAdminUsuario.add(txtxConfircontrasenia);
		
		if(Modalidad == "Insertar"){ frmAdminUsuario.add(lblTipoUsuario);
		frmAdminUsuario.add(comboTipoUsuario);}
		
		frmAdminUsuario.add(btnAceptar);
		frmAdminUsuario.add(btnCancelar);		
				
		/*setUndecorated(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(300, 150);		
		setVisible(true);
		setLocationRelativeTo(this.getParent());	*/	

		return frmAdminUsuario;
	}
	public void limpiaCampos()
	{
		txtContrasenia.setText("");
		
		if(Modalidad == "Cambiar")txtxNuevacontrasenia.setText("");
		else txtNuevoUsuario.setText("");
		
		txtxConfircontrasenia.setText("");
	}
	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == btnCancelar)
		{
			try {
				frmAdminUsuario.setClosed(true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.exit(DISPOSE_ON_CLOSE);
		}
		if(arg0.getSource() == btnAceptar)
		{
				if (Modalidad == "Cambiar") cambiarContrasenia();
				else insertarNuevoUsuario();
		}
		
		if(arg0.getSource() == comboTipoUsuario)
		{
			if(comboTipoUsuario.getSelectedItem() != "---Seleccionar---")
			 {
				 iIdTipoUsuario = idTipoUsuario[comboTipoUsuario.getSelectedIndex()-1];				

			 }
		}
	}
	
	public void insertarNuevoUsuario()
	{
		
		DbUsuario user = new DbUsuario();
		//frmMain principal = new frmMain();
		
		String usuarioNuevo;
		String contraseniaUsuario;
				
		usuarioNuevo = txtNuevoUsuario.getText().trim();
		contraseniaUsuario = txtxConfircontrasenia.getText().trim();
		
		
		
		if(usuarioNuevo.length() == 0 && contraseniaUsuario.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Proporcionar Los Datos Completos", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
		/*else if(contrasenia.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Proporcionar Contrase�a Actual", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );*/
		else if (usuarioNuevo.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Proporcionar un Nuevo Usuario", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
		else if (contraseniaUsuario.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe proporcionar Contrase�a", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
		else if(comboTipoUsuario.getSelectedItem() == "---Seleccionar---")
		 {
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Seleccionar un Tipo de Usuario", "Error Agregar Usuario",JOptionPane.ERROR_MESSAGE );
			return;
		 }
		else if(contraseniaUsuario.length() < 6)
		 {
			JOptionPane.showMessageDialog( AdminUsuario.this,"la contrase�a debe de ser minimo 6 caracteres", "Error Agregar Usuario",JOptionPane.ERROR_MESSAGE );
			return;
		 }
		else
		{
			contraseniaUsuario =  StringMD.getStringMessageDigest(txtxConfircontrasenia.getText().trim(), StringMD.SHA1);
			try
			{
				String iTipoUsuario = idTipoUsuario[comboTipoUsuario.getSelectedIndex()-1];
				boolean bCambiocontrasenia = user.insertarUsuario(usuarioNuevo,contraseniaUsuario, iTipoUsuario );
				
				if (bCambiocontrasenia)
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"el usuario ha sido agregado Correctamente", "Cambio contrase�a",JOptionPane.INFORMATION_MESSAGE );
					limpiaCampos();
					
				}
				else
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"No se pudo agregar al Usuario " + user.getException().getMessage(), "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
				}
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog( AdminUsuario.this,"No se pudo agregar al Usuario [1]" + user.getException().getMessage(), "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
			}
		}
		
	}
	
	public void cambiarContrasenia()
	{
		DbUsuario user = new DbUsuario();
		//frmMain principal = new frmMain();
		
		String contrasenia;
		String contraseniaNueva;
		String confirmarContrasenia;
		
		contrasenia = txtContrasenia.getText().trim();
		contraseniaNueva = txtxNuevacontrasenia.getText().trim();
		confirmarContrasenia = txtxConfircontrasenia.getText().trim();
		
		
		
		if(contraseniaNueva.length() == 0 && confirmarContrasenia.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Proporcionar Los Datos Completos", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
		/*else if(contrasenia.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Proporcionar Contrase�a Actual", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );*/
		else if (contraseniaNueva.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe Proporcionar Contrase�a Nueva", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
		else if (confirmarContrasenia.length() == 0)
			JOptionPane.showMessageDialog( AdminUsuario.this,"Debe confirmar Contrase�a", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
		else
		{
			try 
			{
				//contrasenia = StringMD.getStringMessageDigest(txtContrasenia.getText().trim(), StringMD.SHA1); 
				confirmarContrasenia = StringMD.getStringMessageDigest(txtxConfircontrasenia.getText().trim(), StringMD.SHA1);
				System.out.println("sin cifrar " + frmLogin.clave +  "nueva " + contrasenia);
				System.out.println("contrase�a nueva  " + contraseniaNueva);
				System.out.println("confirmar contrase�a " + confirmarContrasenia);
				
				/*if(frmLogin.clave.equals(contrasenia))
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"La Contrase�a Actual no es Correcta, favor de verifcar", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
					return;
				}					
				else*/ if(contraseniaNueva.equals(confirmarContrasenia ))
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"La Contrase�a Nueva no coincide", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
					return;
				}					 
				else if(contraseniaNueva.length() < 6)
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"La Contrase�a debe de ser Minimo de 6 caracteres", "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
					return;
				}
				
				
				System.out.println("try " + contraseniaNueva);
				contraseniaNueva     = StringMD.getStringMessageDigest(txtxNuevacontrasenia.getText().trim(), StringMD.SHA1);
				
				
				boolean bCambiocontrasenia = user.cambiarPassword(frmLogin.usuario,contraseniaNueva );
				
				if (bCambiocontrasenia)
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"La Contrase�a ha sido Cambiada Correctamente", "Cambio contrase�a",JOptionPane.INFORMATION_MESSAGE );
					limpiaCampos();
					frmLogin.clave = contraseniaNueva;
				}
				else
				{
					JOptionPane.showMessageDialog( AdminUsuario.this,"No se pudo actualizar la Contrase�a " + user.getException().getMessage(), "Error Cambio contrase�a",JOptionPane.ERROR_MESSAGE );
				}
				
			}
			catch(Exception e){
				JOptionPane.showMessageDialog( AdminUsuario.this,
						"No se ha podido Actualizar la contrase�a " + e.getMessage(),
						"Error de Cambio Contrase�a", JOptionPane.ERROR_MESSAGE );
			}						
		}
	}
	
	public void llenaComboCasos()
	{
		DbUsuario objUsuario = new DbUsuario();
		Object [][] vConsultaTipoUsuario = objUsuario.tipoUsuario();
		int iContador, iCuenta;
		iContador = vConsultaTipoUsuario.length;
		String[] lista = new String[iContador];
		 idTipoUsuario = new String[iContador];
		
		 jtmTipoUsuario = new DefaultComboBoxModel();
		 jtmTipoUsuario.removeAllElements();
		comboTipoUsuario.setModel(jtmTipoUsuario);
				
		jtmTipoUsuario.addElement("---Seleccionar---");
		for(iCuenta = 0; iCuenta < vConsultaTipoUsuario.length; iCuenta++ )
		{
			for(int iCuentaAux = 0; iCuentaAux < vConsultaTipoUsuario[iCuenta].length;iCuentaAux++)
			{
				if( iCuentaAux == 0)idTipoUsuario[iCuenta] = vConsultaTipoUsuario[iCuenta][iCuentaAux].toString();
				if( iCuentaAux == 1) 
				{
					lista[iCuenta] = vConsultaTipoUsuario[iCuenta][iCuentaAux].toString();
					jtmTipoUsuario.addElement(lista[iCuenta]);
				}								 
			}
		}
		comboTipoUsuario.addActionListener(this);
		//txtDescripcion.setText(descripciones[comboBox.getSelectedIndex()]);
		//txtCostoTotal.setText(precios[comboBox.getSelectedIndex()]);
		System.out.println("Seleccionado " + jtmTipoUsuario.getSelectedItem()+"\n At " +comboTipoUsuario.getSelectedIndex());
	}
}
