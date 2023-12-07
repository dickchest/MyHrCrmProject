package com.myhrcrmproject.controller;

import com.myhrcrmproject.service.validation.AlreadyExistsException;
import com.myhrcrmproject.service.validation.InvalidJwtException;
import com.myhrcrmproject.service.validation.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.NotAcceptableStatusException;

import java.sql.SQLIntegrityConstraintViolationException;
/**
 * Global exception handler for handling various exceptions that may occur
 * during the execution of RESTful APIs in the HR CRM system.
 *
 * <p>This class is annotated with {@code @ControllerAdvice}, indicating that
 * it assists all controllers in the application. It includes methods annotated
 * with {@code @ExceptionHandler} to handle specific exceptions and return
 * appropriate responses with HTTP status codes.
 *
 * <p>The supported exceptions include:
 * <ul>
 *     <li>{@code NullPointerException}: Returns a 500 Internal Server Error.</li>
 *     <li>{@code SQLIntegrityConstraintViolationException}: Returns a 500 Internal Server Error.</li>
 *     <li>{@code ConstraintViolationException}: Returns a 400 Bad Request.</li>
 *     <li>{@code AlreadyExistsException}: Returns a 400 Bad Request.</li>
 *     <li>{@code DataIntegrityViolationException}: Returns a 400 Bad Request with a custom message.</li>
 *     <li>{@code NotFoundException}: Returns a 404 Not Found.</li>
 *     <li>{@code InvalidJwtException}: Returns a 451 Unavailable For Legal Reasons.</li>
 *     <li>{@code NotAcceptableStatusException}: Returns a 403 Forbidden.</li>
 * </ul>
 *
 * @author Denys Chaykovskyy
 * @version 1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@code NullPointerException} and returns a 500 Internal Server Error.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 500.
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handlerNullPointerException(NullPointerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@code SQLIntegrityConstraintViolationException} and returns a 500 Internal Server Error.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 500.
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handlerSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles {@code ConstraintViolationException} and returns a 400 Bad Request.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 400.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handlerConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@code AlreadyExistsException} and returns a 400 Bad Request.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 400.
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<String> handlerAlreadyExistsException(AlreadyExistsException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@code DataIntegrityViolationException} and returns a 400 Bad Request with a custom message.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with a custom error message and HTTP status code 400.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlerDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>("Entry already exists: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@code NotFoundException} and returns a 404 Not Found.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 404.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handlerNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@code InvalidJwtException} and returns a 451 Unavailable For Legal Reasons.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 451.
     */
    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<String> handlerInvalidJwtException(InvalidJwtException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    /**
     * Handles {@code NotAcceptableStatusException} and returns a 403 Forbidden.
     *
     * @param e The exception to handle.
     * @return A {@code ResponseEntity} with an error message and HTTP status code 403.
     */
    @ExceptionHandler(NotAcceptableStatusException.class)
    public ResponseEntity<String> handlerNotAcceptableStatusException(NotAcceptableStatusException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
