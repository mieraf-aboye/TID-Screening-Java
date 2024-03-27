package consulting.reason.tax_forms_api.util;

import consulting.reason.tax_forms_api.entity.TaxFormHistory;
import consulting.reason.tax_forms_api.enums.TaxFormHistoryType;
import consulting.reason.tax_forms_api.exception.TaxFormHistoryTypeException;

public class TaxFormHistoryTypeUtils {
    public static void save(TaxFormHistory taxFormHistory) throws TaxFormHistoryTypeException {
        if (taxFormHistory.getType() != null) {
            throw new TaxFormHistoryTypeException(
                    taxFormHistory,
                    TaxFormHistoryType.SUBMITTED
            );
        }

        taxFormHistory.setType(TaxFormHistoryType.SUBMITTED);
    }
}
