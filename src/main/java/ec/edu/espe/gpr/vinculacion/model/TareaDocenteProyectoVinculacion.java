package ec.edu.espe.gpr.vinculacion.model;

import java.util.List;

import ec.edu.espe.gpr.vinculacion.model.microservicegpr.Docente;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.Indicador;

public class TareaDocenteProyectoVinculacion {
    private TareaVinculacion tarea;
    private List<Docente> docentes;
    private List<Indicador> indicadors;
    private String claseCirculoPintar;
        
    public TareaDocenteProyectoVinculacion() {
    }

    public TareaDocenteProyectoVinculacion(TareaVinculacion tarea, List<Docente> docentes, List<Indicador> indicadors) {
        this.tarea = tarea;
        this.docentes = docentes;
        this.indicadors = indicadors;
    }

    public TareaVinculacion getTarea() {
        return tarea;
    }

    public void setTarea(TareaVinculacion tarea) {
        this.tarea = tarea;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }

    public List<Indicador> getIndicadors() {
        return indicadors;
    }

    public void setIndicadors(List<Indicador> indicadors) {
        this.indicadors = indicadors;
    }

    public String getClaseCirculoPintar() {
        return claseCirculoPintar;
    }

    public void setClaseCirculoPintar(String claseCirculoPintar) {
        this.claseCirculoPintar = claseCirculoPintar;
    }
}
