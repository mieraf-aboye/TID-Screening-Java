package consulting.reason.tax_forms_api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TaxFormDetailsRequest {
    @NotNull
    @Min(0)
    @Max(100000)
    private Integer assessedValue;
    @Min(0)
    @Max(100000)
    private Long appraisedValue;
    @NotNull
    @Min(0)
    @Max(1)
    private Double ratio;
    @Size(max = 500)
    private String comments;
}
