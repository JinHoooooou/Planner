package com.kh.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate_Sim2 {

  public int executeUpdate(String query, Object... parameters) {
	int result = 0;
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(query);
      for (int i = 0; i < parameters.length; i++) {
        statement.setObject(i + 1, parameters[i]);
      }

      result = statement.executeUpdate();
      
      if (result > 0) {
        connection.commit();
      } else {
        connection.rollback();
      }
    } catch (SQLException e) {
      throw new DataAccessException(e);
    } 
    return result;
    
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
}
