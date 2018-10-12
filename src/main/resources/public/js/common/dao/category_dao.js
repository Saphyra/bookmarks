(function CategoryDao(){
    window.categoryDao = new function(){
        this.create = create;
        this.deleteCategories = deleteCategories;
        this.get = get;
        this.updateCategories = updateCategories;
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
    
    /*
    
    */
    function get(categoryId){
        try{
            if(categoryId == null || categoryId == undefined){
                throwException("IllegalArgument", "categoryId must not be null or undefined");
            }
            
            const path = "category/" + categoryId;
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    
    */
    function updateCategories(categories){
        try{
            if(categories == null || categories == undefined){
                throwException("IllegalArgument", "categories must not be null or undefined");
            }
            if(categories.length < 1){
                throwException("IllegalArgument", "categories must not be empty");
            }
            
            const path = "category";
            const response = dao.sendRequest(dao.POST, path, categories);
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