(function TreeViewController(){
    window.treeViewController = new function(){
        scriptLoader.loadScript("js/links/category_util.js");
        
        this.showRoot = showRoot;
    }
    
    function showRoot(){
        categoryUtil.getCategoryTreeOrdered(displayTree);

        function displayTree(categories){
            const container = document.getElementById("tree_view_container");
                container.innerHTML = "";
                
            for(let cindex in categories){
                addToContainer(container, categories[cindex], true);
            }
            
            function addToContainer(container, categoryData, isRoot){
                try{
                    const categoryContainer = document.createElement("DIV"); 
                        categoryContainer.classList.add("tree_view_category_container");
                        
                        const categoryButton = document.createElement("DIV");
                            categoryButton.classList.add("button");
                            categoryButton.classList.add("tree_view_category");
                            categoryButton.onclick = function(){
                                $("#filter_container").fadeOut();
                                listViewController.openCategory(categoryData.category.categoryId);
                            }
                            
                            const textNode = document.createTextNode(categoryData.category.label);
                        categoryButton.appendChild(textNode);
                        
                    categoryContainer.appendChild(categoryButton);
                    
                        if(categoryData.children.length > 0){
                            const scrollDownButton = document.createElement("SPAN");
                                scrollDownButton.classList.add("button");
                                scrollDownButton.classList.add("tree_view_scroll_down_button");
                                scrollDownButton.innerHTML = "v";
                            categoryButton.insertBefore(scrollDownButton, textNode);
                            
                            const childrenContainer = addChildren(categoryContainer, categoryData.children);
                            
                            scrollDownButton.onclick = function(e){
                                toggleButtonStatus(scrollDownButton, childrenContainer);
                                e.stopPropagation();
                            };
                            if(isRoot){
                                childrenContainer.style.display = "block";
                            }
                        }else{
                            categoryButton.style.paddingLeft = "2rem";
                        }
                        
                    container.appendChild(categoryContainer);
                }catch(err){
                    const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                    logService.log(message, "error");
                }
                
                function addChildren(container, children){
                    try{
                        const childrenContainer = document.createElement("DIV");
                            childrenContainer.classList.add("tree_view_children_container");
                            
                            for(let cindex in children){
                                addToContainer(childrenContainer, children[cindex]);
                            }
                            
                        container.appendChild(childrenContainer);
                        
                        return childrenContainer;
                    }catch(err){
                        const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                        logService.log(message, "error");
                    }
                }
                
                function toggleButtonStatus(button, childrenContainer){
                    try{
                        if(childrenContainer.style.display == "block"){
                            button.innerHTML = "v";
                            $(childrenContainer).fadeOut();
                        }else{
                            button.innerHTML = "^";
                            $(childrenContainer).fadeIn();
                        }
                    }catch(err){
                        const message = arguments.callee.name + " - " + err.name + ": " + err.message;
                        logService.log(message, "error");
                    }
                }
            }
        }
    }
})();