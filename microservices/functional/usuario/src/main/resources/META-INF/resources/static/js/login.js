/**
 * 
 */

var afterInit = function() {
	
	var redirectUrl="./authenticate";
    var passwordRecovery = false;
	
	var submit = function() {
		$.ajax({
			  type: "POST",
			  url: redirectUrl,
			  data: {
				  email: $('#email').val(),
				  senha: $('#password').val()
			  },
			  success: function( redirectUrl ){
				  
				  if ( passwordRecovery )
				  {
					  $("#message-error").html("Um email de redefinição de senha foi enviado.");
					  $("#message-error").css("color", "green");
				  } else
				  {
					  window.location.replace("/" + redirectUrl); 
				  }
			  },
			  error: function( error ){
				  $("#message-error").html(error.responseJSON.message);
				  $("#message-error").css("color", "red");
			  }
			});
    };

	$("#submit").click(submit);
	$('#email').keypress(function(e) {
	    if(e.which == 13) {
	    	submit();
	    }
	});
	$('#password').keypress(function(e) {
	    if(e.which == 13) {
	    	submit();
	    }
	});
	
	function returnToLogin() {
		$('.login').show();
		$('.resetPass').hide();
		$("#message-error").html("");
		$("#submit").html("Acessar");
		window.location.replace("./");
	}
	
	$('#returnToLoginBtn').click(returnToLogin);
	
    $( window ).on("hashchange", function() {
		hash = location.hash.substring( 2 );
		if (hash == "reset-password"){
			passwordRecovery = true;
			redirectUrl="./reset-password";
			$('.login').hide();
			$('.resetPass').show();
			
			$("#submit").html("Enviar email");
			$("#email").focus();
		}
	}).trigger('hashchange');
	    
};

$(afterInit);

