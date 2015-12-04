package aplicacao.Caixa.BancoDeDados;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import aplicacao.BancoConnection;
import aplicacao.Properties;

public class NumeroDeMesas {

	private static Properties properties = new Properties();

	public static Integer getInt() {
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document doc = null;
		int numero = 0;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.parse(new File(properties.getProperty("numeroMesas")));

			NodeList root = doc.getElementsByTagName("NumeroDeMesas");
			Element element = (Element) root.item(0);
			numero = Integer.parseInt(element.getElementsByTagName("numero")
					.item(0).getTextContent());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return numero;
	}

	public static Integer[] getArray() {

		int num = getInt();
		Integer[] numArray = new Integer[num];

		for (int i = 0; i < num; i++) {
			numArray[i] = i + 1;
		}

		return numArray;
	}

	public static List<Integer> getMesasOcupadas() {
		BancoDeDados banco = null;
		List<Integer> listMesasOcupadas = null;
		try {
			banco = BancoConnection.getConnection();
			listMesasOcupadas = banco.getNumMesasOcupadas();
		} catch (DatabaseException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listMesasOcupadas;
	}
}
