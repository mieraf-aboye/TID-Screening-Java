package consulting.reason.tax_forms_api.service;

import consulting.reason.tax_forms_api.dto.TaxFormDetailsDto;
import consulting.reason.tax_forms_api.dto.TaxFormDto;
import consulting.reason.tax_forms_api.dto.request.TaxFormDetailsRequest;
import consulting.reason.tax_forms_api.entity.TaxForm;
import consulting.reason.tax_forms_api.entity.TaxFormHistory;
import consulting.reason.tax_forms_api.exception.TaxFormNotFoundException;
import consulting.reason.tax_forms_api.repository.TaxFormRepository;
import consulting.reason.tax_forms_api.util.TaxFormHistoryTypeUtils;
import consulting.reason.tax_forms_api.util.TaxFormStatusUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaxFormServiceImpl implements TaxFormService {
    private final TaxFormRepository taxFormRepository;

    private final ModelMapper modelMapper;

    public TaxFormServiceImpl(TaxFormRepository taxFormRepository,
                              ModelMapper modelMapper) {
        this.taxFormRepository = taxFormRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaxFormDto> findAllByYear(Integer year) {
        return taxFormRepository.findAllByFormYear(year).stream()
                .map(taxForm -> modelMapper.map(taxForm, TaxFormDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaxFormDto> findById(Integer id) {
        return taxFormRepository.findById(id)
                .map(taxForm -> modelMapper.map(taxForm, TaxFormDto.class));
    }

    @Override
    @Transactional
    public Optional<TaxFormDto> save(Integer id, TaxFormDetailsRequest taxFormDetailsRequest) {
        return taxFormRepository.findById(id)
                .map(taxForm -> {
                    TaxFormStatusUtils.save(taxForm);
                    taxForm.setDetails(modelMapper.map(taxFormDetailsRequest, TaxFormDetailsDto.class));

                    taxFormRepository.save(taxForm);

                    return modelMapper.map(taxForm, TaxFormDto.class);
                });
    }

    @Override
    @Transactional()
    public TaxFormDto submit(Integer id) {
        TaxForm taxForm = taxFormRepository.findById(id).orElseThrow(() -> new TaxFormNotFoundException(id));
        TaxFormStatusUtils.submit(taxForm);
        TaxFormHistory taxFormHistory = new TaxFormHistory();
        TaxFormHistoryTypeUtils.save(taxFormHistory);
        if (taxForm.getHistory() == null) {
            taxForm.setHistory(new ArrayList<>());
        }
        taxForm.getHistory().add(taxFormHistory);
        return modelMapper.map(taxFormRepository.save(taxForm), TaxFormDto.class);
    }
}
