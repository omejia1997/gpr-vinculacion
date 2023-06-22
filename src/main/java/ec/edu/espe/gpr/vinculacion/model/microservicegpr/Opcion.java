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
public class Opcion{
    private String codigoOpcion;
    
    private String descOpcion;
    
    private String urlOpcion;
    
    private List<OpcPer> opcPerList;
    
    private Sistema codSistema;
    
}
