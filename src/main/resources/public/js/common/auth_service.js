(function AuthService(){
    window.authService = new function(){
        scriptLoader.loadScript("js/common/dao/auth_dao.js");
        
        this.login = login;
        this.logout = logout;
    }
    
    /*
        Sends an authentication request.
        Arguments:
            - userName: The userName of the user.
            - password: The password of the user.
        Returns:
            - True if authentication was successful.
            - False otherwise.
        Throws:
            - IllegalState exception upon bad result from dao.
            - UnhandledServerException exception upon unknown failure.
    */
    function login(userName, password){
        try{
            const result = authDao.login(userName, password);
            if(!result){
                throwException("IllegalState", "undefined result from dao.");
            }
            if(result.status == ResponseStatus.UNAUTHORIZED){
                return false;
            }else if(result.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnhandledServerException", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Sends a log out request, and redirects to index page.
    Throws:
        - IllegalState exception upon bad result from dao.
        - UnhandledServer upon unknown failure.
    */
    function logout(){
        try{
            const result = authDao.logout();
            if(!result){
                throwException("IllegalState", "undefined result from dao.");
            }
            if(result.status == ResponseStatus.OK){
                sessionStorage.successMessage = "Sikeres kijelentkezés!";
                sessionStorage.removeItem("characterId");
                window.location.href = "/";
            }else{
                throwException("UnhandledServer", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();