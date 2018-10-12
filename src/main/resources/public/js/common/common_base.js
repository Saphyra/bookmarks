$(document).ready(function(){
    scriptLoader.loadScript("js/common/logservice.js");
    scriptLoader.loadScript("js/common/cache.js");
    scriptLoader.loadScript("js/common/dao.js");
    scriptLoader.loadScript("js/common/auth_service.js");
    scriptLoader.loadScript("js/common/notificationservice.js");
});

(function ScriptLoader(){
    window.scriptLoader = new function(){
        this.loadedScripts = [];
        this.loadScript = loadScript;
    }
    
    /*
        Loads the script given as argument.
        Arguments:
            - src: The path of the requested script.
        Throws
            - IllegalArgument exception if src is null ord undefined
            - IllegalState exception if jQuery cannot be found.
    */
    function loadScript(src){
        try{
            if(src == undefined || src == null){
                throwException("IllegalArgument", "src must not be null or undefined.");
            }
            
            if(this.loadedScripts.indexOf(src) > -1){
                return;
            }
            
            if($ == undefined){
                throwException("IllegalState", "jQuery cannot be resolved.");
            }
            $.ajax({
                async: false,
                url: src,
                dataType: "script",
                cache: true
            });
            this.loadedScripts.push(src);
        }catch(err){
            alert(arguments.callee.name + " - " + err.name + ": " + err.message);
        }
    }
})();

function throwException(name, message){
    name = name == undefined ? "" : name;
    message = message == undefined ? "" : message;
    throw {name: name, message: message};
}

function getActualTimeStamp(){
    return Math.floor(new Date().getTime() / 1000);
}

function switchTab(clazz, id){
    try{
        $("." + clazz).hide();
        $("#" + id).show();
    }catch(err){
        const message = arguments.callee.name + " - " + err.name + ": " + err.message;
        logService.log(message, "error");
    }
}