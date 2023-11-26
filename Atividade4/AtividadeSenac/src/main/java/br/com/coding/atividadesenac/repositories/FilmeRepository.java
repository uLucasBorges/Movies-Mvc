
package br.com.coding.atividadesenac.repositories;

import br.com.coding.atividadesenac.models.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository  extends JpaRepository <Filme, Long>{

}
