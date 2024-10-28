package controller;

import dao.PersonagemDAO;
import java.io.IOException;
import java.io.InputStream;
import model.Personagem;
import java.util.Arrays;

import java.util.List;
import utils.ImageStorageUtil;

public class PersonagemController {

    private final PersonagemDAO personagemDAO;
    private static final List<String> FORMATOS_SUPORTADOS = Arrays.asList("jpg", "jpeg", "png");
    
    // lista com os tipos válidos para personagem
    private static final List<String> TIPOS_VALIDOS = Arrays.asList(
        "artista_plastico", "artista_popular", "escritor", 
        "politico", "governante", "cientista", 
        "militar", "ativista", "religioso", 
        "educador", "empresario", "explorador", 
        "heroi_folclorico"
    );

    public PersonagemController() {
        this.personagemDAO = new PersonagemDAO();
    }
 
    public void adicionarPersonagem(String nome, String biografia, String tipo, InputStream imagemStream, String imagemNome) {
        try {
            nome = validarEntradaObrigatoria(nome, "Nome");
            biografia = validarEntradaObrigatoria(biografia, "Biografia");
            tipo = validarTipoPersonagem(tipo);
            
            if (verificarPersonagemExistente(nome, tipo, null)) {
                throw new IllegalArgumentException("Já existe um personagem com o mesmo nome e tipo.");
            }

            //  upload da imagem (se fornecida)
            String imagemUrl = salvarImagemSeForValida(imagemStream, imagemNome);

            Personagem personagem = new Personagem(nome, biografia, tipo, imagemUrl != null ? imagemUrl : "");
            personagemDAO.savePersonagem(personagem);
            System.out.println("Personagem cadastrado com sucesso.");
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar personagem: " + e.getMessage());
        }
    }

    public List<Personagem> listarTodosPersonagens() {
        try {
            return personagemDAO.findAllPersonagens();
        } catch (Exception e) {
            System.err.println("Erro ao listar personagens: " + e.getMessage());
            return null;
        }
    }
    
    public List<Personagem> listarPersonagensPorTipo(String tipo) {
        try {
            // Valida o tipo de personagem
            tipo = validarTipoPersonagem(tipo);

            // Chama o método no DAO para listar personagens por tipo
            return personagemDAO.listarPersonagensPorTipo(tipo);

        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao listar personagens por tipo: " + e.getMessage());
            return null;
        }
    }


    public Personagem buscarPersonagemPorId(Long id) {
        try {
            return personagemDAO.findPersonagemById(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar personagem: " + e.getMessage());
            return null;
        }
    }
    
    public void atualizarPersonagem(Long id, String novoNome, String biografia, String tipo, InputStream imagemStream, String imagemNome) {
        try {
            Personagem personagem = personagemDAO.findPersonagemById(id);
            if (personagem == null) {
                System.err.println("Personagem não encontrado.");
                return;
            }
            
            if (verificarPersonagemExistente(novoNome, tipo, id)) {
                throw new IllegalArgumentException("Já existe outro personagem com o mesmo nome e tipo.");
            }

            personagem.setNome(validarEntradaObrigatoria(novoNome, "Novo Nome"));
            personagem.setBiografia(validarEntradaObrigatoria(biografia, "Biografia"));
            personagem.setTipo(validarTipoPersonagem(tipo));

            String novaImagemUrl = salvarImagemSeForValida(imagemStream, imagemNome);
            if (novaImagemUrl != null) {
                personagem.setImagemUrl(novaImagemUrl);
            }

            personagemDAO.updatePersonagem(personagem);
            System.out.println("Personagem atualizado com sucesso.");
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Erro ao atualizar personagem: " + e.getMessage());
        }
    }

    public void deletarPersonagem(Long id) {
        try {
            Personagem personagem = personagemDAO.findPersonagemById(id);
            if (personagem == null) {
                System.err.println("Personagem não encontrado.");
                return;
            }
            personagemDAO.deletePersonagem(personagem.getId());
            System.out.println("Personagem excluído com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao excluir personagem: " + e.getMessage());
        }
    }

    public List<Personagem> buscarPersonagemPorNome(String nome) {
        try {
            return personagemDAO.buscarPersonagemPorNome(nome.toLowerCase());
        } catch (Exception e) {
            System.err.println("Erro ao buscar personagem por nome: " + e.getMessage());
            return null;
        }
    }
    
    private boolean isFormatoValido(String nomeArquivo) {
        String extensao = nomeArquivo.substring(nomeArquivo.lastIndexOf('.') + 1).toLowerCase();
        return FORMATOS_SUPORTADOS.contains(extensao);
    }
    
    private String salvarImagemSeForValida(InputStream imagemStream, String imagemNome) throws IOException {
        if (imagemStream != null && imagemNome != null && !imagemNome.isEmpty()) {
            if (!isFormatoValido(imagemNome)) {
                throw new IllegalArgumentException("Formato de imagem não suportado. Use JPG, JPEG ou PNG.");
            }
            return ImageStorageUtil.saveImage(imagemNome, imagemStream);
        }
        return null; 
    }

    private String validarEntradaObrigatoria(String entrada, String campo) {
        if (entrada == null || entrada.trim().isEmpty()) {
            throw new IllegalArgumentException("O campo '" + campo + "' é obrigatório e não pode ser vazio.");
        }	
        return entrada.trim().toLowerCase();
    }
    
    private String validarTipoPersonagem(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo de personagem é obrigatório.");
        }

        tipo = tipo.trim().toLowerCase();

        if (!TIPOS_VALIDOS.contains(tipo)) {
            throw new IllegalArgumentException(
                "Tipo de personagem inválido. Deve ser um dos seguintes: " + TIPOS_VALIDOS
            );
        }

        return tipo;
    }
    
    public boolean verificarPersonagemExistente(String nome, String tipo, Long idIgnorar) {
        List<Personagem> personagensExistentes = personagemDAO.buscarPersonagemPorNomeETipo(nome, tipo);
        if (!personagensExistentes.isEmpty()) {
            if (idIgnorar != null) {
                return personagensExistentes.stream().anyMatch(p -> !p.getId().equals(idIgnorar));
            }
            return true;  
        }
        return false;
    }
}
    
