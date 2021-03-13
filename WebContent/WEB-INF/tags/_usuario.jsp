<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!-- menu del usuario -->
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div class="container-fluid">
                    <button type="button" id="sidebarCollapse" class="navbar-btn">
                        <span></span>
                        <span></span>
                        <span></span>
                    </button>
		            <!-- parte derecha -->
                    <div class="" id="menuUsuario">
                        <div class="ml-auto float-right">
						<c:choose>
	  						<c:when test="${empty usuarioLogueado}">
	                            <div class="d-inline-block">
	                                <a class="nav-link" href="${pageContext.request.contextPath}/Login">Iniciar sesión</a>
	                            </div>
	                            <div class="d-inline-block">
	                                <a class="nav-link" href="${pageContext.request.contextPath}/AltaUsuario">Registrarse</a>
	                            </div>
	  						</c:when>
	  						<c:when test="${not empty usuarioLogueado}">
	                            <div class="d-inline-block">
	                                <a href="ConsultaUsuario?nickname=${usuarioLogueado.getNickname()}">${usuarioLogueado.getNickname()}</a>
	                            </div>
	                            <div class="d-inline-block">
	                                <img src="${usuarioLogueado.getUrlFoto()}" class="rounded" alt="" height="64">
	                            </div>		               	
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
                        </div>
                    </div>
                </div>
            </nav>

