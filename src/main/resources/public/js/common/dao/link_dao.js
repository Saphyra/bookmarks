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
    function create(label, url, root){
        try{
            if(label == null || label == undefined || label.length == 0){
                throwException("IllegalArgument", "label must not be null, undefined or empty.");
            }
            if(url == null || url == undefined){
                throwException("IllegalArgument", "url must not be null or undefined.");
            }
            if(root == null || root == undefined){
                throwException("IllegalArgument", "root must not be null or undefined.");
            }
            
            const path = "link";
            const body = {
                label: label,
                url: url,
                root: root
            }
            
            return dao.sendRequestAsync(dao.PUT, path, body);
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