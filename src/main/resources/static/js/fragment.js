"use strict";
window.addEventListener('load',function() {
    let categoryIdElement = document.getElementById('categoryId')
    let categoryId = document.getElementById('categoryId').value;
    let notePcConditionElement = document.getElementById('notePc-condition');
    let desktopPcConditionElement = document.getElementById('desktopPc-condition');
    //基本は全てhide
    notePcConditionElement.style.display ="none";
    desktopPcConditionElement.style.display ="none";
    //値に合わせてshow
    if(categoryId==1){
        notePcConditionElement.style.display ="block";
        desktopPcConditionElement.style.display ="none";
    };
    if(categoryId==2){
        notePcConditionElement.style.display ="none";
        desktopPcConditionElement.style.display ="block";
    };
    //その後は変わったときにshow,hide
    categoryIdElement.addEventListener('change',function(event){
        if(event.target.value==0){
            notePcConditionElement.style.display ="none";
            desktopPcConditionElement.style.display ="none";
        }else if(event.target.value==1){
            notePcConditionElement.style.display ="block";
            desktopPcConditionElement.style.display ="none";
        }else if(event.target.value==2){
            notePcConditionElement.style.display ="none";
            desktopPcConditionElement.style.display ="block";
        };
    });
    //セレクトボックスの選択属性付与
    
    //セレクトボックスの結果取得
    document.querySelector('.btn-search').addEventListener('click',function(){
        let categoryId = document.getElementById('categoryId').value;
        if(categoryId==0){
        }else if(categoryId==1){
        }else if(categoryId==2){
        };
    })
});