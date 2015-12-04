package aplicacao.Caixa.BancoDeDados.Itens;

import java.io.Serializable;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PratoComposto implements Item, Serializable {

	transient private StringProperty nome = null;
	transient private StringProperty preco = null;
	transient private StringProperty descricao = null;
	transient private IntegerProperty numItens = null;
	transient private SimpleIntegerProperty unid = null;;

	private int id;

	private String nomeTransition = new String();
	private String precoTransition = new String();
	private String descricaoTransition = new String();

	private Integer numItensTransition = null;
	private String oldName = null;
	private int unidTransition;

	public PratoComposto(String nome, double preco, String descricao,
			int numItens) {

		this.oldName = nome;
		this.nomeTransition = nome;
		this.precoTransition = String.valueOf(preco);
		this.descricaoTransition = descricao;

		this.numItensTransition = numItens;

	}

	@Override
	public StringProperty nomeProperty() {
		return nome;
	}

	@Override
	public StringProperty precoProperty() {
		return preco;
	}

	@Override
	public StringProperty descricaoProperty() {

		return descricao;
	};

	public IntegerProperty numItensProperty() {

		return numItens;
	};

	@Override
	public int getID() {
		return id;
	}

	@Override
	public String getDescricao() {
		return descricaoTransition;
	}

	@Override
	public double getPreco() {
		return Double.valueOf(precoTransition);
	}

	@Override
	public Item getItem() {
		return this;
	}

	public String toString() {
		return unidTransition + "%30%" + nomeTransition + "%30%"
				+ precoTransition + "%30%" + descricaoTransition + "%30%"
				+ getCategoria().toString() + "%30%" + numItensTransition;
	}

	@Override
	public String getNome() {
		return nomeTransition;
	}

	@Override
	public String getFoto() {
		return "url";

	}

	@Override
	public int getNumItens() {

		return numItensTransition;
	}

	@Override
	public Categoria getCategoria() {
		return Categoria.PRATO_COMPOSTO;

	}

	@Override
	public void setID(int id) {

		this.id = id;

	}

	@Override
	public void readTransitions() {
		nome = new SimpleStringProperty();
		preco = new SimpleStringProperty();
		descricao = new SimpleStringProperty();
		numItens = new SimpleIntegerProperty();
		unid = new SimpleIntegerProperty();

		nome.setValue(nomeTransition);
		preco.setValue(precoTransition);
		descricao.setValue(descricaoTransition);
		numItens.setValue(numItensTransition);
		unid.setValue(unidTransition);

	}

	@Override
	public void writeTransitions() {

		nomeTransition = nome.getValue();
		precoTransition = preco.getValue();
		descricaoTransition = descricao.getValue();
		numItensTransition = numItens.getValue();

	}

	@Override
	public String getOldName() {
		return oldName;
	}

	@Override
	public int getUnid() {
		return unid.getValue();
	}

	@Override
	public void setUnid(int unid) {
		unidTransition = unid;
		this.unid.setValue(unid);
	}

	@Override
	public IntegerProperty unidProperty() {
		return unid;
	}
}
