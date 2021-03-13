<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
				<c:forEach items="${categorias}" var="categoria" >
                        <li>
                            <a class="link menu-link" href="SeleccionarCursoCategoria?nombre=${fn:escapeXml(categoria)}">${categoria}</a>
                        </li>
				</c:forEach>
