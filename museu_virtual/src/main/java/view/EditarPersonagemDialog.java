package view;

import controller.PersonagemController;
import model.Personagem;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class EditarPersonagemDialog extends JDialog {

    private final PersonagemController personagemController;
    private final Personagem personagem;

    private JTextField nomeField;
    private JTextField biografiaField;
    private JComboBox<String> tipoComboBox;  
    private File imagemSelecionada;  
    private JLabel imagemPreviewLabel; 

    private static final String[] TIPOS_PERSONAGEM = {
        "artista_plastico", "artista_popular", "escritor",
        "politico", "governante", "cientista",
        "militar", "ativista", "religioso",
        "educador", "empresario", "explorador",
        "heroi_folclorico"
    };

    public EditarPersonagemDialog(JFrame parent, PersonagemController personagemController, Personagem personagem) {
        super(parent, "Editar Personagem", true);
        this.personagemController = personagemController;
        this.personagem = personagem;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        initUI();
        carregarImagemPreview(personagem.getImagemUrl()); 
    }

    private void initUI() {
        setLayout(new GridLayout(5, 2));

        JLabel nomeLabel = new JLabel("Nome:");
        nomeField = new JTextField(personagem.getNome() != null ? personagem.getNome() : "");

        JLabel biografiaLabel = new JLabel("Biografia:");
        biografiaField = new JTextField(personagem.getBiografia() != null ? personagem.getBiografia() : "");

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoComboBox = new JComboBox<>(TIPOS_PERSONAGEM);
        tipoComboBox.setSelectedItem(personagem.getTipo() != null ? personagem.getTipo() : "");

        imagemPreviewLabel = new JLabel();  
        imagemPreviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imagemPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagemPreviewLabel.setPreferredSize(new Dimension(100, 100)); // Tamanho da pré-visualização

        JButton selecionarImagemButton = new JButton("Selecionar Imagem");
        selecionarImagemButton.addActionListener(e -> selecionarImagem());

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> salvarAlteracoes());

        add(nomeLabel);
        add(nomeField);
        add(biografiaLabel);
        add(biografiaField);
        add(tipoLabel);
        add(tipoComboBox);
        add(new JLabel("Pré-visualização:"));
        add(imagemPreviewLabel);
        add(selecionarImagemButton);
        add(salvarButton);
    }

    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imagens (JPG, JPEG, PNG)", "jpg", "jpeg", "png"));

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = fileChooser.getSelectedFile();
            carregarImagemPreview(imagemSelecionada.getAbsolutePath()); 
        }
    }

    private void carregarImagemPreview(String imagemUrl) {
        try {
            ImageIcon imagemIcon;
            if (imagemSelecionada != null) {
                imagemIcon = new ImageIcon(imagemSelecionada.getAbsolutePath());
            } else if (imagemUrl != null && !imagemUrl.isEmpty()) {
                imagemIcon = new ImageIcon(new URL(imagemUrl));
            } else {
                imagemIcon = new ImageIcon("path/to/default_image.png"); 
            }
            
            Image imagem = imagemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imagemPreviewLabel.setIcon(new ImageIcon(imagem));
        } catch (MalformedURLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar a imagem: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarAlteracoes() {
        String novoNome = nomeField.getText().trim();
        String biografia = biografiaField.getText().trim();
        String tipo = (String) tipoComboBox.getSelectedItem();  
        
        if (novoNome.isEmpty() || biografia.isEmpty() || tipo == null) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (personagemController.verificarPersonagemExistente(novoNome, tipo, personagem.getId())) {
            JOptionPane.showMessageDialog(this,
                "Já existe outro personagem com o mesmo nome e tipo.",
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (InputStream imagemStream = imagemSelecionada != null
                ? new FileInputStream(imagemSelecionada) : null) {

            String imagemNome = imagemSelecionada != null ? imagemSelecionada.getName() : "";

            personagemController.atualizarPersonagem(
                    personagem.getId(), novoNome, biografia, tipo, imagemStream, imagemNome);

            JOptionPane.showMessageDialog(this, "Personagem atualizado com sucesso.");
            dispose();  

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Erro ao atualizar personagem: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
