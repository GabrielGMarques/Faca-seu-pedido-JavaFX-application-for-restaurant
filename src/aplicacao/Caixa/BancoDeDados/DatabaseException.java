package aplicacao.Caixa.BancoDeDados;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseException extends Exception {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DatabaseException.class);

	public DatabaseException(String message) {
		//
		System.out.println(message);
		LOGGER.error(message);

	}
}
