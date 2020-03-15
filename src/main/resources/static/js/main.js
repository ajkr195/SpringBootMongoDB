"use strict";
//# sourceURL=main.js

 function deleteImg(obj) {
     
	console.log(obj.id);
    $.ajax({
    	url: "/" + obj.id,
        type: 'delete',
        success:function(result){
        	var parentNode = obj.parentNode;
        	var grandNode = parentNode.parentNode;
        	grandNode.removeChild(parentNode);
        	console.log("Success !");

        },
        error:function(){
        	console.log("Error !");
        }
    
    });

}