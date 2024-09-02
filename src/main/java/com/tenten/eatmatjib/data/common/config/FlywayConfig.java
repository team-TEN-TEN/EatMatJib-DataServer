    package com.tenten.eatmatjib.data.common.config;

    import javax.sql.DataSource;
    import org.flywaydb.core.Flyway;
    import org.springframework.beans.factory.annotation.Qualifier;
    import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class FlywayConfig {
        /*  migrate 전에 repair를 먼저 하여 checksum을 재설정해준다
         형상이 깨져 에러가 발생하는 것을 막아주는 코드  */
        @Bean
        public FlywayMigrationStrategy cleanMigrateStrategy() {
            return flyway -> {
                flyway.repair();
                flyway.migrate();
            };
        }
    }
