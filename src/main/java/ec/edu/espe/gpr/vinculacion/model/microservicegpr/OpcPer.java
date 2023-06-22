package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.Date;

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
public class OpcPer{

    private String codOpcper;
    
    private Date fechaAsgOpcper;
    
    private Date fechaRetOpcper;
    
    private Opcion codigoOpcion;
    
    private Perfil codigoPerfil;
    
}
