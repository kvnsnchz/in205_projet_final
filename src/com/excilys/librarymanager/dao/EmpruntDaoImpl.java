package com.excilys.librarymanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.persistence.ConnectionManager;
import com.excilys.librarymanager.utils.Abonnement;

/**
 * EmpruntDaoImpl
 */
public class EmpruntDaoImpl implements EmpruntDao {
    private static EmpruntDaoImpl instance;

    private static final String GET_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String GET_CURRENTS_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private static final String GET_CURRENTS_MEM_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id = ?;";
	private static final String GET_CURRENTS_LIV_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id = ?;";
	private static final String GET_QUERY = "SELECT e.id AS idEmprunt, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id = ?;";
	private static final String INSERT_QUERY = "INSERT INTO Emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String EDIT_QUERY = "UPDATE Emprunt SET idMembre=?, idLivre=?,dateEmprunt=?, dateRetour=? WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt;";

    private EmpruntDaoImpl() {}

    public static EmpruntDaoImpl getInstance() {
        if(instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Emprunt> getList() throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_QUERY);
            res = preparedStatement.executeQuery();
            MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
            LivreDaoImpl livreDaoImpl = LivreDaoImpl.getInstance();
			
			while(res.next()) {
				Emprunt emprunt = new Emprunt(
                    res.getInt("id"), 
                    membreDaoImpl.getById(res.getInt("membre")),
                    livreDaoImpl.getById(res.getInt("livre")),
                    res.getDate("dateEmprunt").toLocalDate(),
                    res.getDate("dateRetour").toLocalDate()
                );

				emprunts.add(emprunt);
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste des emprunts", e);
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
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_CURRENTS_QUERY);
            res = preparedStatement.executeQuery();
            MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
            LivreDaoImpl livreDaoImpl = LivreDaoImpl.getInstance();

			while(res.next()) {
				Emprunt emprunt = new Emprunt(
                    res.getInt("id"), 
                    membreDaoImpl.getById(res.getInt("membre")),
                    livreDaoImpl.getById(res.getInt("livre")),
                    res.getDate("dateEmprunt").toLocalDate(),
                    res.getDate("dateRetour").toLocalDate()
                );

				emprunts.add(emprunt);
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste actuelle des emprunts", e);
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
    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_CURRENTS_MEM_QUERY);
            preparedStatement.setInt(1, idMembre);
            res = preparedStatement.executeQuery();
			MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
            LivreDaoImpl livreDaoImpl = LivreDaoImpl.getInstance();

			while(res.next()) {
				Emprunt emprunt = new Emprunt(
                    res.getInt("id"), 
                    membreDaoImpl.getById(res.getInt("membre")),
                    livreDaoImpl.getById(res.getInt("livre")),
                    res.getDate("dateEmprunt").toLocalDate(),
                    res.getDate("dateRetour").toLocalDate()
                );

				emprunts.add(emprunt);
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste actuelle des emprunts du membre: id=" + idMembre, e);
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
    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Emprunt> emprunts = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_CURRENTS_LIV_QUERY);
            preparedStatement.setInt(1, idLivre);
            res = preparedStatement.executeQuery();
            MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
            LivreDaoImpl livreDaoImpl = LivreDaoImpl.getInstance();

			while(res.next()) {
				Emprunt emprunt = new Emprunt(
                    res.getInt("id"), 
                    membreDaoImpl.getById(res.getInt("membre")),
                    livreDaoImpl.getById(res.getInt("livre")),
                    res.getDate("dateEmprunt").toLocalDate(),
                    res.getDate("dateRetour").toLocalDate()
                );

				emprunts.add(emprunt);
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération de la liste actuelle des emprunts du livre: id=" + idLivre, e);
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
    public Emprunt getById(int id) throws DaoException {
        ResultSet res = null;
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        Emprunt emprunt = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(GET_QUERY);
            preparedStatement.setInt(1, id);
            res = preparedStatement.executeQuery();
			MembreDaoImpl membreDaoImpl = MembreDaoImpl.getInstance();
            LivreDaoImpl livreDaoImpl = LivreDaoImpl.getInstance();

			if(res.next()) {
				emprunt = new Emprunt(
                    res.getInt("id"), 
                    membreDaoImpl.getById(res.getInt("membre")),
                    livreDaoImpl.getById(res.getInt("livre")),
                    res.getDate("dateEmprunt").toLocalDate(),
                    res.getDate("dateRetour").toLocalDate()
                );
			}

        } catch (SQLException e) {
            throw new DaoException("Problème lors de la récupération du emprunt: id= " + id, e);
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

        return emprunt;
    }

    @Override
    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(INSERT_QUERY);
            
            preparedStatement.setInt(1, idMembre);
            preparedStatement.setInt(2, idLivre);
            preparedStatement.setDate(3, Date.valueOf(dateEmprunt));
            preparedStatement.setDate(4, null);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la création du emprunt.", e);
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
    public void update(Emprunt emprunt) throws DaoException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<Membre> membres = new ArrayList<>();
        try {
            connection = ConnectionManager.getConnection();
            preparedStatement = connection.prepareStatement(EDIT_QUERY);
            preparedStatement.setInt(1, emprunt.getMembre().getId());
            preparedStatement.setInt(2, emprunt.getLivre().getId());
            preparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            preparedStatement.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            preparedStatement.setInt(5, emprunt.getId());
            
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Problème lors de la mise à jour du emprunt: " + emprunt, e);
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
            throw new DaoException("Problème lors du comptage des emprunts", e);
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