package annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = validators.TipoUsuarioValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoUsuario {

    String message() default "Tipo de usuário inválido. Deve ser 'administrador' ou 'aluno'.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
