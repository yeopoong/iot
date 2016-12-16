package kyun.iot.framework.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    
	@ExceptionHandler({ RuntimeException.class })
	public ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {

		logger.error("Service Error", ex);
		
		String error = getErrors(ex);

	    RestError restError = new RestError(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), error);

	    return new ResponseEntity<Object>(restError, new HttpHeaders(), restError.getStatus());
	}

    private String getErrors(Exception ex) {
        StringBuilder error = new StringBuilder();
		StackTraceElement[] stackTraceElements = ex.getStackTrace();

		for (int i = 0;  (i < stackTraceElements.length && i < 5); i++) {
		    error.append(stackTraceElements[i].toString() + " | ");
        }

        return error.toString();
    }
}
