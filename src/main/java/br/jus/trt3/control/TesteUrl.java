package br.jus.trt3.control;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import br.jus.trt3.model.Parametro;

public class TesteUrl {

	private static String[] resultados;
	private static WebDriver driver;

	public static void testeUrl() throws IOException {
		driver = Parametro.obtemWebDriver();

		Reader reader = Files.newBufferedReader(Paths.get(Parametro.DIRETORIO_CSV + "urls.csv"));
		CSVReader csvReader = new CSVReaderBuilder(reader).build();

		CSVWriter writer = new CSVWriter(new FileWriter(Parametro.DIRETORIO_CSV + "avaliacao_ases.csv"));

		List<String[]> urls = csvReader.readAll();
		for (String[] url : urls) {
			writer.writeNext(ases(url[0]));
			writer.flush();
		}
		driver.quit();
	}

	private static String[] ases(String url) {

		resultados = new String[4];
		String[] erros;

		driver.get(Parametro.URL_ASES);
		WebElement campoPesquisado = driver.findElement(By.name("url"));
		campoPesquisado.clear();
		campoPesquisado.sendKeys(url);

		campoPesquisado.submit();

		resultados[0] = url;

		WebElement elementTexto;

		List<WebElement> elements = driver.findElements(By.id("webaxscore"));
		for (WebElement element : elements) {

			elementTexto = element.findElement(By.tagName("span"));
			resultados[1] = elementTexto.getText();
		}

		elements = driver.findElements(By.id("total"));
		for (WebElement element : elements) {

			erros = element.getText().split(" ");

			resultados[2] = erros[1];
			resultados[3] = erros[2];
		}

		// System.out.println("Porcentagem ASES: " +
		// driver.findElement(By.id("webaxscore").));

		return resultados;

	}

}
