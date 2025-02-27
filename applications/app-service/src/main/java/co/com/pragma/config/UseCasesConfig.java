package co.com.pragma.config;

import co.com.pragma.model.bootcamp.api.IBootcampServicePort;
import co.com.pragma.model.bootcamp.spi.IBootcampPersistencePort;
import co.com.pragma.model.bootcamp.spi.ICapacityPersistencePort;
import co.com.pragma.r2dbc.mappers.IBootcampMapperAdapter;
import co.com.pragma.r2dbc.repositories.IBootcampRepository;
import co.com.pragma.r2dbc.services.BootcampPersistenceAdapter;
import co.com.pragma.r2dbc.services.CapacityPersistenceAdapter;
import co.com.pragma.usecase.bootcamp.BootcampUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class UseCasesConfig {


        private final IBootcampRepository bootcampRepository;
        private final IBootcampMapperAdapter bootcampMapper;
        private final WebClient webClient;

        public UseCasesConfig(IBootcampRepository bootcampRepository, IBootcampMapperAdapter bootcampMapper, WebClient webClient) {
                this.bootcampRepository = bootcampRepository;
                this.bootcampMapper = bootcampMapper;
                this.webClient = webClient;
        }

        @Bean
        public IBootcampPersistencePort bootcampPersistencePort(){
                return new BootcampPersistenceAdapter(bootcampRepository,bootcampMapper);
        }

        @Bean
        public ICapacityPersistencePort capacityPersistencePort(){
                return new CapacityPersistenceAdapter(webClient);
        }

        @Bean
        public IBootcampServicePort bootcampServicePort(IBootcampPersistencePort bootcampPersistencePort, ICapacityPersistencePort capacityPersistencePort){
                return new BootcampUseCase(bootcampPersistencePort, capacityPersistencePort);
        }

}
