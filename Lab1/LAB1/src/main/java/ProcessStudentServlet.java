import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.sql.SQLException;
import java.time.Year;

import beans.StudentBean;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ProcessStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        try {
            CRUDOperations.setConnection("jdbc:sqlite:/home/student/opt/studenti.db");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // se citesc parametrii din cererea de tip POST
        String nume = request.getParameter("nume");
        String prenume = request.getParameter("prenume");
        String varstaString=request.getParameter("varsta");
        int varsta=Integer.parseInt(varstaString);

        /*
        procesarea datelor - calcularea anului nasterii
        */
        int anCurent = Year.now().getValue();
        int anNastere = anCurent - varsta;

        // initializare serializator Jackson
        XmlMapper mapper = new XmlMapper();
        // creare bean si populare cu date
        StudentBean bean = new StudentBean();
        bean.setNume(nume);
        bean.setPrenume(prenume);
        bean.setVarsta(varsta);

        // serializare bean sub forma de string XML
        mapper.writeValue(new File("/home/student/opt/student.xml"), bean);
        try {
            CRUDOperations.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            CRUDOperations.insertIntoTable(nume, prenume, varsta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // se trimit datele primite si anul nasterii catre o alt pagina JSP pentru afisare

        request.setAttribute("nume", nume);
        request.setAttribute("prenume", prenume);
        request.setAttribute("varsta", varsta);
        request.setAttribute("anNastere", anNastere);
        request.getRequestDispatcher("./info-student.jsp").forward(request, response);
    }
}
