
package br.com.coding.atividadesenac.controllers;

import br.com.coding.atividadesenac.models.Analise;
import br.com.coding.atividadesenac.services.AnaliseService;
import br.com.coding.atividadesenac.services.FilmeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class AnaliseController extends HttpServlet {
   
    
 private final AnaliseService analiseService;
 private final FilmeService filmeService;

 @Autowired
 public AnaliseController(AnaliseService analiseService, FilmeService filmeService) {
     this.analiseService = analiseService;
     this.filmeService = filmeService;
 }

 
 
  @GetMapping("/analise/{filmeid}")
public ModelAndView  filmeAnalise(@PathVariable("filmeid") long filmeid, HttpServletRequest request, HttpServletResponse response) throws IOException {
    //criar variavel com a lista de lista 
    var filme = filmeService.getFilmeById(filmeid);

    // Passar os dados para a página HTML
   
    ModelAndView modelAndView = new ModelAndView("createAnalise"); // Nome da view a ser renderizada (index.html, por exemplo)
     
    
      var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
       
    
    modelAndView.addObject("film", filme);

    return modelAndView;
}
    


  @GetMapping("/avaliacoes/{filmeid}")
public ModelAndView analisesByFilme(@PathVariable("filmeid") long filmeid, HttpServletRequest request, HttpServletResponse response) throws IOException {
  
   ModelAndView modelAndView = new ModelAndView("analises"); // Nome da view a ser renderizada (index.html, por exemplo)

          // Recupere apenas as análises para o filme desejado
   List<Analise> analises = analiseService.getAnalisesByFilmeId(filmeid);

    var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
   
    modelAndView.addObject("analises",   analises);

    return modelAndView;
}


    

   @PostMapping("/analise/create")
    public ModelAndView createAnalise(HttpServletRequest request,HttpServletResponse response,  Model model) throws ServletException, IOException {
       
        
        // Obtenha os dados do formulário
        String tituloFilme = request.getParameter("titulo");
        String analiseFilme = request.getParameter("analise");
        var nota =  Integer.parseInt(request.getParameter("nota"));       
        var filmeCodigo =  Long.parseLong(request.getParameter("filmeid"));

        var filme = filmeService.getFilmeById(filmeCodigo);
        
        ModelAndView modelAndView = new ModelAndView("createAnalise"); // Nome da view a ser renderizada (index.html, por exemplo)

          var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
   
          modelAndView.addObject("film", filme);

        var analise = new Analise(0, tituloFilme, analiseFilme, nota, filmeCodigo);
        
        analiseService.createAnalise(analise);
           
        
        
        return modelAndView;
 
               
     
    }
      
    
   
    
    @PostMapping("/analise/atualizar")
    public ModelAndView updateAnalise(HttpServletRequest request, HttpServletResponse response,  Model model) throws ServletException, IOException {
       Long id = Long.valueOf(request.getParameter("id"));
        // Obtenha os dados do formulário
        String filme = request.getParameter("filme"); 
        String analiseFilme = request.getParameter("analise");
        var nota =  Integer.parseInt(request.getParameter("nota")); 
       Long filmeid = Long.valueOf(request.getParameter("filmeId"));

        var analise = new Analise(id, filme, analiseFilme, nota, filmeid);
         
        ModelAndView modelAndView = new ModelAndView("atualizarAnalise"); // Nome da view a ser renderizada (index.html, por exemplo)

        
                     var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
        
        
        modelAndView.addObject("analise", analise);

        

        analiseService.updateAnalise(analise);
        
        return modelAndView;

    }

     @PostMapping("/analise/apagar/{id}/{filmeid}")
     public ModelAndView filmeAnalisee(@PathVariable("id") long id,@PathVariable("filmeid") long filmeid, HttpServletRequest request, HttpServletResponse response) throws IOException {
      analiseService.deleteAnalise((long) id);

         var analises = analiseService.getAllAnalises().stream().filter(x -> x.filmeId == filmeid).toList();
        
         ModelAndView modelAndView = new ModelAndView("analises"); // Nome da view a ser renderizada (index.html, por exemplo)

                      var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
        
         
        // Passar os dados para a página HTML
        modelAndView.addObject("analises", analises);
 
        return modelAndView;
   }
 
     
    @GetMapping("/analise/atualizar/{id}")
    public ModelAndView update(@PathVariable("id") long id, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        
       var analise = analiseService.getAnaliseById(id);
       
   
       ModelAndView modelAndView = new ModelAndView("atualizarAnalise"); // Nome da view a ser renderizada (index.html, por exemplo)

          var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
       
       // Passar os dados para a página HTML
       modelAndView.addObject("analise", analise);
        
        return modelAndView;  
    }
  
      

    
      
    
    public boolean verificarCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();

        // Verifica se o cookie 'theme' existe
        boolean cookieExiste = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("theme")) {
                    cookieExiste = true;
                }
            }
        }

        return cookieExiste;
    }

}
