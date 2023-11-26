
package br.com.coding.atividadesenac.repositories;

import br.com.coding.atividadesenac.models.Analise;
import br.com.coding.atividadesenac.models.Filme;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnaliseRepository extends JpaRepository <Analise, Long>{
   public List<Analise> findAnalisesByFilmeId(long filmeId);
}
