package ec.edu.espe.gpr.vinculacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import ec.edu.espe.gpr.vinculacion.model.TareaDocenteProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaDocenteVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaVinculacion;
import ec.edu.espe.gpr.vinculacion.model.dashboard.DashboardProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.file.FileModel;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TareaIndicador;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TareasRealizadas;
import ec.edu.espe.gpr.vinculacion.services.TareaDocenteVinculacionService;
import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = { "https://yellow-river-0ca1d1510.3.azurestaticapps.net", "http://localhost:4200" })
@RestController
@RequestMapping(path = "/tareaDocente")
@RequiredArgsConstructor
public class TareaDocenteRestController {

    @Autowired
    private TareaDocenteVinculacionService tareaDocenteService;

    @GetMapping(path = "/listarTareasDocentePorCodigoTarea/{id}")
    public ResponseEntity<List<TareaDocenteVinculacion>> listarTareasDocentePorCodigoTarea(@PathVariable String id) {
        try {
            List<TareaDocenteVinculacion> tareas = this.tareaDocenteService.listarTareasDocentePorCodigoTarea(id);
            return ResponseEntity.ok(tareas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/obtenerArchivoTarea/{idTarea}")
    public ResponseEntity<FileModel> obtenerUrlArchivo(@PathVariable String idTarea) {
        try {

            FileModel fileModel = this.tareaDocenteService.obtenerUrlArchivo(idTarea);
            return ResponseEntity.ok(fileModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/obtenerArchivoTareaDocente/{codigoTareaDocente}")
    public ResponseEntity<FileModel> obtenerArchivoTareaDocente(@PathVariable String codigoTareaDocente) {
        try {

            FileModel fileModel = this.tareaDocenteService.obtenerArchivoTareaDocente(codigoTareaDocente);
            return ResponseEntity.ok(fileModel);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/listarTodasTareasPorProyecto/{idProyecto}")
    public ResponseEntity<List<TareaDocenteProyectoVinculacion>> listarTodasTareasPorProyecto(@PathVariable String idProyecto) {
        try {
            List<TareaDocenteProyectoVinculacion> tareas = this.tareaDocenteService.listarTodasTareasPorProyecto(idProyecto);
            return ResponseEntity.ok(tareas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/obtenerDatosProyectoDashboardVinculacion")
    public ResponseEntity<List<DashboardProyectoVinculacion>> obtenerDatosProyectoDashboardInvestigacion() {
        try {
            List<DashboardProyectoVinculacion> docentes = this.tareaDocenteService.obtenerDatosProyectoDashboardVinculacion();
            return ResponseEntity.ok(docentes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/listarTareaAsignadaPorDocente/{codigoDocente}")
    public ResponseEntity<List<TareaDocenteVinculacion>> listarTareaAsignadaPorDocente(@PathVariable Integer codigoDocente) {
        try {
            List<TareaDocenteVinculacion> tareaDocente = this.tareaDocenteService.listarTareaAsignadaPorDocente(codigoDocente);
            return ResponseEntity.ok(tareaDocente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/listarTareaNoAsignadasPorDocente/{codigoDocente}")
    public ResponseEntity<List<TareaDocenteVinculacion>> listarTareaNoAsignadasPorDocente(@PathVariable Integer codigoDocente) {
        try {
            List<TareaDocenteVinculacion> tareaDocente = this.tareaDocenteService.listarTareasNoAsignadasPorDocente(codigoDocente);
            return ResponseEntity.ok(tareaDocente);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/listarTareasEntregadas/{idDocente}")
    public ResponseEntity<List<TareaDocenteVinculacion>> listarTareasEntregadas(@PathVariable String idDocente) {
        try {
            List<TareaDocenteVinculacion> tareaDocentes = this.tareaDocenteService.listarTareasEntregadas(idDocente);
            return ResponseEntity.ok(tareaDocentes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/listarTodasTareasRevisadas")
    public ResponseEntity<List<TareasRealizadas>> listarTodasTareasRevisadas() {
        try {
            List<TareasRealizadas> tareaDocentes = this.tareaDocenteService.listarTodasTareasRevisadas();
            return ResponseEntity.ok(tareaDocentes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    
    @PostMapping(path = "/crearTarea")
    public ResponseEntity<String> crearTarea(@RequestBody TareaDocenteProyectoVinculacion  tareaDocenteProyecto) {
        try {
            MultipartFile file = null;
            this.tareaDocenteService.crear(tareaDocenteProyecto,file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/crearTareaConArchivo")
    public ResponseEntity<TareaVinculacion> crear(@RequestParam("tareaDocenteProyecto") String strTareaDocenteProyecto,
            @RequestParam("file") MultipartFile file ) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
            TareaDocenteProyectoVinculacion tareaDocenteProyecto = gson.fromJson(strTareaDocenteProyecto, TareaDocenteProyectoVinculacion.class);
            TareaVinculacion tareaVinculacion =this.tareaDocenteService.crear(tareaDocenteProyecto,file);
            return ResponseEntity.ok(tareaVinculacion);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/crearTareaProgramada")
    public ResponseEntity<String> crearTareaProgramada(@RequestBody TareaDocenteProyectoVinculacion  tareaDocenteProyecto) {
        try {
            MultipartFile file = null;
            this.tareaDocenteService.crearTareaProgramada(tareaDocenteProyecto,file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "/crearTareaConArchivoProgramada")
    public ResponseEntity<List<TareaVinculacion>> crearTareaConArchivoProgramada(@RequestParam("tareaDocenteProyecto") String strTareaDocenteProyecto,
            @RequestParam("file") MultipartFile file ) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
            TareaDocenteProyectoVinculacion tareaDocenteProyecto = gson.fromJson(strTareaDocenteProyecto, TareaDocenteProyectoVinculacion.class);
            List<TareaVinculacion> tareaVinculacions = this.tareaDocenteService.crearTareaProgramada(tareaDocenteProyecto,file);
            return ResponseEntity.ok(tareaVinculacions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/modificar")
    public ResponseEntity<TareaDocenteVinculacion> modificar(@RequestBody TareaDocenteProyectoVinculacion tareaDocenteProyecto) {
        try {
            TareaDocenteVinculacion tareaDocente = this.tareaDocenteService.modificarDatos(tareaDocenteProyecto,null);
            return ResponseEntity.ok(tareaDocente);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "/editarTareaConArchivo")
    public ResponseEntity<String> editarTareaConArchivo(@RequestParam("tareaDocenteProyecto") String strTareaDocenteProyecto,
            @RequestParam("file") MultipartFile file ) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
            TareaDocenteProyectoVinculacion tareaDocenteProyecto = gson.fromJson(strTareaDocenteProyecto, TareaDocenteProyectoVinculacion.class);
            this.tareaDocenteService.modificarDatos(tareaDocenteProyecto,file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/guardarTareaAsignadaAlProfesor")
    public ResponseEntity<String> guardarTareaAsignadaAlProfesor(
        @RequestParam("tareaIndicadors") String strTareaIndicadors, @RequestParam("idTareaDocente") String id) {
        try {
            MultipartFile file = null;
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
            List<TareaIndicador> tareaIndicadors = gson.fromJson(strTareaIndicadors, new TypeToken<List<TareaIndicador>>(){}.getType());
            this.tareaDocenteService.guardarTareaAsignadaAlProfesor(tareaIndicadors,file,id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/guardarArchivoTareaAsignadaAlProfesor")
    public ResponseEntity<TareaDocenteVinculacion> guardarArchivoTareaAsignadaAlProfesor(@RequestParam("file") MultipartFile file,
            @RequestParam("tareaIndicadors") String strTareaIndicadors, @RequestParam("idTareaDocente") String id) {
        try {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm").create();
            List<TareaIndicador> tareaIndicadors = gson.fromJson(strTareaIndicadors, new TypeToken<List<TareaIndicador>>(){}.getType());
            TareaDocenteVinculacion tareaDocenteVinculacion = this.tareaDocenteService.guardarTareaAsignadaAlProfesor(tareaIndicadors,file,id);
            return ResponseEntity.ok(tareaDocenteVinculacion);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/aprobarTareaDocente")
    public ResponseEntity<String> aprobarTareaDocente(@RequestBody TareaDocenteVinculacion tareaDocente) {
        try {
            this.tareaDocenteService.aprobarTareaDocente(tareaDocente);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/denegarTareaDocente")
    public ResponseEntity<String> denegarTareaDocente(@RequestBody TareaDocenteVinculacion tareaDocente) {
        try {
            this.tareaDocenteService.denegarTareaDocente(tareaDocente);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/eliminarTarea/{codigoTarea}")
    public ResponseEntity<String> eliminarTarea(@PathVariable String codigoTarea) {
        try {
            this.tareaDocenteService.eliminarTarea(codigoTarea);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }

}
