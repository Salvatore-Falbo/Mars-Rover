package mars_rover.error_handlers;

import mars_rover.dtos.ObstacleDetected;
import mars_rover.exceptions.*;
import mars_rover.singletons.RoverState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationErrorHandler {
    @ExceptionHandler(AlreadyInitializedException.class)
    public ResponseEntity<String> AlreadyInitializedHandler(AlreadyInitializedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotInitializedException.class)
    public ResponseEntity<String> NotInitializedHandler(NotInitializedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSequenceException.class)
    public ResponseEntity<String> InvalidSequenceHandler(InvalidSequenceException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObstacleDetectedException.class)
    public ResponseEntity<ObstacleDetected> ObstacleDetectedHandler(ObstacleDetectedException e) {
        ObstacleDetected result = new ObstacleDetected(RoverState.getPosition(),e.getObstacleX(),e.getObstacleY());
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler(UnsupportedCommandException.class)
    public ResponseEntity<String> UnsupportedCommandHandler(UnsupportedCommandException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("Invalid request format", HttpStatus.BAD_REQUEST);
    }
}
