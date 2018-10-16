(function AuthDao(){
    window.authDao = new function(){ 
        this.login = login;
        this.logout = logout;
    }
    
    /*
    Requests the accessToken from the server.
    Parameters:
        - userName: The user name of the user.
        - password: The password of the user.
        - remember: True, if the user must be kept logged in.
    Returns:
        - true if login successful.
        - false otherwise.
    Throws:
        - IllegalArgument exception if userName, password or remember is null of undefined.
    */
    function login(userName, password, remember){
        try{
            if(userName == null || userName == undefined){
                throwException("IllegalArgument", "userName must not be null or undefined");
            }
            if(password == null || password == undefined){
                throwException("IllegalArgument", "password must not be null or undefined");
            }
            if(remember == null || remember == undefined){
                throwException("IllegalArgument", "remember must not be null or undefined");
            }
            
            return dao.sendRequestAsync("POST", "login", {userName: userName, password: password, remember: remember}, false);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
        Sends the logout request.
    */
    function logout(){
        try{
            return dao.sendRequestAsync("DELETE", "logout");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
})();