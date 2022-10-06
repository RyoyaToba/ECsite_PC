'use strict'

window.addEventListener('load',function(){
    
    //全てのチェックボックスを取得
    const parent = document.getElementById('note_pc_search_form');//「詳細検索フォームの全要素」
    const allInput = parent.getElementsByTagName('input');//「詳細検索フォームの全要素」の中の「全てのinputタグ」
    let allCheckbox = [];//空の配列
    for(let input of allInput){//「詳細検索フォームの全要素」の中の「全てのinputタグ」を1つずつ取り出す。
        if(input.type=="checkbox"){//その「inputタグ」のtype属性がcheckbox,あるいは全選択全解除であれば
            allCheckbox.push(input);//空の配列に追加する。
        }
    }
    //全てのチェックボックスにイベントを付与
    for(let checkbox of allCheckbox){
        checkbox.addEventListener('change',function(){
            getNumberOfSearches();
        })
    }
    let allButtonOfSelectAll = [];
    for(let input of allInput){
        if(input.className=='selectAll'){
            allButtonOfSelectAll.push(input);
        }
    }
    for(let button of allButtonOfSelectAll){
        button.addEventListener('click',function(){
            getNumberOfSearches();
        })
    }
    
    //Ajaxに関する記述を書く予定のメソッド
    const getNumberOfSearches=()=>{
        //alert('確認用');
        
        let hostUrl = 'http://localhost:8080/ec-202204c/item/getNumber';
        let totalMakerNum = document.getElementById('totalMakerNum').value;
        let totalPriceNum = document.getElementById('totalPriceNum').value;
        let totalColorNum = document.getElementById('totalColorNum').value;
        let totalMoniterSizeNum = document.getElementById('totalMoniterSizeNum').value;
        $.ajax({
            url: hostUrl,
            type: 'post',
            dataType: 'json',
            // フォーム要素の内容をハッシュ形式に変換
            data:{
                totalMakerNum:totalMakerNum,
                totalPriceNum:totalPriceNum,
                totalColorNum:totalColorNum,
                totalMoniterSizeNum:totalMoniterSizeNum
            },
            async:true
          })
          .done(function(data) {
            //console.log(data.number);
            document.getElementById('number_of_searches').innerText = +data.number+"件ヒットしました。";
          })
          .fail(function() {
              alert('fail!');
          });
          
    }
    
    
})