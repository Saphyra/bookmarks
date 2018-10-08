(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/links/list_view_controller.js");
        scriptLoader.loadScript("js/links/new_category_controller.js");
        scriptLoader.loadScript("js/links/new_link_controller.js");
        scriptLoader.loadScript("js/links/tree_view_controller.js");
        
        this.openCategoryTab = openCategoryTab;
        this.showMainTab = showMainTab;
        this.showNewCategoryTab = showNewCategoryTab;
        this.showNewLinkTab = showNewLinkTab;
        
        $(document).ready(function(){
            init();
        });
    }
    
    function openCategoryTab(mode){
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
    
    function showNewCategoryTab(){
        try{
            newCategoryController.init();
            switchTab("container", "new_category_tab");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showNewLinkTab(){
        try{
            newLinkController.init();
            switchTab("container", "new_link_tab")
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function init(){
        try{
            newLinkController.init();
            switchTab("container", "new_link_tab")
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();