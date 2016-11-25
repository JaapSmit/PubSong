package nl.pubsong;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nl.pubsong.music.Afspeellijst;
import nl.pubsong.music.AfspeellijstData;
import nl.pubsong.music.AfspeellijstDataRepository;
import nl.pubsong.music.BigListRepository;
import nl.pubsong.music.BigListSqlInjector;
import nl.pubsong.music.Nummer;
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
	
	// Mapping voor de admin page, met een beetje logica
	@RequestMapping("/admin")
	public String adminPage(HttpServletRequest request) {
		// beetje beveiliging erin
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("user");
		if(session == null || user == null) {
			// je bent nu dus niet ingelogd return naar loginpage
			return "redirect:/login";
		} else {
			if(user.getRightsString().equals("AdminUser")) {
				return "adminpage";
			} else {
				// je ben nu niet bevoegd voor deze pagina
				return "redirect:/login";
			}
		}
	}
	
	// Zoek een user op naam
	@RequestMapping(value="/findUser", method=RequestMethod.POST)
	public @ResponseBody User findUser(String userNaam){
		return repoUser.findByuserName(userNaam);
	}
	
	// Sla de waardes na het aanpassen op
	@RequestMapping(value="/saveUser", method=RequestMethod.POST)
	public @ResponseBody void saveUser(String userNaam, int userVotes, String userRights){
		User user =  repoUser.findByuserName(userNaam);
		user.setVotes(userVotes);
		user.setRightsString(userRights);
		repoUser.save(user);		
	}
	
	// REST, Get the number one song
	@RequestMapping("/getNumberOneSong")
	public @ResponseBody AfspeellijstData getNumberOneSong(HttpServletRequest request){
		Afspeellijst mainAfspeellijst = new Afspeellijst();
		Iterator itr = repoAfspeellijstData.findAll().iterator();
		while(itr.hasNext()) {
			AfspeellijstData data = (AfspeellijstData)itr.next();
			mainAfspeellijst.voegToe(data);
		}	
		mainAfspeellijst.sort();
		mainAfspeellijst.getAfspeellijst().get(0).setPlaying(true);
		repoAfspeellijstData.save(mainAfspeellijst.getAfspeellijst().get(0));
		return mainAfspeellijst.getAfspeellijst().get(0);
	}
	
	// REST,  verwijder hiermee de huidige song en return de gene met de meeste votes
	@RequestMapping("/getNextSong")
	public @ResponseBody AfspeellijstData deleteFinishedSong(){
		Afspeellijst mainAfspeellijst = new Afspeellijst();
		Iterator itr = repoAfspeellijstData.findAll().iterator();
		while(itr.hasNext()) {
			AfspeellijstData data = (AfspeellijstData)itr.next();
			if(data.isPlaying()) {
				// delete the one who is playing
				repoAfspeellijstData.delete(data);
			} else {
				mainAfspeellijst.voegToe(data);
			}
			
		}	
		mainAfspeellijst.sort();
		mainAfspeellijst.getAfspeellijst().get(0).setPlaying(true);
		repoAfspeellijstData.save(mainAfspeellijst.getAfspeellijst().get(0));
		return mainAfspeellijst.getAfspeellijst().get(0);
	}
	
	// Geen aanwezige url, ga naar login als je niet bent ingelogd, anders home
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
	
	
	// Mapping van de loginpage
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
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				return "redirect:/home";
				
			} else {
				// fout password
				return "redirect:/login";
			}
		}
	}
	
	// Als je op de log uit knop klikt, log je uit en wordt je geredirect naar login page
	@RequestMapping("/logout") 
	public String logoutPagina(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1);
		return "redirect:/login";
	}
	
	// aanmaken nieuwe user
	@RequestMapping(value="/newUser", method=RequestMethod.POST) 
	public String loginNewUserPost(HttpServletRequest request, String username, String password, String email) {
		// check email
		if(email == null || email.equals("")) {
			// Foutieve email
			return "redirect:/newUser";
		}
		email = email.trim();
		EmailValidator ev = EmailValidator.getInstance();
		if(ev.isValid(email) == false) {
			// Foutieve email
			return "redirect:/newUser";
		}
		
		// check of de username al bestaat
		User user = (User)repoUser.findByuserName(username);
		if(user != null) {
			// User bestaat al
			return "redirect:/newUser";
		}
		
		// Als alles goedgegaan is maak een nieuwe user
		user = new User();
		user.setUserName(username);
		user.setPassWord(password);
		user.setRightsString("BasicUser");
		user.setDate();
		user.setVotes(3);
		repoUser.save(user);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		return "redirect:/home";
	}
	
	// Mapping naar de newUserpage
	@RequestMapping(value="/newUser") 
	public String loginNewUser(HttpServletRequest request) {	
		return "newuserpage";
	}
	
	// mapping voor de homepage
	@RequestMapping("/home")
	public String homePagina(HttpServletRequest request) {
		// beetje beveiliging erin
		HttpSession session = request.getSession(false);
		User user = (User)session.getAttribute("user");
		if(session == null || user == null) {
			// je bent nu dus niet ingelogd return naar loginpage
			return "redirect:/login";
		} else {
			// trek de huidige afspeellijst uit de database
			Afspeellijst mainAfspeellijst = new Afspeellijst();
					
			Iterator itr = repoAfspeellijstData.findAll().iterator();
			while(itr.hasNext()) {
				AfspeellijstData data = (AfspeellijstData)itr.next();
				mainAfspeellijst.voegToe(data);
			}
			
			mainAfspeellijst.sort();
			session = request.getSession();
			session.setAttribute("mainAfspeellijst", mainAfspeellijst);
			// logica voor het optellen van de votes
			user = (User)session.getAttribute("user");
			user.getRights().checkVotes(user);
			repoUser.save(user);
			
			return "homepage";
		}
	}
	
	// de refresh die door een ajax commando wordt aangeroepen, ververst de pagina
	@RequestMapping("/refresh")
	public @ResponseBody List<AfspeellijstData> refresh(HttpServletRequest request) {
		// trek de huidige afspeellijst uit de database
		Afspeellijst mainAfspeellijst = new Afspeellijst();
						
		Iterator itr = repoAfspeellijstData.findAll().iterator();
		while(itr.hasNext()) {
			AfspeellijstData data = (AfspeellijstData)itr.next();
			mainAfspeellijst.voegToe(data);
		}
		// voeg nummers toe als het aantal nummers onder de vijf komt
		if(mainAfspeellijst.getSize() < 5) {
			Random r = new Random();
			long id = r.nextInt((int)repo.count());
			if(repo.exists(id)) {
				AfspeellijstData afspeellijstData = new AfspeellijstData();
				afspeellijstData.setNummer(repo.findOne(id));
				repoAfspeellijstData.save(afspeellijstData);
			}
		}
				
		mainAfspeellijst.sort();
		HttpSession session = request.getSession();		
		// logica voor het optellen van de votes
		User user = (User)session.getAttribute("user");
		user.getRights().checkVotes(user);
		repoUser.save(user);
		return mainAfspeellijst.getAfspeellijst();
	}
	
	// Het zoeken gebeurt nog wel via een post en een redirect, hier is dus duidelijk een pagina refresh te zien
	@RequestMapping(value="/homeZoek", method=RequestMethod.POST) 
	public String homeZoek(HttpServletRequest request, String zoek) {
		ArrayList<Nummer> alleResultaten = new ArrayList<>();
		// logica en attributen zetten, dus de lijst  vullen en laten zien.
		alleResultaten = repo.findByArtiestContainingIgnoreCaseOrTitelContainingIgnoreCase(zoek, zoek);
		HttpSession session = request.getSession();
		session.setAttribute("alleResultaten", alleResultaten);
		return "redirect:/home";
	}
		
	// logica het nummer aan de speellijst
	@RequestMapping(value="/homeVoegToe", method=RequestMethod.POST) 
	public @ResponseBody void homeVoegToe(HttpServletRequest request, long id) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		// heb je genoeg votes om een nummer toe te voegen?
		if(user.getVotes() > 0) {
			// is het nummer al toegevoegd?
			Afspeellijst tmpLijst = (Afspeellijst)session.getAttribute("mainAfspeellijst");
			for(AfspeellijstData data : tmpLijst.getAfspeellijst()) {
				if(data.getNummer().getId() == id) {
					user.getRights().addVote(data, user);
					user.getRights().minusUserVote(user);
					repoAfspeellijstData.save(data);
					repoUser.save(user);
				}
			}
			AfspeellijstData afspeellijstData = new AfspeellijstData();
			afspeellijstData.setNummer(repo.findOne(id));
			user.getRights().addVote(afspeellijstData, user);
			user.getRights().minusUserVote(user);
			repoAfspeellijstData.save(afspeellijstData);
			repoUser.save(user);
		} 
	}
	
	// Als je op de upvote knop klikt
	@RequestMapping(value="/upvote", method=RequestMethod.POST)
	public @ResponseBody void upvote(HttpServletRequest request, long id) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		user.getRights().addVote(repoAfspeellijstData.findOne(id), user);
		user.getRights().minusUserVote(user);
		repoAfspeellijstData.save(repoAfspeellijstData.findOne(id));
		repoUser.save(user);
	}
	
	// Haal via een ajax opdracht de votes van de user binnen, is de user Admin, return "Admin"
	@RequestMapping(value="/uservote")
	public @ResponseBody String uservote(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		if(user.getRightsString().equals("AdminUser")) {
			return "Admin";
		}
		return ""  +user.getVotes();
	}
	
	// Hier niet aanzitten, behalve als je een lege database hebt
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
