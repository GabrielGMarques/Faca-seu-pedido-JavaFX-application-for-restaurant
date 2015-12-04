package aplicacao.Caixa.BancoDeDados;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

import aplicacao.Caixa.BancoDeDados.UserDatabase.UserForList;

public class LoginXML implements Serializable {

	private static XMLHandler handler = new XMLHandler();
	/**
	 * Carrega o XMLHandler
	 */
	static {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			InputSource source = new InputSource(
					"src/resources/permissions.xml");
			parser.parse(source, handler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * verifies that the pair name and password conforms to the administrator of
	 * the pair or according to a couple of permission
	 * 
	 * @param nome
	 * @param senha
	 * @return
	 */
	public static boolean getLogin(String nome, String senha,
			UserDatabase userDatabase) {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			XMLHandler handler = new XMLHandler();
			InputSource source = new InputSource(
					"src/resources/permissions.xml");
			parser.parse(source, handler);

			// checks whether the administrator
			if (nome.toUpperCase().equals(handler.getNomeAdmin().toUpperCase())
					&& senha.equals(handler.getSenhaAdmin())) {
				System.out.println(nome);
				userDatabase.setNome(handler.getNomeAdmin());
				userDatabase.setAdmin(true);

				return true;
			} else {
				// Takes the map permissions
				for (int i = 1; i <= handler.getMapa().size(); i++) {
					String[] nomeSenha = (handler.getMapa()
							.get("permissao" + i) + "").split(":");
					if (nome.toUpperCase().equals(nomeSenha[0].toUpperCase())
							&& senha.equals(nomeSenha[1])) {
						System.out.println(nome);

						userDatabase.setNome(nomeSenha[0]);
						userDatabase.setAdmin(false);

						return true;
					}

				}
			}

		} catch (Exception e2) {
			e2.printStackTrace();
		}

		return false;
	}

	/**
	 * this method is able to give permission to someone, so that that person
	 * can change the database and it is also possible to change passwords
	 * 
	 * @param list
	 */
	public static void savePermissions(List<UserForList> list) {
		DocumentBuilderFactory dbf = null;
		DocumentBuilder db = null;
		Document document = null;
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			document = db.newDocument();

			// primary node
			Element rootElem = document.createElement("perimissoes");
			document.appendChild(rootElem);

			// administer node
			Element adminElem = document.createElement("Administrador");
			rootElem.appendChild(adminElem);

			// administer account
			Element contaAdminElem = document.createElement("contaAdmin");
			adminElem.appendChild(contaAdminElem);

			// administer data
			Element nomeElem = document.createElement("nome");
			Text txtNome = document.createTextNode(handler.getNomeAdmin());
			nomeElem.appendChild(txtNome);
			contaAdminElem.appendChild(nomeElem);

			Element senhaElem = document.createElement("senha");
			Text textSenha = document.createTextNode(handler.getSenhaAdmin());
			senhaElem.appendChild(textSenha);
			contaAdminElem.appendChild(senhaElem);

			// permissions
			Element permissoesElem = document.createElement("permissoes");
			rootElem.appendChild(permissoesElem);

			// Insert all the permissions
			for (UserForList user : list) {
				// permission
				Element permissao = document.createElement(user.getPermissao());
				permissoesElem.appendChild(permissao);

				// Name of the permission
				Element nomeUsuarioElem = document.createElement("nome");
				Text nomeUsuario = document.createTextNode(user.getName());
				nomeUsuarioElem.appendChild(nomeUsuario);
				permissao.appendChild(nomeUsuarioElem);
				// password of the permission
				Element senhaUsuarioElem = document.createElement("senha");
				Text senhaUsuario = document.createTextNode(user.getSenha());
				senhaUsuarioElem.appendChild(senhaUsuario);
				permissao.appendChild(senhaUsuarioElem);
			}

			TransformerFactory tsf = TransformerFactory.newInstance();
			Transformer tf = tsf.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");

			FileWriter file = new FileWriter("src/resources/permissions.xml");

			StreamResult sr = new StreamResult(file);

			DOMSource ds = new DOMSource(document);

			tf.transform(ds, sr);

		} catch (ParserConfigurationException | IOException
				| TransformerException e2) {

			e2.printStackTrace();
		}
	}

	// A variavel change deve retornar se a mudança na senha ocorreu com
	// sucesso, o
	private static boolean change = false;

	/**
	 * Muda a senha do respectivo usuário
	 * 
	 * @param nome
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	public static boolean newPassWord(String nome, String oldPass,
			String newPass) {

		try {

			if (handler.getNomeAdmin().equals(nome)) {
				if (handler.getSenhaAdmin().equals(oldPass)) {
					handler.setSenhaAdmin(newPass);
					change = true;
				}
			}
			// Retorna a nova lista de permissõess
			List<UserForList> list = handler
					.getMapa()
					.entrySet()
					.stream()
					.map(value -> {
						UserForList user = UserDatabase.newUserForList();

						String[] tokens = value.toString().split("=");
						String[] nomeSenha = tokens[1].split(":");
						if (nomeSenha[0].equals(nome)
								&& nomeSenha[1].equals(oldPass)) {
							user.setName(nomeSenha[0]);
							user.setSenha(newPass);
							user.setPermissao(tokens[0]);
							change = true;

						} else {
							user.setName(nomeSenha[0]);
							user.setSenha(nomeSenha[1]);
							user.setPermissao(tokens[0]);

						}
						return user;
					}).collect(Collectors.toList());
			// Salva a nova lista de permissões no arquivo XML
			savePermissions(list);

		} catch (Exception e) {
		}

		return change;
	}

	public static List<UserForList> getUsers() {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			XMLHandler handler = new XMLHandler();
			InputSource source = new InputSource(
					"src/resources/permissions.xml");
			parser.parse(source, handler);

			// Retorna a lista de nomes convertida em uma lista de Users
			return handler.getMapa().entrySet().stream().map(nome -> {
				String[] tokens = nome.toString().split("=");
				String[] nomeSenha = tokens[1].split(":");

				UserForList user = UserDatabase.newUserForList();
				user.setName(nomeSenha[0]);
				user.setSenha(nomeSenha[1]);
				user.setPermissao(tokens[0]);

				return user;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
