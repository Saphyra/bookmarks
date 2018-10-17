(function AuthService(){
    window.authService = new function(){
        scriptLoader.loadScript("js/common/dao/auth_dao.js");
        
        this.isAuthenticated = isAuthenticated;
        this.login = login;
        this.logout = logout;
    }
    
    /*
    Sends a request to check the user is logged in
    */
    function isAuthenticated(callBack){
        try{
            const request = new Request(dao.GET, "user/authenticated");
            request.handleLogout = false;
            request.processValidResponse = callBack;
            request.processInvalidResponse = function(){};
            return dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
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
    function login(credentials, successCallBack, errorCallBack){
        try{
           authDao.login(credentials, successCallBack, errorCallBack);
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
            authDao.logout(function(){
                sessionStorage.successMessage = "You logged out successfully.";
                window.location.href = "/";
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();