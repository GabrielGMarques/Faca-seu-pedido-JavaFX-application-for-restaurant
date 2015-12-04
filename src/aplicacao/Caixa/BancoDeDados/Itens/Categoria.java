package aplicacao.Caixa.BancoDeDados.Itens;

import java.io.Serializable;

public enum Categoria implements Serializable {

	PRATO_COMPOSTO('C'), PRATO_SIMPLES('S'), BEBIDA('B'), OUTRO('O');

	private char categoria;

	Categoria(char c) {

		this.categoria = c;
	}

	public String toString() {

		return String.valueOf(categoria);
	}

	public static Categoria getCatgoria(char c) {

		for (Categoria categoria : values()) {
			if (categoria.toString().charAt(0) == c) {
				return categoria;
			}
		}
		return null;
	}
}
