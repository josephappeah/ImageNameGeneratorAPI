
//================================================================//
//					justice API js
//		    		Created on : Jun 23, 2016
//					Author     : jappeah
//================================================================//


var imagename, image, api_url; 

window.setInterval(function(){
    $("#intro-button").addClass('animated bounce');
    window.setTimeout(function(){	
		$("#intro-button").removeClass('animated bounce');
    },2000);
},10000);

function httpPost(url,image){
	var xhr = createCORSRequest('POST',url);
	if(xhr){
		xhr.onload = function(){
			imagename = xhr.responseText;
			displayTestingArea();
		}
	}
	xhr.send(image);
}

function displayTestingArea(){
	document.getElementById('image-name-text').innerHTML = "The image name would be <span id=\"image-name\">" + imagename +"</span>";
	$('#image-update').addClass('animated zoomIn');
	setTimeout(function(){
		$('#image-update').removeClass('animated zoomIn');
	},2000);
}

function createCORSRequest(method,url){
	var xhr = new XMLHttpRequest();
		if ("withCredentials" in xhr) {
			xhr.open(method, url, false);
		}else if (typeof XDomainRequest != "undefined") {
			xhr = new XDomainRequest();
			xhr.open(method, url);
		} else {
			xhr = null;
		}
	return xhr;
}

function tryItButton(){
	document.getElementById("file").click();
	$('#file').change(function(){
		uploadImage();
	});
}

function uploadImage(){
	api_url = '/v1/image_name_generator?prefix='+ $('#image-prefix').val().trim() + '&suffix=' + $('#image-suffix').val().trim();
	var uploaded_image = document.getElementById('file').files[0];
	httpPost(api_url,uploaded_image);
}
	
function imageUploadButton(){
		if(document.getElementById("pseudo-upload-button").innerHTML === 'get image name'){
			uploadImage();
			document.getElementById("pseudo-upload-button").innerHTML = 'select an image';
		}else if(document.getElementById("pseudo-upload-button").innerHTML === 'select an image'){
			document.getElementById("file").click();
			document.getElementById("pseudo-upload-button").innerHTML = 'get image name';
		}
}


function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
        $('#uploaded-image')
        	.attr('src', e.target.result)

        };
    reader.readAsDataURL(input.files[0]);
	}
}

$(document).ready(function(){
$('#intro-button').click(function(){
    $('html, body').animate({scrollTop : $("#"+"api_tests").offset().top},'slow');
	return false;
});



});