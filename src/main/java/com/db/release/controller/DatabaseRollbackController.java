package com.db.release.controller;

import com.db.release.exception.DatabaseRollbackException;
import com.db.release.service.DatabaseRollbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Database Rollback", description = "API endpoints for database rollback operations")
public class DatabaseRollbackController {

    private final DatabaseRollbackService rollbackService;

    @Autowired
    public DatabaseRollbackController(DatabaseRollbackService rollbackService) {
        this.rollbackService = rollbackService;
    }

    /**
     * Rollback database to a specific tag
     * 
     * @param tag The tag to rollback to
     * @return ResponseEntity with operation result
     */
    @Operation(summary = "Rollback database to a specific tag", 
              description = "Rolls back the database to the state it was in when the specified tag was applied")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully rolled back to tag"),
        @ApiResponse(responseCode = "400", description = "Invalid tag or rollback failed"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/rollback/tag/{tag}")
    public ResponseEntity<?> rollbackToTag(
            @Parameter(description = "Tag to rollback to", required = true) 
            @PathVariable String tag) {
        try {
            rollbackService.rollbackToTag(tag);
            return ResponseEntity.ok().body("Successfully rolled back to tag: " + tag);
        } catch (DatabaseRollbackException e) {
            return ResponseEntity.badRequest().body("Failed to rollback: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Rollback database by a specific number of changesets
     * 
     * @param count The number of changesets to rollback
     * @return ResponseEntity with operation result
     */
    @Operation(summary = "Rollback database by a specific number of changesets", 
              description = "Rolls back the database by the specified number of most recent changesets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully rolled back specified number of changesets"),
        @ApiResponse(responseCode = "400", description = "Invalid count or rollback failed"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/rollback/last/{count}")
    public ResponseEntity<?> rollbackLastCount(
            @Parameter(description = "Number of changesets to rollback", required = true) 
            @PathVariable int count) {
        try {
            if (count <= 0) {
                return ResponseEntity.badRequest().body("Count must be greater than zero");
            }
            rollbackService.rollbackLastCount(count);
            return ResponseEntity.ok().body("Successfully rolled back last " + count + " changesets");
        } catch (DatabaseRollbackException e) {
            return ResponseEntity.badRequest().body("Failed to rollback: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Unexpected error: " + e.getMessage());
        }
    }
}