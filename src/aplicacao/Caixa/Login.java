package aplicacao.Caixa;

import java.io.Serializable;
import java.rmi.RemoteException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import aplicacao.BancoConnection;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;
import aplicacao.Caixa.BancoDeDados.UserDatabase;

public class Login implements Serializable {

	transient private static BooleanProperty login;
	static {
		login = new SimpleBooleanProperty();
		login.setValue(false);

	}

	/**
	 * Faz login de administrador para alterar o Banco de dados
	 * 
	 * @param User
	 * @param password
	 * @return
	 */
	public static boolean getLogin(String User, String password,
			UserDatabase userDatabase) {
		try {
			BancoDeDados banco = BancoConnection.getConnection();
			login.setValue(banco.getLogin(User, password, userDatabase));

		} catch (DatabaseException | RemoteException e) {
			e.printStackTrace();
		}

		return login.getValue();
	}

	
	public static BooleanProperty getLogin() {
		return login;
	}

	public static void setLogin(Boolean login) {
		Login.login.setValue(login);
	}

}
