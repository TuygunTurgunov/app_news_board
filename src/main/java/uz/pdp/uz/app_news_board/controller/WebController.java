//package uz.pdp.uz.app_news_board.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import uz.pdp.uz.app_news_board.entity.User;
//import uz.pdp.uz.app_news_board.repository.UserRepository;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@Controller
//public class WebController {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @GetMapping
//    public String index(Model model){
//        Optional<User> optionalUser = userRepository.findByEmail("murodrasulov1467@gmail.com");
//        if (optionalUser.isPresent())
//            model.addAttribute("user",optionalUser.get());
//        else
//            model.addAttribute("user",null);
//
//        return "index";
//
//    }
//
//
////    @GetMapping
////    public String index(Model model){
////        model.addAttribute("allUsers",authService.getAllUsers());
////        return "auth/index";
////    }
////
////    @GetMapping("/{id}")
////    public String show(@PathVariable UUID id, Model model ){
////        model.addAttribute("oneUser",authService.getOneUser(id));
////        return "auth/show";
////    }
//
//
//
//
//}
