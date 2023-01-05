package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Menu;
import model.Usuario;
import model.UsuarioDAO;

/**
 *
 * @author mushr
 */
@WebServlet(name = "GerenciarLogin", urlPatterns = {"/gerenciarLogin"})
public class GerenciarLogin extends HttpServlet {

    private static HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession sessao = request.getSession();
        sessao.removeAttribute("ulogado");
        sessao.invalidate(); //desvincula objeto atado a ela
        response.sendRedirect("formLogin.jsp");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        GerenciarLogin.response = response;
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");
        String mensagem = "";
        
        Usuario u = new Usuario();
        UsuarioDAO udao = new UsuarioDAO();

        if (login.equals("") || login.isEmpty()) {
            request.setAttribute("msg", "Informe o login do usuário!");
            despacharRequisicao(request, response);
        } else {
            u.setLogin(login);

        }

        if (senha.equals("") || senha.isEmpty()) {
            request.setAttribute("msg", "Informe a senha do usuário!");
            despacharRequisicao(request, response);
        } else {
            u.setSenha(senha);

        }
        try {
            u = udao.getRecuperarUsuario(login);
            if ((u.getIdUsuario() > 0) && (u.getSenha().equals(senha))) {
                HttpSession sessao = request.getSession();
                sessao.setAttribute("ulogado", u);
                response.sendRedirect("index.jsp");

            } else {
                exibirMensagem("Login ou Senha Inválidos!");

            }

        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();

        }

    }

    private void despacharRequisicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/formLogin.jsp").
                forward(request, response);

    }

    private static void exibirMensagem(String mensagem) {
        try {
            PrintWriter out = response.getWriter();
            out.print(
                    "<script type='text/javascript'>"
                    + "alert('" + mensagem + "');"
                    + "history.back();"
                    + "</script>");
            out.close();

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static Usuario verificarAcesso(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        GerenciarLogin.response = response;
        Usuario u = null;
        boolean possuiAcesso = false;

        try {
            HttpSession sessao = request.getSession();
            if (sessao.getAttribute("ulogado") == null) {
                response.sendRedirect("formLogin.jsp");

            } else {
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();

                if (queryString != null) {
                    uri += "?" + queryString;

                }

                u = (Usuario) request.getSession().getAttribute("ulogado");

                if (u == null) {
                    sessao.setAttribute("msg", "Usuário não autenticado no sistema!");
                    response.sendRedirect("formLogin.jsp");
                } else {
                    for (Menu m : u.getPerfil().getMenus()) {
                        if (uri.contains(m.getLink())) {
                            possuiAcesso = true;
                            break;
                        }

                    }
//                    if (!possuiAcesso) {
//                        exibirMensagem("Usúario não autorizado! Erro ocorre no verificarAcesso, porem é chamado o metodo verificarPermissao. Oq?");
//                    }
                }

            }
        } catch (Exception e) {
            exibirMensagem("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        return u;
    }

    public static boolean verificarPermissao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        GerenciarLogin.response = response;
        Usuario u = null;
        boolean possuiAcesso = false;

        try {
            HttpSession sessao = request.getSession();
            if (sessao.getAttribute("ulogado") == null) {
                response.sendRedirect("formLogin.jsp");

            } else {
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();

                if (queryString != null) {
                    uri += "?" + queryString;
                }

                u = (Usuario) request.getSession().getAttribute("ulogado");

                if (u == null) {

                    sessao.setAttribute("msg", "Usuário não autenticado no sistema!");
                    response.sendRedirect("formLogin.jsp");
                } else {

                    for (Menu m : u.getPerfil().getMenus()) {
                        if (uri.contains(m.getLink())) {
                            possuiAcesso = true;
                            break;
                        }

                    }

                }

            }
        } catch (Exception e) {
            exibirMensagem("Erro: " + e.getMessage());
            e.printStackTrace();
        }

        return possuiAcesso;
    }

}
