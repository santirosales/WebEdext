package webEdEXT;

import java.util.ArrayList;
import java.util.List;

import edEXT.server.DtCursoEdicionComentario;
import edEXT.server.DtCursoPuntaje;

public class DtEdicionComentarioMostrar {
	private DtCursoEdicionComentario comentario;
	private List<DtEdicionComentarioMostrar> respuestas = new ArrayList<DtEdicionComentarioMostrar>();
	private DtCursoPuntaje puntaje;

	public DtCursoEdicionComentario getComentario() {
		return comentario;
	}

	public void setComentario(DtCursoEdicionComentario comentario) {
		this.comentario = comentario;
	}

	public DtCursoPuntaje getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(DtCursoPuntaje puntaje) {
		this.puntaje = puntaje;
	}

	public List<DtEdicionComentarioMostrar> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(List<DtEdicionComentarioMostrar> respuestas) {
		this.respuestas = respuestas;
	}
	
}
