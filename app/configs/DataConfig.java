package configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import play.Play;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableTransactionManagement
public class DataConfig {

    private @Autowired DataSource dataSource;

    @Bean
    public DataSource dataSource() throws URISyntaxException {
        final String driverClass = Play.application().configuration().getString("db.default.driver");
//        final String url  = Play.application().configuration().getString("db.default.url");
//        final String user = Play.application().configuration().getString("db.default.user");
//        final String pass = Play.application().configuration().getString("db.default.password");

        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String user = dbUri.getUserInfo().split(":")[0];
        String pass = dbUri.getUserInfo().split(":")[1];
        String url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();

        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws URISyntaxException {
        return new JdbcTemplate(dataSource());
    }

    @PostConstruct
    public void setUp(){
//        Resource resource = new ClassPathResource("schema/mysql.sql");

        String dbSchemaFile = Play.application().configuration().getString("db.schema.file");

        System.out.println("Creating database schema...");
        System.out.println("Schema file: "+dbSchemaFile);


        Resource resource = new ClassPathResource(dbSchemaFile);

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setContinueOnError(true);
        databasePopulator.execute(dataSource);

        System.out.println("Done.");
    }
}