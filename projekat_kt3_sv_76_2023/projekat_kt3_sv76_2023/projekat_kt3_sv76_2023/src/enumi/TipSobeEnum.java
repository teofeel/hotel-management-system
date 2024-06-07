package enumi;

public enum TipSobeEnum {
		JEDNOKREVETNA, DVOKREVETNA, ODVOKREVETNA_DVA, TROKREVETNA, OBRISANA;
		
		private String[] opis = {"Jednokrevetna (1)", "Dvokrevetna (2)", "Dvokrevetna (1+1)", "Trokrevetna (2+1)", "OBRISANA"};
		@Override
		public String toString() {
			return opis[this.ordinal()];
		}
		
}
