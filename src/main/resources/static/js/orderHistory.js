'use strict'

let historyPrice = Number($(".historyPrice").attr("value"));

let historyToppingPrice = 0;
$(".historyToppingPrice").each(function(){
	historyToppingPrice += Number($(this).attr("value"));
});
let historyTotalPrice = historyPrice + historyToppingPrice;

$(".historyTotal").text(historyTotalPrice);
