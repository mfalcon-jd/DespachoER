package Agenda;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class operacionesJTable
{
    public static void rellena(ResultSet rs, DefaultTableModel modelo)
    {
        configuraColumnas(rs, modelo);
        vaciaFilasModelo(modelo);
        anhadeFilasDeDatos(rs, modelo);
    }

    private static void anhadeFilasDeDatos(ResultSet rs,
            DefaultTableModel modelo)
    {
        int numeroFila = 0;
        try
        {
            // Para cada registro de resultado en la consulta 
            while (rs.next())
            {
                // Se crea y rellena la fila para el modelo de la tabla.
                Object[] datosFila = new Object[modelo.getColumnCount()];
                for (int i = 0; i < modelo.getColumnCount(); i++)
                    datosFila[i] = rs.getObject(i + 1);
                modelo.addRow(datosFila);
                numeroFila++;
            }
            rs.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void vaciaFilasModelo(final DefaultTableModel modelo)
    {
        try
        {
            

                while (modelo.getRowCount() > 0)
                        modelo.removeRow(0);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void configuraColumnas(final ResultSet rs,
            final DefaultTableModel modelo)
    {
        try
        {   
                
                    try
                    {
                        ResultSetMetaData metaDatos = rs.getMetaData();
                        
                        // Se obtiene el numero de columnas.
                        int numeroColumnas = metaDatos.getColumnCount();
                        
                        // Se obtienen las etiquetas para cada columna
                        Object[] etiquetas = new Object[numeroColumnas];
                        for (int i = 0; i < numeroColumnas; i++)
                        {
                            etiquetas[i] = metaDatos.getColumnLabel(i + 1);
                        }
                        
                        // Se meten las etiquetas en el modelo. El numero
                        // de columnas se ajusta automáticamente.
                        modelo.setColumnIdentifiers(etiquetas);
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("e operaciones " +e.getMessage());
                    }          

           
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
