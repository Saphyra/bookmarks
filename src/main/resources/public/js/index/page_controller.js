(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/index/login_form_controller.js");
        scriptLoader.loadScript("js/index/registration_form_controller.js");
        
        $(document).ready(function(){
            if(authService.isAuthenticated()){
                window.location.href = "links";
            }
        });
    }
})();