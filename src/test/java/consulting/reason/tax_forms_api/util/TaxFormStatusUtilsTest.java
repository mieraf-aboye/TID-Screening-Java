package consulting.reason.tax_forms_api.util;

import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import consulting.reason.tax_forms_api.exception.TaxFormStatusException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TaxFormStatusUtilsTest {
    private TaxForm taxForm;

    @BeforeEach
    void before() {
        taxForm = TaxForm.builder()
                .id(1)
                .formName("Test Tax Form")
                .formYear(2024)
                .status(TaxFormStatus.NOT_STARTED)
                .build();
    }

    @ParameterizedTest
    @EnumSource(value = TaxFormStatus.class, names = {
            "NOT_STARTED",
            "IN_PROGRESS"
    })
    void testSavePermitted(TaxFormStatus taxFormStatus) {
        taxForm.setStatus(taxFormStatus);
        TaxFormStatusUtils.save(taxForm);
        assertThat(taxForm.getStatus()).isEqualTo(TaxFormStatus.IN_PROGRESS);
    }

    @ParameterizedTest
    @EnumSource(value = TaxFormStatus.class, names = {
            "SUBMITTED",
            "ACCEPTED"
    })
    void testSaveNotPermitted(TaxFormStatus taxFormStatus) {
        taxForm.setStatus(taxFormStatus);
        TaxFormStatusException taxFormStatusException = new TaxFormStatusException(
                taxForm,
                TaxFormStatus.IN_PROGRESS
        );

        assertThatThrownBy(() -> TaxFormStatusUtils.save(taxForm))
                .isInstanceOf(TaxFormStatusException.class)
                .hasMessage(taxFormStatusException.getMessage());
    }
}
