package org.denis.sample.java.spring.tx.di;

public class DiConstants {

    public static final String DATA_SOURCE_RO          = "DATA_SOURCE_RO";
    public static final String JDBC_TEMPLATE_RO        = "JDBC_TEMPLATE_RO";
    public static final String JDBC_TX_MANAGER_RO      = "JDBC_TX_MANAGER_RO";
    public static final String HIBERNATE_TX_MANAGER_RO = "JDBC_TX_MANAGER_RO";
    public static final String SESSION_FACTORY_RO      = "SESSION_FACTORY_RO";

    public static final String DATA_SOURCE_RW          = "DATA_SOURCE_RW";
    public static final String JDBC_TX_MANAGER_RW      = "JDBC_TX_MANAGER_RW";
    public static final String HIBERNATE_TX_MANAGER_RW = "HIBERNATE_TX_MANAGER_RW";
    public static final String JDBC_TEMPLATE_RW        = "JDBC_TEMPLATE_RW";
    public static final String SESSION_FACTORY_RW      = "SESSION_FACTORY_RW";

    public static final String ADAPTIVE_HIBERNATE_TX_MANAGER    = "ADAPTIVE_HIBERNATE_TX_MANAGER";
    public static final String ADAPTIVE_HIBERNATE_RO_TX_MANAGER = "ADAPTIVE_HIBERNATE_RO_TX_MANAGER";
    public static final String ADAPTIVE_HIBERNATE_RW_TX_MANAGER = "ADAPTIVE_HIBERNATE_RW_TX_MANAGER";
    public static final String ADAPTIVE_DATA_SOURCE             = "ADAPTIVE_DATA_SOURCE";
    public static final String ADAPTIVE_SESSION_FACTORY         = "ADAPTIVE_SESSION_FACTORY";

    private DiConstants() {
    }
}
