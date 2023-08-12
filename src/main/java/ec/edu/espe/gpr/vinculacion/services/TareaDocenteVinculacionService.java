package ec.edu.espe.gpr.vinculacion.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import ec.edu.espe.gpr.vinculacion.model.file.FileRequest;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import ec.edu.espe.gpr.vinculacion.config.BaseURLValues;
import ec.edu.espe.gpr.vinculacion.dao.ProyectoDao;
import ec.edu.espe.gpr.vinculacion.dao.TareaDao;
import ec.edu.espe.gpr.vinculacion.dao.TareaDocenteDao;
import ec.edu.espe.gpr.vinculacion.enums.EstadoTareaDocenteEnum;
import ec.edu.espe.gpr.vinculacion.enums.EstadoTareaEnum;
import ec.edu.espe.gpr.vinculacion.enums.ModulosEnum;
import ec.edu.espe.gpr.vinculacion.model.ProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaDocenteProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaDocenteVinculacion;
import ec.edu.espe.gpr.vinculacion.model.TareaVinculacion;
import ec.edu.espe.gpr.vinculacion.model.dashboard.DashboardProyectoVinculacion;
import ec.edu.espe.gpr.vinculacion.model.dashboard.DashboardTareaVinculacion;
import ec.edu.espe.gpr.vinculacion.model.dashboard.Series;
import ec.edu.espe.gpr.vinculacion.model.file.FileModel;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.Docente;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.Indicador;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TareaIndicador;
import ec.edu.espe.gpr.vinculacion.model.microservicegpr.TareasRealizadas;

@Service
public class TareaDocenteVinculacionService {

    @Autowired
    private TareaDao tareaDao;

    @Autowired
    private ProyectoDao proyectoDao;

    @Autowired
    private TareaDocenteDao tareaDocenteDao;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private BaseURLValues baseURLs;

    @Autowired
    private EmailService emservice;

    public TareaVinculacion obtenerTareaPorCodigoTarea(String codTarea) {
        Optional<TareaVinculacion> tareaOpt = this.tareaDao.findById(codTarea);
        if (tareaOpt.isPresent())
            return tareaOpt.get();
        else
            return null;
    }

    public FileModel obtenerUrlArchivo(String idTarea) {
        TareaVinculacion tarea = this.obtenerTareaPorCodigoTarea(idTarea);
        FileModel fileModel = null;
        if (tarea.getNombreArchivoTarea() != ""
                || tarea.getNombreArchivoTarea() != null) {
            ResponseEntity<FileModel> response = this.restTemplate.getForEntity(
                    baseURLs.getGprStorageURL() + "/getFileTarea/"
                            + ModulosEnum.VINCULACION.getValue() + "/"
                            + tarea.getNombreArchivoTarea() + "/"
                            + tarea.getNombreArchivoTareaEnStorage(),
                    FileModel.class);
            fileModel = response.getBody();
        }
        return fileModel;
    }

    public FileModel obtenerArchivoTareaDocente(String idTareaDocente) {
        TareaDocenteVinculacion tareaDocente = this.tareaDocenteDao.findById(idTareaDocente).get();
        FileModel fileModel = null;
        if (tareaDocente.getNombreArchivoTareaDocente() != ""
                || tareaDocente.getNombreArchivoTareaDocente() != null) {
            ResponseEntity<FileModel> response = this.restTemplate.getForEntity(
                    baseURLs.getGprStorageURL() + "/getFileDocenteTarea/"
                            + ModulosEnum.VINCULACION.getValue() + "/"
                            + tareaDocente.getNombreArchivoTareaDocente() + "/"
                            + tareaDocente.getNombreArchivoTareaDocenteEnStorage(),
                    FileModel.class);
            fileModel = response.getBody();
        }
        return fileModel;
    }

    public List<TareaDocenteVinculacion> listarTareasDocentePorCodigoTarea(String idTarea) {
        return this.tareaDocenteDao.findByTareaId(idTarea);
    }

    public List<TareaDocenteProyectoVinculacion> listarTodasTareasPorProyecto(String idProyecto) {
        List<TareaDocenteProyectoVinculacion> tListDocenteProyecto = new ArrayList<>();
        ProyectoVinculacion proyecto = this.proyectoDao.findById(idProyecto).get();//codigo de proyecto
        List<TareaVinculacion> tarea = this.tareaDao.findByProyectoId(proyecto.getId());
        for (TareaVinculacion t : tarea) {
            TareaDocenteProyectoVinculacion tDocenteProyecto = new TareaDocenteProyectoVinculacion();
            tDocenteProyecto.setTarea(t);
            Boolean check = true;
            List<Docente> docentes = new ArrayList<>();
            Integer count = 0;
            Boolean checkEstadotarea = true;
            List<TareaDocenteVinculacion> tareasDocenteVinculacion = this.tareaDocenteDao.findByTareaId(t.getId());
            for (TareaDocenteVinculacion tDocente : tareasDocenteVinculacion) {
                if (checkEstadotarea) {
                    if (tDocente.getEstadoTareaDocente().equals("ACEPTADO")) {
                        count++;
                    }
                }
                if (tDocente.getEstadoTareaDocente().equals("EN REVISIÓN")
                        || tDocente.getEstadoTareaDocente().equals("DENEGADO")
                        || tDocente.getEstadoTareaDocente().equals("ASIGNADA")) {
                    checkEstadotarea = false;
                }
                if (check) {
                    List<Indicador> tareaIndicadors = new ArrayList<>();

                    for (TareaIndicador tareaIndicador : tDocente.getTareaIndicadorList()) {
                        Indicador indicador = new Indicador(
                                tareaIndicador.getIndicadorCODIGOINDICADOR().getCodigoIndicador(),
                                tareaIndicador.getIndicadorCODIGOINDICADOR().getNombreIndicador(),
                                tareaIndicador.getIndicadorCODIGOINDICADOR().getEstadoIndicador(),
                                tareaIndicador.getDescripcionTareaIndicador());
                        tareaIndicadors.add(indicador);
                    }
                    tDocenteProyecto.setIndicadors(tareaIndicadors);
                    check = false;
                }
                docentes.add(tDocente.getDocente());
            }
            if (checkEstadotarea == false)
                tDocenteProyecto.setClaseCirculoPintar("amarillo");
            else if (count == 0) {
                tDocenteProyecto.setClaseCirculoPintar("rojo");
            } else
                tDocenteProyecto.setClaseCirculoPintar("verde");
            tDocenteProyecto.setDocentes(docentes);
            tListDocenteProyecto.add(tDocenteProyecto);
        }
        return tListDocenteProyecto;
    }

    public List<DashboardProyectoVinculacion> obtenerDatosProyectoDashboardVinculacion() {
        List<ProyectoVinculacion> proyectos = this.proyectoDao.findAll();
        List<DashboardProyectoVinculacion> listDataDashboard = new ArrayList<>();
        Double contProyecto;
        Double contadorTotalProyecto;
        Double contTotaltareas;
        Double contTareas;

        List<DashboardTareaVinculacion> dashboardTareaInvestigacionList = null;
        for (ProyectoVinculacion proyecto : proyectos) {
            contadorTotalProyecto = 0.0;
            contProyecto = 0.0;
            DashboardProyectoVinculacion dashboardProyectoInvestigacion = new DashboardProyectoVinculacion();
            dashboardProyectoInvestigacion.setProyecto(proyecto);
            dashboardTareaInvestigacionList = new ArrayList<>();
            Integer contTareasPorProyecto = 0;
            for (TareaVinculacion tarea : this.tareaDao.findByProyectoId(proyecto.getId())) {

                contTotaltareas = 0.0;
                contTareas = 0.0;
                contTareasPorProyecto++;

                Integer contTareaAsignada = 0;
                Integer contTareaRevision = 0;
                Integer contTareaDenegado = 0;

                DashboardTareaVinculacion dashboardTareaInvestigacion = new DashboardTareaVinculacion();

                List<TareaDocenteVinculacion> tareaDocenteVinculacionList = new ArrayList<>();
                for (TareaDocenteVinculacion tareaDocente : this.tareaDocenteDao.findByTareaId(tarea.getId())) {
                    if (tareaDocente.getEstadoTareaDocente().equals("ACEPTADO")) {
                        contProyecto++;
                        contTareas++;
                    }
                    if (tareaDocente.getEstadoTareaDocente().equals("ASIGNADA"))
                        contTareaAsignada++;
                    if (tareaDocente.getEstadoTareaDocente().equals("EN REVISIÓN"))
                        contTareaRevision++;
                    if (tareaDocente.getEstadoTareaDocente().equals("DENEGADO"))
                        contTareaDenegado++;

                    contadorTotalProyecto++;
                    contTotaltareas++;
                    tareaDocenteVinculacionList.add(tareaDocente);
                }
                dashboardTareaInvestigacion.setTareaDocenteVinculacionList(tareaDocenteVinculacionList);
                List<Series> series = new ArrayList<>();

                Series seriesModel = new Series();
                seriesModel.setName("ASIGNADA");
                seriesModel.setValue(contTareaAsignada);

                series.add(seriesModel);

                seriesModel = new Series();
                seriesModel.setName("EN REVISIÓN\"");
                seriesModel.setValue(contTareaRevision);

                series.add(seriesModel);

                seriesModel = new Series();
                seriesModel.setName("DENEGADO");
                seriesModel.setValue(contTareaDenegado);

                series.add(seriesModel);

                seriesModel = new Series();
                seriesModel.setName("ACEPTADO");
                seriesModel.setValue(contTareas.intValue());

                series.add(seriesModel);

                dashboardTareaInvestigacion.setName(contTareasPorProyecto + "." + tarea.getNombreTarea());
                dashboardTareaInvestigacion.setValue((contTareas / contTotaltareas) * 100);
                dashboardTareaInvestigacion.setValueTotal(contTotaltareas.intValue());
                dashboardTareaInvestigacion.setTarea(tarea);
                dashboardTareaInvestigacion.setSeries(series);
                dashboardTareaInvestigacionList.add(dashboardTareaInvestigacion);
            }
            dashboardProyectoInvestigacion.setName(proyecto.getNombreProyecto());
            dashboardProyectoInvestigacion.setValue((contProyecto / contadorTotalProyecto) * 100);
            dashboardProyectoInvestigacion.setValueTotal(contadorTotalProyecto.intValue());
            dashboardProyectoInvestigacion.setDasboardTareaVinculacionList(dashboardTareaInvestigacionList);
            listDataDashboard.add(dashboardProyectoInvestigacion);
        }
        return listDataDashboard;
    }

    public List<TareaDocenteVinculacion> listarTareasEntregadas(String cedulaDocenteRevisor) {
        return this.tareaDocenteDao.findByEstadoTareaDocenteAndCedulaDocenteRevisor(
                EstadoTareaDocenteEnum.EN_REVISION.getText(), cedulaDocenteRevisor);
    }

    public List<TareaDocenteVinculacion> listarTareaAsignadaPorDocente(Integer codigoDocente) {
        List<TareaDocenteVinculacion> tareas = this.tareaDocenteDao.findByDocenteCodigoDocenteAndEstadoTareaDocenteNot(codigoDocente,EstadoTareaDocenteEnum.ACEPTADO.getValue());
        return tareas;
    }

    public List<TareaDocenteVinculacion> listarTareasNoAsignadasPorDocente(Integer codigoDocente) {
        List<TareaDocenteVinculacion> tareas = this.tareaDocenteDao.findByDocenteCodigoDocenteAndEstadoTareaDocente(codigoDocente,EstadoTareaDocenteEnum.ACEPTADO.getValue());
        return tareas;
    }

    public List<TareasRealizadas> listarTodasTareasRevisadas() {
        List<TareaDocenteVinculacion> tareaDocentes = this.tareaDocenteDao
                .findByEstadoTareaDocente(EstadoTareaDocenteEnum.ACEPTADO.getValue());
        List<TareasRealizadas> tRealizadas = new ArrayList<>();
        for (TareaDocenteVinculacion tareaDocente : tareaDocentes) {
            TareasRealizadas tRealizada = new TareasRealizadas();
            tRealizada.setNombreDocenteRevisor(tareaDocente.getTarea().getNombreDocenteRevisor());
            tRealizada.setNombreProyecto(tareaDocente.getTarea().getProyecto().getNombreProyecto());
            tRealizada.setNombreTarea(tareaDocente.getTarea().getNombreTarea());
            tRealizada.setFechaCreaciontarea(tareaDocente.getTarea().getFechaCreaciontarea());
            tRealizada.setFechaEntregaTarea(tareaDocente.getTarea().getFechaEntregaTarea());
            tRealizada.setResponsable(tareaDocente.getDocente().getNombreDocente() + " "
                    + tareaDocente.getDocente().getApellidoDocente());
            tRealizada.setTareaIndicadors(tareaDocente.getTareaIndicadorList());

            if (tareaDocente.getNombreArchivoTareaDocente() != ""
                    || tareaDocente.getNombreArchivoTareaDocente() != null) {
                tRealizada.setNombreArchivo(tareaDocente.getNombreArchivoTareaDocente());
                ResponseEntity<FileModel> response = this.restTemplate.getForEntity(
                        baseURLs.getGprStorageURL() + "/getFileDocenteTarea/"
                                + ModulosEnum.VINCULACION.getValue() + "/"
                                + tareaDocente.getNombreArchivoTareaDocente() + "/"
                                + tareaDocente.getNombreArchivoTareaDocenteEnStorage(),
                        FileModel.class);
                FileModel fileModel = response.getBody();

                tRealizada.setUrlArchivo(fileModel.getUrl());
            }
            tRealizadas.add(tRealizada);
        }
        return tRealizadas;
    }

    public TareaVinculacion crear(TareaDocenteProyectoVinculacion tareaDocenteProyecto, MultipartFile file) {
        tareaDocenteProyecto.getTarea().setFechaCreaciontarea(new Date());
        tareaDocenteProyecto.getTarea().setEstadoTarea(EstadoTareaEnum.ACTIVE.getValue().charAt(0));
        TareaVinculacion tarea = this.tareaDao.save(tareaDocenteProyecto.getTarea());

        if (file != null) {
            String extensionArchivo = "";
            int i = file.getOriginalFilename().toString().lastIndexOf('.');

            if (i > 0)
                extensionArchivo = file.getOriginalFilename().toString().substring(i + 1);

            tarea.setNombreArchivoTareaEnStorage(tarea.getId() + "." + extensionArchivo);
            tarea.setNombreArchivoTarea(file.getOriginalFilename());
            tarea = this.tareaDao.save(tarea);
            try {
             FileRequest fileRequest = new FileRequest(convertBase64(file), tarea.getNombreArchivoTareaEnStorage());
             this.restTemplate.postForObject(
                    baseURLs.getGprStorageURL() + "/saveFileGuia/" + ModulosEnum.VINCULACION.getValue(), fileRequest,
                    FileRequest.class);
            } catch (Exception e) {
                System.out.println("Se cayo:"+e.getMessage());
            }
        }
        Integer codigoTareaIndicador = 0;
        for (Docente docente : tareaDocenteProyecto.getDocentes()) {
            TareaDocenteVinculacion t = new TareaDocenteVinculacion();
            t.setEstadoTareaDocente(EstadoTareaDocenteEnum.ASIGNADA.getValue());
            t.setDocente(docente);
            t.setTarea(tarea);
            t.setCedulaDocenteRevisor(tarea.getIdDocenteRevisor());
            List<TareaIndicador> tareaIndicadors = new ArrayList<>();
            for (Indicador indicador : tareaDocenteProyecto.getIndicadors()) {
                codigoTareaIndicador++;
                TareaIndicador indicadorBD = new TareaIndicador();
                indicadorBD.setCodigoTareaIndicador(codigoTareaIndicador);
                indicadorBD.setFechaCreacionIndicador(new Date());
                indicadorBD.setIndicadorCODIGOINDICADOR(indicador);
                indicadorBD.setDescripcionTareaIndicador(indicador.getDescripcionIndicador());
                tareaIndicadors.add(indicadorBD);
            }
            t.setTareaIndicadorList(tareaIndicadors);
            this.tareaDocenteDao.save(t);

            emservice.enviarCorreo(docente.getCorreoDocente(), "GPR - Nueva Tarea: " + tarea.getNombreTarea(),
            "Se ha asignado una nueva "+
                             ", y debe ser realizada hasta la fecha de:" + tarea.getFechaEntregaTarea());

        }
        return tarea;
    }

    public List<TareaVinculacion> crearTareaProgramada(TareaDocenteProyectoVinculacion tareaDocenteProyecto, MultipartFile file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("uuuu-MM-dd");
        tareaDocenteProyecto.getTarea().setFechaCreaciontarea(new Date());
        tareaDocenteProyecto.getTarea().setEstadoTarea(EstadoTareaEnum.ACTIVE.getValue().charAt(0));
        List<TareaVinculacion> tareaVinculacions = new ArrayList<>();
        for (int k = 0; k < tareaDocenteProyecto.getTarea().getCantidadRepeticiones(); k++) {
            TareaVinculacion tareaNueva = new TareaVinculacion();
            tareaNueva.setNombreTarea(tareaDocenteProyecto.getTarea().getNombreTarea());
            tareaNueva.setFechaCreaciontarea(tareaDocenteProyecto.getTarea().getFechaCreaciontarea());
            tareaNueva.setObservacionTarea(tareaDocenteProyecto.getTarea().getObservacionTarea());
            tareaNueva.setEstadoTarea(tareaDocenteProyecto.getTarea().getEstadoTarea());
            tareaNueva.setNombreArchivoTareaEnStorage(tareaDocenteProyecto.getTarea().getNombreArchivoTareaEnStorage());
            tareaNueva.setNombreArchivoTarea(tareaDocenteProyecto.getTarea().getNombreArchivoTarea());
            tareaNueva.setIdDocenteRevisor(tareaDocenteProyecto.getTarea().getIdDocenteRevisor());
            tareaNueva.setNombreDocenteRevisor(tareaDocenteProyecto.getTarea().getNombreDocenteRevisor());
            tareaNueva.setPeriodo(tareaDocenteProyecto.getTarea().getPeriodo());
            tareaNueva.setFechaInicioTarea(tareaDocenteProyecto.getTarea().getFechaInicioTarea());

            tareaNueva.setCantidadRepeticiones(tareaDocenteProyecto.getTarea().getCantidadRepeticiones());
            tareaNueva.setTipoActividad(tareaDocenteProyecto.getTarea().getTipoActividad());
            tareaNueva.setProyecto(tareaDocenteProyecto.getTarea().getProyecto());

            LocalDate fechaFinTarea = LocalDate.parse(sdf.format(tareaNueva.getFechaInicioTarea()), formateador);

            if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Diaria")) {
                fechaFinTarea = fechaFinTarea.plusDays(1);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Semanal")) {
                fechaFinTarea = fechaFinTarea.plusWeeks(1);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Mensual")) {
                fechaFinTarea = fechaFinTarea.plusMonths(1);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Bimestral")) {
                fechaFinTarea = fechaFinTarea.plusMonths(6);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Trimestral")) {
                fechaFinTarea = fechaFinTarea.plusMonths(3);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Cuatrimestral")) {
                fechaFinTarea = fechaFinTarea.plusMonths(4);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Semestral")) {
                fechaFinTarea = fechaFinTarea.plusMonths(4);
            } else if (tareaDocenteProyecto.getTarea().getPeriodo().equals("Anual")) {
                fechaFinTarea = fechaFinTarea.plusYears(1);
            }
            try {
                tareaNueva.setFechaFinTarea(sdf.parse(fechaFinTarea.toString()));
                tareaNueva.setFechaEntregaTarea(sdf.parse(fechaFinTarea.toString()));
            } catch (ParseException e) {
                throw new RuntimeException("error al transformar la fecha " + e.getMessage());
            }

            TareaVinculacion tarea = this.tareaDao.save(tareaNueva);
            if (file != null) {
                String extensionArchivo = "";
                int i = file.getOriginalFilename().toString().lastIndexOf('.');

                if (i > 0)
                    extensionArchivo = file.getOriginalFilename().toString().substring(i + 1);

                tarea.setNombreArchivoTareaEnStorage(tarea.getId() + "." + extensionArchivo);
                tarea.setNombreArchivoTarea(file.getOriginalFilename());
                tarea = this.tareaDao.save(tarea);
                try {
                FileRequest fileRequest = new FileRequest(convertBase64(file), tarea.getNombreArchivoTareaEnStorage());
                this.restTemplate.postForObject(
                        baseURLs.getGprStorageURL() + "/saveFileGuia/" + ModulosEnum.VINCULACION.getValue(),
                        fileRequest, FileRequest.class);    
                } catch (Exception e) {
                    System.out.println("Se cayo:"+e.getMessage());
                }
            }
            tareaVinculacions.add(tarea);
            Integer codigoTareaIndicador = 0;
            for (Docente docente : tareaDocenteProyecto.getDocentes()) {
                TareaDocenteVinculacion t = new TareaDocenteVinculacion();
                t.setEstadoTareaDocente(EstadoTareaDocenteEnum.ASIGNADA.getValue());
                t.setDocente(docente);
                t.setTarea(tarea);
                t.setCedulaDocenteRevisor(tarea.getIdDocenteRevisor());

                List<TareaIndicador> tareaIndicadors = new ArrayList<>();
                for (Indicador indicador : tareaDocenteProyecto.getIndicadors()) {
                    codigoTareaIndicador++;
                    TareaIndicador indicadorBD = new TareaIndicador();
                    indicadorBD.setCodigoTareaIndicador(codigoTareaIndicador);
                    indicadorBD.setFechaCreacionIndicador(new Date());
                    indicadorBD.setIndicadorCODIGOINDICADOR(indicador);
                    indicadorBD.setDescripcionTareaIndicador(indicador.getDescripcionIndicador());
                    tareaIndicadors.add(indicadorBD);
                }
                t.setTareaIndicadorList(tareaIndicadors);
                this.tareaDocenteDao.save(t);

                emservice.enviarCorreo(docente.getCorreoDocente(), "GPR - Nueva Tarea: " +
                tarea.getNombreTarea(),
                "Se ha asignado una nueva tarea " +
                        ",y debe ser realizada hasta la fecha de:" + tarea.getFechaEntregaTarea());
            }
            tareaNueva.setFechaInicioTarea(tareaNueva.getFechaFinTarea());
            tareaDocenteProyecto.getTarea().setFechaInicioTarea(tareaNueva.getFechaFinTarea());
        }
        return tareaVinculacions;
    }

    private String convertBase64(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();
            byte[] encodedBytes = Base64.encodeBase64(fileBytes);
            return new String(encodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TareaDocenteVinculacion modificarDatos(TareaDocenteProyectoVinculacion tareaDocenteProyecto, MultipartFile file) {
        String previousNameFile = null;
        TareaVinculacion tarea = this.tareaDao.save(tareaDocenteProyecto.getTarea());
        if (file != null) {
            String extensionArchivo = "";
            int i = file.getOriginalFilename().toString().lastIndexOf('.');

            if (i > 0)
                extensionArchivo = file.getOriginalFilename().toString().substring(i + 1);
            if(tarea.getNombreArchivoTareaEnStorage()!=null){
                previousNameFile = tarea.getNombreArchivoTareaEnStorage();
            }

            tarea.setNombreArchivoTareaEnStorage(tarea.getId().toString() + "." + extensionArchivo);
            tarea.setNombreArchivoTarea(file.getOriginalFilename());
            tarea = this.tareaDao.save(tarea);
            try {
                FileRequest fileRequest = new FileRequest(convertBase64(file), tarea.getNombreArchivoTareaEnStorage());
                fileRequest.setPreviousNameFile(previousNameFile);
                this.restTemplate.postForObject(
                baseURLs.getGprStorageURL() + "/saveFileGuia/" + ModulosEnum.VINCULACION.getValue(), fileRequest,
                FileRequest.class);
            } catch (Exception e) {
            }
        }

        int indice;
        List<TareaDocenteVinculacion> tareaDocenteVinculacions = this.tareaDocenteDao.findByTareaId(tarea.getId());
        for (TareaDocenteVinculacion tareaDocente : tareaDocenteVinculacions) {
            indice = tareaDocenteProyecto.getDocentes().indexOf(tareaDocente.getDocente());
            if (indice == -1) {
                this.tareaDocenteDao.delete(tareaDocente);
            } else
                tareaDocenteProyecto.getDocentes().remove(indice);
        }

        if (tareaDocenteProyecto.getDocentes().size() > 0) {
            for (Docente docente : tareaDocenteProyecto.getDocentes()) {
                TareaDocenteVinculacion t = new TareaDocenteVinculacion();
                t.setEstadoTareaDocente(EstadoTareaDocenteEnum.ASIGNADA.getValue());
                t.setDocente(docente);
                t.setTarea(tareaDocenteProyecto.getTarea());
                t.setCedulaDocenteRevisor(tarea.getIdDocenteRevisor());

                List<TareaIndicador> tareaIndicadors = new ArrayList<>();
                for (Indicador indicador : tareaDocenteProyecto.getIndicadors()) {
                    TareaIndicador indicadorBD = new TareaIndicador();
                    indicadorBD.setFechaCreacionIndicador(new Date());
                    indicadorBD.setIndicadorCODIGOINDICADOR(indicador);
                    indicadorBD.setDescripcionTareaIndicador(indicador.getDescripcionIndicador());
                    tareaIndicadors.add(indicadorBD);
                }
                t.setTareaIndicadorList(tareaIndicadors);
                this.tareaDocenteDao.save(t);

                emservice.enviarCorreo(docente.getCorreoDocente(),
                "GPR - Nueva Tarea: " + tareaDocenteProyecto.getTarea().getNombreTarea(),
                "Se ha asignado una nueva tarea  " +
                        ", y debe ser realizada hasta la fecha de:"
                                 + tareaDocenteProyecto.getTarea().getFechaEntregaTarea());
            }
        }
        return new TareaDocenteVinculacion();
    }

    public TareaDocenteVinculacion guardarTareaAsignadaAlProfesor(List<TareaIndicador> tareaIndicadors, MultipartFile file,
                                                                  String id) {

        TareaDocenteVinculacion tareaDocente = this.tareaDocenteDao.findById(id).get();
        String previousNameFile = null;
        ResponseEntity<Docente> response = this.restTemplate.getForEntity(
                baseURLs.getGprMicroserviceInvestigationURL() + "/api/v1/obtenerDocentePorCedula/"
                        + tareaDocente.getCedulaDocenteRevisor(),
                Docente.class);
        Docente docenteRevisor = response.getBody();

        if (file != null) {
            if(tareaDocente.getNombreArchivoTareaDocenteEnStorage()!=null){
                previousNameFile = tareaDocente.getNombreArchivoTareaDocenteEnStorage();
            }

            tareaDocente.setNombreArchivoTareaDocenteEnStorage(tareaDocente.getId() + ".pdf");// Revisar
            tareaDocente.setNombreArchivoTareaDocente(file.getOriginalFilename());

            try {
            FileRequest fileRequest = new FileRequest(convertBase64(file), tareaDocente.getNombreArchivoTareaDocenteEnStorage());
            fileRequest.setPreviousNameFile(previousNameFile);
            this.restTemplate.postForObject(
                    baseURLs.getGprStorageURL() + "/saveFile/" + ModulosEnum.VINCULACION.getValue(), fileRequest,
                    FileRequest.class);    
            } catch (Exception e) {
            }
        }
        tareaDocente.setTareaIndicadorList(tareaIndicadors);
        tareaDocente.setEstadoTareaDocente(EstadoTareaDocenteEnum.EN_REVISION.getValue());
        this.tareaDocenteDao.save(tareaDocente);

        emservice.enviarCorreo(docenteRevisor.getCorreoDocente(),
                "GPR - Actividad: " + tareaDocente.getTarea().getNombreTarea(),
                "La Actividad perteneciente a: " + tareaDocente.getDocente().getNombreDocente() + " " +
                        tareaDocente.getDocente().getApellidoDocente() + " ha sido enviada y debe ser revisada ");
        return tareaDocente;
    }


    public void aprobarTareaDocente(TareaDocenteVinculacion tareaDocente) {
        tareaDocente.setEstadoTareaDocente(EstadoTareaDocenteEnum.ACEPTADO.getValue());
        emservice.enviarCorreo(tareaDocente.getDocente().getCorreoDocente(),
        "GPR - Actividad: " + tareaDocente.getTarea().getNombreTarea(),
        "Su Actividad ha sido aprobada: ");


        this.tareaDocenteDao.save(tareaDocente);
    }

    public void denegarTareaDocente(TareaDocenteVinculacion tareaDocente) {
        tareaDocente.setEstadoTareaDocente(EstadoTareaDocenteEnum.DENEGADO.getValue());
        emservice.enviarCorreo(tareaDocente.getDocente().getCorreoDocente(),
        "GPR - Actividad: " + tareaDocente.getTarea().getNombreTarea(),
        "Su Actividad ha sido Denegada: ");
        this.tareaDocenteDao.save(tareaDocente);
    }

    public void eliminarTarea(String codigoTarea) {
        TareaVinculacion tarea = this.obtenerTareaPorCodigoTarea(codigoTarea);
        List<TareaDocenteVinculacion> tareaDocentes = this.tareaDocenteDao.findByTareaId(codigoTarea);
        for (TareaDocenteVinculacion tareaDocente : tareaDocentes) {
           this.tareaDocenteDao.delete(tareaDocente);
        }
        this.tareaDao.delete(tarea);
    }

}