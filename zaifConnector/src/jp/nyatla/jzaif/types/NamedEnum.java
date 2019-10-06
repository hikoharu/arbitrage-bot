package jp.nyatla.jzaif.types;

/**
 * シンボル名付きenumのための実装です。
 * {@link NamedEnum.Interface}を実装したenumから使う関数を定義します。
 */
public class NamedEnum{
	public interface Interface{
		public int getId();
		public String getSymbol();
	}
	/**
	 * シンボルの一致するオブジェクトを返します。
	 * @param i_enum
	 * @param i_symbol
	 * @return
	 */
	public static <T extends Interface> T toEnum(Class<T> i_enum,String i_symbol)
	{
		for(T i:i_enum.getEnumConstants()){
			if(i.getSymbol().compareToIgnoreCase(i_symbol)==0){
				return i;
			}
		}
		throw new IllegalArgumentException();
	}
	/**
	 * IDの一致するオブジェクトを返します。
	 * @param i_enum
	 * @param i_symbol
	 * @return
	 */
	public static <T extends Interface> T toEnum(Class<T> i_enum,int id)
	{
		for(T i:i_enum.getEnumConstants()){
			if(i.getId()==id){
				return i;
			}
		}
		throw new IllegalArgumentException();
	}
	
}
