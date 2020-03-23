package com.excilys.librarymanager.service;

import java.util.List;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Livre;

/**
 * <b>LivreService</b>
 * Book service
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-20
 */
public interface LivreService {

	/**
	 * @return List<Livre>
	 * @throws ServiceException
	 */
	public List<Livre> getList() throws ServiceException;
	/**
	 * @return List<Livre>
	 * @throws ServiceException
	 */
	public List<Livre> getListDispo() throws ServiceException;
	/**
	 * @return Livre
	 * @throws ServiceException
	 */
	public Livre getById(int id) throws ServiceException;
	/**
	 * 
	 * @param titre
	 * @param auteur
	 * @param isbn
	 * @return idBook
	 * @throws ServiceException
	 */
	public int create(String titre, String auteur, String isbn) throws ServiceException;
	/**
	 * @param livre
	 * @throws ServiceException
	 */
	public void update(Livre livre) throws ServiceException;
	/**
	 * @param id
	 * @throws ServiceException
	 */
	public void delete(int id) throws ServiceException;
	/**
	 * @return
	 * @throws ServiceException
	 */
	public int count() throws ServiceException;
}
