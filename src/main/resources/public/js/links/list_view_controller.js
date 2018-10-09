(function ListViewController(){
    window.listViewController = new function(){
        scriptLoader.loadScript("js/links/category_util.js");
        
        this.actualCategory = null;
        
        this.openCategory = openCategory;
    }

    function openCategory(categoryId){
        try{
            listViewController.actualCategory = categoryId;
            
            const container = document.getElementById("list_view_container");
                container.innerHTML = "";
            
            const data = categoryUtil.getDataOrdered(categoryId);
            
            addUpButton(container, categoryId);
            
            for(let dindex in data){
                addToContainer(container, data[dindex]);
            }
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
                    
                container.appendChild(button);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
        
        function addToContainer(container, data){
            try{
                let dataContainer;
                    
                    switch(data.type){
                        case categoryUtil.TYPE_CATEGORY:
                            dataContainer = document.createElement("DIV");
                            dataContainer.classList.add("list_view_category");
                            dataContainer.title = data.element.description;
                        break;
                        case categoryUtil.TYPE_LINK:
                            dataContainer = document.createElement("a");
                            dataContainer.classList.add("list_view_link");
                            dataContainer.href = data.element.url;
                            dataContainer.title = data.element.url;
                            dataContainer.target = "blank";
                        break;
                        default:
                            throwException("IllegalArgument", "Unknown data type: " + data.type);
                        break;
                    }
                    
                    dataContainer.classList.add("button");
                    dataContainer.classList.add("list_view_item");
                    dataContainer.innerHTML = data.element.label;
                    
                container.appendChild(dataContainer);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
        }
    }
})();