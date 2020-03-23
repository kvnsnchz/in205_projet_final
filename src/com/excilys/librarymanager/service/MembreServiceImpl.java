package com.excilys.librarymanager.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.dao.MembreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Membre;

/**
 * <b>MembreServiceImpl</b>
 * Member service implementation
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro,sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-20
 */
public class MembreServiceImpl implements MembreService {
    private static MembreServiceImpl instance;

    private MembreServiceImpl() {}

    
    /** 
     * @return MembreService
     */
    public static MembreService getInstance() {
        if(instance == null) {
            instance = new MembreServiceImpl();
        }
        return instance;
    }
    
    
    /** 
     * @return List<Membre>
     * @throws ServiceException
     */
    @Override
    public List<Membre> getList() throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        List<Membre> membres = new ArrayList<>();
        try {
            membres = membreDao.getList();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return membres;
    }

    
    /** 
     * @return List<Membre>
     * @throws ServiceException
     */
    @Override
	public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        List<Membre> membres = getList();

        Iterator<Membre> iter = membres.iterator();
        while (iter.hasNext()) {
            Membre membre = iter.next(); 
            if(!empruntService.isEmpruntPossible(membre)) {
                iter.remove();
            }
        }
        return membres;
    }

    
    /** 
     * @param id
     * @return Membre
     * @throws ServiceException
     */
    @Override
	public Membre getById(int id) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        Membre membre = new Membre();
        try {
            membre = membreDao.getById(id);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return membre;
    }

    
    /** 
     * @param nom
     * @param prenom
     * @param adresse
     * @param email
     * @param telephone
     * @return int
     * @throws ServiceException
     */
    @Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        int i = -1;
        
        if(nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {
			throw new ServiceException("Problème lors de la création du membre: Le nom ou le prénom ne peut pas être vide");
        }

        try {
            i = membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
        return i;
    }

    
    /** 
     * @param membre
     * @throws ServiceException
     */
    @Override
	public void update(Membre membre) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        
        if(membre.getNom() == null ||  membre.getNom().isBlank() || membre.getPrenom() == null || membre.getPrenom().isBlank()) {
            throw new ServiceException("Problème lors de la mis à jour du membre: Le nom ou le prénom ne peut pas être vide");
        }

        try {
            membre.setNom(membre.getNom().toUpperCase());
            membreDao.update(membre);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
    }

    
    /** 
     * @param id
     * @throws ServiceException
     */
    @Override
	public void delete(int id) throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        try {
            membreDao.delete(id);
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
		}
    }

    
    /** 
     * @return int
     * @throws ServiceException
     */
    @Override
	public int count() throws ServiceException {
        MembreDao membreDao = MembreDaoImpl.getInstance();
        int count = -1;
        try {
            count = membreDao.count();
        } catch (DaoException e) {
			System.out.println(e.getMessage());			
        }
        return count;
    }
    
}