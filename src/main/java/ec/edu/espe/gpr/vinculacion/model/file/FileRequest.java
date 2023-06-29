package ec.edu.espe.gpr.vinculacion.model.file;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public class FileRequest {

    private ByteArrayResource file;
    private String nameFile;

    public FileRequest() {
    }
    public FileRequest(ByteArrayResource file, String nameFile) {
        this.file = file;
        this.nameFile = nameFile;
    }
    public ByteArrayResource getFile() {
        return file;
    }
    public void setFile(ByteArrayResource file) {
        this.file = file;
    }
    public String getNameFile() {
        return nameFile;
    }
    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

}