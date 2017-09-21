/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static web.ListaAlunos.DATABASE_URL;
import static web.ListaAlunos.JDBC_DRIVER;

/**
 *
 * @author raul
 */
@WebServlet(name = "AlunosSelecionados", urlPatterns = {"/AlunosSelecionados"})
public class AlunosSelecionados extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String str="";
        Enumeration<String> campos = request.getParameterNames();
        // abre a lista que está no objeto session
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        ArrayList<Matricula> lista = (ArrayList<Matricula>)session.getAttribute("list");
        ArrayList<String> alunos = (ArrayList<String>)session.getAttribute("alunos");
     
        // Cria uma lista auxiliar
        ArrayList<Matricula> listaAux = new ArrayList<>();
  
        int i = 0;
        for (Enumeration<String> e = campos; e.hasMoreElements();){
            str = (String)e.nextElement();
            for (Matricula alu: lista){
                String ok = request.getParameter("checkbox[" + i + "]");
                if (ok != null){
                   listaAux.add(alu);
                }
                i++;
            }
        }
        
        if(listaAux.isEmpty()){
            System.out.println("vaziaa");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Matrícula</title>");
            out.println( "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
            out.println("</head>");
            out.println("<body class=\"background-image background-image-fixed\" style=\"background-image : url('http://mtquadrado.com.br/wp-content/uploads/home-1-bg-0.jpg')\">");
            out.println("<center><h1>UNIVERSIDADE FEDERAL DE SERGIPE</h1><center>");
            out.println("<img src=\"logoUFS.png\">");
            out.println("<center><br><h2>Nenhuma Disciplina Selecionada</h2></center>");
            out.println("<center><h3>Por gentileza selecione as disciplinas.</h3></center>");
            out.println("<br/><center><input type='submit' onclick=\"goBack()\" value='Voltar' name='voltar' class='button'/></center>");
            out.println("<script>\n" +
                "function goBack() {\n" +
                "    window.history.back();\n" +
                "}\n" +
                "</script>");
            out.println("</body>");
            out.println("</html>");
        }else{
      
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<title>Solicitacao de Matrícurlas</title>");
            out.println("<meta charset=\"UTF-8\">"); 
            out.println( "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
            out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
                     
            out.println("</head>");
            out.println("<body class=\"background-image background-image-fixed\" style=\"background-image : url('http://mtquadrado.com.br/wp-content/uploads/home-1-bg-0.jpg')\">");
            out.println("<center><h1>UNIVERSIDADE FEDERAL DE SERGIPE</h1><center>");
            out.println("<img src=\"logoUFS.png\">");
            out.println("<div align=\"center\">");
                     out.println("<center><br><h2>Dados do aluno </h2></center>");
                     out.println("<b>Matricula:</b>" + alunos.get(0)
                            + "<b></br>Nome Aluno:</b>" + alunos.get(1)
                            +"<b></br>Codigo Curso:</b>"+alunos.get(2)); 
            out.println("</div>");  
            
            
            out.println("<center><br><h2>Disciplinas Solicitadas</h2></center>");
            out.println("<table align=\"center\" class = \"table table-bordered\"<tr>");
	    out.println("<th><b>Nome da Disciplina</b></th><th><b>Código da Disciplina</b></th>" +
                            "<th><b>Turma</b></th>" +
                            "<th><b>Horário</b></th>" +"</tr>");
            for (Matricula matricula: listaAux){
                    out.println("<tr><td>" + matricula.getCodDisciplina() + "</td>"
                                + "<td>" + matricula.getNomeDisciplina() + "</td>"
                                + "<td>" + matricula.getCodTurma() + "</td>"
                                + "<td>" + matricula.getDiaHora()+ "</td></tr></br>"); 
                    
                    try{
                        Class.forName(JDBC_DRIVER );
                        Connection conn = (Connection) DriverManager.getConnection(DATABASE_URL,
                                "root", "1984am00" );
                       Statement stAluno = (Statement) conn.createStatement();
                       String query = "insert into matriculas (MatriculaDoAluno,CodigoDisciplina,Turma)"+"values('"+alunos.get(0)+"','"+matricula.getNomeDisciplina()+"','"+matricula.getCodTurma()+"')";
                       stAluno.executeUpdate(query);
                    }
                    catch(SQLException s) {
                       out.println("SQL Error: " + s.toString() + " "
                               + s.getErrorCode() + " " + s.getSQLState());
                   }
                                
            }                  
            out.println("</table>");
            
            out.println("<center><h3>Solicitação feita com Sucesso!</h3></center>");    
                        
            out.println("<br/><center><input type='submit' onclick=\"goBack()\" value='Voltar' name='voltar' class='button'/></center>");
            out.println("<script>\n" +
            "function goBack() {\n" +
            "    window.history.back();\n" +
            "}\n" +
            "</script>");
            out.println("<script src=\"js/index.js\"></script>");
            out.println("</body>");
            out.println("</html>");
            
            
            //lista.clear();
            //listaAux.clear();
            } catch (Exception e) {
	            out.println("Error: " + e.toString()
	                + e.getMessage());
                }
                    out.close();
        
        
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
        processRequest(request, response);
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
        processRequest(request, response);
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
