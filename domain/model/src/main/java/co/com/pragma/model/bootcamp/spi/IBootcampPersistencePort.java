package co.com.pragma.model.bootcamp.spi;

import co.com.pragma.model.bootcamp.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampPersistencePort {

    Mono<Bootcamp> save (Bootcamp bootcamp);
    Mono<Void> delete(Long bootcampId);
    Mono<Bootcamp> findByName(String name);
}
