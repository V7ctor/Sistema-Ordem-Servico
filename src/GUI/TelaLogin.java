package GUI;

import DAO.Conexao;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TelaLogin extends javax.swing.JFrame {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public TelaLogin() {
       initComponents();
       conectarBanco();
       setIcon();
    }
    
    private void conectarBanco(){
        conn = Conexao.getConector();
        
        if (conn != null){
            txtStatus.setText("Conectado");
            txtStatus.setIcon(new ImageIcon(getClass().getResource("/Icones/accept.png")));
        } else {
            txtStatus.setText("Não Conectado");
             txtStatus.setIcon(new ImageIcon(getClass().getResource("/Icones/SairIcone2.png")));
        }
    }
    
    private void logarSistema(){
        String sql = "select * from usuarios where Login=? and Senha=?";
        
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, campoLogin.getText());
            pst.setString(2, campoSenha.getText());
            
            rs = pst.executeQuery();
            
            if (rs.next()){
                fecharComponentes();
                chamarTela(new TelaPrincipal());
            }else {
                JOptionPane.showMessageDialog(null, "Usuário ou Senha Inválido!");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
       }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtLogin = new javax.swing.JLabel();
        campoLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JLabel();
        campoSenha = new javax.swing.JPasswordField();
        btEntrar = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        txtStatus = new javax.swing.JLabel();
        fundo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Olá, Bem Vindo!");
        setResizable(false);
        getContentPane().setLayout(null);

        txtLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtLogin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtLogin.setText("Login:");
        getContentPane().add(txtLogin);
        txtLogin.setBounds(0, 100, 80, 30);

        campoLogin.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        getContentPane().add(campoLogin);
        campoLogin.setBounds(90, 100, 310, 30);
        campoLogin.getAccessibleContext().setAccessibleDescription("");

        txtSenha.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSenha.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtSenha.setText("Senha:");
        getContentPane().add(txtSenha);
        txtSenha.setBounds(0, 140, 80, 30);
        getContentPane().add(campoSenha);
        campoSenha.setBounds(90, 140, 310, 30);

        btEntrar.setBackground(new java.awt.Color(0, 153, 153));
        btEntrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btEntrar.setText("Entrar");
        btEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEntrarActionPerformed(evt);
            }
        });
        getContentPane().add(btEntrar);
        btEntrar.setBounds(90, 180, 90, 40);

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/LogoTecnoLabs.png"))); // NOI18N
        getContentPane().add(logo);
        logo.setBounds(450, 80, 190, 110);

        txtStatus.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtStatus.setText("Status");
        getContentPane().add(txtStatus);
        txtStatus.setBounds(200, 180, 200, 40);

        fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/ImagemFundoLogin.jpg"))); // NOI18N
        getContentPane().add(fundo);
        fundo.setBounds(0, -20, 700, 340);

        setSize(new java.awt.Dimension(678, 330));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEntrarActionPerformed
        logarSistema();
    }//GEN-LAST:event_btEntrarActionPerformed

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
     
        java.awt.EventQueue.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btEntrar;
    private javax.swing.JTextField campoLogin;
    private javax.swing.JPasswordField campoSenha;
    private javax.swing.JLabel fundo;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel txtLogin;
    private javax.swing.JLabel txtSenha;
    private javax.swing.JLabel txtStatus;
    // End of variables declaration//GEN-END:variables
    
    private void chamarTela(JFrame tela){
        if (tela == null) {
            System.out.println("Instancie a Tela.");
        }
        tela.setVisible(true);
    }
    
    private void fecharComponentes(){
        try {
            this.dispose();
            conn.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icones/IconeLogo.png")));
    }
}
