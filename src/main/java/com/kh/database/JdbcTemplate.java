package com.kh.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

  public int executeUpdate(String query, Object... parameters) {
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      for (int i = 0; i < parameters.length; i++) {
        statement.setObject(i + 1, parameters[i]);
      }
      return statement.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public int executeUpdate(String query, KeyHolder keyHolder, Object... parameters) {
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
      for (int i = 0; i < parameters.length; i++) {
        statement.setObject(i + 1, parameters[i]);
      }
      int result = statement.executeUpdate();

      ResultSet resultSet = statement.getGeneratedKeys();
      if (resultSet.next()) {
        keyHolder.setId(resultSet.getInt(1));
      }
      resultSet.close();

      return result;
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }

  }

  public <T> List<T> executeQuery(String query, RowMapper<T> mapper, Object... parameters) {
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      for (int i = 0; i < parameters.length; i++) {
        statement.setObject(i + 1, parameters[i]);
      }

      ResultSet resultSet = statement.executeQuery();

      List<T> result = new ArrayList<>();
      while (resultSet.next()) {
        result.add(mapper.mapRow(resultSet));
      }
      return result;
    } catch (SQLException e) {
      throw new DataAccessException(e);
    }
  }

  public <T> T executeQueryForOne(String query, RowMapper<T> mapper, Object... parameters) {
    List<T> result = this.executeQuery(query, mapper, parameters);
    if (result.isEmpty()) {
      return null;
    }
    return result.get(0);
  }

  public int getNextVal(String sequence) {
    String query = String.format("SELECT %s.NEXTVAL FROM DUAL", sequence);
    RowMapper<KeyHolder> mapper = resultSet -> new KeyHolder(resultSet.getInt(1));
    return executeQueryForOne(query, mapper).getId();
  }
}
