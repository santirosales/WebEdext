<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!-- barra lateral -->
        <nav id="sidebar" class="sidebar">
            <div class="sidebar-header">
                <h3 class="d-inline p-2">
                  EdEXT
               </h3>
               <div class="d-inline p-2">
                  <span class="fa-stack" style="vertical-align: top;">
                     <i class="far fa-circle fa-stack-2x"></i>
                     <i class="fas fa-graduation-cap fa-stack-1x"></i>
                  </span> 
               </div>
            </div>

			<ul class="list-unstyled components">
              	<c:if test="${not empty usuarioLogueado}">
				<li class="">
				    <a href="#miperfilSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle menu-link">Mi perfil</a>
				    <ul class="collapse list-unstyled ${seccion == 'perfil' ? 'show' : ''}" id="miperfilSubmenu">
				        <li>
				            <a class="link menu-link" href="${pageContext.request.contextPath}/SeleccionarUsuario">Consulta de Usuarios</a>
				        </li>
				        <li>
				            <a class="link menu-link" href="${pageContext.request.contextPath}/ModificarUsuario">Modificar Usuario</a>
				        </li>
				    </ul>
				</li>
           		</c:if>
               	<c:if test="${empty usuarioLogueado}">
                <li>
                    <a href="#consultasSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle menu-link">Consultas</a>
                    <ul class="collapse list-unstyled ${seccion == 'consultas' ? 'show' : ''}" id="consultasSubmenu">
                        <li>
                            <a class="link menu-link" href="${pageContext.request.contextPath}/SeleccionarUsuario">Consulta de usuarios</a>
                        </li>
                    </ul>
                </li>
           		</c:if>
            <c:if test="${usuarioLogueado.getTipo() == 'Estudiante'}">    
                <li class="">
                    <a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle menu-link">Inscripciones</a>
                    <ul class="collapse list-unstyled ${seccion == 'inscripciones' ? 'show' : ''}" id="homeSubmenu">
                        <li>
                            <a class="link menu-link" href="InscripcionEdicionCurso">Inscribirme a un Curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="ListarResultados">Resultados inscripciones Cursos</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="ListarEvaluaciones">Resultados evaluaciones Cursos</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="ListarDesistirInscripcionEdicionCurso">Desistir inscripción Curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="InscripcionProgramaFormacion">Inscribirme a un Programa</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="ListarTramitarCertificadoProgramaFormacion">Tramitar Certif. Programa</a>
                        </li>
                    </ul>
                </li>
            </c:if>                     
                <li>
                    <a href="#cursosSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle menu-link">Cursos</a>
                    <ul class="collapse list-unstyled ${seccion == 'cursos' ? 'show' : ''}" id="cursosSubmenu">
            <c:if test="${usuarioLogueado.getTipo() == 'Docente'}">
                        <li>
                            <a class="link menu-link" href="AltaCurso">Alta curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="AltaEdicionCurso">Alta edición</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="CrearProgramaFormacion">Alta programa</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="AgregarCursoProgramaFormacion">Agregar curso a programa</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="SeleccionarEstudiantesEdicionCurso">Seleccionar estudiantes edición curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="ListarAceptadosEdicionCurso">Listar aceptados edición curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="IngresarResultadosEdicionCurso">Ingresar resultados edición curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="VerResultadosEdicionCurso">Ver resultados edición curso</a>
                        </li>
            </c:if>
            <c:if test="${usuarioLogueado.getTipo() == 'Estudiante'}">                  
                        <li>
                            <a class="link menu-link" href="ListarEdicionesCursosComentar">Comentar Edición Curso</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="ListarValorarCurso">Valorar Curso</a>
                        </li>
            </c:if>
                        <li>
                            <a class="link menu-link" href="ListarCursosTendencias">Tendencias</a>
                        </li>
                        <li>
                            <a class="link menu-link" href="SeleccionarProgramaFormacion">Consulta programa</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="#institutosSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle menu-link">Institutos</a>
                    <ul class="collapse list-unstyled ${seccion == 'institutos' ? 'show' : ''}" id="institutosSubmenu">
						<jsp:include page="${''}/ListarInstitutosSidebar"/>
                    </ul>
                </li>
                <li>
                    <a href="#categoriasSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle menu-link">Categorías</a>
                    <ul class="collapse list-unstyled ${seccion == 'categorias' ? 'show' : ''}" id="categoriasSubmenu">
						<jsp:include page="${''}/ListarCategoriasSidebar"/>
                    </ul>
                </li>
             	<c:if test="${not empty usuarioLogueado}">
                <li>
                    <a class="menu-link" href="${pageContext.request.contextPath}/Salir">Salir</a>
                </li>
          		</c:if>                
            </ul>
        </nav>
