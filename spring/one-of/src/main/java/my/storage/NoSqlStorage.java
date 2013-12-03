package my.storage;

import my.DataConverter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Denis Zhdanov
 * @since 03/12/13 09:45
 */
@Named
@Lazy
@Profile("storage")
public class NoSqlStorage implements Storage {

    @Inject
    public NoSqlStorage(@Nonnull DataConverter converter) {
    }
}
