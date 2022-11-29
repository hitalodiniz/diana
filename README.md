# diana
Robô DIANA - Diagnóstico do Nível de Acessibilidade de portais eletrônicos governamentais
breve roteiro para configuração e execução do robô DIANA e a última versão do código-fonte em anexo.

Quando desenvolvi o robô para o trabalho de auditoria de acessibilidade digital gravei um vídeo explicando o seu funcionamento. O vídeo está disponível em: https://www.youtube.com/watch?v=hWxMjY2s3Bg

É necessário a seguinte configuração de ambiente para a execução do robô:
Instalar e configurar a JDK 17;
Instalar e configurar o Maven 3.8.6;
Instalar o Chrome, última versão;
Executar o Eclipse IDE e importar o projeto anexado utilizando a opção Maven -> Existing Maven Projects.
Instruções para a execução do código-fonte:
Incluir as URLs a serem avaliadas no arquivo "urls.csv" no diretório raiz do projeto, esse arquivo já está incluído junto ao código-fonte;
Parametrizar a execução do robô por meio da classe "Parametro.java";
Realizar o build do código-fonte utilizando a opção Run para baixar as dependências do projeto

Executar o método " TesteCodigo.testeCodigo(Boolean.TRUE); " da classe "Principal.java" para iniciar o testes das URLs na ferramenta ASES;

Ao fim da execução o resultado da avaliação realizada pelo ASES será gravado no arquivo "RESULTADO_ASES-TRT.csv".

As demais funcionalidades do robô que testam elementos recomendados pelo eMAG estão disponíveis na classe "eMag.java". O uso dessa funcionalidade requer adaptação ao conteúdo do site, como por exemplo, deve-se configurar o ID que contém o conteúdo das páginas.

A classe "AutenticarIntranet.java" está configurada para logar na intranet do TRT-MG para que as páginas que exigem autenticação possam ser testadas no ASES por meio do seu código-fonte. Para uso por outro órgão é necessário configurar os IDs dos campos do formulário de autenticação da sua intranet no seguinte trecho de código em negrito:

WebElement campoUsuario = driver.findElement(By.id("__ac_name"));
WebElement campoSenha = driver.findElement(By.id("__ac_password"));

Além desta configuração dos IDs dos campos do formulário de autenticação, é necessário configurar na classe "Parametro.java" os seguintes parâmetros em negrito:

public static final String USER_INTRANET = "xxxx";
public static final String PASS_INTRANET = "xxxxx";

public static final String URL_LOGIN_INTRANET = "https://portal.trt3.jus.br/acl_users/credentials_cookie_auth/require_login";
public static final String URL_INTRANET = "https://portal.trt3.jus.br/intranet";


De forma resumida, essas são as configurações básicas para a execução do robô.
