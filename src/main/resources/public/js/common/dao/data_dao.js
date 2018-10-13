(function DataDao(){
    window.dataDao = new function(){
        this.getCategoriesOfRoot = getCategoriesOfRoot;
        this.getCategoryTree = getCategoryTree;
        this.getContentOfCategory = getContentOfCategory;
        this.getFilteredData = getFilteredData;
    }
    
    /*
    
    */
    function getCategoriesOfRoot(rootId){
        try{
            if(rootId == null || rootId == undefined){
                throwException("IllegalArgument", "rootId must not be null or undefined.");
            }
            
            const path = "data/categories/" + rootId;
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    
    */
    function getCategoryTree(){
        try{
            const path = "categories";
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    Queries the content of the given categoryId
    Returns:
        - List of elements
        - Empty list upon fail
    */
    function getContentOfCategory(categoryId){
        try{
            if(categoryId == null || categoryId == undefined){
                throwException("IllegalArgument", "categoryId must not be null or undefined.");
            }
            
            const path = "data/" + categoryId;
            const response = dao.sendRequest(dao.GET, path);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    
    */
    function getFilteredData(label, secondary, type){
        try{
            const path = "data";
            const body = {
                label: label,
                secondary: secondary,
                type: type
            };
            const response = dao.sendRequest(dao.POST, path, body);
            if(response.status == ResponseStatus.OK){
                return JSON.parse(response.response);
            }else{
                throwException("UnknownBackendError", response.toString());
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
})();