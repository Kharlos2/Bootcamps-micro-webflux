package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.SaveBootcampDTO;
import co.com.pragma.api.dto.SaveBootcampResponseDTO;
import co.com.pragma.model.bootcamp.model.Bootcamp;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBootcampMapper {

    Bootcamp toModel (SaveBootcampDTO saveBootcampDTO);
    SaveBootcampResponseDTO toSaveResponseDTO( Bootcamp bootcamp );
}
