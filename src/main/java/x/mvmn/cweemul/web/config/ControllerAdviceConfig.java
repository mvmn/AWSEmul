package x.mvmn.cweemul.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import x.mvmn.cweemul.web.exception.ApiGenericException;

@ControllerAdvice
public class ControllerAdviceConfig {

	private static final Logger LOG = LoggerFactory.getLogger(ControllerAdviceConfig.class);

	@ExceptionHandler(ApiGenericException.class)
	public ResponseEntity<?> handleApiGenericException(ApiGenericException exc) {
		if (exc.getCause() != null) {
			LOG.error(exc.getMessage(), exc.getCause());
		}
		return new ResponseEntity<>(exc.getMessage(), HttpStatus.valueOf(exc.getStatusCode()));
	}
}
