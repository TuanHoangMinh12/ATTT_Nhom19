package nhom19.hcmuaf.services;


import nhom19.hcmuaf.dao.ProductDao;
import nhom19.hcmuaf.dao.ProductDaoImpl;

public class DeleteProductServiceForAdmin {
    private static DeleteProductServiceForAdmin instance;
    private DeleteProductServiceForAdmin() {
    }

    public static DeleteProductServiceForAdmin getInstance() {
        if (instance == null) {
            instance = new DeleteProductServiceForAdmin();
        }
        return instance;
    }
    public void deleteProduct(int idProduct) {
        ProductDao dao = new ProductDaoImpl();
        dao.deleteProduct(idProduct);
    }
}
