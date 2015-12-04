package aplicacao;

import java.rmi.Naming;

import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;

public class BancoConnection {

	/**
	 * 
	 * @return the connection to the database
	 * @throws DatabaseException
	 */
	public static BancoDeDados getConnection() throws DatabaseException {
		Properties properties = new Properties();

		BancoDeDados banco = null;
		try {
			banco = (BancoDeDados) Naming.lookup("rmi://"
					+ properties.getProperty("localRMI") + ":"
					+ properties.getProperty("portaRMI") + "/BancoDeDados");
		} catch (Exception e) {

			System.out.println(e.getMessage());

		}

		return banco;
	}
}
