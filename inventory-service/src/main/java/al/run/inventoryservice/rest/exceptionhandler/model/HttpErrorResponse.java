package al.run.inventoryservice.rest.exceptionhandler.model;

import lombok.Data;

import java.util.Date;

@Data
public class HttpErrorResponse {
    private int errorCode;
    private Date timestamp;
    private String path;
    private String message;
    private String localizedMessage;
    private String exceptionName;

    public HttpErrorResponse() {
        this.timestamp = new Date();
    }
}