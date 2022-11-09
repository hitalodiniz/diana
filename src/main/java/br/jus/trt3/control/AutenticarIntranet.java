package br.jus.trt3.control;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.jus.trt3.model.Pagina;
import br.jus.trt3.model.Parametro;

public class AutenticarIntranet {

	private static Boolean isUsuarioAutenticado = Boolean.FALSE;
		
	public static final Boolean getIsUsuarioAutenticado() {
		return isUsuarioAutenticado;
	}

	public static final void setIsUsuarioAutenticado(Boolean isUsuarioAutenticado) {
		AutenticarIntranet.isUsuarioAutenticado = isUsuarioAutenticado;
	}

	public static Boolean verificarAutenticarIntranet(WebDriver driver, Pagina pagina) throws IOException {		
		driver.get(pagina.getUrl());
		
		if ((driver.getCurrentUrl().contains(Parametro.URL_LOGIN_INTRANET) || driver.getCurrentUrl().contains(Parametro.URL_INTRANET)) && !isUsuarioAutenticado){
			isUsuarioAutenticado = acessarIntranet(driver, pagina);
			pagina.setIsExigeUsuarioIntranet(Boolean.TRUE);
		}
		return isUsuarioAutenticado;
				
	}

	private static Boolean acessarIntranet(WebDriver driver, Pagina pagina) throws IOException {
			driver.get(Parametro.URL_LOGIN_INTRANET.concat("?came_from=").concat(pagina.getUrl()));
			WebElement campoUsuario = driver.findElement(By.id("__ac_name"));
			WebElement campoSenha = driver.findElement(By.id("__ac_password"));
			campoUsuario.clear();
			campoUsuario.sendKeys(Parametro.USER_INTRANET);
			campoSenha.clear();
			campoSenha.sendKeys(Parametro.PASS_INTRANET);
			campoSenha.submit();
			return Boolean.TRUE;
	}
}