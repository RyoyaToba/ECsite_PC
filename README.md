# ECサイト：らくらくPC

開発人数２〜３名

開発期間２ヶ月

pullRequestなどは全てPrivateレポジトリに。ここにあるのは作成記録を公開しているだけです。

らくらくPCではパソコン販売ECサイトを想定して作成を行なっており、現在はノートパソコン、デスクトップパソコンを販売しています。  
今後商品ラインナップを増やし、多様な商品を検索できるようにしていきます。

![ノートPC販売画面](/document/ノートPC販売画面.png)

## 目次
- [使用ツール](#使用ツール)
- [セットアップ](#セットアップ)
- [システム詳細](#システム詳細)
- [実装済機能](#実装済機能)
- [実装予定機能](#実装予定機能)

## 使用ツール
#### フロント
- HTML 5
- CSS 3
- BootStrap(3.3.1)
- jQuery(1.11.3)
#### サーバー
- Java(openjdk 18.0.1.1)
- Springboot(2.7.0)
- Thymeleaf
- JavaScript()
#### データベース
- PostgreSQL()
#### テスト
- Junit 5

## セットアップ

### Javaインストール
Javaのインストールを行なってください。作成したバージョンは[openjdk(18.0.1.1)](https://jdk.java.net/18/)です。

### Spring Tool Tuiteインストール
アプリケーションの実行環境として、[Spring Tool Tuite(4.15.1)](https://spring.io/tools)を使用します。

### データベースのセットアップ
PostgreSQLで実行します。データベース名は`ec-202204c`としてください。
[こちら](https://docs.google.com/document/d/1zNUTxA2tU4yPibrzuUVojuntrzlo78_AwRJe8CPCkKs/edit)よりSQLを実行し各テーブルを作成してください。なお今回のデータベースにおけるER図は下のとおりです。

<details>

<summary>ER図</summary>

![ERpc](/document/ER図.svg)

</details>

また[INSERT用スプレッドシート](https://docs.google.com/spreadsheets/d/1m0JlAFQ0rJIVU2larTafdDufp1nKnCA5e9ZjwnLruVQ/edit#gid=974798405)より、商品のインサートを行なってください。

<details>

<summary>①NotePCのインサート</summary>

<br />

![DBインサート](/document/DBインサート.png)

</details>

<details>

<br />

<summary>②DesktopPcのインサート</summary>

![DesktopPcインサート](/document/DesktopPcインサート.png)

</details>

<details>

<br />

<summary>③その他アイテムオプションのインサート</summary>

![他インサート](/document/他インサート.png)

</details>

## システム詳細
Springbootを用いてSpringMVCモデルを構築いたしました。  
ユーザーからのリクエストをControllerが受け取り、View(HTML)をユーザーに返します。  
DBとのやりとりはRepositoryが行います。

```mermaid
flowchart LR
    id1(User)--Request-->id2(Controller)
    id3(View)--Response-->id1(User)
        subgraph  [SpringMVC]
            id2(Controller)-->id3(View)
            id2(Controller)-->id4(Model)
            id4(Model)-->id3(View)
            id5[(DataBase)]-->id4(Model)
            id4(Model)-->id5[(DataBase)]
        end
```

## 実装済機能

詳しくは[こちら](実装済機能一覧.md)をご覧ください

## 実装予定機能

詳しくは[Issues](https://github.com/rpentry202204w/ec-202204c/issues?q=is%3Aopen+is%3Aissue)にてご確認ください

