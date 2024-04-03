package consulting.reason.tax_forms_api.entity;

import consulting.reason.tax_forms_api.enums.TaxFormHistoryType;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tax_form_history")
@Entity
public class TaxFormHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @CreationTimestamp
    private ZonedDateTime createdAt;
    @Enumerated(value = EnumType.STRING)
    @Column(name="type", nullable = false)
    private TaxFormHistoryType type;
    @ManyToOne
    @JoinColumn(name="tax_form_id", nullable=false)
    private TaxForm taxForm;
}
