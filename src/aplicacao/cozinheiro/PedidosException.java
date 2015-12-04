package aplicacao.cozinheiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aplicacao.Caixa.BancoDeDados.ServidorRMI;

public class PedidosException extends Exception {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServidorRMI.class);

	public PedidosException(String mensagem) {
		System.out.println(mensagem);
		LOGGER.error(mensagem);
	}

}
