package consulting.reason.tax_forms_api.repository;

import consulting.reason.tax_forms_api.entity.TaxForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxFormRepository extends JpaRepository<TaxForm, Integer> {
    List<TaxForm> findAllByFormYear(Integer formYear);
}
