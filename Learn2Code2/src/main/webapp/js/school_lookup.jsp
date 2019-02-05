
<script>	
	
    //---- Function for filling a dropdown select from target data ----//
	function fill_tdropdown(postcode, suburb, targetdropdown, inputid){
		
		// Variable for storeing the final html code for insertion			
		var html_code = '';
		
		// Datastring contructed from the input elements to the function
		var datastring = 'postcode=' + postcode + '&suburb=' + suburb;
					
		//AJAX Serlvet Request to Get JSON Data	
		$.ajax( {
			url: 'GetCSVData',	
			type: 'POST',
			dataType: 'json',
			data: datastring,
			success: function(data) {
				if(data.responsecode == 100){
					$('#output_box').html('returned' + data.postcodelist[0]);
					data.postcodelist.forEach(function (value) {
						html_code += '<option>'+ value +'</option>';
						//$('#output_box').html(html_code);
						$('#' + targetdropdown).html(html_code);
					});
					$(inputid).attr("class", "form-control");
					$(inputid).prop('disabled', true);
				} else {
					$(inputid).attr("class", "form-control is-invalid");
				}	
			}
		});		
	}	
	
	//---- Event handler for loading school info on change of suburb ----//
	$(document).on('change', '#city', function(){
		var pcode = $('#schoolpostcode').val();
		var suburb = $('#city').val();
		fill_tdropdown(pcode, suburb, 'sname');
		$('#sname').prop('disabled', false);
	});
	
	//---- Event handler for keyup of schoolpostcode that loads suburb dropdown ----//	
	$("#schoolpostcode").on("keyup",function() {		  	 	
		  var maxLength = $(this).attr("maxlength");
		  if(maxLength == $(this).val().length) {			  
			var pcode = $('#schoolpostcode').val();  
		    fill_tdropdown(pcode, 'nosub', 'city', '#schoolpostcode');
		    $('#city').prop('disabled', false);
		  }
	});
	
	//---- Event handler for checking validity of phone----//
	$("#schoolpostcode").focusout(function() {		
		if($(this).val().length != 4){
			$('#schoolpostcode').val('');
		} 
	});
	
	//---- Event handler for checking validity of email input----//	
	$("#email").focusout(function() {
		
		// Variable for storeing the final html code for insertion			
		var emailaddr = $('#email').val(); ;
		
		// Datastring contructed from the input elements to the function
		var datastring = 'email=' + emailaddr;
		
		$.ajax( {
			url: 'CheckEmail',	
			type: 'POST',
			dataType: 'json',
			data: datastring,
			success: function(data) {
				if(data.responsecode == 100){
					$("#email").attr("class", "form-control");
				} else if (data.responsecode == 200){
					$("#email").attr("class", "form-control is-invalid");
				}
			}
		});	
		
	});
	
	
	//---- Event handler for checking validity of phone----//
	$("#phone").focusout(function() {
		if($(this).val().length > 7){
			$(this).attr("class", "form-control");
		} else {
			$(this).attr("class", "form-control is-invalid");
		}
	});
	
	//---- Event handler for resetting the location inputs----//
	$("#reset-postcode").click(function() {  
		  $('#schoolpostcode').prop('disabled', false);
		  $('#schoolpostcode').val('');		  
		  $('#city').prop('disabled', true);
		  $('#sname').prop('disabled', true);
		  
	});
	
	function validate(evt) {
		  var theEvent = evt || window.event;

		  // Handle paste
		  if (theEvent.type === 'paste') {
		      key = event.clipboardData.getData('text/plain');
		  } else {
		  // Handle key press
		      var key = theEvent.keyCode || theEvent.which;
		      key = String.fromCharCode(key);
		  }
		  var regex = /[0-9]|\./;
		  if( !regex.test(key) ) {
		    theEvent.returnValue = false;
		    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
    }
	
	//---- Event handler for submitting the data----//
	$("#submit").click(function() {  
		  var formdata = $("#signup").find("select,textarea, input").serialize();		  
		  formdata += '&postcode=' + $('#schoolpostcode').val();	  
		  
		  //Add check for input in every box
		  if( !$('fname').val()) {
			  $("fname").alert()
		  } else {
			  $.ajax( {
					url: 'register',	
					type: 'POST',
					dataType: 'json',
					data: formdata,
					success: function(data) {
					}
				});
		  }		  		  
	});
	
</script>