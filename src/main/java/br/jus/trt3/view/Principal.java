package br.jus.trt3.view;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import br.jus.trt3.control.TesteCodigo;
import br.jus.trt3.control.eMag;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Principal {
	

	public static void main(String[] args) throws IOException {
		//analisando os elementos da página
//		eMag.testeUrl();
		
		//teste no ASES
		TesteCodigo.testeCodigo(Boolean.TRUE);
		// TesteUrl.testeUrl();
		

	}

}
