package aplicacao.Caixa.BancoDeDados;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDatabase implements Serializable {

	transient private static StringProperty nome = new SimpleStringProperty();
	transient private static BooleanProperty admin = new SimpleBooleanProperty();
	private String nomeTransition;
	private boolean adminTransition;

	static {
		admin.setValue(false);
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public String getNome() {
		return nomeTransition;
	}

	public void setNome(String nome) {

		this.nome = new SimpleStringProperty();
		this.nomeTransition = nome;
		this.nome.setValue(nome);
	}

	public BooleanProperty adminProperty() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = new SimpleBooleanProperty();
		this.adminTransition = admin;
		this.admin.setValue(admin);
	}

	public boolean isAdmin() {
		return adminTransition;
	}

	public UserDatabase() {

	}

	public void clear() {

		nome.setValue("");
		admin.setValue(false);
	}

	public static UserForList newUserForList() {
		return new UserForList();
	}

	public static class UserForList {

		private UserForList() {
			// TODO Auto-generated constructor stub
		}

		private StringProperty name = new SimpleStringProperty();
		private StringProperty senha = new SimpleStringProperty();
		private StringProperty permissao = new SimpleStringProperty();

		public StringProperty nameProperty() {
			return name;
		}

		public String getName() {
			return name.getValue();
		}

		public void setName(String name) {
			this.name.setValue(name);
		}

		public StringProperty senhaProperty() {
			return this.senha;
		}

		public String getSenha() {
			return this.senhaProperty().get();
		}

		public void setSenha(String senha) {
			this.senhaProperty().setValue(senha);
		}

		public StringProperty permissaoProperty() {
			return this.permissao;
		}

		public String getPermissao() {
			return this.permissaoProperty().getValue();
		}

		public void setPermissao(String permissao) {
			this.permissaoProperty().setValue(permissao);
		}

	}
}
