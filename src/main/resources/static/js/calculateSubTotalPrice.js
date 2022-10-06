"use strict";
window.addEventListener('load',function(){
    //税抜き価格を計算するメソッド
    let calculateSubTotalPriceWithoutTax = () => {
        const basePriceValue = (Number)(document.getElementById('item-basePrice').dataset.itemBasePrice);//本体価格
        let cpuPriceValue = (Number)(document.getElementById('cpu').options[document.getElementById('cpu').selectedIndex].dataset.cpuPrice);//cpu価格
        let ramPriceValue = (Number)(document.getElementById('ram').options[document.getElementById('ram').selectedIndex].dataset.ramPrice);//ram価格
        let romPriceValue = (Number)(document.getElementById('rom').options[document.getElementById('rom').selectedIndex].dataset.romPrice);//rom価格
        let quantityValue = (Number)(document.getElementById('quantity').value);//数量
        let subTotalPriceWithoutTax = (basePriceValue+cpuPriceValue+ramPriceValue+romPriceValue)*quantityValue//現在価格の計算式
        let formattedSubTotalPriceWithoutTax = subTotalPriceWithoutTax.toLocaleString()
        document.getElementById('total-price').textContent = formattedSubTotalPriceWithoutTax;
    }
    
    //アクセス時に実行
    calculateSubTotalPriceWithoutTax();
    
    //何かしら変化があった時に実行
    const elementsAffectingPrice = document.getElementsByClassName('affect-Price');
    for(let element of elementsAffectingPrice){
        element.addEventListener('change',function(){
            calculateSubTotalPriceWithoutTax();
        })
    }
    
    
});
