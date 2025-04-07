package com.db.release.exception;

import liquibase.exception.LiquibaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handleDatabaseRollbackException() {
        // Arrange
        String errorMessage = "Failed to rollback database";
        DatabaseRollbackException exception = new DatabaseRollbackException(errorMessage);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleDatabaseRollbackException(exception);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Database Rollback Error", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
    }

    @Test
    void handleLiquibaseException() {
        // Arrange
        String errorMessage = "Liquibase operation failed";
        LiquibaseException exception = new LiquibaseException(errorMessage);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleLiquibaseException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Liquibase Error", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
    }

    @Test
    void handleSQLException() {
        // Arrange
        String errorMessage = "SQL error occurred";
        SQLException exception = new SQLException(errorMessage);

        // Act
        ResponseEntity<Object> response = exceptionHandler.handleSQLException(exception);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Database Error", responseBody.get("error"));
        assertEquals(errorMessage, responseBody.get("message"));
    }
}