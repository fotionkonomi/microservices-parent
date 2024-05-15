package al.run.userservice.rest.exceptionhandler.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExceptionMessageUtilImpl implements ExceptionMessageUtil {

    private final MessageSource messageSource;

    private MessageSourceAccessor accessor;

    @PostConstruct
    public void init() {
        accessor = new MessageSourceAccessor(messageSource);
    }

    @Override
    public String getLocalizedMessage(String messageKey) {
        return accessor.getMessage(messageKey);
    }

}
