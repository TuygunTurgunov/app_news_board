package uz.pdp.uz.app_news_board.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.uz.app_news_board.entity.Role;
import uz.pdp.uz.app_news_board.entity.Status;
import uz.pdp.uz.app_news_board.entity.User;
import uz.pdp.uz.app_news_board.entity.enums.RoleName;
import uz.pdp.uz.app_news_board.entity.enums.StatusName;
import uz.pdp.uz.app_news_board.repository.RoleRepository;
import uz.pdp.uz.app_news_board.repository.StatusRepository;
import uz.pdp.uz.app_news_board.repository.UserRepository;
import uz.pdp.uz.app_news_board.service.AuthService;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Project run bo'lishi bilan ishga tushadigan class FAQAT application.properties dagi
 *          ba'zi  buyruqlarga bog'lab qoyishimiz kerak , bu class ni faqat bir marta o'qishligi uchun
 */




@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StatusRepository statusRepository;

    /**
     *   Ko'r siklicheskiy exception
     */


    /**
     * spring.sql.init.enabled ==>shuni  application propertiesdagi qiymatini opkeladi
     */
//    @Value(value = "${spring.sql.init.enabled}")
//    boolean runFirstWithDataSql;


    /**
     *spring.jpa.hibernate.ddl-auto ==>shuni  application propertiesdagi qiymatini opkeladi
     */
    @Value(value = "${spring.jpa.hibernate.ddl-auto}")
    String runDDL;



    // runDDL=update and   runFirstWithDataSql=true
            //bo'lganida shu  CommandLineRunner ni override bo'lgan methodi
                    // ishga tushadi va emailga habar ketadi.
    @Override
    public void run(String... args) throws Exception {
        if(runDDL.equals("create")){

    //Role  for users
            Role roleAdmin=new Role();
            roleAdmin.setRoleName(RoleName.ROLE_ADMIN);
            roleRepository.save(roleAdmin);

            Role roleUser=new Role();
            roleUser.setRoleName(RoleName.ROLE_USER);
            roleRepository.save(roleUser);

    //Status for news
            Status statusNew=new Status();
            statusNew.setStatusName(StatusName.STATUS_NEW);
            statusRepository.save(statusNew);

            Status statusNotNew=new Status();
            statusNotNew.setStatusName(StatusName.STATUS_NOT_NEW);
            statusRepository.save(statusNotNew);
 }





        if (runDDL.equals("create")){
            User admin=new User();
            admin.setLogin("admin");
            admin.setAge(44);
            admin.setEmail("murodrasulov1467@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin123"));


            HashSet<Role>set=new HashSet<>();
            Role role = roleRepository.findByRoleName(RoleName.ROLE_ADMIN);


            set.add(role);
            admin.setRoles(set);
            admin.setEmailCode(UUID.randomUUID().toString());
            userRepository.save(admin);

        }
    }
}
