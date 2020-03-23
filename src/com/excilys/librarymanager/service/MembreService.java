package com.excilys.librarymanager.service;

import java.util.List;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;

/**
 * <b>MembreService</b>
 * Member service
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-20
 */
public interface MembreService {

	/**
	 * @return List<Membre>
	 * @throws ServiceException
	 */
	public List<Membre> getList() throws ServiceException;
	/**
	 * @return List<Membre>
	 * @throws ServiceException
	 */
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException;
	/**
	 * @return Membre
	 * @throws ServiceException
	 */
	public Membre getById(int id) throws ServiceException;
	/**
	 * @param nom
	 * @param prenom
	 * @param adresse
	 * @param email
	 * @param telephone
	 * @return memberId
	 * @throws ServiceException
	 */
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException;
	/**
	 * @param membre
	 * @throws ServiceException
	 */
	public void update(Membre membre) throws ServiceException;
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
