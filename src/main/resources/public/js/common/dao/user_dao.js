(function UserDao(){
    window.userDao = new function(){
        this.isUserNameExists = isUserNameExists;
        this.registerUser = registerUser;
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
            const result = dao.sendRequest(dao.POST, path, body);

            if(result.status != ResponseStatus.OK){
                throwException("UnknownServerError", result.toString());
            }

            if(result.response === "true"){
                return true;
            }else if(result.response === "false"){
                return false;
            }else{
                throwException("InvalidResult", result.toString());
            }
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