package com.db.release;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DbReleaseApplicationTest {

    @Test
    void contextLoads() {
        // This test verifies that the application context loads successfully
        // No assertions needed - test will fail if context cannot be loaded
    }
}