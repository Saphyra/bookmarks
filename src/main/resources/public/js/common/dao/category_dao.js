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
    function create(category, successCallback, errorCallback){
        try{
            const path = "category";
            const request = new Request(dao.PUT, path, category);
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
    function deleteCategories(categoryIds, successCallback, errorCallback){
        try{
            const path = "category";
            const request = new Request(dao.DELETE, path, categoryIds);
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
    function updateCategories(categories, successCallback, errorCallback){
        try{
            const path = "category";
            const request = new Request(dao.POST, path, categories);
                request.processValidResponse = successCallback;
                request.processInvalidResponse = errorCallback;
            
            dao.sendRequestAsync(request);        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();