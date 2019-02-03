<script>	
	
	function fill_tdropdown(postcode, suburb, targetdropdown){
		
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
				$('#output_box').html('returned' + data.postcodelist[0]);
				data.postcodelist.forEach(function (value) {
					html_code += '<option>'+ value +'</option>';
					//alert('returned data :' + value)
					//$('#output_box').html(html_code);
					$('#' + targetdropdown).html(html_code);
				});
			}
		});		
	}	
	
	$(document).on('change', '#city', function(){
		//alert("input suburb changed")
		var pcode = $('#schoolpostcode').val();
		var suburb = $('#city').val();
		fill_tdropdown(pcode, suburb, 'sname');
	});
	
	$(document).on('change', '#sname', function(){
		alert("input school changed")
	});
	
	$("#schoolpostcode").on("keyup",function() {
		  var maxLength = $(this).attr("maxlength");
		  if(maxLength == $(this).val().length) {			  
			var pcode = $('#schoolpostcode').val();  
		    //alert("You can't write more than " + pcode +" chacters")
		    fill_tdropdown(pcode, 'nosub', 'city');
		  }
	});
	
</script>