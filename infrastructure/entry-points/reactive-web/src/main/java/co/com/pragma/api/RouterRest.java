package co.com.pragma.api;

import co.com.pragma.api.dto.ErrorModel;
import co.com.pragma.api.dto.SaveBootcampDTO;
import co.com.pragma.api.dto.SaveBootcampResponseDTO;
import co.com.pragma.api.handlers.Handler;
import co.com.pragma.model.bootcamp.model.PagedResponseBootcamp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    @RouterOperations({

            @RouterOperation(
                    path = "/api/bootcamp",
                    beanClass = Handler.class,
                    beanMethod = "saveBootcamp",
                    method = RequestMethod.POST,
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    operation = @Operation(
                            operationId = "saveBootcamp",
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = SaveBootcampDTO.class))),
                            summary = "Creacion de bootcamp",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "201",
                                            description = "Creacion exitosa",
                                            content = @Content(schema = @Schema(implementation = SaveBootcampResponseDTO.class))
                                    ),
                                    @ApiResponse(
                                            responseCode = "400",
                                            description = "Solicitud incorrecta",
                                            content = @Content(schema = @Schema(implementation = ErrorModel.class))
                                    )
                            }
                    )
            ),
            @RouterOperation(
                    path = "/api/bootcamp",
                    beanClass = Handler.class,
                    beanMethod = "findAllBootcamps",
                    method = RequestMethod.GET,
                    produces = { MediaType.APPLICATION_JSON_VALUE },
                    operation = @Operation(
                            operationId = "findAllBootcamps",
                            summary = "Busqueda paginada de bootcamps",
                            parameters = {
                                    @Parameter(name = "page", description = "Número de página", schema = @Schema(type = "integer", defaultValue = "0")),
                                    @Parameter(name = "size", description = "Tamaño de la página", schema = @Schema(type = "integer", defaultValue = "10")),
                                    @Parameter(name = "sortBy", description = "Campo para ordenar (name/quantityTechnologies)", schema = @Schema(type = "string", defaultValue = "name")),
                                    @Parameter(name = "sortOrder", description = "Orden de clasificación (asc/desc)", schema = @Schema(type = "string", defaultValue = "asc"))
                            },
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "Busquda exitosa.",
                                            content = @Content(schema = @Schema(implementation = PagedResponseBootcamp.class))
                                    )
                            }
                    )
            )

    })
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/bootcamp"), handler::saveBootcamp)
                .andRoute(GET("/api/bootcamp"), handler::findAllBootcamps)
        ;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8081/api/").build();
    }

}
