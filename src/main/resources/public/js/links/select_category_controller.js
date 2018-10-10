(function SelectCategoryController(){
    window.selectCategoryController = new function(){
        scriptLoader.loadScript("js/links/category_util.js");
        
        this.MODE_CATEGORY = "category";
        this.MODE_LINK = "link";
        
        this.mode = null;
        
        this.displayCategories = displayCategories;
        this.init = init;
    }
    
    function displayCategories(rootId){
        try{
            const container = document.getElementById("selectable_categories");
                container.innerHTML = "";
                
            const categories = categoryUtil.getCategoriesOfRootOrdered(rootId)
            
            addUpButton(container, rootId);
            
            for(let cindex in categories){
                addToContainer(container, categories[cindex]);
            }
            
            setSelectButton(rootId);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function addUpButton(container, categoryId){
            try{
                const button = document.createElement("BUTTON");
                    button.classList.add("bold");
                    button.classList.add("textaligncenter");
                    button.classList.add("block");
                    button.classList.add("width90percent");
                    button.classList.add("marginauto");
                    button.classList.add("fontsize1_25rem");
                    
                    button.innerHTML = "Up";
                    
                    if(categoryId === ""){
                        button.disabled = true;
                    }
                    
                    button.onclick = function(){
                        selectCategoryController.displayCategories(cache.get(categoryId).element.root);
                    }
                    
                container.appendChild(button);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function addToContainer(container, data){
            try{
                const dataContainer = document.createElement("DIV");
                    dataContainer.classList.add("list_view_category");
                    dataContainer.title = data.element.description;
                    dataContainer.classList.add("button");
                    dataContainer.classList.add("list_view_item");
                    
                    dataContainer.onclick = function(){
                        selectCategoryController.displayCategories(data.element.categoryId);
                    }
                    
                    const labelSpan = document.createElement("SPAN");
                        labelSpan.innerHTML = data.element.label;
                dataContainer.appendChild(labelSpan);
                    
                container.appendChild(dataContainer);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function setSelectButton(rootId){
            try{
                const button = document.getElementById("select_category_button");
                    button.innerHTML = "Select: " + (rootId.length === 0 ? "Root" : cache.get(rootId).element.label);
                    
                    button.onclick = function(){
                        switch(selectCategoryController.mode){
                            case selectCategoryController.MODE_CATEGORY:
                                categoryController.selectCategory(rootId);
                            break;
                            case selectCategoryController.MODE_LINK:
                                linkController.selectCategory(rootId);
                            break;
                            default:
                                throwException("IllegalArgument", mode + " mode is not valid.");
                            break;
                        }
                        
                        $("#select_category_tab").hide();
                    }
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
    
    function init(mode){
        try{
            selectCategoryController.mode = mode == selectCategoryController.MODE_CATEGORY || mode == selectCategoryController.MODE_LINK ? mode : throwException("IllegalArgument", mode + " mode is not valid.");
            
            selectCategoryController.displayCategories("");
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();