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

@WebServlet(name = "CheckCoinsServlet", urlPatterns = {"/checkCoins"})

public class CheckCoinsServlet extends HttpServlet {


    public static void main(String[] args) {

        String dbRequest = "SELECT * FROM coins; ";
        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
        ResultSetAdapter resultSetAdapter = null;
        try {
            resultSetAdapter = writerReaderPsql.getDbAnswer(dbRequest);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        int i = 1;
        while (resultSetAdapter.iterator().hasNext()){

            System.out.println("Db answer   " + "  " + resultSetAdapter.next());
            i = i+1;
        }

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        String dbRequest = "SELECT * FROM coins; ";
//        WriterReaderPsql writerReaderPsql = new WriterReaderPsql();
//        ResultSetAdapter resultSetAdapter = null;
//        try {
//            resultSetAdapter = writerReaderPsql.getDbAnswer(dbRequest);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        int i = 1;
//        while (resultSetAdapter.iterator().hasNext()){
//            System.out.println("Db answer   " + "  " + resultSetAdapter.next());
//            i = i+1;
//        }

        resp.getWriter().println("coins 123123");

    }
}
