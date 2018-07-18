/**
 * 공통으로 사용되는 이벤트를 정의
 */
var BaseForm = {
  init : function()
  {
      $(".bnb").delegate("#inquiry","click",function(){
          ACTIONS.dispatch(ACTIONS.PAGE_SAVE);
      });
      $(".bnb").delegate("#save","click",function(){
          ACTIONS.dispatch(ACTIONS.PAGE_SEARCH);
      });
  }
};
