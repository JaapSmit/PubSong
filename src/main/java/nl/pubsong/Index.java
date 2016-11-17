package nl.pubsong;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.pubsong.music.Afspeellijst;
import nl.pubsong.music.AfspeellijstData;
import nl.pubsong.music.AfspeellijstDataRepository;
import nl.pubsong.music.BigListRepository;
import nl.pubsong.music.BigListSqlInjector;
import nl.pubsong.music.Nummer;
import nl.pubsong.operations.BasicUser;
import nl.pubsong.operations.User;
import nl.pubsong.operations.UserRepository;



@Controller
public class Index {
	
	@Autowired
	private BigListRepository repo;
	
	@Autowired
	private UserRepository repoUser;
	
	@Autowired
	private AfspeellijstDataRepository repoAfspeellijstData;
	
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
	
	// login van een bestaande user
	@RequestMapping(value="/login", method=RequestMethod.POST) 
	public String loginPaginaPost(HttpServletRequest request, String username, String password) {
		
		User user = repoUser.findByuserName(username);
		if(user == null) {
			// geen user gevonden
			return "redirect:/login";
		} else {
			if(user.getPassWord().equals(password)) {
				/*
				// succesvol
				Afspeellijst mainAfspeellijst = new Afspeellijst();
				
				// logica voor correcte inlogmethode, voor nu alles goed
				HttpSession session = request.getSession();
				
				session.setAttribute("mainAfspeellijst", mainAfspeellijst);
				*/
				HttpSession session = request.getSession();
				session.setAttribute("user", user.getId());
				return "redirect:/home";
				
			} else {
				// fout password
				return "redirect:/login";
			}
		}
	}
	
	// aanmaken nieuwe user
	@RequestMapping(value="/newUser", method=RequestMethod.POST) 
	public String loginNewUserPost(HttpServletRequest request, String username, String password) {
		User user = new User();
		user.setUserName(username);
		user.setPassWord(password);
		user.setRights(new BasicUser());
		user.setRightsString("BasicUser");
		HttpSession session = request.getSession();
		session.setAttribute("user", user.getId());
		repoUser.save(user);
		/*
		Afspeellijst mainAfspeellijst = new Afspeellijst();
		HttpSession session = request.getSession();
		session.setAttribute("mainAfspeellijst", mainAfspeellijst);
		*/
		return "redirect:/home";
	}
	
	@RequestMapping(value="/newUser") 
	public String loginNewUser(HttpServletRequest request) {	
		return "newuserpage";
	}
	
	
	@RequestMapping("/home")
	public String homePagina(HttpServletRequest request) {
		// trek de huidige afspeellijst uit de database
		Afspeellijst mainAfspeellijst = new Afspeellijst();
				
		Iterator itr = repoAfspeellijstData.findAll().iterator();
		while(itr.hasNext()) {
			AfspeellijstData data = (AfspeellijstData)itr.next();
			mainAfspeellijst.voegToe(data);
		}
		mainAfspeellijst.sort();
		HttpSession session = request.getSession();
		session.setAttribute("mainAfspeellijst", mainAfspeellijst);
		return "homepage";
	}
	
	@RequestMapping(value="/homeZoek", method=RequestMethod.POST) 
	public String homeZoek(Model model, String zoek) {
		ArrayList<Nummer> alleResultaten = new ArrayList<>();
		// logica en attributen zetten, dus de lijst  vullen en laten zien.
		alleResultaten = repo.findByArtiestContainingIgnoreCaseOrTitelContainingIgnoreCase(zoek, zoek);
		model.addAttribute("alleResultaten", alleResultaten);
		
		return "homepage";
	}
	
	@RequestMapping(value="/homeSelectie") 
	public String homeSelectie(Model model, long id) {
		// logica het nummer aan de speelijst
		model.addAttribute("gekozenNummer", repo.findOne(id));
		return "homepage";
	}
	
	@RequestMapping(value="/homeVoegToe", method=RequestMethod.POST) 
	public String homeVoegToe(HttpServletRequest request, long id) {
		// logica het nummer aan de speellijst
		/*
		HttpSession session = request.getSession();
		Afspeellijst mainAfspeellijst = (Afspeellijst)session.getAttribute("mainAfspeellijst");
		mainAfspeellijst.voegToe(repo.findOne(id));
		session.setAttribute("mainAfspeellijst", mainAfspeellijst);
		*/
		AfspeellijstData afspeellijstData = new AfspeellijstData();
		afspeellijstData.setNummer(repo.findOne(id));
		repoAfspeellijstData.save(afspeellijstData);
		
		return "redirect:/home";
	}
	
	@RequestMapping(value="/upvote", method=RequestMethod.POST)
	public String upvote(HttpServletRequest request, long id) {
		HttpSession session = request.getSession();
		User user = repoUser.findOne((long)session.getAttribute("user"));
		user.getRights().addVote(repoAfspeellijstData.findOne(id));
		repoAfspeellijstData.save(repoAfspeellijstData.findOne(id));
		return "redirect:/home";
	}
	
	
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
