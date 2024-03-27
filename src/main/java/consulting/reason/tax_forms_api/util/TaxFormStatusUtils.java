package consulting.reason.tax_forms_api.util;

import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import consulting.reason.tax_forms_api.exception.TaxFormStatusException;

public class TaxFormStatusUtils {
    public static void save(TaxForm taxForm) throws TaxFormStatusException {
        if (taxForm.getStatus().equals(TaxFormStatus.SUBMITTED) ||
                taxForm.getStatus().equals(TaxFormStatus.ACCEPTED)) {
            throw new TaxFormStatusException(
                    taxForm,
                    TaxFormStatus.IN_PROGRESS
            );
        }

        taxForm.setStatus(TaxFormStatus.IN_PROGRESS);
    }

    public static void submit(TaxForm taxForm) throws TaxFormStatusException {
        if (!taxForm.getStatus().equals(TaxFormStatus.IN_PROGRESS)) {
            throw new TaxFormStatusException(
                    taxForm,
                    TaxFormStatus.IN_PROGRESS
            );
        }
        taxForm.setStatus(TaxFormStatus.SUBMITTED);
    }

    public static void returnForm(TaxForm taxForm) throws TaxFormStatusException {
        // Implement with task 4
    }

    public static void accept(TaxForm taxForm) throws TaxFormStatusException {
        // Implement with task 5
    }
}
