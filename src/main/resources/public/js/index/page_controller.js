(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/index/login_form_controller.js");
        scriptLoader.loadScript("js/index/registration_form_controller.js");
        
        $(document).ready(function(){
            authService.isAuthenticated(function(){window.location.href = "links";})
        });
    }
})();