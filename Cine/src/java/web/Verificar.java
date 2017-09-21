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

/**
 *
 * @author Raul
 */
@WebServlet(name = "Verificar", urlPatterns = {"/Verificar"})
public class Verificar extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost/cine";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            String codFilme = request.getParameter("filme");
            String qtdMeias = request.getParameter("meiaEntrada");
            String qtdInteira = request.getParameter("inteira");
            int meias = Integer.parseInt(qtdMeias);
            int inteiras = Integer.parseInt(qtdInteira);

            int valora = 0, valorb = 0, valorc = 0, valorcam = 0;

            String saida = "";

            Class.forName(JDBC_DRIVER);
            Connection conn
                    = DriverManager.getConnection(DATABASE_URL, "root", "1984am00");
            Statement st = conn.createStatement();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Cine</title>");
            out.println("<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
            out.println("<link href=\"css/styleForm.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleForm.css\" type=\"text/css\">");
            out.println("<style>\n" +
            "      .input{color:#ccc;}\n" +
            "          .input:focus{color:#000;}\n" +
            "    </style>");
            out.println("</head>");
            out.println("<font face=\"HelveticaNeue-Light\"</font>");
            out.println("<body style=\"background-color:#D3D3D3;\">");
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
"     </div> ");
            out.println("<center>");
            ResultSet resultSet = st.executeQuery(
                    "SELECT * FROM cine.filmes WHERE (codFilme ='"+codFilme+"')");
           
            if (resultSet.next()) {

                String nome = resultSet.getString("nomeFilme");
                String dataInicial = resultSet.getString("dataINicial");
                String dataFinal = resultSet.getString("dataFinal");
                int qtdMeiasDisp = resultSet.getInt("qtdMeiasEntradas");
                int qtdInteirasDisp = resultSet.getInt("qtdInteira");
                float renda = resultSet.getFloat("rendaObtida");

                out.println("<h2>Cine</h2>");
                out.println("<img src=\"banner.png\" width=\"100px\" height=\"100px\">");
                out.println("<div class=\"section\">");
                out.println("<table align=\"center\" class=\"table table-hover\"<tr>");
                out.println("<th><b>Nome</b></th>"
                        + "<th><b>Entra em Cartaz</b></th>" + "<th><b>Sai de Cartaz</b></th>"
                        + "<th><b>Quantidade de Meias entradas (Disponível)</b></th>"
                        + "<th><b>Quantidade de Inteiras (Disponível)</b></th>" + "<th><b>Renda</b></th>" + "</tr>");

                out.println("<tr><td>" + nome + "</td>"
                        + "<td>" + dataInicial + "</td>"
                        + "<td>" + dataFinal + "</td>"
                        + "<td>" + qtdMeiasDisp + "</td>"
                        + "<td>" + qtdInteirasDisp + "</td>"
                        + "<td>" + renda + "</td></tr>");
                out.println("</table>");
                out.println("</div>");

                out.println("<br/><h1>--- Informações da Compra ---</h1>");
                if (meias == 0 && inteiras == 0 ) {
                    out.println("<h3>Por gentileza informe a quantidade de ingressos!</h3>");

                } else {
                    out.println("<center>");
                    out.println("  <div class=\"section\">"
                            + "<div class=\"container\">\n" +
                                "        <div class=\"row\">\n" +
                                "          <div class=\"col-md-12\">");
                    out.println("<table class=\"table table-condensed\">");
                    out.println("<tr> <th>Quantidade</th> <th>Ingressos</th> <th>Total</th> </tr>");
                    
                    if (qtdMeiasDisp >= meias && meias != 0) {
                        valora = meias * 10;
                        out.println("<tr class=\"success\"> <td>"+meias+"</td>"+"<td>Meia Entrada</td>"+"<td> R$"+valora+",00</td> </tr>");
                       // out.println("<b>" + meias + " ingresso(s) Meia Entrada -->  Total: R$ " + valora + ",00<br>");
                        
                        
                    } else if (meias == 0) {
                        out.println("");
                    } else if (qtdMeiasDisp < meias) {
                         out.println("<tr class=\"danger\"> <td>"+meias+"</td>"+"<td>Meia Entrada</td>"+"<td> R$"+0+",00</td> </tr>");
                         out.println("<b>Você solicitou: </b>" + meias + " meia entrada(s), Mas temos: " + qtdMeiasDisp + " ingressos, Compra da meia entrada não computada!" + "<br/>");
                    }

                    if (qtdInteirasDisp >= inteiras && inteiras != 0) {
                        valorb = inteiras * 20;
                        out.println("<tr class=\"success\"> <td>"+inteiras+"</td>"+"<td>Inteira</td>"+"<td> R$"+valorb+",00</td> </tr>");                       
                      //  out.println("" + inteiras + " ingresso(s) Inteiras --> <b> Total: R$ " + valorb + ",00<br/>");
                    } else if (inteiras == 0) {
                        out.println("");
                    } else {
                        out.println("<tr class=\"danger\"> <td>"+inteiras+"</td>"+"<td>Inteira</td>"+"<td> R$"+0+",00</td> </tr>");
                        out.println("<b>Você solicitou: </b>" + inteiras + " inteira(s), Mas temos: " + qtdInteirasDisp + " ingressos, Compra da inteira não computada!" + "<br/>");
                    }
                    out.println("</table>");
                    out.println("</div></div></div></div>");
                    out.println("</center>"); 
                }
                out.println("");
                out.println("<form action='ConfirmarCompra' method='get'>");
                
                if ((meias != 0 || inteiras != 0)) {
                  
                    int i = 0;
                    if(qtdInteirasDisp >= inteiras && qtdMeiasDisp >= meias){
                        //pra comrpar as poltronas meias
                          out.println("<div class=\"col-md-12 text-center\">  \n" +
                                      "          <div> \n" +
                                      "              </br>\n" +
                                      "              <span class=\"badge badge-warning\">1º</span>\n" + 
                                      "              <div >\n" +
                                      "                  <h2> <span class=\"label label-info\"> Escolha as poltronas da Meia Entrada </span> </h2> \n" +
                                      "              </div>\n" +
                                      "          </div><br>");
                    
                    i = 0;
                    while( i < meias){
                                                out.println("<div><label>\n" +
                        "              <p class=\"label label-default\">"+(i+1)+"ª Poltrona</p>\n" +
                        "              <input type=\"text\" name=\"poltronameia"+i+"\" value=\"\"  class=\"input\">\n" +
                        "            <br></label>                 \n" +
                        "          </div>       \n" +                             
                        "            ");
                        i = i+1;    
                            
                    }
                    // para compraras poltronas inteiras
                        out.println("<div class=\"col-md-12 text-center\">  \n" +
                                      "          <div> \n" +
                                      "              </br>\n" +
                                      "              <span class=\"badge badge-warning\">1º</span>\n" + 
                                      "              <div >\n" +
                                      "                  <h2> <span class=\"label label-info\"> Escolha as poltronas da Inteira </span> </h2> \n" +
                                      "              </div>\n" +
                                      "          </div><br>");
                    
                    i = 0;
                    while( i < inteiras){
                                                out.println("<div><label>\n" +
                        "              <p class=\"label label-default\">"+(i+1)+"ª Poltrona</p>\n" +
                        "              <input type=\"text\" name=\"poltronainteiras"+i+"\" value=\"\"  class=\"input\">\n" +
                        "            <br></label>                 \n" +
                        "          </div>       \n" +                             
                        "            ");
                        i = i+1;    
                    
                    } 
                     
                        
                      
                        out.println("<input type='hidden' name='inteira' value='" + inteiras + "' />");
                        out.println("<input type='hidden' name='inteiraDisponivel' value='" + qtdInteirasDisp + "' />");
                        out.println("<input type='hidden' name='meia' value='" + meias + "' />");                    
                        out.println("<input type='hidden' name='meiaDisponivel' value='" + qtdMeiasDisp + "' />");
                        out.println("<input type='hidden' name='codFilme' value='" + codFilme + "' />");
                        out.println("<input type='hidden' name='filme' value='" + nome + "' />");
                       // out.println("<input type='hidden' name='poltronainteira' value='" + poltronainteira + "' />");
                      //  out.println("<input type='hidden' name='poltronameia' value='" + poltronameia + "' />");
                        out.println("<br/><center><input type='submit' value='Confirmar' name='enviar' class=\"btn btn-lg btn-warning\"/></center>");
                        out.println("</form>");
                    }
                    else {
                        out.println("<form action='menu.html'method='get'>");
                        out.println("<br/><center><input type='submit' value='Voltar' name='enviar' class=\"btn btn-lg btn-warning\"/></center>");
                        out.println("</form>");
                    }
                } else {
                    out.println("<form action='menu.html'method='get'>");
                    out.println("<br/><center><input type='submit' value='Voltar' name='enviar' class=\"btn btn-lg btn-warning\"/></center>");
                    out.println("</form>");
                }

                out.println("<form action='index.html'method='get'>");
                out.println("<br/><center><input type='submit' value='Home' name='enviar' class=\"btn btn-lg btn-warning\"/></center>");
                out.println("</form>");
            }
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
        } catch (SQLException ex) {
            Logger.getLogger(Verificar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Verificar.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (SQLException ex) {
            Logger.getLogger(Verificar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Verificar.class.getName()).log(Level.SEVERE, null, ex);
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

}// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
/*    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }*/

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
  /*  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }*/

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
/*    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}*/
