(function RegistrationController(){
    window.registrationController = new function(){
        scriptLoader.loadScript("js/common/dao/user_dao.js");
        this.lastUserNameQueried = null;
        this.lastUserNameValid = true;
        this.lastEmailQueried = null;
        this.lastEmailValid = true;
        
        this.validate = validate;
        this.sendForm = sendForm;
        
        $(document).ready(function(){
            addListeners();
        });
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
                username: userName,
                password: password,
                confirmPassword: confirmPassword
            };
            
            const result = userDao.registerUser(user);
            if(result){
                sessionStorage.successMessage = "Sikeres regisztráció!";
                loginController.login(userName, password);
            }else{
                notificationService.showError("Sikertelen regisztráció.");
            }
            passwordInput.value = "";
            confirmPasswordInput.value = "";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Validates the input fields.
    */
    function validate(){
        try{
            const isUsernameValid = validateUserName();
            const arePasswordsValid = validatePasswords();
            
            const isValid = isUsernameValid && arePasswordsValid && isEmailValid;
            if(isValid){
                document.getElementById("registration_button").disabled = false;
            }else{
                document.getElementById("registration_button").disabled = true;
            }
            
            return isValid;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        /*
        Validates the userName.
        Requirements:
            - At least 3 character long
            - Unique
        */
        function validateUserName(){
            try{
                const userName = document.getElementById("registration_username").value;
                const errorElementName = "#invalid_username";
                let errorMessage;
                
                if(userName.length < 3){
                    errorMessage = "Felhasználónév túl rövid (Minimum 3 karakter).";
                    this.lastUserNameValid = false;
                }else if(userName.length > 30){
                    errorMessage = "Felhasználónév túl hosszú (Maximum 30 karakter).";
                    this.lastUserNameValid = false;
                }else{
                    if(this.lasUserNameQueried !== userName){
                        this.lastUserNameValid = !userDao.isUserNameExists(userName);
                        this.lasUserNameQueried = userName;
                    }
                    
                    if(!this.lastUserNameValid){
                        errorMessage = "Felhasználónév foglalt.";
                    }
                }
                
                if(!this.lastUserNameValid){
                    activateErrorElement(errorElementName, errorMessage);
                }else{
                    deactivateErrorElement(errorElementName);
                }
                
                return this.lastUserNameValid;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        /*
        Validates the password and confirmPassword.
        Requirements:
            - At least 6 character long
            - Equals
        */
        function validatePasswords(){
            try{
                const password = document.getElementById("registration_password").value;
                const passwordErrorElementName = "#invalid_password";
                const confirmPassword = document.getElementById("registration_confirm_password").value;
                const confirmPasswordErrorElementName = "#invalid_confirm_password";
                
                let passwordResult = true;
                let confirmPasswordResult = true;
                
                if(password.length < 6){
                    activateErrorElement(passwordErrorElementName, "Jelszó túl rövid (Minimum 6 karakter).");
                    passwordResult = false;
                }
                
                if(password.length > 30){
                    activateErrorElement(passwordErrorElementName, "Jelszó túl hosszú (Maximum 30 karakter).");
                    passwordResult = false;
                }
                
                if(passwordResult && confirmPasswordResult && confirmPassword != password){
                    activateErrorElement(passwordErrorElementName, "A jelszavak nem egyeznek.");
                    activateErrorElement(confirmPasswordErrorElementName, "A jelszavak nem egyeznek.");
                    passwordResult = false;
                    confirmPasswordResult = false;
                }
                
                if(passwordResult){
                    deactivateErrorElement(passwordErrorElementName);
                }if(confirmPasswordResult){
                    deactivateErrorElement(confirmPasswordErrorElementName);
                }
                
                return passwordResult && confirmPasswordResult;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        /*
        Shows the error notification.
        Parameters:
            - elementName: the elements to show.
            - message: the error message.
        Throws:
            - IllegalArgument: if elementName is null or undefined.
        */
        function activateErrorElement(elementName, message){
            try{
                if(elementName == null || elementName == undefined){
                    throwException("IllegalArgument", "elementName must not be null or undefined.");
                }
                $(elementName).attr("title", message).fadeIn();
                document.getElementById("registration_button").disabled = true;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        /*
        Hides the error notification.
        Parameters:
            - elementName: the element to hide.
        Throws:
            - IllegalArgument: if elementName is null or undefined.
        */
        function deactivateErrorElement(elementName){
            try{
                if(elementName == null || elementName == undefined){
                    throwException("IllegalArgument", "elementName must not be null or undefined.");
                }
                $(elementName).fadeOut();
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
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