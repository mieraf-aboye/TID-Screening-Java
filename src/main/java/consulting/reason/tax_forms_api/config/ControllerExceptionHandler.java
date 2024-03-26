package consulting.reason.tax_forms_api.config;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<String> handleException(ResponseStatusException e, HttpServletRequest request) {
        logger.error(String.format("%s occurred.", e.getClass().getName()), e);

        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }
}
