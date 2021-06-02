package org.kaczucha;

import org.kaczucha.repository.ClientSpringJpaRepository;
import org.kaczucha.repository.entity.Account;
import org.kaczucha.repository.entity.Client;
import org.kaczucha.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//        try (Scanner scanner = new Scanner(System.in)){
//            while(true) {
//                System.out.println("1 - add user");
//                System.out.println("2 - find user");
//                System.out.println("3 - exit app");
//
//                final String next = scanner.next();
//                if (next.equals("1")) {
//                    addUser(scanner);
//                }
//                if (next.equals("2")) {
//                    printUser(scanner);
//                }
//                if (next.equals("3")) {
//                    break;
//                }
//            }
//        }
}
