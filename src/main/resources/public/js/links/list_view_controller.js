(function ListViewController(){
    window.listViewController = new function(){
        scriptLoader.loadScript("js/links/category_util.js");
        scriptLoader.loadScript("js/common/dao/category_dao.js");
        
        this.ARCHIVE_FILTER_MODE_ARCHIVED = "archived";
        this.ARCHIVE_FILTER_MODE_UNARCHIVED = "unarchived";
        this.ARCHIVE_FILTER_MODE_ALL = "all";
        
        this.actualCategory = null;
        
        this.openCategory = openCategory;
        this.showFilterResult = showFilterResult;
        
        $(document).ready(function(){
            addListeners();
        });
    }

    function openCategory(categoryId, preloadedData){
        const container = document.getElementById("list_view_elements");
            container.innerHTML = "";
        
        const state = {
            emptyMessage: "No result.",
            container: container,
            categoryId: categoryId
        };
        
        if(categoryId != null && categoryId != undefined){
            listViewController.actualCategory = categoryId;
            emptyMessage = "Category is empty.";
            
            categoryUtil.getDataOrdered(
                categoryId,
                function(data, state){
                    processUnloadedData(data, state);
                    processLoadedData(data, state);
                },
                state
            );
            
        }else if(preloadedData == null || preloadedData == undefined){
            throwException("IllegalArgument", "categoryId or preloadedData must be set.");
        }else{
            processLoadedData(preloadedData, state);
        }
    
        function processUnloadedData(data, state){
            document.getElementById("actual_category_name").innerHTML = 
                categoryId.length == 0 ? 
                    "Root" : cache.get(
                        categoryId, function(){return categoryDao.get(categoryId)}
                    ).element.label;
            
            addUpButton(state.container, state.categoryId);
            
            function addUpButton(container, categoryId){
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
            }
        }
        
        function processLoadedData(data, state){
            if(data.length == 0){
                addEmptyMessage(state.container, state.emptyMessage);
            }
            
            for(let dindex in data){
                addToContainer(state.container, data[dindex]);
            }
            
            function addEmptyMessage(container, message){
                const div = document.createElement("DIV");
                    div.classList.add("fontsize2rem");
                    div.innerHTML = message;
                container.appendChild(div);
            }
            
            function addToContainer(container, data){
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
                                $("#filter_container").fadeOut();
                            }
                        break;
                        case categoryUtil.TYPE_LINK:
                            const archiveFilterMode = $("input[name=archive_filter]:checked").val();
                            if(archiveFilterMode != listViewController.ARCHIVE_FILTER_MODE_ALL){
                                if(archiveFilterMode == listViewController.ARCHIVE_FILTER_MODE_ARCHIVED && !data.element.archived){
                                    return;
                                }
                                if(archiveFilterMode == listViewController.ARCHIVE_FILTER_MODE_UNARCHIVED && data.element.archived){
                                    return;
                                }
                            }
                            
                            dataContainer = document.createElement("a");
                            dataContainer.classList.add("list_view_link");
                            dataContainer.href = data.element.url;
                            dataContainer.title = data.element.url;
                            dataContainer.target = "_blank";
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
                
                function addFunctionButtonsForCategory(container, category){
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
                }
                
                function addFunctionButtonsForLink(container, link){
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
                }
            }
        }
    }
    
    function showFilterResult(){
        try{
            const labelFilter = document.getElementById("label_filter").value;
            const secondaryFilter = document.getElementById("secondary_filter").value;
            const typeFilter = $("input[name=type_filter]:checked").val();
            
            const data = categoryUtil.getFilteredDataOrdered(labelFilter, secondaryFilter, typeFilter);
            listViewController.openCategory(null, data);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function addListeners(){
        $("input[name=archive_filter]").change(function(){
            listViewController.openCategory(listViewController.actualCategory);
        });
        
        $("input[name=type_filter]").change(listViewController.showFilterResult);
        
        $("#label_filter, #secondary_filter").keyup(listViewController.showFilterResult);
    }
})();