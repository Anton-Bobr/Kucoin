package servlet;

import org.json.JSONArray;
import psql.WriterReaderPsql;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckCoinsServlet", urlPatterns = {"/checkCoins"})

public class CheckCoinsServlet extends HttpServlet {


    public static void main(String[] args) throws Exception {
        String dbRequest = "SELECT coin FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray = writerReaderPsql.getDbAnswerInJson(dbRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonArray);
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String dbRequest = "SELECT coin FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        JSONArray jsonArray = new JSONArray();

        try {
            jsonArray = writerReaderPsql.getDbAnswerInJson(dbRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(jsonArray.toString());
        response.getWriter().flush();
    }
}
