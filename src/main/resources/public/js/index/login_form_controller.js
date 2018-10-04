(function LoginController(){
    window.loginController = new function(){
        this.sendForm = sendForm;
        this.login = login;
        
        $(document).ready(function(){
            addListeners();
        });
    }
    
    /*
    Sends the login form.
    */
    function sendForm(){
        try{
            const userNameInput = document.getElementById("login_username");
            const passwordInput = document.getElementById("login_password");
            
            const userName = userNameInput.value;
            const password = passwordInput.value;
            
            if(userName == ""){
                notificationService.showError("Adja meg felhasználónevét!");
            }else if(password == ""){
                notificationService.showError("Adja meg jelszavát!");
            }else{
                login(userName, password);
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    /*
    Logs in the user and redirects to home page.
    Arguments:
        - userName: The username of the user.
        - password: The password of the user.
    Throws:
        - IllegalArgument exception is userName / password is null or undefined.
    */
    function login(userName, password){
        try{
            if(userName == null || userName == undefined){
                throwException("IllegalArgument", "userName must not be null or undefined");
            }
            if(password == null || password == undefined){
                throwException("IllegalArgument", "password must not be null or undefined");
            }
            
            if(authService.login(userName, password)){
                window.location.href = "/characterselect";
            }else{
                notificationService.showError("Hibás felhasználónév vagy jelszó.");
                document.getElementById("login_password").value = "";
            }
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
    
    function addListeners(){
        try{
            $(".logininput").on("keyup", function(e){
                if(e.which == 13){
                    loginController.sendForm();
                }
            });
        }catch(err){
            const message = arguments.callee.name + " - " + err.name + ": " + err.message;
            logService.log(message, "error");
        }
    }
})();