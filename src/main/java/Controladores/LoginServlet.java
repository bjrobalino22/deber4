package Controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet({"/Login"})
public class LoginServlet extends HttpServlet {

    // Credenciales estáticas para comparación
    private static final String USERNAME = "usuario";
    private static final String PASSWORD = "1234";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String usuario = req.getParameter("username");
        String password = req.getParameter("password");

        // Verificar las credenciales
        if (USERNAME.equals(usuario) && PASSWORD.equals(password)) {
            // Configurar la respuesta como HTML
            resp.setContentType("text/html;charset=UTF-8");

            // Generar la respuesta HTML
            try (PrintWriter out = resp.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset=\"utf-8\">");
                out.println("<title>Bienvenido</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Bienvenido, " + usuario + "!</h1>");
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            // Enviar un error 401 si las credenciales son incorrectas
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lo sentimos, no tiene acceso.");
        }
    }
}
