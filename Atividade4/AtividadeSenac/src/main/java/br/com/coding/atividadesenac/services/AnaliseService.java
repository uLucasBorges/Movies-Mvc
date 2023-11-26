
package br.com.coding.atividadesenac.services;

import br.com.coding.atividadesenac.models.Analise;
import br.com.coding.atividadesenac.models.Filme;
import br.com.coding.atividadesenac.repositories.AnaliseRepository;
import br.com.coding.atividadesenac.repositories.FilmeRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnaliseService {
    

  @Autowired
  private AnaliseRepository analiseRepository;
  


    public List<Analise> getAllAnalises(){
       var analises = analiseRepository.findAll();
       
       if(!analises.isEmpty() && analises != null){
       return analises;
       }
       
       return new ArrayList<Analise>();
    }
    
    
     public List<Analise> getAnalisesByFilmeId(Long id) throws IOException {
        var analises = analiseRepository.findAnalisesByFilmeId(id);
       
       if(!analises.isEmpty() && analises != null){
       return analises;
       }
       
       return new ArrayList<Analise>();
    }
    

    public Analise getAnaliseById(long filmeId) throws IOException {
      return analiseRepository.findById(filmeId).get();
    }
    

    

    public  void createAnalise(Analise novaAnalise) throws IOException {
      analiseRepository.save(novaAnalise);
    }

    public  void updateAnalise(Analise analiseAtualizada) throws IOException {
    analiseRepository.save(analiseAtualizada);

    }

    public  void deleteAnalise(long analiseId) throws IOException {
     analiseRepository.deleteById(analiseId);

    }

   

}
