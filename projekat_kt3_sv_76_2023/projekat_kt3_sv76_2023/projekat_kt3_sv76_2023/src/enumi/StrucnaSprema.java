package enumi;

public enum StrucnaSprema {
	VISOKA, SREDNJA, OSNOVNA;
	private String [] opis = {"Visoka sprema", "Srednja sprema", "Osnovna sprema"};
	@Override
	public String toString() {
		return opis[this.ordinal()];
	}
}
