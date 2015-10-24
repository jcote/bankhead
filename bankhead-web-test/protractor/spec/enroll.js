'use strict';

describe('bankhead enroll', function() {
  it('should greet the named user', function() {
	    browser.get('http://localhost:9898/#/enroll').then(function() {
	        
		    element(by.model('user.name')).sendKeys('JulieTest2');
		    element(by.model('user.password')).sendKeys('mypassword');
		
		    var enrollButton = element(by.css('[value="enroll"]'));
		    enrollButton.click();
		    
		    var statusDiv = element(by.binding('alertText')); 

		    
		    expect(statusDiv.getText()).toEqual('Success!');
	    });
  });

});
