'use strict'

window.addEventListener('load', function() {

	document.getElementById('quantity_negative_1').addEventListener('click', function(){
		alert(this.id);
	})

	let quantityTag = document.getElementById('shoppingCart_quantity');
	let quantityText = quantityTag.dataset.quantity;
	let quantityInt = parseInt(quantityText);
	let negativeButton = document.getElementById('quantity_negative');
	let pogitiveButton = document.getElementById('quantity_pogitive');
	
	negativeButton.addEventListener('click', function() {
		
		quantityInt -= 1;
		quantityTag.textContent = quantityInt;
	})

	pogitiveButton.addEventListener('click', function() {

		quantityInt += 1;
		quantityTag.textContent = quantityInt;
	})
})
