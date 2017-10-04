
var afterInit = function() {
	
	var redirectUrl = "./password-recovery";
	
	var submit = function(code) {
		var dataObj;
		if ( typeof code != "string" ) {
			dataObj = {
					   codigo: location.hash.substring( 2 ),
					   senha: $('#password').val(),
					   confSenha: $( '#password-confirm' ).val()
				  	  }
			
			$.ajax({
				  type: "POST",
				  url: redirectUrl,
				  data: dataObj,
				  success: function( e ){
					  $("#message-error").html("A senha foi redefinida.");
					  $("#message-error").css("color", "green");
					  
					  window.setTimeout( function() {
						  window.location.replace("./autenticacao");					  
					  }, 3000);
				  },
				  error: function( error ){
					  $("#message-error").html(error.responseJSON.message);
					  $("#message-error").css("color", "red");
				  }
				});
			
		} else {
			$.ajax({
				  type: "GET",
				  url: redirectUrl + code,
				  data: null,
				  success: function( e ){
					  console.log("Chamou o GET!")
				  },
				  error: function( error ){
					  $("#message-error").html(error.responseJSON.message);
					  $("#message-error").css("color", "red");
				  }
				});
		}
    };

	$("#submit").click(submit);
	$('#password').keypress(function(e) {
	    if(e.which == 13) {
	    	submit(null);
	    }
	});
	$('#password-confirm').keypress(function(e) {
	    if(e.which == 13) {
	    	submit(null);
	    }
	});
	

	function navHash(){
		hash = location.hash.substring(1);
		if (hash == "") return;
		submit(hash);
	}
    $( window ).on("hashchange", function() {
		navHash();
	}).trigger('hashchange');
    
};

$(afterInit);