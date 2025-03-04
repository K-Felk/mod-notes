package org.folio.notes.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.folio.spring.liquibase.FolioSpringLiquibase;

@Testcontainers
@DirtiesContext
@ContextConfiguration
@SpringBootTest
public abstract class TestBase {

  @Container
  protected static PostgreSQLContainer<?> postgreDBContainer = new PostgreSQLContainer<>("postgres:12-alpine");
  @Autowired
  protected FolioSpringLiquibase liquibase;
  @Autowired
  protected JdbcTemplate jdbc;

  @DynamicPropertySource
  static void postgresProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreDBContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreDBContainer::getUsername);
    registry.add("spring.datasource.password", postgreDBContainer::getPassword);
  }

}
