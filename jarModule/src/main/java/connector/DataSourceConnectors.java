package connector;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DataSourceConnectors {
    private static DataSourceConnectors dataSourceConnectors;
    private final ComboPooledDataSource comboPooledDataSource;

    private DataSourceConnectors() throws PropertyVetoException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("baseMySQL");
        String url = resourceBundle.getString("URL");
        String driver = resourceBundle.getString("DRIVER");
        String user = resourceBundle.getString("USER");
        String pass = resourceBundle.getString("PASS");
        comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(driver);
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(pass);
        comboPooledDataSource.setMinPoolSize(5);
        comboPooledDataSource.setAcquireIncrement(5);
        comboPooledDataSource.setMaxPoolSize(20);
        comboPooledDataSource.setMaxStatements(180);
    }

    public static DataSourceConnectors getInstance() throws PropertyVetoException {
        if (dataSourceConnectors==null){
            dataSourceConnectors = new DataSourceConnectors();
            return dataSourceConnectors;
        }
        return dataSourceConnectors;
    }

    public Connection getConnection() throws SQLException {
        return this.comboPooledDataSource.getConnection();
    }
}
