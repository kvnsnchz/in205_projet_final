package com.excilys.librarymanager.service;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Membre;

/**
 * <b>EmpruntService</b>
 * Lending service
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-20
 */
public interface EmpruntService {
	/**
	 * @return List<Emprunt>
	 * @throws ServiceException
	 */
	public List<Emprunt> getList() throws ServiceException;
	/**
	 * @return List<Emprunt>
	 * @throws ServiceException
	 */
	public List<Emprunt> getListCurrent() throws ServiceException;
	/**
	 * @return List<Emprunt>
	 * @throws ServiceException
	 */
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException;
	/**
	 * @return List<Emprunt>
	 * @throws ServiceException
	 */
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException;
	/**
	 * @return Emprunt
	 * @throws ServiceException
	 */
	public Emprunt getById(int id) throws ServiceException;
	/**
	 * @param idMembre
	 * @param idLivre
	 * @param dateEmprunt
	 * @throws ServiceException
	 */
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException;
	/**
	 * @param id
	 * @throws ServiceException
	 */
	public void returnBook(int id) throws ServiceException;
	/**
	 * @return int
	 * @throws ServiceException
	 */
	public int count() throws ServiceException;
	/**
	 * @param idLivre
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean isLivreDispo(int idLivre) throws ServiceException;
	/**
	 * @param membre
	 * @return boolean
	 * @throws ServiceException
	 */
	public boolean isEmpruntPossible(Membre membre) throws ServiceException ;
}
