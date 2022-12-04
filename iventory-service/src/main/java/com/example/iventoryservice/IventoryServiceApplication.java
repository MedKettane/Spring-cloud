package com.example.iventoryservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@SpringBootApplication
public class IventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IventoryServiceApplication.class, args);
    }
@Bean
    CommandLineRunner start(ProductRepository productRepository){

        return args -> {
            productRepository.save(new Product(null,"PC",5555,55));
            productRepository.save(new Product(null,"Monitor",5555,55));
            productRepository.save(new Product(null,"Tv",5555,55));
            productRepository.save(new Product(null,"smart phone",5555,55));
            productRepository.findAll().forEach(p -> {
                System.out.println(p.getName());
            });
        };
    }
}


@Entity
@Data @NoArgsConstructor @AllArgsConstructor
class   Product{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private double quantity;
}
@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product,Long>{

}
