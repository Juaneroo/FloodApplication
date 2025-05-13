$(document).ready(function() {

	// Validate the form first
	$("#updateStreamer").validate({
		submitHandler : function(event) {
			updateModel();
		}
	});

	/**
	 * creates a studio using Ajax
	 */
	function updateModel() {

		// Security token
		var token = $("meta[name='_csrf']").attr("content");
		
		//var t = getBase64(document.getElementById('idFrontalPhoto').files[0]);
		var lady = {
			id : 0,
			// hotname : '' + $("#txtUsername").val(),
			// email : '' + $("#txtEmail").val(),
			// password : '' + $("#txtPassword").val(),
			idFrontalPhoto : '' + $("#idFrontalPhoto").val(),
			idRearPhoto : '' + $("#idRearPhoto").val(),
			active : false
		};
		console.log(window.location.pathname);
		console.log(window.location);
		
		var form = $("#updateStreamer")[0];
		$.ajax({
			type : "POST",
			url : window.location,
			data : new FormData(form),
			enctype : 'multipart/form-data',
			processData : false, 
			contentType : false,
			cache : false,
			beforeSend : function(xhr) {
				// $('#default-modal').modal('hide');
				loadingMask("Actualizando...");
			},
			success : function(result) {

				unmask();
				$("#dt-opt").load('?loadFragment=true');

				if (result.statusCode === 201) {
					showMessage('success', '¡Cool!', result.message);
				} else {
					showMessage('warning', '¡Mmm!', result.message);
				}
			},
			error : function(xhr, status, error) {
				unmask();
				var result = xhr.responseJSON;
				showMessage('error', '¡Oopsss!', result.errorMessage);

			}
		});
	}

	// SUBMIT FORM
	// $("#formEditStudio").submit(function (event) {
	// // Prevent the form from submitting via the browser.
	// event.preventDefault();
	// editStudio();
	// });
	//
	// /**
	// * Edits a stduio.
	// */
	// function editStudio() {
	//
	// var token = $("meta[name='_csrf']").attr("content");
	//
	// var studio = {
	// id: $("#studioId2").val(),
	// name: '' + $("#txtStudioName2").val(),
	// active: Boolean($("#chkStudioActive2").val())
	// };
	//
	// var studioId = $("#studioId2").val();
	// $.ajax({
	// type: "PUT",
	// contentType: "application/json",
	// url: 'studios/' + studioId,
	// data: JSON.stringify(studio),
	// dataType: 'json',
	// beforeSend: function (xhr) {
	// xhr.setRequestHeader('X-CSRF-TOKEN', token);
	// loadingMask('Editando estudio...');
	// },
	// success: function (result, status, xhr) {
	//
	// $('#estudio-edit-modal').modal('hide');
	// $("#dt-opt").load('?loadFragment=true');
	//
	// showMessage('success', '¡Perfecto!', result.message);
	// unmask();
	// resetFormEdit();
	//
	// },
	// error: function (xhr, status, error) {
	// var result = xhr.responseJSON;
	//
	// if (xhr.status === 500) {
	// showMessage('error', '¡Oopsss!', result.message);
	// } else {
	// showMessage('error', '¡Oopsss!', 'Algo ha salido mal. Intenta mas tarde
	// :(');
	// }
	//
	// unmask();
	// }
	// });
	// }
	//
	//
	// function resetFormEdit() {
	// $("#studioId2").val("");
	// $("#txtStudioName2").val("");
	// $("#chkStudioActive2").val("");
	// }
	//
	// function resetFormSave() {
	// $("#txtStudioName").val("");
	// $("#chkStudioActive").val("");
	// }
});