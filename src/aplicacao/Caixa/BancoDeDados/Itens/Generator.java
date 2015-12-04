package aplicacao.Caixa.BancoDeDados.Itens;

import java.util.Arrays;

public class Generator {

	public static Item generatorItem(String item) {
		String[] atributes = item.split("%30%");

		try {
			if (atributes[4].equals("C")) {
				PratoComposto prato = new PratoComposto(atributes[1],
						Double.parseDouble(atributes[2]), atributes[3],
						Integer.parseInt(atributes[5]));
				prato.readTransitions();// Inicia os atributos do tipo property
				prato.writeTransitions();

				prato.setUnid(Integer.parseInt(atributes[0]));

				return prato;
			}
			if (atributes[4].equals("S")) {
				PratoSimples prato = new PratoSimples(atributes[1],
						Double.parseDouble(atributes[2]), atributes[3]);
				prato.readTransitions();// Inicia os atributos do tipo property
				prato.setUnid(Integer.parseInt(atributes[0]));

				return prato;

			}
			if (atributes[4].equals("B")) {
				Bebida prato = new Bebida(atributes[1],
						Double.parseDouble(atributes[2]), atributes[3]);
				prato.readTransitions();// Inicia os atributos do tipo property
				prato.setUnid(Integer.parseInt(atributes[0]));

				return prato;

			}
			if (atributes[4].equals("O")) {
				OutroItem prato = new OutroItem(atributes[1],
						Double.parseDouble(atributes[2]), atributes[3]);
				prato.readTransitions();// Inicia os atributos do tipo property
				prato.setUnid(Integer.parseInt(atributes[0]));

				return prato;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
