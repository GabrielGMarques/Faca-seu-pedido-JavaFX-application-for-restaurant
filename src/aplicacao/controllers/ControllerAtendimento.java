package aplicacao.controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import aplicacao.BancoConnection;
import aplicacao.Main;
import aplicacao.Caixa.Delivery;
import aplicacao.Caixa.Mesa;
import aplicacao.Caixa.Pedido;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;
import aplicacao.Caixa.BancoDeDados.NumeroDeMesas;
import aplicacao.Caixa.BancoDeDados.Itens.Generator;
import aplicacao.Caixa.BancoDeDados.Itens.Item;
import aplicacao.atendente.mesaTransition;

public class ControllerAtendimento {

	@FXML
	TabPane tabPane;

	@FXML
	TextField pedido;

	@FXML
	Button btnVoltar;

	@FXML
	TextArea textPedido;

	@FXML
	Button btnItens;

	@FXML
	Button btnConfirmar;

	@FXML
	Tab tabPedido;

	@FXML
	ScrollBar scroll;

	@FXML
	ComboBox comboChoise;

	@FXML
	ComboBox comboN_Mesas;

	@FXML
	ComboBox comboMesa;

	@FXML
	TableView<Item> tablePedido;

	@FXML
	TableView<Item> tableMesa;

	@FXML
	Tab tabMesa;

	@FXML
	Label lblPreco;

	@FXML
	Label precoMesa;

	@FXML
	Button btnAlteracaoMesa;

	private static int numTab = 0;

	@FXML
	private void initialize() {
		// Verifica se o há uma categoria selecionada antes de enviar o pedido,
		// e se for celecionada Mesa certifica que há uma mesa escolhida e
		// também se há itens a ser gravados

		lblPreco.textProperty()
				.bind(ControllerItensAtendimento.precoProperty());
		precoMesa.textProperty().bind(
				ControllerItensAtendimento.precoProperty());

		comboN_Mesas.disableProperty().bind(
				comboChoise.valueProperty().isEqualTo("Delivery"));

		tabPane.getSelectionModel().select(numTab);

		// System.out.println(selectionModel.isSelected(1));
		tablePedido.setItems(FXCollections.observableArrayList(
				ControllerItensAtendimento.getList()).filtered(
				Item -> Item.getUnid() > 0));

		tableMesa.setItems(FXCollections.observableArrayList(
				ControllerItensAtendimento.getList()).filtered(
				Item -> Item.getUnid() > 0));

		// Pega o número de mesas no documento xml
		comboN_Mesas.setItems(FXCollections.observableArrayList(
				NumeroDeMesas.getArray()).filtered(
				item -> !NumeroDeMesas.getMesasOcupadas().contains(item)));

		comboMesa.setItems(FXCollections.observableArrayList(NumeroDeMesas
				.getMesasOcupadas()));

		// Passa a lista com a mesa e delivery para o choise box
		comboChoise.setItems(FXCollections.observableArrayList("Mesa",
				"Delivery"));

		btnConfirmar.disableProperty().bind(
				comboChoise
						.valueProperty()
						.isNull()
						.or(comboChoise.valueProperty().isEqualTo("Mesa")
								.and(comboN_Mesas.valueProperty().isNull()))
						.or(Bindings
								.size(tablePedido.getItems().filtered(
										item -> item.getUnid() > 0))
								.greaterThan(0).not()));

		btnAlteracaoMesa.disableProperty().bind(
				comboMesa
						.valueProperty()
						.isNull()
						.or(Bindings
								.size(tableMesa.getItems().filtered(
										item -> item.getUnid() > 0))
								.greaterThan(0).not()));

	}

	@FXML
	public void onBack() {
		ControllerItensAtendimento.clearList();

		Main.loadStage("/aplicacao/FacaSeuPedidoFXML.fxml", "Faca Seu Pedido");

	}

	@FXML
	public void onItens() {
		Main.loadStage("/aplicacao/atendente/Itens.fxml", "Itens");

	}

	@FXML
	public void onPedido() {

	}

	@FXML
	public void enviarPedido() {
		List<Item> itensPedido = filter(tableMesa.getItems()
				.filtered(item -> item.getUnid() > 0)
				.filtered(item -> !item.getCategoria().toString().equals("B")));

		List<Item> itensMesa = filter(tableMesa.getItems().filtered(
				item -> item.getUnid() > 0));

		numTab = 0;// O numero do controle da visualização das paginas volta a
					// 0(Pagina principal)

		BancoDeDados banco;

		try {
			banco = BancoConnection.getConnection();

			banco.fazerPedido(new Pedido(itensPedido));
			if (comboChoise.getValue().equals("Mesa")) {
				System.out.println("185");
				Mesa mesa = new Mesa(itensMesa);
				mesa.setPreco(Double.parseDouble(lblPreco.getText()));
				mesa.setID(Integer.parseInt(comboN_Mesas.getValue().toString()));
				banco.addMesa(mesa);
			} else {
				System.out.println("190");
				Delivery delivery = new Delivery(itensMesa);
				delivery.setPreco(Double.parseDouble(lblPreco.getText()));
				banco.addDelivery(delivery);
			}
			System.out.println("196");

			ControllerItensAtendimento.clearList();
			ControllerItensAtendimento.invertControl(true);
			System.out.println("200");
			Main.loadStage("/aplicacao/atendente/AtendimentoFXML.fxml",
					"Atendimento");
		} catch (DatabaseException | RemoteException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Absttrai itens da lista fazendo uma copia dos atributos e atribuindo em
	 * novos itens, evitando assim o problema de serialização do pacote javafx
	 * 
	 * @param itens
	 * @return
	 */
	private List<Item> filter(ObservableList<Item> itens) {
		List<Item> listItem = new ArrayList<Item>();
		itens.forEach(item -> listItem.add(Generator.generatorItem(item
				.toString())));
		return listItem;
	}

	public TextArea getTextArea() {
		return textPedido;
	}

	@FXML
	public void onItensMesa() {
		numTab = 1;
		Main.loadStage("/aplicacao/atendente/Itens.fxml", "Itens");
	}

	@FXML
	public void enviarPedidoMesa() {
		List<Item> itensPedido = filter(tableMesa.getItems()
				.filtered(item -> item.getUnid() > 0)
				.filtered(item -> !item.getCategoria().toString().equals("B")));

		List<Item> itensMesa = filter(tableMesa.getItems().filtered(
				item -> item.getUnid() > 0));

		numTab = 0;// O numero do controle da visualização das paginas volta a
					// 0(Pagina principal)

		BancoDeDados banco;

		try {
			banco = BancoConnection.getConnection();

			// banco.fazerPedido(new Pedido(itensPedido));
			Mesa mesaOriginal = banco
					.getMesas()
					.stream()
					.filter(item -> item.getID() == Integer.parseInt(comboMesa
							.getValue().toString())).findAny().get(); // Captura
																		// a
																		// mesa
																		// original

			Mesa mesa = mesaTransition.transition(itensMesa, mesaOriginal);// Adiciona
																			// todos
																			// os
																			// itens
																			// antigos
																			// a
																			// lista
																			// atual
			mesa.setPreco(Double.parseDouble(precoMesa.getText())
					+ mesaOriginal.getPreco());
			mesa.setID(Integer.parseInt(comboMesa.getValue().toString()));

			banco.alterMesa(mesa);
			ControllerItensAtendimento.clearList();
			ControllerItensAtendimento.invertControl(true);

			Main.loadStage("/aplicacao/atendente/AtendimentoFXML.fxml",
					"Atendimento");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
