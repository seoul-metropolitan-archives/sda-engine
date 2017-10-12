var AD004Controller = function()
{
    var _url = {
        search_h : "/ad/ad004/ad004/searchPopupHeader.do"
        , insert : ""
        , update : ""
        , delete : ""
    }
    SimpleController.call(this,_url);
};

AD004Controller.prototype = new SimpleController();
AD004Controller.prototype.constructor = AD004Controller;

if(undefined == window.rmsoft)
    window.rmsoft = {};
if(undefined == window.rmsoft.controller)
    window.rmsoft.controller = {};
if(undefined == window.rmsoft.controller.ad)
    window.rmsoft.controller.ad = {};

window.rmsoft.controller.ad.ad004 = new AD004Controller();