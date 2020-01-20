$(document).ready(function(){
	    $(".chat_head").click(function(){
        $(".chat_body").slideToggle('slow');
    });
	
	 $(".msg_head").click(function(){
        $(".msg_wrap").slideToggle('slow');
    });
	
		 $(".in").click(function(){
        $(".chat,.send").show();
    });
	
		$(".in").click(function(){
        $(".signin,.in").hide();
    });
	
	$(".close").click(function(){
		$('.msg_box').hide();
	});
	
		$(".user").click(function(){
		$('.msg_box').show();
		$('.msg_wrap').show();
	});
	
	/*$('textarea').keypress(
	function(e){
		if(e.which == 13){
			
		}
	});*/
	/*$('.name').keypress(
		function(e){
			if(e.which == 13){
				var name = $(".name").val();
				var dataString = 'name1='+ name;
				if(name=='')
				{
					alert("Please enter your name to start chat");
				}
				else
				{
					// AJAX Code To Submit Form.
					$.ajax({
							type: "POST",
							url: "read.php",
							data: dataString,
							cache: false,
							success: function(result){
								alert(result);
							}
						});
				}
				return false;
			}
	});*/

});



