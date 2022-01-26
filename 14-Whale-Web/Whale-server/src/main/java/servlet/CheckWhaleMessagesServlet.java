package servlet;

import psql.WriterReaderPsql;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckWhaleMessagesServlet", urlPatterns = {"/checkWhaleMessages"})
public class CheckWhaleMessagesServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String dateStart = (request.getParameter("dateStart")).replace('T',' ');
        String dateStop = (request.getParameter("dateStop")).replace('T',' ');
        String coins = ("'" + (request.getParameter("coins")) + "'").replace("-","', '");

        String dbRequest = "select array_to_json(array_agg(row_to_json(t))) from (SELECT time, coin, amount, amount_usd FROM whale_message WHERE coin  IN ("+ coins +
                ") AND time BETWEEN '" + dateStart + "' AND '"+ dateStop +"') t";
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
