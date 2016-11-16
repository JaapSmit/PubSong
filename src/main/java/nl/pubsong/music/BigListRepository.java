package nl.pubsong.music;

import java.util.*;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param; // deze heb ik toegevoegd

public interface BigListRepository 
extends CrudRepository<Nummer, Long> {
	public ArrayList<Nummer> findByArtiestContainingIgnoreCaseOrTitelContainingIgnoreCase(@Param("naam")String naam,
															@Param("titel") String titel);

}