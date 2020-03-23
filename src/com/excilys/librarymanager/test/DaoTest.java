package com.excilys.librarymanager.test;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.dao.EmpruntDaoImpl;
import com.excilys.librarymanager.dao.LivreDao;
import com.excilys.librarymanager.dao.LivreDaoImpl;
import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.dao.MembreDaoImpl;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;

public class DaoTest {

    public static void main(String[] args) throws DaoException {

        System.out.println("\n--------Livre DAO--------\n");
        LivreDao livreDao = LivreDaoImpl.getInstance();
        int id = livreDao.create("C++", "Javier", "non");
		System.out.println("nouveau livre: id=" + id);
        
        Livre livre = livreDao.getById(id);
        System.out.println("Titre: " + livre.getTitre());

        livre.setTitre("Java");
        livreDao.update(livre);
        livre = livreDao.getById(id);
        System.out.println("Titre mis à jour: " + livre.getTitre());
        
        List<Livre> livres = livreDao.getList();
        System.out.println("Taille de list de livres: " + livres.size());
        
		int count = livreDao.count();
		System.out.println("Quantité de livres: " + count);
        
        livreDao.delete(livre.getId());
		count = livreDao.count();
        System.out.println("Quantité de livres après la suppression: " + count);



        System.out.println("\n--------Membre DAO--------\n");
        MembreDao membreDao = MembreDaoImpl.getInstance();
        
        id = membreDao.create("Diaz", "Kevin", "Massy", "kvn@gmail.com", "1234555");
		System.out.println("nouveau membre: id=" + id);
        
        Membre membre = membreDao.getById(id);
        System.out.println("Nom: " + membre.getNom());
        
        membre.setNom("Sanchez");
        membreDao.update(membre);
        membre = membreDao.getById(id);
        System.out.println("Nom mis à jour: " + membre.getNom());
        
        List<Membre> membres = membreDao.getList();
        System.out.println("Taille de list de membres: " + membres.size());

		count = membreDao.count();
		System.out.println("Quantité de membres: " + count);

        membreDao.delete(membre.getId());
		count = membreDao.count();
        System.out.println("Quantité de membres après la suppression: " + count);
        


        System.out.println("\n--------Emprunt DAO--------\n");

		EmpruntDao empruntDao = EmpruntDaoImpl.getInstance();
        
        empruntDao.create(1, 1, LocalDate.now());
        Emprunt emprunt = empruntDao.getById(1);
        System.out.println("emprunt: id= " + emprunt.getId() + " dateRetour=" + emprunt.getDateEmprunt());
        
        emprunt.setDateRetour(LocalDate.now());
        empruntDao.update(emprunt);
		emprunt = empruntDao.getById(1);
        System.out.println("Emprunt mis à jour: dateRetour=" + emprunt.getDateRetour());
        
        List<Emprunt> emprunts = empruntDao.getList();
        System.out.println("Taille de list de emprunts: " + emprunts.size());
        
		emprunts = empruntDao.getListCurrent();
        System.out.println("Taille de list de emprunts actuelle: " + emprunts.size());
        
        livre = livreDao.getById(1);
		emprunts = empruntDao.getListCurrentByLivre(1);
        System.out.println("Taille de list de emprunts actuelle de livre "+ livre.getTitre() +": " + emprunts.size());
        
        membre = membreDao.getById(1);
		emprunts = empruntDao.getListCurrentByMembre(1);
        System.out.println("Taille de list de emprunts actuelle de membre "+ membre.getNom() +": " + emprunts.size());

		count = empruntDao.count();
        System.out.println("Quantité de emprunts: " + count);        
        
    }   
        
}