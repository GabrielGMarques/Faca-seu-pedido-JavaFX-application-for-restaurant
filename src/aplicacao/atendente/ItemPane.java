package aplicacao.atendente;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import aplicacao.BancoConnection;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.Itens.Categoria;
import aplicacao.Caixa.BancoDeDados.Itens.Item;
import aplicacao.controllers.ControllerItensAtendimento;

public class ItemPane {

	/**
	 * Loads items from the database the first time, in the second carries on
	 * the items from the item list
	 * 
	 * @param vbox
	 * @param list
	 * @param controller
	 */

	public void getItens(Categoria categoria, VBox vbox, List<Item> list,
			TextField textPreco, ControllerItensAtendimento controller,
			boolean controll) {
		try {
			if (controll) {
				BancoDeDados banco = BancoConnection.getConnection();

				banco.getItens(categoria).forEach(item -> {
					item.readTransitions();// Reads the data of simple types and
											// add to the property type
						getItem(item, vbox, textPreco, controller);
						list.add(item);
					});
			} else {
				list.forEach(item -> {
					// Checks whether the selected type
					if (item.getCategoria().equals(categoria)) {
						getItem(item, vbox, textPreco, controller);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	/**
	 * Inserts the item to the vbox
	 * 
	 * @param item
	 * @param vbox
	 * @param textPreco
	 * @param controller
	 */
	private void getItem(Item item, VBox vbox, TextField textPreco,
			ControllerItensAtendimento controller) {

		GridPane grid = new GridPane();

		Label nome = new Label(item.getNome());// Name of item
		Label preco = new Label(String.valueOf(item.getPreco()));// Price of
																	// item

		TextArea area = new TextArea(item.getDescricao());// Description of item
		area.setDisable(true);
		area.setMaxSize(300, 100);

		VBox vbox2 = new VBox();
		vbox2.setSpacing(10);

		TextField textNumItem = new TextField(String.valueOf(item.getUnid()));// Units
																				// of
																				// item
		textNumItem.setDisable(true);
		textNumItem.setMaxSize(50, 10);

		HBox hbox = new HBox();
		hbox.setSpacing(10);
		// Remove one unit of item
		Button btnRemover = new Button("remover");
		btnRemover.setOnAction(e -> {
			if (Integer.parseInt(textNumItem.getText()) > 0) {
				textNumItem.setText(String.valueOf((Integer
						.parseInt(textNumItem.getText()) - 1)));// TextField
																// unit
				textPreco.setText(String.valueOf((Double.parseDouble(textPreco
						.getText()) - item.getPreco())));// TextField price

				item.setUnid(Integer.parseInt(textNumItem.getText()));
				controller.loadData();
			}
		});

		// Add one unit of item
		Button btnAdicionar = new Button("adicionar");
		btnAdicionar.setOnAction(e -> {
			textNumItem.setText(String.valueOf((Integer.parseInt(textNumItem
					.getText()) + 1)));// TextField unit

				textPreco.setText(String.valueOf((Double.parseDouble(textPreco
						.getText()) + item.getPreco())));// TextField price

				item.setUnid(Integer.parseInt(textNumItem.getText()));
				controller.loadData();

			});

		hbox.getChildren().add(btnRemover);
		hbox.getChildren().add(btnAdicionar);

		vbox2.getChildren().add(textNumItem);
		vbox2.getChildren().add(hbox);

		grid.add(nome, 0, 0);
		grid.add(preco, 1, 0);
		grid.add(area, 0, 1);
		grid.add(vbox2, 1, 1);

		vbox.getChildren().add(grid);
	}
}
