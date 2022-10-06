'use strict';

/**
 * 郵便番号から住所自動取得機能
 */
window.onload=function(){
    document.getElementById('get_address_btn').onclick = function(){
     $.ajax({
            url: 'http://zipcoda.net/api',
            dataType: 'jsonp',
            data: {
                zipcode: $('#inputZipcode').val()
            },
            async: true
        }).done(function(data){
            console.dir(JSON.stringify(data));
            $('#inputAddress').val(data.items[0].address);
        }).fail(function(XMLHttpRequest, textStatus, errorThrown){
            alert('正しい結果を得られませんでした。');
            console.log('XMLHttpRequest:' + XMLHttpRequest.status);
            console.log('textStatus:' + textStatus);
            console.log('errorThrown:' + errorThrown.message);
        });
    }
}

