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
    function sendRequestAsync(request){
        if(request == null || request == undefined){
            throwException("IllegalArgument", "request must not be null or undefined.");
        }
        request.validate();
        
        const xhr = new XMLHttpRequest();
            xhr.open(request.method, request.path, 1);
            prepareRequest(xhr, request.method);
        
            xhr.onload = function(){
                const response = new Response(xhr);
                if(request.handleLogout && response.status == ResponseStatus.UNAUTHORIZED){
                    authService.logout();
                }else {
                    request.processResponse(response);
                }
            };
            xhr.onerror = function(){
                request.processErrorResponse(new Response(xhr));
            };
            
            
            xhr.send(request.body);
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
    Request object for processing async BackEnd calls.
    Fields:
        - method:
        - path:
        - body: The request body. Will be converted to JSON if object.
        - handleLogout: if true, auto logut when response status is UNAUTHORIZED
        - state: helper field for result processing
    Methods:
        - processResponse: will be called by dao when xhr request returns. Parameter: response object of the xhr
        - isResponseOk: determinated if the request is Ok. If it is, processValidResponse, if not, processInvalidResponse will be called.
        - convertResponse: converts the response of the xhr response.
        - processValidResponse: will be called when xhr response is valid.
        - processInvalidResponse: will be called when xhr response is not valid.
        - processErrorResponse: will be called when xhr request fails.
        - validate: validates if the Request is valid for sending.
*/
function Request(method, path, body){
    this.method = method;
    this.path = path;
    this.body = processBody(body);
    this.handleLogout = true;
    this.state = {};
    
    function processBody(body){
        if(body == null || body == undefined){
            return "";
        }
        if(typeof body == "object"){
            return JSON.stringify(body);
        }
        return body;
    }
    
    this.processResponse = function(response){
        if(this.isResponseOk(response)){
            this.processValidResponse(this.convertResponse(response), this.state);
        }else{
            this.processInvalidResponse(response, this.state);
        }
    }
    
    this.isResponseOk = function(response){
        return response.status === ResponseStatus.OK;
    }
    
    this.convertResponse = function(response){
        return response;
    }
    
    this.processValidResponse = function(response, state){
        console.log("Using no overridden processValidResponse");
    }
    
    this.processInvalidResponse = function(response, state){
        logService.log(response.toString(), "warn", "Invalid response from BackEnd: ");
    }
    
    this.processErrorResponse = function(response){
        logService.log(response.toString(), "error", "Invalid response from BackEnd: ");
    }
    
    this.validate = function(){
        if(!this.method || typeof this.method !== "string"){
            throwException("IllegalArgument", "method must be a string.");
        }
        this.method = this.method.toUpperCase();
        if(dao.allowedMethods.indexOf(this.method) == -1){
            throwException("IllegalArgument", "Unsupported method: " + this.method);
        }
        if(!this.path || typeof this.path !== "string"){
            throwException("IllegalArgument", "path must be a string.");
        }
    }
}

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