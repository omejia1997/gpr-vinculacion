package ec.edu.espe.gpr.vinculacion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ec.edu.espe.gpr.vinculacion.model.ProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TipoProceso;

public interface ProyectoDao extends MongoRepository<ProyectoVinculacion, String> {
    List<ProyectoVinculacion> findByEstadoProyecto(String estadoProyecto);
    List<ProyectoVinculacion> findByTipoProceso(TipoProceso tipoProceso);
}