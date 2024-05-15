package al.run.orderbusinessservice.business.rollback;

import al.run.orderbusinessservice.business.exceptions.model.HttpErrorDto;
import al.run.orderbusinessservice.business.exceptions.model.HttpException;
import al.run.orderbusinessservice.business.service.OrderService;

public class RollbackUtil {
    public static HttpException rollback(HttpErrorDto httpErrorDto, OrderService orderService, Long idToDelete) {
        switch (ExceptionEnum.fromString(httpErrorDto.getExceptionName())) {
            case NOT_ENOUGH_STOCK_EXCEPTION, RACE_NOT_FOUND_EXCEPTION, RESOURCE_ACCESS_EXCEPTION -> orderService.deleteOrder(idToDelete);
        }

        return new HttpException(httpErrorDto);
    }


}
