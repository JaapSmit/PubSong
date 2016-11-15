package nl.pubsong;

import music.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		mainAfspeellijst.voegToe(new Nummer("Andre Hazes", "vlieger", "volkzang"));
		mainAfspeellijst.voegToe(new Nummer("Chain Smokers", "Closer", "pop"));
		mainAfspeellijst.voegToe(new Nummer("Sean Mendez", "Blabla", "country"));
		mainAfspeellijst.voegToe(new Nummer("Kensington", "Do I Ever", "pop"));
		mainAfspeellijst.voegToe(new Nummer("Rolling Stones", "Sympathy For the Devil", "rock"));
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
	
}
