(function Translator(){
    window.translator = new function(){
        this.convertTimeStamp = convertTimeStamp;
        this.translateSlot = translateSlot;
        this.translateType = translateType;
    }
    
    function convertTimeStamp(timeStamp){
        try{
            const hours = Math.floor(timeStamp / 3600);
            const minutes = Math.floor((timeStamp - hours * 3600) / 60);
            const seconds = timeStamp - hours * 3600 - minutes * 60;
            
            return new Time(hours, minutes, seconds);
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
        
        function Time(hours, minutes, seconds){
            this.hours = hours || 0;
            this.minutes = minutes || 0;
            this.seconds = seconds || 0;
            
            this.toString = function(){
                return (this.hours < 10 ? "0" : "") + this.hours + ":"
                + (this.minutes < 10 ? "0" : "") + this.minutes + ":"
                + (this.seconds < 10 ? "0" : "") + this.seconds;
            }
        }
    }
    
    function translateSlot(slot){
        try{
            switch(slot){
                case "connector":
                    return "Csatlakozó";
                break;
                case "defense":
                    return "Védelem";
                break;
                case "ship":
                    return "Hajó";
                break;
                case "weapon":
                    return "Fegyver";
                break;
                case "material":
                    return "Alapanyag";
                break;
                default:
                    throwException("IllegalArgument", "Unknown slot: " + slot);
                break;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function translateType(type){
        try{
            switch(type){
                default:
                    throwException("IllegalArgument", "Unknown type: " + type);
                break;
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();