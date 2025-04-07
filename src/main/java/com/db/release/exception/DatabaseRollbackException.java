package com.db.release.exception;

import liquibase.exception.LiquibaseException;

/**
 * Custom exception for database rollback operations
 */
public class DatabaseRollbackException extends RuntimeException {

    public DatabaseRollbackException(String message) {
        super(message);
    }

    public DatabaseRollbackException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Factory method to create exception from LiquibaseException
     * 
     * @param message Custom message
     * @param liquibaseException Original Liquibase exception
     * @return DatabaseRollbackException
     */
    public static DatabaseRollbackException fromLiquibaseException(String message, LiquibaseException liquibaseException) {
        return new DatabaseRollbackException(message, liquibaseException);
    }
}