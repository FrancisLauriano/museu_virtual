package validators;

import annotations.TipoUsuario;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class TipoUsuarioValidator implements ConstraintValidator<TipoUsuario, String> {

    @Override
    public void initialize(TipoUsuario constraintAnnotation) {
    }
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return Set.of(
            "administrador", "aluno"
        ).contains(value);
    }

    //@Override
    //public boolean isValid(String value, ConstraintValidatorContext context) {
        // Valida se o valor é "administrador" ou "aluno"
        //return value != null && 
               //(value.equalsIgnoreCase("administrador") || value.equalsIgnoreCase("aluno"));
    //}
}
