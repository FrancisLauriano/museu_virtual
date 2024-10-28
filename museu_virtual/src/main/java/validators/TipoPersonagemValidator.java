package validators;

import annotations.TipoPersonagem;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Set;

public class TipoPersonagemValidator implements ConstraintValidator<TipoPersonagem, String> {

    @Override
    public void initialize(TipoPersonagem constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return false;
        }
        return Set.of(
            "artista_plastico", "artista_popular", "escritor", 
            "politico", "governante", "cientista", 
            "militar", "ativista", "religioso", 
            "educador", "empresario", "explorador", 
            "heroi_folclorico"
        ).contains(value);
    }
}
