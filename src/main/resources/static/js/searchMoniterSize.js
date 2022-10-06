'use strict'

window.addEventListener('load', function() {
    let notePcSearchForm = document.getElementById('note_pc_search_form');
	let moniterSizeNumElements = notePcSearchForm.getElementsByClassName('moniter_size');
	let totalMoniterSizeNumElements = document.querySelector('.totalMoniterSizeNum');

	for (let moniterSizeNumElement of moniterSizeNumElements) {
		moniterSizeNumElement.addEventListener('change', function() {
			getMoniterSize();
		})
	}

	let getMoniterSize = function() {
		let totalMoniterSizeNum = "";
		for (let moniterSizeNumElement of moniterSizeNumElements) {

			if (moniterSizeNumElement.checked) {
				let moniterSizeMin = moniterSizeNumElement.dataset.min;
				let moniterSizeMax = moniterSizeNumElement.dataset.max;
				if (totalMoniterSizeNum == '') {
					totalMoniterSizeNum += moniterSizeMin;
				} else {
					totalMoniterSizeNum += ',' + moniterSizeMin;
				}
				totalMoniterSizeNum += ',' + moniterSizeMax;
			}
		}
		totalMoniterSizeNumElements.value = totalMoniterSizeNum;
	}
	
	let allBtn = document.getElementById('moniter_size_all');
	let allResetBtn = document.getElementById('moniter_size_all_reset');
	allBtn.addEventListener('click', function(){
		allCheck();
	});
	allResetBtn.addEventListener('click' ,function(){
		allReset();
	})
	
	function allCheck(){
		for (let moniterSizeNumElement of moniterSizeNumElements){
			moniterSizeNumElement.checked = true;
			getMoniterSize();
		}
		getMoniterSize();
	}
	function allReset(){
		for (let moniterSizeNumElement of moniterSizeNumElements){
			moniterSizeNumElement.checked = false;
			getMoniterSize();
		}
		getMoniterSize();
	}
	
	
})
