package co.com.pragma.r2dbc.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table("public.bootcamps")
public class BootcampEntity {

    @Id
    private Long id;
    private String name;
    private String description;
    @Column("quantity_capabilities")
    private int quantityCapabilities;

}
