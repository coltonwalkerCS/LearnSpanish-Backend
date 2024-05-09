//package a.i.learn.spanish.com.learnspanishbackend.config;
//
//public class MvcConfig {
//}
//package a.i.learn.spanish.com.learnspanishbackend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // Note: Adjust the base path to match the absolute path to your React app's build directory
//        String basePath = "file:/Users/colewalker/Desktop/Personal\\ Proj's\\ Fullstack/Spanish\\ Full\\ Stack\\ Project/build/";
//        registry.addResourceHandler("/static/**")
//                .addResourceLocations(basePath + "static/");
//        registry.addResourceHandler("/*.js")
//                .addResourceLocations(basePath);
//        registry.addResourceHandler("/*.json")
//                .addResourceLocations(basePath);
//        registry.addResourceHandler("/*.ico")
//                .addResourceLocations(basePath);
//        registry.addResourceHandler("/index.html")
//                .addResourceLocations(basePath + "index.html");
//    }
//}