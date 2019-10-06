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

package jp.nyatla.jzaif.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.nyatla.jzaif.api.result.ActiveOrdersResult;
import jp.nyatla.jzaif.api.result.CancelOrderResult;
import jp.nyatla.jzaif.api.result.DepositHistoryResult;
import jp.nyatla.jzaif.api.result.GetInfoResult;
import jp.nyatla.jzaif.api.result.TradeHistoryResult;
import jp.nyatla.jzaif.api.result.TradeResult;
import jp.nyatla.jzaif.api.result.WithdrawHistoryResult;
import jp.nyatla.jzaif.api.result.WithdrawResult;
import jp.nyatla.jzaif.io.IHttpClient;
import jp.nyatla.jzaif.io.NyansatHttpClient;
import jp.nyatla.jzaif.types.Currency;
import jp.nyatla.jzaif.types.CurrencyPair;
import jp.nyatla.jzaif.types.SortOrder;
import jp.nyatla.jzaif.types.TradeType;

/**
 * Zaif取引APIのラッパークラスです。 利用にはAPIキーの発行が必要です。
 * https://corp.zaif.jp/api-docs/trade-api/
 */
public class ExchangeApi {
	/** 浮動小数点/整数をzaifフォーマットで出力する関数 */
	private static String numberToStr(double n) {
		if (n - Math.floor(n) != 0) {
			return Double.toString(n);
		} else {
			return Long.toString((long) (Math.floor(n)));
		}
	}

	final private static String URL = "https://api.zaif.jp/tapi";
	private long _nonce;
	final private ApiKey _apikey;
	final private IHttpClient _cl = new NyansatHttpClient();

	private static long makeAutoNonce() {
		try {
			long base_date = (new SimpleDateFormat("yyyy-MM-dd")).parse("2016-01-01").getTime();
			return (System.nanoTime() - base_date) / 1000000;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	private static Logger log = LoggerFactory.getLogger(ExchangeApi.class);

	private JSONObject doCommand(String i_method, String i_additional_params) throws Exception {
		String query;
		synchronized (this) {
			this._nonce++;
			query = String.format("nonce=%d&method=%s", this._nonce, i_method);
		}

		if (i_additional_params != null && i_additional_params.length() > 0) {
			query += i_additional_params;
		}
		log.info("Zaif updated nonce is " + this._nonce);
		log.info("Zaif query is " + query);
		String result = this._cl.postText(URL, this._apikey.key, this._apikey.makeSignedHex(query), query);
		log.info("Zaif result is " + result);
		return new JSONObject(result);

	}

	private JSONObject doCommand(String i_method, String i_additional_params,long nonce) throws Exception {
		String query;
		synchronized (this) {
			this._nonce++;
			query = String.format("nonce=%d&method=%s", nonce, i_method);
		}

		if (i_additional_params != null && i_additional_params.length() > 0) {
			query += i_additional_params;
		}
		log.info("Zaif updated nonce is " + nonce);
		log.info("Zaif query is " + query);
		String result = this._cl.postText(URL, this._apikey.key, this._apikey.makeSignedHex(query), query);
		log.info("Zaif result is " + result);
		return new JSONObject(result);

	}
	
	/**
	 * nonceを自動で設定するコンストラクタです。 nonce値は現在時刻を基準に自動で設定します。
	 * 
	 * @param i_key
	 *            ZaifのAPIキー
	 */
	public ExchangeApi(ApiKey i_key) {
		this(i_key, makeAutoNonce());
	}

	/**
	 * APIキーと初期のnonce値から、インスタンスを構築します。
	 * 
	 * @param i_key
	 *            Zaifのキーオブジェクト
	 * @param i_nonce
	 *            nonceの初期値
	 */
	public ExchangeApi(ApiKey i_key, long i_nonce) {
		this._apikey = i_key;
		this._nonce = i_nonce;
	}

	/**
	 * get_infoコマンドを実行して戻り値を得ます。
	 * 
	 * @return 結果を格納した{@link GetInfoResult}オブジェクト。
	 * @throws Exception
	 */
	public GetInfoResult getInfo(long nonce) throws Exception {
		return new GetInfoResult(this.doCommand("get_info", null,nonce));
	}

	/**
	 * trade_historyコマンドを実行して戻り値を得ます [Optional]パラメータはnullを設定できます。
	 * 
	 * @param i_from
	 *            [Optional]
	 * @param i_count
	 *            [Optional]
	 * @param i_from_id
	 *            [Optional]
	 * @param i_end_id
	 *            [Optional]
	 * @param i_order
	 *            [Optional]
	 * @param i_since
	 *            [Optional]
	 * @param i_end
	 *            [Optional]
	 * @param i_currency_pair
	 *            [Optional]
	 * @return 結果を格納した{@link TradeHistoryResult}オブジェクト。
	 * @throws Exception
	 */
	public TradeHistoryResult tradeHistory(Integer i_from, Integer i_count, Long i_from_id, Long i_end_id,
			SortOrder i_order, Date i_since, Date i_end, CurrencyPair i_currency_pair) throws Exception {
		String p = "";
		if (i_from != null) {
			p += "&from=" + i_from.toString();
		}
		if (i_count != null) {
			p += "&count=" + i_count.toString();
		}
		if (i_from_id != null) {
			p += "&from_id=" + i_from_id.toString();
		}
		if (i_end_id != null) {
			p += "&from_id=" + i_end_id.toString();
		}
		if (i_order != null) {
			p += "&order=" + i_order.symbol;
		}
		if (i_since != null) {
			p += "&since=" + (i_since.getTime() / 1000);
		}
		if (i_end != null) {
			p += "&end=" + (i_end.getTime() / 1000);
		}
		if (i_currency_pair != null) {
			p += "&currency_pair=" + i_currency_pair.symbol;
		}
		return new TradeHistoryResult(this.doCommand("trade_history", p));
	}

	/**
	 * Optionalパラメータを省略した{@link #tradeHistory}関数です。
	 * 
	 * @return 結果を格納した{@link TradeHistoryResult}オブジェクト。
	 * @throws Exception
	 */
	public TradeHistoryResult tradeHistory() throws Exception {
		return this.tradeHistory(null, null, null, null, null, null, null, null);
	}

	/**
	 * active_ordersコマンドを実行して戻り値を得ます [Optional]パラメータはnullを設定できます。
	 * 
	 * @param i_currency_pair
	 *            [Optional]
	 * @return 結果を格納した{@link ActiveOrdersResult}オブジェクト。
	 * @throws Exception
	 */
	public ActiveOrdersResult activeOrders(CurrencyPair i_currency_pair) throws Exception {
		String p = "";
		if (i_currency_pair != null) {
			p += "&currency_pair=" + i_currency_pair.symbol;
		}
		return new ActiveOrdersResult(this.doCommand("active_orders", p));
	}

	/**
	 * Optionalパラメータを省略した{@link #activeOrders}関数です。
	 * 
	 * @return 結果を格納した{@link ActiveOrdersResult}オブジェクト。
	 * @throws Exception
	 */
	public ActiveOrdersResult activeOrders() throws Exception {
		return this.activeOrders(null);
	}

	/**
	 * tradeコマンドを実行して戻り値を得ます [Optional]パラメータはnullを設定できます。
	 * 
	 * @param i_currency_pair
	 *            [Required]
	 * @param i_action
	 *            [Required]
	 * @param i_price
	 *            [Required]
	 * @param i_amount
	 *            [Required]
	 * @param i_limit
	 *            [Optional]
	 * @return 結果を格納した{@link TradeResult}オブジェクト。
	 * @throws Exception
	 */
	public TradeResult trade(CurrencyPair i_currency_pair, TradeType i_action, double i_price, Number i_amount,
			Double i_limit,long nonce) throws Exception {
		String p = "";
		p += "&currency_pair=" + i_currency_pair.symbol;
		p += "&action=" + i_action.symbol;
		p += "&price=" + numberToStr(i_price);
		p += "&amount=" + numberToStr(i_amount.doubleValue());
		if (i_limit != null) {
			p += "&limit=" + numberToStr(i_limit);
		}
		return new TradeResult(this.doCommand("trade", p,nonce));
	}

	/**
	 * Optionalを省略した{@link #trade}関数です。
	 * 
	 * @return 結果を格納した{@link TradeResult}オブジェクト。
	 * @throws Exception
	 */
	public TradeResult trade(CurrencyPair i_currency_pair, TradeType i_action, double i_price, Number i_amount,long nonce)
			throws Exception {
		return this.trade(i_currency_pair, i_action, i_price, i_amount, null,nonce);
	}

	/**
	 * cancel_orderコマンドを実行して戻り値を得ます
	 * 
	 * @param i_order_id
	 *            [Required]
	 * @return 結果を格納した{@link CancelOrderResult}オブジェクト。
	 * @throws Exception
	 */
	public CancelOrderResult cancelOrder(long i_order_id) throws Exception {
		String p = "";
		p += "&order_id=" + Long.toString(i_order_id);
		return new CancelOrderResult(this.doCommand("cancel_order", p));

	}

	/**
	 * withdrawコマンドを実行して戻り値を得ます [Optional]パラメータはnullを設定できます。
	 * 
	 * @param i_currency
	 *            [Required]
	 * @param i_address
	 *            [Required]
	 * @param i_amount
	 *            [Required]
	 * @param i_opt_fee
	 *            [Optional]
	 * @return 結果を格納した{@link WithdrawResult}オブジェクト。
	 * @throws Exception
	 */
	public WithdrawResult withdraw(Currency i_currency, String i_address, double i_amount, Double i_opt_fee)
			throws Exception {
		String p = "";
		p += "&currency=" + i_currency.symbol;
		p += "&address=" + i_address;
		p += "&amount=" + numberToStr(i_amount);
		if (i_opt_fee != null) {
			p += "&opt_fee=" + numberToStr(i_opt_fee);
		}
		return new WithdrawResult(this.doCommand("withdraw", p));
	}

	/**
	 * Optionalを省略した{@link #withdraw}関数です。
	 * 
	 * @return 結果を格納した{@link WithdrawResult}オブジェクト。
	 * @throws Exception
	 */
	public WithdrawResult withdraw(Currency i_currency, String i_address, double i_amount) throws Exception {
		return this.withdraw(i_currency, i_address, i_amount, null);
	}

	/**
	 * deposit_historyコマンドを実行して戻り値を得ます [Optional]パラメータはnullを設定できます。
	 * 
	 * @param i_currency
	 *            [Required]
	 * @param i_from
	 *            [Optional]
	 * @param i_count
	 *            [Optional]
	 * @param i_from_id
	 *            [Optional]
	 * @param i_end_id
	 *            [Optional]
	 * @param i_order
	 *            [Optional]
	 * @param i_since
	 *            [Optional]
	 * @param i_end
	 *            [Optional]
	 * @return 結果を格納した{@link DepositHistoryResult}オブジェクト。
	 * @throws Exception
	 */
	public DepositHistoryResult depositHistory(Currency i_currency, Integer i_from, Integer i_count, Long i_from_id,
			Long i_end_id, SortOrder i_order, Date i_since, Date i_end) throws Exception {
		String p = "";
		p += "&currency=" + i_currency.symbol;
		if (i_from != null) {
			p += "&from=" + i_from.toString();
		}
		if (i_count != null) {
			p += "&count=" + i_count.toString();
		}
		if (i_from_id != null) {
			p += "&from_id=" + i_from_id.toString();
		}
		if (i_end_id != null) {
			p += "&from_id=" + i_end_id.toString();
		}
		if (i_order != null) {
			p += "&order=" + i_order.symbol;
		}
		if (i_since != null) {
			p += "&since=" + (i_since.getTime() / 1000);
		}
		if (i_end != null) {
			p += "&end=" + (i_end.getTime() / 1000);
		}
		return new DepositHistoryResult(this.doCommand("deposit_history", p));
	}

	/**
	 * Optionalを省略した{@link #depositHistory}関数です。
	 * 
	 * @return 結果を格納した{@link DepositHistoryResult}オブジェクト。
	 * @throws Exception
	 */
	public DepositHistoryResult depositHistory(Currency i_currency) throws Exception {
		return this.depositHistory(i_currency, null, null, null, null, null, null, null);
	}

	/**
	 * withdraw_historyコマンドを実行して戻り値を得ます [Optional]パラメータはnullを設定できます。
	 * 
	 * @param i_currency
	 *            [Required]
	 * @param i_from
	 *            [Optional]
	 * @param i_count
	 *            [Optional]
	 * @param i_from_id
	 *            [Optional]
	 * @param i_end_id
	 *            [Optional]
	 * @param i_order
	 *            [Optional]
	 * @param i_since
	 *            [Optional]
	 * @param i_end
	 *            [Optional]
	 * @return 結果を格納した{@link WithdrawHistoryResult}オブジェクト。
	 * @throws Exception
	 */
	public WithdrawHistoryResult withdrawHistory(Currency i_currency, Integer i_from, Integer i_count, Long i_from_id,
			Long i_end_id, SortOrder i_order, Date i_since, Date i_end) throws Exception {
		String p = "";
		p += "&currency=" + i_currency.symbol;
		if (i_from != null) {
			p += "&from=" + i_from.toString();
		}
		if (i_count != null) {
			p += "&count=" + i_count.toString();
		}
		if (i_from_id != null) {
			p += "&from_id=" + i_from_id.toString();
		}
		if (i_end_id != null) {
			p += "&from_id=" + i_end_id.toString();
		}
		if (i_order != null) {
			p += "&order=" + i_order.symbol;
		}
		if (i_since != null) {
			p += "&since=" + (i_since.getTime() / 1000);
		}
		if (i_end != null) {
			p += "&end=" + (i_end.getTime() / 1000);
		}
		return new WithdrawHistoryResult(this.doCommand("withdraw_history", p));
	}

	/**
	 * Optionalを省略した{@link #withdrawHistory}関数です。
	 * 
	 * @return 結果を格納した{@link WithdrawHistoryResult}オブジェクト。
	 * @throws Exception
	 */
	public WithdrawHistoryResult withdrawHistory(Currency i_currency) throws Exception {
		return this.withdrawHistory(i_currency, null, null, null, null, null, null, null);
	}

}
