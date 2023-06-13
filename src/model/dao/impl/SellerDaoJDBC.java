package model.dao.impl;

import dao.DbException;
import db.BaseDAO;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC extends BaseDAO implements SellerDao {
    private final Connection CONNECTION;

    public SellerDaoJDBC() {
        this.CONNECTION = getConnection();
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteByid(Integer id) {

    }

    @Override
    public Seller finByid(Integer id) {
        ResultSet resultSet = null;

        Department department;
        Seller seller = null;

        String sql = "SELECT seller.*,department.Name as DepName " +
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " +
                "WHERE seller.Id = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                department = instantiateDepartment(resultSet);
                seller = instantiateSeller(resultSet, department);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            fecharResultSet(resultSet);
        }

        return seller;
    }

    @Override
    public List<Seller> findAll() {
        return null;
    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getObject("BirthDate", LocalDate.class));
        obj.setDepartment(dep);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
}
