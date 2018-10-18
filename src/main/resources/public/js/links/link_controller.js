(function LinkController(){
    window.linkController = new function(){
        scriptLoader.loadScript("js/common/dao/link_dao.js");
        
        this.selectedCategory = "";
        
        this.create = create;
        this.deleteLinks = deleteLinks;
        this.selectCategory = selectCategory;
        this.update = update;
        this.updateLinks = updateLinks;
        this.init = init;
    }
    
    function create(){
        try{
            const label = document.getElementById("link_label").value;
            const url = document.getElementById("link_url").value;
            
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
            
            const link = {
                label: label,
                url: url,
                root: linkController.selectedCategory
            }
            linkDao.create(
                link,
                function(){
                    notificationService.showSuccess("Link saved.");
                    pageController.showMainTab();
                },
                function(){
                    notificationService.showError("Unexpected error occurred.");
                }
            );
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function deleteLinks(linkIds){
        try{
            if(confirm("Are you sure to delete the selected links?")){
                linkDao.deleteLinks(
                    linkIds,
                    function(){
                        notificationService.showSuccess("Links are successfully deleted.");
                        pageController.showMainTab();
                    },
                    function(){
                        notificationService.showError("Unexpected error occurred.");
                    }
                );
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function selectCategory(rootId){
        try{
            linkController.selectedCategory = rootId;
            
            document.getElementById("link_selected_category").innerHTML = rootId.length === 0 ? "Root" : cache.get(rootId).element.label;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function update(linkId){
        try{
            const label = document.getElementById("link_label").value;
            const url = document.getElementById("link_url").value;
            
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
            
            const link = [{
                linkId: linkId,
                label: label,
                url: url,
                root: linkController.selectedCategory
            }];
            linkController.updateLinks(
                link,
                function(){},
                function(){}
            );
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function updateLinks(links){
        try{
            validateRequests(links);
            linkDao.updateLinks(
                links,
                function(){
                    notificationService.showSuccess("Links are successfully updated.");
                    pageController.showMainTab();
                },
                function(){
                    notificationService.showError("Unexpected error occurred.");
                }
            );
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function validateRequests(links){
            for(let lindex in links){
                if(!links[lindex].linkId){
                    throwException("IllegalArgument", "linkId is null.");
                }
            }
        }
    }
    
    function init(mode, linkId){
        try{
            document.getElementById("link_selected_category").innerHTML = "Root";
            
            let link;
            if(mode == pageController.MODE_EDIT){
                if(linkId == null || linkId == undefined){
                    throwException("IllegalArgument", "linkId must not be null or undefined when EDIT_MODE");
                }
                
                link = cache.get(linkId).element;
                linkController.selectCategory(link.root);
            }else{
                linkController.selectCategory("");
            }
            
            document.getElementById("link_label").value = mode == pageController.MODE_CREATE ? "" : link.label;
            document.getElementById("link_url").value = mode == pageController.MODE_CREATE ? "" : link.url;
            
            
            document.getElementById("link_header").innerHTML = pageController.getModeText(mode) + " link";
            document.getElementById("link_button").onclick = mode == pageController.MODE_CREATE ? linkController.create : function(){linkController.update(linkId)};
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return false;
        }
    }
})();