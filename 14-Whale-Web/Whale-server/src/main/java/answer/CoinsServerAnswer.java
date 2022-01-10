package answer;

import org.json.JSONArray;
import psql.WriterReaderPsql;


public class CoinsServerAnswer extends ServerAnswer{


    @Override
    public String buildAnswer(String clientRequest) throws Exception {

        String dbRequest = "SELECT * FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
         ;
        JSONArray jsonArray = writerReaderPsql.getDbAnswerInJson(dbRequest);

        return "Coins  " + jsonArray.toString();
    }
}
