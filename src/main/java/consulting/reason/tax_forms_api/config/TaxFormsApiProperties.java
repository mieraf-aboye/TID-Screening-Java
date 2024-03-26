package consulting.reason.tax_forms_api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Validated
@Component
@ConfigurationProperties(prefix = "tax-forms-api")
public class TaxFormsApiProperties {
}
