package com.excilys.librarymanager.dao;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.persistence.ConnectionManager;
import com.excilys.librarymanager.utils.Abonnement;
import com.excilys.librarymanager.model.Membre;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MembreDaoImpl
 */
public class MembreDaoImpl implements MembreDao {


    private static final String InsertQuery = "INSERT INTO membre (nom, prenom, adresse, email, telephone, abonnement) values (?,?,?,?,?,?)";
    private static final String DeleteQuery = "DELETE FROM membre WHERE id=?";
    private static final String EditQuery = "UPDATE membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?";
    private static final String GetQuery = "SELECT * FROM membre WHERE id=?";
    private static final String GetAllQuery = "SELECT * FROM membre";
    private static final String CountQuery = "SELECT COUNT(*) as quantity FROM membre";
    

    @Override
    public List<Membre> getList() throws DaoException {
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
                    res.getString("prenom"),
                    res.getString("adresse"), 
                    res.getString("email"), 
                    res.getString("telephone"),
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
    public Membre getById(int id) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        Membre membre = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetQuery);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();

            membre = new Membre(
                res.getInt("id"), 
                res.getString("nom"), 
                res.getString("prenom"),
                res.getString("adresse"), 
                res.getString("email"), 
                res.getString("telephone"),
                (Abonnement.values()[res.getInt("abonnement")]) 
            );
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
        return membre;
    }

    @Override
    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(InsertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            


            res = preparedStatement.getGeneratedKeys();
            id = res.getInt(1);
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
        return id;
    }

    @Override
    public void update(Membre membre) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(EditQuery);

            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setInt(6, (membre.getAbonnement().getVal()));

            res = preparedStatement.executeQuery();
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

    }

    @Override
    public void delete(int id) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DeleteQuery);
            preparedStatement.setInt(1, id);

            res = preparedStatement.executeQuery();
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

    }

    @Override
    public int count() throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        int quantity = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(CountQuery);
            res = preparedStatement.executeQuery();
            quantity = res.getInt("quantity");

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
        return quantity;
    }

    
}