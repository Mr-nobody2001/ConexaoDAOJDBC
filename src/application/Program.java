package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        Department department = new Department(2, null);
        List<Seller> listSeller = sellerDao.findAll();

        for (Seller temp : listSeller) {
            System.out.println(temp);
        }
    }
}
