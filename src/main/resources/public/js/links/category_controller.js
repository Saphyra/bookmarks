(function CategoryController(){
    window.categoryController = new function(){
        scriptLoader.loadScript("js/common/dao/category_dao.js");
        
        this.selectedCategory = "";
        
        this.create = create;
        this.deleteCategories = deleteCategories;
        this.selectCategory = selectCategory;
        this.update = update;
        this.updateCategories = updateCategories;
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
    
    function update(categoryId){
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
            
            const category = [{
                categoryId: categoryId,
                label: label,
                description: description,
                root: categoryController.selectedCategory
            }];
            categoryController.updateCategories(category);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function updateCategories(categories){
        try{
            validateRequests(categories);
            if(categoryDao.updateCategories(categories)){
                notificationService.showSuccess("Categories saved.");
                pageController.showMainTab();
            }else{
                notificationService.showError("Unexpected error occurred.");
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function validateRequests(categories){
            for(let cindex in categories){
                if(!categories[cindex].categoryId){
                    throwException("IllegalArgument", "categoryId is null.");
                }
            }
        }
    }
    
    function init(mode, categoryId){
        try{
            document.getElementById("category_selected_category").innerHTML = "Root";
            
            let category;
            if(mode == pageController.MODE_EDIT){
                if(categoryId == null || categoryId == undefined){
                    throwException("IllegalArgument", "categoryId must not be null or undefined when EDIT_MODE");
                }
                
                category = cache.get(categoryId).element;
                categoryController.selectCategory(category.root);
            }else{
                categoryController.selectCategory("");
            }
            
            document.getElementById("category_label").value = mode == pageController.MODE_CREATE ? "" : category.label;
            document.getElementById("category_description").value = mode == pageController.MODE_CREATE ? "" : category.description;
            
            document.getElementById("category_header").innerHTML = pageController.getModeText(mode) + " category";
            
            document.getElementById("category_button").onclick = mode == pageController.MODE_CREATE ? categoryController.create : function(){categoryController.update(categoryId)};
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();