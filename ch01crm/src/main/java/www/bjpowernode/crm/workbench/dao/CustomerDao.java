package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerDao {
    

    Customer getCustomerByname(String company);

    int addNewCustomer(Customer customer);

    List<String> getCustomerName(String name);

}
