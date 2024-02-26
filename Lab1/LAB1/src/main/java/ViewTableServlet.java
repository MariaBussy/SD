import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ViewTableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse)
            throws ServletException, IOException {
        try {
            CRUDOperations.setConnection("jdbc:sqlite:/home/student/opt/studenti.db");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try{
            CRUDOperations.exportDatabaseAsJSON("/home/student/opt/studenti.json");
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        String filename = "/home/student/opt/studenti.json";

        File jsonFile = new File(filename);
        if (!jsonFile.exists()) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "JSON file not found.");
            return;
        }

        byte[] bytes = new byte[(int) jsonFile.length()];
        try (InputStream inputStream = new FileInputStream(jsonFile)) {
            inputStream.read(bytes);
        }

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getOutputStream().write(bytes);
    }
}