package ec.edu.espe.gpr.vinculacion.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.espe.gpr.vinculacion.model.ProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.services.ProyectoService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins= {"https://yellow-river-0ca1d1510.3.azurestaticapps.net","http://localhost:4200"})
@RestController
@RequestMapping(path = "/proyecto")
@RequiredArgsConstructor
public class ProyectoController {
    @Autowired
    private ProyectoService proyectoService;

    @GetMapping(path = "/listarProyectosVinculacion")
    public ResponseEntity<List<ProyectoVinculacion>> listarProyectos() {
        try {
            List<ProyectoVinculacion> proyectos = this.proyectoService.listarProyectos();
            return ResponseEntity.ok(proyectos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // @GetMapping(path = "/listarProyectosVinculacionPorProceso/{idProceso}")
    // public ResponseEntity<List<ProyectoVinculacion>> listarProyectosPorProceso(@PathVariable Integer idProceso) {
    //     try {
    //         List<Proyecto> proyectos = this.proyectoService.listarProyectosPorProceso(idProceso);
    //         return ResponseEntity.ok(proyectos);
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().build();
    //     }
    // }

    @GetMapping(path = "/listarProyectosActivos")
    public ResponseEntity<List<ProyectoVinculacion>> listarProyectosActivos() {
        try {
            List<ProyectoVinculacion> proyectos = this.proyectoService.listarProyectosActivos();
            return ResponseEntity.ok(proyectos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody ProyectoVinculacion proyecto) {
        try {
            this.proyectoService.crear(proyecto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<ProyectoVinculacion> modificar(@RequestBody ProyectoVinculacion proyecto) {
        try {
            this.proyectoService.modificarDatos(proyecto);
            return ResponseEntity.ok(proyecto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // @PutMapping("/cambiarEstado/{codigoProyecto}")
    // public ResponseEntity<String> cambiarEstado(@PathVariable Integer codigoProyecto) {
    //     try {
    //         this.proyectoService.cambiarEstadoProyecto(codigoProyecto);
    //         return ResponseEntity.ok().build();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return ResponseEntity.badRequest().build();
    //     }
    // }

}
