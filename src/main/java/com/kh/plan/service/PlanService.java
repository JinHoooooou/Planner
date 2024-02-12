package com.kh.plan.service;

import com.kh.database.JdbcTemplate;
import com.kh.plan.model.vo.Plan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlanService {

  public Plan create(String title, String memo) {
    if (title.isEmpty()) {
      throw new IllegalArgumentException("invalid title");
    }
    String sql = "insert into plan(title, memo) values (?, ?)";

    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql,
          Statement.RETURN_GENERATED_KEYS);
      statement.setString(1, title);
      statement.setString(2, memo);

      int result = statement.executeUpdate();
      ResultSet id = statement.getGeneratedKeys();

      if (result > 0 && id.next()) {
        connection.commit();
        return Plan.builder()
            .id(id.getInt(1))
            .title(title)
            .memo(memo).build();
      } else {
        connection.rollback();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    throw new IllegalArgumentException("something wrong");
  }

  public Plan findById(int planId) {
    String sql = "select * from plan where id = ?";
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, planId);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return Plan.builder()
            .id(resultSet.getInt("id"))
            .title(resultSet.getString("title"))
            .memo(resultSet.getString("memo"))
            .timerCount(resultSet.getInt("timercount"))
            .clear(resultSet.getBoolean("clear")).build();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    throw new IllegalArgumentException("invalid plan id");
  }

  public List<Plan> findAll() {
    String sql = "select * from plan";
    List<Plan> result = new ArrayList<>();
    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        result.add(Plan.builder()
            .id(resultSet.getInt("id"))
            .title(resultSet.getString("title"))
            .memo(resultSet.getString("memo"))
            .timerCount(resultSet.getInt("timercount"))
            .clear(resultSet.getBoolean("clear")).build()
        );
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  public Plan update(Plan original, String updateTitle, String updateMemo) {

    if (updateTitle.isEmpty()) {
      throw new IllegalArgumentException("invalid update title");
    }
    String sql = "update plan set title=?, memo=? where id=?";

    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, updateTitle);
      statement.setString(2, updateMemo);
      statement.setInt(3, original.getId());

      if (statement.executeUpdate() > 0) {
        connection.commit();
        return Plan.builder().id(original.getId())
            .timerCount(original.getTimerCount())
            .clear(original.isClear())
            .title(updateTitle)
            .memo(updateMemo)
            .build();
      } else {
        connection.rollback();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    throw new IllegalArgumentException("something wrong");
  }

  public boolean delete(Plan target) {
    String sql = "delete from plan where id=?";

    try (Connection connection = JdbcTemplate.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, target.getId());

      int result = statement.executeUpdate();
      if (result > 0) {
        connection.commit();
        return true;
      } else {
        connection.rollback();
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    throw new IllegalArgumentException("something wrong");
  }
}
