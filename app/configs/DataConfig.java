package configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import scala.reflect.io.VirtualFile;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

@Configuration
@EnableTransactionManagement
public class DataConfig {

    private @Autowired DataSource dataSource;

    @Bean
    public DataSource dataSource() {
        final String driverClass = Play.application().configuration().getString("db.default.driver");
        final String url  = Play.application().configuration().getString("db.default.url");
        final String user = Play.application().configuration().getString("db.default.user");
        final String pass = Play.application().configuration().getString("db.default.password");

        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
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