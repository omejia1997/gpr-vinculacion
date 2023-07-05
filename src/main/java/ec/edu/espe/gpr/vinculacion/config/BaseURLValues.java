package ec.edu.espe.gpr.vinculacion.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BaseURLValues {
    private final String gprStorageURL;

    private final String gprMicroserviceInvestigationURL;

    public BaseURLValues(@Value("${gpr.storage.base-url}") String gprStorageURL,
                         @Value("${gpr.microservice.investigation.base-url}") String gprMicroserviceInvestigationURL) {
        this.gprStorageURL = gprStorageURL;
        this.gprMicroserviceInvestigationURL = gprMicroserviceInvestigationURL;
    }
}
