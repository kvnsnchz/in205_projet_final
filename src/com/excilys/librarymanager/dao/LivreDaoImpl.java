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

    private static final String InsertQuery = "INSERT INTO livre (titre, auteur, isbn) values (?,?,?)";
    private static final String DeleteQuery = "DELETE FROM livre WHERE id=?";
    private static final String EditQuery = "UPDATE livre SET nom=?, titre=?, auteur=?, isbn=? WHERE id=?";
    private static final String GetQuery = "SELECT * FROM livre WHERE id=?";
    private static final String GetAllQuery = "SELECT * FROM livre";
    private static final String CountQuery = "SELECT COUNT(*) as quantity FROM livre";

    @Override
    public List<Livre> getList() throws DaoException {
        List<Livre> livres = new ArrayList<>();
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GetAllQuery);
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
            preparedStatement = connection.prepareStatement(GetAllQuery);
            res = preparedStatement.executeQuery();
			
            livre = new Livre(
                res.getInt("id"), 
                res.getString("titre"),
                res.getString("auteur"), 
                res.getString("isbn")
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
            preparedStatement = connection.prepareStatement(GetAllQuery);
            preparedStatement = connection.prepareStatement(InsertQuery, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, titre);
            preparedStatement.setString(2, auteur);
            preparedStatement.setString(3, isbn);
            
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
    public void update(Livre livre) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(EditQuery);

            preparedStatement.setString(1, livre.getTitre());
            preparedStatement.setString(2, livre.getAuteur());
            preparedStatement.setString(3, livre.getIsbn());

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
            res = preparedStatement.executeQuery();
            preparedStatement.setInt(1, id);
            
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