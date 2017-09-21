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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author raul
 */
@WebServlet(name = "ListaAlunos", urlPatterns = {"/ListaAlunos"})
public class ListaAlunos extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";        
    static final String DATABASE_URL = "jdbc:mysql://localhost/matricula";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        // Obtendo um objeto session
        HttpSession session = request.getSession();
	       try (PrintWriter out = response.getWriter()) {
                   Connection conn;
                   String numMatricula = request.getParameter("matricula");
                   String saida = null;
                    if(numMatricula.equals("")){
                         
                        out.println("<html>");
                        out.println("<head>");
                        out.println("<body>");
                        out.println("<script>");
                        out.println("alert(\"Matrícula Incorreta\");");
                        out.println("window.history.back();");
                        out.println("</script>");
                        out.println("</body>");
                        out.println("</html>");
                    
                    }
                   ArrayList<Matricula> lista = new ArrayList<>();
                   ArrayList<String> alunos = new ArrayList<>();
                   try {
                       Class.forName(JDBC_DRIVER );
                       conn = (Connection) DriverManager.getConnection(DATABASE_URL,
                               "root", "1984am00" );
                       Statement stAluno = (Statement) conn.createStatement();
                       ResultSet recAluno =  stAluno.executeQuery("SELECT * FROM aluno "
                               + "WHERE aluno.matricula = "+ numMatricula);
                       if(!recAluno.next()){
                           
                           out.println("<html>");
                           out.println("<head>");
                           out.println("<body>");
                           out.println("<script>");
                           out.println("alert(\"Matrícula Incorreta\");");
                           out.println("window.history.back();");
                           out.println("</script>");
                           out.println("</body>");
                           out.println("</html>");
                       }else{
                           out.println("<html>");
                           out.println("<head>");
                           out.println("<meta charset=\"UTF-8\">");
                           out.println("<title>Disciplinas Ofertadas</title>");
                           out.println("<meta charset=\"UTF-8\">");
                           out.println( "<link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\"/>");
                           out.print("<link rel=\"stylesheet\" href=\"styleTable.css\" type=\"text/css\">");
                           out.println("</head>");
                     
                           out.println("<body class=\"background-image background-image-fixed\" style=\"background-image : url('http://mtquadrado.com.br/wp-content/uploads/home-1-bg-0.jpg')\">");
                           out.println("<center><h1>UNIVERSIDADE FEDERAL DE SERGIPE</h1><center>");
                           out.println("<img src=\"logoUFS.png\">");
                           out.println("<center><br><h2>Disciplinas Ofertadas</h2></center>");
                           
                           Statement st = (Statement) conn.createStatement();
                           ResultSet rec = st.executeQuery(
                            "SELECT turmas.CodigoDisciplina, disciplinas.NomeDisciplina, turmas.CodigoTurma, turmas.Horario " +
                            "FROM turmas " +
                             "INNER JOIN disciplinas " +
                              "ON turmas.CodigoDisciplina = disciplinas.CodigoDisciplina "+
                            "ORDER BY turmas.CodigoDisciplina");
                           out.println("<table align=\"center\" class = \"table table-bordered\"<tr>");
                           out.println("<th><b>Nome da Disciplina</b></th><th><b>Código da Disciplina</b></th>" +
                                   "<th><b>Turma</b></th>" +
                                   "<th><b>Horário</b></th>" +
                                   "<th><b>Selecionar</b></th>" + "</tr>");
                           out.println("<form action=\"AlunosSelecionados\"");
                           int i = 0;
                           
                           while(rec.next()){
                               Matricula matricula = new Matricula();
                               Turma turm = new Turma();
                               matricula.setNomeDisciplina(rec.getString(1));
                               matricula.setCodDisciplina(rec.getString(2));
                               matricula.setCodTurma(rec.getString(3));
                               turm.setDia_hor(rec.getString(4));
                               matricula.setDiaHora(turm.getHoraioFormtado());
                               
                               out.println("<tr><td>" + matricula.getCodDisciplina() + "</td>"
                                       + "<td>" + matricula.getNomeDisciplina() + "</td>"
                                       + "<td>" + matricula.getCodTurma() + "</td>"
                                       + "<td>" + matricula.getDiaHora()+ "</td>"
                                       + "<td><input type=\"checkbox\" name=\"checkbox["
                                       + i + "]\" value=" + i + "</td></tr>");
                               lista.add(matricula);
                               i++;
                           }
                           out.println("</table>");
                           
                           out.println("<center><input type='submit' value='Matricular' name='enviar' class='btn btn-success'/></center>");
                           out.println("<input type='hidden' name='disciplina' value='"
                                   + numMatricula + "'/>");
                           
                           out.println("</form>");
                           out.println("<input type='submit' onclick=\"goBack()\" value='Sair' name='voltar' class='btn'/>");
                           out.println("<script>\n" +
                                   "           function goBack() {\n" +
                                   "            window.history.back();\n" +
                                   "           }\n" +
                                   "           </script>");
                          
                           out.println("<script src=\"js/index.js\"></script>");
                           out.println("</body>");
                           out.println("</html>");
                           
                           //PRO MOVES 2017
                           alunos.add(numMatricula);
                           alunos.add(recAluno.getString("NomeDoAluno"));
                           alunos.add(recAluno.getString("CodigoDoCurso"));
                           
                           session.setAttribute("list", lista);
                           session.setAttribute("alunos",alunos);
                           st.close();
                       }
                   } catch (SQLException s) {
                       out.println("SQL Error: " + s.toString() + " "
                               + s.getErrorCode() + " " + s.getSQLState());
                   } catch (ClassNotFoundException e) {
                       out.println("Error: " + e.toString()
                               + e.getMessage());
                   }
	        }    }


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
