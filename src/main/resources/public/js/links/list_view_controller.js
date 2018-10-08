(function ListViewController(){
    window.listViewController = new function(){
        scriptLoader.loadScript("js/links/category_util.js");
        
        this.openCategory = openCategory;
    }

    function openCategory(categoryId){
        try{
            const container = document.getElementById("list_view_container");
                container.innerHTML = "";
            
            const categories = categoryUtil.getCategoriesOrdered(categoryId);
            
            addUpButton(container, categoryId);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function addUpButton(container, categoryId){
            try{
                const button = document.createElement("BUTTON");
                    button.classList.add("textaligncenter");
                    button.classList.add("block");
                    button.classList.add("width90percent");
                    button.classList.add("marginauto");
                    
                    button.innerHTML = "Up";
                    
                    if(categoryId === ""){
                        button.disabled = true;
                    }
                    
                container.appendChild(button);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();