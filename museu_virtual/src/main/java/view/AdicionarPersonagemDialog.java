package view;

import controller.PersonagemController;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class AdicionarPersonagemDialog extends JDialog {

    private final PersonagemController personagemController;

    private JTextField nomeField;
    private JTextField biografiaField;
    private JComboBox<String> tipoComboBox;
    private JTextField imagemUrlField;
    private JLabel imagemPreviewLabel;  
    private File imagemSelecionada;  

    private static final String[] TIPOS_PERSONAGEM = {
            "artista_plastico", "artista_popular", "escritor",
            "politico", "governante", "cientista",
            "militar", "ativista", "religioso",
            "educador", "empresario", "explorador",
            "heroi_folclorico"
    };

    public AdicionarPersonagemDialog(JFrame parent, PersonagemController personagemController) {
        super(parent, "Adicionar Personagem", true);
        this.personagemController = personagemController;

        setSize(500, 400);
        setLocationRelativeTo(parent);
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(6, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField();

        JLabel biografiaLabel = new JLabel("Biografia:");
        biografiaField = new JTextField();

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoComboBox = new JComboBox<>(TIPOS_PERSONAGEM);

        JLabel imagemUrlLabel = new JLabel("Caminho da Imagem:");
        imagemUrlField = new JTextField();
        imagemUrlField.setEditable(false);
        
        imagemPreviewLabel = new JLabel();  
        imagemPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagemPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton selecionarImagemButton = new JButton("Selecionar Imagem");
        selecionarImagemButton.addActionListener(e -> selecionarImagem());

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(e -> adicionarPersonagem());

        add(nomeLabel);
        add(nomeField);
        add(biografiaLabel);
        add(biografiaField);
        add(tipoLabel);
        add(tipoComboBox);
        add(imagemUrlLabel);
        add(imagemUrlField);
        add(new JLabel("Pré-visualização:"));  
        add(imagemPreviewLabel);  
        add(selecionarImagemButton);
        //add(new JLabel());  
        add(adicionarButton);
    }

    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = fileChooser.getSelectedFile();
            imagemUrlField.setText(imagemSelecionada.getAbsolutePath());  
            carregarImagemPreview(imagemSelecionada);  
        }
    }
    
    private void carregarImagemPreview(File imagemFile) {
        try {
            ImageIcon imagemIcon = new ImageIcon(imagemFile.getAbsolutePath());
            Image imagem = imagemIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            imagemPreviewLabel.setIcon(new ImageIcon(imagem));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarPersonagem() {
        String nome = nomeField.getText().trim();
        String biografia = biografiaField.getText().trim();
        String tipo = (String) tipoComboBox.getSelectedItem();

        if (nome.isEmpty() || biografia.isEmpty() || tipo == null) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (personagemController.verificarPersonagemExistente(nome, tipo, null)) {
            JOptionPane.showMessageDialog(this, 
                    "Já existe um personagem com o mesmo nome e tipo.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (InputStream imagemStream = imagemSelecionada != null
                ? new FileInputStream(imagemSelecionada) : null) {

            String imagemNome = imagemSelecionada != null ? imagemSelecionada.getName() : "";

            personagemController.adicionarPersonagem(nome, biografia, tipo, imagemStream, imagemNome);

            JOptionPane.showMessageDialog(this, "Personagem adicionado com sucesso.");
            dispose(); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar personagem: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
