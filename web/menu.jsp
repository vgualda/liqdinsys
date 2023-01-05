<%@page language="java" contentType="text/html; utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="controller.GerenciarLogin" %>
<%@page import="model.Usuario" %>

<%
    Usuario ulogado
            = GerenciarLogin.verificarAcesso(request, response);
    request.setAttribute("ulogado", ulogado);

%> 


    <c:choose>
        <c:when test="${ulogado != null}">
            <div class="col justify-content-sm-end mt-4">
                <h6 class="mt-1 badge"><b>Bem-vindo, ${ulogado.nome}</b></h6>
                <a href="gerenciarLogin?"
                   class="btn-dark btn-sm" role="button">
                    Sair&nbsp;<i class="fa-solid fa-user-lock"></i>
                </a> 
            </div>
        </c:when>
        <c:otherwise>
            <% response.sendRedirect("formLogin.jsp");%>
            <div class="col justify-content-sm-end mt-4">
                <a href="formLogin.jsp"
                   class="btn-dark btn" role="button">
                    Login&nbsp;<i class="fa-solid fa-user-unlock"></i>
                </a>
            </div>
        </c:otherwise>
    </c:choose>





<header class="mt-sm-0">
    <nav class="navbar navbar-expand-sm">
        <button class="navbar-toggler bg-dark" type="button" data-toggle="collapse" 
                data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="fas fa-droplet text-info" ></span>
        </button>

        <div class="collapse navbar-collapse bg-dark mr-sm-auto" 
             id="navbarSupportedContent">
            <ul class="navbar-nav ">
                <c:if test="${ulogado != null && ulogado.perfil != null}">
                    <c:forEach var="menu" items="${ulogado.perfil.menus}">
                        <c:if test="${menu.exibir == 1}">
                            <li class="nav-item" style="text-shadow: 2px 2px #484d50">
                                <a class="nav-link text-light" href="${menu.link}">${menu.nome}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </c:if>

            </ul>

        </div>
    </nav>

</header>