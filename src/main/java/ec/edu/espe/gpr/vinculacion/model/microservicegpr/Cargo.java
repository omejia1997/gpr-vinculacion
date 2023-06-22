package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cargo{

    private String codCargo;
    
    private String nombreCargo;
    
    private String descriCargo;
    
}
