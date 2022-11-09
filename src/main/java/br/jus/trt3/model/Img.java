package br.jus.trt3.model;

public class Img {

	private Pagina pagina;
	private String src;
	private String alt;
	

	public Img() {
		// TODO Auto-generated constructor stub
	}
	
	public Img(Pagina pagina) {
		this.pagina = pagina;
	}

	public Pagina getPagina() {
		return pagina;
	}

	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}


	
	public final String getSrc() {
		return src;
	}

	public final void setSrc(String src) {
		this.src = src;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}
	
	@Override
	public String toString() {
		return "Img [pagina=" + pagina + ", src=" + src + ", alt=" + alt + "]";
	}

	public static String[] headerCSV() {
		String[] objArray = {"ID PAGINA", "SRC", "ALT"};
		return objArray;
	}		
	
	public String[] toCSV() {
		String[] objArray = {pagina.getId().toString(), src, alt};
		return objArray;
	}

	


	
	
	
	

}
