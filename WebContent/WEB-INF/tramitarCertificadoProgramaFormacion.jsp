<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Tramitar Certificado ${programa}</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div>
		<c:if test="${empty error}">
			<div class="form-group row">
	           	<div class="mx-auto">
	           		<div id="mensaje" class="d-flex justify-content-center">Se ha generado la aprobación del programa.</div>
			        <div class="d-flex justify-content-center"><a class="link" href="GenerarPdf?programa=${fn:escapeXml(programa)}">Click aquí para descargar el certificado</a></div>
				</div>
			</div> 
		</c:if>
    </jsp:body>
</t:plantilla>