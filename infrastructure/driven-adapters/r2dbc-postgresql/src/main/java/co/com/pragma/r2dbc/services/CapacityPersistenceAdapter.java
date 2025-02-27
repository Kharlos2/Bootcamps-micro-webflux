package co.com.pragma.r2dbc.services;

import co.com.pragma.model.bootcamp.exceptions.HttpException;
import co.com.pragma.model.bootcamp.model.CapacityBootcamp;
import co.com.pragma.model.bootcamp.model.ValidationResponse;
import co.com.pragma.model.bootcamp.spi.ICapacityPersistencePort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class CapacityPersistenceAdapter implements ICapacityPersistencePort {

    private final WebClient webClient;

    public CapacityPersistenceAdapter(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override // Preguntar si esta bien implementado este metodo de guardado o se tiene que hacer como en technologies
    public Mono<ValidationResponse> saveCapacityBootcamp(CapacityBootcamp capacityBootcamp) {
        return webClient.post()
                .uri("capacity-bootcamp")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(capacityBootcamp)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .defaultIfEmpty("Internal Server Error")
                                .flatMap(errorMessage ->
                                        Mono.error(new HttpException(500,"Error en capacity-bootcamp: " + errorMessage))
                                )
                )
                .bodyToMono(ValidationResponse.class)
                .onErrorResume(ex -> Mono.just(new ValidationResponse("Error al conectar con capacity-bootcamp: " + ex.getMessage(), false)));
    }
}
