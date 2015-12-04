package aplicacao.Caixa.BancoDeDados;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aplicacao.Properties;

public class ServidorRMI {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServidorRMI.class);
	private Registry registry;
	private BancoDeDados bancoDeDados;

	public boolean aniciarServidor() {

		try {
			bancoDeDados = new BancoDeDadosImpl();

			registry = LocateRegistry.createRegistry(Integer
					.parseInt(new Properties().getProperty("portaRMI")));

			registry.rebind("BancoDeDados", bancoDeDados);

		} catch (Exception e) {

			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public void closeServer() {

		try {

			System.out.println("Servidor fechado");
			UnicastRemoteObject.unexportObject(bancoDeDados, true);
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
