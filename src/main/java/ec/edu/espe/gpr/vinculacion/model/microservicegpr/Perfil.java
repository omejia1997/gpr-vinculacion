package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.List;

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
public class Perfil {

    private String codigoPerfil;
    
    private String descPerfil;
    
    private String obsPerfil;

    private List<OpcPer> opcPerList;
    
    private List<Usuper> usuperList;

}
