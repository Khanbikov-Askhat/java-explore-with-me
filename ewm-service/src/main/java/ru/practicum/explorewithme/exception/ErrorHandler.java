package ru.practicum.explorewithme.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.exception.dto.ErrorResponse;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Ошибка валидации параметра");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Ошибка валидации параметра", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataValidationException(DataValidationException e) {
        log.error("Ошибка в поступивших данных", e);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Ошибка в поступивших данных", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("Нарушено ограничение целостности базы данных");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.CONFLICT, "Конфликт во время выполнения операции с поступившими данными",
                e.getMessage(), Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataConflictException(DataConflictException e) {
        log.error("Конфликт при выполнении запроса");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.CONFLICT, "Конфликт во время выполнения операции с поступившими данными",
                e.getMessage(), Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCategoryNotFoundException(CategoryNotFoundException e) {
        log.error("Ошибка поиска категории", e);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Ошибка поиска категории", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException e) {
        log.error("Ошибка поиска пользователя");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Ошибка поиска пользователя", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEventNotFoundException(EventNotFoundException e) {
        log.error("Ошибка поиска события");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Ошибка поиска события", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleCompilationNotFoundException(CompilationNotFoundException e) {
        log.error("Ошибка поиска подборки");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Ошибка поиска подборки", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRequestNotFoundException(RequestNotFoundException e) {
        log.error("Ошибка поиска запроса на участие");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.NOT_FOUND, "Ошибка поиска запроса на участие", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Отстутсвует необходимый параметр запроса");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Отстутсвует необходимый параметр запроса", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidRequestException(InvalidRequestException e) {
        log.error("Некорректный запрос");
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.BAD_REQUEST, "Некорректный запрос", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnknownException(Throwable e) {
        log.error("Возникла непредвиденная ошибка", e);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Непредвиденная ошибка", e.getMessage(),
                Collections.singletonList(Arrays.toString(e.getStackTrace())), LocalDateTime.now());
    }
}
