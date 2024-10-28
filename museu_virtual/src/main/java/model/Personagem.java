package model;

import annotations.TipoPersonagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "personagens")
public class Personagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    @Column(nullable = false, length = 100)
    private String nome;

    @NotBlank(message = "A biografia é obrigatória.")
    @Column(nullable = false, length = 1000)
    private String biografia;

    @TipoPersonagem(message = "O tipo de personagem deve ser ‘artista_plastico’, ‘artista_popular’, ‘escritor’,  ‘politico’, ‘governante’, ‘cientista’, ‘militar’, ‘ativista’, ‘religioso’,  ‘educador’, ‘empresario’, ‘explorador’ ou  ‘heroi_folclorico’")
    @Column(name = "tipo_personagem", nullable = false)
    private String tipo; // Exemplo: Artista ou Político
    

    @Column(name = "imagem_url", nullable = true)  // imagem é opcional
    private String imagemUrl;

    // Construtor padrão
    public Personagem() {}

    // Construtor completo
    public Personagem(String nome, String biografia, String tipo, String imagemUrl) {
        this.nome = nome;
        this.biografia = biografia;
        this.tipo = tipo;
        this.imagemUrl = imagemUrl;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    // Sobrescrita de equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personagem that = (Personagem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString para exibir mais informações
    @Override
    public String toString() {
        return "Personagem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", biografia='" + biografia.substring(0, Math.min(biografia.length(), 30)) + "..." + '\'' +
                ", tipo='" + tipo + '\'' +
                ", imagemUrl='" + imagemUrl + '\'' +
                '}';
    }
}
