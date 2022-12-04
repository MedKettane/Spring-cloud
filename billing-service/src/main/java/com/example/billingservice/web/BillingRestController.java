package com.example.billingservice.web;

import com.example.billingservice.entities.Bill;
import com.example.billingservice.feign.CustomerClient;
import com.example.billingservice.feign.ProductItemClient;
import com.example.billingservice.model.Customer;
import com.example.billingservice.model.Product;
import com.example.billingservice.repositories.BillRepository;
import com.example.billingservice.repositories.ProductItemRepository;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private CustomerClient customerClient;
    private ProductItemClient productItemClient;
    private ProductItemRepository productItemRepository;

    public BillingRestController(BillRepository billRepository, CustomerClient customerClient, ProductItemClient productItemClient, ProductItemRepository productItemRepository) {
        this.billRepository = billRepository;
        this.customerClient = customerClient;
        this.productItemClient = productItemClient;
        this.productItemRepository = productItemRepository;
    }

    @GetMapping(path = "/fullBill/{id}")
    public Bill getBill (@PathVariable(name = "id") Long id){
        Bill bill=billRepository.findById(id).get();
        Customer customer = customerClient.getCustomerById(bill.getCustomerID());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi->{
            Product product=productItemClient.getProductById(pi.getProductID());
            pi.setProductName(product.getName());
        });
        return bill;
    }
}
