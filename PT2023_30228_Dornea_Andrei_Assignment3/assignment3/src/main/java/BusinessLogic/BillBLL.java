package BusinessLogic;

import DataAccess.BillDAO;
import Model.Bill;

import java.util.List;
import java.util.NoSuchElementException;

public class BillBLL {
    private BillDAO billDAO;

    public BillBLL(){
        billDAO=new BillDAO();
    }

    /**
     * metoda insereaza in baaza de date o linie pentru Bill
     * @param bill
     */
    public void insertOrderBill(Bill bill) {
        billDAO.addBill(bill.getIdOrder(), bill.getQuantity(), bill.getTotal());
    }

    /**
     * Metoda returneaza o lista cu toate facturile din tabela
     * @return billList
     */

    public List<Bill> findAllBill() {
        List<Bill> billList = billDAO.findAll();
        if (billList == null) {
            throw new NoSuchElementException("There are no orders in the bill table!");
        }
        return billList;
    }
}
