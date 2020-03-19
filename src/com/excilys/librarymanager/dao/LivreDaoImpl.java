package com.excilys.librarymanager.dao;

import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.persistence.ConnectionManager;
import com.excilys.librarymanager.model.Livre;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * LivreDaoImpl
 */
public class LivreDaoImpl implements LivreDao {
    private static LivreDaoImpl instance;
    
    private static final String INSERT_QUERY = "INSERT INTO livre (titre, auteur, isbn) values (?,?,?)";
    private static final String DELETE_QUERY = "DELETE FROM livre WHERE id=?";
    private static final String EDIT_QUERY = "UPDATE livre SET nom=?, titre=?, auteur=?, isbn=? WHERE id=?";
    private static final String GET_QUERY = "SELECT * FROM livre WHERE id=?";
    private static final String GET_ALL_QUERY = "SELECT * FROM livre";
    private static final String COUNT_QUERY = "SELECT COUNT(*) as quantity FROM livre";

    private LivreDaoImpl() {}

    public static LivreDaoImpl getInstance() {
        if(instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Livre> getList() throws DaoException {
        List<Livre> livres = new ArrayList<>();
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_QUERY);
            res = preparedStatement.executeQuery();
			
			while(res.next()) {
				Livre livre = new Livre(
                    res.getInt("id"), 
                    res.getString("titre"),
                    res.getString("auteur"), 
                    res.getString("isbn")
                );
				livres.add(livre);
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des livres", e);
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
        return livres;
    }

    @Override
    public Livre getById(int id) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        Livre livre = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_QUERY);
            res = preparedStatement.executeQuery();
            
            if(res.next()) {
                livre = new Livre(
                    res.getInt("id"), 
                    res.getString("titre"),
                    res.getString("auteur"), 
                    res.getString("isbn")
                );
            }

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du livre: id= " + id, e);
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
        return livre;
    }

    @Override
    public int create(String titre, String auteur, String isbn) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        int id = -1;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);
            
            preparedStatement.executeUpdate();
            res = preparedStatement.getGeneratedKeys();
            if (res.next()) {
                id = res.getInt(1);
            }
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du livre.", e);
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
    public void update(Livre livre) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(EDIT_QUERY);

            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());
            preparedStatement.setInt(4, livre.getId());
           
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du livre: " + livre, e);
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
            throw new DaoException("Problème lors de la suppression du livre: id= " + id, e);
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