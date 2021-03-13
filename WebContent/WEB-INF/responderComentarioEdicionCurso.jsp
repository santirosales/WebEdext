<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Responder Comentario a ${edicion.getNombre()}</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="ResponderComentarioEdicionCurso" method="post">
		   <input type="hidden" name="edicion" id="edicion" value="${fn:escapeXml(edicion.getNombre())}">
		   <input type="hidden" name="padreId" id="padreId" value="${padreId}">
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="texto">Respuesta:</label>
             <div class="col-sm-10">
             	<input type="text" class="form-control" name="texto" id="texto" required value="${texto}">
             </div>
           </div>           
           <div class="form-group row">
             <div class="col-sm-offset-2 col-sm-10">
               <button type="submit" class="btn btn-info">Guardar</button>
             </div>
           </div>		   
         </form>         
    </jsp:body>
</t:plantilla>