package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private static final int WORKLOAD = 12;

    /**
     * Gera um hash para a senha fornecida.
     * @param plainPassword A senha em texto plano.
     * @return O hash da senha.
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(WORKLOAD));
    }

    /**
     * Compara a senha em texto plano com o hash armazenado.
     * @param plainPassword A senha em texto plano fornecida pelo usuário.
     * @param hashedPassword O hash armazenado no banco de dados.
     * @return true se a senha for válida, false caso contrário.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        if (hashedPassword == null || !hashedPassword.startsWith("$2a$")) {
            throw new IllegalArgumentException("Hash inválido.");
        }
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode ser nula ou vazia.");
        }
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
