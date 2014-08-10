package reporteador;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import GUI.estilos;
import GUI.Pagos.guiPagos;
import GUI.tramite.guiAgregarTramite;

import com.crystaldecisions.ReportViewer.ReportViewerBean;
import com.crystaldecisions.reports.reportdefinition.ParameterFieldDefinition;
import com.crystaldecisions.sdk.occa.report.application.OpenReportOptions;
import com.crystaldecisions.sdk.occa.report.application.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

/**
 * A sample report viewer class which can load a report and display it in 
 * a ReportViewerBean embedded in a JFrame.
 */
@SuppressWarnings("serial")
public class NotaPagoTramiteViewer extends JFrame
{
    public static final String FRAME_TITLE = "Nota de Pago";
    public static ParameterFieldDefinition parametros;    
    /** The report viewer bean instance. */ 
    private final ReportViewerBean reportViewer;   
    /** The ReportClientDocument instance being used. 
     *  Set by loadReport(). */
    private static ReportClientDocument reportClientDocument;    	
    
    /**
     * Private constructor for this class.
     * Call showViewer() to obtain instances.
     */
    public NotaPagoTramiteViewer() {
    	estilos obj = new estilos();
		URL URLIcon = obj.cargador("img/al.png");				
    	setIconImage(Toolkit.getDefaultToolkit().getImage(URLIcon));
        setTitle (FRAME_TITLE);
        
        this.reportViewer = new ReportViewerBean();
        reportViewer.init ();

        // A menu bar can be added here if desired
        
        // Handle closing of the viewer.
        addWindowListener (new WindowAdapter ()
        {
            public void windowClosing (WindowEvent e)
            {
                closeViewer ();
            }
        });
        
        getContentPane ().add (reportViewer, BorderLayout.CENTER);

        // Set to some default size
        Insets insets = getInsets ();
        setSize (insets.left + 700 + insets.right, insets.top + 500 + insets.bottom);

        // Show in a sensible location for the platform.
        setLocationByPlatform (true);
    }
    
    /**
     * Create a new instance of this viewer class and show it.
     *
     * @return the new instance of this class that was created.
     */
    static NotaPagoTramiteViewer showViewerFrame () 
    {
    	NotaPagoTramiteViewer viewerFrame = new NotaPagoTramiteViewer();
    	viewerFrame.setVisible (true);
        viewerFrame.reportViewer.start ();                
        return viewerFrame;
    }

    /**
     * Entry point for this class.
     * Create and show the report viewer frame, then bind a report to it so that it can be viewed. 
     */
    public static void showViewer () 
    {
    	NotaPagoTramiteViewer viewerFrame = showViewerFrame ();
        boolean success = viewerFrame.showReport ();        
        if (!success) {
            viewerFrame.closeViewer ();
        }
    }
    
    /**
     * Close the viewer.
     */
    private void closeViewer ()
    {
        if (reportViewer != null)
        {
            reportViewer.stop ();
            reportViewer.destroy ();
        }
        
        removeAll ();
        dispose ();
    }
    
    /**
     * Load a report and show it in the viewer.
     * @return whether a report was successfully displayed.
     */
    private boolean showReport () 
    {
        try
        {
            loadReport ();

            if (reportClientDocument != null) {
                setDatabaseLogon ();
                setResultReporte();
                setParameterFieldValues ();
                setReportSource ();                                
                return true;
            }
        }
        catch (ReportSDKException e)
        {
            String localizedMessage = e.getLocalizedMessage ();
            int errorCode = e.errorCode ();
            
            String title = "Problem showing report";
            String message = localizedMessage + "\nError code: " + errorCode;
            JOptionPane.showMessageDialog (NotaPagoTramiteViewer.this, message, title, JOptionPane.WARNING_MESSAGE);
        }
        return false;
    }
    
    
   public void printReport()
   {
    	try {
    		loadReport();
    		setDatabaseLogon ();
    		setResultReporte();
    		setParameterFieldValues ();
    		setImprimir();
			
		} catch (ReportSDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Determine which report to display, and use this to set the reportClientDocument field.
     * @throws ReportSDKException if there is a problem opening the specified document. 
     */
    private static void loadReport () throws ReportSDKException
    {
        if (reportClientDocument == null) 
        {            
        	String reportFilePath = "Imprimir/Nota.rpt";
               // Create a new client document and use it to open the desired report.
            reportClientDocument = new ReportClientDocument ();               
            reportClientDocument.setReportAppServer(ReportClientDocument.inprocConnectionString);
            reportClientDocument.open (reportFilePath, OpenReportOptions._openAsReadOnly);               
        }
    }
    
    private void setImprimir() throws ReportSDKException
    {
    	PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
    	String impresoraPredeterminada = defaultPrintService.getName();
    	CRJavaHelper.printToServer(reportClientDocument, impresoraPredeterminada);
    	System.out.println("Nombre Impresora Predeterminada: " + impresoraPredeterminada);
    }
    
    /**
     * Set the database logon associated with the report document.
     * @throws ReportSDKException if there is a problem setting the database logon. 
     */
    private void setDatabaseLogon () throws ReportSDKException
    {
        // TODO Set up database logon here to have the report log onto the
    	// data sources defined in the report automatically, without prompting the
    	// user.  For more information about this feature, refer to the documentation.
    	// For example:
    	CRJavaHelper.logonDataSource (reportClientDocument, "root", "123456");
    	// will log onto the data sources defined in the report with username "username"
    	// and password "password".
    }
    
    private void setResultReporte () throws ReportSDKException
    {
    	CRJavaHelper.passResult(reportClientDocument, guiAgregarTramite.res);    	
    }     
    
    /**
     * Set values for parameter fields in the report document.
     * @throws ReportSDKException if there is a problem setting parameter field values. 
     */
    public static void setParameterFieldValues () throws ReportSDKException
    {      	    	    	        
	    CRJavaHelper.addDiscreteParameterValue(reportClientDocument, "", "?importeRecibido", guiAgregarTramite.importePagado);
	    CRJavaHelper.addDiscreteParameterValue(reportClientDocument, "", "?cambio", guiAgregarTramite.sCambio);
	    System.out.println("IMPORTE RECIBIDO: " + guiAgregarTramite.importePagado);
	    System.out.println("CAMBIO: " + guiAgregarTramite.sCambio);
    }
    
    /**
     * Bind the report document to the report viewer so that the report is displayed.
     */
    private void setReportSource ()
    {
        reportViewer.setReportSource (reportClientDocument.getReportSource ());        
    }
}
