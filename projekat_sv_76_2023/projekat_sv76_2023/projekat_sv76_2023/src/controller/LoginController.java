package controller;

import manager.*;
import enumi.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import entity.*;
import views.*;

public class LoginController {
	public static String checkLogin(String korisnickoIme, String lozinka) {
		if(AdminManager.admini.containsKey(korisnickoIme) && AdminManager.admini.get(korisnickoIme).checkLogin(korisnickoIme, lozinka)) {
			return "admin";
		}

		if(RecepcionerManager.recepcioneri.containsKey(korisnickoIme) && RecepcionerManager.recepcioneri.get(korisnickoIme).checkLogin(korisnickoIme, lozinka)){
			return "recepcioner";
		}

		if(SobaricaManager.sobarice.containsKey(korisnickoIme) && SobaricaManager.sobarice.get(korisnickoIme).checkLogin(korisnickoIme, lozinka)) {
			return "sobarica";
		}	
		if(GostManager.gosti.containsKey(korisnickoIme) && GostManager.gosti.get(korisnickoIme).checkLogin(korisnickoIme, lozinka)) {
			return "gost";
		}
		return "";
	}
}
