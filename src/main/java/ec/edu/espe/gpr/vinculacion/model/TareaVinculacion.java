package ec.edu.espe.gpr.vinculacion.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = "tareaVinculacion")
@TypeAlias("tareaVinculacion")
public class TareaVinculacion {

    @Id 
    private String id;
    
    private String nombreTarea;

    private Date fechaCreaciontarea;

    private String observacionTarea;

    private Character estadoTarea;

    private Date fechaEntregaTarea;

    private String nombreArchivoTareaEnStorage;

    private String nombreArchivoTarea;

    private String idDocenteRevisor;

    private String nombreDocenteRevisor;

    private String periodo;

    private Date fechaInicioTarea;

    private Date fechaFinTarea;

    private Integer cantidadRepeticiones;

    private String tipoActividad;

    private ProyectoVinculacion proyecto;

}
