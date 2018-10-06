(function LinkDao(){
    window.linkDao = new function(){
        this.create = create;
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
            
            const result = dao.sendRequest(dao.PUT, path, body);
            if(result.status == ResponseStatus.OK){
                return true;
            }else{
                throwException("UnknownBackendError", result.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();