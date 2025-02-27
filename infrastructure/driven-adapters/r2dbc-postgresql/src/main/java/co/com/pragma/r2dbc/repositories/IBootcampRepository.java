package co.com.pragma.r2dbc.repositories;

import co.com.pragma.r2dbc.entities.BootcampEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBootcampRepository extends ReactiveCrudRepository<BootcampEntity,Long> {

    @Query("SELECT * FROM public.bootcamps WHERE name ILIKE :name")
    Mono<BootcampEntity> findByName(String name);
    Flux<BootcampEntity> findAllBy(PageRequest pageable);


}
