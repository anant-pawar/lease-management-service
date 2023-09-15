package com.amg.lms.exception;


import com.amg.lms.contract.ContractController;
import com.amg.lms.customer.CustomerController;
import com.amg.lms.exception.model.ErrorResponse;
import com.amg.lms.exception.model.ValidationErrorResponse;
import com.amg.lms.vehicle.VehicleController;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@org.springframework.web.bind.annotation.ControllerAdvice(assignableTypes = {CustomerController.class, VehicleController.class, ContractController.class})
public class ControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse onEntityNotFoundException(final EntityNotFoundException exception) {
        log.error(exception.getMessage());

        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({PersistenceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onPersistenceException(final PersistenceException exception) {
        log.error(exception.getMessage());

        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse onRunTimeException(final RuntimeException exception) {
        log.error(exception.getMessage());

        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        final var validationErrorResponse = new ValidationErrorResponse();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> validationErrorResponse.getViolations()
                        .add(new ValidationErrorResponse.Violation(fieldError.getField(), fieldError.getDefaultMessage())));
        return validationErrorResponse;
    }
}

