package servlet;

import psql.WriterReaderPsql;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckCoinsServlet", urlPatterns = {"/checkCoins"})

public class CheckCoinsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String dbRequest = "select array_to_json(array_agg(row_to_json(t))) from (SELECT coin FROM coins ) t";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        String responseFromDB = null;

        try {
            responseFromDB = writerReaderPsql.getDbAnswerInJson(dbRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(responseFromDB);
        response.getWriter().flush();
    }
}
