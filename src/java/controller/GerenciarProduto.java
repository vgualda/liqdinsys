package controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.Produto;
import model.ProdutoDAO;

/**
 * @author mushr
 */
@WebServlet("/gerenciarProduto")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, //10 MB
        maxFileSize = 1024 * 1024 * 1024, //1 GB 
        maxRequestSize = 1024 * 1024 * 50)  // 50 GB

public class GerenciarProduto extends HttpServlet {

    private static final long serialVersionUID = 1L;

//    public GerenciarProduto() {
//        super();
//
//    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        String idProduto = request.getParameter("idProduto");
        String mensagem = "";

        try {
            Produto pd = new Produto();
            ProdutoDAO pdao = new ProdutoDAO();

            if (acao.equals("listar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    ArrayList<Produto> produtos = new ArrayList<>();
                    produtos = pdao.listar();
                    for (Produto produto : produtos) {
                        System.out.println(produto);
                    }
                    RequestDispatcher dispatcher
                            = getServletContext().getRequestDispatcher("/listarProdutos.jsp");
                    request.setAttribute("produtos", produtos);
                    dispatcher.forward(request, response);
                } else {
                    mensagem = "Acesso não autorizado!";
                }
            }
            
            if (acao.equals("alterar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    pd = pdao.getProdutoPorId(Integer.parseInt(idProduto));
                    if (pd.getIdProduto() > 0) {
                        RequestDispatcher dispatcher
                                = getServletContext().getRequestDispatcher("/cadastrarProduto.jsp");
                        request.setAttribute("produto", pd);
                        dispatcher.forward(request, response);
                    } else {
                        mensagem = "Produto não encontrado na base de dados!";
                    }
                }
            } else {
                mensagem = "Acesso não autorizado!";
            }

            if (acao.equals("ativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    pd.setIdProduto(Integer.parseInt(idProduto));
                    if (pdao.ativar(pd)) {
                        mensagem = "Produto ativado com sucesso!";
                    } else {
                        mensagem = "Falha ao ativar o produto!";
                    }
                } else {
                    mensagem = "Acesso não autorizado!";
                }
            }

            if (acao.equals("desativar")) {
                if (GerenciarLogin.verificarPermissao(request, response)) {
                    pd.setIdProduto(Integer.parseInt(idProduto));
                    if (pdao.desativar(pd)) {
                        mensagem = "Produto desativado com sucesso!";
                        
                    } else {
                        mensagem = "Falha ao desativar o produto!";
                    }
                } else {
                    mensagem = "Acesso não autorizado!";

                }
            }
        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarProduto?acao=listar';"
                + "</script>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String idProduto = request.getParameter("idProduto");
        String nome = request.getParameter("nome");
        String estoque = request.getParameter("estoque");
        String preco = request.getParameter("preco");
        String status = request.getParameter("status");
        String mensagem = "";

        Part parte = request.getPart("nomeArquivo");
        String fileName = extractFileName(parte);
        String savePath
                = "C:\\Users\\mushr\\Documents\\NetBeansProjects\\liqdinsys\\web\\imagens\\produtos\\"
                + fileName;

        File fileSaveDir = new File(savePath);
        parte.write(savePath + File.separator);
        String filePath = savePath + File.pathSeparator + fileName;

        Produto pd = new Produto();

        if (!idProduto.isEmpty()) {
            pd.setIdProduto(Integer.parseInt(idProduto));
        }

        if (nome.isEmpty() || nome.equals("")) {
            request.setAttribute("msg", "Informe o nome do produto!");
            despacharRequisicao(request, response);
        } else {
            pd.setNome(nome);
        }
        
        pd.setEstoque(Integer.parseInt(estoque));

        if (preco.isEmpty() || preco.equals("")) {
            request.setAttribute("msg", "Informe o preço do produto!");
            despacharRequisicao(request, response);
        } else {
            
        }

        if (status.equals("") || status.isEmpty()) {
            request.setAttribute("msg", "Informe o status do produto!");
            despacharRequisicao(request, response);
        } else {
            pd.setStatus(Integer.parseInt(status));
        }

        //FORMATAÇÃO DO PREÇO -->
        double novoPreco = 0;
        if (!preco.isEmpty()) {
            novoPreco = Double.parseDouble(
                    preco.replace(",", "."));
        }
        // --------------------->

        
        pd.setPreco(novoPreco);
        pd.setNomeArquivo(fileName);
        pd.setCaminho(savePath);

        try {
            ProdutoDAO pdao = new ProdutoDAO();
            if (pdao.gravar(pd)) {
                mensagem = "Produto foi gravado com sucesso!";
            } else {
                mensagem = "Ocorreu uma falha ao gravar o produto na base de dados!";
            }
        } catch (SQLException e) {
            mensagem = "Erro123: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('" + mensagem + "');"
                + "location.href='gerenciarProduto?acao=listar';"
                + "</script>");
    }

    /* 
         * O nome do arquivo que será feito o upload será incluído
	 * no content-disposition-header:
	 * form-data
	 * name ="data file"
	 * filename = "photo.jpg" ou photo.png"
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";

    }

    private void despacharRequisicao(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().
                getRequestDispatcher("/cadastrarProduto.jsp").
                forward(request, response);

    }

}
