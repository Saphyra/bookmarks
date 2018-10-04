(function DateTimeUtil(){
    window.dateTimeUtil = new function(){
        this.formatEpoch = formatEpoch;
    }
    
    function formatEpoch(epoch){
        try{
            const date = new Date(0);
                date.setUTCSeconds(epoch);
            
            const months = (date.getMonth() + 1) < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1);
            const days = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            const hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
            const seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
            
            return date.getFullYear()
                + "/" + months
                + "/" + days
                + " " + hours
                + ":" + seconds;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
            return "";
        }
    }
})();