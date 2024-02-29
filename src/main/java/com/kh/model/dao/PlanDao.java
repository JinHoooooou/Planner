package com.kh.model.dao;

import com.kh.database.ConnectionManager;
import com.kh.model.vo.Plan;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlanDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(PlanDao.class);

  public void create(String title, LocalDate startDate, LocalDate endDate) {
    if (title.isEmpty()) {
      throw new IllegalArgumentException("invalid title");
    }
    String sql = "insert into plan(id, title, start_date, end_date) "
        + "values (plan_seq.nextval, ?, ?, ?)";
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, title);
      statement.setDate(2, Date.valueOf(startDate));
      statement.setDate(3, Date.valueOf(endDate));

      int result = statement.executeUpdate();
      if (result > 0) {
        connection.commit();
      } else {
        connection.rollback();
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }
  }

  public Plan findById(int planId) {
    String sql = "select * from plan where id = ?";
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setInt(1, planId);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        return Plan.builder()
            .id(resultSet.getInt("id"))
            .title(resultSet.getString("title"))
            .build();
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }

    throw new IllegalArgumentException("invalid plan id");
  }

  public List<Plan> findAll() {
    String sql = "select * from plan";
    List<Plan> result = new ArrayList<>();
    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        result.add(Plan.builder()
            .id(resultSet.getInt("id"))
            .title(resultSet.getString("title"))
            .startDate(resultSet.getDate("start_date").toLocalDate())
            .endDate(resultSet.getDate("end_date").toLocalDate())
            .createDate(resultSet.getDate("create_date").toLocalDate())
            .build());
      }
    } catch (SQLException e) {
      LOGGER.error(e.getMessage());
    }

    return result;
  }

  public Plan update(Plan original, String updateTitle, String updateMemo) {

    if (updateTitle.isEmpty()) {
      throw new IllegalArgumentException("invalid update title");
    }
    String sql = "update plan set title=?, memo=? where id=?";

    try (Connection connection = ConnectionManager.getConnection()) {
      PreparedStatement statement = connection.prepareStatement(sql);
      statement.setString(1, updateTitle);
      statement.setString(2, updateMemo);
      statement.setInt(3, original.getId());

      if (statement.executeUpdate() > 0) {
        connection.commit();
        return Plan.builder().id(original.getId())
            .title(updateTitle)
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

    try (Connection connection = ConnectionManager.getConnection()) {
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
