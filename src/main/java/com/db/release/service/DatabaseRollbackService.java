package com.db.release.service;

import com.db.release.exception.DatabaseRollbackException;
import liquibase.exception.LiquibaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

@Service
public class DatabaseRollbackService {

    private final DataSource dataSource;
    private final String changeLogFile;

    @Autowired
    public DatabaseRollbackService(DataSource dataSource) {
        this.dataSource = dataSource;
        this.changeLogFile = "db/changelog/db.changelog-master.xml";
    }

    /**
     * Rollback database to a specific tag
     *
     * @param tag The tag to rollback to
     * @throws DatabaseRollbackException If there's an error during rollback
     */
    public void rollbackToTag(String tag) {
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
            
            liquibase.rollback(tag, "");
        } catch (LiquibaseException e) {
            throw new DatabaseRollbackException("Error rolling back to tag: " + tag, e);
        } catch (SQLException e) {
            throw new DatabaseRollbackException("Database connection error during rollback to tag: " + tag, e);
        }
    }

    /**
     * Rollback database by a specific number of changesets
     *
     * @param count The number of changesets to rollback
     * @throws DatabaseRollbackException If there's an error during rollback
     */
    public void rollbackLastCount(int count) {
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase = new Liquibase(changeLogFile, new ClassLoaderResourceAccessor(), database);
            
            liquibase.rollback(count, "");
        } catch (LiquibaseException e) {
            throw new DatabaseRollbackException("Error rolling back last " + count + " changesets", e);
        } catch (SQLException e) {
            throw new DatabaseRollbackException("Database connection error during rollback of last " + count + " changesets", e);
        }
    }
}