(function CategoryController(){
    window.categoryController = new function(){
        scriptLoader.loadScript("js/common/dao/category_dao.js");
        
        this.selectedCategory = "";
        
        this.create = create;
        this.init = init;
    }
    
    function create(){
        try{
            const label = document.getElementById("category_label").value;
            const description = document.getElementById("category_description").value;
            
            if(label.length == 0){
                notificationService.showError("Label must not be empty.");
                return;
            }else if(label.length > 100){
                notificationService.showError("Label is too long. (Max. 100 characters)");
                return;
            }else if(description.length > 1000){
                notificationService.showError("Description is too long. (Max. 1000 characters)");
                return;
            }
            
            if(categoryDao.create(label, description, newCategoryController.selectedCategory)){
                notificationService.showSuccess("Category saved.");
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
            document.getElementById("category_label").value = "";
            document.getElementById("category_description").value = "";
            document.getElementById("category_selected_category").innerHTML = "No category";
            
            newCategoryController.selectedCategory = "";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();