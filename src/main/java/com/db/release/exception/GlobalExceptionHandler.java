package com.db.release.exception;

import liquibase.exception.LiquibaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application
 * Handles specific exceptions and returns appropriate HTTP responses
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle DatabaseRollbackException
     */
    @ExceptionHandler(DatabaseRollbackException.class)
    public ResponseEntity<Object> handleDatabaseRollbackException(DatabaseRollbackException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Database Rollback Error");
        body.put("message", ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    /**
     * Handle LiquibaseException
     */
    @ExceptionHandler(LiquibaseException.class)
    public ResponseEntity<Object> handleLiquibaseException(LiquibaseException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Liquibase Error");
        body.put("message", ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handle SQLException
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Database Error");
        body.put("message", ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Handle all other exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("error", "Internal Server Error");
        body.put("message", ex.getMessage());
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}