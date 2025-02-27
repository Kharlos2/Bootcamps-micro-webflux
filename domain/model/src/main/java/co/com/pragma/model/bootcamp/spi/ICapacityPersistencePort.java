package co.com.pragma.model.bootcamp.spi;

import co.com.pragma.model.bootcamp.model.CapacityBootcamp;
import co.com.pragma.model.bootcamp.model.ValidationResponse;
import reactor.core.publisher.Mono;

public interface ICapacityPersistencePort {

    Mono<ValidationResponse> saveCapacityBootcamp (CapacityBootcamp capacityBootcamp);

}
