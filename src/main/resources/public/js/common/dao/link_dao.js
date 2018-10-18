(function LinkDao(){
    window.linkDao = new function(){
        this.create = create;
        this.deleteLinks = deleteLinks;
        this.updateLinks = updateLinks;
    }
    
    /*
    Sends a create link request.
    Returns:
        - true, if the link is succesfully created.
        - false otherwise.
    */
    function create(link, successCallback, errorCallback){
        try{
            
            const path = "link";
            const request = new Request(dao.PUT, path, link);
                request.processValidResponse = successCallback;
                request.processInvalidResponse = errorCallback;
            
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    Deletes the given categories.
    Returns:
        - true, when deletion was successful.
        - false otherwise.
    */
    function deleteLinks(linkIds){
        try{
            if(linkIds == null || linkIds == undefined){
                throwException("IllegalArgument", "linkIds must not be null or undefined");
            }
            if(linkIds.length < 1){
                throwException("IllegalArgument", "linkIds must not be empty");
            }
            
            const path = "link";
            const response = dao.sendRequest(dao.DELETE, path, linkIds);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
    
    /*
    
    */
    function updateLinks(links){
        try{
            if(links == null || links == undefined){
                throwException("IllegalArgument", "links must not be null or undefined");
            }
            if(links.length < 1){
                throwException("IllegalArgument", "links must not be empty");
            }
            
            const path = "link";
            const response = dao.sendRequest(dao.POST, path, links);
            if(response.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();