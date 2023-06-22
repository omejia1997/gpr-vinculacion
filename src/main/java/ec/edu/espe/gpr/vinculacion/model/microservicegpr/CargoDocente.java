package ec.edu.espe.gpr.vinculacion.model.microservicegpr;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CargoDocente {

    private Integer codigoCargoDocente;

    private Date fechaActCargoDocente;

    private Docente codigoDocente;

    private Cargo codCargo;
}
