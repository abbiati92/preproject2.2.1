package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
   public static void main(String[] args) throws SQLException {
      AnnotationConfigApplicationContext context = 
            new AnnotationConfigApplicationContext(AppConfig.class);

      UserService userService = context.getBean(UserService.class);

      User Denis = (new User( "Denis", "Palin", "denis@mail.ru"));
      User Daniil =(new User( "Daniil", "Dubov", "daniil@mail.ru"));
      User Aleksey =(new User("Aleksey", "Mukhin", "aleksey@ mail.ru"));
      User Renat =(new User("Renat", "Azimov", "renat@mail.ru"));

      Car Audi = new Car("Audi", 5);
      Car Mercedes = new Car("Mercedes", 200);
      Car Volkswagen = new Car("Volkswagen", 2);
      Car Toyota = new Car("Toyota", 1);
      Car AudiBad = new Car("AudiBad", 100);

      userService.add(Denis.setCar(Toyota).setUser(Denis));
      userService.add(Daniil.setCar(Audi).setUser(Daniil));
      userService.add(Aleksey.setCar(Mercedes).setUser(Aleksey));
      userService.add(Renat.setCar(Volkswagen).setUser(Renat));

      List<User> users = userService.listUsers();
      for (User user : users) {
         System.out.println("Id = "+user.getId());
         System.out.println("First Name = "+user.getFirstName());
         System.out.println("Last Name = "+user.getLastName());
         System.out.println("Email = "+user.getEmail());
         System.out.println();
      }


      for (User user : userService.listUsers()) {
         System.out.println(user + " " + user.getCar());
      }


      User user = userService.getUserByCar("Toyota", 1);
      System.out.println(user.toString());


      try {
         System.out.println(userService.getUserByCar("Audi", 6));
      } catch (NoResultException e) {
         System.out.println("Пользователь с авто " + AudiBad + " не найден");
      }
      context.close();
   }
}
