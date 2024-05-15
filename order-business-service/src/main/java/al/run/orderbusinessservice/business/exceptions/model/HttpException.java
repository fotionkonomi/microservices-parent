package al.run.orderbusinessservice.business.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpException extends RuntimeException {
    private HttpErrorDto httpErrorDto;
}
