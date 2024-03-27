package consulting.reason.tax_forms_api.exception;

import consulting.reason.tax_forms_api.entity.TaxFormHistory;
import consulting.reason.tax_forms_api.enums.TaxFormHistoryType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TaxFormHistoryTypeException extends ResponseStatusException {
    public TaxFormHistoryTypeException(TaxFormHistory taxFormHistory, TaxFormHistoryType taxFormHistoryType) {
        super(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Cannot update form history to status %s from status %s".formatted(
                        taxFormHistory.getId(),
                        taxFormHistoryType.name(),
                        taxFormHistory.getType().name()
                )
        );
    }
}
