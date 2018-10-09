(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/links/list_view_controller.js");
        scriptLoader.loadScript("js/links/category_controller.js");
        scriptLoader.loadScript("js/links/link_controller.js");
        scriptLoader.loadScript("js/links/tree_view_controller.js");
        
        this.MODE_CREATE = "Create ";
        this.MODE_EDIT = "Edit ";
        
        this.openSelectCategoryTab = openSelectCategoryTab;
        this.showMainTab = showMainTab;
        this.showCategoryTab = showCategoryTab;
        this.showLinkTab = showLinkTab;
        
        $(document).ready(function(){
            init();
        });
    }
    
    function openSelectCategoryTab(mode){
        try{
            $("#select_category_tab").show();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showMainTab(){
        try{
            switchTab("container", "main_tab")
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showCategoryTab(){
        try{
            newCategoryController.init();
            switchTab("container", "category_tab");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showLinkTab(){
        try{
            newLinkController.init();
            switchTab("container", "link_tab");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function init(){
        try{
            listViewController.openCategory("");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();