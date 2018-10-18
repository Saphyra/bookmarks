(function ChangeUsernameController(){
    window.changeUserNameController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        
        this.lastUsernameValid = false;
        this.lastUsernameQueried = "";
        
        this.changeUserName = changeUserName;
        this.validateInputs = validateInputs;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    function changeUserName(){
        try{
            const newUserNameInput = document.getElementById("newusername");
            const passwordInput = document.getElementById("newusernamepassword");
            
            const newUserName = newUserNameInput.value;
            const password = passwordInput.value;
            
            const validationResult = validateInputs();
            if(validationResult.isValid){
                const result = userDao.changeUserName(
                    newUserName,
                    password,
                    function(){
                        notificationService.showSuccess("Username saved.");
                    },
                    function(response){
                        if(response.status == dao.UNAUTHORIZED){
                            notificationService.showError("Password is wrong.");
                        }else if(response.status == dao.BAD_REQUEST){
                            notificationService.showError("Username is already in use.");
                        }else{
                            throwException("UnknownServerError", result.toString());
                        }
                    }
                );
                
                passwordInput.value = "";
            }else{
                for(let mindex in validationResult.responses){
                    notificationService.showError(validationResult.responses[mindex]);
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function validateInputs(){
        const newUserName = $("#newusername").val();
        const password = $("#newusernamepassword").val();
        
        const userNameErrorElementName = document.getElementById("invalid_newusername");
        const passwordErrorElementName = document.getElementById("invalid_newusernamepassword");
        
        const validationResult = new ValidationResult();
        
        if(password.length == 0){
            validationResult.setPasswordResult(new ValidationError(passwordErrorElementName, "Please enter your password."));
        }
        
        if(newUserName.length < 3){
            validationResult.setUserNameResult(new ValidationError(userNameErrorElementName, "User name is too short (Minimum 3 characters)."));
        }else if(newUserName.length > 30){
            validationResult.setUserNameResult(new ValidationError(userNameErrorElementName, "User name is too long. (Maximum 30 characters)."));
        }else if(changeUserNameController.lastUserNameQueried != newUserName){
            changeUserNameController.lastUserNameQueried = newUserName;
            validationResult.setUserNameResult(new ValidationError(userNameErrorElementName, "Checking in progress..."));

            userDao.isUserNameExists(
                newUserName,
                function(result, state){
                    changeUserNameController.lastUserNameValid = true;
                    changeUserNameController.validateInputs();
                },
                function(response, state){
                    if(response.status == ResponseStatus.OK){
                        changeUserNameController.lastUserNameValid = false;
                        changeUserNameController.validateInputs();
                    }else{
                        throwException("BackendError:", response.toString());
                    }
                }
            );
        }else if(!changeUserNameController.lastUserNameValid){
            validationResult.setUserNameResult(new ValidationError(userNameErrorElementName, "User name already registered."));
        }
        
        validationResult.process();
    }
    
    function addListeners(){
        try{
            $("#newusername, #newusernamepassword").on("keyup focusin", function(e){
                const validationResult = changeUserNameController.validateInputs();
                if(e.which == 13 && validationResult.isValid){
                    changeUserNameController.changeUserName();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();

function ValidationResult(){
    let passwordResult = new ValidationSuccess("#invalid_newusernamepassword");
    let userNameResult = new ValidationSuccess("#invalid_newusername");
    
    this.process = function(){
        if(isValid()){
            document.getElementById("newusernamebutton").disabled = false;
        }else{
            document.getElementById("newusernamebutton").disabled = true;
        }
        
        passwordResult.process();
        userNameResult.process();
    }
    
    function isValid(){
        return passwordResult.isValid && userNameResult.isValid;
    }
    
    this.setPasswordResult = function(newResult){
        validate(newResult);
        passwordResult = newResult;
    }
    
    this.setUserNameResult = function(newResult){
        validate(newResult);
        userNameResult = newResult;
    }
    this.isUserNameValid = function(){
        return userNameResult.isValid;
    }
    
    function validate(newResult){
        if(newResult == null || newResult == undefined){
            throwException("IllegalArgument", "newResult must not be null or undefined.");
        }
    }
    
    this.clone = function(){
        const copy = new ValidationResult();
        copy.setPasswordResult(passwordResult.clone());
        copy.setUserNameResult(userNameResult.clone());
        return copy;
    }
}

function ValidationSuccess(id){
    this.id = id;
    this.isValid = true;
    this.process = function(){
        $(id).fadeOut();
    }
    
    this.clone = function(){
        return new ValidationSuccess(this.id);
    }
}

function ValidationError(id, message){
    this.id = id;
    this.message = message;
    
    this.isValid = false;
    
    this.process = function(){
        $(id).attr("title", message).fadeIn();
    }
    
    this.clone = function(){
        return new ValidationError(this.id, this.message);
    }
}