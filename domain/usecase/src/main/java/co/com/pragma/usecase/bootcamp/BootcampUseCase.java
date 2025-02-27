package co.com.pragma.usecase.bootcamp;


import co.com.pragma.model.bootcamp.api.IBootcampServicePort;
import co.com.pragma.model.bootcamp.exceptions.CustomException;
import co.com.pragma.model.bootcamp.exceptions.ExceptionsEnum;
import co.com.pragma.model.bootcamp.exceptions.HttpException;
import co.com.pragma.model.bootcamp.model.Bootcamp;
import co.com.pragma.model.bootcamp.model.CapacityBootcamp;
import co.com.pragma.model.bootcamp.spi.IBootcampPersistencePort;
import co.com.pragma.model.bootcamp.spi.ICapacityPersistencePort;
import reactor.core.publisher.Mono;

import java.util.HashSet;

public class BootcampUseCase implements IBootcampServicePort {

    private final IBootcampPersistencePort bootcampPersistencePort;
    private final ICapacityPersistencePort capacityPersistencePort;

    public BootcampUseCase(IBootcampPersistencePort bootcampPersistencePort, ICapacityPersistencePort capacityPersistencePort) {
        this.bootcampPersistencePort = bootcampPersistencePort;
        this.capacityPersistencePort = capacityPersistencePort;

    }

    @Override
    public Mono<Bootcamp> save(Bootcamp bootcamp) {

        return validateBootcamp(bootcamp)
                .then(checkIfBootcampExists(bootcamp.getName()))
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(new CustomException(ExceptionsEnum.DUPLICATE_BOOTCAMP));
                    }
                    bootcamp.setQuantityCapabilities(bootcamp.getCapabilitiesIds().size());
                    return bootcampPersistencePort.save(bootcamp)
                            .flatMap(bootcampSaved ->
                                    capacityPersistencePort.saveCapacityBootcamp(
                                                    new CapacityBootcamp(bootcamp.getCapabilitiesIds(), bootcampSaved.getId()))
                                            .flatMap(validationResponse -> {
                                                if (Boolean.FALSE.equals(validationResponse.getValid())) {
                                                    return bootcampPersistencePort.delete(bootcampSaved.getId())
                                                            .then(Mono.error(new HttpException(400, validationResponse.getMessage())));
                                                }
                                                return Mono.just(bootcampSaved);
                                            })
                            );
                });
    }

    private Mono<Void> validateBootcamp(Bootcamp bootcamp) {
        if (bootcamp.getCapabilitiesIds() == null || bootcamp.getCapabilitiesIds().isEmpty()) {
            return Mono.error(new CustomException(ExceptionsEnum.MIN_CAPACITY));
        }

        if (bootcamp.getCapabilitiesIds().size() > 4) {
            return Mono.error(new CustomException(ExceptionsEnum.MAX_CAPABILITIES));
        }

        if (new HashSet<>(bootcamp.getCapabilitiesIds()).size() != bootcamp.getCapabilitiesIds().size()) {
            return Mono.error(new CustomException(ExceptionsEnum.DUPLICATE_CAPACITY));
        }

        return Mono.empty();
    }

    private Mono<Boolean> checkIfBootcampExists(String name) {
        return bootcampPersistencePort.findByName(name)
                .hasElement();
    }
}

