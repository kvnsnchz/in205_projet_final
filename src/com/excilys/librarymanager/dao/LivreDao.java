package com.excilys.librarymanager.dao;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Livre;

/**
 * <b>LivreDao</b>
 * Data access object of book
 * 
 * @author  Javier Martinez <i>[javier-andres.martinez@-boada@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-19
 */
public interface LivreDao {
	public List<Livre> getList() throws DaoException;
	public Livre getById(int id) throws DaoException;
	public int create(String titre, String auteur, String isbn) throws DaoException;
	public void update(Livre livre) throws DaoException;
	public void delete(int id) throws DaoException;
	public int count() throws DaoException;
}
