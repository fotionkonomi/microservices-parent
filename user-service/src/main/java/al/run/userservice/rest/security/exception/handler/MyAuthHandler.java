package al.run.userservice.rest.security.exception.handler;

import al.run.userservice.rest.exceptionhandler.dto.HttpErrorResponse;
import al.run.userservice.rest.exceptionhandler.util.ExceptionMessageUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

@Component
@RequiredArgsConstructor
public class MyAuthHandler implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;
    private final ExceptionMessageUtil messageUtil;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {
        String exceptionMessage;
        try {
            exceptionMessage = messageUtil.getLocalizedMessage(authException.getMessage());
        } catch (NoSuchMessageException e) {
            exceptionMessage = authException.getMessage();
        }
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse();
        httpErrorResponse.setErrorCode(HttpServletResponse.SC_UNAUTHORIZED);
        httpErrorResponse.setPath(request.getRequestURI());
        httpErrorResponse.setMessage("Authorization Error: " + exceptionMessage);
        response.setStatus(httpErrorResponse.getErrorCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, httpErrorResponse);
        responseStream.flush();
    }

}
