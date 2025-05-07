package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundException(NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerValidationException(ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerValidationException(Throwable e) {
        String errorMessage = "Произошла внутренняя ошибка сервера: ";
        errorMessage += "Тип исключения - " + e.getClass().getSimpleName() + ". ";
        if (e.getMessage() != null && !e.getMessage().isEmpty()) {
            errorMessage += "Сообщение: " + e.getMessage();
        } else {
            errorMessage += "Сообщение отсутствует.";
        }
        return new ErrorResponse(errorMessage);
    }

    @ExceptionHandler(DuplicatedDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicatedDataException(DuplicatedDataException e) {
        return new ErrorResponse(e.getMessage());
    }
}
