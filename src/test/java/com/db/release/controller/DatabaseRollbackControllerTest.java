package com.db.release.controller;

import com.db.release.exception.DatabaseRollbackException;
import com.db.release.service.DatabaseRollbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseRollbackControllerTest {

    @Mock
    private DatabaseRollbackService rollbackService;

    @InjectMocks
    private DatabaseRollbackController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rollbackToTag_Success() {
        // Arrange
        String tag = "v1.0";
        doNothing().when(rollbackService).rollbackToTag(tag);

        // Act
        ResponseEntity<?> response = controller.rollbackToTag(tag);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully rolled back to tag: " + tag, response.getBody());
        verify(rollbackService, times(1)).rollbackToTag(tag);
    }

    @Test
    void rollbackToTag_DatabaseRollbackException() {
        // Arrange
        String tag = "v1.0";
        String errorMessage = "Tag not found";
        doThrow(new DatabaseRollbackException(errorMessage)).when(rollbackService).rollbackToTag(tag);

        // Act
        ResponseEntity<?> response = controller.rollbackToTag(tag);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to rollback: " + errorMessage, response.getBody());
        verify(rollbackService, times(1)).rollbackToTag(tag);
    }

    @Test
    void rollbackToTag_GeneralException() {
        // Arrange
        String tag = "v1.0";
        String errorMessage = "Unexpected error";
        doThrow(new RuntimeException(errorMessage)).when(rollbackService).rollbackToTag(tag);

        // Act
        ResponseEntity<?> response = controller.rollbackToTag(tag);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: " + errorMessage, response.getBody());
        verify(rollbackService, times(1)).rollbackToTag(tag);
    }

    @Test
    void rollbackLastCount_Success() {
        // Arrange
        int count = 5;
        doNothing().when(rollbackService).rollbackLastCount(count);

        // Act
        ResponseEntity<?> response = controller.rollbackLastCount(count);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfully rolled back last " + count + " changesets", response.getBody());
        verify(rollbackService, times(1)).rollbackLastCount(count);
    }

    @Test
    void rollbackLastCount_InvalidCount() {
        // Arrange
        int count = 0;

        // Act
        ResponseEntity<?> response = controller.rollbackLastCount(count);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Count must be greater than zero", response.getBody());
        verify(rollbackService, never()).rollbackLastCount(anyInt());
    }

    @Test
    void rollbackLastCount_DatabaseRollbackException() {
        // Arrange
        int count = 5;
        String errorMessage = "Error during rollback";
        doThrow(new DatabaseRollbackException(errorMessage)).when(rollbackService).rollbackLastCount(count);

        // Act
        ResponseEntity<?> response = controller.rollbackLastCount(count);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to rollback: " + errorMessage, response.getBody());
        verify(rollbackService, times(1)).rollbackLastCount(count);
    }

    @Test
    void rollbackLastCount_GeneralException() {
        // Arrange
        int count = 5;
        String errorMessage = "Unexpected error";
        doThrow(new RuntimeException(errorMessage)).when(rollbackService).rollbackLastCount(count);

        // Act
        ResponseEntity<?> response = controller.rollbackLastCount(count);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Unexpected error: " + errorMessage, response.getBody());
        verify(rollbackService, times(1)).rollbackLastCount(count);
    }
}