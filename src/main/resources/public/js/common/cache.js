(function Cache(){
    window.cache = new function(){
        this.storage = {};
        
        this.add = add;
        this.addAll = addAll;
        this.get = get;
    }
    
    /*
    Adds an element to the storage.
    Arguments:
        - key:  The key of the element.
        - data: The data of the element.
    Throws:
        - IllegalArgument exception if key is null of undefined.
    */
    function add(key, data){
        try{
            if(key == null || key == undefined){
                throwException("IllegalArgument", "key must not be null or undefined");
            }
            this.storage[key] = data;
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Adds all elements to the cache.
    Arguments:
        - datas: key-value pairs to be store.
    Throws:
        - IllegalArgument if datas is null or undefined.
    */
    function addAll(datas){
        try{
            if(datas == null || datas == undefined){
                throwException("IllegalArgument", "datas must not be null or undefined");
            }
            
            for(let dindex in datas){
                this.add(dindex, datas[dindex]);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    /*
    Returns the stored element specified by the key.
    Arguments:
        - key: The key of the value to retrirve.
    Returns:
        - The stored element.
        - null if no element with the given key.
    Throws:
        - IllegalArgument exception if key is null or undefined.
    */
    function get(key){
        try{
            if(key == null || key == undefined){
                throwException("IllegalArgument", "key must not be null or undefined");
            }
            
            if(this.storage[key] == undefined){
                logService.log(key, "warn", "The following element is not stored in cache: ");
            }
            
            return this.storage[key];
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();