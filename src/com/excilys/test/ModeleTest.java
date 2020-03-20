package com.excilys.test;

import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.utils.Abonnement;

public class ModeleTest {
    public static void main(String[] argc) {
        Membre membre = new Membre(1, "Sanchez", "Kevin", "Massy", "kvn@gmail.com", "123343444", Abonnement.PREMIUM);
        Livre livre = new Livre(1, "Java", "Javier", "non");
        Emprunt emprunt = new Emprunt(1, membre, livre, null, null);

        System.out.println(emprunt.toString());
    }
}