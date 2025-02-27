package co.com.pragma.model.bootcamp.api;

import co.com.pragma.model.bootcamp.model.Bootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {

    Mono<Bootcamp> save (Bootcamp bootcamp);

}
