package com.excilys.librarymanager.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.LivreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Livre;

/**
 * LivreServiceImpl
 */
public class LivreServiceImpl implements LivreService {
    private static LivreServiceImpl instance;

    private LivreServiceImpl() {}

    public static LivreService getInstance() {
        if(instance == null) {
            instance = new LivreServiceImpl();
        }
        return instance;
    } 

    @Override
    public List<Livre> getList() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        List<Livre> livres = new ArrayList<>();
        try {
            livres = livreDao.getList();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return livres;
    }

    @Override
	public List<Livre> getListDispo() throws ServiceException {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Livre> livres = getList();
        
        Iterator<Livre> iter = livres.iterator();
        while (iter.hasNext()) {
            Livre livre = iter.next(); 
            if(!empruntService.isLivreDispo(livre.getId())) {
                iter.remove();
            }
        }
        return livres;
    }

    @Override
	public Livre getById(int id) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        Livre livre = new Livre();
        try {
            livre = livreDao.getById(id);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return livre;
    }

    @Override
	public int create(String titre, String auteur, String isbn) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int i = -1;
        
        if(titre == null || titre.isBlank()) {
			throw new ServiceException("Problème lors de la création du livre: Le titre ne peut pas être vide");
        }

        try {
            i = livreDao.create(titre, auteur, isbn);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return i;
    }

    @Override
	public void update(Livre livre) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        
        if(livre.getTitre() == null || livre.getTitre().isBlank()) {
			throw new ServiceException("Problème lors de la mis à jour du livre: Le titre ne peut pas être vide");
        }

        try {
            livreDao.update(livre);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
    }

    @Override
	public void delete(int id) throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        try {
            livreDao.delete(id);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
    }

    @Override
	public int count() throws ServiceException {
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int count = -1;
        try {
            count = livreDao.count();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
        }
        return count;
    }
}