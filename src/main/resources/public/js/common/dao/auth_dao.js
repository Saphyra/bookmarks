(function AuthDao(){
    window.authDao = new function(){ 
        this.login = login;
        this.logout = logout;
    }
    
    function login(credentials, successCallBack, errorCallBack){
        try{
            const request = new Request(dao.POST, "login", credentials);
                request.handleLogout = false;
                request.processValidResponse = successCallBack;
                request.processInvalidResponse = errorCallBack;
            return dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
    
    /*
        Sends the logout request.
    */
    function logout(successCallBack){
        try{
            const request = new Request(dao.DELETE, "logout");
                request.handleLogout = false;
                request.processValidResponse = successCallBack;
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return new Response();
        }
    }
})();