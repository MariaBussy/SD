<html xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <title>Actualizare student</title>
    </head>
    <body>
        <h3>Actualizare student</h3>

        <!-- populare bean cu informatii din cererea HTTP -->
        <jsp:useBean id="studentBean" class="beans.StudentBean" />
        <jsp:setProperty name="studentBean" property="nume" value='<%=request.getAttribute("nume") %>'/>
        <jsp:setProperty name="studentBean" property="prenume" value='<%=request.getAttribute("prenume") %>'/>
        <jsp:setProperty name="studentBean" property="varsta" value='<%=request.getAttribute("varsta") %>'/>

        <!-- folosirea bean-ului pentru afisarea informatiilor -->
        <p>Urmatoarele informatii au fost introduse:</p>
        <formaction="./update-student" method="post">
             <li>Nume: <jsp:getProperty name="studentBean"
             property="nume" /></li>
             <li>Prenume: <jsp:getProperty name="studentBean"
             property="prenume" /></li>
             <li>Varsta: <jsp:getProperty name="studentBean"
             property="varsta" /></li>
             <!-- anul nasterii nu face parte din bean, il afisam separat (dacaexista) -->
             <li>Anul nasterii: <%
             Object anNastere = request.getAttribute("anNastere");
             if (anNastere != null) {
                 out.print(anNastere);
             } else {
                 out.print("necunoscut");
             }
             %></li>
             Nume: <input type="text" name="nume" />
             <br />
             Prenume: <input type="text" name="prenume" />
             <br />
             Varsta: <input type="number" name="varsta" />
             <br />
             <br />
             <button type="submit" name="submit">Actualizare</button>
        </form>
    </body>
</html>