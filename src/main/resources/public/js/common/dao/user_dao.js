(function UserDao(){
    window.userDao = new function(){
        this.changePassword = changePassword;
        this.changeUserName = changeUserName;
        this.deleteAccount = deleteAccount;
        this.isUserNameExists = isUserNameExists;
        this.registerUser = registerUser;
    }
    
    function changePassword(password, oldPassword, successCallback, errorCallback){
        try{
            const path = "user/password";
            const body = {
                newPassword: password,
                oldPassword: oldPassword
            };
            
            const request = new Request(dao.POST, path, body);
                request.handleLogout = false;
                request.processValidResponse = successCallback;
                request.processInvalidResponse = errorCallback;
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
    Changes the username of the user.
    Arguments:
        - newUserName: the new username.
        - password: the password of the user.
    Returns:
        - new Response contains the result of the request.
    Throws:
        - IllegalArgument exception if newUserName or password is null or undefined.
    */
    function changeUserName(newUserName, password){
        try{
            if(newUserName == null || newUserName == undefined){
                throwException("IllegalArgument", "newUserName must not be null or undefined.");
            }
            if(password == null || password == undefined){
                throwException("IllegalArgument", "password must not be null or undefined.");
            }
            
            const path = "user/name";
            const body = {
                newUserName: newUserName,
                password: password
            };
            return dao.sendRequest("POST", path, body, false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
    Deletes all data of the account.
    Arguments:
        - password: The password of the user.
    Returns:
        - new Response contains the result of the request.
    Throws:
        - IllegalArgument exception if password is null or undefined.
    */
    function deleteAccount(password, successCallback, errorCallback){
        try{
            const path = "user";
            const body = {
                password: password
            };
            const request = new Request(dao.DELETE, path, body);
                request.handleLogout = false;
                request.processValidResponse = successCallback;
                request.processInvalidResponse = errorCallback;
            
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
    Checks if username already registrated.
    Arguments:
        - userName: the name to check.
    Returns:
        - false, if userName has not been registered.
        - true otherwise.
    Throws:
        - IllegalArgument exception if userName is null or undefined.
        - InvalidResult exception if the response cannot be recognized.
    */
    function isUserNameExists(userName, validationResult, validationError){
        try{
            const path = "user/name/exist";
            const body = {
                value: userName
            }
            const request = new Request(dao.POST, path, body);
                request.isResponseOk = function(response){
                    if(response.status == ResponseStatus.OK && response.response == "false"){
                        return true;
                    }
                    return false;
                };
                
                request.processValidResponse = function(){
                    registrationController.lastUserNameValid = true;
                    validationResult.process()
                };
                request.processInvalidResponse = function(response){
                    if(response.status == ResponseStatus.OK){
                        validationResult.setUserNameResult(validationError);
                        registrationController.lastUserNameValid = false;
                        validationResult.process();
                    }else{
                        throwException("BackendError:", response.toString());
                    }
                }
            
            
            const result = dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return true;
        }
    }

    /*
    Registrating new user.
    Arguments:
        - user: Object that contains the details of the new user.
    Returns:
        - true, if registration was successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if user is null or undefined.
        - UnknownServerError if request fails.
    */
    function registerUser(user){
        try{
            if(user == null && undefined){
                throwException("IllegalArgument", "user must not be null or undefined");
            }
            const result =  dao.sendRequest(dao.POST, "user/register", user);
            if(result.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownServerError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();