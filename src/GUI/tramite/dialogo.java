package GUI.tramite;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import GUI.estilos;



import GUI.estilos;


public class dialogo extends JFrame implements ActionListener{
	/**
	 * @param args
	 */
	 
	 private static JFrame frmBusqueda;
	 JDialog jCaja;
	 JFormattedTextField txtTotalPagar;
	JFormattedTextField txtEntrega;
	JButton dialog;
	double pagoTotal;
	double cambio;
	double entrega;
	String sEntrega;
	JButton btnPagar;
	
	public dialogo()
	{

		frmBusqueda = new JFrame();
		frmBusqueda.setBounds(100, 100, 600, 500);
		// TODO Auto-generated method stub
		dialog = new JButton("Dialogo");
		dialog.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		 buttonPanel.add(dialog);
		   
		 frmBusqueda.getContentPane().add(buttonPanel, java.awt.BorderLayout.CENTER);
		 //add(frmBusqueda);


	}
	public static void main(String[] args)
	{
		dialogo obj = new dialogo();
		frmBusqueda.setVisible(true);
	}
	

		public void dialogoCaja()
		 {
		  estilos obj = new estilos();
		  obj.aplicar();
		  jCaja = new JDialog();  
		  jCaja.setTitle("Caja");
		  jCaja.setBounds(100, 100, 404, 140);
		  jCaja.setVisible(true);
		  
		  
		  GridBagLayout gridBagLayout = new GridBagLayout();
		  gridBagLayout.columnWidths = new int[]{119, 227, 0};
		  gridBagLayout.rowHeights = new int[]{34, 0, 0, 0};
		  gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		  gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		  jCaja.getContentPane().setLayout(gridBagLayout);
		  
		  JLabel lblTotalAPagar = new JLabel("Total a Pagar:");
		  GridBagConstraints gbc_lblTotalAPagar = new GridBagConstraints();
		  gbc_lblTotalAPagar.insets = new Insets(0, 0, 5, 5);
		  gbc_lblTotalAPagar.anchor = GridBagConstraints.EAST;
		  gbc_lblTotalAPagar.gridx = 0;
		  gbc_lblTotalAPagar.gridy = 0;
		  jCaja.getContentPane().add(lblTotalAPagar, gbc_lblTotalAPagar);
		  
		  //txtTotalPagar = new JFormattedTextField();
		  txtTotalPagar = estilos.formatoText(txtTotalPagar);
		  txtTotalPagar.addActionListener(estilos.tranfiereElFoco);
		  GridBagConstraints gbc_txtTotalPagar = new GridBagConstraints();
		  gbc_txtTotalPagar.insets = new Insets(0, 0, 5, 0);
		  gbc_txtTotalPagar.fill = GridBagConstraints.HORIZONTAL;
		  gbc_txtTotalPagar.gridx = 1;
		  gbc_txtTotalPagar.gridy = 0;
		  jCaja.getContentPane().add(txtTotalPagar, gbc_txtTotalPagar);
		  txtTotalPagar.setColumns(10);
		  
		  JLabel lblEntrega = new JLabel("Entrega:");
		  GridBagConstraints gbc_lblEntrega = new GridBagConstraints();
		  gbc_lblEntrega.anchor = GridBagConstraints.EAST;
		  gbc_lblEntrega.insets = new Insets(0, 0, 5, 5);
		  gbc_lblEntrega.gridx = 0;
		  gbc_lblEntrega.gridy = 1;
		  jCaja.getContentPane().add(lblEntrega, gbc_lblEntrega);
		  
		  //txtEntrega = new JFormattedTextField();
		  txtEntrega = estilos.formatoText(txtEntrega);
		  txtEntrega.addActionListener(estilos.tranfiereElFoco);
		  GridBagConstraints gbc_txtEntrega = new GridBagConstraints();
		  gbc_txtEntrega.insets = new Insets(0, 0, 5, 0);
		  gbc_txtEntrega.fill = GridBagConstraints.HORIZONTAL;
		  gbc_txtEntrega.gridx = 1;
		  gbc_txtEntrega.gridy = 1;
		  jCaja.getContentPane().add(txtEntrega, gbc_txtEntrega);
		  txtEntrega.setColumns(10);
		  
		  JPanel panel = new JPanel();
		  GridBagConstraints gbc_panel = new GridBagConstraints();
		  gbc_panel.gridwidth = 2;
		  gbc_panel.insets = new Insets(0, 0, 0, 5);
		  gbc_panel.fill = GridBagConstraints.BOTH;
		  gbc_panel.gridx = 0;
		  gbc_panel.gridy = 2;
		  jCaja.getContentPane().add(panel, gbc_panel);
		  GridBagLayout gbl_panel = new GridBagLayout();
		  gbl_panel.columnWidths = new int[]{150, 141, 0, 0};
		  gbl_panel.rowHeights = new int[]{41, 0};
		  gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		  gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		  panel.setLayout(gbl_panel);
		  
		  JLabel txtCambio = new JLabel("");  
		  //txtCambio.setFont(new Font("Tahoma", Font.BOLD, 16));
		  GridBagConstraints gbc_txtCambio = new GridBagConstraints();
		  gbc_txtCambio.insets = new Insets(0, 0, 0, 5);
		  gbc_txtCambio.gridx = 0;
		  gbc_txtCambio.gridy = 0;
		  panel.add(txtCambio, gbc_txtCambio);
		  
		  btnPagar = new JButton("Pagar");
		  btnPagar.addActionListener(this);
		  GridBagConstraints gbc_btnPagar = new GridBagConstraints();
		  gbc_btnPagar.anchor = GridBagConstraints.EAST;
		  gbc_btnPagar.insets = new Insets(0, 0, 0, 5);
		  gbc_btnPagar.gridx = 1;
		  gbc_btnPagar.gridy = 0;
		  panel.add(btnPagar, gbc_btnPagar);
		  
		  JButton btnCancelar = new JButton("Cancelar");
		  btnCancelar.addActionListener(this);
		  GridBagConstraints gbc_btnCancelar = new GridBagConstraints();
		  gbc_btnCancelar.gridx = 2;
		  gbc_btnCancelar.gridy = 0;
		  panel.add(btnCancelar, gbc_btnCancelar);   
		 }

		public void actionPerformed(ActionEvent arg0) 
		{
			if(arg0.getSource() == dialog)
			{
				System.out.println("si");
				dialogoCaja();
				

			}
			if(arg0.getSource() == btnPagar)
			{
				pagoTotal = Double.parseDouble(estilos.getObtieneValorText(txtTotalPagar));
		        System.out.println("Pago Total Caja " + pagoTotal);
		       sEntrega = estilos.getObtieneValorText(txtEntrega);                       
		        entrega = Double.parseDouble(sEntrega);
		        System.out.println("Pago Total entrega " + entrega);
		        cambio = entrega - pagoTotal;
		        System.out.println("CAMBIO DE LA CAJA " + cambio);
			}
			
		}
	
}
