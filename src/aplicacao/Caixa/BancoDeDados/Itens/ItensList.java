package aplicacao.Caixa.BancoDeDados.Itens;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import aplicacao.BancoConnection;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;

public class ItensList implements Serializable {

	private List<Item> list = new ArrayList<Item>();

	public ItensList(Categoria categoria) {

		list = getItens(categoria);

	}

	/**
	 */
	public void salvarAlteracoes() {

		try {
			BancoDeDados banco = new BancoConnection().getConnection();

			// Se o ID do item for maior que 0 é salvo no banco de dados
			for (Item item : list) {
				item.writeTransitions();

				if (item.getID() != 1404) {
					// Lê as properties e grava nos tipos seriallizaveis, depois
					// faz a requisição para salvar no banco de dados
					banco.alterarItem(item);
				} else {
					// Caso contrário é excluido no Banco De Dados

					banco.excluirItem(item);
				}

			}
		} catch (DatabaseException | RemoteException e) {
			e.printStackTrace();
		}
	}

	public void getItensList(VBox vbox) {

		for (Item item : list) {
			GridPane mesasPane = new GridPane();
			TextField nome = new TextField();
			nome.textProperty().bindBidirectional(item.nomeProperty());

			TextField lblPreco = new TextField();
			lblPreco.textProperty().bindBidirectional(item.precoProperty());

			TextArea txtArea = new TextArea();

			txtArea.setMinSize(300, 100);
			txtArea.setMaxSize(300, 100);
			txtArea.textProperty().bindBidirectional(item.descricaoProperty());
			Button btnPago = new Button("Remover");
			btnPago.setOnAction(e -> {
				item.writeTransitions();
				vbox.getChildren().remove(mesasPane);
				item.setID(1404);// Atribui o valor 0 ao id do item para ser
									// excluido no banco de dados
			});

			mesasPane.setMinSize(400, 250);
			mesasPane.add(nome, 0, 0);
			mesasPane.add(txtArea, 0, 1);
			mesasPane.add(btnPago, 1, 2);
			mesasPane.add(lblPreco, 0, 2);
			vbox.getChildren().add(mesasPane);
		}

	}

	private List<Item> getItens(Categoria categoria) {

		try {
			BancoDeDados database = BancoConnection.getConnection();
			List<Item> list = database.getItens(categoria);
			// Le os dados e grava nos properties
			list.forEach(item -> item.readTransitions());

			return list;

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

}
