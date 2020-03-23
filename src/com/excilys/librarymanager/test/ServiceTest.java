package com.excilys.librarymanager.test;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.exception.ServiceException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.service.EmpruntService;
import com.excilys.librarymanager.service.EmpruntServiceImpl;
import com.excilys.librarymanager.service.LivreService;
import com.excilys.librarymanager.service.LivreServiceImpl;
import com.excilys.librarymanager.service.MembreService;
import com.excilys.librarymanager.service.MembreServiceImpl;

/**
 * ServiceTest
 */
public class ServiceTest {

    public static void main(String[] args) throws ServiceException {
 
        System.out.println("\n--------Livre Service--------\n");
        LivreService livreService = LivreServiceImpl.getInstance();
        
        try {
            int id = livreService.create("", "Javier", "non");
            System.out.println("nouveau livre: id=" + id);   
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

        int id = livreService.create("C++", "Javier", "non");
		System.out.println("nouveau livre: id=" + id);
        
        

        Livre livre = livreService.getById(id);
        System.out.println("Titre: " + livre.getTitre());

        livre.setTitre("Java");
        livreService.update(livre);
        livre = livreService.getById(id);
        System.out.println("Titre mis à jour: " + livre.getTitre());
        
        List<Livre> livres = livreService.getList();
        System.out.println("Taille de list de livres: " + livres.size());
        List<Livre> livresDispo = livreService.getListDispo();
        System.out.println("Taille de list de livres disponibles: " + livresDispo.size());
        
		int count = livreService.count();
		System.out.println("Quantité de livres: " + count);
        
        livreService.delete(livre.getId());
		count = livreService.count();
        System.out.println("Quantité de livres après la suppression: " + count);



        System.out.println("\n--------Membre Service--------\n");
        MembreService membreService = MembreServiceImpl.getInstance();
        try {
            id = membreService.create("Diaz", null, "Massy", "kvn@gmail.com", "1234555");
		    System.out.println("nouveau membre: id=" + id);  
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

        id = membreService.create("Diaz", "Kevin", "Massy", "kvn@gmail.com", "1234555");
		System.out.println("nouveau membre: id=" + id);
        
        Membre membre = membreService.getById(id);
        System.out.println("Nom: " + membre.getNom());
        
        membre.setNom("Sanchez");
        membreService.update(membre);
        membre = membreService.getById(id);
        System.out.println("Nom mis à jour: " + membre.getNom());
        
        List<Membre> membres = membreService.getList();
        System.out.println("Taille de list de membres: " + membres.size());
        List<Membre> membresEmpPos = membreService.getListMembreEmpruntPossible();
        System.out.println("Taille de list de membres qui peut emprunter: " + membresEmpPos.size());

		count = membreService.count();
		System.out.println("Quantité de membres: " + count);

        membreService.delete(membre.getId());
		count = membreService.count();
        System.out.println("Quantité de membres après la suppression: " + count);
        


        System.out.println("\n--------Emprunt Service--------\n");

		EmpruntService empruntService = EmpruntServiceImpl.getInstance();
        
        empruntService.create(1, 1, LocalDate.now());
        Emprunt emprunt = empruntService.getById(1);
        System.out.println("emprunt: id= " + emprunt.getId() + " dateRetour=" + emprunt.getDateEmprunt());
        
        empruntService.returnBook(emprunt.getId());
		emprunt = empruntService.getById(1);
        System.out.println("Emprunt mis à jour: dateRetour=" + emprunt.getDateRetour());
        
        List<Emprunt> emprunts = empruntService.getList();
        System.out.println("Taille de list de emprunts: " + emprunts.size());
        
		emprunts = empruntService.getListCurrent();
        System.out.println("Taille de list de emprunts actuelle: " + emprunts.size());
        
        livre = livreService.getById(1);
		emprunts = empruntService.getListCurrentByLivre(1);
        System.out.println("Taille de list de emprunts actuelle de livre "+ livre.getTitre() +": " + emprunts.size());
        
        membre = membreService.getById(1);
		emprunts = empruntService.getListCurrentByMembre(1);
        System.out.println("Taille de list de emprunts actuelle de membre "+ membre.getNom() +": " + emprunts.size());

		count = empruntService.count();
        System.out.println("Quantité de emprunts: " + count);        
        
    }
}