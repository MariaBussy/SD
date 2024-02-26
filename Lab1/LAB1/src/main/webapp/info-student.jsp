<html xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <title>Informatii student</title>
    </head>
    <body>
        <h3>Informatii student</h3>

        <!-- populare bean cu informatii din cererea HTTP -->
        <jsp:useBean id="studentBean" class="beans.StudentBean" />
        <jsp:setProperty name="studentBean" property="nume" value='<%=request.getAttribute("nume") %>'/>
        <jsp:setProperty name="studentBean" property="prenume" value='<%=request.getAttribute("prenume") %>'/>
        <jsp:setProperty name="studentBean" property="varsta" value='<%=request.getAttribute("varsta") %>'/>

        <!-- folosirea bean-ului pentru afisarea informatiilor -->
        <p>Urmatoarele informatii au fost introduse/Informatii actualizate:</p>
        <form action="./process-student" method="post">
             Nume:<jsp:getProperty name="studentBean"
                              property="nume" />
             <input type="text" name="nume" />
             <br />
             Prenume: <jsp:getProperty name="studentBean"
                                  property="prenume" />
             <input type="text" name="prenume" />
             <br />
             Varsta: <jsp:getProperty name="studentBean"
                                 property="varsta" />
             <input type="number" name="varsta" />
             <br />
             <br />
             <button type="submit" name="submit">Actualizare</button>
        </form>
    </body>
</html>