<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:plantilla>
    <jsp:body>
		<div class="container">
		    <div class="row">
		        <div class="col-md-12">
		            <div class="error-template">
		                <h1>
		                    Oops!</h1>
		                <h2>
		                    Ha ocurrido un error...</h2>
		                <div class="error-details">
		                    <span id="error" class="error text-danger">${error}</span>
		                </div>
		                <div class="error-actions">
		                    <a href="${pageContext.request.contextPath}/Login" class="btn btn-primary btn-lg">
		                    	<i class="fas fa-home"></i>
		                        Volver al inicio</a>
                        </div>
		            </div>
		        </div>
		    </div>
		</div>
    </jsp:body>
</t:plantilla>