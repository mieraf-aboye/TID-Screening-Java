package consulting.reason.tax_forms_api;

import consulting.reason.tax_forms_api.config.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@Import({
        ModelMapperConfig.class
})
public abstract class AbstractServiceTest {
    @Autowired
    protected ModelMapper modelMapper;
}
