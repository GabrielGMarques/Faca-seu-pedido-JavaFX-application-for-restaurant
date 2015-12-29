package aplicacao.Caixa.BancoDeDados;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import aplicacao.Properties;
import aplicacao.Caixa.Delivery;
import aplicacao.Caixa.Mesa;
import aplicacao.Caixa.Pedido;
import aplicacao.Caixa.BancoDeDados.Itens.Bebida;
import aplicacao.Caixa.BancoDeDados.Itens.Categoria;
import aplicacao.Caixa.BancoDeDados.Itens.Generator;
import aplicacao.Caixa.BancoDeDados.Itens.Item;
import aplicacao.Caixa.BancoDeDados.Itens.OutroItem;
import aplicacao.Caixa.BancoDeDados.Itens.PratoComposto;
import aplicacao.Caixa.BancoDeDados.Itens.PratoSimples;

public class BancoDeDadosImpl extends UnicastRemoteObject implements
		BancoDeDados {
	private static Properties config = new Properties();
	private static final String URL = "jdbc:mysql://localhost:3030/restaurante";
	private static final String USER = "root";
	private static final String PASSWORD = "JdBC84439879";

	private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	private Lock read = rwLock.readLock();
	private Lock write = rwLock.writeLock();

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BancoDeDadosImpl.class);

	public BancoDeDadosImpl() throws RemoteException {

		try {
			Class.forName(config.getProperty("driver"));
		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		}

	}

	@Override
	public boolean alterarItem(Item item) throws RemoteException {

		// Insert the id obtained of getId()
		item.setID(getID(item.getOldName()));
		// String builder for the concatenation of the Strings
		StringBuilder sb = null;
		write.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {

			sb = new StringBuilder();
			// UPDATE in table intens
			sb.append("UPDATE Itens SET nome='" + item.getNome() + "',preco="
					+ item.getPreco() + ",descricao='" + item.getDescricao()
					+ "',tipo ='" + item.getCategoria().toString()
					+ "' WHERE id=" + item.getID());

			stmt.executeUpdate(sb.toString());

			// UPDATE in table pratossimpless
			if (item.getCategoria().toString().equals("S")) {
				sb = new StringBuilder();
				sb.append("UPDATE pratossimpless SET nome='" + item.getNome()
						+ "',preco=" + item.getPreco() + ",descricao='"
						+ item.getDescricao() + "' WHERE id=" + item.getID());

				int i = stmt.executeUpdate(sb.toString());
				if (i > 0) {
					return true;
				}
				// UPDATE in table pratoscompostos
			} else if (item.getCategoria().toString().equals("C")) {
				sb = new StringBuilder();
				sb.append("UPDATE pratoscompostos SET nome='" + item.getNome()
						+ "',preco=" + item.getPreco() + ",descricao='"
						+ item.getDescricao() + "', numItens = "
						+ item.getNumItens() + " WHERE id=" + item.getID());

				int i = stmt.executeUpdate(sb.toString());

				if (i > 0) {
					return true;
				}
				// UPDATE in table bebidas
			} else if (item.getCategoria().toString().equals("B")) {
				sb = new StringBuilder();

				sb.append("UPDATE bebidas SET nome='" + item.getNome()
						+ "',preco=" + item.getPreco() + ",decricao='"
						+ item.getDescricao() + "' WHERE id=" + item.getID());

				int i = stmt.executeUpdate(sb.toString());

				if (i > 0) {
					return true;
				}
			}
			// UPDATE in table outrositens

			if (item.getCategoria().toString().equals("O")) {
				sb = new StringBuilder();
				sb.append("UPDATE outrosItens SET nome='" + item.getNome()
						+ "',preco=" + item.getPreco() + ",descricao='"
						+ item.getDescricao() + "' WHERE id=" + item.getID());

				int j = stmt.executeUpdate(sb.toString());

				if (j > 0) {
					return true;
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;

		} finally {
			write.unlock();

		}

		return false;
	}

	@Override
	public boolean fazerPedido(Pedido p) throws RemoteException {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			// insert the request in Database
			stmt.execute("INSERT pedido(pedido) VALUES('" + p.getPedido()
					+ "')");

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return false;
		}

		return true;
	}

	@Override
	public List<Item> getItens(Categoria c) throws RemoteException {

		List<Item> lista = null;

		read.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			if (c.toString().equals("C")) {
				lista = new ArrayList<Item>();
				// Request the PratosCompostos
				ResultSet result = stmt
						.executeQuery("SELECT *from PratosCompostos");

				while (result.next()) {

					lista.add(new PratoComposto(result.getString(2), result
							.getDouble(3), result.getString(4), result
							.getInt(5)));
				}
				// Request the PratosSimples

			} else if (c.toString().equals("S")) {
				lista = new ArrayList<Item>();

				ResultSet result = stmt
						.executeQuery("SELECT *from PratosSimpless");

				while (result.next()) {

					lista.add((Item) new PratoSimples(result.getString(2),
							result.getDouble(3), result.getString(4)));
				}
				// Request the Bebidas
			} else if (c.toString().equals("B")) {

				lista = new ArrayList<Item>();
				ResultSet result = stmt.executeQuery("SELECT *from Bebidas");

				while (result.next()) {

					lista.add(new Bebida(result.getString(2), result
							.getDouble(3), result.getString(4)));
				}
				// Request the OutrosItens

			} else if (c.toString().equals("O")) {
				lista = new ArrayList<Item>();
				ResultSet result = stmt
						.executeQuery("SELECT *from OutrosItens");

				while (result.next()) {

					lista.add(new OutroItem(result.getString(2), result
							.getDouble(3), result.getString(4)));
				}

			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			read.unlock();

		}

		return lista;
	}

	@Override
	public boolean alterMesa(Mesa mesa) {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			// Initializes the Properties
			mesa.getList().forEach(item -> item.readTransitions());
			// Updates in table mesas
			stmt.executeUpdate("UPDATE mesas SET preco=" + mesa.getPreco()
					+ ", pedido='" + mesa.toMesa() + "' WHERE id="
					+ mesa.getID());

			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return true;
	}

	@Override
	public boolean addItem(Item item) throws RemoteException {

		write.lock();
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try (PrepareStatement prepare = conn.PrepareStatement("INSERT INTO Itens (nome,preco,descricao,tipo) VALUES(?,?,?,?)")) {

			conn.setAutoCommit(false);
			// Does the insert in the table of items
					prepare.setString(1,item.getNome());
				        prepare.setDouble(2,item.getPreco());
					prepare.setString(3,item.getDescricao());
				        prepare.setString(4,item.getCategoria().toString());
					prepare.execute();

			/*t
			 * Create a result set to get the last id (which is the item that *
			 * has just been inserted) and insert in the corresponding table
			 * item the same id
			 */
			ResultSet result = conn.createStatement().executeQuery("SELECT *from itens");
			int id = 0;
			while (result.next()) {

				id = result.getInt(1);

			}

			item.setID(id);

			// Now check the categories and enter the corresponding tables
			if (item.getCategoria().toString().equals("S")) {

				int i = stmt
						.executeUpdate("INSERT pratosSimpless (id,nome,preco,descricao) VALUES("
								+ item.getID()
								+ ",'"
								+ item.getNome()
								+ "',"
								+ item.getPreco()
								+ ",'"
								+ item.getDescricao()
								+ "')");
				if (i > 0) {
					return true;
				}

			} else if (item.getCategoria().toString().equals("C")) {

				int i = stmt
						.executeUpdate("INSERT pratoscompostos(id,nome,preco,descricao,numItens) VALUES("
								+ item.getID()
								+ ",'"
								+ item.getNome()
								+ "',"
								+ item.getPreco()
								+ ",'"
								+ item.getDescricao()
								+ "'," + item.getNumItens() + ")");

				if (i > 0) {
					return true;
				}

			} else if (item.getCategoria().toString().equals("B")) {

				int i = stmt
						.executeUpdate("INSERT bebidas (id,nome,preco,decricao) VALUES("
								+ item.getID()
								+ ",'"
								+ item.getNome()
								+ "',"
								+ item.getPreco()
								+ ",'"
								+ item.getDescricao()
								+ "')");

				if (i > 0) {
					return true;
				}
			} else if (item.getCategoria().toString().equals("O")) {
				System.out.println("O");

				int j = stmt
						.executeUpdate("INSERT outrosItens (id,nome,preco,descricao) VALUES("
								+ item.getID()
								+ ",'"
								+ item.getNome()
								+ "',"
								+ item.getPreco()
								+ ",'"
								+ item.getDescricao()
								+ "')");

				if (j > 0) {
					return true;
				}
			}

			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			LOGGER.error(e.getMessage());
			return false;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					LOGGER.error(e.getMessage());
				}
			}
			write.unlock();
		}
		return true;
	}

	@Override
	public boolean excluirItem(Item item) throws RemoteException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try (Statement stmt = conn.createStatement()) {
			String tipo = null;
			if (item.getCategoria().toString().equals("C")) {
				tipo = "PratosCompostos";
			}
			if (item.getCategoria().toString().equals("B")) {
				tipo = "Bebidas";

			}
			if (item.getCategoria().toString().equals("S")) {
				tipo = "PratosSimpless";

			}
			if (item.getCategoria().toString().equals("O")) {
				tipo = "OutrosItens";
			}

			item.setID(getID(item.getNome()));
			// Deletes the item from all tables that it exists
			conn.setAutoCommit(false);
			stmt.executeUpdate("DELETE FROM itens WHERE id=" + item.getID());
			int i = stmt.executeUpdate("DELETE FROM " + tipo + " WHERE id="
					+ item.getID());
			stmt.executeUpdate("ALTER TABLE itens AUTO_INCREMENT=1");
			stmt.executeUpdate("ALTER TABLE " + tipo + " AUTO_INCREMENT=1");

			conn.commit();
			if (i > 0) {
				return false;
			}

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				LOGGER.error(e.getMessage());
			}
			LOGGER.error(e.getMessage());
			return false;
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
			}
		}
		return false;

	}

	@Override
	public boolean excluirPedido(int id) throws RemoteException {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		// Deletes the request from tables
		try (Statement stmt = conn.createStatement()) {
			conn.setAutoCommit(false);

			stmt.executeUpdate("DELETE FROM pedido WHERE id=" + id);
			stmt.executeUpdate("ALTER TABLE pedido AUTO_INCREMENT = 1");

			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {

				LOGGER.error(e.getMessage());
				return false;
			}
			LOGGER.error(e.getMessage());
			return false;

		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {

				LOGGER.error(e.getMessage());
			}

		}

		return true;

	}

	@Override
	public List<Pedido> getPedidos() throws RemoteException {

		List<Pedido> lista = null;
		read.lock();

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT *FROM pedido")) {
			lista = new ArrayList<Pedido>();

			while (result.next()) {

				List<Item> listItens = new ArrayList<Item>();
				int ID = result.getInt(1);

				// Abstracting the attributes of each item request
				Arrays.asList(result.getString(2).split(";")).forEach(
						atributes -> listItens.add(Generator
								.generatorItem(atributes))); /*
															 * Interacts with
															 * Array Items form
															 * of attributes
															 */
				Pedido pedido = new Pedido(listItens);
				pedido.setID(ID);
				lista.add(pedido);

			}

		} catch (SQLException e) {

			LOGGER.error(e.getMessage());
		} finally {
			read.unlock();

		}

		return lista;
	}

	@Override
	public boolean addMesa(Mesa mesa) throws RemoteException {

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {

			stmt.execute("INSERT mesas(id,preco,pedido) VALUES(" + mesa.getID()
					+ "," + mesa.getPreco() + ",'" + mesa.toMesa() + "')");

			return true;
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	@Override
	public List<Mesa> getMesas() throws RemoteException {
		List<Mesa> lista = new ArrayList<Mesa>();

		read.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT *FROM mesas")) {

			// Interact under each table
			while (result.next()) {
				// Lists all table items
				List<Item> listItem = new ArrayList<>();

				int ID = result.getInt(1);// ID of table
				double preco = result.getDouble(2);// Value of table

				// Interacts in the array and pass the attributes of each item
				// to item generator, recording the same list of items
				Arrays.asList(result.getString(3).split(";")).forEach(
						atributes -> listItem.add(Generator
								.generatorItem(atributes))); /*
															 * Each item of the
															 * array contain the
															 * attributes of an
															 * object Item
															 */
				// Pass the list of items subject to Mesa
				Mesa mesa = new Mesa(listItem);
				mesa.setID(ID);
				mesa.setPreco(preco);
				lista.add(mesa);

			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			read.unlock();
		}

		return lista;
	}

	@Override
	public boolean deleteMesa(int id) throws RemoteException {

		write.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DELETE FROM mesas WHERE id=" + id);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return false;
		} finally {
			write.unlock();
		}
		return true;
	}

	@Override
	public Categoria getCategoria(String nome) throws RemoteException {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT *FROM itens")) {

			while (result.next()) {

				if (result.getString(2).toUpperCase()
						.equals(nome.toUpperCase())) {
					if (result.getString(5).equals("C")) {
						return Categoria.PRATO_COMPOSTO;
					}
					if (result.getString(5).equals("S")) {
						return Categoria.PRATO_SIMPLES;
					}
					if (result.getString(5).equals("B")) {
						return Categoria.BEBIDA;
					}
					if (result.getString(5).equals("O")) {
						return Categoria.OUTRO;
					}
				}
			}
		} catch (Exception e) {

			return null;
		} finally {
		}

		return null;
	}

	@Override
	public int getID(String nome) throws RemoteException {

		read.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT *FROM itens")) {

			while (result.next()) {

				int i = result.getInt(1);
				if (result.getString(2).toUpperCase()
						.equals(nome.toUpperCase())) {
					return i;
				}

			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return 0;
		} finally {
			read.unlock();
		}

		return 0;
	}

	@Override
	public List<Delivery> getDeliverys() throws RemoteException {
		List<Delivery> lista = new ArrayList<Delivery>();
		// This method is the same as the method getMesas() do it now order
		// the deliverys database
		read.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT *FROM delivery")) {

			while (result.next()) {
				List<Item> listItem = new ArrayList<>();

				int ID = result.getInt(1);
				double preco = result.getDouble(2);

				Arrays.asList(result.getString(3).split(";")).forEach(
						atributes -> listItem.add(Generator
								.generatorItem(atributes)));

				Delivery delivery = new Delivery(listItem);
				delivery.setID(ID);
				delivery.setPreco(preco);
				lista.add(delivery);

			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		} finally {
			read.unlock();
		}

		return lista;
	}

	@Override
	public boolean addDelivery(Delivery delivery) throws RemoteException {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			stmt.execute("INSERT delivery(preco,descricao) VALUES("
					+ delivery.getPreco() + ",'" + delivery.toDelivery() + "')");

		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean getLogin(String User, String password,
			UserDatabase userDatabase) throws RemoteException {
		return LoginXML.getLogin(User, password, userDatabase);
	}

	@Override
	public List<Integer> getNumMesasOcupadas() throws RemoteException {
		List<Integer> listMesas = new ArrayList<>();

		read.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery("SELECT *FROM mesas")) {
			;

			int i = 0;
			while (result.next()) {
				listMesas.add(result.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listMesas;
	}

	@Override
	public boolean deleteDelivery(int id) throws RemoteException {
		write.lock();
		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Statement stmt = conn.createStatement()) {
			stmt.executeUpdate("DELETE FROM delivery WHERE id=" + id);
		} catch (Exception e) {

			LOGGER.error(e.getMessage());
			return false;
		} finally {
			write.unlock();
		}
		return true;
	}

}
