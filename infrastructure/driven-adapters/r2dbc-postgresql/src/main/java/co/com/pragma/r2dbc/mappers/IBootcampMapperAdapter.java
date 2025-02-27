package co.com.pragma.r2dbc.mappers;

import co.com.pragma.model.bootcamp.model.Bootcamp;
import co.com.pragma.r2dbc.entities.BootcampEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IBootcampMapperAdapter {

    Bootcamp toModel(BootcampEntity bootcampEntity);

    BootcampEntity toEntity(Bootcamp bootcamp);

}
