(function UserDao(){
    window.userDao = new function(){
        this.changePassword = changePassword;
        this.changeUserName = changeUserName;
        this.deleteAccount = deleteAccount;
        this.isUserNameExists = isUserNameExists;
        this.registerUser = registerUser;
    }
    
    /*
    Changes the password of the user.
    Arguments:
        - password1: the new password of the user.
        - password2: the confirmation password.
        - oldPassword: the current password of the user.
    Returns:
        - new Response contains the result of the request.
    Throws:
        - IllegalArgument exception if password1, password2, oldPassword is null or undefined.
    */
    function changePassword(password, oldPassword){
        try{
            if(password == null || password == undefined){
                throwException("IllegalArgument", "password must not be null or undefined.");
            }
            if(oldPassword == null || oldPassword == undefined){
                throwException("IllegalArgument", "oldPassword must not be null or undefined.");
            }
            
            const path = "user/password";
            const body = {
                newPassword: password,
                oldPassword: oldPassword
            };
            return dao.sendRequest("POST", path, body, false);
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
    function deleteAccount(password){
        try{
            if(password == null || password == undefined){
                throwException("IllegalArgument", "password must not be null or undefined.");
            }
            
            const path = "user";
            const body = {
                password: password
            };
            return dao.sendRequest(dao.DELETE, path, body, false);
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
    function isUserNameExists(userName){
        try{
            if(userName == undefined || userName == null){
                throwException("IllegalArgument", "userName must not be null or undefined.");
            }
            const path = "user/name/exist";
            const body = {
                value: userName
            }
            const result = dao.sendRequestAsync(dao.POST, path, body);
            result.then(function(response){
                if(result.response === "true"){
                    return true;
                }else if(result.response === "false"){
                    return false;
                }else{
                    throwException("InvalidResult", result.toString());
                }
            });
            
            return result;
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