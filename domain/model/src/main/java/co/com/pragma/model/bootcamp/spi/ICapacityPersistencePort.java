package co.com.pragma.model.bootcamp.spi;

import co.com.pragma.model.bootcamp.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICapacityPersistencePort {

    Mono<ValidationResponse> saveCapacityBootcamp (CapacityBootcamp capacityBootcamp);
    Flux<Capacity> getCapabilitiesByBootcampId(Long bootcampId);
}
