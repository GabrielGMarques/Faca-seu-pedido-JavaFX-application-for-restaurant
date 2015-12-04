package aplicacao.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import aplicacao.Main;
import aplicacao.FuncaoScrollPane;
import aplicacao.Caixa.BancoDeDados.Itens.Categoria;
import aplicacao.Caixa.BancoDeDados.Itens.Item;
import aplicacao.atendente.ItemPane;

public class ControllerItensAtendimento {

	private static List<Item> newList = new ArrayList<Item>();
	private static StringProperty preco = new SimpleStringProperty("0.0");
	private static Map<String, Integer> mapControll = new HashMap<String, Integer>();

	@FXML
	VBox vboxBebidas;
	@FXML
	VBox vboxPratosC;

	@FXML
	VBox vboxPratosS;

	@FXML
	VBox vboxOutros;

	@FXML
	ScrollBar scroll;

	@FXML
	TableView<Item> tablePedido;

	@FXML
	TextField textPreco;

	@FXML
	Tab tabPratosSimples;

	@FXML
	Tab tabOutros;

	@FXML
	Tab tabPratosCompostos;

	@FXML
	Tab tabBebidas;

	private ItemPane itemPaneBebida = new ItemPane();
	private ItemPane itemPanePratosC = new ItemPane();
	private ItemPane itemPanePratosS = new ItemPane();
	private ItemPane itemPaneOutros = new ItemPane();
	private static boolean controll = true;

	@FXML
	public void initialize() {

		// O atributo é igual a lista inicialmente, para caso for cancelado a
		// transação a lista volte a ter os mesmos atributos quando foi
		// nicializada
		FuncaoScrollPane.organizar(vboxBebidas, scroll);

		textPreco.setText(preco.getValue());
		preco.bindBidirectional(textPreco.textProperty());

		itemPaneBebida.getItens(Categoria.BEBIDA, vboxBebidas, newList,
				textPreco, this, controll);
		itemPanePratosC.getItens(Categoria.PRATO_COMPOSTO, vboxPratosC,
				newList, textPreco, this, controll);
		itemPanePratosS.getItens(Categoria.PRATO_SIMPLES, vboxPratosS, newList,
				textPreco, this, controll);
		itemPaneOutros.getItens(Categoria.OUTRO, vboxOutros, newList,
				textPreco, this, controll);

		loadData();

		load();// Grava o numero de unidade de cada tem em um  HasMap

	}

	public void loadData() {

		tablePedido.setItems(FXCollections.observableArrayList(newList)
				.filtered(item -> item.getUnid() > 0));

	}

	private void load() {
		mapControll.clear();
		newList.forEach(item -> {
			mapControll.put(item.getNome(), item.getUnid());
		});
	}

	@FXML
	public void onCancel() {
		if (tablePedido.getItems().filtered(item -> item.getUnid() > 0).size() > 0) {
			invertControl(false);

			// Atribui aos atributos de unidad
			newList.forEach(item -> {
				item.setUnid(mapControll.get(item.getNome()));
			});
		}
		Main.loadStage("/aplicacao/atendente/AtendimentoFXML.fxml",
				"Atendimento");

	}

	@FXML
	public void onBebdias() {
		try {
			FuncaoScrollPane.organizar(vboxBebidas, scroll);
		} catch (Exception e) {
		}
	}

	@FXML
	public void onCompostos() {
		FuncaoScrollPane.organizar(vboxPratosC, scroll);

	}

	@FXML
	public void onSimples() {
		FuncaoScrollPane.organizar(vboxPratosS, scroll);

	}

	@FXML
	public void onOutros() {
		FuncaoScrollPane.organizar(vboxOutros, scroll);

	}

	public static List<Item> getList() {
		return newList;
	}

	public static void clearList() {
		preco.setValue("0.0");
		newList.clear();
	}

	public static void invertControl(boolean value) {
		controll = value;
	}

	public static StringProperty precoProperty() {
		return preco;
	}

	@FXML
	public void onConcluir() {
		invertControl(false);

		Main.loadStage("/aplicacao/atendente/AtendimentoFXML.fxml",
				"Atendimento");
	}

}
