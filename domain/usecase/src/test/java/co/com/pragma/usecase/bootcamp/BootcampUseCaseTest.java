package co.com.pragma.usecase.bootcamp;

import co.com.pragma.model.bootcamp.exceptions.CustomException;
import co.com.pragma.model.bootcamp.exceptions.ExceptionsEnum;
import co.com.pragma.model.bootcamp.exceptions.HttpException;
import co.com.pragma.model.bootcamp.model.*;
import co.com.pragma.model.bootcamp.spi.IBootcampPersistencePort;
import co.com.pragma.model.bootcamp.spi.ICapacityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.*;

class BootcampUseCaseTest {

    @Mock
    private IBootcampPersistencePort bootcampPersistencePort;

    @Mock
    private ICapacityPersistencePort capacityPersistencePort;

    @InjectMocks
    private BootcampUseCase bootcampUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void save_WhenBootcampAlreadyExists_ShouldThrowDuplicateBootcampException() {
        Bootcamp bootcamp = new Bootcamp(1L,"Java","Pragma",2, List.of(1L,2L));

        when(bootcampPersistencePort.findByName(bootcamp.getName()))
                .thenReturn(Mono.just(bootcamp));

        StepVerifier.create(bootcampUseCase.save(bootcamp))
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(CustomException.class);
                    assertThat(((CustomException) throwable).getStatus())
                            .isEqualTo(ExceptionsEnum.DUPLICATE_BOOTCAMP.getHttpStatus());
                })
                .verify();
    }

    @Test
    void save_WhenValidBootcamp_ShouldSaveSuccessfully() {
        Bootcamp bootcamp = new Bootcamp(1L,"New Bootcamp","s", 2,List.of(1L, 2L));
        Bootcamp savedBootcamp = new Bootcamp(99L, "New Bootcamp", "s", 2,List.of(1L, 2L));
        ValidationResponse validationResponse = new ValidationResponse("Success", true);

        when(bootcampPersistencePort.findByName(bootcamp.getName()))
                .thenReturn(Mono.empty());

        when(bootcampPersistencePort.save(bootcamp))
                .thenReturn(Mono.just(savedBootcamp));

        when(capacityPersistencePort.saveCapacityBootcamp(any()))
                .thenReturn(Mono.just(validationResponse));

        StepVerifier.create(bootcampUseCase.save(bootcamp))
                .expectNext(savedBootcamp)
                .verifyComplete();
    }

    @Test
    void save_WhenCapacityBootcampFails_ShouldRollbackAndThrowHttpException() {
        Bootcamp bootcamp = new Bootcamp(1L,"New Bootcamp","s", 2,List.of(1L, 2L));
        Bootcamp savedBootcamp = new Bootcamp(99L, "New Bootcamp", "s", 2,List.of(1L, 2L));
        ValidationResponse failedValidation = new ValidationResponse("Capacity Error", false);

        when(bootcampPersistencePort.findByName(bootcamp.getName()))
                .thenReturn(Mono.empty());

        when(bootcampPersistencePort.save(bootcamp))
                .thenReturn(Mono.just(savedBootcamp));

        when(capacityPersistencePort.saveCapacityBootcamp(any()))
                .thenReturn(Mono.just(failedValidation));

        when(bootcampPersistencePort.delete(savedBootcamp.getId()))
                .thenReturn(Mono.empty());

        StepVerifier.create(bootcampUseCase.save(bootcamp))
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(HttpException.class);
                    assertThat(((HttpException) throwable).getStatus()).isEqualTo(400);
                    assertThat(throwable.getMessage()).isEqualTo("Capacity Error");
                })
                .verify();

        verify(bootcampPersistencePort).delete(savedBootcamp.getId());
    }

    @Test
    void save_WhenBootcampHasDuplicateCapabilities_ShouldThrowException() {
        Bootcamp bootcamp = new Bootcamp(1L, "Invalid Bootcamp","s",3, List.of(1L, 2L, 2L));

        StepVerifier.create(bootcampUseCase.save(bootcamp))
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(CustomException.class);
                    assertThat(((CustomException) throwable).getStatus())
                            .isEqualTo(ExceptionsEnum.DUPLICATE_CAPACITY.getHttpStatus());
                })
                .verify();
    }
    @Test
    void save_WhenBootcampNotHasCapabilities_ShouldThrowException() {
        Bootcamp bootcamp = new Bootcamp(1L, "Invalid Bootcamp","s",3, List.of());

        StepVerifier.create(bootcampUseCase.save(bootcamp))
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(CustomException.class);
                    assertThat(((CustomException) throwable).getStatus())
                            .isEqualTo(ExceptionsEnum.MIN_CAPACITY.getHttpStatus());
                })
                .verify();
    }
    @Test
    void save_WhenBootcampMaxCapabilities_ShouldThrowException() {
        Bootcamp bootcamp = new Bootcamp(1L, "Invalid Bootcamp","s",3, List.of(1L, 2L, 2L, 2L));

        StepVerifier.create(bootcampUseCase.save(bootcamp))
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable).isInstanceOf(CustomException.class);
                    assertThat(((CustomException) throwable).getStatus())
                            .isEqualTo(ExceptionsEnum.MIN_CAPACITY.getHttpStatus());
                })
                .verify();
    }

    @Test
    void findAllBootcamps_ShouldReturnPagedResponseWithBootcamps() {
        int page = 0, size = 10;
        String sortBy = "name", sortOrder = "asc";

        Bootcamp bootcamp1 = new Bootcamp(1L, "Java Bootcamp", "pragma", 2,List.of(1L, 2L));
        Bootcamp bootcamp2 = new Bootcamp(2L, "Spring Bootcamp","pragma", 1,List.of(3L));
        List<Bootcamp> bootcamps = List.of(bootcamp1, bootcamp2);

        List<Capacity> capacities1 =
                List.of(new Capacity(1L, "Java",List.of(new Technology()),1),
                        new Capacity(2L, "Spring",List.of(new Technology()),1));
        List<Capacity> capacities2 = List.of(new Capacity(3L, "WebFlux",List.of(new Technology()),1));

        long totalBootcamps = 2L;

        when(bootcampPersistencePort.findAllBootcamps(page, size, sortBy, sortOrder))
                .thenReturn(Flux.fromIterable(bootcamps));

        when(capacityPersistencePort.getCapabilitiesByBootcampId(1L))
                .thenReturn(Flux.fromIterable(capacities1));

        when(capacityPersistencePort.getCapabilitiesByBootcampId(2L))
                .thenReturn(Flux.fromIterable(capacities2));

        when(bootcampPersistencePort.countBootcamps())
                .thenReturn(Mono.just(totalBootcamps));

        // Ejecutar y verificar
        StepVerifier.create(bootcampUseCase.findAllBootcamps(page, size, sortBy, sortOrder))
                .assertNext(response -> {
                    assertThat(response.getCount()).isEqualTo(totalBootcamps);
                    assertThat(response.getItems()).hasSize(2);

                    CapacityBootcampSearch first = response.getItems().get(0);
                    assertThat(first.getId()).isEqualTo(1L);
                    assertThat(first.getName()).isEqualTo("Java Bootcamp");
                    assertThat(first.getCapacityList()).hasSize(2);
                    assertThat(first.getCapacityList().get(0).getName()).isEqualTo("Java");

                    CapacityBootcampSearch second = response.getItems().get(1);
                    assertThat(second.getId()).isEqualTo(2L);
                    assertThat(second.getCapacityList()).hasSize(1);
                    assertThat(second.getCapacityList().get(0).getName()).isEqualTo("WebFlux");
                })
                .verifyComplete();
    }

    @Test
    void findAllBootcamps_WhenNoBootcamps_ShouldReturnEmptyResponse() {
        int page = 0, size = 10;

        when(bootcampPersistencePort.findAllBootcamps(page, size, "name", "asc"))
                .thenReturn(Flux.empty());

        when(bootcampPersistencePort.countBootcamps())
                .thenReturn(Mono.just(0L));

        StepVerifier.create(bootcampUseCase.findAllBootcamps(page, size, "name", "asc"))
                .assertNext(response -> {
                    assertThat(response.getItems()).isEmpty();
                })
                .verifyComplete();
    }

    @Test
    void findAllBootcamps_ShouldCacheCapabilitiesCall() {
        int page = 0, size = 10;
        Bootcamp bootcamp = new Bootcamp(1L, "Java Bootcamp","pragma",2, List.of(1L, 2L));
        List<Capacity> capacities = List.of(
                new Capacity(1L, "Java",List.of(new Technology()),1),
                new Capacity(2L, "Spring",List.of(new Technology()),1));

        when(bootcampPersistencePort.findAllBootcamps(page, size, "name", "asc"))
                .thenReturn(Flux.just(bootcamp));

        when(capacityPersistencePort.getCapabilitiesByBootcampId(bootcamp.getId()))
                .thenReturn(Flux.fromIterable(capacities));

        when(bootcampPersistencePort.countBootcamps())
                .thenReturn(Mono.just(1L));

        StepVerifier.create(bootcampUseCase.findAllBootcamps(page, size, "name", "asc"))
                .expectNextCount(1)
                .verifyComplete();

        verify(capacityPersistencePort, times(1)).getCapabilitiesByBootcampId(bootcamp.getId());
    }
}