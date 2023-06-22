package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TareaIndicador{

    private Integer codigoTareaIndicador;
    
    private Date fechaCreacionIndicador;
    
    private String valorIndicador;
    
    private String descripcionTareaIndicador;
    
    private Indicador indicadorCODIGOINDICADOR;
    
    //private TareaDocente tareadocenteCODIGOTAREADOCENTE;
}
