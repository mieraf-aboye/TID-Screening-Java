package consulting.reason.tax_forms_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaxFormNotFoundException extends ResponseStatusException {
    public TaxFormNotFoundException(Integer id) {
        super(
                HttpStatus.NOT_FOUND,
                "Tax form %d not found".formatted(id)
        );
    }
}
