package aplicacao.Caixa.BancoDeDados;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import aplicacao.Caixa.Delivery;
import aplicacao.Caixa.Mesa;
import aplicacao.Caixa.Pedido;
import aplicacao.Caixa.BancoDeDados.Itens.Categoria;
import aplicacao.Caixa.BancoDeDados.Itens.Item;

public interface BancoDeDados extends Remote {

	/**
	 * Add an item to the database
	 * 
	 * @param item
	 * @return
	 * @throws RemoteException
	 */
	public boolean addItem(Item item) throws RemoteException;

	/**
	 * Alter an item to the database
	 * 
	 * @param item
	 * @return
	 * @throws RemoteException
	 */
	public boolean alterarItem(Item item) throws RemoteException;

	/**
	 * Delete an item to the database
	 * 
	 * @param idItem
	 * @return
	 * @throws RemoteException
	 */
	public boolean excluirItem(Item idItem) throws RemoteException;

	/**
	 * request a items of list in database with a ENUM of Category
	 * 
	 * @param Categoria
	 * @return
	 * @throws RemoteException
	 */
	public List<Item> getItens(Categoria c) throws RemoteException;

	/**
	 * Inserts a request to the database
	 * 
	 * @param p
	 * @return
	 * @throws RemoteException
	 */
	public boolean fazerPedido(Pedido p) throws RemoteException;

	/**
	 * Excludes a request in the database
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean excluirPedido(int id) throws RemoteException;

	/**
	 * requests all requests of database
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<Pedido> getPedidos() throws RemoteException;

	/**
	 * Add a table in the database
	 * 
	 * @param mesa
	 * @return
	 * @throws RemoteException
	 */
	public boolean addMesa(Mesa mesa) throws RemoteException;

	/**
	 * Alters a table in Database
	 * 
	 * @param mesa
	 * @return
	 * @throws RemoteException
	 */
	public boolean alterMesa(Mesa mesa) throws RemoteException;

	/**
	 * Requests all of tables from database
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<Mesa> getMesas() throws RemoteException;

	/**
	 * Requests all tables disabled
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<Integer> getNumMesasOcupadas() throws RemoteException;

	/**
	 * Excludes the table from database
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean deleteMesa(int id) throws RemoteException;

	/**
	 * Excludes the delivery from database
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	public boolean deleteDelivery(int id) throws RemoteException;

	/**
	 * Requests all deliveries database
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public List<Delivery> getDeliverys() throws RemoteException;

	/**
	 * Add new Delivery
	 * 
	 * @param delivery
	 * @return
	 * @throws RemoteException
	 */
	public boolean addDelivery(Delivery delivery) throws RemoteException;

	/**
	 * requests an id of the main table item "ITENS" c@param nome
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getID(String nome) throws RemoteException;

	/**
	 * Makes a login for change the database
	 * 
	 * @param User
	 * @param password
	 * @param userDatabase
	 * @return
	 * @throws RemoteException
	 */
	public boolean getLogin(String User, String password,
			UserDatabase userDatabase) throws RemoteException;

	/**
	 * Requests the category of item with name of item
	 * 
	 * @param nome
	 * @return
	 * @throws RemoteException
	 */
	public Categoria getCategoria(String nome) throws RemoteException;

}
