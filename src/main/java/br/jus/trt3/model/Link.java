package br.jus.trt3.model;

public class Link {

	private Pagina pagina;
	private String href;
	private String target;
	private String texto;
	private Boolean isImg = Boolean.FALSE;
	private Img img;
	private Boolean isArquivo = Boolean.FALSE;
	private String nomeArquivo;
	private String extArquivo;


	public Link() {
		// TODO Auto-generated constructor stub
	}

	public Link(Pagina pagina) {
		this.pagina = pagina;
	}

	public Pagina getPagina() {
		return pagina;
	}

	public void setPagina(Pagina pagina) {
		this.pagina = pagina;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Boolean getIsImg() {
		return isImg;
	}

	public void setIsImg(Boolean isImg) {
		this.isImg = isImg;
	}

	public final Img getImg() {
		return img;
	}

	public final void setImg(Img img) {
		this.img = img;
	}

	public final Boolean getIsArquivo() {
		return isArquivo;
	}

	public final void setIsArquivo(Boolean isArquivo) {
		this.isArquivo = isArquivo;
	}

	public final String getNomeArquivo() {
		return nomeArquivo;
	}

	public final void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	public final String getExtArquivo() {
		return extArquivo;
	}

	public final void setExtArquivo(String extArquivo) {
		this.extArquivo = extArquivo;
	}

	@Override
	public String toString() {
		return "Link [pagina=" + pagina.getId() + ", href=" + href + ", target=" + target 
				+ ", texto=" + texto + ", isImg=" + isImg + "]";
	}

	public static String[] headerCSV() {
		String[] objArray = { "ID PAGINA", "URL LINK", "TARGET LINK", "TEXTO LINK", "IMAGEM LINK", "SRC IMAGEM LINK",
				"ALT IMAGEM LINK", "LINK ARQUIVO", "NOME ARQUIVO", "EXTENSÃO ARQUIVO" };
		return objArray;
	}

	public String[] toCSV() {
		String[] objArray = {pagina.getId().toString(), href, target, texto, isImg.toString(), (isImg?img.getSrc():""),  (isImg?img.getAlt():""), isArquivo.toString(), nomeArquivo, extArquivo};
		return objArray;
	}

}
