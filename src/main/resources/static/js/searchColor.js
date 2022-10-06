'use strict'

window.addEventListener('load', function(){
    let notePcSearchForm = document.getElementById('note_pc_search_form');
	let colorNumElements = notePcSearchForm.getElementsByClassName('colorId');
	let totalColorNumElements = document.querySelector('.totalColorNum');
	
	for (let colorNumElement of colorNumElements){
		colorNumElement.addEventListener('change', function(){
			getColorNum();
		})
	}
	
	let getColorNum = function(){
		let totalColorNum = "";
		for (let colorNumElement of colorNumElements){
			if (colorNumElement.checked){
				if (totalColorNum == ""){
					totalColorNum += colorNumElement.value;
				} else {
					totalColorNum += ',' + colorNumElement.value;
				}
			}
			console.log(totalColorNum);
			totalColorNumElements.value = totalColorNum;
		}	
	}
	
	let allBtn = document.getElementById('color_all');
	let allResetBtn = document.getElementById('color_all_reset');
	allBtn.addEventListener('click', function(){
		allCheck();
	});
	allResetBtn.addEventListener('click' ,function(){
		allReset();
	})
	
	function allCheck(){
		for (let colorNumElement of colorNumElements){
			colorNumElement.checked = true;
			getColorNum();
		}
	}
	function allReset(){
		for (let colorNumElement of colorNumElements){
			colorNumElement.checked = false;
			getColorNum();
		}
	}
	
	
})
