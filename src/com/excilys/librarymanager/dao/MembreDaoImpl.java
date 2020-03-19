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
    private static MembreDaoImpl instance;

    private static final String INSERT_QUERY = "INSERT INTO membre (nom, prenom, adresse, email, telephone, abonnement) values (?,?,?,?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM membre WHERE id=?";
    private static final String EDIT_QUERY = "UPDATE membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?";
    private static final String GET_QUERY = "SELECT * FROM membre WHERE id=?";
    private static final String GET_ALL_QUERY = "SELECT * FROM membre";
    private static final String COUNT_QUERY = "SELECT COUNT(*) as quantity FROM membre";
    
    private MembreDaoImpl() {}

    public static MembreDaoImpl getInstance() {
        if(instance == null) {
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Membre> getList() throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_QUERY);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Membre membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("prenom"),
                    res.getString("adresse"), 
                    res.getString("email"), 
                    res.getString("telephone"),
                    Abonnement.valueOf(res.getString("abonnement")) 
                );
				membres.add(membre);
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des membres", e);
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
            preparedStatement = connection.prepareStatement(GET_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
            if(res.next()) {
                membre = new Membre(
                    res.getInt("id"), 
                    res.getString("nom"), 
                    res.getString("prenom"),
                    res.getString("adresse"), 
                    res.getString("email"), 
                    res.getString("telephone"),
                    Abonnement.valueOf(res.getString("abonnement")) 
                );
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du membre: id= " + id, e);
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
            preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, telephone);
            preparedStatement.setString(6, null);

            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du membre.", e);
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
		Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(EDIT_QUERY);

            preparedStatement.setString(1, membre.getNom());
            preparedStatement.setString(2, membre.getPrenom());
            preparedStatement.setString(3, membre.getAdresse());
            preparedStatement.setString(4, membre.getEmail());
            preparedStatement.setString(5, membre.getTelephone());
            preparedStatement.setString(6, membre.getAbonnement().toString());
            preparedStatement.setInt(7, membre.getId());
            
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du membre: " + membre, e);
        } finally {
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
		Connection connection = null;
		PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, id);

            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la suppression du membre: id= " + id, e);
        } finally {
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
            preparedStatement = connection.prepareStatement(COUNT_QUERY);
            res = preparedStatement.executeQuery();
            
            if(res.next()) {
                quantity = res.getInt("quantity");
            }

        } catch (SQLException e) {
            throw new DaoException("Problème lors du comptage des membres", e);
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