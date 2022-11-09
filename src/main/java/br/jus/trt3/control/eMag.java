package br.jus.trt3.control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import br.jus.trt3.model.Img;
import br.jus.trt3.model.Link;
import br.jus.trt3.model.Pagina;
import br.jus.trt3.model.Parametro;

public class eMag {

	private static WebDriver driver;
	// private static List<Pagina> resultados = new ArrayList<>();
	private static Pagina pagina;
	private static Link link;
	private static Img img;

	public static void testeUrl() throws IOException {
		driver = Parametro.obtemWebDriver();

		Reader reader = Files.newBufferedReader(Paths.get(Parametro.DIRETORIO_CSV + "urls.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();

		CSVWriter writerPagina = new CSVWriter(
				new FileWriter(Parametro.DIRETORIO_CSV + "dados_pagina.csv"));
		writerPagina.writeNext(Pagina.headerCSV());
		writerPagina.flush();

		CSVWriter writerLink = new CSVWriter(
				new FileWriter(Parametro.DIRETORIO_CSV + "dados_pagina_link.csv"));
		writerLink.writeNext(Link.headerCSV());
		writerLink.flush();

		CSVWriter writerImg = new CSVWriter(
				new FileWriter(Parametro.DIRETORIO_CSV + "dados_pagina_img.csv"));
		writerImg.writeNext(Img.headerCSV());
		writerImg.flush();

		List<String[]> urls = csvReader.readAll();
		int i = 0;
		for (String[] url : urls) {
			pagina = new Pagina(url[0], ++i);
			if (!AutenticarIntranet.getIsUsuarioAutenticado()) {
				AutenticarIntranet.verificarAutenticarIntranet(driver, pagina);
			}

			pagina = avaliacaoEMag(pagina);
			pagina = avaliaConteudoPlone(pagina);

			writerPagina.writeNext(pagina.toCSV());
			writerPagina.flush();
			// System.out.println(pagina);

			for (Link l : pagina.getLinkList()) {
				writerLink.writeNext(l.toCSV());
				writerLink.flush();
			}

			for (Img img : pagina.getImgList()) {
				writerImg.writeNext(img.toCSV());
				writerImg.flush();
			}

		}
		driver.quit();
	}

	private static Pagina avaliacaoEMag(Pagina pagina) {

		driver.get(pagina.getUrl());
		pagina.setTitulo(driver.getTitle());
		pagina.setCodigoFonte(driver.getPageSource());

		try {
			List<WebElement> forms = driver.findElements(By.tagName("form"));
			for (WebElement form : forms) {
				// desconsiderando os forms padrões do template da internet
				if (!(form.getAttribute("id").equals("trt3-menu-pessoal") || // login intranet
						form.getAttribute("id").equals("form-busca-site")
						|| form.getAttribute("id").equals("trt3-case-search"))) {
					pagina.setIsForm(Boolean.TRUE);
				}
			}

		} catch (WebDriverException e) {
			// TODO: handle exception
			pagina.setIsForm(Boolean.FALSE);
		}

		//

		return pagina;

	}

	private static Pagina avaliaConteudoPlone(Pagina pagina) {
		try

		{
			WebElement divLocal = (WebElement) driver.findElement(By.id("portal-breadcrumbs"));
			List<WebElement> elements = divLocal.findElements(By.tagName("span"));
			for (WebElement element : elements) {
				if (!StringUtils.isEmpty(element.getText())) {
					pagina.setLocal(pagina.getLocal() + element.getText() + " > ");
				}
			}

		} catch (WebDriverException e) {
			// TODO: handle exception
		}

		try {
			WebElement conteudo = (WebElement) driver.findElement(By.id("conteudo-corpo"));

			pagina.setAlturaBarraRolagem(conteudo.getSize().height);

			pagina.setIsTagConteudo(Boolean.TRUE);
			// pegando os links apenas do conteúdo
			try {
				List<WebElement> elementsConteudo = conteudo.findElements(By.tagName("a"));
				List<WebElement> elementsConteudoImg;
				for (WebElement element : elementsConteudo) {
					link = new Link(pagina);
					link.setHref(element.getAttribute("href"));
					link.setTarget(element.getAttribute("target"));
					link.setTexto(element.getText());
					verificarLinkArquivo(link);
					// verificando imagens no link
					elementsConteudoImg = element.findElements(By.tagName("img"));
					for (WebElement elementImg : elementsConteudoImg) {
						link.setIsImg(Boolean.TRUE);
						link.setImg(new Img());
						link.getImg().setAlt(elementImg.getAttribute("alt"));
						link.getImg().setSrc(elementImg.getAttribute("src"));						
					}

					pagina.getLinkList().add(link);
				}

			} catch (WebDriverException e) {
				// TODO: handle exception
				pagina.setIsForm(Boolean.FALSE);
			}

			// extraindo as imagens no conteúdo
			try {
				List<WebElement> elementsConteudoImg = conteudo.findElements(By.tagName("img"));
				for (WebElement elementImg : elementsConteudoImg) {
					img = new Img(pagina);
					img.setSrc(elementImg.getAttribute("src"));
					img.setAlt(elementImg.getAttribute("alt"));
					pagina.getImgList().add(img);
				}				

			} catch (WebDriverException e) {
				// TODO: handle exception
				pagina.setIsForm(Boolean.FALSE);
			}

			// extraindo o responsável pelo conteúdo
			try {
				WebElement responsavel = ((WebElement) driver.findElement(By.id("trt3-localrole-policy")))
						.findElement(By.tagName("span"));
				pagina.setResponsavelConteudo(responsavel.getText());
			} catch (WebDriverException e) {
				// TODO: handle exception
			}
		} catch (WebDriverException e) {
			// TODO: handle exception
			pagina.setIsTagConteudo(Boolean.FALSE);
		}
		return pagina;
	}

	public static void verificarLinkArquivo(Link link) {
		String[] aux;
		if (StringUtils.containsAny(link.getHref(), new String[] {".exe",".doc",".xls",".odf",".ods",".odt",".pdf",".txt",".rtf",".msi",".odg"})) {
			link.setIsArquivo(Boolean.TRUE);
			aux = link.getHref().split("/");
			link.setNomeArquivo(aux[aux.length - 1]);
    		link.setExtArquivo(StringUtils.substring(link.getNomeArquivo(), link.getNomeArquivo().length() - 4));
		}

	}
}
