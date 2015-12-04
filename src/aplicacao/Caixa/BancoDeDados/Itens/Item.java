package aplicacao.Caixa.BancoDeDados.Itens;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public interface Item {

	public String getOldName();

	public String getFoto();

	public String getNome();

	public IntegerProperty unidProperty();

	public int getUnid();

	public void setUnid(int unid);

	public void readTransitions();

	public void writeTransitions();

	public StringProperty nomeProperty();

	public int getID();

	public String getDescricao();

	public StringProperty descricaoProperty();

	public double getPreco();

	public StringProperty precoProperty();

	public default int getNumItens() {
		return 0;
	}

	public Categoria getCategoria();

	public Item getItem();

	public String toString();

	public void setID(int id);

}
