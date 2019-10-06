/*
 * Copyright (c) 2016, nyatla
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies, 
 * either expressed or implied, of the FreeBSD Project.
 */

package jp.nyatla.jzaif.api.result;

import jp.nyatla.jzaif.types.Currency;
import jp.nyatla.jzaif.types.NamedEnum;

import org.json.JSONObject;

/**
 * ExchangeAPIのベースクラスです。
 */
public class ExchangeCommonResult {
	/** APIが成功したかのフラグ値です。 */
	final public boolean success;
	/** 失敗理由を格納するテキスト。{@link #success}がfalseのときのみ有効。 */
	final public String error_text;
	final public ErrorType error_type;

	public ExchangeCommonResult(boolean i_success, String i_error_text) {
		this.success = i_success;
		this.error_text = i_error_text;
		ErrorType et;
		try {
			et = ErrorType.toEnum(i_error_text);
		} catch (IllegalArgumentException e) {
			et = ErrorType.UNKNOWN;
		}
		this.error_type = et;
	}

	/** パース済みJSONからインスタンスを構築します。 */
	public ExchangeCommonResult(JSONObject i_jso) {
		boolean s = i_jso.getInt("success") == 1;
		String er = (s ? null : i_jso.getString("error"));
		this.success = s;
		this.error_text = er;
		if (!s) {
			// エラーの時だけ
			ErrorType et;
			try {
				et = ErrorType.toEnum(er);
			} catch (IllegalArgumentException e) {
				et = ErrorType.UNKNOWN;
			}
			this.error_type = et;
		} else {
			this.error_type = null;
		}
	}

	public static class Funds {
		final public double jpy;
		final public double btc;
		final public double mona;
		final public double xem;
		final public double eth;

		public Funds(double i_jpy,double i_btc,double i_mona,double i_xem,double i_eth){
			this.jpy=i_jpy;
			this.btc=i_btc;
			this.mona=i_mona;
			this.xem=i_xem;
			this.eth=i_eth;
		}

		public Funds(JSONObject i_jso) {
			this(i_jso.getDouble("jpy"), i_jso.getDouble("btc"), i_jso.getDouble("mona"),
					i_jso.has("xem") ? i_jso.getDouble("xem") : 0, i_jso.has("ETH") ? i_jso.getDouble("ETH") : 0);
		}
	}

	public static enum ErrorType implements NamedEnum.Interface {
		NONCE_NOT_INCREMENTED("nonce not incremented", 1), INVALID_AMOUNT_PARAMETER("invalid amount parameter",
				2), INSUFFICIENT_FUNDS("insufficient funds", 3), INVALID_ORDER_ID_PARAMETER(
						"invalid order_id parameter", 4), ORDER_NOT_FOUND("order not found", 5),

		UNKNOWN("unknown", 254), NONE("", 255);
		/** エラーメッセージのenum値です。 */
		final public String symbol;
		final public int id;

		private ErrorType(String i_symbol, int i_id) {
			this.id = i_id;
			this.symbol = i_symbol;
		}

		@Override
		final public int getId() {
			return this.id;
		}

		@Override
		final public String getSymbol() {
			return this.symbol;
		}

		public static ErrorType toEnum(String i_symbol) {
			return NamedEnum.toEnum(ErrorType.class, i_symbol);
		}

		public static ErrorType toEnum(int i_id) {
			return NamedEnum.toEnum(ErrorType.class, i_id);
		}

		public static void main(String[] i_args) {
			System.out.println(ErrorType.toEnum(3));
			return;

		}
	}
}
