package aplicacao.Caixa;

import java.io.Serializable;
import java.util.List;

import aplicacao.Caixa.BancoDeDados.Itens.Item;

public class Pedido implements Serializable {
	List<Item> list = null;
	private int ID = 0;

	public Pedido(List<Item> list) {
		this.list = list;

	}

	public String getPedido() {
		StringBuilder pedido = new StringBuilder();
		list.forEach(item -> pedido.append(item.toString() + ";"));
		return pedido.toString();
	}

	public List<Item> getList() {
		return list;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

}