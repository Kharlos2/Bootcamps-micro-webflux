package co.com.pragma.api.handlers;

import co.com.pragma.api.dto.SaveBootcampDTO;
import co.com.pragma.api.dto.SaveBootcampResponseDTO;
import co.com.pragma.api.mapper.IBootcampMapper;
import co.com.pragma.model.bootcamp.api.IBootcampServicePort;
import co.com.pragma.model.bootcamp.model.Bootcamp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    private final IBootcampServicePort bootcampServicePort;
    private final IBootcampMapper bootcampMapper;

    public Handler(IBootcampServicePort bootcampServicePort, IBootcampMapper bootcampMapper) {
        this.bootcampServicePort = bootcampServicePort;
        this.bootcampMapper = bootcampMapper;
    }

    public Mono<ServerResponse> saveBootcamp (ServerRequest serverRequest) {
        Mono<Bootcamp> capacityMono = serverRequest
                .bodyToMono(SaveBootcampDTO.class)
                .map(bootcampMapper::toModel);
        return capacityMono.flatMap(saveCapacity->
                ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(bootcampServicePort.save(saveCapacity).map(bootcampMapper::toSaveResponseDTO), SaveBootcampResponseDTO.class)
        );
    }
}
