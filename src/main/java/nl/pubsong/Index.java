package nl.pubsong;

import nl.pubsong.music.Afspeellijst;
import nl.pubsong.music.BigListRepository;
import nl.pubsong.music.BigListSqlInjector;
import nl.pubsong.music.Nummer;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Index {
	
	@RequestMapping("/")
	public String indexPagina(HttpServletRequest request) {
		// hier komt dus logica
		HttpSession session = request.getSession(false);
		if(session == null){
			return "redirect:/login";
		} else {
			return "redirect:/home";
		}
	}
	
	@RequestMapping("/login") 
	public String loginPagina() {
		return "loginpage";
	}

	@RequestMapping(value="/login", method=RequestMethod.POST) 
	public String loginPaginaPost(HttpServletRequest request) {
		//Tijdelijke nummerlijst, later database
		Afspeellijst mainAfspeellijst = new Afspeellijst();
		// Einde
		
		// logica voor correcte inlogmethode, voor nu alles goed
		HttpSession session = request.getSession();
		session.setAttribute("mainAfspeellijst", mainAfspeellijst);
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String homePagina() {
		
		return "homepage";
	}
	
	@RequestMapping(value="/homeZoek", method=RequestMethod.POST) 
	public String homeZoek() {
		// logica en attributen zetten, dus de lijst  vullen en laten zien.
		return "homepage";
	}
	
	@RequestMapping(value="/homeVoegToe", method=RequestMethod.POST) 
	public String homeVoegToe() {
		// logica het nummer aan de speellijst
		return "homepage";
	}
	
	@Autowired
	private BigListRepository repo;
	
	@RequestMapping("/musicinput")
	public @ResponseBody String musicinput() {
		ArrayList<Nummer> tmpMusicList = new ArrayList<>();
		tmpMusicList = BigListSqlInjector.musiclist();
		for(Nummer n : tmpMusicList) {
			n.getArtiest();
			repo.save(n);
		}
		return "gelukt";
	}
}
