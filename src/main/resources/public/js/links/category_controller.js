(function CategoryController(){
    window.categoryController = new function(){
        scriptLoader.loadScript("js/common/dao/category_dao.js");
        
        this.selectedCategory = "";
        
        this.create = create;
        this.deleteCategories = deleteCategories;
        this.selectCategory = selectCategory;
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
            
            if(categoryDao.create(label, description, categoryController.selectedCategory)){
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
    
    function deleteCategories(categoryIds){
        try{
            if(confirm("Are you sure to delete the selected categories?")){
                if(categoryDao.deleteCategories(categoryIds)){
                    notificationService.showSuccess("Categories are successfully deleted.");
                    pageController.showMainTab();
                }else{
                    notificationService.showError("Unexpected error occurred.");
                }
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function selectCategory(rootId){
        try{
            categoryController.selectedCategory = rootId;
            
            document.getElementById("category_selected_category").innerHTML = rootId.length === 0 ? "Root" : cache.get(rootId).element.label;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function init(mode){
        try{
            document.getElementById("category_label").value = "";
            document.getElementById("category_description").value = "";
            document.getElementById("category_selected_category").innerHTML = "No category";
            
            document.getElementById("category_header").innerHTML = pageController.getModeText(mode) + " category";
            
            document.getElementById("category_button").onclick = mode == pageController.MODE_CREATE ? categoryController.create : null;
            
            categoryController.selectedCategory = "";
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();