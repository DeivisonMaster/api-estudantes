<h1> API de Gerenciamento de Estudantes </h1>
<p>Api desenvolvida com Spring boot</p>

<h2>Descrição</h2>
<p>A aplicação é uma API desenvolvida em Java com Spring Boot. Terá camadas de autenticação e autorização com Basic Authentication e JWT Token. Bean Validation fará as validações necessárias para manipulação dos recursos.
O Spring Security gerenciará as classes e configurações de segurança. Swagger será a camada de documentação da API. MySQL o provedor de base de dados.</p>

<h2>Objetivo</h2>
<p>
Desenvolver API que manipula estudantes com recursos de autenticação e autorização de acessos.
</p>
  
<h2>Estilo Arquitetural</h2>
<p>Action Based</p>

<h2>Padrão de Arquitetura</h2>
<p>MVC</p>

<h2>Testes</h2>
<p>Validação camada Endpoint (Controller)</p>
<p>Validação camada Repositorio</p>

<br/>

<h3>Configuração Gerais</h3>
*** Token com base no usuário admin com permissões de POST, DEL e PUT
Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTY4NTgwNDg1NX0.v_6XpXJtSfFpz-PDvS5q90HUcYjKfGIJQM8fpkKLhS3qpAhADiwkFPG3P4m_k3Nq4yELIq55CNegVmTUXX35hA

1. Efetuar requisição para:
POST http://<dominio-local>:8080/login 
Body:
{
    "usuario": "admin",
    "senha": "123"
}

2. Recuperar da resposta o Header Authorization com o valor to token gerado, ex:
"Authorization Bearer <hash-token>"

<br/><br/>

ACESSO SWAGGER DOC:
1. http://<dominio-local>:8080/swagger-ui.html
<br/>

Obs: Endpoints protegidos com bearer token
2 Utilizar token base acima. Caso expirado, gerar novo token POST /login 

  
<h2>Tecnologias Aplicadas</h2>
<p>Java 8</p>
<p>Apache Maven</p>
<p>Spring Boot</p>
<p>Spring IoC</p>
<p>Spring Security</p>
<p>JWT Token</p>
<p>Basic Authentication</p>
<p>Swagger</p>
<p>Bean Validation</p>
<p>Spring Data JPA</p>
<p>Devtools</p>
<p>Apache Tomcat</p>
<p>MySQL</p>
<p>IDE Spring Tool Suite</p>
<p>DataJpaTest</p>
<p>MockMvc</p>
<p>AssertJ</p>

