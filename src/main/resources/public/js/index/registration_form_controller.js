(function RegistrationController(){
    window.registrationController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        this.lastUserNameQueried = null;
        this.lastUserNameValid = true;
        
        this.validate = validate;
        this.sendForm = sendForm;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    function validate(){
        const validationResult = new ValidationResult();
        
        const password = document.getElementById("registration_password").value;
        const passwordErrorElementName = "#invalid_password";
        
        const confirmPassword = document.getElementById("registration_confirm_password").value;
        const confirmPasswordErrorElementName = "#invalid_confirm_password";
        
        const userName = document.getElementById("registration_username").value;
        const errorElementName = "#invalid_username";
        
        if(password.length < 6){
            validationResult.setPasswordResult(new ValidationError(passwordErrorElementName, "Password is too short. (Minimum 6 characters)."));
        }
        
        if(password.length > 30){
            validationResult.setPasswordResult(new ValidationError(passwordErrorElementName, "Password is too long. (Maximum 30 characters)."));
        }
        
        if(confirmPassword != password){
            validationResult.setConfirmPassworsResult(new ValidationError(confirmPasswordErrorElementName, "Confirm password does not match."));
        }
        
        if(userName.length < 3){
            validationResult.setUserNameResult(new ValidationError(errorElementName, "User name is too short (Minimum 3 characters)."));
        }else if(userName.length > 30){
            validationResult.setUserNameResult(new ValidationError(errorElementName, "User name is too long. (Maximum 30 characters)."));
        }
        
        if(validationResult.isUserNameValid()){
            if(registrationController.lastUserNameQueried !== userName){
                registrationController.lastUserNameQueried = userName;
                userDao.isUserNameExists(userName, validationResult.clone(), new ValidationError(errorElementName, "User name already registered."));
                validationResult.setUserNameResult(new ValidationError(errorElementName, "Checking in progress..."));
            }else if(!registrationController.lastUserNameValid){
                validationResult.setUserNameResult(new ValidationError(errorElementName, "User name already registered."));
            }
        }
        
        validationResult.process();
    }
    
    /*
    Sends the registration form.
    */
    function sendForm(){
        try{
            const userNameInput = document.getElementById("registration_username");
            const passwordInput = document.getElementById("registration_password");
            const confirmPasswordInput = document.getElementById("registration_confirm_password");
            
            const userName = userNameInput.value;
            const password = passwordInput.value;
            const confirmPassword = confirmPasswordInput.value;
            
            const user = {
                userName: userName,
                password: password
            };
            
            const result = userDao.registerUser(user);
            if(result){
                notificationService.showSuccess("Account registered successfully.")
                document.getElementById("login_username").value = userName;
            }else{
                notificationService.showError("Unexpected error occurred.");
            }
            
            userNameInput.value = "";
            passwordInput.value = "";
            confirmPasswordInput.value = "";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Adds listeners to registrationinput elements.
    */
    function addListeners(){
        try{
            $(".registrationinput").on("keyup focusin", function(e){
                const isValid = registrationController.validate()
                if(isValid && e.which == 13){
                    registrationController.sendForm();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();

function ValidationResult(){
    let passwordResult = new ValidationSuccess("#invalid_password");
    let confirmPasswordResult = new ValidationSuccess("#invalid_confirm_password");
    let userNameResult = new ValidationSuccess("#invalid_username");
    
    this.process = function(){
        if(isValid()){
            document.getElementById("registration_button").disabled = false;
        }else{
            document.getElementById("registration_button").disabled = true;
        }
        
        passwordResult.process();
        confirmPasswordResult.process();
        userNameResult.process();
    }
    
    function isValid(){
        return passwordResult.isValid && confirmPasswordResult.isValid && userNameResult.isValid;
    }
    
    this.setPasswordResult = function(newResult){
        passwordResult = newResult;
    }
    
    this.setConfirmPassworsResult = function(newResult){
        confirmPasswordResult = newResult;
    }
    
    this.setUserNameResult = function(newResult){
        userNameResult = newResult;
    }
    this.isUserNameValid = function(){
        return userNameResult.isValid;
    }
    
    this.clone = function(){
        const copy = new ValidationResult();
        copy.setPasswordResult(passwordResult);
        copy.setConfirmPassworsResult(confirmPasswordResult);
        copy.setUserNameResult(userNameResult);
        return copy;
    }
}

function ValidationSuccess(id){
    this.id = id;
    this.isValid = true;
    this.process = function(){
        $(id).fadeOut();
    }
}

function ValidationError(id, message){
    this.id = id;
    this.message = message;
    
    this.isValid = false;
    
    this.process = function(){
        $(id).attr("title", message).fadeIn();
    }
}