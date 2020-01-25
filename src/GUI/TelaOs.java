package GUI;

import DAO.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

public class TelaOs extends javax.swing.JInternalFrame {
    
    Connection conn;
    ResultSet rs;
    PreparedStatement pst;
    
    private String tipo;
    
    public TelaOs() {
        initComponents();
        conn = Conexao.getConector();
    }
    
    private void pesquisa_Cliente() {
        String sql = "select Id as idCliente, Nome as nomeCliente, Telefone as foneCliente "
                + "from clientes where Nome like ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, campoClientePesquisa.getText() + "%");
            
            rs = pst.executeQuery();
            
            tabelaCliente.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void setar_Campos(JTable tabela, JTextField campo) {
        int setar = tabela.getSelectedRow();
        campo.setText(tabela.getModel().getValueAt(setar, 0).toString());
    }
    
    private void pesquisa_Tecnico() {
        String sql = "select Id as idTecnico, Nome as nomeTecnico, Telefone as foneTecnico "
                + "from tecnico where Nome like ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, campoPesquisaTecnico.getText() + "%");
            
            rs = pst.executeQuery();
            
            tabelaTecnico.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void emitir_Os() {
        String sql = "insert into ordemservico(Equipamento, Defeito, Servico, Id_Tecnico, Valor, Id_Cliente, Tipo, Situacao)"
                + " values (?,?,?,?,?,?,?,?)";
        
        try {
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, campoEquipamento.getText());
            pst.setString(2, campoDefeito.getText());
            pst.setString(3, campoServico.getText());
            pst.setString(4, campoIdTecnico.getText());
            pst.setString(5, campoValorTotal.getText().replace(",", "."));
            pst.setString(6, campoIdCliente.getText());
            pst.setString(7, tipo);
            pst.setString(8, boxSituacao.getSelectedItem().toString());
            
            if (campoEquipamento.getText().isEmpty() || campoDefeito.getText().isEmpty()
                    || campoServico.getText().isEmpty() || campoIdTecnico.getText().isEmpty()
                    || campoIdCliente.getText().isEmpty() || campoValorTotal.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os Campos!!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço Emitida com Sucesso!!!");
                    limparCampos();
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void pesquisar_Os() {
        String numero_Os = JOptionPane.showInputDialog("Número da Os: ");
        String sql = "select * from ordemservico where Id = " + numero_Os;
        
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            
            if (rs.next()) {
                campoIdOs.setText(rs.getString(1));
                campoDataOs.setText(rs.getString(2));
                campoEquipamento.setText(rs.getString(3));
                campoDefeito.setText(rs.getString(4));
                campoServico.setText(rs.getString(5));
                campoIdTecnico.setText(rs.getString(6));
                campoValorTotal.setText(rs.getString(7));
                campoIdCliente.setText(rs.getString(8));
                tipo = rs.getString(9);
                boxSituacao.setSelectedItem(rs.getString(10));
                
                if (tipo.equals("Ordem de Serviço")) {
                    opOrdemServico.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    opOrcamento.setSelected(true);
                    tipo = "Orçamento";
                }
                
                mudancaSeletor();
                
            } else {
                JOptionPane.showMessageDialog(null, "Ordem de Serviço não Cadastrada!");
                voltarAcao();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Insira Somente Números!");
            voltarAcao();
        }        
    }
    
    private void alterar_Os() {
        String sql = "update ordemservico set Equipamento = ?, Defeito = ?, Servico = ?, Valor = ?, "
                + "Tipo = ?, Situacao = ? where Id = ?";
        
        try {
            pst = conn.prepareStatement(sql);
            
            pst.setString(1, campoEquipamento.getText());
            pst.setString(2, campoDefeito.getText());
            pst.setString(3, campoServico.getText());
            pst.setString(4, campoValorTotal.getText().replace(",", "."));
            pst.setString(5, tipo);
            pst.setString(6, boxSituacao.getSelectedItem().toString());
            pst.setString(7, campoIdOs.getText());
            
            if (campoEquipamento.getText().isEmpty() || campoDefeito.getText().isEmpty()
                    || campoServico.getText().isEmpty() || campoValorTotal.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os Campos!!");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço Alterada com Sucesso!!!");
                    voltarAcao();
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
    
    private void excluir_Os() {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Tem Certeza que Deseja Excluir essa Ordem de Serviço ?", "Atenção", JOptionPane.YES_NO_OPTION);
        
        if (confirmacao == JOptionPane.YES_OPTION) {
            String sql = "delete from ordemservico where Id=?";
            
            try {
                pst = conn.prepareStatement(sql);
                pst.setString(1, campoIdOs.getText());
                
                int apagado = pst.executeUpdate();
                
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Ordem de Serviço Excluída com Sucesso!");
                    voltarAcao();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                voltarAcao();
            }            
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupo1botoes = new javax.swing.ButtonGroup();
        PainelGeral = new javax.swing.JPanel();
        painelOs = new javax.swing.JPanel();
        txtIdOs = new javax.swing.JLabel();
        campoIdOs = new javax.swing.JTextField();
        txtDataOs = new javax.swing.JLabel();
        campoDataOs = new javax.swing.JTextField();
        opOrdemServico = new javax.swing.JRadioButton();
        opOrcamento = new javax.swing.JRadioButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaCliente = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        campoClientePesquisa = new javax.swing.JTextField();
        campoIdCliente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        boxSituacao = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelaTecnico = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        campoPesquisaTecnico = new javax.swing.JTextField();
        campoIdTecnico = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoEquipamento = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        campoServico = new javax.swing.JTextField();
        campoValorTotal = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btPesquisar = new javax.swing.JButton();
        btCadastrar = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btImprimir = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        campoDefeito = new javax.swing.JTextField();
        btCancelar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Ordem de Serviço");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(1024, 780));
        setMinimumSize(new java.awt.Dimension(1000, 650));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        PainelGeral.setBackground(new java.awt.Color(255, 255, 255));

        painelOs.setBackground(new java.awt.Color(255, 255, 255));
        painelOs.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Ordem de Serviço"));

        txtIdOs.setText("ID:");

        campoIdOs.setEnabled(false);

        txtDataOs.setText("Data:");

        campoDataOs.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        campoDataOs.setEnabled(false);

        opOrdemServico.setBackground(new java.awt.Color(255, 255, 255));
        grupo1botoes.add(opOrdemServico);
        opOrdemServico.setText("Ordem de Serviço");
        opOrdemServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opOrdemServicoActionPerformed(evt);
            }
        });

        opOrcamento.setBackground(new java.awt.Color(255, 255, 255));
        grupo1botoes.add(opOrcamento);
        opOrcamento.setText("Orçamento");
        opOrcamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opOrcamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelOsLayout = new javax.swing.GroupLayout(painelOs);
        painelOs.setLayout(painelOsLayout);
        painelOsLayout.setHorizontalGroup(
            painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdOs)
                    .addComponent(opOrcamento))
                .addGap(18, 18, 18)
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoDataOs)
                    .addGroup(painelOsLayout.createSequentialGroup()
                        .addComponent(txtDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelOsLayout.createSequentialGroup()
                        .addGap(0, 42, Short.MAX_VALUE)
                        .addComponent(opOrdemServico)))
                .addContainerGap())
        );
        painelOsLayout.setVerticalGroup(
            painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoIdOs, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDataOs, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(painelOsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(opOrdemServico)
                    .addComponent(opOrcamento))
                .addContainerGap())
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Cliente"));

        tabelaCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaClienteMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaCliente);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/iconePesquisa.png"))); // NOI18N
        jLabel5.setText("Pesquisar:");

        campoClientePesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoClientePesquisaKeyReleased(evt);
            }
        });

        campoIdCliente.setEnabled(false);

        jLabel7.setText("ID:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoClientePesquisa))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(campoIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoClientePesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setText("Situação:");

        boxSituacao.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrega Ok", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Na Bancada", "Retornou" }));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Técnico"));

        tabelaTecnico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTecnico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaTecnicoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabelaTecnico);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icones/iconePesquisa.png"))); // NOI18N
        jLabel6.setText("Pesquisar:");

        campoPesquisaTecnico.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                campoPesquisaTecnicoKeyReleased(evt);
            }
        });

        campoIdTecnico.setEnabled(false);

        jLabel8.setText("ID:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoIdTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoPesquisaTecnico))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(campoIdTecnico, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoPesquisaTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setText("Equipamento:");

        jLabel3.setText("Serviço:");

        campoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoServicoActionPerformed(evt);
            }
        });

        campoValorTotal.setText("0");

        jLabel4.setText("Valor Total:");

        btPesquisar.setBackground(new java.awt.Color(255, 255, 255));
        btPesquisar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btPesquisar.setText("Pesquisar");
        btPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btPesquisarActionPerformed(evt);
            }
        });

        btCadastrar.setBackground(new java.awt.Color(0, 102, 102));
        btCadastrar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCadastrar.setForeground(new java.awt.Color(255, 255, 255));
        btCadastrar.setText("Cadastrar");
        btCadastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCadastrarActionPerformed(evt);
            }
        });

        btAlterar.setBackground(new java.awt.Color(17, 12, 100));
        btAlterar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btAlterar.setForeground(new java.awt.Color(255, 255, 255));
        btAlterar.setText("Alterar");
        btAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btExcluir.setEnabled(false);
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btImprimir.setBackground(new java.awt.Color(51, 51, 51));
        btImprimir.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btImprimir.setText("Imprimir");
        btImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btImprimir.setEnabled(false);
        btImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimirActionPerformed(evt);
            }
        });

        jLabel9.setText("Defeito:");

        campoDefeito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoDefeitoActionPerformed(evt);
            }
        });

        btCancelar.setBackground(new java.awt.Color(210, 116, 16));
        btCancelar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btCancelar.setForeground(new java.awt.Color(255, 255, 255));
        btCancelar.setText("Cancelar Ação");
        btCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCancelar.setEnabled(false);
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelGeralLayout = new javax.swing.GroupLayout(PainelGeral);
        PainelGeral.setLayout(PainelGeralLayout);
        PainelGeralLayout.setHorizontalGroup(
            PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelGeralLayout.createSequentialGroup()
                .addGroup(PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(painelOs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boxSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelGeralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoEquipamento)
                            .addGroup(PainelGeralLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1083, Short.MAX_VALUE))))
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(campoServico))
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(campoValorTotal))
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(campoDefeito)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelGeralLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PainelGeralLayout.setVerticalGroup(
            PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelGeralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PainelGeralLayout.createSequentialGroup()
                        .addComponent(painelOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 12, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(PainelGeralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCadastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelGeral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PainelGeral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btPesquisarActionPerformed
        pesquisar_Os();
    }//GEN-LAST:event_btPesquisarActionPerformed

    private void btCadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCadastrarActionPerformed
        emitir_Os();
    }//GEN-LAST:event_btCadastrarActionPerformed

    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        alterar_Os();
    }//GEN-LAST:event_btAlterarActionPerformed

    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        excluir_Os();
    }//GEN-LAST:event_btExcluirActionPerformed

    private void btImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btImprimirActionPerformed

    private void campoServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoServicoActionPerformed

    private void campoDefeitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoDefeitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoDefeitoActionPerformed

    private void campoClientePesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoClientePesquisaKeyReleased
        pesquisa_Cliente();
    }//GEN-LAST:event_campoClientePesquisaKeyReleased

    private void campoPesquisaTecnicoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoPesquisaTecnicoKeyReleased
        pesquisa_Tecnico();
    }//GEN-LAST:event_campoPesquisaTecnicoKeyReleased

    private void tabelaClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaClienteMouseClicked
        setar_Campos(tabelaCliente, campoIdCliente);
    }//GEN-LAST:event_tabelaClienteMouseClicked

    private void tabelaTecnicoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaTecnicoMouseClicked
        setar_Campos(tabelaTecnico, campoIdTecnico);
    }//GEN-LAST:event_tabelaTecnicoMouseClicked

    private void opOrcamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opOrcamentoActionPerformed
        tipo = "Orçamento";
    }//GEN-LAST:event_opOrcamentoActionPerformed

    private void opOrdemServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opOrdemServicoActionPerformed
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_opOrdemServicoActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        opOrcamento.setSelected(true);
    }//GEN-LAST:event_formInternalFrameOpened

    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        voltarAcao();
    }//GEN-LAST:event_btCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PainelGeral;
    private javax.swing.JComboBox boxSituacao;
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCadastrar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btImprimir;
    private javax.swing.JButton btPesquisar;
    private javax.swing.JTextField campoClientePesquisa;
    private javax.swing.JTextField campoDataOs;
    private javax.swing.JTextField campoDefeito;
    private javax.swing.JTextField campoEquipamento;
    private javax.swing.JTextField campoIdCliente;
    private javax.swing.JTextField campoIdOs;
    private javax.swing.JTextField campoIdTecnico;
    private javax.swing.JTextField campoPesquisaTecnico;
    private javax.swing.JTextField campoServico;
    private javax.swing.JTextField campoValorTotal;
    private javax.swing.ButtonGroup grupo1botoes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton opOrcamento;
    private javax.swing.JRadioButton opOrdemServico;
    private javax.swing.JPanel painelOs;
    private javax.swing.JTable tabelaCliente;
    private javax.swing.JTable tabelaTecnico;
    private javax.swing.JLabel txtDataOs;
    private javax.swing.JLabel txtIdOs;
    // End of variables declaration//GEN-END:variables
    
    private void limparCampos() {
        campoClientePesquisa.setText("");
        campoDataOs.setText("");
        campoDefeito.setText("");
        campoEquipamento.setText("");
        campoIdCliente.setText("");
        campoIdOs.setText("");
        campoIdTecnico.setText("");
        campoPesquisaTecnico.setText("");
        campoServico.setText("");
        campoValorTotal.setText("");
    }
    
    private void mudancaSeletor() {
        btAlterar.setEnabled(true);
        btExcluir.setEnabled(true);
        btCancelar.setEnabled(true);
        btImprimir.setEnabled(true);
        
        btCadastrar.setEnabled(false);
        btPesquisar.setEnabled(false);
        tabelaCliente.setVisible(false);
        tabelaTecnico.setVisible(false);
        campoClientePesquisa.setEnabled(false);
        campoPesquisaTecnico.setEnabled(false);
    }
    
    private void voltarAcao() {
        limparCampos();
        
        btAlterar.setEnabled(false);
        btExcluir.setEnabled(false);
        btCancelar.setEnabled(false);
        btImprimir.setEnabled(false);
        
        btCadastrar.setEnabled(true);
        btPesquisar.setEnabled(true);
        tabelaCliente.setVisible(true);
        tabelaTecnico.setVisible(true);
        campoClientePesquisa.setEnabled(true);
        campoPesquisaTecnico.setEnabled(true);
    }
}
