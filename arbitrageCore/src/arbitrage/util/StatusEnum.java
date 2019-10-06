package arbitrage.util;

public enum StatusEnum {

	CHECKED(0), BIDED(1), ASKED(2);

	private int status;

	private StatusEnum(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
