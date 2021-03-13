<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>

<t:plantilla>
    <jsp:body>
    	 <h4>Inscripción a Programa de Formación</h4>
         <hr />
         <br />
		<div class="form-group row">
           	<div class="mx-auto">
           		<span id="error" class="error text-danger">${error}</span>
			</div>
		</div> 
		<form class="form-horizontal" action="InscripcionProgramaFormacion" method="post" autocomplete="off">
		   <div class="form-group row">
             <label class="control-label col-sm-2" for="curso">Programa:</label>
             <div class="col-sm-10">
                <select class="form-control" name="programa" id="programa">
            		<option value="" ${programa == '' ? 'selected' : ''}>(Seleccione un programa)</option>
				<c:forEach items="${programas}" var="prog" >
	            	<option value="${fn:escapeXml(prog)}" ${programa == prog ? 'selected' : ''}>${prog}</option>
				</c:forEach>
                </select>
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