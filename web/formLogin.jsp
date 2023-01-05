
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
        <title>liQdin</title>
    </head>
    <body class="bg-background bg">
        <%
            String msg = (String) request.getAttribute("msg");
            if (msg != null) {
                out.println(
                        "<script type='text/javascript'>"
                        + "alert('" + msg + "');"
                        + "history.back();"
                        + "</script>"
                );
            }
        %>
        
        <%
            String mensagem
                    = (String) request.getSession().getAttribute("mensagem");

            if (mensagem != null) {

                request.getSession().removeAttribute("mensagem");
        %>

        <div class="alert alert-info" role="alert">
            <%= mensagem %>
        </div>

        <%
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
                <form action="gerenciarLogin" method="POST" 
                      accept-charset="iso-8859-1,utf-8">
                    <h3 class="text-center mt-5"><b>liQd.in<b></h3>

                    <div class="form-group row offset-md-3 mt-5">
                        <label for="idLogin" 
                               class="col-sm-2 mt-2 badge-secondary text-center" style="border-radius: 10px">Usuário</label>
                        <div class="col-md-5">
                            <input type="text" name="login" id="idLogin" 
                                   class="form-control">

                        </div>
                    </div>
                    <div class="form-group row offset-md-3 mt-3">
                        <label for="idsenha" 
                               class="col-sm-2 mt-2 badge-secondary text-center" style="border-radius: 10px">Senha</label>
                        <div class="col-md-5">
                            <input type="password" name="senha" id="idSenha" 
                                   class="form-control">

                        </div>
                    </div>

                    <div class="d-md-flex justify-content-md-center mr-3 d">
                        <button  class="btn btn-dark btn-md mr-2">
                            Login&nbsp;
                            <i class="fa fa-sign-in-alt"></i>
                        </button>

                    </div>



                </form>

            </div>

        </div>

        <!--JQuery.js -->
        <script src="js/jquery.min.js"></script>
        <!--Popper.js via cdn -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha512-Ua/7Woz9L5O0cwB/aYexmgoaD7lw3dWe9FvXejVdgqu71gRog3oJgjSWQR55fwWx+WKuk8cl7UwA1RS6QCadFA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!-- Bootstrap.js -->
        <script src="js/bootstrap.min.js"></script>

    </body>
</html>
