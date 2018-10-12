(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/links/list_view_controller.js");
        scriptLoader.loadScript("js/links/category_controller.js");
        scriptLoader.loadScript("js/links/link_controller.js");
        scriptLoader.loadScript("js/links/select_category_controller.js");
        scriptLoader.loadScript("js/links/tree_view_controller.js");
        
        this.MODE_CREATE = "new";
        this.MODE_EDIT = "edit";
        
        this.getModeText = getModeText;
        this.openSelectCategoryTab = openSelectCategoryTab;
        this.showMainTab = showMainTab;
        this.showCategoryTab = showCategoryTab;
        this.showLinkTab = showLinkTab;
        
        $(document).ready(function(){
            init();
        });
    }
    
    function getModeText(mode){
        switch(mode){
            case pageController.MODE_CREATE:
                return "Create";
            break;
            case pageController.MODE_EDIT:
                return "Edit";
            break;
            default:
                throwException("IllegalArgument", "Unknown mode: " + mode);
            break;
        }
    }
    
    function openSelectCategoryTab(mode){
        try{
            selectCategoryController.init(mode);
            $("#select_category_tab").show();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showMainTab(){
        try{
            listViewController.openCategory(listViewController.actualCategory);
            treeViewController.showRoot();
            switchTab("container", "main_tab")
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showCategoryTab(mode, categoryId){
        try{
            categoryController.init(mode, categoryId);
            switchTab("container", "category_tab");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function showLinkTab(mode, linkId){
        try{
            linkController.init(mode, linkId);
            switchTab("container", "link_tab");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function init(){
        try{
            listViewController.openCategory("");
            treeViewController.showRoot();
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();