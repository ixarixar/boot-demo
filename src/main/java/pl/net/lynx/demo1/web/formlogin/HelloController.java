package pl.net.lynx.demo1.web.formlogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired HelloService helloService;

    @GetMapping("/")
    public String index(){



        return "index";
    }


    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpSession session){
        session.setAttribute("error", getErrorMessage(request,"SPRING_SECURITY_LAST_EXCEPTION"));
        return "login";
    }

    @GetMapping("/register")
    public String login(){
        return "register";
    }

    @PostMapping(
            value = "/register",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = { MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } )
    public String addUser(@RequestParam Map<String, String> body) {
        helloService.addUser(body.get("username"), body.get("password"));
        return "redirect:/";
    }


    private String getErrorMessage(HttpServletRequest request, String key) {
        Exception exception = (Exception) request.getSession().getAttribute(key);
        String error = "";

        if(exception != null) {
//            if (exception instanceof Exception) {
                error = exception.getMessage();
//            }
        }

        return error;
    }




}







