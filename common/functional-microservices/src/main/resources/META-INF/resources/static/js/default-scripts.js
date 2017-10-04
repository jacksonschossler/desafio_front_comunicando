// Firefox 1.0+
var isFirefox = typeof InstallTrigger !== 'undefined';

// Chrome 1+
var isChrome = !!window.chrome && !!window.chrome.webstore;

if( !isChrome && !isFirefox )
{
	$( "body" ).ready(function() {
		$("body").prepend("<div id='message'>"+
			        "<div class='alert alert-info' role='alert'>"+
			           "<button type='button' id='close-alert-browser' class='close' data-dismiss='alert'>&times;</button>"+
			            "<b>Atenção:</b> O navegador utilizado não é homologado, o sistema pode não funcionar corretamente, favor utilizar um dos navegadores homologados. "+
			            "<a href='https://www.google.com.br/chrome/browser/desktop/index.html' target='_blank'>Google Chrome</a> - "+
			            "<a href='http://br.mozdev.org/firefox/download/' target='_blank'>Mozila Firefox</a>"+
			        "</div>"+
			    "</div>");
		
		$( "#close-alert-browser" ).click(function() {
			$( "#message" ).remove();
		});
	});
}

var counter = 1800;
var timeOut;
timeOut = $.timeoutDialog();

if ( dwr && dwr.engine )
{
	dwr.engine.setTextHtmlHandler(function() {
		window.alert(/*[[#{sessionExpired}]]*/);
		document.location = './';
	});

	dwr.engine.setPreHook( function() {
		//angular.element("body").scope().loading = true;
	});

	dwr.engine.setPostHook(function() {
		timeOut.keepAliveFromRequest();
		//angular.element("body").scope().loading = false;
	});
}

//user session
window.user = /*[[${#authentication} ? ${#authentication.principal} : null]]*/ null;