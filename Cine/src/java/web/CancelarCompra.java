
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
import static web.InformacoesFilme.JDBC_DRIVER;

/**
 *
 * @author Raul
 */
@WebServlet(name = "CancelarCompra", urlPatterns = {"/CancelarCompra"})
public class CancelarCompra extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/cine";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        String meias = request.getParameter("meias");
        String inteiras = request.getParameter("inteiras");
        String codFilme = request.getParameter("codFilme");
        String meiasDispString = request.getParameter("meiaDisponivel");
        String inteirasDispString = request.getParameter("inteiraDisponivel");         
        String data = "";
        String horario = "";
        String sala = "";
        int ingressosVendidos = 0;
        int meiasInt = Integer.parseInt(meias);
        int inteirasInt = Integer.parseInt(inteiras);
        int meiasIntDisp = Integer.parseInt(meiasDispString);
        int inteirasIntDisp = Integer.parseInt(inteirasDispString);
            
        float rendaFilme = 0;            
        float rendaFinal=0;
            
            Class.forName(JDBC_DRIVER);
            Connection conn =                                                     
            DriverManager.getConnection(DATABASE_URL, "root", "1984am00" );
	    Statement st = conn.createStatement();
            
                        ResultSet resultSet = st.executeQuery(            
            "SELECT * FROM cine.sessoes where (codFilme = '" + codFilme + "')");
            if(resultSet.next()){
                
               rendaFilme  = resultSet.getFloat("rendaObtida");
               data = resultSet.getString("dataDaSessao");
               sala = resultSet.getString("numSala");
               ingressosVendidos = resultSet.getInt("ingressosVendidos");
               horario = resultSet.getString("horario");
              
            }
            
            int renda1, renda2;
            
            if(meiasInt > 0){
                renda1 = meiasInt * 10;
            }else{
                renda1 = 0;
            }
            if(inteirasInt > 0){
                renda2 = inteirasInt * 20;
            }else{
                renda2 = 0;
            }
            
            rendaFinal = rendaFilme - (renda1 + renda2);
            
            int finalMeia = meiasIntDisp + meiasInt;
            int finalInteira = inteirasIntDisp + inteirasInt;
            ingressosVendidos = ingressosVendidos - (meiasInt + inteirasInt); 
          
            st.executeUpdate("UPDATE cine.filmes SET "
                    + "qtdMeiasEntradas='"+ finalMeia + ""
                    + "',qtdInteira='"+ finalInteira + ""
                    + "',rendaObtida='"+ rendaFinal
                    + "' WHERE codFilme='" + codFilme + "'");
            
             st.executeUpdate("UPDATE cine.sessoes SET "
                    + "ingressosVendidos='"+ ingressosVendidos + ""
                    + "',rendaObtida='"+ rendaFinal
                    + "' WHERE codFilme='" + codFilme + "'"
            );
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Compra Cancelada</title>");    
            out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
            out.println("<link href=\"css/styleForm.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleForm.css\" type=\"text/css\">");
            out.println("</head>");
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
            out.println("<font face=\"HelveticaNeue-Light\"</font>");
            out.println("<h1>Cine</h1>");
            out.println("<img src=\"banner.png\" width=\"100px\" height=\"100px\">");            
            out.println("<h2>" + "Compra cancelada!" + "</h2>");
             out.println(" <form action='index.html'method='get'> \n"
                    + "           <br/><center>\n"
                    + "               <input type='submit' value='Home' name='enviar' class=\"btn btn-lg btn-warning\"/>\n"
                    + "           </center>\n"
                    + "            </form>");
            out.println("</form>");
            out.println("</center>");
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
            Logger.getLogger(CancelarCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CancelarCompra.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(CancelarCompra.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CancelarCompra.class.getName()).log(Level.SEVERE, null, ex);
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