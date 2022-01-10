package psql;

import org.json.JSONArray;

import java.sql.*;

public class WriterReaderPsql {
    static private String DB_CONNECTION = System.getenv().getOrDefault("DB_URL", "jdbc:postgresql://localhost:5432/whale-coin");
//    static private String DB_CONNECTION = "jdbc:postgresql://my_p_g:5432/whale-coin";
    static private String DB_USER = "anton";
    static private String DB_PASSWORD = "111";


    public static void main(String[] args) throws Exception {

        String dbRequest = "SELECT coin FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray = writerReaderPsql.getDbAnswerInJson(dbRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonArray.toString());

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
    public JSONArray getDbAnswerInJson (String sqlRequest) throws Exception {
        Connection dbConnection = getDBConnection();
        Statement statement = dbConnection.createStatement();
        ResultSetToJsonExtractor resultSetToJsonExtractor = new ResultSetToJsonExtractor();

        return resultSetToJsonExtractor.convertToJSON(statement.executeQuery(sqlRequest));
        }
}
