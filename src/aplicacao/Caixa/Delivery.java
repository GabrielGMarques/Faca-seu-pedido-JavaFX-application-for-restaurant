package aplicacao.Caixa;

import java.io.Serializable;
import java.util.List;

import aplicacao.Caixa.BancoDeDados.Itens.Item;

public class Delivery implements Serializable {

	private int ID;
	private double preco;
	private List<Item> list;

	public Delivery(List<Item> list) {
		this.list = list;
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	/**
	 * Interage com cada item da lista
	 * 
	 * @return Uma strings com todos os itens compactados em forma de atributos
	 *         e cada item é divido por ";"
	 */
	public String toDelivery() {
		StringBuilder sb = new StringBuilder();
		list.forEach(item -> sb.append(item.toString() + ";"));
		return sb.toString();
	}
}
