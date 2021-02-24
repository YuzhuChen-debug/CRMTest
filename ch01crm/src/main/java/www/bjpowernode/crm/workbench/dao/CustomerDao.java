package www.bjpowernode.crm.workbench.dao;

import www.bjpowernode.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerByname(String company);

    int addNewCustomer(Customer customer);
}
