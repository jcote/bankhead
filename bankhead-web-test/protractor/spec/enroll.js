'use strict';

describe('bankhead enroll', function() {
  it('should greet the named user', function() {
    browser.get('http://localhost:9898/#/enroll');
    var ptor = protractor.getInstance();
    

    element(by.model('user.name')).sendKeys('JulieTest2');
    element(by.model('user.password')).sendKeys('mypassword');

    var enrollButton = element(by.css('[value="enroll"]'));
    enrollButton.click();
    
    var ptor = protractor.getInstance();
    var statusDiv = element(by.binding('alertText')); 
    ptor.wait(function() {
        return statusDiv.isDisplayed();
    }, 10.0);
    
    expect(statusDiv.getText()).toEqual('Success!');
  });

});
