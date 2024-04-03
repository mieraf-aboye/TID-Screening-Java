package consulting.reason.tax_forms_api.config;

import consulting.reason.tax_forms_api.dto.TaxFormDetailsDto;
import consulting.reason.tax_forms_api.dto.TaxFormDto;
import consulting.reason.tax_forms_api.dto.TaxFormHistoryDto;
import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;
import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.entity.TaxFormHistory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(TaxFormDetailsRequest.class, TaxFormDetailsDto.class).setConverter(context -> {
            TaxFormDetailsRequest taxFormDetailsRequest = context.getSource();

            return TaxFormDetailsDto.builder()
                    .appraisedValue(taxFormDetailsRequest.getAppraisedValue())
                    .assessedValue(taxFormDetailsRequest.getAssessedValue())
                    .comments(taxFormDetailsRequest.getComments())
                    .ratio(taxFormDetailsRequest.getRatio())
                    .build();
        });

        modelMapper.typeMap(TaxForm.class, TaxFormDto.class).setConverter(context -> {
            TaxForm taxForm = context.getSource();
            List<TaxFormHistoryDto> taxFormHistoryDtos = taxForm.getTaxFormHistories() == null ? new ArrayList<>() : taxForm.getTaxFormHistories().stream().map(history -> modelMapper.map(history, TaxFormHistoryDto.class)).toList();
            return TaxFormDto.builder()
                    .id(taxForm.getId())
                    .formYear(taxForm.getFormYear())
                    .formName(taxForm.getFormName())
                    .status(taxForm.getStatus())
                    .details(taxForm.getDetails())
                    .createdAt(taxForm.getCreatedAt())
                    .updatedAt(taxForm.getUpdatedAt())
                    .history(taxFormHistoryDtos)
                    .build();
        });

        modelMapper.typeMap(TaxFormHistory.class, TaxFormHistoryDto.class).setConverter(context -> {
            TaxFormHistory taxFormHistory = context.getSource();

            return TaxFormHistoryDto.builder()
                    .id(taxFormHistory.getId())
                    .type(taxFormHistory.getType())
                    .createdAt(taxFormHistory.getCreatedAt())
                    .build();
        });

        return modelMapper;
    }
}