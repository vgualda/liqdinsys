
<%@ page language="java" contentType="text/html; utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <title>liQdin - Cadastro de Produto</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta http-equiv="content-type" content="text/html">
        <meta name="viewport" content="width=device-width, 
              initial-scale=1.0, shrink-to-fit=no">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css">
    </head>
    <body class="bg-background bg">

        <%
            //HTTP 1.1
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            // HTTP 1.0
            response.setHeader("Pragma", "no-cache");
            //HTTP Proxie
            response.setHeader("Expires", "0");

            if (session.getAttribute("ulogado") == null) {
                response.sendRedirect("formLogin.jsp");
            }
        %>

        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('" + msg + "');"
                        + "</script>");
            }

        %>

        <div id="container">

            <div id="header">
                <jsp:include page="banner.jsp"></jsp:include>

                </div>
                <div id="menu">
                <jsp:include page="menu.jsp"></jsp:include>

                </div> <!-- fim da div menu -->

                <div id="conteudo" class="bg-background">
                    <form action="gerenciarProduto" method="POST" 
                          enctype="multipart/form-data">
                        <h3 class="text-center mt-3"><b>Cadastro de Produto</b></h3>

                        <input type="hidden" name="idProduto" id="idproduto" 
                               value="${produto.idProduto}">

                    <div class="form-group row offset-md-2 mt-5">
                        <label for="idnome"
                               class="col-md-1 form-label btn btn-secondary btn-sm">Produto</label>
                        <div class="col-md-6">
                            <input type="text" name="nome" id="idnome"
                                   class="form-control-sm"
                                   value="${produto.nome}">

                        </div>

                    </div>
                    <div class="form-group row offset-md-2">
                        <label for="idestoque"
                               class="col-md-1 form-label btn btn-secondary btn-sm">Estoque</label>
                        <div class="col-md-6">
                            <input type="text" name="estoque" id="idestoque"
                                   class="form-control-sm" 
                                   value="${produto.estoque}"> 

                        </div>

                    </div>

                    <div class="form-group row offset-md-2">
                        <label for="idpreco"
                               class="col-md-1 form-label btn btn-secondary btn-sm">Preço</label>
                        <div class="col-md-6">
                            <input type="text" name="preco" id="idpreco"
                                   class="form-control-sm"
                                   value="${produto.preco}"/> 

                        </div>

                    </div>


                    <div class="form-group row offset-md-2">
                        <label for="idNomeArquivo"
                               class="col-md-1 form-label btn btn-secondary btn-sm">Imagem</label>
                        <div class="col-md-6">
                            <input type="file" multiple="multiple" 
                                   name="nomeArquivo" id="idNomeArquivo"
                                   class="form-control-sm"
                                   value="${produto.nomeArquivo}" > 

                        </div>

                    </div>

                    <div class="form-group row offset-md-2">
                        <label for="idstatus"
                               class="col-md-1 form-label btn btn-secondary btn-sm">Status</label>
                        <div class="col-md-6">
                            <select id="idstatus" name="status" 
                                    class="form-control-sm">
                                <option value="">Escolha uma Opção</option>
                                <option value="1">
                                    <c:if test="${produto.status == 1}"></c:if>
                                        Ativado
                                    </option>
                                    <option value="0">
                                    <c:if test="${produto.status == 0}"></c:if>
                                    Desativado
                                </option>
                            </select>
                        </div>
                    </div>

                    <div class="d-grip gap-2 d-md-flex justify-content-md-center mr-3">
                        <button class="btn btn-primary btn-md mr-2">
                            Gravar&nbsp;
                            <i class="fa-solid fa-floppy-disk"></i>
                        </button>
                        <a href="gerenciarProduto?acao=listar"
                           class="btn btn-info btn-md"
                           role="button">
                            Voltar&nbsp;
                            <i class="fa-solid fa-circle-left"></i>
                        </a>

                    </div>


                </form>


            </div> <!--  fim da div conteúdo -->
        </div><!-- fim da div conteiner -->
        <!-- JQuery.js -->
        <script src="js/jquery.min.js"></script>


        <!-- Popper via cdn -->
        <script src = "https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" 
                integrity = "sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" 
                crossorigin = "anonymous">
        </script>

        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>


    </body>
</html>