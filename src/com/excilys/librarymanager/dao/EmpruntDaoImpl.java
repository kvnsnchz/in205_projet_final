package com.excilys.librarymanager.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.persistence.ConnectionManager;
import com.excilys.librarymanager.model.Emprunt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * EmpruntDaoImpl
 */
public class EmpruntDaoImpl implements EmpruntDao {

    private static final String InsertQuery = "INSERT INTO emprunt (membre, livre, dateEmprunt, dateRetour) values (?,?,?,?)";
    private static final String DeleteQuery = "DELETE FROM emprunt WHERE id=?";
    private static final String EditQuery = "UPDATE emprunt SET membre=?, livre=?, dateEmprunt=?, dateRetour=? WHERE id=?";
    private static final String GetQuery = "SELECT * FROM emprunt WHERE id=?";
    private static final String GetAllQuery = "SELECT * FROM emprunt";
    private static final String CountQuery = "SELECT COUNT(*) as quantity FROM emprunt";

    @Override
    public List<Emprunt> getList() throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
            MembreDaoImpl membreDaoImpl = new MembreDaoImpl();
            LivreDaoImpl livreDaoImpl = new LivreDaoImpl();
			
			while(res.next()) {
				Emprunt emprunt = new Emprunt(
                    res.getInt("id"), 
                    membreDaoImpl.getById(res.getInt("membre")),
                    livreDaoImpl.getById(res.getInt("livre")),
                    LocalDate.parse(res.getString("dateEmprunt")),
                    LocalDate.parse(res.getString("dateRetour"))
                );

				emprunts.add(emprunt);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return emprunts;
    }

    @Override
    public List<Emprunt> getListCurrent() throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(]GetQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;
        return null;
    }

    @Override
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;
        return null;
    }

    @Override
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;
        return null;
    }

    @Override
    public Emprunt getById(int id) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;
        return null;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;

    }

    @Override
    public void update(Emprunt emprunt) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;

    }

    @Override
    public int count() throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("membre"),
                    res.getString("livre"), 
                    res.getString("dateEmprunt"), 
                    res.getString("dateRetour"),
                    (Abonnement.values()[res.getInt("abonnement")]) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException(e.getMessage());
        } finally {
            try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

        }
        return membres;
        return 0;
    }

    
}