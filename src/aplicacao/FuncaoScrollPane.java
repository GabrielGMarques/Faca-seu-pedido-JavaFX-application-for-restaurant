package aplicacao;

import javafx.scene.control.ScrollBar;
import javafx.scene.layout.VBox;

public class FuncaoScrollPane {

	public static void organizar(VBox vbox, ScrollBar scroll) {

		scroll.valueProperty().addListener((ov, old_val, new_val) -> {
			vbox.setLayoutY(-new_val.doubleValue());
			scroll.setMax(vbox.getChildren().size() * 170);
		});

	}
}
