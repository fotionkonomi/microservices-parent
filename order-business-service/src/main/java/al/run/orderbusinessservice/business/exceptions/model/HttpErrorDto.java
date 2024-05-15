package al.run.orderbusinessservice.business.exceptions.model;

import lombok.Data;

import java.util.Date;

@Data
public class HttpErrorDto {
    private int errorCode;
    private Date timestamp;
    private String path;
    private String message;
    private String localizedMessage;
    private String exceptionName;
}
