package aplicacao.cozinheiro;

import java.rmi.RemoteException;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import aplicacao.BancoConnection;
import aplicacao.Caixa.Pedido;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;
import aplicacao.Caixa.BancoDeDados.Itens.Item;

public class PedidoPane {

	public static void getPedido(VBox vbox) {
		try {

			BancoDeDados banco = BancoConnection.getConnection();

			for (Pedido pedido : banco.getPedidos()) {
				GridPane pedidosPane = new GridPane();
				// TODO implementar id em pedido
				Label label = new Label("Pedido: " + pedido.getID());
				// TODO arrumar a tabela
				TableView<Item> table = new TableView<Item>();
				table.setMaxSize(450, 200);
				table.setMinSize(450, 200);
				TableColumn<Item, Integer> coUnid = new TableColumn<Item, Integer>(
						"unid");
				coUnid.setCellValueFactory(new PropertyValueFactory<>("unid"));

				TableColumn<Item, String> coNome = new TableColumn<Item, String>(
						"nome");
				coNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

				TableColumn<Item, String> coDescricao = new TableColumn<Item, String>(
						"descricao");
				coDescricao.setCellValueFactory(new PropertyValueFactory<>(
						"descricao"));

				table.getColumns().addAll(coUnid, coNome, coDescricao);
				pedido.getList().forEach(item -> item.readTransitions());
				table.setItems(FXCollections.observableArrayList(pedido
						.getList()));
				Button btnPPronto = new Button("Pronto");
				btnPPronto.setOnAction(e -> {
					vbox.getChildren().remove(pedidosPane);
					try {
						banco.excluirPedido(pedido.getID());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				pedidosPane.setMinSize(400, 250);
				pedidosPane.add(label, 0, 0);
				pedidosPane.add(table, 0, 1);
				pedidosPane.add(btnPPronto, 0, 2);
				vbox.getChildren().add(pedidosPane);
			}
		} catch (DatabaseException | RemoteException e) {
			e.printStackTrace();
		}
	}

}
