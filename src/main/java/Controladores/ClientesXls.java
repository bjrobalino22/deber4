package Controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Modelo.Clientes;
import Servicios.ClientesService;
import Servicios.ClientesServiceImplement;
import com.google.gson.Gson;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

@WebServlet({"/Clientes"})
public class ClientesXls extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Crear instancia del servicio
        ClientesService service = new ClientesServiceImplement();
        List<Clientes> clientes = service.listar();

        // Verificar el formato solicitado (html, xls o json)
        String format = req.getParameter("format");

        if ("json".equalsIgnoreCase(format)) {
            // Descargar en formato JSON
            generarJson(clientes, resp);
        } else if ("xls".equalsIgnoreCase(format)) {
            // Descargar en formato Excel
            generarExcel(clientes, resp);
        } else {
            // Mostrar la tabla en HTML por defecto
            mostrarTablaHTML(clientes, resp);
        }
    }

    private void generarJson(List<Clientes> clientes, HttpServletResponse resp) throws IOException {
        // Configurar la respuesta para JSON
        resp.setContentType("application/json;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment; filename=clientes.json");

        // Convertir la lista de clientes a JSON
        Gson gson = new Gson();
        String json = gson.toJson(clientes);

        // Escribir el JSON en la respuesta
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"))) {
            out.write(json);
        }
    }

    private void generarExcel(List<Clientes> clientes, HttpServletResponse resp) throws IOException {
        // Configurar el encabezado para la descarga de archivo Excel
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=clientes.xls");

        // Crear un libro de trabajo de Excel
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Clientes");

        // Crear encabezados de columna
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID Cliente");
        headerRow.createCell(1).setCellValue("CI");
        headerRow.createCell(2).setCellValue("Nombre");
        headerRow.createCell(3).setCellValue("Dirección");
        headerRow.createCell(4).setCellValue("Teléfono");

        // Rellenar datos de clientes
        int rowNum = 1;
        for (Clientes cliente : clientes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(cliente.getidCliente());
            row.createCell(1).setCellValue(cliente.getCi());
            row.createCell(2).setCellValue(cliente.getnombre());
            row.createCell(3).setCellValue(cliente.getdireccion());
            row.createCell(4).setCellValue(cliente.gettelefono());
        }

        // Ajustar automáticamente el ancho de las columnas
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Escribir el libro de trabajo en la respuesta
        try (OutputStream out = resp.getOutputStream()) {
            workbook.write(out);
        } finally {
            workbook.close();
        }
    }

    private void mostrarTablaHTML(List<Clientes> clientes, HttpServletResponse resp) throws IOException {
        // Configurar la respuesta como HTML
        resp.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            // Escribir la tabla en HTML
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"utf-8\">");
            out.println("<title>Listado de Clientes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Listado de Clientes</h1>");
            out.println("<table border=\"1\" style=\"border-collapse: collapse; width: 100%;\">");
            out.println("<tr>");
            out.println("<th>ID Cliente</th>");
            out.println("<th>CI</th>");
            out.println("<th>Nombre</th>");
            out.println("<th>Dirección</th>");
            out.println("<th>Teléfono</th>");
            out.println("</tr>");

            // Iterar sobre la lista de clientes y generar las filas de la tabla
            clientes.forEach(cliente -> {
                out.println("<tr>");
                out.println("<td>" + cliente.getidCliente() + "</td>");
                out.println("<td>" + cliente.getCi() + "</td>");
                out.println("<td>" + cliente.getnombre() + "</td>");
                out.println("<td>" + cliente.getdireccion() + "</td>");
                out.println("<td>" + cliente.gettelefono() + "</td>");
                out.println("</tr>");
            });

            out.println("</table>");
            out.println("<br>");
            out.println("<a href=\"Clientes?format=xls\">Descargar en Excel</a>");
            out.println("<br>");
            out.println("<a href=\"Clientes?format=json\">Descargar en JSON</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
