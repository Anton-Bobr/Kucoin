package answer;

import org.json.JSONArray;
import psql.WriterReaderPsql;


public class CoinsServerAnswer extends ServerAnswer{


    @Override
    public String buildAnswer(String clientRequest) throws Exception {

        String dbRequest = "select array_to_json(array_agg(row_to_json(t))) from (SELECT coin FROM coins ) t";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
         ;
        String Answer = writerReaderPsql.getDbAnswerInJson(dbRequest);

        return "Coins  " + Answer;
    }
}
