# coincheck-java

```java
CoinCheck client = new CoinCheck("ACCESS-KEY", "SECRET-KEY");

/**
 * Public API
 */
client.ticker().all();
client.trade().all();
client.orderBook().all();

/**
 * Private API
 */
// 新規注文
// "buy" 指値注文 現物取引 買い
// "sell" 指値注文 現物取引 売り
// "market_buy" 成行注文 現物取引 買い
// "market_sell" 成行注文 現物取引 売り
// "leverage_buy" 指値注文 レバレッジ取引新規 買い
// "leverage_sell" 指値注文 レバレッジ取引新規 売り
// "close_long" 指値注文 レバレッジ取引決済 売り
// "close_short" 指値注文 レバレッジ取引決済 買い

JSONObject orderObj = new JSONObject();
orderObj.put("rate", "28500");
orderObj.put("amount", "0.00508771");
orderObj.put("order_type", "buy");
orderObj.put("pair", "btc_jpy");
client.order().create(orderObj);

// 未決済の注文一覧
client.order().opens();

// 注文のキャンセル
client.order().cancel("2953613");

// 取引履歴
client.order().transactions();

// ポジション一覧
Map<String, String> positions = new HashMap<>();
client.leverage().positions(positions);

// 残高
client.account().balance();

// レバレッジアカウントの残高
client.account().leverageBalance();

// アカウント情報
client.account().info();

// ビットコインの送金
JSONObject sendObj = new JSONObject();
sendObj.put("address", "1Gp9MCp7FWqNgaUWdiUiRPjGqNVdqug2hY");
sendObj.put("amount", "0.0002");
client.send().create(sendObj);

// ビットコインの送金履歴
Map<String, String> sendParam = new HashMap<>();
sendParam.put("currency", "BTC");
client.send().all(sendParam);

// ビットコインの受け取り履歴
client.deposit().all(sendParam);

// ビットコインの高速入金
JSONObject depositObj = new JSONObject();
orderObj.put("id", "2222");
client.deposit().fast(depositObj);

// 銀行口座一覧
client.bankAccount().all();

// 銀行口座の登録
JSONObject bankAccObj = new JSONObject();
bankAccObj.put("bank_name", "MUFG");
bankAccObj.put("branch_name", "Tokyo");
bankAccObj.put("bank_account_type", "toza");
bankAccObj.put("number", "1234567");
bankAccObj.put("name", "Albert Enstein");
client.bankAccount().create(bankAccObj);

// 銀行口座の削除
client.bankAccount().delete("23334");

// 出金履歴
client.withdraw().all();

// 出金申請の作成
JSONObject withdrawObj = new JSONObject();
withdrawObj.put("bank_account_id", "23335");
withdrawObj.put("amount", "20000");
withdrawObj.put("currency", "JPY");
withdrawObj.put("is_fast", "false");
client.withdraw().create(withdrawObj);

// 出金申請のキャンセル
client.withdraw().cancel("15678");

// 借入申請
JSONObject borrowObj = new JSONObject();
borrowObj.put("amount", "0.01");
borrowObj.put("currency", "BTC");
client.borrow().create(borrowObj);

// 借入中一覧
client.borrow().matches();

// 返済
JSONObject borrowRepayObj = new JSONObject();
borrowRepayObj.put("id", "100");
client.borrow().repay(borrowRepayObj);

// レバレッジアカウントへの振替
JSONObject transferToObj = new JSONObject();
transferToObj.put("amount", "100");
transferToObj.put("currency", "JPY");
client.transfer().to_leverage(transferToObj);

// レバレッジアカウントからの振替
JSONObject transferFromObj = new JSONObject();
transferFromObj.put("amount", "100");
transferFromObj.put("currency", "JPY");
client.transfer().from_leverage(transferFromObj);    

```
