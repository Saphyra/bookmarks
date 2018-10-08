(function ListViewController(){
    window.listViewController = new function(){
        scriptLoader.loadScript("js/common/dao/data_dao.js");
        
        this.openCategory = openCategory;
    }

    function openCategory(categoryId){
        try{
            
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();