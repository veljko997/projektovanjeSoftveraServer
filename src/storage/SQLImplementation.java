/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storage;

import database.ConnectionFactory;
import domain.Confederation;
import domain.Match;
import domain.Selection;
import domain.User;
import exceptions.DatabaseException;
import exceptions.IllegalDateException;
import exceptions.IllegalGoalsException;
import exceptions.IllegalMatchStateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import services.users.GetMatches;
import staticFactory.ConfederationFactory;
import staticFactory.MatchTypeFactory;

/**
 *
 * @author Veljko
 */
public class SQLImplementation implements DatabaseOperations {

    private final Logger logger = Logger.getLogger(SQLImplementation.class);

    @Override
    public User Login(String username, String password) throws SQLException {
        String upit = "SELECT id,username,password,active,administrator FROM user WHERE username=? AND password=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            return rs.absolute(1) ? new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getBoolean(5)) : null;
        }
    }

    @Override
    public List<Selection> getAllSelections() throws Exception {
        List<Selection> selections = new LinkedList<>();
        String upit = "SELECT name,id,points,confederation,ranking FROM selection WHERE active=TRUE ORDER BY name ASC";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);
                ResultSet rs = preparedStatement.executeQuery();) {

            while (rs.next()) {
                Selection selection = new Selection(rs.getString(1));
                selection.setId(rs.getInt(2));
                selection.setPoints(rs.getInt(3));
                selection.setConfederation(ConfederationFactory.createConfederation(rs.getString(4)));
                selection.setRang(rs.getInt(5));
                selections.add(selection);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        return selections;
    }

    @Override
    public Match getMatch() {
        String upit = "SELECT (host.name,match.hostGoals,away.name,match.awayGoals,mtName,m.id) FROM match m "
                + "JOIN selection host on (host.id=m.idHost)"
                + "JOIN selection away on (away.id=m.idAway) "
                + "JOIN matchType mt on (mt.id=m.matchTypeId)";
        Match match = null;

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);
                ResultSet rs = preparedStatement.executeQuery();) {

            Selection host = new Selection(rs.getString(1));
            Selection away = new Selection(rs.getString(3));
            match = new Match.Builder(host, away).hostGoals(rs.getInt(2)).awayGoals(rs.getInt(4)).build();
            match.setId(rs.getInt(6));
        } catch (SQLException | IllegalGoalsException | IllegalMatchStateException ex) {
            logger.error(ex.getMessage());
        }
        return match;
    }

    @Override
    public boolean deleteSelection(Selection selection) {
        String upit = "DELETE FROM selection WHERE id=?";
        boolean insert = false;
        List<Match> matchs = getHostMatches(selection);
        matchs.addAll(getAwayMatches(selection));

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            for (Match m : matchs) {
                String upitMatch = "DELETE FROM matches WHERE id=?";
                PreparedStatement preparedStatementMatch = connection.prepareCall(upitMatch);
                preparedStatementMatch.setInt(1, m.getId());
                preparedStatementMatch.execute();
            }

            preparedStatement.setInt(1, selection.getId());
            int executed = preparedStatement.executeUpdate();
            connection.commit();
            if (executed == 1) {
                insert = true;
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        return insert;
    }

    @Override
    public boolean deleteMatch(Match match) {
        String upit = "DELETE FROM matches WHERE id=?";
        boolean insert = false;
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
            preparedStatement.setInt(1, match.getId());
            int executed = preparedStatement.executeUpdate();
            connection.commit();
            if (executed == 1) {
                insert = true;
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        return insert;
    }

    @Override
    public boolean deleteUser(User user) {
        String upit = "UPDATE user SET active=false WHERE id=?";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setInt(1, user.getId());
            boolean execute = preparedStatement.execute();
            connection.commit();
            return execute;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public void updateUser(User user) {
        String upit = "UPDATE user SET active=? WHERE id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setBoolean(1, user.isActive());
            preparedStatement.setInt(2, user.getId());
            preparedStatement.execute();
            connection.commit();

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public List<Selection> getAllSelectionsByPoints() throws Exception {
        List<Selection> selections = new LinkedList<>();
        String upit = "SELECT name,id,points,confederation,ranking FROM selection WHERE active=TRUE ORDER by points DESC";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);
                ResultSet rs = preparedStatement.executeQuery();) {

            while (rs.next()) {
                Selection selection = new Selection(rs.getString(1));
                selection.setId(rs.getInt(2));
                selection.setConfederation(ConfederationFactory.createConfederation(rs.getString(4)));
                selection.setPoints(rs.getInt(3));
                selection.setRang(rs.getInt(5));
                selections.add(selection);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
        return selections;
    }

    @Override
    public boolean deactivateSelection(Selection selection) {
        String upit = "UPDATE selection\n"
                + "SET ACTIVE=FALSE\n"
                + "WHERE id=?";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
            preparedStatement.setInt(1, selection.getId());
            int rezultat = preparedStatement.executeUpdate();
            connection.commit();
            return rezultat == 1;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean insertSelection(Selection selection, int id) {
        String upit = "INSERT INTO selection (name,confederation,points,userID,active) values(?,?,?,?,?)";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setString(1, selection.getName());
            preparedStatement.setString(2, selection.getConfederation().name());
            preparedStatement.setInt(3, selection.getPoints());
            preparedStatement.setInt(4, id);
            preparedStatement.setBoolean(5, true);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            connection.commit();
            return true;
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        String upit = "SELECT id,username,active FROM user ORDER BY username asc";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);
                ResultSet rs = preparedStatement.executeQuery();) {

            while (rs.next()) {
                User user = new User(rs.getInt(1), rs.getString(2));
                user.setActive(rs.getBoolean(3));
                users.add(user);
            }

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        return users;
    }

    @Override
    public boolean CheckUser(String username) {
        String upit = "SELECT * FROM user WHERE username=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit)) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.absolute(1);
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    @Override
    public void register(String username, String password) throws SQLException {
        String upit = "INSERT INTO user(username,password,active,administrator)"
                + " VALUES (?,?,?,?)";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setBoolean(3, true);
            preparedStatement.setBoolean(4, false);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<Selection> getRangList() throws DatabaseException {
        String upit = "SELECT id,name,points,confederation FROM selection WHERE active=TRUE ORDER BY points DESC";
        List<Selection> selections = new LinkedList<>();
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Selection selection = new Selection(rs.getString(2));
                selection.setId(rs.getInt(1));
                selection.setPoints(rs.getInt(3));
                selection.setConfederation(Confederation.EUROPE);
                selections.add(selection);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error in server side!");
        }
        return selections;
    }

    @Override
    public List<Match> getHostMatches(Selection selection) {
        List<Match> matches = new LinkedList<>();
        String upit = "SELECT away.name,m.hostGoals,m.awayGoals,m.date,m.matchtype,m.id,away.ranking,away.confederation\n"
                + "FROM selection s \n"
                + "JOIN matches m ON m.host=s.id \n"
                + "JOIN selection away ON m.away=away.id\n"
                + "WHERE s.id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setInt(1, selection.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Selection selection1 = new Selection(rs.getString(1));
                selection1.setConfederation(ConfederationFactory.createConfederation(rs.getString(8)));
                selection1.setRang(rs.getInt(7));
                Match match = new Match.Builder(selection, selection1).matchType(MatchTypeFactory.createMatchType(rs.getString(5))).
                        hostGoals(rs.getInt(2)).awayGoals(rs.getInt(3)).date(rs.getDate(4)).build();
                matches.add(match);
                match.setId(rs.getInt(6));
            }
        } catch (SQLException | IllegalGoalsException | IllegalDateException | IllegalMatchStateException ex) {
            logger.error(ex.getMessage());
        }
        return matches;
    }

    @Override
    public List<Match> getAwayMatches(Selection selection) {
        List<Match> matches = new LinkedList<>();
        String upit = "SELECT host.name,m.hostGoals,m.awayGoals,m.date,m.matchtype,m.id,host.ranking,host.confederation\n"
                + "FROM selection s \n"
                + "JOIN matches m ON m.away=s.id \n"
                + "JOIN selection HOST ON m.host=host.id\n"
                + "WHERE s.id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
            preparedStatement.setInt(1, selection.getId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Selection selection1 = new Selection(rs.getString(1));
                selection1.setConfederation(ConfederationFactory.createConfederation(rs.getString(8)));
                selection1.setRang(rs.getInt(7));
                Match match = new Match.Builder(selection1, selection).matchType(MatchTypeFactory.createMatchType(rs.getString(5))).
                        hostGoals(rs.getInt(2)).awayGoals(rs.getInt(3)).date(rs.getDate(4)).build();
                match.setId(rs.getInt(6));
                matches.add(match);
            }
        } catch (SQLException | IllegalGoalsException | IllegalDateException | IllegalMatchStateException ex) {
            logger.error(ex.getMessage());
        }
        return matches;
    }

    @Override
    public void saveMatch(Match match, Integer id) throws SQLException {
        String upit = "INSERT INTO matches(HOST,away,hostGoals,awayGoals,DATE,matchtype,userID) VALUES  (?,?,?,?,?,?,?)";

        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {

            preparedStatement.setInt(1, match.getHost().getId());
            preparedStatement.setInt(2, match.getAway().getId());
            preparedStatement.setInt(3, match.getHostGoals());
            preparedStatement.setInt(4, match.getAwayGoals());
            preparedStatement.setDate(5, new java.sql.Date(match.getDate().getTime()));
            preparedStatement.setString(6, match.getMatchType().toString());
            preparedStatement.setInt(7, id);
            System.out.println(preparedStatement);
            preparedStatement.execute();
            connection.commit();

        } catch (SQLException ex) {
            throw ex;
        }
    }

    @Override
    public void updatePoints(Selection selection) {
        String upit = "UPDATE selection SET points=? WHERE id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
            preparedStatement.setInt(1, selection.getPoints());
            preparedStatement.setInt(2, selection.getId());
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }

    @Override
    public void updateRanking(Selection selection, int ranking) {
        String upit = "UPDATE selection SET ranking=? WHERE id=?";
        try (Connection connection = ConnectionFactory.getInstance().getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(upit);) {
            preparedStatement.setInt(1, ranking);
            preparedStatement.setInt(2, selection.getId());
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
    }
}
