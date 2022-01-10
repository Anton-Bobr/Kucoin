package servlet;

import org.httprpc.sql.ResultSetAdapter;
import psql.WriterReaderPsql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CheckWhaleMessagesServlet", urlPatterns = {"/checkWhaleMessages"})
public class CheckWhaleMessagesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String dbRequest = "SELECT time, coin, amount, amount_usd FROM whale_message WHERE coin  IN ('ETH', 'BNB')\n" +
                "AND amount BETWEEN 10000 AND 50000 AND time BETWEEN '2021-01-10 04:10:27' AND '2021-05-10 04:10:27';";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        ResultSetAdapter resultSetAdapter = null;


        resp.getWriter().println("{coins}");
    }

}
