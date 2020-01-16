package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;


public class TelaPrincipal extends javax.swing.JFrame {
    
    public TelaPrincipal() {
        initComponents();
        setIcon();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImageIcon imagem = new ImageIcon(getClass().getResource("/Icones/fundoTelaPrincipal.jpg"));
        Image icone = imagem.getImage();
        painelDesktop = new javax.swing.JDesktopPane(){
            public void paintComponent(Graphics g) {
                g.drawImage(icone,0,0,getWidth(),getHeight(), this);
            }
        };
        Menu = new javax.swing.JMenuBar();
        OPCadastros = new javax.swing.JMenu();
        clienteMenu = new javax.swing.JMenuItem();
        OPRelatorio = new javax.swing.JMenu();
        servicoMenu = new javax.swing.JMenuItem();
        OPAjuda = new javax.swing.JMenu();
        sobreMenu = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema para Controle de Ordem de Serviço");
        setExtendedState(6);
        setMinimumSize(new java.awt.Dimension(900, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        painelDesktop.setPreferredSize(new java.awt.Dimension(900, 700));

        javax.swing.GroupLayout painelDesktopLayout = new javax.swing.GroupLayout(painelDesktop);
        painelDesktop.setLayout(painelDesktopLayout);
        painelDesktopLayout.setHorizontalGroup(
            painelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 855, Short.MAX_VALUE)
        );
        painelDesktopLayout.setVerticalGroup(
            painelDesktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );

        Menu.setPreferredSize(new java.awt.Dimension(56, 30));

        OPCadastros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/IconeFormulario.png"))); // NOI18N
        OPCadastros.setText("Cadastro");
        OPCadastros.setPreferredSize(new java.awt.Dimension(80, 19));

        clienteMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        clienteMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/IconeCliente.png"))); // NOI18N
        clienteMenu.setText("Cliente");
        clienteMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OPCadastros.add(clienteMenu);

        Menu.add(OPCadastros);

        OPRelatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/RelatórioIcone.png"))); // NOI18N
        OPRelatorio.setText("Relatório");
        OPRelatorio.setPreferredSize(new java.awt.Dimension(80, 19));

        servicoMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        servicoMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/IconeOperacao.png"))); // NOI18N
        servicoMenu.setText("Serviço");
        servicoMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OPRelatorio.add(servicoMenu);

        Menu.add(OPRelatorio);

        OPAjuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/AjudaIcone.png"))); // NOI18N
        OPAjuda.setText("Ajuda");
        OPAjuda.setPreferredSize(new java.awt.Dimension(80, 19));

        sobreMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_MASK));
        sobreMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/SobreIcone.png"))); // NOI18N
        sobreMenu.setText("Sobre");
        sobreMenu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        OPAjuda.add(sobreMenu);

        Menu.add(OPAjuda);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, 855, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelDesktop, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(871, 533));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       int sair = JOptionPane.showConfirmDialog(null, "Tem Certeza que deseja Sair ?", "Atenção", JOptionPane.YES_NO_OPTION);
       if (sair == JOptionPane.YES_OPTION) {
           System.exit(0);
       }
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
     
       
        java.awt.EventQueue.invokeLater(() -> {
            new TelaPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenu OPAjuda;
    private javax.swing.JMenu OPCadastros;
    private javax.swing.JMenu OPRelatorio;
    private javax.swing.JMenuItem clienteMenu;
    private javax.swing.JDesktopPane painelDesktop;
    private javax.swing.JMenuItem servicoMenu;
    private javax.swing.JMenuItem sobreMenu;
    // End of variables declaration//GEN-END:variables
  private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Icones/IconeLogo.png")));
    }
    
    private void chamarTela(JInternalFrame frame){
        if (frame == null){
            System.out.println("Tela deve ser Instanciada!");
        }
        painelDesktop.add(frame);
        frame.setVisible(true);
    }
}