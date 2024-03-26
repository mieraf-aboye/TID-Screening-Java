package consulting.reason.tax_forms_api.exception;

import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaxFormStatusException extends ResponseStatusException {
    public TaxFormStatusException(TaxForm taxForm, TaxFormStatus taxFormStatus) {
        super(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Cannot update form id %d to status %s from status %s".formatted(
                        taxForm.getId(),
                        taxFormStatus.name(),
                        taxForm.getStatus().name()
                )
        );
    }
}
