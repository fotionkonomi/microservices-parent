package al.run.orderbusinessservice.business.rollback;

import lombok.Getter;

public enum ExceptionEnum {

    ORDER_OBJECT_BAD_REQUEST_EXCEPTION("OrderObjectBadRequestException"),
    NOT_ENOUGH_STOCK_EXCEPTION("NotEnoughStockException"),
    RACE_NOT_FOUND_EXCEPTION("RaceNotFoundException"),
    RESOURCE_ACCESS_EXCEPTION("ResourceAccessException");

    @Getter
    private final String exceptionName;

    ExceptionEnum(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public static ExceptionEnum fromString(String text) {
        for (ExceptionEnum exceptionEnum : ExceptionEnum.values()) {
            if (exceptionEnum.getExceptionName().equalsIgnoreCase(text)) {
                return exceptionEnum;
            }
        }
        return null;
    }

}
