package ec.edu.espe.gpr.vinculacion.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationValues {
  private final String mongoHost;
  private final String mongoDB;
  private final String mongoUsr;
  private final String mongoPwd;
  private final String mongoAut;

  public ApplicationValues(
      @Value("${gpr.tareasvinculacion.mongo.host}") String mongoHost,
      @Value("${gpr.tareasvinculacion.mongo.db}") String mongoDB,
      @Value("${gpr.tareasvinculacion.mongo.usr}") String mongoUsr,
      @Value("${gpr.tareasvinculacion.mongo.pwd}") String mongoPwd,
      @Value("${gpr.tareasvinculacion.mongo.aut}") String mongoAut) {

    this.mongoHost = mongoHost;
    this.mongoDB = mongoDB;
    this.mongoUsr = mongoUsr;
    this.mongoPwd = mongoPwd;
    this.mongoAut = mongoAut;
  }
}
