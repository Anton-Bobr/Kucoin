package servlet;

import org.json.JSONArray;
import psql.WriterReaderPsql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CheckWhaleMessagesServlet", urlPatterns = {"/checkWhaleMessages"})
public class CheckWhaleMessagesServlet extends HttpServlet {


    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String dateStart = (request.getParameter("dateStart")).replace('T',' ');
        String dateStop = (request.getParameter("dateStop")).replace('T',' ');
        String coins = ("'" + (request.getParameter("coins")) + "'").replace("-","', '");

        String dbRequest = "SELECT time, coin, amount, amount_usd FROM whale_message WHERE coin  IN ("+ coins +
                ") AND time BETWEEN '" + dateStart + "' AND '"+ dateStop +"';";
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
