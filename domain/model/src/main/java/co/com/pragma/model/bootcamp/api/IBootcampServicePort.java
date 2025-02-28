package co.com.pragma.model.bootcamp.api;

import co.com.pragma.model.bootcamp.model.Bootcamp;
import co.com.pragma.model.bootcamp.model.PagedResponseBootcamp;
import reactor.core.publisher.Mono;

public interface IBootcampServicePort {

    Mono<Bootcamp> save (Bootcamp bootcamp);
    Mono<PagedResponseBootcamp> findAllBootcamps (int page, int size, String sortBy, String sortOrder );

}
