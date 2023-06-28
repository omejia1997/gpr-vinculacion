package ec.edu.espe.gpr.vinculacion.model.dashboard;

import lombok.Data;

import java.util.List;

import ec.edu.espe.gpr.vinculacion.model.TareaVinculacion;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TareaIndicador;

@Data
public class DashboardTareaVinculacion {
    private String name;
    private Double value;
    private Integer valueTotal;
    private TareaVinculacion tarea;
    private List<Series> series;
    private List<TareaIndicador> tareaIndicadorList;

}
