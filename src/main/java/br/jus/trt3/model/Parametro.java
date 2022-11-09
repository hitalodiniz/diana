package br.jus.trt3.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Parametro {


	public static final String DIRETORIO_CSV = "";

	
	public static final String USER_INTRANET = "xxxx";
	public static final String PASS_INTRANET = "xxxxx";
	
	public static final String URL_LOGIN_INTRANET = "https://portal.trt3.jus.br/acl_users/credentials_cookie_auth/require_login";
	public static final String URL_INTRANET = "https://portal.trt3.jus.br/intranet";	
	
	public static final String URL_ASES = "https://asesweb.governoeletronico.gov.br";
	
	public static final String SITE_AVALIADO = "TRT12";
	
	
    public static WebDriver obtemWebDriver(){
		WebDriver driver = null;
		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized"); 
		options.addArguments("enable-automation"); 
		options.addArguments("--no-sandbox"); 
		options.addArguments("--disable-infobars");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-browser-side-navigation"); 
		options.addArguments("--disable-gpu"); 
		driver = new ChromeDriver(options); 
		
		return driver;
    }
}
