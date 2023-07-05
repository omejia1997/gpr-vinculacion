package ec.edu.espe.gpr.vinculacion.model.dashboard;

import java.util.List;

import ec.edu.espe.gpr.vinculacion.model.TareaDocenteVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaVinculacion;
import lombok.Data;

@Data
public class DashboardTareaVinculacion {
    private String name;
    private Double value;
    private Integer valueTotal;
    private TareaVinculacion tarea;
    private List<Series> series;
    private List<TareaDocenteVinculacion> tareaDocenteVinculacionList;
//    private List<TareaIndicador> tareaIndicadorList;
}
