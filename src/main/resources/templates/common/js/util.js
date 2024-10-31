function getInternacionalizedMessage(messageId){
	var p = document.getElementById(messageId + "").textContent;
	return p;
}

/**
 * Covers all the website with a loading-waiting simple mask.
 * 
 * @author Carlos Mario
 * @argument msg message to show durin the "masking"
 */
function loadingMask() {

    $.LoadingOverlay("show", {
        background: "rgba(149, 149, 149, 0.3)",
    });
}

/**
 * Hides the last showed mask.
 * 
 * @author Carlos Mario
 */
function unmask() {
    $.LoadingOverlay("hide");
}

/*
 * ============================================================ functions to
 * show message -Sweet Alert-
 * ============================================================
 */

/**
 * 
 * @param {string}
 *            type
 * @param {string}
 *            title
 * @param {string}
 *            message
 */
function showMessage(type, title, message = '') {
	Swal.fire({
		  icon: type,
		  title: title,
		  text: message,
		})
}

function getBase64(file){
	var reader = new FileReader();
	   // reader.readAsDataURL(file);
	   var value;

// reader.onload = function(readerEvt) {
// var binaryString = readerEvt.target.result;
// showMessage('info', 'base64', binaryString);
// };
	   reader.onload = function() {
	        value = reader.result;
	    };
	   
	    reader.onerror = function() {
	        console.log('there are some problems');
	    };
	   reader.readAsDataURL(file);
	   return value;
}



