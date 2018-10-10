(function CategoryDao(){
    window.categoryDao = new function(){
        this.create = create;
        this.deleteCategories = deleteCategories;
    }
    
    /*
    Sends a create category request.
    Returns:
        - true, if the category is succesfully created.
        - false otherwise.
    */
    function create(label, description, root){
        try{
            if(label == null || label == undefined || label.length == 0){
                throwException("IllegalArgument", "label must not be null, undefined or empty.");
            }
            if(description == null || description == undefined){
                throwException("IllegalArgument", "description must not be null or undefined.");
            }
            if(root == null || root == undefined){
                throwException("IllegalArgument", "root must not be null or undefined.");
            }
            
            const path = "category";
            const body = {
                label: label,
                description: description,
                root: root
            }
            
            const response = dao.sendRequest(dao.PUT, path, body);
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
    Deletes the given categories.
    Returns:
        - true, when deletion was successful.
        - false otherwise.
    */
    function deleteCategories(categoryIds){
        try{
            if(categoryIds == null || categoryIds == undefined){
                throwException("IllegalArgument", "categoryIds must not be null or undefined");
            }
            if(categoryIds.length < 1){
                throwException("IllegalArgument", "categoryIds must not be empty");
            }
            
            const path = "category";
            const response = dao.sendRequest(dao.DELETE, path, categoryIds);
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