package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;

public class DatabaseConnector {
    private static final String PROP_FILE = "DATA/database.info";
    private SQLServerDataSource ds;

    /**
     * reading from a file database.settings. to setup the database.
     * not included in the github so to acces the database,
     * create a new package named Data, and include a file called database.settings . Holding login information.
     * @throws IOException
     */
    public DatabaseConnector() throws IOException {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));

        String server = databaseProperties.getProperty("SERVERNAME");
        String database = databaseProperties.getProperty("DATABASENAME");
        String user = databaseProperties.getProperty("USERNAME");
        String password = databaseProperties.getProperty("PASSWORD");

        ds = new SQLServerDataSource();
        ds.setServerName(server);
        ds.setDatabaseName(database);
        ds.setUser(user);
        ds.setPassword(password);
    }

    /**
     *  get the database connection.
     * @return the database connection.
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return ds.getConnection();
    }
}
