package entity;
import java.util.HashMap;
import java.time.LocalDate;

public class Cenovnik {
	private HashMap<String, TipSobe> tipoviSoba;
	private HashMap<String, DodatneUsluge> dodatneUsluge;
	private LocalDate vaziOD;
	private LocalDate vaziDO;
	private boolean aktivan;
	
	public Cenovnik() {
		this.tipoviSoba = new HashMap<String, TipSobe>();
		this.dodatneUsluge = new HashMap<String, DodatneUsluge>();
		this.aktivan = false;
	}
	
	public Cenovnik(String vaziOD, String vaziDO, boolean aktivan) {
		this.tipoviSoba = new HashMap<String, TipSobe>();
		this.dodatneUsluge = new HashMap<String, DodatneUsluge>();
		this.aktivan = aktivan;
		
		this.vaziOD = LocalDate.parse(vaziOD);
		this.vaziDO = LocalDate.parse(vaziDO);
	}
	public Cenovnik(HashMap<String, TipSobe> tipovi, HashMap<String, DodatneUsluge> du, String vaziOD, String vaziDO, boolean aktivan) {
		this.tipoviSoba = tipovi;
		this.dodatneUsluge = du;
		this.aktivan = aktivan;
		
		this.vaziOD = LocalDate.parse(vaziOD);
		this.vaziDO = LocalDate.parse(vaziDO);
		
	}
	
	public HashMap<String, TipSobe> getTipoviSoba(){
		return this.tipoviSoba;
	}
	public HashMap<String, DodatneUsluge> getDodatneUsluge(){
		return this.dodatneUsluge;
	}
	public LocalDate getVaziDo() {
		return this.vaziDO;
	}
	public LocalDate getVaziOd() {
		return this.vaziOD;
	}
	public boolean getAktivan() {
		return this.aktivan;
	}
	
	
	public void setVaziDO(LocalDate vaziDo) {
		this.vaziDO = vaziDo;
	}
	public void setVaziOD(LocalDate vaziOd) {
		this.vaziOD = vaziOd;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

	
	@Override
	public String toString() {
		return "";
	}
	
}
