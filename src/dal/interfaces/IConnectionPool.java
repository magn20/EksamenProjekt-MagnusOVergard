package dal.interfaces;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionPool {
    Connection getConnection() throws SQLException, IOException;
    boolean releaseConnection(Connection connection);
}
