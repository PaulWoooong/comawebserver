var CheckboxGroup = Class.create();

//defining the rest of the class implementation
CheckboxGroup.prototype = {

   initialize: function(controlCheckbox, controlledCheckboxes) {	        
		this.controlCheckbox = $(controlCheckbox);
		this.controlledCheckboxes = controlledCheckboxes;
		
		this.controlCheckbox.onclick = 
		   this.updateControlledCheckboxes.bindAsEventListener(this);
		
   },

      // Set the state of each of the controlled checkboxs, to the state of 
      // control checkbox
   updateControlledCheckboxes: function() {	   
      var flag = $F(this.controlCheckbox);
	  this.controlledCheckboxes.each(function(cb) {		     
	       $(cb).checked=flag;
	  });
   },
};
