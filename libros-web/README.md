O Libros é um sistema de gerenciamento de bibliotecas construído com as seguintes tecnologias: J2EE, JSF, JAX-RS, JPA (Hibernate) e PrimeFaces.

Para executar esse projeto, é preciso ter um servidor de aplicação e um banco de dados. Os passos a seguir o ajudarão a configurar o servidor de aplicação Wildfly 11.0.0.Beta1 e o banco de dados H2 Database Engine.

Obs.: o servidor de aplicação Wildfly 11.0.0.Beta1 foi utilizado porque a versão 10.1.0.Final possui um bug relacionado com injeção CDI em EntityListeners (https://issues.jboss.org/browse/WFLY-2387).

Obs.: para ter acesso ao recurso de filas JMS do Wildfly, vamos utilizar o perfil 'standalone-full.xml' ao invés do padrão 'standalone.xml'. Esse perfil já disponibiliza uma fila, que será utilizada pelo Libros.

Para integrar o Wildfly com uma base de dados H2, é preciso criar um DataSource no arquivo '<WILDFLY_HOME_DIR>/standalone/configuration/standalone-full.xml' ou disponibilizar a configuração dos DataSources em um arquivo 'ds.xml' dentro do diretório WEB-INF da aplicação. O Libros utiliza essa segunda opção, configurando os DataSources no arquivo 'src/main/webapp/WEB-INF/libros-ds.xml'. Portanto, não há configuração a ser realizada em relação aos DataSources. Porém, se quiser acessar alguma das bases de dados através um cliente externo (como o Eclipse Data Tools Platform (DTP), por exemplo), será necessário iniciar o H2 Database Engine em modo servidor e alterar a URL de conexão do respectivo DataSource no arquivo 'libros-ds.xml' para a alternativa que está comentada e que faz referência ao protocolo TCP ('jdbc:h2:tcp://localhost:9092/~/LibrosH2DB/LibrosDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE', por exemplo).
 
A configuração a seguir deve ser feita no arquivo 'standalone-full.xml' do servidor de aplicação e diz respeito à criação de um Security Domain. O Security Domain é utilizado para definir como um usuário se autentica e recebe autorização para acessar os recursos da aplicação. Procure a tag '<security-domains>' e adicione o conteúdo necessário:

            <security-domains>
                ...
                <security-domain name="MeuSecurityDomain" cache-type="default">
                    <authentication>
                        <login-module code="Database" flag="required">
                            <module-option name="dsJndiName" value="java:jboss/datasources/LibrosDS"/>
                            <module-option name="rolesQuery" value="SELECT role, 'Roles' FROM users WHERE username=?"/>
                            <module-option name="hashAlgorithm" value="MD5"/>
                            <module-option name="hashEncoding" value="hex"/>
                            <module-option name="principalsQuery" value="SELECT password from users WHERE username=?"/>
                        </login-module>
                    </authentication>
                    <authorization>
                        <policy-module code="Database" flag="required">
                            <module-option name="dsJndiName" value="java:jboss/datasources/LibrosDS"/>
                            <module-option name="rolesQuery" value="SELECT role, 'Roles' FROM users WHERE username=?"/>
                            <module-option name="hashAlgorithm" value="MD5"/>
                            <module-option name="hashEncoding" value="hex"/>
                            <module-option name="principalsQuery" value="SELECT password from users WHERE username=?"/>
                        </policy-module>
                    </authorization>
                </security-domain>
                ...
            </security-domains>
 
Obs.: note que o conteúdo acima faz referência ao DataSource 'LibrosDS', disponível no arquivo 'libros-ds.xml', conforme mencionado anteriormente. Além disso, o conteúdo também faz referência à tabela 'users', que será criada automaticamente pela aplicação quando esta for iniciada no servidor de aplicação.
 
Pronto! Digite 'mvn clean package javadoc:javadoc' a partir do diretório raiz do projeto para compilar, executar testes e empacotar a aplicação, gerando também javadocs. O pacote WAR será criado no diretório 'target'.

Uma vez realizado o deploy no servidor de aplicação, você poderá acessar a aplicação pelos seguintes endereços:

http://localhost:8080/libros (interface web da aplicação)

http://localhost:8080/libros/javadocs (javadocs do projeto)

http://localhost:8080/libros/api-docs (documentação das APIs REST da aplicação)

http://localhost:8080/libros/api/bibliotecas (API REST do módulo Biblioteca)

http://localhost:8080/libros/api/clientes (API REST do módulo Cliente)

http://localhost:8080/libros/api/livros (API REST do módulo Livro)

Obs.: o usuário 'admin' (com senha 'admin') e o usuário 'user' (com senha 'user') estão disponíveis e podem ser utilizados para logar na aplicação.

Obs.: a partir do diretório raiz do projeto, digite 'mvn test' para executar os testes unitários ou digite 'mvn package verify -P wildfly-managed' para executar os testes de integração.

Obs.: se for utilizar a IDE Eclipse para compilar e fazer deploy da aplicação, é preciso instalar o plugin 'm2e-apt' a fim de habilitar o recurso de Annotation Processing utilizado pela biblioteca MapStruct. O plugin pode ser instalado a partir do Eclipse Market. Depois de instalar o plugin, entre em 'Eclipse -> Preferências -> Maven -> Annotation Processing' e selecione a opção 'Automatically configure JDT APT'.

