(function ChangePasswordController(){
    window.changePasswordController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        
        this.changePassword = changePassword;
        this.validateInputs = validateInputs;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    function changePassword(){
        try{
            const password1Input = document.getElementById("newpassword1");
            const password2Input = document.getElementById("newpassword2");
            const oldPasswordInput = document.getElementById("newpasswordoldpassword");
            
            const password1 = password1Input.value;
            const password2 = password2Input.value;
            const oldPassword = oldPasswordInput.value;
            
            const validationResult = validateInputs();
            if(validationResult.isValid){
                userDao.changePassword(
                    password1,
                    oldPassword,
                    function(){
                        notificationService.showSuccess("New password saved.");
                    },
                    function(response){
                        if(response.status == dao.UNAUTHORIZED){
                            notificationService.showError("Password is wrong.")
                        }else{
                            throwException("UnknownServerError", result.toString());
                        }
                    }
                );
                
                password1Input.value = "";
                password2Input.value = "";
                oldPasswordInput.value = "";
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
        try{
            const password1 = $("#newpassword1").val();
            const password2 = $("#newpassword2").val();
            const oldPassword = $("#newpasswordoldpassword").val();
            
            const submitButton = document.getElementById("newpasswordbutton");
            
            const invalidOldPassword = document.getElementById("invalid_newpasswordoldpassword");
            const invalidPassword1 = document.getElementById("invalid_newpassword1");
            const invalidPassword2 = document.getElementById("invalid_newpassword2");
            
            let newPassword1Valid = true;
            let newPassword2Valid = true;
            
            const response = {
                isValid: true,
                responses: []
            };
            
            if(oldPassword.length == 0){
                response.isValid = false;
                const errorMessage = "Please enter your password.";
                response.responses.push(errorMessage);
                invalidOldPassword.title = errorMessage;
                $(invalidOldPassword).fadeIn();
            }else{
                $(invalidOldPassword).fadeOut();
            }
            
            if(password1.length < 6){
                response.isValid = false;
                newPassword1Valid = false;
                const errorMessage = "Password is too short. (Minimum 6 characters)";
                response.responses.push(errorMessage);
                invalidPassword1.title = errorMessage;
                $(invalidPassword1).fadeIn();
            }
            
            if(password1.length > 30){
                response.isValid = false;
                newPassword1Valid = false;
                const errorMessage = "Password is too long. (Maximum 30 characters)";
                response.responses.push(errorMessage);
                invalidPassword1.title = errorMessage;
                $(invalidPassword1).fadeIn();
            }
            
            if(newPassword1Valid && newPassword2Valid){
                if(password1 != password2){
                    newPassword1Valid = false;
                    newPassword2Valid = false;
                    response.isValid = false;
                    const errorMessage = "Wrong confirm password.";
                    response.responses.push(errorMessage);
                    invalidPassword1.title = errorMessage;
                    invalidPassword2.title = errorMessage;
                    $(invalidPassword1).fadeIn();
                    $(invalidPassword2).fadeIn();
                }
            }
            
            if(newPassword1Valid){
                $(invalidPassword1).fadeOut();
            }
            if(newPassword2Valid){
                $(invalidPassword2).fadeOut();
            }
            
            submitButton.disabled = !response.isValid;
            
            return response;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return {
                isValid: false,
                responses: ["Unexpected error."]
            };
        }
    }
    
    function addListeners(){
        try{
            $("#newpassword1, #newpassword2, #newpasswordoldpassword").on("keyup focusin", function(e){
                const validationResult = changePasswordController.validateInputs();
                if(e.which == 13 && validationResult.isValid){
                    changePasswordController.changePassword();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();