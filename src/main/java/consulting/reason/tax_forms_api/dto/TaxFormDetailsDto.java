package consulting.reason.tax_forms_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TaxFormDetailsDto {
    private Integer assessedValue;
    private Long appraisedValue;
    private Double ratio;
    private String comments;
}
