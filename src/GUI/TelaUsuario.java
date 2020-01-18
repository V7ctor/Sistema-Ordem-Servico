package GUI;

import DAO.Conexao;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class TelaUsuario extends javax.swing.JInternalFrame {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    
    public TelaUsuario() {
        initComponents();
        conn = Conexao.getConector();
    }

    private void consultar(){
    try {
            if (campoID.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Insira um ID para Pesquisar!");
            } else {
            String sql = "select * from usuarios where Id=?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, campoID.getText());
            
            rs = pst.executeQuery();
            
            if (rs.next()){
                campoNome.setText(rs.getString(2));
                campoTelefone.setText(rs.getString(3));
                campoLogin.setText(rs.getString(4));
                campoSenha.setText(rs.getString(5));
                boxPerfil.setSelectedItem(rs.getString(6));
                
                btCadastrar.setEnabled(false);
                btPesquisar.setEnabled(false);
                btAlterar.setEnabled(true);
                btExcluir.setEnabled(true);
                btCancelar.setEnabled(true);
                campoID.setEnabled(false);
                
            } else {
                JOptionPane.showMessageDialog(null, "Usuário com esse ID ainda não foi cadastrado!");
            }
        }
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    private void cadastrar(){
        String sql = "insert into usuarios(Id, Nome, Telefone, Login, Senha, Perfil) values (?,?,?,?,?,?)";
        try {
           pst = conn.prepareStatement(sql);
           
           pst.setString(1, campoID.getText());
           pst.setString(2, campoNome.getText());
           pst.setString(3, campoTelefone.getText());
           pst.setString(4, campoLogin.getText());
           pst.setString(5, campoSenha.getText());
           pst.setString(6, boxPerfil.getSelectedItem().toString());

           if (campoID.getText().isEmpty() || campoNome.getText().isEmpty() || campoTelefone.getText().isEmpty()
               || campoLogin.getText().isEmpty() || campoSenha.getText().isEmpty()) {
               JOptionPane.showMessageDialog(null, "Preencha Todos os Campos!");
           } else {
               
                int adicionado = pst.executeUpdate();

                if (adicionado > 0){
                    JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso!");
                    limparCampos();
                }
           }
           
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error:" + e.getMessage());
        }
    }
    
    private void alterar(){
        String sql = "update usuarios set Nome=?, Telefone=?, Login=?, Senha=?, Perfil=? where Id=?";
        
        try {
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, campoNome.getText());
            pst.setString(2, campoTelefone.getText());
            pst.setString(3, campoLogin.getText());
            pst.setString(4, campoSenha.getText());
            pst.setString(5, boxPerfil.getSelectedItem().toString());
            pst.setString(6, campoID.getText());
            
            int alterado = pst.executeUpdate();
            
              if (campoID.getText().isEmpty() || campoNome.getText().isEmpty() || campoTelefone.getText().isEmpty()
               || campoLogin.getText().isEmpty() || campoSenha.getText().isEmpty()) {
               JOptionPane.showMessageDialog(null, "Preencha Todos os Campos!");
           } else {
                if (alterado > 0){
                    JOptionPane.showMessageDialog(null, "Dados alterados com Sucesso!");
                    voltarAcao();
                }
           }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }
    
    private void excluir(){
        
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza que Deseja Remover esse Usuário ?", "Atenção!", JOptionPane.YES_NO_OPTION);
        
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from usuarios where Id=?";
            try {
                pst = conn.prepareStatement(sql);
                
                pst.setString(1, campoID.getText());
                
                int delecao = pst.executeUpdate();
                
                if (delecao > 0){
                    JOptionPane.showMessageDialog(null, "Usuário Removido com Sucesso!");
                    voltarAcao();
                }
            }catch(SQLException e){
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fundoPainel = new javax.swing.JPanel();
        txtID = new javax.swing.JLabel();
        campoID = new javax.swing.JTextField();
        txtNome = new javax.swing.JLabel();
        campoNome = new javax.swing.JTextField();
        txtLogin = new javax.swing.JLabel();
        campoLogin = new javax.swing.JTextField();
        txtSenha = new javax.swing.JLabel();
        campoSenha = new javax.swing.JTextField();
        txtPerfil = new javax.swing.JLabel();
        boxPerfil = new javax.swing.JComboBox();
        btCadastrar = new javax.swing.JButton();
        btPesquisar = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JLabel();
        campoTelefone = new javax.swing.JFormattedTextField();
        btCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Cadastro de Usuários");
        setMaximumSize(new java.awt.Dimension(1024, 780));
        setMinimumSize(new java.awt.Dimension(900, 700));

        fundoPainel.setBackground(new java.awt.Color(255, 255, 255));

        txtID.setText("ID:");

        txtNome.setText("Nome:");

        txtLogin.setText("Login:");

        txtSenha.setText("Senha:");

        txtPerfil.setText("Perfil:");

        boxPerfil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Administrador", "Usuário" }));

        btCadastrar.setBackground(new java.awt.Color(0, 102, 102));
        btCadastrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btCadastrar.setText("Cadastrar");
        btCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarActionPerformed(evt);
            }
        });

        btPesquisar.setBackground(new java.awt.Color(255, 255, 255));
        btPesquisar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btPesquisar.setText("Pesquisar");
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btAlterar.setBackground(new java.awt.Color(17, 12, 100));
        btAlterar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btAlterar.setText("Alterar");
        btAlterar.setEnabled(false);
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btExcluir.setBackground(new java.awt.Color(124, 13, 13));
        btExcluir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btExcluir.setForeground(new java.awt.Color(255, 255, 255));
        btExcluir.setText("Excluir");
        btExcluir.setEnabled(false);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/LogoTecnoLabs.png"))); // NOI18N

        txtTelefone.setText("Telefone:");

        try {
            campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }

        btCancelar.setBackground(new java.awt.Color(210, 116, 16));
        btCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar Ação");
        btCancelar.setEnabled(false);
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout fundoPainelLayout = new javax.swing.GroupLayout(fundoPainel);
        fundoPainel.setLayout(fundoPainelLayout);
        fundoPainelLayout.setHorizontalGroup(
            fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoPainelLayout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(fundoPainelLayout.createSequentialGroup()
                            .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, fundoPainelLayout.createSequentialGroup()
                                    .addComponent(campoID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(fundoPainelLayout.createSequentialGroup()
                                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(85, 85, 85)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, fundoPainelLayout.createSequentialGroup()
                            .addComponent(campoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(campoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(fundoPainelLayout.createSequentialGroup()
                                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(campoTelefone))))
                    .addGroup(fundoPainelLayout.createSequentialGroup()
                        .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
            .addGroup(fundoPainelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        fundoPainelLayout.setVerticalGroup(
            fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fundoPainelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel2)
                .addGap(74, 74, 74)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoID, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(fundoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(111, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fundoPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 869, 617);
    }// </editor-fold>//GEN-END:initComponents

    private void btCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarActionPerformed
         cadastrar();
    }//GEN-LAST:event_btCadastrarActionPerformed

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
         consultar();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
         voltarAcao();
    }//GEN-LAST:event_btCancelarActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
         alterar();
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        excluir();
    }//GEN-LAST:event_btExcluirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox boxPerfil;
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCadastrar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JTextField campoID;
    private javax.swing.JTextField campoLogin;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoSenha;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JPanel fundoPainel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel txtID;
    private javax.swing.JLabel txtLogin;
    private javax.swing.JLabel txtNome;
    private javax.swing.JLabel txtPerfil;
    private javax.swing.JLabel txtSenha;
    private javax.swing.JLabel txtTelefone;
    // End of variables declaration//GEN-END:variables
    
    private void voltarAcao(){
        btCadastrar.setEnabled(true);
        btAlterar.setEnabled(false);
        btExcluir.setEnabled(false);
        btCancelar.setEnabled(false);
        campoID.setEnabled(true);
        btPesquisar.setEnabled(true);
        limparCampos();
    }
    private void limparCampos(){
        campoID.setText("");
        campoLogin.setText("");
        campoNome.setText("");
        campoSenha.setText("");
        campoTelefone.setText("");
    }

}
