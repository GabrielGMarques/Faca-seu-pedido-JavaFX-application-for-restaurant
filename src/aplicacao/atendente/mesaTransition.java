package aplicacao.atendente;

import java.util.ArrayList;
import java.util.List;

import aplicacao.Caixa.Mesa;
import aplicacao.Caixa.BancoDeDados.Itens.Item;

public class mesaTransition {

	/**
	 * Download a list of items for a table object
	 * 
	 * @param list
	 * @param mesa
	 * @return
	 */
	public static Mesa transition(List<Item> list, Mesa mesa) {

		List<Item> listaExclusao = new ArrayList<>();

		list.forEach(item -> {
			item.readTransitions();
			mesa.getList().forEach(item2 -> {
				item2.readTransitions();
				if (item2.getNome().equals(item.getNome())) {
					item.setUnid((item.getUnid() + item2.getUnid()));
					listaExclusao.add(item2);// Add the item to the list of
												// control to the exclusion
												// of the table list
				}

			});
		});

		listaExclusao.forEach(mesa.getList()::remove);// Remove all items The
														// new list already
														// contains
		mesa.getList().forEach(list::add); // Add ancient items that do not
											// contain in the new list

		return new Mesa(list);

	}
}
