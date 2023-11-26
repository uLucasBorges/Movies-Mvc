package br.com.coding.atividadesenac.controllers;

import br.com.coding.atividadesenac.models.Analise;
import br.com.coding.atividadesenac.models.Filme;
import br.com.coding.atividadesenac.repositories.AnaliseRepository;
import br.com.coding.atividadesenac.repositories.FilmeRepository;
import br.com.coding.atividadesenac.services.FilmeService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController

public class FilmeController  extends HttpServlet  {

  
 private final FilmeService filmeService;

 @Autowired
 public FilmeController(FilmeService filmeService, HttpServletResponse response, HttpServletRequest request) {
     this.filmeService = filmeService;
 }

 
    @GetMapping("/tema")
    public ModelAndView alterarTema(HttpServletResponse response, HttpServletRequest request) throws IOException  {
           var tema = verificarEManipularCookie(request, response);
           
           ModelAndView modelAndView = new ModelAndView("filmes"); // Nome da view a ser renderizada (index.html, por exemplo)
      
        
           var filme = filmeService.getAllFilmes();
         

        // Passar os dados para a página HTML

          modelAndView.addObject("filmes", filme);
        
        if(tema){
                    modelAndView.addObject("css","styleDark");

        }
        
        if(!tema){
                    modelAndView.addObject("css","style");

        }
        
        return modelAndView;
       
    }
 
 

  @GetMapping("/")
   public ModelAndView  filmes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
       var filme = filmeService.getAllFilmes();
          
       ModelAndView modelAndView = new ModelAndView("filmes"); // Nome da view a ser renderizada (index.html, por exemplo)

   var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
        // Passar os dados para a página HTML

        modelAndView.addObject("filmes", filme);
          
        return modelAndView;
    }    
      
      
@GetMapping("/detalhes/{id}")
public ModelAndView filmeDetail(@PathVariable("id") long id,HttpServletRequest request, HttpServletResponse response) throws IOException {

   var filme = filmeService.getFilmeById(id);
  
   ModelAndView modelAndView = new ModelAndView("detalhes"); // Nome da view a ser renderizada (index.html, por exemplo)

   
    var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
   

    // Passar os dados para a página HTML
   modelAndView.addObject("filme", filme);
    
   return modelAndView;
}
           
     @GetMapping("/atualizar/{id}")
    public ModelAndView update(@PathVariable("id") long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        
       var filme = filmeService.getFilmeById(id);

       ModelAndView modelAndView = new ModelAndView("atualizar"); // Nome da view a ser renderizada (index.html, por exemplo)
   
       
       var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
   
       // Passar os dados para a página HTML
       modelAndView.addObject("filme", filme);
        
        return modelAndView;  
    }
  
      
       @PostMapping("/atualizar")
    public ModelAndView updateMovie(@ModelAttribute("filme") Filme filme) throws IOException {
        
        filmeService.updateFilme(filme);
       
        ModelAndView modelAndView = new ModelAndView("atualizar"); // Nome da view a ser renderizada (index.html, por exemplo)

           // Passar os dados para a página HTML
           modelAndView.addObject("filme", filme);
           return modelAndView;  

    }
    
     @GetMapping("/create")
    public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws IOException {
     
        ModelAndView modelAndView = new ModelAndView("createfilme"); // Nome da view a ser renderizada (index.html, por exemplo)
      
        
          var tema = verificarCookie(request, response);
           
 
        
        if(!tema){
                    modelAndView.addObject("css","styleDark");

        }else{
                    modelAndView.addObject("css","style");
        }
        
        return modelAndView;  
    }
 
    
    @PostMapping("/createFilm")
    public ModelAndView doPost(HttpServletRequest request) throws ServletException, IOException {
       
        
        // Obtenha os dados do formulário
        String titulo = request.getParameter("titulo");
        String sinopse = request.getParameter("sinopse");
        String genero = request.getParameter("genero");
        String dataLancamento = request.getParameter("dataLancamento");

        Filme filme = new Filme(0, titulo,sinopse,genero,dataLancamento);

        filmeService.createFilme(filme);
        
        ModelAndView modelAndView = new ModelAndView("createfilme"); // Nome da view a ser renderizada (index.html, por exemplo)

        
        return modelAndView;

     
    }


     @PostMapping("/apagar/{id}")
     public ModelAndView filmeAnalisee(@PathVariable("id") long id) throws IOException {
          filmeService.deleteFilme((long) id);

          var filme = filmeService.getAllFilmes();
      
          ModelAndView modelAndView = new ModelAndView("filmes"); // Nome da view a ser renderizada (index.html, por exemplo)

          
         // Passar os dados para a página HTML
          modelAndView.addObject("filmes", filme);
    
           return modelAndView;
   }
 
 

   public boolean verificarEManipularCookie(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();

        // Verifica se o cookie 'theme' existe
        boolean cookieExiste = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("theme")) {
                    // Se existe, apaga o cookie
                    cookieExiste = true;
                    cookie.setMaxAge(0); // Define o tempo de vida do cookie como 0 para excluí-lo
                    response.addCookie(cookie);
                    System.out.println("Cookie 'theme' apagado");
                }
            }
        }

        // Se o cookie não existir, cria ele
        if (!cookieExiste) {
            Cookie novoCookie = new Cookie("theme", "black"); // Substitua 'seuValorAqui' pelo valor desejado
            novoCookie.setMaxAge(24 * 60 * 60); // Tempo de vida do cookie em segundos (1 dia neste exemplo)
            response.addCookie(novoCookie);
            System.out.println("Novo cookie 'theme' criado");
        }
        
        return cookieExiste;
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
