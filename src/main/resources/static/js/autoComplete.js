'use strict';

$(function() {
let availableTags = [
			
		]
	$("#code").autocomplete({
		source: availableTags,
	});
});

// 並べ替えボタン表示・非表示機能
$(function() {
	$("#selectBtn").hide();
	$("#select").on("change", function() {
		let selectValue = $("#select").val();
		if (selectValue != 0) {
			$("#selectBtn").show();
		};
	});
});