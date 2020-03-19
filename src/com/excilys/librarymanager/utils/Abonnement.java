package com.excilys.librarymanager.utils;

/**
 * Abonnement
 */
public enum Abonnement {
    BASIC(0),
    PREMIUM(1),
    VIP(2);

    public int val;
    
    private Abonnement(int val)
    {
        this.val = val;
    }

    public int getVal () {
        return this.val;
    }
}