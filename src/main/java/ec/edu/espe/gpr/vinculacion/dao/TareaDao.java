package ec.edu.espe.gpr.vinculacion.dao;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import ec.edu.espe.gpr.vinculacion.model.ProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaVinculacion;

public interface TareaDao extends MongoRepository<TareaVinculacion, String> {
    List<TareaVinculacion> findByIdDocenteRevisor(String idDocenteRevisor);
    List<TareaVinculacion> findByIdDocenteRevisorAndProyecto(String idDocenteRevisor,ProyectoVinculacion proyecto);
    List<TareaVinculacion> findByProyecto(ProyectoVinculacion proyecto);
}