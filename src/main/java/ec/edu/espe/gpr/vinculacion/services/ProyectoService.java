package ec.edu.espe.gpr.vinculacion.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.edu.espe.gpr.vinculacion.dao.ProyectoDao;
import ec.edu.espe.gpr.vinculacion.enums.EstadoProyectoEnum;
import ec.edu.espe.gpr.vinculacion.model.ProyectoVinculacion;


@Service
public class ProyectoService {

    @Autowired
	private ProyectoDao proyectoDao;
	
	public ProyectoVinculacion obtenerPorCodigoProyecto(String codProyecto) {	
		Optional<ProyectoVinculacion> proyOpt = this.proyectoDao.findById(codProyecto);
		if (proyOpt.isPresent())
			return proyOpt.get();
		else 
			return null;
	}

    public List<ProyectoVinculacion> listarProyectos() {
        return this.proyectoDao.findAll();
    }

	public List<ProyectoVinculacion> listarProyectosActivos() {
        return this.proyectoDao.findByEstadoProyecto(EstadoProyectoEnum.ACTIVE.getValue());
    }
	
    public void crear(ProyectoVinculacion proyecto) {
        proyecto.setNombreProyecto(proyecto.getNombreProyecto().toUpperCase());
        proyecto.setFechaCreacionproyecto(new Date());
		proyecto.setEstadoProyecto(EstadoProyectoEnum.ACTIVE.getValue());
		this.proyectoDao.save(proyecto);
    }

    public ProyectoVinculacion modificarDatos(ProyectoVinculacion proyecto) {
        proyecto.setNombreProyecto(proyecto.getNombreProyecto().toUpperCase());
        this.proyectoDao.save(proyecto);
        return proyecto;
    }
	
    // public ProyectoVinculacion cambiarEstadoProyecto(Integer codProyecto) {
    //     ProyectoVinculacion proyectoDB = this.obtenerPorCodigoProyecto(codProyecto);
    //     if(proyectoDB.getEstadoProyecto().equals(EstadoProyectoEnum.ACTIVE.getValue()))
    //         proyectoDB.setEstadoProyecto(EstadoProyectoEnum.INACTIVE.getValue());
    //     else 
    //         proyectoDB.setEstadoProyecto(EstadoProyectoEnum.ACTIVE.getValue());
    //     this.proyectoDao.save(proyectoDB);
    //     return proyectoDB;
    // }
}
