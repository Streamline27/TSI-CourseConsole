package configs;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
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

@Configuration
@EnableTransactionManagement
public class DataConfig {

    private @Autowired DataSource dataSource;

    @Bean
    public DataSource dataSource() {

        BasicConfigurator.configure();
        Logger log = Logger.getLogger(DataConfig.class);


        final String driverClass = Play.application().configuration().getString("db.default.driver");
        final String url  = Play.application().configuration().getString("db.default.url");
        final String user = Play.application().configuration().getString("db.default.user");
        final String pass = Play.application().configuration().getString("db.default.password");

        log.info(url);
        log.info(user);
        log.info(pass);
        log.info(driverClass);


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
        BasicConfigurator.configure();
        Logger log = Logger.getLogger(DataConfig.class);

        String dbSchemaFile = Play.application().configuration().getString("db.schema.file");

        log.info("Creating database schema...");
        log.info("Schema file: "+dbSchemaFile);

        log.info("DataSource: ");


        Resource resource = new ClassPathResource(dbSchemaFile);

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.setContinueOnError(true);
        databasePopulator.execute(dataSource);

        log.info("Done.");
    }
}