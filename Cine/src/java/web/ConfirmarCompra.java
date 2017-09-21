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
import java.sql.PreparedStatement;
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

/**
 *
 * @author Raul
 */
@WebServlet(name = "ConfirmarCompra", urlPatterns = {"/ConfirmarCompra"})
public class ConfirmarCompra extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/cine";

//   private final String INSERT_RENDA = "INSERT INTO cine.sessoes (codFilme, dataDaSessao, numSala, horario, ingressosVendidos , rendaObtida) VALUES (?,?,?,?,?,?,?)";

    public Connection getConnection() throws SQLException {
        Connection con = null;
        con = DriverManager.getConnection("jdbc:mysql://localhost/cine");
        return con;
    }

    public void closeConnnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String meiasString = request.getParameter("meia");
            String inteirasString = request.getParameter("inteira");
            String codFilme = request.getParameter("codFilme");
             String nome = request.getParameter("filme");
            String meiasDispString = request.getParameter("meiaDisponivel");
            String inteirasDispString = request.getParameter("inteiraDisponivel");
            String data = "";
            String horario = "";
            String sala = "";           
            int meias = Integer.parseInt(meiasString);
            int inteiras = Integer.parseInt(inteirasString);
            int meiasDisp = Integer.parseInt(meiasDispString);
            int inteirasDisp = Integer.parseInt(inteirasDispString);
            int i = 0;           
            int meiasAux = 0, inteirasAux = 0;
            float rendaFilme = 0;

            int rendaMeiaAux = 0, rendaInteirasAux = 0;
            float rendafinal;

            Class.forName(JDBC_DRIVER);
            Connection conn
                    = DriverManager.getConnection(DATABASE_URL, "root", "1984am00");
            Statement st = conn.createStatement();

            ResultSet resultSet = st.executeQuery(
                    "SELECT * FROM cine.sessoes where (codFilme = '" + codFilme + "')");
            if (resultSet.next()) {
                //nome = resultSet.getString("filme");
                data = resultSet.getString("dataDaSessao");
                sala = resultSet.getString("numSala");
                horario = resultSet.getString("horario");
                rendaFilme = resultSet.getFloat("rendaObtida");
                
               
            }

            if (meias > 0) {
                rendaMeiaAux = meias * 10;
            } else {
                rendaMeiaAux = 0;
            }

            if (inteiras > 0) {
                rendaInteirasAux = inteiras * 20;
            } else {
                rendaInteirasAux = 0;
            }
            
            rendafinal = rendaFilme + rendaMeiaAux + rendaInteirasAux;

            int finalMeia = meiasDisp - meias;
            int finalInteira = inteirasDisp - inteiras;
            int ingressosVendidos = meias + inteiras;
            
            st.executeUpdate("UPDATE cine.filmes SET "
                    + "qtdMeiasEntradas='"+ finalMeia + ""
                    + "',qtdInteira='"+ finalInteira + ""
                    + "',rendaObtida='"+ rendafinal
                    + "' WHERE codFilme='" + codFilme + "'");
            
            st.executeUpdate("UPDATE cine.sessoes SET "
                    + "ingressosVendidos='"+ ingressosVendidos + ""
                    + "',rendaObtida='"+ rendafinal
                    + "' WHERE codFilme='" + codFilme + "'"
            );

            int n = 0;
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Ingressos</title>");
            out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
            out.println("<link href=\"css/styleForm.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleForm.css\" type=\"text/css\">");
            out.println("<style>\n" +
            "      .input{color:#ccc;}\n" +
            "          .input:focus{color:#000;}\n" +
            "    </style>");
            out.println("</head>");
            out.println("<body style=\"background-color:#D3D3D3;\">"); 
            out.println("<font face=\"HelveticaNeue-Light\"</font>");
            out.println("<center>");
            out.println("<h1>Cine</h1>");
            out.println("<img src=\"banner.png\" width=\"100px\" height=\"100px\">");
            out.println("<h2>" + "Parabéns, compra concluída!<br>Ingressos comprados." + "</h2>");
                        
            i = 0;
            if (meias != 0) {
                out.println("<h3>" + "Meias Compradas" + "</h3>");
                 out.println("<table class=\"table table-hover\">");
                 out.println("<th>Codigo</th><th>Nome</th><th><b>Horario</b></th><th><b>Sesão</b></th>"
                 + "<th><b>Sala</b></th>"
                + "<th><b>Poltrona</b><th></tr>");
                while(i < meias){
                    String poltronameia = request.getParameter("poltronameia"+i+"");
                   
                    out.println("<tr><td>" + codFilme + "</td>"
                            + "<td>" + nome + "</td><td>"
                            + horario + "hrs</td>"
                            + "<td>" + data + "</td>");
                    out.println("<td>" + sala + "</td><br>");                
                    out.println("<td>" + poltronameia + "</td><br>");
                   i++; 
                }
                out.println("</table>");
            }
            i = 0;
            if (inteiras != 0){
                out.println("<h3>" + "Inteiras Compradas" + "</h3>");
                out.println("<table class=\"table table-hover\">");
                out.println("<th>Codigo</th><th>Nome</th><th><b>Horario</b></th><th><b>Data</b></th>"
                + "<th><b>Sala</b></th>"+ "<th><b>Poltrona</b><th></tr>");
                while(i < inteiras){
                    String poltronainteira = request.getParameter("poltronainteiras"+i+"");                    
                     out.println("<tr><td>" + codFilme + "</td>"
                            + "<td>" + nome + "</td>"
                             + "<td>" + horario + "hrs</td>"
                            + "<td>" + data + "</td>");                
                    out.println("<td>" + sala + "</td><br>");
                    out.println("<td>" + poltronainteira + "</td><br>");
                    i++;
                }
                out.println("</table>");
            }
            
            out.println("<form action='CancelarCompra'method='get'>");
            out.println("<input type='hidden' name='meias' value='" + meiasString + "' />");
            out.println("<input type='hidden' name='inteiras' value='" + inteirasString + "' />");
            out.println("<input type='hidden' name='codFilme' value='" + codFilme + "' />");
            out.println("<input type='hidden' name='inteiraDisponivel' value='" + finalInteira + "' />");
            out.println("<input type='hidden' name='meiaDisponivel' value='" + finalMeia + "' />");
                   
            out.println("<br><input type='submit' value='Cancelar' name='enviar' class=\"btn btn-lg btn-warning\" />");
            out.println("</form>");
            
             out.println(" <form action='index.html'method='get'> \n"
                    + "           <br/><center>\n"
                    + "               <input type='submit' value='Comprar Outro Ingresso' name='enviar' class=\"btn btn-lg btn-warning\"/>\n"
                    + "           </center>\n"
                    + "            </form>");

            out.println("</center>");
            out.println("<br><footer class=\"section section-warning\">\n" +
"      <a name=\"contato\"></a>\n" +
"      <div class=\"container\">\n" +
"        <div class=\"row\">\n" +
"          <div class=\"col-sm-6\">\n" +
"            <h1>Agradecimentos</h1>\n" +
"            <p>Desenvolvido por Raul Oliveira de Andrade como nota parcial da disciplina\n" +
"              de\n" +
"              <br>&nbsp;Programacao Web, ministrada pelo Profº Antonio Monteiro.</p>\n" +
"          </div>\n" +
"          <div class=\"col-sm-6\">\n" +
"            <p class=\"text-info text-right\">\n" +
"              <br>\n" +
"              <br>\n" +
"            </p>\n" +
"            <div class=\"row\">\n" +
"              <div class=\"col-md-12 hidden-lg hidden-md hidden-sm text-left\">\n" +
"                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-instagram text-inverse\"></i></a>\n" +
"                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-twitter text-inverse\"></i></a>\n" +
"                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-facebook text-inverse\"></i></a>\n" +
"                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-github text-inverse\"></i></a>\n" +
"              </div>\n" +
"            </div>\n" +
"            <div class=\"row\">\n" +
"              <div class=\"col-md-12 hidden-xs text-right\">\n" +
"                <a href=\"https://instagram.com/theandraderaul/\"><i class=\"fa fa-3x fa-fw fa-instagram text-inverse\"></i></a>\n" +
"                <a href=\"https://twitter.com/TheAndradeRaul\"><i class=\"fa fa-3x fa-fw fa-twitter text-inverse\"></i></a>\n" +
"                <a href=\"https://www.facebook.com/theandraderaul\"><i class=\"fa fa-3x fa-fw fa-facebook text-inverse\"></i></a>\n" +
"                <a href=\"#\"><i class=\"fa fa-3x fa-fw fa-github text-inverse\"></i></a>\n" +
"              </div>\n" +
"            </div>\n" +
"          </div>\n" +
"        </div>\n" +
"      </div>\n" +
"    </footer>\n" +
" ");
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
            Logger.getLogger(ConfirmarCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmarCompra.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ConfirmarCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmarCompra.class.getName()).log(Level.SEVERE, null, ex);
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