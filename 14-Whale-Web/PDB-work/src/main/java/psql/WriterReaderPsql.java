package psql;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.sql.*;

public class WriterReaderPsql {

//    @Inject
//    @ConfigProperty(name = "psql.postgres.url", defaultValue = )
//            private String DB_CONNECTION;

//    SmallRyeConfig config = new SmallRyeConfigBuilder()
//            .addDefaultInterceptors()
//            .addDefaultSources()
//            .build();

//    private Config config = ConfigProvider.getConfig();
//
//    private String dbUrl = System.getenv().getOrDefault("DB_URL",
//            config.getValue("psql.postgres.url", String.class));
//    private String postgresUser = System.getenv().getOrDefault("POSTGRES_USER",
//            config.getValue("psql.postgres.user", String.class)) ;
//    private String postgresPassword = System.getenv().getOrDefault("POSTGRES_PASSWORD",
//            config.getValue("psql.postgres.password", String.class));

    public static void main(String[] args) throws Exception {

        String dbRequest = "select array_to_json(array_agg(row_to_json(t))) from (SELECT coin FROM coins ) t";

        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        String responseFromDB = null;
        try {
            responseFromDB = writerReaderPsql.getDbAnswerInJson(dbRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Connection getDBConnection() {

        Config config = ConfigProvider.getConfig();

        String dbUrl = System.getenv().getOrDefault("DB_URL",
                config.getValue("psql.postgres.url", String.class));
        String postgresUser = System.getenv().getOrDefault("POSTGRES_USER",
                config.getValue("psql.postgres.user", String.class)) ;
        String postgresPassword = System.getenv().getOrDefault("POSTGRES_PASSWORD",
                config.getValue("psql.postgres.password", String.class));

        Connection dbConnection = null;
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
            e.printStackTrace();
        }
        try {
            dbConnection =  DriverManager.getConnection(dbUrl, postgresUser, postgresPassword);
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
    public String getDbAnswerInJson (String sqlRequest) throws Exception {
        Connection dbConnection = getDBConnection();
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlRequest);

        String responseFromDB = null;

        while(resultSet.next()){
            System.out.println(resultSet.getString(1));
            responseFromDB = resultSet.getString(1);
        }
        return responseFromDB;
        }
}
