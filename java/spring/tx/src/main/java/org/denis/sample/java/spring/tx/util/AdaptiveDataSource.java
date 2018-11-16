package org.denis.sample.java.spring.tx.util;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class AdaptiveDataSource implements DataSource {

    private final DataSource roDataSource;
    private final DataSource rwDataSource;

    private final ThreadLocal<DataSource> delegate = new ThreadLocal<>();

    public AdaptiveDataSource(DataSource roDataSource, DataSource rwDataSource) {
        this.roDataSource = roDataSource;
        this.rwDataSource = rwDataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDelegate().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getDelegate().getConnection(username, password);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return getDelegate().unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return getDelegate().isWrapperFor(iface);
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return getDelegate().getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        getDelegate().setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        getDelegate().setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return getDelegate().getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getDelegate().getParentLogger();
    }

    private DataSource getDelegate() {
        DataSource result = delegate.get();
        if (result != null) {
            return result;
        }
        delegate.set(result = rwDataSource);
        return result;
    }

    public void activate(boolean readOnly) {
        delegate.set(readOnly ? roDataSource : rwDataSource);
    }

    public void reset() {
        delegate.set(null);
    }
}
