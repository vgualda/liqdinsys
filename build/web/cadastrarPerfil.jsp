
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0,
              shrink-to-fit=no">
        <link rel="stylesheet" href="bootstrap/bootstrap.min.css" type="text/css">
        <link rel="stylesheet" href="fonts/css/all.css" type="text/css">
        <link rel="stylesheet" href="css/menu.css" type="text/css">
        <link rel="stylesheet" href="css/styles.css" type="text/css">
        <title>Cadastro de Perfil</title>
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
                </div>

                <div id="conteudo" class="bg-background">
                    <form action="gerenciarPerfil" method="POST" 
                          accept-charset="iso-8859-1,utf-8">
                        <h3 class="text-center mt-5"><b>Cadastro de Perfil</b></h3>

                        <input type="hidden" id="idperfil" name="idPerfil" 
                               value="${perfil.idPerfil}">

                    <div class="form-group row offset-md-2 mt-3">
                        <label for="idnome" class="col-md-1 form-label btn btn-secondary btn-sm">Nome</label>
                        <div class="col-md-6">
                            <input type="text" name="nome" id="idnome" 
                                   class="form-control-sm" value="${perfil.nome}">

                        </div>
                    </div>

                    <div class="form-group row offset-md-2 mt-3">
                        <label for="idstatus" class="col-md-1 form-label btn btn-secondary btn-sm">Status</label>
                        <div class="col-md-6">
                            <select id="idstatus" name="status"
                                    class="form-control-sm mt-2">
                                <option value="">Escolha uma Op????o</option>
                                <option value="1">
                                    <c:if test="${perfil.status == 1}"></c:if>
                                        Ativado
                                    </option>
                                    <option value="0">
                                    <c:if test="${perfil.status == 0}"></c:if>
                                        Desativado
                                    </option>
                                </select>
                            </div>
                        </div>


                        <div class="form-group row offset-md-2 mt-3">
                            <label for="iddata" class="col-md-2 form-label btn btn-secondary btn-sm">Data de Cadastro</label>
                            <div class="col-lg-1">
                                <input type="date" name="dataCadastro" id="iddata" 
                                       class="form-control-sm" value="${perfil.dataCadastro}">
                        </div>
                    </div>

                    <div class="d-md-flex justify-content-md-center mr-3">
                        <button  
                            class="btn btn-primary btn-md mr-2">
                            Gravar&nbsp;
                            <i class="fa-solid fa-floppy-disk"></i>
                        </button>

                        <a href="gerenciarPerfil?acao=listar"
                           class="btn btn-info btn-md" role="button">
                            Voltar&nbsp;<i class="fa-solid fa-rotate-left"></i>
                        </a>
                    </div>



                </form>
            </div>
        </div>
    </body>


    <!--JQuery.js -->
    <script src="js/jquery.min.js"></script>
    <!--Popper.js via cdn -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <!-- Bootstrap.js -->
    <script src="js/bootstrap.min.js"></script>

</body>
</html>
