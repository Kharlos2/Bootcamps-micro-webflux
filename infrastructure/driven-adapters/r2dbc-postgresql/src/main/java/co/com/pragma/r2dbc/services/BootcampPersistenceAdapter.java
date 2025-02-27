package co.com.pragma.r2dbc.services;

import co.com.pragma.model.bootcamp.model.Bootcamp;
import co.com.pragma.model.bootcamp.spi.IBootcampPersistencePort;
import co.com.pragma.r2dbc.mappers.IBootcampMapperAdapter;
import co.com.pragma.r2dbc.repositories.IBootcampRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public class BootcampPersistenceAdapter implements IBootcampPersistencePort {

    private final IBootcampRepository bootcampRepository;
    private final IBootcampMapperAdapter bootcampMapper;

    public BootcampPersistenceAdapter(IBootcampRepository bootcampRepository, IBootcampMapperAdapter bootcampMapper) {
        this.bootcampRepository = bootcampRepository;
        this.bootcampMapper = bootcampMapper;
    }

    @Override
    public Mono<Bootcamp> save(Bootcamp bootcamp) {
        return bootcampRepository.save(bootcampMapper.toEntity(bootcamp)).map(bootcampMapper::toModel);
    }

    @Override
    public Mono<Void> delete(Long bootcampId) {
        return bootcampRepository.deleteById(bootcampId);
    }

    @Override
    public Mono<Bootcamp> findByName(String name) {
        return bootcampRepository.findByName(name).map(bootcampMapper::toModel);
    }

    @Override
    public Flux<Bootcamp> findAllBootcamps(int page, int size, String sortBy, String sortOrder) {
        Sort.Direction direction = sortOrder.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return bootcampRepository.findAllBy(pageRequest)
                .map(bootcampMapper::toModel);
    }

    @Override
    public Mono<Long> countBootcamps() {
        return bootcampRepository.count();
    }

}
