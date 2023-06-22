package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoProceso{

    private Integer codigoTipoProceso;

    private String nombreTipoProceso;
    
    private String descripcionTipoProceso;

    private String estadoTipoProceso;
}
