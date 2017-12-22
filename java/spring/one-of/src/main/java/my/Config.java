package my;

import my.storage.JdbcStorage;
import my.storage.NoSqlStorage;
import my.storage.Storage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Denis Zhdanov
 * @since 03/12/13 10:08
 */
@Configuration
@ComponentScan("my")
public class Config {

    @Nonnull private final AtomicReference<ApplicationContext> storageContext = new AtomicReference<ApplicationContext>();

    @Bean
    public Storage storage(@Nonnull ApplicationContext baseContext) {
        ApplicationContext context = getStorageContext(baseContext);
        if (Boolean.getBoolean("use.nosql.storage")) {
            return context.getBean(NoSqlStorage.class);
        } else {
            return context.getBean(JdbcStorage.class);
        }
    }

    @Nonnull
    private ApplicationContext getStorageContext(@Nonnull final ApplicationContext baseContext) {
        ApplicationContext result = storageContext.get();
        if (result == null) {
            final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.getEnvironment().setActiveProfiles("storage");
            context.register(StorageConfig.class);
            context.setParent(baseContext);
            context.refresh();
            storageContext.compareAndSet(null, context);
            return storageContext.get();
        } else {
            return result;
        }
    }

    @Configuration
    @ComponentScan(basePackages = "my.storage")
    @Profile("storage")
    public static class StorageConfig {
    }
}
