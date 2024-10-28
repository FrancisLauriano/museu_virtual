package annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import validators.TipoPersonagemValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TipoPersonagemValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoPersonagem {

    String message() default "Tipo de personagem inválido. Deve ser um dos tipos permitidos.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
