
package br.com.coding.atividadesenac.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Table;

@Entity
@Table(schema = "senac", name = "analise")
public class Analise {
    @Id
    public long id;
    public String filme;
    public String analise;
    public int nota;
    public long filmeId;

     public Analise(){}

    
    public Analise(long id, String filme, String analise, int nota, long filmeId) {
        this.id = id;
        this.filme = filme;
        this.analise = analise;
        this.nota = nota;
        this.filmeId = filmeId;
    }
    
       public long getFilmeId(){
    return filmeId;
    }
       // Método de modificação (setter) para o campo 'id'
    public void setFilmeId(long id) {
        this.filmeId = id;
    }
      // Método de acesso (getter) para o campo 'id'
    public long getId() {
        return id;
    }

    // Método de modificação (setter) para o campo 'id'
    public void setId(long id) {
        this.id = id;
    }

    // Método de acesso (getter) para o campo 'filme'
    public String getFilme() {
        return filme;
    }

    // Método de modificação (setter) para o campo 'filme'
    public void setFilme(String filme) {
        this.filme = filme;
    }

    // Método de acesso (getter) para o campo 'analise'
    public String getAnalise() {
        return analise;
    }

    // Método de modificação (setter) para o campo 'analise'
    public void setAnalise(String analise) {
        this.analise = analise;
    }

    // Método de acesso (getter) para o campo 'nota'
    public int getNota() {
        return nota;
    }

    // Método de modificação (setter) para o campo 'nota'
    public void setNota(int nota) {
        this.nota = nota;
    }
    
     
}
