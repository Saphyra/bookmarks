(function NewLinkController(){
    window.newLinkController = new function(){
        scriptLoader.loadScript("js/common/dao/link_dao.js");
        
        this.selectedCategory = "";
        
        this.create = create;
        this.init = init;
    }
    
    function create(){
        try{
            const label = document.getElementById("new_link_label").value;
            const url = document.getElementById("new_link_url").value;
            
            if(label.length == 0){
                notificationService.showError("Label must not be empty.");
                return;
            }else if(label.length > 100){
                notificationService.showError("Label is too long. (Max. 100 characters)");
                return;
            }else if(url.length == 0){
                notificationService.showError("URL must not be empty.");
                return;
            }else if(url.length > 4000){
                notificationService.showError("URL is too long. (Max. 4000 characters)");
                return;
            }
            
            if(linkDao.create(label, url, newLinkController.selectedCategory)){
                notificationService.showSuccess("Link saved.");
                pageController.showMainTab();
            }else{
                notificationService.showError("Unexpected error occurred.");
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function init(){
        try{
            document.getElementById("new_link_label").value = "";
            document.getElementById("new_link_url").value = "";
            document.getElementById("new_link_selected_category").innerHTML = "No category";
            
            newCategoryController.selectedCategory = "";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();