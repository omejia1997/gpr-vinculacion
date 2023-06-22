package ec.edu.espe.gpr.vinculacion.model.dashboard;

import lombok.Data;

import java.util.List;

import ec.edu.espe.gpr.vinculacion.model.ProyectoVinculacion;

@Data
public class DashboardProyectoInvestigacion {
    private String name;
    private Double value;
    private Integer valueTotal;
    private ProyectoVinculacion proyecto;
    private List<DashboardTareaInvestigacion> dasboardTareaInvestigacionList;
}