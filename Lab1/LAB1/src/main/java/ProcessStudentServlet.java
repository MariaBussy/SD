import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.File;
import java.time.Year;

import beans.StudentBean;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ProcessStudentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        // se citesc parametrii din cererea de tip POST
        String nume = request.getParameter("nume");
        if(nume==""){
            // deserializare student din fisierul XML de pe disc
            File file = new File("/home/student/opt/student.xml");
            // se returneaza un raspuns HTTP de tip 404 in cazul in care nu se gaseste fisierul cu date
            if (!file.exists()) {
                response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
                return;
            }
            XmlMapper xmlMapper = new XmlMapper();
            StudentBean bean = xmlMapper.readValue(file, StudentBean.class);
            nume= bean.getNume();
        }
        String prenume = request.getParameter("prenume");
        if(prenume==""){
            // deserializare student din fisierul XML de pe disc
            File file = new File("/home/student/opt/student.xml");
            // se returneaza un raspuns HTTP de tip 404 in cazul in care nu se gaseste fisierul cu date
            if (!file.exists()) {
                response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
                return;
            }
            XmlMapper xmlMapper = new XmlMapper();
            StudentBean bean = xmlMapper.readValue(file, StudentBean.class);
            prenume= bean.getPrenume();
        }
        String varstaString=request.getParameter("varsta");
        int varsta =0;
        if(varstaString==""){
            // deserializare student din fisierul XML de pe disc
            File file = new File("/home/student/opt/student.xml");
            // se returneaza un raspuns HTTP de tip 404 in cazul in care nu se gaseste fisierul cu date
            if (!file.exists()) {
                response.sendError(404, "Nu a fost gasit niciun student serializat pe disc!");
                return;
            }
            XmlMapper xmlMapper = new XmlMapper();
            StudentBean bean = xmlMapper.readValue(file, StudentBean.class);
            varsta=bean.getVarsta();
        }
        else{
            varsta=Integer.parseInt(varstaString);
        }

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
        // se trimit datele primite si anul nasterii catre o alt pagina JSP pentru afisare

        request.setAttribute("nume", nume);
        request.setAttribute("prenume", prenume);
        request.setAttribute("varsta", varsta);
        request.setAttribute("anNastere", anNastere);
        request.getRequestDispatcher("./info-student.jsp").forward(request, response);
    }
}
