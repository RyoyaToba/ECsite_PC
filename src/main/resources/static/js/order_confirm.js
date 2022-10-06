'use strict';

/**
 * 郵便番号から住所自動取得機能
 */

$(function(){
    $(document).on('click', '#get_address_btn', function(){
        $.ajax({
            url: 'http://zipcoda.net/api',
            dataType: 'jsonp',
            data: {
                zipcode: $('#destinationZipcode').val()
            },
            async: true
        }).done(function(data){
            console.dir(JSON.stringify(data));
            $('#destinationAddress').val(data.items[0].address);
        }).fail(function(XMLHttpRequest, textStatus, errorThrown){
            alert('正しい結果を得られませんでした。');
            console.log('XMLHttpRequest:' + XMLHttpRequest.status);
            console.log('textStatus:' + textStatus);
            console.log('errorThrown:' + errorThrown.message);
        });
    });
});

$(function(){
    var dtToday= new Date();
    dtToday.setDate(dtToday.getDate() + 2);
    var month= dtToday.getMonth() + 1;
    var day= dtToday.getDate();
    var year= dtToday.getFullYear();
    if(month < 10)
        month= '0' + month.toString();
    if(day < 10)
        day= '0' + day.toString();
    var minDate= year + '-' + month + '-' + day;
    $('#deliveryDate').attr('min', minDate);
});

$('#card').hide()
$('.radiobutton').on('change',function(){
	if($('.radiobutton:checked').val()==='1'){
		$('#card').hide()
	}else if($('.radiobutton:checked').val()==='2'){
		$('#card').show()
	}

})
