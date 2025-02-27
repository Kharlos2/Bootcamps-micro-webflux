package co.com.pragma.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveBootcampDTO {

    private String name;
    private String description;
    private List<Long> capabilitiesIds;

}
