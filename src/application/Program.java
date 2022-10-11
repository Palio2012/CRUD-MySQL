package application;

import db.DbException;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        SellerDao sellerDao = DaoFactory.createSellerDao();


        try {
            System.out.print("How many operations? ");
            int n = sc.nextInt();
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            for (int i = 0; i < n; i++) {
                System.out.println("Wich operation you want to do? ");
                System.out.println("1 - findById");
                System.out.println("2 - findByDepartment");
                System.out.println("3 - findAll");
                System.out.println("4 - insert");
                System.out.println("5 - update");
                System.out.println("6 - deleteById");
                int operation = sc.nextInt();

                if (operation == 1) {
                    System.out.println("Enter the Id number for search: ");
                    int id = sc.nextInt();
                    Seller seller = sellerDao.findById(id);
                    System.out.println(seller);
                    sc.nextLine();
                } else if (operation == 2) {

                    System.out.println("Enter department Id: ");
                    int id = sc.nextInt();
                    Department department = new Department(id, null);
                    List<Seller> list = sellerDao.findByDepartment(department);

                    for (Seller obj : list) {
                        System.out.println(obj);
                    }
                    sc.nextLine();
                } else if (operation == 3) {

                    List<Seller> list = sellerDao.findAll();

                    for (Seller obj : list) {
                        System.out.println(obj);
                    }
                    sc.nextLine();
                } else if (operation == 4) {

                    System.out.println("Enter the Name, email, birthdate (dd/MM/yyyy), salary and Department Id1");

                    Department department = new Department();

                    sc.nextLine();
                    String name = sc.nextLine();
                    String email = sc.nextLine();
                    Date birthdate = sdf.parse(sc.nextLine());
                    double salary = sc.nextDouble();
                    int DepId = sc.nextInt();
                    department.setId(DepId);

                    Seller newSeller = new Seller(null, name, email, birthdate, salary, new Department(department.getId(), department.getName()));
                    sellerDao.insert(newSeller);
                    System.out.println("Inserted! New id = " + newSeller.getId());

                    sc.nextLine();
                } else if (operation == 5) {

                    System.out.println("Enter the seller Id to update: ");
                    int id = sc.nextInt();
                    Seller seller = sellerDao.findById(id);

                    System.out.println(seller);
                    System.out.println("What to update? ( 1- Name, 2 - Email, 3 - Salary, 4 - Department) ");
                    sc.nextLine();
                    int whatToUpdate = sc.nextInt();

                    if (whatToUpdate == 1) {
                        System.out.print("Enter the new name: ");
                        sc.nextLine();
                        String name = sc.nextLine();
                        seller.setName(name);
                        sellerDao.update(seller);

                    } else if (whatToUpdate == 2) {
                        System.out.print("Enter the new Email: ");
                        sc.nextLine();
                        String email = sc.nextLine();
                        seller.setEmail(email);
                        sellerDao.update(seller);
                    } else if (whatToUpdate == 3) {
                        System.out.print("Enter the new Salary: ");
                        double salary = sc.nextDouble();
                        seller.setSalary(salary);
                        sellerDao.update(seller);
                    } else if (whatToUpdate == 4) {
                        System.out.print("Enter the new Department (Id) : ");
                        Department department = new Department();
                        int DepId = sc.nextInt();
                        String name = sc.nextLine();
                        department.setId(DepId);
                        seller.setDepartment(new Department(department.getId(), department.getName()));
                        sellerDao.update(seller);
                    }
                    System.out.println("Update Completed!");
                    sc.nextLine();
                } else if (operation == 6) {
                    System.out.print("Enter the seller Id to delete: ");
                    int id = sc.nextInt();
                    sellerDao.deleteById(id);
                    System.out.println("Delete completed!");
                    sc.nextLine();
                }
            }
        } catch (InputMismatchException e) {
            throw new DbException("Wrong insert!");
        } catch (ParseException e) {
            e.getMessage();
        }
        sc.close();
    }
}
