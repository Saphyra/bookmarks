(function PageController(){
    window.pageController = new function(){
        scriptLoader.loadScript("js/account/change_password_controller.js");
        scriptLoader.loadScript("js/account/change_username_controller.js");
        scriptLoader.loadScript("js/account/delete_account_controller.js");
    }
})();