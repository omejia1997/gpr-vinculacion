package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sistema {
    private String codSistema;

    private String descriSistema;

    private List<Opcion> opcionList;
    
}
