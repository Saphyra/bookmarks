(function CategoryUtil(){
    window.categoryUtil = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        this.TYPE_CATEGORY = "CATEGORY";
        this.TYPE_LINK = "LINK";
        
        this.getCategoriesOrdered = getCategoriesOrdered;
    }
    
    function getCategoriesOrdered(categoryId){
        try{
            const categories = dataDao.getContentOfCategory(categoryId);
            
            categories.sort(function(a, b){
                if(a.type === categoryUtil.TYPE_CATEGORY && b.type !== a.type){
                    return -1;
                }
                return a.element.label.localeCompare(b.element.label);
            });
            
            return categories;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return [];
        }
    }
})();