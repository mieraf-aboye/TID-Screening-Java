package consulting.reason.tax_forms_api.controller;

import consulting.reason.tax_forms_api.AbstractControllerTest;
import consulting.reason.tax_forms_api.dto.TaxFormDetailsDto;
import consulting.reason.tax_forms_api.dto.TaxFormDto;
import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;
import consulting.reason.tax_forms_api.enums.TaxFormStatus;
import consulting.reason.tax_forms_api.service.TaxFormService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TaxFormController.class)
public class TaxFormControllerTest extends AbstractControllerTest {
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    private TaxFormService taxFormService;
    private final TaxFormDetailsDto taxFormDetailsDto = TaxFormDetailsDto.builder()
            .ratio(0.5)
            .assessedValue(100)
            .appraisedValue(1000L)
            .comments("testing")
            .build();
    private final TaxFormDetailsRequest taxFormDetailsRequest = TaxFormDetailsRequest.builder()
            .ratio(0.5)
            .assessedValue(100)
            .appraisedValue(1000L)
            .comments("testing")
            .build();
    private final TaxFormDto taxFormDto = TaxFormDto.builder()
            .id(1)
            .details(taxFormDetailsDto)
            .formName("Testing form RCC")
            .formYear(2024)
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .build();

    @BeforeEach
    void before() {
        taxFormDetailsRequest.setRatio(0.5);
        taxFormDetailsRequest.setAssessedValue(100);
        taxFormDetailsRequest.setAppraisedValue(1000L);
        taxFormDetailsRequest.setComments("testing");
    }

    @Test
    void testFindAllByYear() throws Exception {
        given(taxFormService.findAllByYear(2024)).willReturn(List.of(taxFormDto));

        mockMvc.perform(get(Endpoints.FORMS)
                        .param("year", "2024")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(List.of(taxFormDto))));
    }

    @Test
    void testFindById() throws Exception {
        given(taxFormService.findById(taxFormDto.getId())).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(get(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(taxFormDto)));
    }

    @Test
    void testFindByIdHandlesNotFound() throws Exception {
        mockMvc.perform(get(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSave() throws Exception {
        given(taxFormService.save(taxFormDto.getId(), taxFormDetailsRequest)).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(taxFormDto)));
    }

    @Test
    void testSaveHandlesNotFound() throws Exception {
        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveWithBadRatioShouldFail() throws Exception {
        taxFormDetailsRequest.setRatio(1.1);
        given(taxFormService.save(taxFormDto.getId(), taxFormDetailsRequest)).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveWithBadAssessedValueShouldFail() throws Exception {
        taxFormDetailsRequest.setAssessedValue(100001);
        given(taxFormService.save(taxFormDto.getId(), taxFormDetailsRequest)).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveWithBadCommentsFail() throws Exception {
        String badComment = new String(new char[501]).replace('\0', ' ');
        taxFormDetailsRequest.setComments(badComment);
        given(taxFormService.save(taxFormDto.getId(), taxFormDetailsRequest)).willReturn(Optional.of(taxFormDto));

        mockMvc.perform(patch(Endpoints.FORMS + "/" + taxFormDto.getId())
                        .content(objectMapper.writeValueAsString(taxFormDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSubmit() throws Exception {
        taxFormDto.setStatus(TaxFormStatus.IN_PROGRESS);
        given(taxFormService.submit(taxFormDto.getId())).willReturn(taxFormDto);

        mockMvc.perform(post(Endpoints.FORMS + "/submit/" + taxFormDto.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(taxFormDto)));
    }

}
