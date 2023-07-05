package ec.edu.espe.gpr.vinculacion.model;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TipoProceso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "ProyectoVinculacion")
@TypeAlias("ProyectoVinculacion")
public class ProyectoVinculacion{
    
    @Id 
    private String id;
    
    private String nombreProyecto;

    private String nombreDirectorProyecto;
    
    private Date fechaCreacionproyecto;
    
    private String descripcionProyecto;
    
    private String estadoProyecto;

    private TipoProceso tipoProceso;

    private String tipoFinanciamiento;

    private String propiedadProyecto;
    
    //private List<TareaVinculacion> tareaList;
}
