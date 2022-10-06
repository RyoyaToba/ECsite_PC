/**
 * 
 */
'use strict'

window.addEventListener('load', function() {
    let notePcSearchForm = document.getElementById('note_pc_search_form');
	let priceNumElements = notePcSearchForm.getElementsByClassName('price_num');
	let totalPriceNumElement = document.getElementById('totalPriceNum');

	for (let priceNumElement of priceNumElements) {
		priceNumElement.addEventListener('change', function() {
			getPriceNum();
		})
	}

	let getPriceNum = function() {
		let totalPriceNumber = '';
		for (let priceNumElement of priceNumElements) {
			if (priceNumElement.checked) {
				if (totalPriceNumber == '') {
					totalPriceNumber += priceNumElement.dataset.min;
				} else {
					totalPriceNumber += ',' +  priceNumElement.dataset.min;
				}
				totalPriceNumber += ',' + priceNumElement.dataset.max;
			}
		}
		totalPriceNumElement.value = totalPriceNumber;
	}
	
	
	let allBtn = document.getElementById('price_all');
	let allResetBtn = document.getElementById('price_all_reset');
	allBtn.addEventListener('click', function(){
		allCheck();
	});
	allResetBtn.addEventListener('click' ,function(){
		allReset();
	})
	
	function allCheck(){
		for (let priceNumElement of priceNumElements){
			priceNumElement.checked = true;
		}
		getPriceNum();
	}
	function allReset(){
		for (let priceNumElement of priceNumElements){
			priceNumElement.checked = false;
		}
		getPriceNum();
	}
});
