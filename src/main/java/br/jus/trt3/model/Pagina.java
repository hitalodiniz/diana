package br.jus.trt3.model;

import java.util.ArrayList;
import java.util.List;

public class Pagina {

	private Integer id;
	private String url;
	private String titulo;
	private String local = "";
	private String responsavelConteudo;
	private String codigoFonte;
	private Integer alturaBarraRolagem = 0;
	private Boolean isExigeUsuarioIntranet = Boolean.FALSE;
	private Boolean isTagConteudo = Boolean.FALSE;
	private Boolean isForm = Boolean.FALSE;
	private List<String> fomList = new ArrayList<>();
	private List<Link> linkList = new ArrayList<>();
	private List<Img> imgList = new ArrayList<>();

	public Pagina(String url, int i) {
		super();
		this.id = i;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
		
	public String getResponsavelConteudo() {
		return responsavelConteudo;
	}

	public void setResponsavelConteudo(String responsavelConteudo) {
		this.responsavelConteudo = responsavelConteudo;
	}


	public void setCodigoFonte(String codigoFonte) {
		this.codigoFonte = codigoFonte;
	}

	
	public Integer getAlturaBarraRolagem() {
		return alturaBarraRolagem;
	}

	public void setAlturaBarraRolagem(Integer alturaBarraRolagem) {
		this.alturaBarraRolagem = alturaBarraRolagem;
	}

	public final String getCodigoFonte() {
		return codigoFonte;
	}

	public Boolean getIsForm() {
		return isForm;
	}

	public void setIsForm(Boolean isForm) {
		this.isForm = isForm;
	}

	
	
	public final Boolean getIsExigeUsuarioIntranet() {
		return isExigeUsuarioIntranet;
	}

	public final void setIsExigeUsuarioIntranet(Boolean isExigeUsuarioIntranet) {
		this.isExigeUsuarioIntranet = isExigeUsuarioIntranet;
	}

	public List<Link> getLinkList() {
		return linkList;
	}

	public void setLinkList(List<Link> linkList) {
		this.linkList = linkList;
	}

	public final List<Img> getImgList() {
		return imgList;
	}

	public final void setImgList(List<Img> imgList) {
		this.imgList = imgList;
	}

	public final List<String> getFomList() {
		return fomList;
	}

	public final void setFomList(List<String> fomList) {
		this.fomList = fomList;
	}

	public Boolean getIsTagConteudo() {
		return isTagConteudo;
	}

	public void setIsTagConteudo(Boolean isTagConteudo) {
		this.isTagConteudo = isTagConteudo;
	}



	@Override
	public String toString() {
		return "Pagina [id=" + id + ", url=" + url + ", titulo=" + titulo + ", local=" + local
				+ ", responsavelConteudo=" + responsavelConteudo + ", isExigeUsuarioIntranet=" + isExigeUsuarioIntranet + ", isTagConteudo=" + isTagConteudo
				+ ", isForm=" + isForm + "]";
	}

	public static String[] headerCSV() {
		String[] objArray = {"ID PAGINA", "URL", "TITULO", "LOCAL", "ALTURA ROLAGEM PIXEL", "EXISTE TAG CONTEUDO", "RESPONSAVEL CONTEUDO", "EXISTE FORM", "EXIGIU AUTENTICAÇÃO INTRANET"};
		return objArray;
	}
	
	public String[] toCSV() {
		String[] objArray = {id.toString(), url, titulo, local, Integer.toString(alturaBarraRolagem), isTagConteudo.toString(), responsavelConteudo, isForm.toString(), isExigeUsuarioIntranet.toString()};
		return objArray;
	}

}
