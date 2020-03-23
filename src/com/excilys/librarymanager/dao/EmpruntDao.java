package com.excilys.librarymanager.dao;
import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
/**
 * <b>EmpruntDao</b>
 * Data access object of lending
 * 
 * @author  Javier Martinez <i>[javier-andres.martinez@-boada@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-19
 */
public interface EmpruntDao {
	public List<Emprunt> getList() throws DaoException;
	public List<Emprunt> getListCurrent() throws DaoException;
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException;
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException;
	public Emprunt getById(int id) throws DaoException;
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException;
	public void update(Emprunt emprunt) throws DaoException;
	public int count() throws DaoException;
}
