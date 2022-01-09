package psql;

import org.httprpc.sql.ResultSetAdapter;

import java.sql.*;

public class WriterReaderPsql {
    static private String DB_CONNECTION = "jdbc:postgresql://localhost:5432/whale-coin";
    static private String DB_USER = "anton";
    static private String DB_PASSWORD = "111";


    public static void main(String[] args) throws SQLException {

        String dbRequest = "SELECT time, coin, amount, amount_usd FROM whale_message WHERE coin  IN ('ETH', 'BNB')\n" +
                "AND amount BETWEEN 10000 AND 50000 AND time BETWEEN '2021-01-10 04:10:27' AND '2021-05-10 04:10:27';";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        ResultSetAdapter resultSetAdapter;
        resultSetAdapter = writerReaderPsql.getDbAnswer(dbRequest);
        while (resultSetAdapter.iterator().hasNext()){
            int i = 1;
            System.out.println("Db answer   " + "  " + resultSetAdapter.next());
            i = i+1;
        }

        //int i = resultSetAdapter.size();
        //System.out.println(resultSetAdapter.iterator().);
    }

    private static Connection getDBConnection () {
        Connection dbConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            e.printStackTrace();
        }
        try {
            dbConnection =  DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }

    public void setDbRequest (String sqlRequest) throws SQLException {
        try (Connection dbConnection = getDBConnection();
             Statement statement = dbConnection.createStatement()) {
             statement.execute(sqlRequest);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public ResultSetAdapter getDbAnswer (String sqlRequest) throws SQLException {
        Connection dbConnection = getDBConnection();
        Statement statement = dbConnection.createStatement();
        return new ResultSetAdapter(statement.executeQuery(sqlRequest));
        }
}
