package enumi;

public enum StatusRezervacije {
	NA_CEKANJU, POTVRDJENA, ODBIJENA, OTKAZANA;
	
	private String[] opis = {"NA CEKANJU", "POTVRDJENA", "ODBIJENA", "OTKAZANA"};
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}
	
}
