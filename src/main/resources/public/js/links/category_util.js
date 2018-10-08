(function CategoryUtil(){
    window.categoryUtil = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        this.TYPE_CATEGORY = "CATEGORY";
        this.TYPE_LINK = "LINK";
        
        this.getDataOrdered = getDataOrdered;
    }
    
    function getDataOrdered(categoryId){
        try{
            const data = dataDao.getContentOfCategory(categoryId);
            
            addToCache(data);
            
            data.sort(function(a, b){
                if(a.type === categoryUtil.TYPE_CATEGORY && b.type !== a.type){
                    return -1;
                }
                return a.element.label.localeCompare(b.element.label);
            });
            
            return data;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
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
    }
})();