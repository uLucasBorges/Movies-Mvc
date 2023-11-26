
package br.com.coding.atividadesenac.services;


import br.com.coding.atividadesenac.models.Filme;
import br.com.coding.atividadesenac.repositories.FilmeRepository;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {
    

  @Autowired
  private FilmeRepository filmeRepository;
  
    public List<Filme> getAllFilmes() throws IOException {
       var filmes = filmeRepository.findAll();
       
       if(!filmes.isEmpty() && filmes != null){
       return filmes;
       }
       
       return new ArrayList<Filme>();
    }

   
    
    public Filme getFilmeById(long filmeId) throws IOException {
             return filmeRepository.findById(filmeId).get();
    }

    public  void createFilme(Filme novoFilme) throws IOException {
        filmeRepository.save(novoFilme);
    }

    public  void updateFilme(Filme filmeAtualizado) throws IOException {
         filmeRepository.save(filmeAtualizado);
    }

    public  void deleteFilme(long filmeId) throws IOException {
      filmeRepository.deleteById(filmeId);
    }

  
    
}
