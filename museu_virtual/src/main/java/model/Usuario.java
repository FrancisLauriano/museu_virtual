package model;

import annotations.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 255, message = "O nome deve ter entre 8 e 255 caracteres.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "Email inválido.")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Column(nullable = false)
    private String senha;

    @TipoUsuario(message = "O tipo de usuário deve ser 'administrador' ou 'aluno'.")
    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    // construtor padrão
    public Usuario() {}

    // construtor com todos os atributos
    public Usuario(String nome, String email, String senha, String tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    // getters e setters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // equals e hashCode para comparações de objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // toString para exibir informações do usuário
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                '}';
    }
}
