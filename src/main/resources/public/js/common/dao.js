(function DAO(){
    window.dao = new function(){
        this.GET = "GET";
        this.POST = "POST";
        this.PUT = "PUT";
        this.DELETE = "DELETE";
        
        this.allowedMethods = [this.GET, this.POST, this.PUT, this.DELETE];
        
        this.sendRequest = sendRequest;
        this.sendRequestAsync = sendRequestAsync;
    }
    
    /*
    Sends HttpRequest based on the specified arguments
    Arguments:
        - method: The method of the request.
        - path: The target of the request.
        - content: The body of the request.
    Returns:
        - A promise with the result of the query
    Throws:
        - IllegalArgument exception, if method is not a string.
        - IllegalArgument exception, if method is unsupported.
        - IllegalArgument exception, if path is not a string.
    */
    function sendRequestAsync(method, path, content, handleLogout){
        const promise = new Promise(function(resolve, reject){
            sendAsyncRequest(resolve, reject, method, path, content, handleLogout);
        });
        
        return promise;
        
        function sendAsyncRequest(resolve, reject, method, path, content, handleLogout){
            const request = new XMLHttpRequest();
            try{
                validation(method, path);
                method = method.toUpperCase();
                
                if(handleLogout == null || handleLogout == undefined){
                    handleLogout = true;
                }
                
                content = content || "";
                if(typeof content === "object"){
                    content = JSON.stringify(content);
                }
                
                request.onload = function(){
                    const response = new Response(request);
                    if(handleLogout && response.status == ResponseStatus.UNAUTHORIZED){
                        authService.logout();
                    }else if(response.status === ResponseStatus.OK){
                        resolve(response)
                    }else{
                        reject(response);
                    }
                };
                request.onerror = function(){
                    reject(new Response(request));
                };
                
                request.open(method, path, 1);
                prepareRequest(request, method);
                
                request.send(content);
            }
            catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error", request.responseURL + " - ");
                reject(new Response(request));
            }
        }
    }
    
    /*
    Sends HttpRequest based on the specified arguments
    Arguments:
        - method: The method of the request.
        - path: The target of the request.
        - content: The body of the request.
    Returns:
        - The sent request.
    Throws:
        - IllegalArgument exception, if method is not a string.
        - IllegalArgument exception, if method is unsupported.
        - IllegalArgument exception, if path is not a string.
    */
    function sendRequest(method, path, content, handleLogout){
        validation(method, path);
        method = method.toUpperCase();
        if(handleLogout == null || handleLogout == undefined){
            handleLogout = true;
        }
        
        const request = new XMLHttpRequest();
        try{
            content = content || "";
            if(typeof content === "object"){
                content = JSON.stringify(content);
            }
            
            request.open(method, path, 0);
            prepareRequest(request, method);
            
            request.send(content);
            
            if(handleLogout && request.status == ResponseStatus.UNAUTHORIZED){
                authService.logout();
            }
        }
        catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error", request.responseURL + " - ");
        }finally{
            return new Response(request);
        }
    }
    
    function validation(method, path){
        if(!method || typeof method !== "string"){
            throwException("IllegalArgument", "method must be a string.");
        }
        method = method.toUpperCase();
        if(dao.allowedMethods.indexOf(method) == -1){
            throwException("IllegalArgument", "Unsupported method: " + method);
        }
        if(!path || typeof path !== "string"){
            throwException("IllegalArgument", "path must be a string.");
        }
    }
    
    function prepareRequest(request, method){
        if(method !== "GET"){
            request.setRequestHeader("Content-Type", "application/json");
        }
        
        request.setRequestHeader("Request-Type", "rest");
    }
})();

/*
Response object contains the response status, statusKey, and text of the qiven request.
*/
function Response(response){
    response = response || {
        status: null,
        responseText: null
    };
    const statusKey = responseStatusMapper.getKeyOf(response.status);
    
    this.statusKey = statusKey;
    this.status = response.status;
    this.response = response.responseText;
    
    this.toString = function(){
        return this.status + ": " + this.statusKey + " - " + this.response;
    }
}

(function ResponseStatusMapper(){
    window.responseStatusMapper = new function(){
        this.getKeyOf = getKeyOf;
    }
    
    /*
    Gets the key of the given status code.
    Arguments: 
        - statusCode: the status code
    Returns:
        - The key of the given status code
    Throws:
        - IllegalArgument exception if statusCode is undefined.
        - KeyNotFound exception if key not found.
    */
    function getKeyOf(statusCode){
        try{
            if(statusCode == undefined){
                throwException("IllegalArgument", "statusCode must not be null or undefined");
            }
            if(statusCode == null){
                return null;
            }
            if(typeof statusCode != "number"){
                throwException("IllegalArgument", "statusCode must be a number");
            }
            
            for(let key in ResponseStatus){
                if(ResponseStatus[key] == statusCode){
                    return key;
                }
            }
            
            throwException("KeyNotFound", "No key found for status code " + statusCode);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return null;
        }
    }
})();

/*
Enumeration contains response status codes for HttpRequest
*/
window.ResponseStatus = new function(){
    this.OK = 200;
    this.BAD_REQUEST = 400;
    this.UNAUTHORIZED = 401;
    this.FORBIDDEN = 403
    this.NOT_FOUND = 404;
    this.CONFLICT = 409;
    this.INTERNAL_SERVER_ERROR = 500;
    this.CONNECTION_REFUSED = 0;
}