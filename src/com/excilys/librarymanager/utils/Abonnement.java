package com.excilys.librarymanager.utils;

/**
 * Abonnement
 */
public enum Abonnement {
    BASIC(2),
    PREMIUM(5),
    VIP(20);
 
    public int val;
    
    private Abonnement(int val)
    {
        this.val = val;
    }

    public int getVal () {
        return this.val;
    }
}