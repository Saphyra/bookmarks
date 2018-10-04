(function LogService(){
    window.logService = new function(){
        this.log = log;
    }
    /*
        error: Hibák a kódban (amit a catch elkap)
        warn: Figyelmeztetések (érvénytelen változóérték)
        info: Üzenetek
        debug: Részletes információk a futás állapotáról
    */
    function log(message, level, prefix){
        try{
            level = level || "info";
            prefix = prefix || "";
            
            if(!shouldLog(level)){
                return;
            }
            
            const container = getContainer();
            container.insertBefore(createMessage(message, level, prefix), container.childNodes[0]);
        }catch(err){
            alert(arguments.callee.name + " - " + err.name + ": " + err.message);
        }
        /*
            Determinates if the message with the given level should be displayed or not.
        */
        function shouldLog(level){
            try{
                switch(level){
                    case "debug":
                        return true;
                    break;
                    case "info":
                        return true;
                    break;
                    case "warn":
                        return true;
                    break;
                    case "error":
                        return true;
                    break;
                    default:
                        return true;
                    break;
                }
            }catch(err){
                alert(arguments.callee.name + " - " + err.name + ": " + err.message);
            }
        }
        /*
            Retrieves the container for logs, or creates one if not exists.
        */
        function getContainer(){
            try{
                let mainContainer = document.getElementById("logcontainermain");
                mainContainer = mainContainer || createContainer();
                
                return document.getElementById("logcontainermessages");
            }catch(err){
                alert(arguments.callee.name + " - " + err.name + ": " + err.message);
            }
            /*
                Creates the window for displaying the log messages
            */
            function createContainer(){
                try{
                    const mainContainer = document.createElement("DIV");
                        mainContainer.classList.add("cover");
                        mainContainer.id = "logcontainermain";
                        
                        const title = document.createElement("H1");
                            title.innerHTML = "Log";
                            title.classList.add("relative");
                            
                            const closeButton = document.createElement("BUTTON");
                                closeButton.innerHTML = "X";
                                closeButton.classList.add("absolute");
                                closeButton.classList.add("fontsize1rem");
                                closeButton.classList.add("right2px");
                                closeButton.classList.add("top2px");
                                closeButton.onclick = function(){
                                    document.body.removeChild(mainContainer);
                                }
                        title.appendChild(closeButton);
                    mainContainer.appendChild(title);
                    
                        const messageContainer = document.createElement("DIV");
                            messageContainer.id = "logcontainermessages";
                            messageContainer.classList.add("padding0_25rem");
                    mainContainer.appendChild(messageContainer);
                        
                    document.body.appendChild(mainContainer);
                }catch(err){
                    alert(arguments.callee.name + " - " + err.name + ": " + err.message);
                }
            }
        }
        /*
            Creates the log message.
        */
        function createMessage(message, level, prefix){
            try{
                const color = getColor(level);
                
                const messageContainer = document.createElement("DIV");
                    messageContainer.classList.add("margintop0_5rem");
                    messageContainer.style.color = color;
                    
                    const levelNode = document.createElement("DIV");
                        levelNode.classList.add("bold");
                        levelNode.classList.add("inlineblock");
                        levelNode.classList.add("width10rem");
                        levelNode.innerHTML = level.toUpperCase();
                messageContainer.appendChild(levelNode);
                
                messageContainer.appendChild(createTextNode(prefix));
                
                if(message == null){
                    messageContainer.appendChild(createTextNode("null"));
                }else if(message == undefined){
                    messageContainer.appendChild(createTextNode("undefined"));
                }else if(typeof message == "object"){
                    messageContainer.appendChild(parseObject(message));
                }else{
                    messageContainer.appendChild(createTextNode(message));
                }
                
                return messageContainer;
            }catch(err){
                alert(arguments.callee.name + " - " + err.name + ": " + err.message);
            }
            
            function getColor(level){
                let color;
                switch(level){
                    case "info":
                        color = "white";
                    break;
                    case "debug":
                        color = "green";
                    break;
                    case "warn":
                        color = "yellow";
                    break;
                    case "error":
                        color = "red";
                    break;
                    default:
                        return "orange";
                    break;
                }
                return color;
            }
            
            function createTextNode(message){
                const element = document.createElement("SPAN");
                    element.innerHTML = message;
                return element;
            }
            
            function parseObject(obj){
                try{
                    const element = document.createElement("OL");
                    const keys = Object.keys(obj);
                    
                    if(keys.length  == 0){
                        return document.createTextNode("(Empty object/array)");
                    }

                    for(let kindex in keys){
                        const key = keys[kindex]
                        const elem = obj[key];
                        const line = document.createElement("LI");
                            if(elem == null){
                                line.appendChild(createTextNode("null"));
                            }else if(elem == undefined){
                                line.appendChild(createTextNode("undefined"));
                            }else if(typeof elem == "object"){
                                line.appendChild(document.createTextNode(key + ": "));
                                line.appendChild(parseObject(elem));
                            }else{
                                line.innerHTML = key + ": " + elem;
                            }
                        element.appendChild(line);
                    }
                    
                    return element;
                }catch(err){
                    alert(arguments.callee.name + " - " + err.name + ": " + err.message);
                }
            }
        }
    }
})();