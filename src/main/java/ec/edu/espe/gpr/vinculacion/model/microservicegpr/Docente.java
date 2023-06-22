package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.List;

import ec.edu.espe.gpr.vinculacion.model.TareaDocenteVinculacion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Docente{

    private Integer codigoDocente;
    
    private String idDocente;
    
    private String nombreDocente;
    
    private String apellidoDocente;
    
    private String cedulaDocente;
    
    private String telefonoDocente;
    
    private String correoDocente;
    
    private String sexo;
    
    private String puestoTrabajoDocente;

    private Integer numLogueo;
    
    private Usuario codigoUsuario;
    
    private List<TareaDocenteVinculacion> tareaDocenteList;

    private List<CargoDocente> cargoDocenteList;
}
