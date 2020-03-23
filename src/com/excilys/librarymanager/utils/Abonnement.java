package com.excilys.librarymanager.utils;

/**
 * <b>Abonnement</b>
 * Types of subscriptions
 * 
 * @author  Kevin Sanchez <i>[kevin-alexandro.sanchez-diaz@ensta.fr]</i>
 * @version 1.0
 * @since   2020-03-10
 */
public enum Abonnement {
    BASIC(2),
    PREMIUM(5),
    VIP(20);
 
    private int val;
    
    private Abonnement(int val)
    {
        this.val = val;
    }

    public int getVal () {
        return this.val;
    }
}