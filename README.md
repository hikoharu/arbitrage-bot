# Overview
仮想通貨の取引所間で裁定取引をするbotです

# Description
設定された取引所をクローリングして、価格差が発生した瞬間に買い、売り、同時に注文を入れます。

取引を行う条件については、下記の設定ファイルで指定することができます

https://github.com/haruhiko-tano/arbitrage-bot/blob/master/arbitrageCore/arbitrage.properties.sample

詳細については下記にまとめてあります。

https://qiita.com/haruhiko_tano/items/87aa88aef7b0c421e837

対応通貨
- JPY
- BTC
- ETH

対応取引所
- Coincheck
- Zaif
- bitbank
- Liquid by Quoine
- bitFlyer
