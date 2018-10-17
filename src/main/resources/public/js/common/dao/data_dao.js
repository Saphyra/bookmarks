(function DataDao(){
    window.dataDao = new function(){
        this.getCategoriesOfRoot = getCategoriesOfRoot;
        this.getCategoryTree = getCategoryTree;
        this.getContentOfCategory = getContentOfCategory;
        this.getFilteredData = getFilteredData;
    }
    
    /*
    
    */
    function getCategoriesOfRoot(rootId, state, sortMethod, successCallback){
        try{
            const path = "data/categories/" + rootId;
            const request = new Request(dao.GET, path);
                request.state = state;
                request.convertResponse = function(response){
                    return sortMethod(JSON.parse(response.response));
                }
                
                request.processValidResponse = successCallback;
            
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    
    */
    function getCategoryTree(successCallback, sortMethod){
        try{
            const path = "categories";
            
            const request = new Request(dao.GET, path);
                request.convertResponse = function(response){
                    return sortMethod(JSON.parse(response.response));
                }
                
                request.processValidResponse = successCallback;
            
            dao.sendRequestAsync(request);
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
    function getContentOfCategory(categoryId, sortMethod, successCallback, state){
        try{
            const path = "data/" + categoryId;
            const request = new Request(dao.GET, path);
                request.state = state;
                request.convertResponse = function(response){
                    return sortMethod(JSON.parse(response.response));
                }
                
                request.processValidResponse = successCallback;
            
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    /*
    
    */
    function getFilteredData(label, secondary, type, sortMethod, successCallback){
        try{
            const path = "data";
            const body = {
                label: label,
                secondary: secondary,
                type: type
            };
            
            const request = new Request(dao.POST, path, body);
                request.convertResponse = function(response){
                    return sortMethod(JSON.parse(response.response));
                }
                
                request.processValidResponse = successCallback;
            
            dao.sendRequestAsync(request);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
})();