package MyReports;

import DAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class GerarRelatorio {
    
    private String relatorio;
    
    private Connection conn;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public void ficha (String relatorio) {
        
        this.relatorio = relatorio;
        
        conn = Conexao.getConector();
        
        String sql = "select * from ordemservico";
        
        try {
            pst = conn.prepareStatement(sql);
            
            rs = pst.executeQuery();
            
            JRResultSetDataSource jrRS = new JRResultSetDataSource(rs);
            
            Map parametros = new HashMap();
            
            JasperPrint jp = JasperFillManager.fillReport(relatorio, parametros, jrRS);
            
            JasperViewer.viewReport(jp, false);
            
        } catch (SQLException | JRException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        
    }
}
