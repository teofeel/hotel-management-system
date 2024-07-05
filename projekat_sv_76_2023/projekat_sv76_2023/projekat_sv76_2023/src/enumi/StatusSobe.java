package enumi;

public enum StatusSobe {
	SLOBODNA, SPREMANJE, ZAUZETO;
	
	private String[] opis = {"SLOBODNA", "SPREMANJE", "ZAUZETO"};
	
	
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}
}
