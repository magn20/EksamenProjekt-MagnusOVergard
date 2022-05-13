package dal.db;

import dal.interfaces.IConnectionPool;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class BasicConnectionPoolTest {

    @Test
    void create() throws SQLException, IOException {
        IConnectionPool connectionPool = BasicConnectionPool.create();
        assertTrue(connectionPool.getConnection().isValid(1));
    }

    @Test
    void releaseConnection() throws SQLException, IOException {
        BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

    Connection conn1 = basicConnectionPool.getConnection();
    Connection conn2 = basicConnectionPool.getConnection();
    Connection conn3 = basicConnectionPool.getConnection();
    Connection conn4 = basicConnectionPool.getConnection();
    Connection conn5 = basicConnectionPool.getConnection();

    basicConnectionPool.releaseConnection(conn1);
    basicConnectionPool.releaseConnection(conn2);
    basicConnectionPool.releaseConnection(conn3);
    basicConnectionPool.releaseConnection(conn4);
    basicConnectionPool.releaseConnection(conn5);


    assertEquals(basicConnectionPool.getSize(),basicConnectionPool.getAvailableConnections());
    }

    @Test
    void GetConnectionsAsYouNeed() throws SQLException, IOException {
        BasicConnectionPool basicConnectionPool = new BasicConnectionPool();

        for (int i = 0; i < 15; i++){
            basicConnectionPool.getConnection();
        }
        assertTrue(basicConnectionPool.getConnectionsInUse() == 15);
    }
}