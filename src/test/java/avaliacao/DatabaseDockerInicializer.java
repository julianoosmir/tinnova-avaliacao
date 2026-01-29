package avaliacao;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.MySQLContainer;

import java.util.HashMap;
import java.util.Map;

public class DatabaseDockerInicializer implements QuarkusTestResourceLifecycleManager {

    private static final MySQLContainer<?> MYSQL_CONTAINER =
            new MySQLContainer<>("mysql:8.0")
                    .withDatabaseName("testdb")
                    .withUsername("osmir")
                    .withPassword("123456");

    @Override
    public Map<String, String> start() {
        // Inicia o container
        MYSQL_CONTAINER.start();

        var url = MYSQL_CONTAINER.getJdbcUrl();
        var username = MYSQL_CONTAINER.getUsername();
        var password = MYSQL_CONTAINER.getPassword();


        // Configura as propriedades para o Quarkus
        Map<String, String> properties = new HashMap<>();
        properties.put("quarkus.datasource.jdbc.url", url);
        properties.put("quarkus.datasource.username", username);
        properties.put("quarkus.datasource.password", password);
        properties.put("quarkus.datasource.jdbc.driver", MYSQL_CONTAINER.getDriverClassName());

        System.out.println("-------------DATABASE------------------");

        return properties;
    }

    @Override
    public void stop() {
        // Para o container
        MYSQL_CONTAINER.stop();
    }
}

