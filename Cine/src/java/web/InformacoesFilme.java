/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static web.Verificar.JDBC_DRIVER;

/**
 *
 * @author Raul
 */
@WebServlet(name = "InformacoesFilme", urlPatterns = {"/InformacoesFilme"})
public class InformacoesFilme extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/cine";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            Class.forName(JDBC_DRIVER);
            Connection conn
                    = DriverManager.getConnection(DATABASE_URL, "root", "1984am00");
            Statement st = conn.createStatement();

            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cine</title>");
            out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
            out.println("<link href=\"css/styleForm.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleForm.css\" type=\"text/css\">");
            out.println("</head>");

            out.println("<font face=\"HelveticaNeue-Light\"</font>");
            out.println("<body background=\"http://conditioning.com.br/wp-content/uploads/2016/02/background-cinza-1.jpg\">");
             out.println("  <div class=\"cover\">\n" +
"      <div class=\"navbar navbar-default\">\n" +
"        <div class=\"container\">\n" +
"          <div class=\"navbar-header\">\n" +
"            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar-ex-collapse\">\n" +
"              <span class=\"sr-only\">Toggle navigation</span>\n" +
"              <span class=\"icon-bar\"></span>\n" +
"              <span class=\"icon-bar\"></span>\n" +
"              <span class=\"icon-bar\"></span>\n" +
"            </button>\n" +
"            <a class=\"navbar-brand\" href=\"#\"><span>Cine</span></a>\n" +
"          </div>\n" +
"          <div class=\"collapse navbar-collapse\" id=\"navbar-ex-collapse\">\n" +
"            <ul class=\"nav navbar-nav navbar-right\">\n" +
"              <li class=\"active\">\n" +
"                <a href=\"index.html\">Home</a>\n" +
"              </li>\n" +
"              <li>\n" +
"                <a href=\"#contato\">Contato</a>\n" +
"              </li>\n" +
"            </ul>\n" +
"          </div>\n" +
"        </div>\n" +
"      ");
            out.println("<center>");            
            ResultSet resultSet = st.executeQuery(
                    "SELECT * FROM cine.filmes");

            out.println("<h2>Cine</h2>");
            out.println("<img src=\"banner.png\" width=\"100px\" height=\"100px\"><br>");
            out.println("<br><table align=\"center\" class=\"table table-hover\"<tr>");
            out.println("<th><b>Nome</b></th><th><b>Codigo</b></th>"
                    + "<th><b>Data Inicial de Exibição</b></th>" + "<th><b>Data Final de Exibição</b></th>"
                    + "<th><b>Quantidade de Meias Entradas</b></th>"
                    + "<th><b>Quantidade de Inteiras</b></th>" + "<th><b>Renda Obtida</b></th>" + "</tr>");
            while (resultSet.next()) {

                String nome = resultSet.getString("nomeFilme");
                String codFilme = resultSet.getString("codFilme");
                String dataInicial = resultSet.getString("dataInicial");
                String dataFinal = resultSet.getString("dataFinal");
                int qtdMeias = resultSet.getInt("qtdMeiasEntradas");
                int qtdInteiras = resultSet.getInt("qtdInteira");
                float renda = resultSet.getFloat("rendaObtida");

                out.println("<tr><td>" + nome + "</td>"
                        + "<td>" + codFilme + "</td>"
                        + "<td>" + dataInicial + "</td>"
                        + "<td>" + dataFinal + "</td>"
                        + "<td>" + qtdMeias + "</td>"
                        + "<td>" + qtdInteiras + "</td>"
                        + "<td>" + "R$ " + renda + "</td></tr>");

            }

            out.println("</table>");
            out.println("</center>");
            out.println(" <form action='index.html'method='get'> \n"
                    + "           <br/><center>\n"
                    + "               <input type='submit' value='Home' name='enviar' class=\"btn btn-lg btn-warning\"/>\n"
                    + "           </center>\n"
                    + "            </form>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InformacoesFilme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InformacoesFilme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InformacoesFilme.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InformacoesFilme.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}