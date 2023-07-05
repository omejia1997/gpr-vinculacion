package ec.edu.espe.gpr.vinculacion.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import ec.edu.espe.gpr.vinculacion.model.microservicegpr.Docente;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TareaIndicador;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "tareaDocente")
@TypeAlias("tareaDocente")
public class TareaDocenteVinculacion{

    @Id 
    private String id;
    
    private TareaVinculacion tarea;

    private String nombreArchivoTareaDocenteEnStorage;
    
    private String descripcionTareadocente;
    
    private String estadoTareaDocente;

    private String nombreArchivoTareaDocente;

    private Date fechaEntregadaTareaDocente;

    private String cedulaDocenteRevisor;

    private Docente docente;
    
    private List<TareaIndicador> tareaIndicadorList;
}