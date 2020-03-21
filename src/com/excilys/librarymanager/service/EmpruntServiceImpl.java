package com.excilys.librarymanager.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.dao.EmpruntDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Membre;

/**
 * EmpruntServiceImpl
 */
public class EmpruntServiceImpl implements EmpruntService {
    private static EmpruntServiceImpl instance;

    private EmpruntServiceImpl() {}

    public static EmpruntService getInstance() {
        if(instance == null) {
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getList();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return emprunts;
    }

    @Override
	public List<Emprunt> getListCurrent() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrent();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return emprunts;
    }

    @Override
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return emprunts;
    }

    @Override
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            emprunts = empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return emprunts;
    }

    @Override
	public Emprunt getById(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        Emprunt emprunt = new Emprunt();
        try {
            emprunt = empruntDao.getById(id);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return emprunt;
    }

    @Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
    }

    @Override
	public void returnBook(int id) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
            Emprunt emprunt = empruntDao.getById(id);
            emprunt.setDateRetour(LocalDate.now());
            empruntDao.update(emprunt);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
    }

    @Override
	public int count() throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int count = -1;
        try {
            count = empruntDao.count();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
        }
        return count;
    }

    @Override
	public boolean isLivreDispo(int idLivre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        try {
           int count = empruntDao.getListCurrentByLivre(idLivre).size();
           return count == 0;
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return false;
    }

    @Override
	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        int count = -1;
        try {
           count = empruntDao.getListCurrentByMembre(membre.getId()).size();
           return count < membre.getAbonnement().getVal();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
        }
        
        return false;
    }
}