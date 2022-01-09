package answer;

import org.httprpc.sql.ResultSetAdapter;
import org.json.JSONArray;
import psql.ResultSetToJsonExtractor;
import psql.WriterReaderPsql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinsServerAnswer extends ServerAnswer{

    public static void main(String[] args) throws Exception {
        String dbRequest = "SELECT * FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        ResultSet resultSet;
        resultSet = (ResultSet) writerReaderPsql.getDbAnswer(dbRequest);
        ResultSetToJsonExtractor resultSetToJsonExtractor = new ResultSetToJsonExtractor();
        JSONArray jsonArray = new JSONArray();
        jsonArray = resultSetToJsonExtractor.convertToJSON(resultSet);
        System.out.println(jsonArray);
    }

    @Override
    public String buildAnswer(String clientRequest) throws SQLException {

        String dbRequest = "SELECT * FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        ResultSetAdapter resultSetAdapter;
        resultSetAdapter = writerReaderPsql.getDbAnswer(dbRequest);
        while (resultSetAdapter.iterator().hasNext()){

            System.out.println("Db answer   " + resultSetAdapter.next());
        }
        return "Coins  " + clientRequest;
    }
}
