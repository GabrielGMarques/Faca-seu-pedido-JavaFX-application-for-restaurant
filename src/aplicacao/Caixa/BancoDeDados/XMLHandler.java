package aplicacao.Caixa.BancoDeDados;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class is the treatment of xml file , capturing the name and the
 * administrator password and permissions
 * 
 * @author Gabriel
 */
public class XMLHandler extends DefaultHandler implements Serializable {
	private String nomeAdmin = "";
	private String senhaAdmin = "";
	private Map<String, String> mapa = new HashMap<String, String>();
	private StringBuilder sb = new StringBuilder();

	private boolean admin = false;
	private String nome = "";
	private String senha = "";
	private String tag = "";

	public void setNomeAdmin(String nome) {
		this.nomeAdmin = nome;
	}

	public void setSenhaAdmin(String senha) {
		this.senhaAdmin = senha;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		sb.append(ch, start, length);

	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		if (qName.equals("Administrador")) {
			admin = false;
		}
		if (qName.equals("nome")) {

			if (admin) {
				if (nomeAdmin.isEmpty()) {
					// Transforms the first letter capitalized
					nomeAdmin = sb
							.toString()
							.substring(0, 1)
							.toUpperCase()
							.concat(sb.toString().substring(1,
									sb.toString().length()));
				}
			} else {
				nome = sb
						.toString()
						.substring(0, 1)
						.toUpperCase()
						.concat(sb.toString().substring(1,
								sb.toString().length()));
				;

			}
		}
		if (qName.equals("senha")) {
			if (admin) {
				if (senhaAdmin.isEmpty()) {
					senhaAdmin = sb.toString();

				}
			} else {
				senha = sb.toString().trim();

				mapa.put(tag, nome + ":" + senha);

			}

		}

	}

	public String getNomeAdmin() {
		return nomeAdmin;
	}

	public String getSenhaAdmin() {
		return senhaAdmin;
	}

	public Map<String, String> getMapa() {
		return mapa;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equals("Administrador")) {
			admin = true;
		}

		if (!qName.equals("nome") && !qName.equals("senha")) {
			tag = qName;

		}

		if (qName.equals("nome")) {

			sb = new StringBuilder();

		}
		if (qName.equals("senha")) {

			sb = new StringBuilder();

		}

	}

}
