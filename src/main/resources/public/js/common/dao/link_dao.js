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
    */
    function deleteLinks(linkIds, successCallback, errorCallback){
        try{
            const path = "link";
            const request = new Request(dao.DELETE, path, linkIds);
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
    
    */
    function updateLinks(links, successCallback, errorCallback){
        try{
            const path = "link";
            const request = new Request(dao.POST, path, links);
                request.processValidResponse = successCallback;
                request.processInvalidResponse = errorCallback;
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();