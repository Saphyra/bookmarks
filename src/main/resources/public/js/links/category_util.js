(function CategoryUtil(){
    window.categoryUtil = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        this.TYPE_CATEGORY = "CATEGORY";
        this.TYPE_LINK = "LINK";
        
        this.getCategoriesOfRootOrdered = getCategoriesOfRootOrdered;
        this.getCategoryTreeOrdered = getCategoryTreeOrdered;
        this.getDataOrdered = getDataOrdered;
        this.getFilteredDataOrdered = getFilteredDataOrdered;
    }
    
    function getCategoriesOfRootOrdered(rootId, successCallback){
        try{
            dataDao.getCategoriesOfRoot(
                rootId,
                {rootId: rootId},
                order,
                function(data, state){
                    addToCache(data);
                    successCallback(data, state);
                }
            );
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    function getCategoryTreeOrdered(successCallback){
        try{
            dataDao.getCategoryTree(successCallback, orderCategoryTree);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
        
        function orderCategoryTree(data){
            try{
                for(let dindex in data){
                    data[dindex].children = orderCategoryTree(data[dindex].children);
                }
                
                data.sort(function(a, b){
                    return a.category.label.localeCompare(b.category.label);
                });
                
                return data;
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
                return [];
            }
        }
    }
    
    function getDataOrdered(categoryId, successCallback, state){
        try{
            const data = dataDao.getContentOfCategory(
                categoryId,
                order,
                function(data, state){
                    addToCache(data);
                    successCallback(data, state);
                },
                state
            );
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    function getFilteredDataOrdered(label, secondary, type, successCallback){
        try{
            dataDao.getFilteredData(
                label,
                secondary,
                type,
                order,
                function(data, state){
                    addToCache(data);
                    successCallback(data, state);
                }
            );
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
    
    function addToCache(data){
        try{
            for(let dindex in data){
                const idKey = getIdKey(data[dindex].type);
                
                cache.add(data[dindex]["element"][idKey], data[dindex]);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function getIdKey(type){
            try{
                return type == categoryUtil.TYPE_CATEGORY ? "categoryId" : "linkId";
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
    
    function order(data){
        try{
            data.sort(function(a, b){
                if(a.type === categoryUtil.TYPE_CATEGORY && b.type !== a.type){
                    return -1;
                }else if(a.type === categoryUtil.TYPE_LINK && b.type !== a.type){
                    return 1;
                }
                return a.element.label.localeCompare(b.element.label);
            });
            
            return data;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
})();