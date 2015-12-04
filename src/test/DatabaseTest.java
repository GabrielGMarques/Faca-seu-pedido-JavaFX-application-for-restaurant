package test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import aplicacao.BancoConnection;
import aplicacao.Caixa.BancoDeDados.BancoDeDados;
import aplicacao.Caixa.BancoDeDados.DatabaseException;
import aplicacao.Caixa.BancoDeDados.Itens.Bebida;
import aplicacao.Caixa.BancoDeDados.Itens.Categoria;
import aplicacao.Caixa.BancoDeDados.Itens.Item;

public class DatabaseTest {
	BancoDeDados banco;

	public DatabaseTest() {
		try {
			banco = BancoConnection.getConnection();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void addItem() throws Exception {
		// Assert.assertTrue(banco.addItem(new PratoSimples(0, "TESTE", 0,
		// "TESTE")));
	}

	@Test
	public void alterarItem() throws Exception {
//		Item item = new Bebida("Margarita", 232, "Especial");
//		item.readTransitions();
//		item.nomeProperty().setValue("Margaritar");
//		item.writeTransitions();
//		item.setID(17);
//		
//		Assert.assertTrue(banco
//				.alterarItem(item));
	}

	@Test
	public void excluirItem() throws Exception {

//		Item item = new Bebida("Margaritar",232,"Especial");
//		item.readTransitions();
//		item.writeTransitions();
//		 Assert.assertTrue(banco.excluirItem(item));
	}

	@Test
	public void getItens() throws Exception {

		BancoDeDados banco = BancoConnection.getConnection();
		List<Item> list = banco.getItens(Categoria.PRATO_SIMPLES);
		Assert.assertTrue(list.size() > 1);

	}

	@Test
	public void fazerPedido() {
	}

	@Test
	public void alterarPedido() {
	}

	@Test
	public void excluirPedido() {
	}

	@Test
	public void getPedidos() {
	}

	@Test
	public void getPedido() {
	}

	@Test
	public void addMesa() {
	}

	@Test
	public void addMesa2() {
	}

	@Test
	public void alterMesa() {
	}

	@Test
	public void getMesa() {
	}

	@Test
	public void getMesas() {
	}

	@After
	public void after() {

	}

	@Test
	public void deleteMesa() {
	}

}
