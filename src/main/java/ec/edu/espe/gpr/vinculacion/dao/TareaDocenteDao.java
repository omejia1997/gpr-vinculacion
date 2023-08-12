package ec.edu.espe.gpr.vinculacion.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ec.edu.espe.gpr.vinculacion.model.TareaDocenteVinculacion;


public interface TareaDocenteDao extends MongoRepository<TareaDocenteVinculacion, String> {
    List<TareaDocenteVinculacion> findByTareaId(String idTarea);
    List<TareaDocenteVinculacion> findByDocenteCodigoDocenteAndEstadoTareaDocente(Integer codDocente, String estadoTareaDocente);
    List<TareaDocenteVinculacion> findByDocenteCodigoDocenteAndEstadoTareaDocenteNot(Integer codDocente, String estadoTareaDocente);

    List<TareaDocenteVinculacion> findByEstadoTareaDocente(String estadoTareaDocente);
    List<TareaDocenteVinculacion> findByEstadoTareaDocenteAndCedulaDocenteRevisor(String estadoTareaDocente,String cedulaDocenteRevisor);
}