package my;

import my.storage.Storage;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Denis Zhdanov
 * @since 03/12/13 09:46
 */
@Named
public class Service {

    @Nonnull private final Storage storage;

    @Inject
    public Service(@Nonnull Storage storage) {
        this.storage = storage;
    }

    @Nonnull
    public Storage getStorage() {
        return storage;
    }
}
