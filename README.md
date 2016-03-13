Aluno: Danilo Augusto Pinotti de Mello
RA: 1573420

---------------------------------------------------------------------

O sistema foi desenvolvido pensando em uma pequena empresa que trabalha com produtos a granel e que também empacotam/ensacam os mesmos.

---------------------------------------------------------------------

Pré requisitos:
	Java 7 ou superior
	MySQL client

---------------------------------------------------------------------

Instalação do Banco de dados:
	Abra o terminal do linux na pasta em que o projeto foi extraído e execute o seguinte comando.
			$ mysql -u usuario -p < db_projeto_integrador_LOO.sql
	
			Altere "usuario" pelo nome de usuário do banco de dados

--------------------------------------------------------------------

Configuração no projeto
	Abra com um editor de texto o seguinte arquivo:
		./source/src/model/mysql/MySQLMainDatabase.java

	Edite as seguintes linhas de acordo com sua necessidade:
		private static final String URL = "jdbc:mysql://127.0.0.1/";	//Coloque o IP do servidor MySQL
		private static final String USER = "root";			//Coloque o nome do usuário do banco de dados
		private static final String PASSWORD = "root";			//Coloque a senha do usuário do banco de dados
	
	Salve o arquivo.

--------------------------------------------------------------------

Após ter feito as descritas configuraçõe execute o projeto.
	Usuário padrão: admin@admin
	Senha padrão: abc123

Divirta-se.

-------------------------------------------------------------------

Endereço Git:
	https://github.com/danilopinotti/interdisciplinary-project-4th-period.git