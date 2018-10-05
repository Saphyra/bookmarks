(function PageController(){
    window.pageController = new function(){
        this.openCategoryTab = openCategoryTab;
    }
    
    function openCategoryTab(mode){
        try{
            $("#select_category_tab").show();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();