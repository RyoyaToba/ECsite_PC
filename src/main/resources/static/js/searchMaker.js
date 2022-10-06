'use strict'

window.addEventListener('load', function(){
    let notePcSearchForm = document.getElementById('note_pc_search_form');
	let makerNumElements = notePcSearchForm.getElementsByClassName('maker_num');
	let totalMakerNumElements = document.querySelector('.totalMakerNum');
	
	for (let makerNumElement of makerNumElements){
		makerNumElement.addEventListener('change', function(){
			getMakerNum();		
		})
	}
	
	let getMakerNum = function(){
		let totalMakerNum = '';
		for (let makerNumElement of makerNumElements){
		    if(makerNumElement.checked){
			    if (totalMakerNum == ''){
				    totalMakerNum += makerNumElement.value;
		    	} else {
		    		totalMakerNum += ',' + makerNumElement.value; 
			    }
		    }
        }
        totalMakerNumElements.value = totalMakerNum;
	}
	
	let allBtn = document.getElementById('maker_all');
	let allResetBtn = document.getElementById('maker_all_reset');
	allBtn.addEventListener('click', function(){
		allCheck();
	});
	allResetBtn.addEventListener('click' ,function(){
		allReset();
	})
	
	function allCheck(){
		for (let makerNumElement of makerNumElements){
			makerNumElement.checked = true;
		}
		getMakerNum();
	}
	function allReset(){
		for (let makerNumElement of makerNumElements){
			makerNumElement.checked = false;
		}
		getMakerNum();
	}
	
})
