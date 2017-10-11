var AD000Controller = function()
{
    var _url = {
        getService : "/ad/ad000/getServiceList.do"
    }
    SimpleController.call(this,_url);
};
AD000Controller.prototype = new SimpleController();
AD000Controller.prototype.constructor = AD000Controller;

if(undefined == window.rmsoft)
    window.rmsoft = {};
if(undefined == window.rmsoft.controller)
    window.rmsoft.controller = {};

window.rmsoft.controller.ad = {};
window.rmsoft.controller.ad.ad000 = new AD000Controller();