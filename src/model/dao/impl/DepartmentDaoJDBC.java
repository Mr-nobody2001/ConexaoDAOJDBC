package model.dao.impl;

import db.BaseDAO;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DepartmentDaoJDBC extends BaseDAO implements DepartmentDao {
    private final Connection CONNECTION;

    public DepartmentDaoJDBC() {
        this.CONNECTION = getConnection();
    }

    @Override
    public void insert(Department obj) {
        ResultSet resultSet = null;

        String sql = "INSERT INTO department\n" +
                "(Name) \n" +
                "VALUES \n" +
                "(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getName());
            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                resultSet = preparedStatement.getGeneratedKeys();
                while (resultSet.next()) {
                    int key = resultSet.getInt(1);
                    obj.setId(key);
                }
            } else {
                throw new dao.DbException("Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new dao.DbException(e.getMessage());
        } finally {
            fecharResultSet(resultSet);
        }
    }

    @Override
    public void update(Department obj) {
        String sql = "UPDATE department \n" +
                "SET Name = ? \n" +
                "WHERE Id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, obj.getName());
            preparedStatement.setInt(2, obj.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new dao.DbException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Integer id) {
        String sql = "DELETE FROM department \n" +
                "WHERE Id = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new dao.DbIntegrityException(e.getMessage());
        }
    }

    @Override
    public Department findById(Integer id) {
        ResultSet resultSet = null;

        Department department = null;

        String sql = "SELECT * FROM department WHERE Id = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            throw new dao.DbException(e.getMessage());
        } finally {
            fecharResultSet(resultSet);
        }

        return department;
    }

    @Override
    public List<Department> findAll() {
        List<Department> listaDepartment = new ArrayList<>();

        ResultSet resultSet = null;

        Department department;

        String sql = "SELECT * FROM department ORDER BY Name";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("Name"));
                listaDepartment.add(department);
            }
        } catch (SQLException e) {
            throw new dao.DbException(e.getMessage());
        } finally {
            fecharResultSet(resultSet);
        }

        return listaDepartment;
    }
}
