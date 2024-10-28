package view;

import controller.PersonagemController;
import model.Personagem;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AlunoPainelView extends JFrame {

    private final PersonagemController personagemController;
    private JTable tabelaPersonagens;

    public AlunoPainelView() {
        this.personagemController = new PersonagemController();
        setTitle("Painel do Aluno");
        setSize(1400, 680);  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230)); 

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); 

        JLabel banner = new JLabel(
                "Bem Vindo ao Museu Virtual de Personagens Históricos de Pernambuco",
                SwingConstants.CENTER
        );
        banner.setFont(new Font("Serif", Font.BOLD, 20));
        banner.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel sairButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sairButtonPanel.setOpaque(false); 
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> sair());
        sairButtonPanel.add(sairButton);

        topPanel.add(banner, BorderLayout.CENTER);
        topPanel.add(sairButtonPanel, BorderLayout.EAST);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Personagens", criarPainelPersonagens());

        // Adicionando tudo ao layout principal
        //add(topPanel, BorderLayout.NORTH);
        //add(tabbedPane, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }
    
    private JPanel criarPainelPersonagens() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); 
        
        JPanel botoesPanel = new JPanel(new GridLayout(1, 5));
        botoesPanel.setOpaque(false); 

        JButton listarButton = new JButton("Listar Personagens");
        JButton listarPorTipoButton = new JButton("Listar por Tipo");
        JButton buscarButton = new JButton("Buscar Personagem");

        listarButton.addActionListener(e -> atualizarTabelaPersonagens(personagemController.listarTodosPersonagens()));

        listarPorTipoButton.addActionListener(e -> abrirDialogoEscolherTipo());
        
        buscarButton.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Digite o nome do personagem:").toLowerCase();
            if (nome != null && !nome.trim().isEmpty()) {
                List<Personagem> personagens = personagemController.buscarPersonagemPorNome(nome);
                if (personagens != null && !personagens.isEmpty()) {
                    atualizarTabelaPersonagens(personagens);
                } else {
                    JOptionPane.showMessageDialog(this, "Personagem não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

     
        botoesPanel.add(listarButton);
        botoesPanel.add(listarPorTipoButton); 
        botoesPanel.add(buscarButton);
        
        tabelaPersonagens = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaPersonagens);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    //lista por tipo
    private void abrirDialogoEscolherTipo() {
        // lista de tipos disponíveis
        String[] tiposDisponiveis = {
            "artista_plastico", "artista_popular", "escritor", 
            "politico", "governante", "cientista", 
            "militar", "ativista", "religioso", 
            "educador", "empresario", "explorador", 
            "heroi_folclorico"
        };

        JComboBox<String> tipoComboBox = new JComboBox<>(tiposDisponiveis);
        int resultado = JOptionPane.showConfirmDialog(
            this, 
            tipoComboBox, 
            "Escolha o Tipo de Personagem", 
            JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String tipoSelecionado = (String) tipoComboBox.getSelectedItem();
            List<Personagem> personagens = personagemController.listarPersonagensPorTipo(tipoSelecionado);
            if (personagens != null && !personagens.isEmpty()) {
                atualizarTabelaPersonagens(personagens);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Nenhum personagem encontrado para o tipo: " + tipoSelecionado, 
                    "Resultado da Busca", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    private void atualizarTabelaPersonagens(List<Personagem> personagens) {
        String[] colunas = {"ID", "Nome", "Biografia", "Tipo", "Imagem"};
        Object[][] dados = new Object[personagens.size()][5];

        for (int i = 0; i < personagens.size(); i++) {
            Personagem p = personagens.get(i);
            dados[i][0] = p.getId();
            dados[i][1] = p.getNome();
            dados[i][2] = p.getBiografia();
            dados[i][3] = p.getTipo();
            dados[i][4] = carregarImagem(p.getImagemUrl());
        }

        tabelaPersonagens.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));
        tabelaPersonagens.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
        tabelaPersonagens.setRowHeight(100); 
    }
    
    private ImageIcon carregarImagem(String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.isEmpty()) {
            return null;
        }
        try {
            ImageIcon imagemIcon = new ImageIcon(caminhoImagem);
            Image imagem = imagemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            return new ImageIcon(imagem);
        } catch (Exception e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            return null;
        }
    }
    
    class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon imageIcon) {
                return new JLabel(imageIcon);
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    private void sair() {
        dispose();
        new LoginView().setVisible(true);
    }
}
