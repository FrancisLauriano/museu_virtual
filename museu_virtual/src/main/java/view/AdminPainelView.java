package view;

import controller.PersonagemController;
import controller.UsuarioController;
import model.Usuario;
import model.Personagem;
import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
//import java.io.File;
import java.util.List;


public class AdminPainelView extends JFrame {

    private final UsuarioController usuarioController;
    private final PersonagemController personagemController;
    private JTable tabelaUsuarios;
    private JTable tabelaPersonagens;

    public AdminPainelView() {
        this.usuarioController = new UsuarioController();
        this.personagemController = new PersonagemController();
        setTitle("Admin Painel");
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
        //banner.setOpaque(false);  

        JPanel sairButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //sairButtonPanel.setBackground(new Color(173, 216, 230)); 
        sairButtonPanel.setOpaque(false); 
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> sair());
        sairButtonPanel.add(sairButton);

        topPanel.add(banner, BorderLayout.CENTER);
        topPanel.add(sairButtonPanel, BorderLayout.EAST);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Usuários", criarPainelUsuarios());
        tabbedPane.addTab("Personagens", criarPainelPersonagens());

        //add(topPanel, BorderLayout.NORTH);
        //add(tabbedPane, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private JPanel criarPainelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JPanel botoesPanel = new JPanel(new GridLayout(1, 5));
        botoesPanel.setOpaque(false);
        
        
        JButton listarButton = new JButton("Listar Usuários");
        JButton listarPorTipoButton = new JButton("Listar por Tipo");
        JButton buscarButton = new JButton("Buscar Usuário");
        JButton editarButton = new JButton("Editar Usuário");
        JButton deletarButton = new JButton("Deletar Usuário");
        JButton adicionarButton = new JButton("Adicionar Usuário");

        listarButton.addActionListener(e -> atualizarTabelaUsuarios(usuarioController.listarTodosUsuarios()));

        listarPorTipoButton.addActionListener(e -> abrirDialogoEscolherTipoUsuario());
        
        buscarButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Digite o email do usuário:").toLowerCase();
            if (email != null && !email.trim().isEmpty()) {
                Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);
                if (usuario != null) {
                    atualizarTabelaUsuarios(List.of(usuario));
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        editarButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Digite o email do usuário a ser editado:").toLowerCase();
            if (email != null && !email.trim().isEmpty()) {
                Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);
                if (usuario != null) {
                    new EditarUsuarioDialog(this, usuarioController, usuario).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deletarButton.addActionListener(e -> {
            String email = JOptionPane.showInputDialog("Digite o email do usuário a ser deletado:").toLowerCase();
            if (email != null && !email.trim().isEmpty()) {
                Usuario usuario = usuarioController.buscarUsuarioPorEmail(email);
                if (usuario != null) {
                    int confirmacao = JOptionPane.showConfirmDialog(
                            this,
                            "Você realmente deseja excluir o seguinte usuário?\n\n" + usuario,
                            "Confirmar Exclusão",
                            JOptionPane.YES_NO_OPTION
                    );
                    if (confirmacao == JOptionPane.YES_OPTION) {
                        usuarioController.deletarUsuario(usuario.getId());
                        JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adicionarButton.addActionListener(e -> new AdicionarUsuarioDialog(this, usuarioController).setVisible(true));

        botoesPanel.add(listarButton);
        botoesPanel.add(listarPorTipoButton);
        botoesPanel.add(buscarButton);
        botoesPanel.add(editarButton);
        botoesPanel.add(deletarButton);
        botoesPanel.add(adicionarButton);

        tabelaUsuarios = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaUsuarios);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    private void abrirDialogoEscolherTipoUsuario() {
        // lista de tipos disponíveis
        String[] tiposUsuarios = {"administrador", "aluno"};

        JComboBox<String> tipoComboBox = new JComboBox<>(tiposUsuarios);
        int resultado = JOptionPane.showConfirmDialog(
                this,
                tipoComboBox,
                "Escolha o Tipo de Usuário",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (resultado == JOptionPane.OK_OPTION) {
            String tipoSelecionado = (String) tipoComboBox.getSelectedItem();
            List<Usuario> usuarios = usuarioController.listarUsuariosPorTipo(tipoSelecionado);
            if (usuarios != null && !usuarios.isEmpty()) {
                atualizarTabelaUsuarios(usuarios);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nenhum usuário encontrado para o tipo: " + tipoSelecionado,
                        "Resultado da Busca",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    private JPanel criarPainelPersonagens() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); 
        
        JPanel botoesPanel = new JPanel(new GridLayout(1, 5));
        botoesPanel.setOpaque(false); 

        JButton listarButton = new JButton("Listar Personagens");
        JButton listarPorTipoButton = new JButton("Listar por Tipo");
        JButton buscarButton = new JButton("Buscar Personagem");
        JButton editarButton = new JButton("Editar Personagem");
        JButton deletarButton = new JButton("Deletar Personagem");
        JButton adicionarButton = new JButton("Adicionar Personagem");

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
        
        listarButton.addActionListener(e -> atualizarTabelaPersonagens(personagemController.listarTodosPersonagens()));

        editarButton.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Digite o nome do personagem a ser editado:").toLowerCase();

            if (nome != null && !nome.trim().isEmpty()) {
                List<Personagem> personagens = personagemController.buscarPersonagemPorNome(nome);

                if (personagens != null && !personagens.isEmpty()) {
                    if (personagens.size() == 1) {
                        // Se houver apenas um personagem, abre o diálogo diretamente
                        Personagem personagem = personagens.get(0);
                        new EditarPersonagemDialog(this, personagemController, personagem).setVisible(true);
                    } else {
                        // Se houver mais de um personagem, exibe uma lista para escolha
                        Personagem personagemSelecionado = selecionarPersonagem(personagens);
                        if (personagemSelecionado != null) {
                            new EditarPersonagemDialog(this, personagemController, personagemSelecionado).setVisible(true);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Personagem não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        
        deletarButton.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog("Digite o nome do personagem a ser deletado:").toLowerCase();
            if (nome != null && !nome.trim().isEmpty()) {
                List<Personagem> personagens = personagemController.buscarPersonagemPorNome(nome);
                if (personagens != null && personagens.size() == 1) {
                    Personagem personagem = personagens.get(0);
                    int confirmacao = JOptionPane.showConfirmDialog(
                            this, "Confirmar exclusão de: " + personagem.getNome(),
                            "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

                    if (confirmacao == JOptionPane.YES_OPTION) {
                        personagemController.deletarPersonagem(personagem.getId());
                        JOptionPane.showMessageDialog(this, "Personagem excluído.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Personagem não encontrado.");
                }
            }
        });

        adicionarButton.addActionListener(e -> new AdicionarPersonagemDialog(this, personagemController).setVisible(true));

        botoesPanel.add(listarButton);
        botoesPanel.add(listarPorTipoButton); 
        botoesPanel.add(buscarButton);
        botoesPanel.add(editarButton);
        botoesPanel.add(deletarButton);
        botoesPanel.add(adicionarButton);

        tabelaPersonagens = new JTable();
        JScrollPane scrollPane = new JScrollPane(tabelaPersonagens);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    
    private Personagem selecionarPersonagem(List<Personagem> personagens) {
        String[] opcoes = personagens.stream()
                .map(p -> p.getId() + " - " + p.getNome() + " (" + p.getTipo() + ")")
                .toArray(String[]::new);

        String escolha = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o personagem:",
                "Seleção de Personagem",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (escolha != null) {
            // Extrai o ID do personagem da escolha e retorna o correspondente
            String idEscolhido = escolha.split(" - ")[0];
            return personagens.stream()
                    .filter(p -> p.getId().toString().equals(idEscolhido))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
    
    //lista por tipo
    private void abrirDialogoEscolherTipo() {
        // Lista de tipos disponíveis
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

    private void atualizarTabelaUsuarios(List<Usuario> usuarios) {
        String[] colunas = {"ID", "Nome", "Email", "Tipo de Usuário"};
        String[][] dados = new String[usuarios.size()][4];

        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);
            dados[i][0] = String.valueOf(u.getId());
            dados[i][1] = u.getNome();
            dados[i][2] = u.getEmail();
            dados[i][3] = u.getTipoUsuario();
        }

        tabelaUsuarios.setModel(new javax.swing.table.DefaultTableModel(dados, colunas));
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
