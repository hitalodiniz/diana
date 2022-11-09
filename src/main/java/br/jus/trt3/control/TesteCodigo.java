package br.jus.trt3.control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import br.jus.trt3.model.Pagina;
import br.jus.trt3.model.Parametro;

public class TesteCodigo {

	private static Pagina pagina;
	private static String[] resultados;
	private static WebDriver driver;
	private static WebDriver driverCodigoFonte;
	private static File arquivoCodigo;
	private static FileWriter fwArquivoCodigo;
	private static WebElement campoPesquisado;
	private static WebElement btExecutar;
	private static WebElement elementTexto;

	private static String[] headerCSV = { "TITULO", "URL", "NIVEL_ACESS", "TOTAL_ERRO", "TOTAL_AVISO", "ERRO_MARCACAO",
			"AVISO_MARCACAO", "ERRO_COMPORTAMENTO", "AVISO_COMPORTAMENTO", "ERRO_CONT_INFO", "AVISO_CONT_INFO",
			"ERRO_APRESENT", "AVISO_APRESENT", "ERRO_MULTI", "AVISO_MULTI", "ERRO_FORM", "AVISO_FORM" };
	private static String[] listaClassesCssResultado = { "mark_error", "mark_warning", "behavior_error",
			"behavior_warning", "information_error", "information_warning", "presentation_error",
			"presentation_warning", "multimedia_error", "multimedia_warning", "form_error", "form_warning" };

	/***
	 * 
	 * @param isTestarUrl Quando TRUE submete a página ao ASES pela URL, quando
	 *                    falso submete pelo código-fonte
	 * @throws IOException
	 */
	public static void testeCodigo(boolean isTestarUrl) throws IOException {

		Reader reader = Files.newBufferedReader(Paths.get(Parametro.DIRETORIO_CSV + "urls.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();
		List<String[]> urls = csvReader.readAll();
		System.out.println("Iniciando a avaliação de " + urls.size() + " páginas web.");

		String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		StringBuffer nomeArquivoNivelAcess = new StringBuffer(Parametro.DIRETORIO_CSV);
		nomeArquivoNivelAcess.append("RESULTADO_ASES");
		nomeArquivoNivelAcess.append("-");
		nomeArquivoNivelAcess.append(Parametro.SITE_AVALIADO);
		nomeArquivoNivelAcess.append("-");
		nomeArquivoNivelAcess.append(data);
		nomeArquivoNivelAcess.append(".csv");

		CSVWriter writer = new CSVWriter(new FileWriter(nomeArquivoNivelAcess.toString()));
		writer.writeNext(headerCSV);

		driver = Parametro.obtemWebDriver();
		driverCodigoFonte = Parametro.obtemWebDriver();

		int i = 0;
		for (String[] url : urls) {
			try {
				pagina = new Pagina(url[0], ++i);
				if (pagina.getUrl().contains("intranet") && !AutenticarIntranet.getIsUsuarioAutenticado()) {
					AutenticarIntranet.verificarAutenticarIntranet(driverCodigoFonte, pagina);
				}
				writer.writeNext(ases(pagina, isTestarUrl));
				writer.flush();
			} catch (Exception e) {
				System.out.println("Erro ao avaliar a página: " + url[0] + "\n Erro:" + e.getMessage());
				System.out.println(e.fillInStackTrace());
			}
		}

		driverCodigoFonte.quit();
		driver.quit();
	}

	/****
	 * 
	 * @param pagina
	 * @param isTestarUrl Quando TRUE submete a página ao ASES pela URL, quando
	 *                    falso submete pelo código-fonte
	 * @return
	 * @throws IOException
	 */
	private static String[] ases(Pagina pagina, boolean isTestarUrl) throws IOException {

		String[] erros;
		resultados = new String[18];

		driver.get(Parametro.URL_ASES);
		driver.manage().window().setPosition(new Point(0, 0));
		driver.manage().window().setSize(new Dimension(700, 800));

		// WebElement aba = driver.findElement(By.id("tab-3"));
		// aba.c;

		driverCodigoFonte.get(pagina.getUrl());
		driverCodigoFonte.manage().window().setPosition(new Point(700, 0));
		driverCodigoFonte.manage().window().setSize(new Dimension(700, 800));

		if (isTestarUrl) {
			WebElement campoPesquisado = driver.findElement(By.name("url"));
			campoPesquisado.clear();
			campoPesquisado.sendKeys(pagina.getUrl());
			campoPesquisado.submit();
		} else {
			String codigo = driverCodigoFonte.getPageSource();// .replaceAll("\n", "").replaceAll("\r",
																// "").replaceAll("<", "\n<");

			arquivoCodigo = new File(Parametro.DIRETORIO_CSV + "codigo.html");
			arquivoCodigo.setWritable(true);
			arquivoCodigo.setReadable(true);

			fwArquivoCodigo = new FileWriter(arquivoCodigo);
			fwArquivoCodigo.write(codigo);
			fwArquivoCodigo.flush();

			campoPesquisado = driver.findElement(By.id("up_file"));

//		System.out.println("Local " + arquivoCodigo.getCanonicalPath());

			campoPesquisado.sendKeys(arquivoCodigo.getCanonicalPath());

			btExecutar = driver.findElement(By.id("input_tab_2"));
			btExecutar.sendKeys(Keys.TAB);
			btExecutar.click();
		}
		// TITULO
		resultados[0] = driverCodigoFonte.getTitle();
		// URL
		resultados[1] = pagina.getUrl();

		WebElement elementTexto;

		// NIVEL_ACESS
		List<WebElement> elements = driver.findElements(By.id("webaxscore"));
		for (WebElement element : elements) {
			elementTexto = element.findElement(By.tagName("span"));
			resultados[2] = elementTexto.getText();
		}

		elements = driver.findElements(By.id("total"));
		for (WebElement element : elements) {
			erros = element.getText().split(" ");
			// TOTAL_ERRO
			resultados[3] = erros[1];
			// TOTAL_AVISO
			resultados[4] = erros[2];
		}

		int i = 0;
		while (i < 12) {
			resultados[i + 5] = "";
			try {
				// indiceAvaliar0 é a classe de erro
				// soma 5 no indice por causa da posição no CSV
				elements = driver.findElement(By.className(listaClassesCssResultado[i]))
						.findElements((By.className("indiceAvaliar0")));
				for (WebElement element : elements) {
					resultados[i + 5] = resultados[i + 5] + element.getText().trim() + "\n";
				}

			} catch (Exception e) {
				// Quando não apresenta a classe indiceAvaliar0 gera exceção
			}
			// System.out.println("i " + i + " - " + resultados[i + 5]);
			i++;
			resultados[i + 5] = "";
			try {
				// indiceAvaliar1 é a classe de aviso
				// soma 6 no indice por causa da posição no CSV
				elements = driver.findElement(By.className(listaClassesCssResultado[i]))
						.findElements((By.className("indiceAvaliar1")));
				for (WebElement element : elements) {
					if (!element.getText().trim().equals("Recomendação")) {
						resultados[i + 5] = resultados[i + 5] + element.getText().trim() + "\n";
					}
				}
			} catch (Exception e) {
				// Quando não apresenta a classe indiceAvaliar0 gera exceção
			}
			// System.out.println("i " + i + " - " + resultados[i + 5]);
			i++;
		}

		return resultados;

	}

}
