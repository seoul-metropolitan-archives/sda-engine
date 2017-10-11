var SimpleController = function(_url)
{
    var url = _url;

    this.getURL = function(code)
    {
        return url[code];
    }
};
SimpleController.prototype ={
    /*
    getURL : function(code)
    {

    },
    */
    defaultMessage : {
        success : "요청에 성공했습니다.",
        error : "요청 중 오류가 발생하였습니다."
    }
    ,message : function(data)
    {
        var thisObj = this;
        this.request("/ad/ad002/getMessage.do",{code : data},function(response){
            if(response.header.result)
            {
                alert(response.body.messageName);
            }
            else
            {
                alert(thisObj.message.error);
            }
        });
    }
    ,request : function(requestCode,data,async,callback)
    {
        var thisObj = this;
        axboot.ajax({
            url : this.getURL(requestCode)
            , type : "POST"
            , data : JSON.stringify(data)
            , async : async
            , contentType : "application/json"
            , dataType : "JSON"
            , success: function(response) {
                if(response.header.result)
                {
                    if(callback && typeof callback === "function")
                        callback(response.body);
                    else
                        alert(thisObj.defaultMessage.success);
                }
                else
                {
                    this.message(response.header.errorCode);
                }
            }
        });
    }
}



