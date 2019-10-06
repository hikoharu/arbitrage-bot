package arbitrage.util;

public class BitCoinUtil {
	
	public static String getServiceCaption(int serviceId) {

		for (BitCoinServiceEnum e : BitCoinServiceEnum.values()) {
			if (serviceId == e.getServiceId()) {
				return e.getServiceCaption();
			}
		}

		return "";

	}
}
