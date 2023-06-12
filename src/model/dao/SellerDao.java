package model.dao;

import entities.Seller;
import java.util.List;

public interface SellerDao {
    void insert(Seller obj);
    void update(Seller obj);
    void deleteByid(Integer id);
    Seller finByid(Integer id);
    List<Seller> findAll();
}
