package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import dal.interfaces.IConnectionPool;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BasicConnectionPool implements IConnectionPool {

    private static SQLServerDataSource ds;
    private static final String PROP_FILE = "DATA/database.info";
    private static List<Connection> connectionPool = new ArrayList<>();
    private static List<Connection> usedConnections = new ArrayList<>();
    private static int INITIAL_POOL_SIZE = 10;

    public static BasicConnectionPool create() throws SQLException, IOException {

        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return new BasicConnectionPool();
    }

    @Override
    public Connection getConnection() throws SQLException, IOException {
        if (connectionPool.isEmpty()){
            create();
        }
        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }


    @Override
    public boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private static Connection createConnection() throws SQLException, IOException {

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

        connectionPool.add(ds.getConnection());
        return ds.getConnection();
    }

    public int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
    public int getAvailableConnections(){
        return connectionPool.size();
    }
    public int getConnectionsInUse(){
        return usedConnections.size();
    }
}
