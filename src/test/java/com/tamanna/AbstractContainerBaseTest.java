package com.tamanna;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

/**
 * https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/#singleton-containers
 */
@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
public abstract class AbstractContainerBaseTest {

    public static PostgreSQLContainer<?> postgreSQLContainer;

    @BeforeAll
    public static void before() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3")
                .withReuse(true);
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
