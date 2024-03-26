package consulting.reason.tax_forms_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import consulting.reason.tax_forms_api.config.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import({
        ModelMapperConfig.class
})
public abstract class AbstractControllerTest {
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected ObjectMapper objectMapper;
}
