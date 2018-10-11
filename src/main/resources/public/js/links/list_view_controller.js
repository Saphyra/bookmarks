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
                    
                    button.onclick = function(){
                        listViewController.openCategory(cache.get(categoryId).element.root);
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
                const labelSpan = document.createElement("SPAN");
                    
                    switch(data.type){
                        case categoryUtil.TYPE_CATEGORY:
                            dataContainer = document.createElement("DIV");
                            dataContainer.classList.add("list_view_category");
                            dataContainer.title = data.element.description;
                            addFunctionButtonsForCategory(dataContainer, data.element);
                            dataContainer.onclick = function(){
                                listViewController.openCategory(data.element.categoryId);
                            }
                        break;
                        case categoryUtil.TYPE_LINK:
                            dataContainer = document.createElement("a");
                            dataContainer.classList.add("list_view_link");
                            dataContainer.href = data.element.url;
                            dataContainer.title = data.element.url;
                            dataContainer.target = "blank";
                            addFunctionButtonsForLink(dataContainer, data.element);
                        break;
                        default:
                            throwException("IllegalArgument", "Unknown data type: " + data.type);
                        break;
                    }
                    
                    dataContainer.classList.add("button");
                    dataContainer.classList.add("list_view_item");
                    labelSpan.innerHTML = data.element.label;
                    dataContainer.appendChild(labelSpan);
                    
                container.appendChild(dataContainer);
            }catch(err){
                const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                logService.log(message, "error");
            }
            
            function addFunctionButtonsForCategory(container, category){
                try{
                    const buttonContainer = document.createElement("DIV");
                        buttonContainer.classList.add("button_container");
                        
                        const editButton = document.createElement("BUTTON");
                            editButton.innerHTML = "Edit";
                            editButton.onclick = function(e){
                                e.stopPropagation();
                                pageController.showCategoryTab(pageController.MODE_EDIT, category.categoryId);
                            }
                    buttonContainer.appendChild(editButton);
                    
                        const deleteButton = document.createElement("BUTTON");
                            deleteButton.innerHTML = "Delete";
                            deleteButton.onclick = function(e){
                                e.stopPropagation();
                                categoryController.deleteCategories([category.categoryId]);
                            }
                    buttonContainer.appendChild(deleteButton);
                    
                    container.appendChild(buttonContainer);
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
            
            function addFunctionButtonsForLink(container, link){
                try{
                    const buttonContainer = document.createElement("DIV");
                        buttonContainer.classList.add("button_container");
                        
                        const editButton = document.createElement("BUTTON");
                            editButton.innerHTML = "Edit";
                            editButton.onclick = function(){
                                pageController.showLinkTab(pageController.MODE_EDIT, link.linkId);
                                return false;
                            }
                    buttonContainer.appendChild(editButton);
                    
                        const deleteButton = document.createElement("BUTTON");
                            deleteButton.innerHTML = "Delete";
                            deleteButton.onclick = function(){
                                linkController.deleteLinks([link.linkId]);
                                return false;
                            }
                    buttonContainer.appendChild(deleteButton);
                    
                        const archiveButton = document.createElement("BUTTON");
                        if(link.archived){
                            archiveButton.innerHTML = "Unarchive";
                            archiveButton.onclick = function(){
                                linkController.updateLinks(
                                    [{
                                        linkId: link.linkId,
                                        archived: false
                                    }]
                                );
                                return false;
                            }
                        }else{
                            archiveButton.innerHTML = "Archive";
                            archiveButton.onclick = function(){
                                linkController.updateLinks(
                                    [{
                                        linkId: link.linkId,
                                        archived: true
                                    }]
                                );
                                return false;
                            }
                        }
                    buttonContainer.appendChild(archiveButton);
                    
                    container.appendChild(buttonContainer);
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
            }
        }
    }
})();