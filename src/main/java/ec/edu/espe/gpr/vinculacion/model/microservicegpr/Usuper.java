package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.Date;

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
public class Usuper{

    private Integer codUsuper;
    
    private Date fechaAsgUsuper;
    
    private Date fechRetiroUsuperOpcper;
    
    private Perfil codigoPerfil;
    
    private Usuario codigoUsuario;
    
}
