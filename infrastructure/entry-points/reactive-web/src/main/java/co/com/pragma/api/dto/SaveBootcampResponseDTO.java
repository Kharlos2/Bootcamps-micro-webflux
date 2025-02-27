package co.com.pragma.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBootcampResponseDTO {

    private Long id;
    private String name;
    private String description;
    private int quantityCapabilities;

}
