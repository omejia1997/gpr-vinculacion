package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario{

    private Integer codigoUsuario;
    
    private String nombreUsuario;
    
    private String passwUsuario;
    
    private Date fechaCreUsu;
    
    private Date fechaModUsuario;
    
    private Character estadoUsuario;
    
    private List<Usuper> usuperList;

    private List<Docente> docenteList;
}
